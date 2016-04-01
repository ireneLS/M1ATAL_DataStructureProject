import java.util.ArrayList;
import java.util.Random;

public class Graphe {
	private int n;// nb de sommet
	private int m = 0; // nb d'arete
	private int degreMax = 0;// degre max
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

		// une fois le graphe construit on peut compter son degre.
		int sommeDegres = 0;
		for (ArrayList<Integer> listeSommet : listeSuccesseur) {
			this.degreMax = Integer.max(this.degreMax, listeSommet.size());
			sommeDegres += listeSommet.size();
		}

		this.degreMoyen = sommeDegres / n;
	}

	/**
	 * constructeur d'un graphe G' correspondant au graphe G privé de son sommet u
	 * \/!\ cette méthode n'initialise pas delta et degreMax.
	 * Elle est utilisé uniquement par VC.ARB_VC qui n'a pas besoin de ces attributs
	 * 
	 * @param g
	 *            un graphe G
	 * @param sommetASupprimer
	 *            le sommet a supprimer de G
	 */
	public Graphe(Graphe g, int sommetASupprimer) {
		this.n = g.getN() - 1;
		// liste successeur ne change pas et ne représente pas listeAretes.
		this.listeSuccesseur = new ArrayList<ArrayList<Integer>>(g.getListeSuccesseur());
		
		ArrayList<Arete> aretesASupprimer = new ArrayList<Arete>();

		// on supprime toutes les aretes incidentes du sommetASupprimer
		for (int sommetIncident : g.getSuccesseurs(sommetASupprimer)) {
			aretesASupprimer.add(new Arete(sommetASupprimer, sommetIncident));
		}
		
		this.listeAretes = new ArrayList<Arete>(g.getListeAretes());
		this.listeAretes.removeAll(aretesASupprimer);

		this.m = listeAretes.size();
		this.degreMax = 0;
		this.degreMoyen = 0;
	}
	
	public boolean aLArete(int i, int j){
		return listeAretes.contains(new Arete(i,j));
	}

	public ArrayList<Integer> getSuccesseurs(int sommet) {
		return listeSuccesseur.get(sommet);
	}

	public String toString() {
		return "nombre de sommet : " + n + "\nnombre d'arrete : " + m
				+ "\ndegre max : " + degreMax + ", degre moyen : " + degreMoyen
				+ "\narete : " + listeSuccesseur.toString();
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

	public int getDegreMax() {
		return degreMax;
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
