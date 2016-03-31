public class Arete {
	int sommetDepart;
	int sommetArrivee;

	public Arete(int sommet1, int sommet2) {
		this.sommetDepart = sommet1;
		this.sommetArrivee = sommet2;
	}

	/**
	 * @param aComparer
	 *            l'arete a comparer
	 * @return pour une arete (u,v) et une arete AComparer (a,b) retourne vrai
	 *         si (u == a ou u == b) && (v == a ou v == b)
	 */
	@Override
	public boolean equals(Object aComparer) {
		return (((Arete) aComparer).getSommetArrivee() == this.sommetDepart || ((Arete) aComparer)
				.getSommetDepart() == this.sommetDepart)
				&& (((Arete) aComparer).getSommetArrivee() == this.sommetArrivee || ((Arete) aComparer)
						.getSommetDepart() == this.sommetArrivee);
	}

	public int getSommetArrivee() {
		return sommetArrivee;
	}

	public int getSommetDepart() {
		return sommetDepart;
	}

	public String toString() {
		return "(" + sommetDepart + ", " + sommetArrivee + ")";
	}
}
