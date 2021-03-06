public class Arete {
	/**
	 * il n'y a en fait pas d'ordre aux aretes puisqu'on travaille sur des
	 * graphes non orientes
	 */
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
		if (aComparer.getClass().equals(Arete.class)) {
			return (((Arete) aComparer).getSommetArrivee() == this.sommetDepart
					|| ((Arete) aComparer).getSommetDepart() == this.sommetDepart)
					&& (((Arete) aComparer).getSommetArrivee() == this.sommetArrivee
							|| ((Arete) aComparer).getSommetDepart() == this.sommetArrivee);
		} else {// si on veut supprimer toutes les aretes contenant un sommet
				// particulier
			return ((Integer) aComparer).equals(this.sommetArrivee) || ((Integer) aComparer).equals(this.sommetDepart);
		}
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
