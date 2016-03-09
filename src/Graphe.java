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

	public String toString() {
		return "nombre de sommet : " + n + "\nnombre d'arrete : " + m
				+ "\ndegre max : " + degreMax + ", degre moyen : " + degreMoyen;
		// + "\narete : " + listeSuccesseur.toString();
	}
	
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
	
	public int getN() {
		return n;
	}
}
