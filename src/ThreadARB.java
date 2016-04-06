
public class ThreadARB extends Thread {
	private Graphe graphe;
	private int k;
	private int kMin = 0;

	public ThreadARB(Graphe g, int k) {
		super("ARB");
		this.graphe = g;
		this.k = k;
	}

	public void run() {
		this.kMin = Main.calculeARB_VC(graphe, k);
	}

	public int getkMin() {
		return kMin;
	}
}
