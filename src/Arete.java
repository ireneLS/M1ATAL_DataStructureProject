public class Arete {
	int sommetDepart;
	int sommetArrivee;

	public Arete(int sommet1, int sommet2) {
		this.sommetDepart = sommet1;
		this.sommetArrivee = sommet2;
	}

	public boolean equals(Arete aComparer) {
		return (aComparer.getSommetArrivee() == this.sommetDepart || aComparer
				.getSommetDepart() == this.sommetDepart)
				&& (aComparer.getSommetArrivee() == this.sommetArrivee || aComparer
						.getSommetDepart() == this.sommetArrivee);
	}
	

	public int getSommetArrivee() {
		return sommetArrivee;
	}

	public int getSommetDepart() {
		return sommetDepart;
	}
}
