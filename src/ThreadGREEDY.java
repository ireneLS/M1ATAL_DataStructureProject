
public class ThreadGREEDY extends Thread {
	private Graphe graphe;
	private int val = 0;
	private double tpsExec = 0.0;

	public ThreadGREEDY(Graphe g) {
		super("GREEDY");
		this.graphe = g;
	}

	public void run() {
		long tempsDebut = System.nanoTime();
		this.val = VC.GreedyVC(graphe);
		tpsExec = ((System.nanoTime()-tempsDebut) / 1000000000.00f);
		
	}

	public int getVal() {
		return val;
	}
	
	public double getTpsExec() {
		return tpsExec;
	}
}
