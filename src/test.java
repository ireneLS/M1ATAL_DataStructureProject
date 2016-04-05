

public class test {

	public static void main(String[] args) {

		Graphe g = new Graphe(4, 0.5);
		
		System.out.println(g.toString());
		System.out.println(g.getListeAretes().toString());

		
		Graphe gPrime = new Graphe(g, 2);
		
		System.out.println("\n"+gPrime.toString());
		System.out.println(gPrime.getListeAretes().toString());	

		
		System.out.println("\n"+g.toString());
		System.out.println(g.getListeAretes().toString());
	}
}
