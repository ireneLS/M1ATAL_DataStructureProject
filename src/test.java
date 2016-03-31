import org.chocosolver.util.objects.graphs.UndirectedGraph;
import org.chocosolver.util.objects.setDataStructures.SetType;


public class test {

	public static void main(String[] args) {
		Graphe g = new Graphe(4, 0.5);
		System.out.println(g.getListeAretes().toString());
//		
UndirectedGraph ug = new UndirectedGraph(g.getN(), SetType.LINKED_LIST, true);

for (Arete arete : g.getListeAretes()) {
	ug.addEdge(arete.sommetDepart, arete.sommetArrivee);
} 

System.out.println(ug.toString());
	}

}
