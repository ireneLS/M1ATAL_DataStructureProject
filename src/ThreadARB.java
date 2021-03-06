
public class ThreadARB extends Thread {
	private Graphe graphe;
	private int k =-1;
	private int kMin = 0;
	private double tpsExec = 0.0;

	public ThreadARB(Graphe g, int k) {
		super("ARB");
		this.graphe = g;
		this.k = k;
	}

	public void run() {
		long tempsDebut = System.nanoTime();
		this.kMin = Main.calculeARB_VC(graphe, k);
		tpsExec = ((System.nanoTime()-tempsDebut) / 1000000000.00f);
		
	}

	public int getkMin() {
		return kMin;
	}
	
	public double getTpsExec() {
		return tpsExec;
	}
}
