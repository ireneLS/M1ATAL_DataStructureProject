import java.util.Scanner;

public class Main {

	private static int nbExecARB = 0;
	private static int nbExecKERNEL = 0;

	public static void main(String[] args) {
		double p;
		Graphe g;
		System.out.println("Bonjour,");
		Scanner sc = new Scanner(System.in);
		int choix = Integer.MAX_VALUE;
		int n;
		while (choix != 0) {
			System.out.print(" quelle valeur de n (nombre de sommets) pour votre graphe ?\n");
			n = sc.nextInt();
			System.out.println(
					"_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			// premiere ligne titre
			System.out.println(
					"\t\t||\t\t\t\t\t\t||\t\t\tKERNEL-VC\t\t||\t\t\tARB-VC\t\t\t||\tGREEDY-VC\t\t||\tIPL-VC\t\t\t||");
			// deuxieme ligne titre
			System.out.println("|\tn=" + n
					+ "\t||\tm\t|\t\u0394\t|\tdM\t||\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\tVal\t||\tTemps\t|\tVal\t||");
			// --------------------
			System.out.println(
					"_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");

			double[] tabValP = { (3.0 / ((double) n)), (4.0 / ((double) n)), (5.0 / ((double) n)),
					(Math.log((double) n) / ((double) n)), (1.0 / Math.sqrt((double) n)), 0.1, 0.2 };

			for (int i = 0; i < tabValP.length; i++) {
				p = tabValP[i];
				g = new Graphe(n, p);
				int valGreedy = VC.GreedyVC(g);
				int valIPL = VC.IPL_VC(g);
				ThreadARB tarb = new ThreadARB(g, Math.min(valGreedy, valIPL));
				tarb.start();
				try {
					
					tarb.join(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				int valARB = tarb.getkMin();
				// int valARB = calculeARB_VC(g, Math.min(valGreedy, valIPL));
				int valKERNEL = 0;// calculeKERNEL_VC(g, Math.min(valGreedy,
									// valIPL));
				// graphe
				System.out.print("|p= n/3 = " + (double) (Math.floor(p * 1000) / 1000) + "\t||" + g.getM() + "\t\t|"
						+ g.getDegreMax() + "\t\t|" + g.getDegreMoyen() + "\t\t||");
				// KERNEL
				System.out.print("\tTemps\t|\t" + valKERNEL + "\t|\t" + nbExecKERNEL + "\t||");
				// ARB
				System.out.print("\tTemps\t|\t" + valARB + "\t|\t" + nbExecARB + "\t||");
				// GREEDY
				System.out.print("\tTemps\t|\t" + valGreedy + "\t||");
				// IPL
				System.out.println("\tTemps\t|\t" + valIPL + "\t||");
				System.out.println(
						"_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
			}
			System.out.println("quitter : 0, tester une autre valeur de n : 1");
			choix = sc.nextInt();
			System.out.println("\n\n");

		}
		sc.close();
	}

	/**
	 * Le k optimal est dans l'intervalle [w/2, w]
	 * 
	 * @param g
	 *            le graphe a tester
	 * @param w
	 *            = min(vGREEDY, vIPL) l'entier le plus petit des résultats
	 *            deja obtenus.
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

	/**
	 * Le k optimal est dans l'intervalle [w/2, w]
	 * 
	 * @param g
	 *            le graphe a tester
	 * @param w
	 *            = min(vGREEDY, vIPL) l'entier le plus petit des résultats
	 *            deja obtenus.
	 * @return
	 */
	static int calculeKERNEL_VC(Graphe g, int k) {
		int valKMin = (k / 2);
		int valKMax = k;
		int valKMilieu = (int) Math.ceil(((valKMax + valKMin) / 2));
		// System.out.println("val k min = "+ valKMin);
		// System.out.println("val k max = "+ valKMax);
		// System.out.println(" val k milieu = " + valKMilieu);

		while (valKMin <= valKMax) {
			nbExecKERNEL += 1;
			if (VC.KERNEL_VC(g, valKMilieu)) {
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
