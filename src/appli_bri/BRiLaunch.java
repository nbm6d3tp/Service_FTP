package appli_bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import bri.ServeurBRi;
import bri.Service;
import bri.ServiceRegistry;
import bri.User;
import bri.ValidationException;
import clientprog.ListeUsers;

public class BRiLaunch {
	private static final int PORT_SERVICE_CLIPRO = 3000;

	private static final int PORT_SERVICE_CLIAMA = 4000;

	private static List<User> initUsers() throws MalformedURLException {
		List<User> users = new ArrayList<User>();
		users.add(new User("minh", "12345678", "ftp://localhost:2121/"));
		return users;
	}
	// chargement dynamique d'un jarfile contenant une seule classe
	// cette classe implémente l'interface VerySimple

	public static void main(String[] args) throws IOException, ClassNotFoundException, ValidationException {
		List<User> lesUsers = initUsers();
		ListeUsers.setUsers(lesUsers);
		ServiceRegistry.addService(
				ListeUsers.getUser(0).getUrlcl().loadClass("minh.ServiceInversion").asSubclass(Service.class));
		ServiceRegistry
				.addService(ListeUsers.getUser(0).getUrlcl().loadClass("minh.ServiceXML").asSubclass(Service.class));
		try {
			new Thread(new ServeurBRi(PORT_SERVICE_CLIPRO)).start();
			new Thread(new ServeurBRi(PORT_SERVICE_CLIAMA)).start();
		} catch (IOException e) {
			System.err.println("Pb lors de la création du serveur : " + e);
		}
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

		do {
			if (clavier.readLine() != null) {
				System.out.println(ServiceRegistry.toStringue());
			}
		} while (true);
	}
}
