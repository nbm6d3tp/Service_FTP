package appli_cli;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*ce client respecte le protocole BTTP (BretteText Transfer protocol)
    et ne contient aucune information
	liée au domaine traité par les services que le serveur va nous proposer

	mettre dans les arguments BTTP:host:port
*/

public class AppliClient {
	private static int PORT;
	private static String HOST;

	private static boolean isNumeric(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
		// connexion
		// System.out.print("Tapez l'url de connexion (par défaut
		// BTTP:localhost:1234)");
		String url = args[0];
		try {
			validBTTP(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		Socket socket = null;
		try {

			socket = new Socket(HOST, PORT);

			boolean serviceXML = false;

			FileInputStream fis = null;
			BufferedInputStream bis = null;
			OutputStream os = null;

			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);
			// Informe l'utilisateur de la connection
			System.out.println("Connecté au serveur " + socket.getInetAddress() + ":" + socket.getPort());

			String line;
			// protocole BTTP jusqu'à saisie de "0" ou fermeture coté service
			// réception et affichage de la question provenant du service
			do {

				line = sin.readLine();
				if (line == null) {
					break; // fermeture par le service
				}
				System.out.println(line.replaceAll("##", "\n"));
				// prompt d'invite à la saisie
				System.out.print("->");
				line = clavier.readLine();
				if (line.equals("")) {
					break; // fermeture par le client
				}
				
				
				// envoie au service de la réponse saisie au clavier
				sout.println(line);
			} while (true);
		} catch (IOException e) {
			System.err.println("Fin du service");
		}
		// Refermer dans tous les cas la socket
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e2) {
			;
		}
	}

	private static void validBTTP(String url) throws Exception {
		String[] elts = url.split(":");
		if (elts.length != 3 || !elts[0].equals("BTTP") || !isNumeric(elts[2])) {
			throw new Exception("L'URL ne respecte pas le protocole BTTP");
		}
		HOST = elts[1];
		PORT = Integer.parseInt(elts[2]);
	}
}
