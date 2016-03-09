import java.util.ArrayList;
import java.util.Random;


public class VC {
	static int GreedyVC(Graphe g){
		Random rand = new Random();
		ArrayList<Arete> aretes = new ArrayList<Arete>(g.getListeAretes());
		ArrayList<Integer> VPrime = new ArrayList<Integer>(); // sommets selectionnes
		ArrayList<Arete> EPrime = new ArrayList<Arete>(); // arete a supprimer
		Arete areteATraiter;
		while (aretes.size()>0) {
			int randomNumber = rand.nextInt(aretes.size() - 0) + 0;
			areteATraiter = aretes.get(randomNumber);
			for (int sommetIncident : g.getListeSuccesseur().get(areteATraiter.getSommetDepart())) {
				// pour chaque sommet, ajouter une arete dans la liste des aretes a supprimer
			}
			for (int sommetIncident : g.getListeSuccesseur().get(areteATraiter.getSommetArrivee())) {
				// pour chaque sommet, ajouter une arete dans la liste des aretes a supprimer
				
				// TODO gerer le cas de l'arete en question
			}
		}
	}
}
