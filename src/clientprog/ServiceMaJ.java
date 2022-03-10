package clientprog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bri.Service;
import bri.ServiceRegistry;
import bri.User;
import bri.ValidationException;

public class ServiceMaJ implements Service {
	private Socket client;
	private User user;

	public ServiceMaJ(Socket socket, User user) {
		client = socket;
		this.user = user;
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	@Override
	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			String errString = "";

			do {
				String msg = "";
				msg += "Votre choix: Mettre-a-jour un service##";
				msg += ServiceRegistry.toStringue();
				msg += "Tapez 'menu' pour rentrer au menu##";
				msg += "Tapez le numéro de service désiré :";
				out.println(errString + "##" + msg);
				String choixString = in.readLine();
				if (choixString.equals("menu")) {
					break;
				}
				int servicedesire;
				try {
					servicedesire = Integer.parseInt(choixString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					servicedesire = 0;
				}

				out.println("Tapez le nom de la classe :");
				choixString = in.readLine();
				if (choixString.equals("menu")) {
					break;
				}

				errString = "";

				try {
					ServiceRegistry.updateService(servicedesire,
							user.getUrlcl().loadClass(choixString).asSubclass(Service.class));
					errString = "Mettre a jour avec succes";
				}

				catch (ClassCastException e) {
					errString += "La classe doit implémenter bri.Service";
					;
				} catch (ValidationException e) {
					errString += e.getMessage();
					;
				} catch (ClassNotFoundException e) {
					errString += "La classe n'est pas sur le serveur ftp dans home";
				} catch (IndexOutOfBoundsException e) {
					errString += "Ne peut pas trouvez service";
				} catch (Exception e) {
					errString += e.toString();
				}

			} while (true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}