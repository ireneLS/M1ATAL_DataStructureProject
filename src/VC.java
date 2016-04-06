import java.util.ArrayList;
import java.util.Random;

import org.chocosolver.solver.ResolutionPolicy;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

public class VC {
	static int GreedyVC(Graphe g) {
		Random rand = new Random();
		ArrayList<Arete> aretes = new ArrayList<Arete>(g.getListeAretes());
		// sommets selectionnes
		ArrayList<Integer> VPrime = new ArrayList<Integer>();
		// aretes a supprimer
		ArrayList<Arete> EPrime = new ArrayList<Arete>();
		Arete areteATraiter;
		int u, v;
		// tant qu'on a des aretes
		while (aretes.size() > 0) {
			EPrime.clear();
			// l'argument de nextInt est exclusif on peut donc laisser
			// aretes.size() sans -1 (cf doc Random)
			int randomNumber = rand.nextInt(aretes.size());
			// on choisi une arete a traiter aleatoirement dans les aretes du
			// graphes
			areteATraiter = aretes.get(randomNumber);
			u = areteATraiter.getSommetDepart();
			v = areteATraiter.getSommetArrivee();
			// on ajoute les sommets incidents a l'arete a VPrime
			VPrime.add(u);
			VPrime.add(v);

			// on ajoute a EPrime les aretes incidentes a u et a v
			for (int sommetIncident : g.getSuccesseurs(u)) {
				// pour chaque sommet incident a u, ajouter une arete dans la
				// liste des aretes a supprimer
				EPrime.add(new Arete(u, sommetIncident));
			}
			for (int sommetIncident : g.getSuccesseurs(v)) {
				// pour chaque sommet incident a v, ajouter une arete dans la
				// liste des aretes a supprimer
				EPrime.add(new Arete(v, sommetIncident));
			}
			/*
			 * on supprime toutes les arretes de incidentes a u et v du graphe g
			 * /!\ pour la methode Arete.equals(Object) (u,v) == (v,u) on a donc
			 * pas a rajouter les aretes "inverses" dans EPrime. Puisque
			 * ArrayList.removeAll(...) se base sur la methode equals du type de
			 * la collection (ici Arete)
			 */
			aretes.removeAll(EPrime);
		}

		return VPrime.size();
	}

