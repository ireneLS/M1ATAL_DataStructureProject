import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	private static int nbExecARB = 0;

	public static void main(String[] args) {
		double p;
		Graphe g;
		ArrayList<ArrayList<String>> resultats = new ArrayList<ArrayList<String>>();
		System.out.println("Bonjour,");
		Scanner sc = new Scanner(System.in);
		int choix = Integer.MAX_VALUE;
		int n;
		while (choix != 0) {
			System.out
					.print(" quelle valeur de n (nombre de sommets) pour votre graphe ?\n");
			n = sc.nextInt();
			System.out
					.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			System.out
					.println("\t\t||\t\t\t\t\t\t||\t\t\tKERNEL-VC\t\t||\t\t\tARB-VC\t\t\t||\tGREEDY-VC\t\t||\tIPL-VC\t\t\t||");
			System.out
					.println("|\tn="
							+ n
							+ "\t||\tm\t|\t\u0394\t|\tdM\t||\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\tVal\t||\tTemps\t|\tVal\t||");
			System.out
					.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			p = ((double) n) / 3;
			g = new Graphe(n, p);
			System.out.print("|p= n/3 = "
					+ (double) (Math.floor(p * 1000) / 1000) + "\t||"
					+ g.getM() + "\t\t|" + g.getDegreMax() + "\t\t|"
					+ g.getDegreMoyen() + "\t\t||");
			int valGreedy = VC.GreedyVC(g);
			int valIPL = VC.IPL_VC(g);
			System.out.println("\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\t"
					+ calculeARB_VC(g, Math.min(valGreedy, valIPL))
					+ "\t|\t"+nbExecARB+"\t||\tTemps\t|\t" + valGreedy
					+ "\t||\tTemps\t|\t"+valIPL+"\t||");
			System.out
					.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");

			System.out.println("quitter : 0, tester une autre valeur de n : 1");
			choix = sc.nextInt();
			System.out.println("\n\n");

		}
		sc.close();
	}

	static void afficheResultat(ArrayList<ArrayList<String>> tab) {
		for (ArrayList<String> ligne : tab) {
			for (String colonne : ligne) {
				System.out.println(colonne + "\t||");
			}
			System.out
					.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		}
	}

	/**
	 * Le k optimal est dans l'intervalle [w/2, w]
	 * 
	 * @param g
	 *            le graphe a tester
	 * @param w
	 *            = min(vGREEDY, vIPL) l'entier le plus petit des résultats deja
	 *            obtenus.
	 * @return
	 */
	static int calculeARB_VC(Graphe g, int k) {
		int valKMin = (k / 2);
		int valKMax = k;
		int valKMilieu = (int) Math.ceil(((valKMax + valKMin) / 2));
		// System.out.println("val k min = "+ valKMin);
		// System.out.println("val k max = "+ valKMax);
		// System.out.println(" val k milieu = " + valKMilieu);

		while (valKMin <= valKMax) {
			nbExecARB += 1;
			if (VC.ARB_VC(g, valKMilieu)) {
				valKMax = valKMilieu - 1;
			} else {
				valKMin = valKMilieu + 1;
			}
			valKMilieu = (int) Math.ceil(((valKMax + valKMin) / 2));
		}

		// System.out.println("val k min = "+ valKMin + " "+ VC.ARB_VC(g,
		// valKMin));
		return valKMin;
	}
}
