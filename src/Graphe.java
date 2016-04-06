import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Graphe {
	private int n;// nb de sommet
	private int m = 0; // nb d'arete
	private int degreMax = 0;// degre max
	private int degreMin = Integer.MAX_VALUE;// degre min utile pour Kernel_VC
	private int degreMoyen; // degre moyen
	// liste de sucesseur representant le graphe
	private ArrayList<ArrayList<Integer>> listeSuccesseur;
	private ArrayList<Arete> listeAretes = new ArrayList<Arete>();

	public Graphe(int n, double p) {
		// nombre aleatoire pour generer les aretes
		Random random = new Random();
		// initialisation des valeurs
		this.n = n;
		this.listeSuccesseur = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; i++) {
			this.listeSuccesseur.add(new ArrayList<Integer>());
		}

		// on construit les arretes
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				// si on est dans la proba
				if (random.nextDouble() <= p) {
					// on ajoute l'arete dans la liste de succ
					this.listeSuccesseur.get(i).add(j);
					this.listeSuccesseur.get(j).add(i);
					this.listeAretes.add(new Arete(i, j));
					// on en compte une de plus
					this.m = this.m + 1;
				}
			}
		}

		// une fois le graphe construit on peut compter ses degres.
		int sommeDegres = 0;
		for (ArrayList<Integer> listeSommet : listeSuccesseur) {
			// un sommet isole n'est pas interessant
			if (listeSommet.size() != 0) {
				this.degreMax = Math.max(this.degreMax, listeSommet.size());
				this.degreMin = Math.min(this.degreMin, listeSommet.size());
				sommeDegres += listeSommet.size();
			}
		}

		this.degreMoyen = sommeDegres / n;
	}

	/**
	 * constructeur d'un graphe G' correspondant au graphe G prive de son sommet
	 * u \/!\ cette methode n'initialise pas delta. Elle est utilise par
	 * VC.ARB_VC et KERNEL8VC qui n'ont pas besoin de cet attribut
	 * 
	 * @param g
	 *            un graphe G
	 * @param sommetASupprimer
	 *            le sommet a supprimer de G
	 */
	public Graphe(Graphe g, int sommetASupprimer) {
		// on a toujours autant de sommet, les sommets sont juste isoles des
		// autres.
		this.n = g.getN();

		// ----- MAJ LISTE SUCCESSEUR -----
		// liste successeur representera listeAretes tout en gardant les sommets
		// a supprimer pour ne pas decaler l'ordre.
		this.listeSuccesseur = new ArrayList<ArrayList<Integer>>();
		for ( ArrayList<Integer> successeurs : g.getListeSuccesseur()) {
			this.listeSuccesseur.add(new ArrayList<Integer>(successeurs));
		}
		
		// on supprime les aretes incidentes au sommet a supprimer
		// dans un sens
		this.listeSuccesseur.get(sommetASupprimer).clear();
		// et dans l'autre
		for (ArrayList<Integer> successeurs : this.listeSuccesseur) {
			// on teste si on est sur un sommet isole, dans ce cas pas besoin de
			// supprimer
			if (successeurs.size() != 0) {
				successeurs.remove((Object) sommetASupprimer);
				// on en profite pour mettre a jour les degre min et max
				this.degreMax = Math.max(this.degreMax, successeurs.size());

				// /!\ le degre min ne doit pas etre confondu avec un sommet
				// devenu isole on rajoute donc une condition pour exclure ces
				// sommets
				if (successeurs.size() != 0) {
					this.degreMin = Math.min(this.degreMin, successeurs.size());
				}
			}
		}

		// ----- MAJ LISTE ARRETES -----
		this.listeAretes = new ArrayList<Arete>(g.getListeAretes());

		// la liste des aretes qu'on supprimera de la listeAretes...
		ArrayList<Arete> aretesASupprimer = new ArrayList<Arete>();

		// ...toutes les aretes incidentes du sommetASupprimer
		for (int sommetIncident : g.getSuccesseurs(sommetASupprimer)) {
			aretesASupprimer.add(new Arete(sommetASupprimer, sommetIncident));
		}
		/*
		 * /!\ ArrayList.removeAll(...) se base sur la methode equals du type de
		 * la collection (ici Arete). pour la methode Arete.equals(Object),
		 * (u,v) == (v,u) on a donc pas a rajouter les aretes "inverses" dans
		 * EPrime.
		 */
		this.listeAretes.removeAll(aretesASupprimer);

		this.m = listeAretes.size();

		this.degreMoyen = 0;
	}
	
	public boolean aUnVC(ArrayList<Integer> VCaTester){
		ArrayList<Arete> listeAretesTmp = new ArrayList<Arete>(this.listeAretes);
		for (Object sommet : VCaTester) {
			// on supprime toutes les aretes contenant le sommet
			while(listeAretesTmp.remove(sommet));
		}
		// si on a supprimer toutes les aretes c'est que c'est un VC
		return listeAretesTmp.isEmpty();
	}

	public ArrayList<Integer> getSuccesseurs(int sommet) {
		return listeSuccesseur.get(sommet);
	}

	public String toString() {
		return "nombre de sommet : " + n + "\nnombre d'arrete : " + m + "\ndegre max : " + degreMax + ", degre moyen : "
				+ degreMoyen + ", degre min : " + degreMin + "\narete : " + listeSuccesseur.toString();
	}
	
	public String toStringSimple() {
		return "nombre de sommet : " + n + "\nnombre d'arrete : " + m + "\ndegre max : " + degreMax + ", degre moyen : "
				+ degreMoyen + ", degre min : " + degreMin;
	}

	/**
	 * 
	 * @return le nombre d'aretes du graphe
	 */
	public int getM() {
		return m;
	}

	public ArrayList<Arete> getListeAretes() {
		return listeAretes;
	}
	
	public int getDegre(int i){
		return this.listeSuccesseur.get(i).size();
	}

	public int getDegreMax() {
		return degreMax;
	}

	public int getDegreMin() {
		return degreMin;
	}

	public int getDegreMoyen() {
		return degreMoyen;
	}

	public ArrayList<ArrayList<Integer>> getListeSuccesseur() {
		return listeSuccesseur;
	}

	/**
	 * 
	 * @return le nombre de sommet du graphe
	 */
	public int getN() {
		return n;
	}
}
