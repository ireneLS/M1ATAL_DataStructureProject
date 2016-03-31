import java.util.ArrayList;
import java.util.Random;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.objects.graphs.UndirectedGraph;
import org.chocosolver.util.objects.setDataStructures.SetType;

public class VC {
	static int GreedyVC(Graphe g) {
		Random rand = new Random();
		ArrayList<Arete> aretes = new ArrayList<Arete>(g.getListeAretes());
		ArrayList<Integer> VPrime = new ArrayList<Integer>(); // sommets
																// selectionnes
		ArrayList<Arete> EPrime = new ArrayList<Arete>(); // arete a supprimer
		Arete areteATraiter;
		int u, v;
		while (aretes.size() > 0) {// tant qu'on a des aretes
			EPrime.clear();
			// l'argument de nextInt est exclusif on peut donc laisser
			// aretes.size() sans -1
			int randomNumber = rand.nextInt(aretes.size());
			// on choisi une arete a traiter
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
			// on supprime toutes les arretes de incidentes à u et v des aretes
			// du graphe.
			// /!\ pour la methode Arete.equals(Object) (u,v) == (v,u) on a donc
			// pas a rajouter les aretes "inverse"
			aretes.removeAll(EPrime);
		}

		return VPrime.size();
	}
	
	public static int IPL_VC(Graphe g){
		Solver solveur = new Solver("IPL_VC");
		
		RealVar[] variables = VariableFactory.realArray("x",g.getN(), 0.0, 1.0,0.001d, solveur);
		
		UndirectedGraph ug = new UndirectedGraph(g.getN(), SetType.LINKED_LIST, true);

		for (Arete arete : g.getListeAretes()) {
			ug.addEdge(arete.sommetDepart, arete.sommetArrivee);
		}
		//IntConstraintFactory
		//Constraint C1 = 
		//RealVar objectif = VariableFactory.
		
		return 0;
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
			//System.out.println("nb aretes : " + aretes.size());
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
			// System.out.println("aretes G1 : "+G1.getListeAretes().toString());
			// System.out.println("aretes G2 : "+G2.getListeAretes().toString());

			return ARB_VC(new Graphe(g, areteATraiter.getSommetDepart()),
					(k - 1))
					|| ARB_VC(new Graphe(g, areteATraiter.getSommetArrivee()),
							(k - 1));
		}
	}
}
