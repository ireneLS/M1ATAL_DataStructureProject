import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

	private static int nbExecARB = 0;
	private static int nbExecKERNEL = 0;
	// le temps max d'execution accepte en millisecondes
	private static int tpsMax = 180000;

	public static void main(String[] args) {
		double p;
		Graphe g;
		System.out.println("Bonjour,");
		Scanner sc = new Scanner(System.in);
		int choix = Integer.MAX_VALUE;
		int n = 10;

		String chemin = "..\\resultat.csv";
		File fichier = new File(chemin);
		StringBuffer resultStr = new StringBuffer(250);

		//while (choix != 0) {
		while (n<=100) {
			System.out.print(" quelle valeur de n (nombre de sommets) pour votre graphe ?\n");
			//n = sc.nextInt();
			System.out.println("n = "+n);
			resultStr.append("n = ").append(n).append("\n");
//			System.out.println(
//					"_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
//			// premiere ligne titre
//			System.out.println(
//					"\t\t||\t\t\t\t\t\t||\t\t\tKERNEL-VC\t\t||\t\t\tARB-VC\t\t\t||\tGREEDY-VC\t\t||\tIPL-VC\t\t\t||");
//			// deuxieme ligne titre
//			System.out.println("|\tn=" + n
//					+ "\t||\tm\t|\t\u0394\t|\tdM\t||\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\tVal\t|\tNb Exec\t||\tTemps\t|\tVal\t||\tTemps\t|\tVal\t||");
//			// --------------------
//			System.out.println(
//					"_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");

			// le tableau des valeurs que prend p a chaque ligne
			double[] tabValP = { (3.0 / ((double) n)), (4.0 / ((double) n)), (5.0 / ((double) n)),
					(Math.log((double) n) / ((double) n)), (1.0 / Math.sqrt((double) n)), 0.1, 0.2 };

			for (int i = 0; i < tabValP.length; i++) {
				p = tabValP[i];
				g = new Graphe(n, p);

				ThreadGREEDY tGreedy = new ThreadGREEDY(g);
				ThreadIPL tIpl = new ThreadIPL(g);
				try {
					tGreedy.start();
					tGreedy.join(tpsMax);
					tIpl.start();
					tIpl.join(tpsMax);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int valGreedy = tGreedy.getVal();
				double tpsGreedy = tGreedy.getTpsExec();
				int valIPL = tIpl.getVal();
				double tpsIPL = tIpl.getTpsExec();

				ThreadARB tarb = new ThreadARB(g, Math.min(valGreedy, valIPL));
				ThreadKERNEL tKernel = new ThreadKERNEL(g, Math.min(valGreedy, valIPL));
				try {
					tarb.start();
					tarb.join(tpsMax);
					tKernel.start();
					tKernel.join(tpsMax);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int valARB = tarb.getkMin();
				double tpsARB = tarb.getTpsExec();
				int valKERNEL = tKernel.getkMin();
				double tpsKERNEL = tKernel.getTpsExec();

				// ================= AFFICHAGE =================
//				// graphe
//				System.out.print("|p= n/3 = " + (double) (Math.floor(p * 1000) / 1000) + "\t||" + g.getM() + "\t\t|"
//						+ g.getDegreMax() + "\t\t|" + g.getDegreMoyen() + "\t\t||");
//				// KERNEL
//				if (tpsKERNEL == 0.0) {
//					System.out.print("\tNRP");
//				} else {
//					System.out.printf("\t%.3fs", tpsKERNEL);
//				}
//				System.out.print("\t|\t" + valKERNEL + "\t|\t" + nbExecKERNEL + "\t||");
//				// ARB
//				if (tpsARB == 0.0) {
//					System.out.print("\tNRP");
//				} else {
//					System.out.printf("\t%.3fs", tpsARB);
//				}
//				System.out.print("\t|\t" + valARB + "\t|\t" + nbExecARB + "\t||");
//				// GREEDY
//				if (tpsGreedy == 0.0) {
//					System.out.print("\tNRP");
//				} else {
//					System.out.printf("\t%.3fs", tpsGreedy);
//				}
//				System.out.print("\t|\t" + valGreedy + "\t||");
//				// IPL
//				if (tpsIPL == 0.0) {
//					System.out.print("\tNRP");
//				} else {
//					System.out.printf("\t%.3fs", tpsIPL);
//				}
//				System.out.println("\t|\t" + valIPL + "\t||");
//				System.out.println(
//						"_________________________________________________________________________________________________________________________________________________________________________________________________________________________________");

				resultStr.append("p = n/3 = " + p + "; " + g.getM() + "; " + g.getDegreMax() + "; " + g.getDegreMoyen()
						+ "; " + (tpsKERNEL == 0.0 ? "NRP" : tpsKERNEL) + "; " + valKERNEL + "; " + nbExecKERNEL + "; "
						+ (tpsARB == 0.0 ? "NRP" : tpsARB) + "; " + valARB + "; " + nbExecARB + "; "
						+ (tpsGreedy == 0.0 ? "NRP" : tpsGreedy) + "; " + valGreedy + "; "
						+ (tpsIPL == 0.0 ? "NRP" : tpsIPL) + "; " + valIPL + "\n");

			}
			//System.out.println("quitter : 0, tester une autre valeur de n : 1");
			//choix = sc.nextInt();
			System.out.println("\n\n");

			// System.out.println(resultStr.toString());
		n+=10;
		}

		try {
			if (!fichier.exists()) {
				// Creation du fichier
				fichier.createNewFile();
			}
			// creation d'un writer (un écrivain)
			FileWriter writer = new FileWriter(fichier);

			try {
				writer.write(resultStr.toString());
			} finally {
				writer.close();
			}
		} catch (Exception e) {
			System.out.println("Impossible de creer le fichier");
		}

		sc.close();

	}

	/**
	 * Le k optimal est dans l'intervalle [w/2, w]
	 * 
	 * @param g
	 *            le graphe a tester
	 * @param w
	 *            = min(vGREEDY, vIPL) l'entier le plus petit des rÃ©sultats
	 *            deja obtenus.
	 * @return
	 */
	static int calculeARB_VC(Graphe g, int k) {
		nbExecARB = 0;
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
	 *            = min(vGREEDY, vIPL) l'entier le plus petit des resultats deja
	 *            obtenus.
	 * @return
	 */
	static int calculeKERNEL_VC(Graphe g, int k) {
		nbExecKERNEL = 0;
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