	/**
	 * Resolution du probleme de Vertex Cover grace a un solveur. On travaille
	 * ici avec des variables entieres. Chaque variable representant un sommet.
	 * Si la varible xi == 1 alors le sommet i est dans le VC, sinon xi == 0 et
	 * i n'est PAS dans le VC
	 * 
	 * @param g
	 * @return
	 */
	public static int IPL_VC(Graphe g) {
		Solver solveur = new Solver("IPL_VC");

		/*
		 * les variables sont toutes de valeur 0 ou 1, pas besoin d'ajouter de
		 * contrainte
		 */
		IntVar[] variables = VariableFactory.integerArray("x", g.getN(), 0, 1, solveur);

		// contrainte : chaque arete a au moins un de ses sommets compris dans
		// le VC donc pour toutes les aretes de g on ajoute une contrainte
		for (Arete arete : g.getListeAretes()) {
			solveur.post(IntConstraintFactory.arithm(variables[arete.getSommetDepart()], "+",
					variables[arete.getSommetArrivee()], ">=", 1));
		}

		/*
		 * creation de la fonction objectif a minimiser. Dans le solveur elle
		 * est representee sous la forme d'une variable entiere dont la valeur
		 * est comprise entre 0 et le nombre de sommet
		 */
		IntVar obj = VariableFactory.integer("MIN_VC", 0, g.getN(), solveur);
		solveur.post(IntConstraintFactory.sum(variables, obj));
		solveur.set(IntStrategyFactory.lexico_Split(variables));
		solveur.findOptimalSolution(ResolutionPolicy.MINIMIZE, obj);

		try {
			solveur.restoreLastSolution();
			// for (int i = 0; i < variables.length; i++) {
			// System.out.println("x" + i + " = " + variables[i].getValue());
			// }
		} catch (ContradictionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// // affichage des contraintes
		// for (int i = 0; i < solveur.getNbCstrs(); i++) {
		// System.out.println("contrainte " + i + " = " +
		// solveur.getCstrs()[i]);
		// }

		return obj.getValue();
	}

	public static boolean ARB_VC(Graphe g, int k) {
		ArrayList<Arete> aretes = new ArrayList<Arete>(g.getListeAretes());
		if (aretes.size() <= 0) {
			return true;
		} else if (k == 0) {
			return false;
		} else {
			Random rand = new Random();
			// int u, v;
			// Graphe G1, G2;
			// System.out.println("nb aretes : " + aretes.size());
			int randomNumber = rand.nextInt(aretes.size());
			// l'argument de nextInt est exclusif on peut donc laisser
			// aretes.size() sans -1
			// on choisi une arete a traiter
			Arete areteATraiter = aretes.get(randomNumber);
			// u = areteATraiter.getSommetDepart();
			// v = areteATraiter.getSommetArrivee();
			// G1 = new Graphe(g, u);
			// G2 = new Graphe(g, v);
			// System.out.println("u = "+u+", v = "+v);
			// System.out.println("aretes G : "+g.getListeAretes().toString());
			// System.out.println("aretes G1 :
			// "+G1.getListeAretes().toString());
			// System.out.println("aretes G2 :
			// "+G2.getListeAretes().toString());

			return ARB_VC(new Graphe(g, areteATraiter.getSommetDepart()), (k - 1))
					|| ARB_VC(new Graphe(g, areteATraiter.getSommetArrivee()), (k - 1));
		}
	}

	public static boolean KERNEL_VC(Graphe g, int k) {
		// --- "reduction" de g en g' ---
		// System.out.println(g.toString());
		// System.out.println(g.getListeAretes().toString());
		// G est de degre max k, sinon on peut retourner faux directement
		if (g.getDegreMax() > k) {
			return false;
		}
		if (g.getDegreMin() == 1) {
			// System.out.println("degreMIN !");
			// on cherche le premier sommet de degre 1
			for (int u = 0; u < g.getN(); u++) {
				if (g.getDegre(u) == 1) {
					// System.out.println("on supprime le sommet :
					// "+g.getSuccesseurs(u).get(0));
					return KERNEL_VC(new Graphe(g, g.getSuccesseurs(u).get(0)), k - 1);
				}
			}
		} else if (g.getDegreMax() >= k + 1) {
			// System.out.println("degreMAX !");
			// on cherche le premier sommet de degre >= k+1
			for (int u = 0; u < g.getN(); u++) {
				if (g.getDegre(u) >= k + 1) {
					// System.out.println("on supprime le sommet : "+u);
					return KERNEL_VC(new Graphe(g, u), k - 1);
				}
			}
		}

		// TODO : on a la reduction de I en I'
		// --- graphe reduit ---
		// generation de toutes les combinaison possible.

		ArrayList<ArrayList<Integer>> listeCombinaisons = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> sommetDuGrapheReduit = new ArrayList<Integer>();
		for (int i = 0; i < g.getN(); i++) {
			if (!g.getSuccesseurs(i).isEmpty()) {
				sommetDuGrapheReduit.add(i);
			}
		}
//		System.out.println("graphe reduit : " + g.toString());
//		System.out.println(g.getListeAretes().toString());
//		System.out.println("liste sommet des permut : " + sommetDuGrapheReduit);
		partition(sommetDuGrapheReduit, k, 0, new int[k], listeCombinaisons);
//		System.out.println("liste des permut : "+listeCombinaisons);
		/*
		 * pour toutes les combinaisons possibles de sommets pour un VC de
		 * taille k on teste si c'est un VC de g. et des qu'on en trouve un on retourne oui
		 */
		for (ArrayList<Integer> combinaison : listeCombinaisons) {
			//System.out.println("combi ! "+combinaison.toString());
			if (g.aUnVC(combinaison)) {
				return true;
			}
		}
		// si aucune combinaison n'est un VC alors on retourne faux
		return false;
	}

	// construction recursive des listes possibles
	private static void partition(ArrayList<Integer> n, int k, int index, int[] liste,
			ArrayList<ArrayList<Integer>> allCombi) {
		if (index >= k) {
			// la liste est construite -> FIN
			// on la rajoute a la liste des combis
			// System.out.println(Arrays.toString(liste));
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for (int i = 0; i < k; i++) {
				tmp.add(liste[i]);
			}
			allCombi.add(tmp);
			return;
		}

		// ajoute un nouvel element candidat dans la liste
		// - avec ordre -> candidat: seulement les elements supérieurs au
		// précédant

		int start = 0;
		if (index > 0)
			start = n.indexOf(liste[index - 1]) + 1;

		for (int i = start; i < n.size(); i++) {
			liste[index] = n.get(i);
			partition(n, k, index + 1, liste, allCombi);
		}
	}
	
	
}
