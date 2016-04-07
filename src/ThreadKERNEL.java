
public class ThreadKERNEL extends Thread {
	private Graphe graphe;
	private int k=-1;
	private int kMin = 0;
	private double tpsExec = 0.0;

	public ThreadKERNEL(Graphe g, int k) {
		super("KERNEL");
		this.graphe = g;
		this.k = k;
	}

	public void run() {
		long tempsDebut = System.nanoTime();
		this.kMin = Main.calculeKERNEL_VC(graphe, k);
		tpsExec = ((System.nanoTime()-tempsDebut) / 1000000000.00f);
		
	}

	public int getkMin() {
		return kMin;
	}
	
	public double getTpsExec() {
		return tpsExec;
	}
}
