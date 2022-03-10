package clientprog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bri.Service;
import bri.ServiceRegistry;
import bri.User;
import bri.ValidationException;

public class ServiceFournir implements Service {

	private Socket client;
	private User user;

	public ServiceFournir(Socket socket, User user) {
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
				msg += "Votre choix: Fournir un nouveau service##";
				msg += ServiceRegistry.toStringue();
				msg += "Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp##";
				msg += "Tapez 'menu' pour rentrer au menu##";
				msg += "A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer:";
				out.println(errString + "##" + msg);
				errString = "";

				String classeName = in.readLine();
				if (classeName.equals("menu")) {
					break;
				}

				try {

					ServiceRegistry.addService(user.getUrlcl().loadClass(classeName).asSubclass(Service.class));
					errString = "Ajoute avec succes";
				} catch (ClassCastException e) {
					errString += "La classe doit implémenter bri.Service";
					;
				} catch (ValidationException e) {
					errString += e.getMessage();
					;
				} catch (ClassNotFoundException e) {
					errString += "La classe n'est pas sur le serveur ftp dans home";
				} catch (Exception e) {
					errString += "La classe n'est pas sur le serveur ftp dans home";
				}

			} while (true);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
