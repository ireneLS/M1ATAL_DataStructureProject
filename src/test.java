

public class test {

	public static void main(String[] args) {

		Graphe g = new Graphe(40, 0.2);
		
		//System.out.println(g.toString());
//		System.out.println(g.getListeAretes().toString());

		System.out.println(VC.KERNEL_VC(g, 4));
	}
}
