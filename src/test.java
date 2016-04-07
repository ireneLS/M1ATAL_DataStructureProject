import java.io.File;
import java.io.FileWriter;

public class test {

	public static void main(String[] args) {

		final String chemin = "..\\resultat.csv";
		final File fichier = new File(chemin);
		try {
			// Creation du fichier
			fichier.createNewFile();
			// creation d'un writer (un écrivain)
			final FileWriter writer = new FileWriter(fichier);
			try {
				writer.write("ceci est un texte\n");
				writer.write("encore et encoretututut");
			} finally {
				// quoiqu'il arrive, on ferme le fichier
				writer.close();
			}
		} catch (Exception e) {
			System.out.println("Impossible de creer le fichier");
		}
	}

}
