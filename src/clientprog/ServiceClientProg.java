package clientprog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bri.Service;
import bri.User;

public class ServiceClientProg implements Service {

	private Socket client;

	public ServiceClientProg(Socket socket) {
		client = socket;
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

			String mdp;
			String login;
			String msg = "";
			User user = new User();

			do {
				out.println("Tapez votre login:");
				login = in.readLine();

				out.println("Tapez votre mot de passe:");
				mdp = in.readLine();
				user = ListeUsers.getUser(login, mdp);
			} while (user == null);
//
//			try {
//				ServiceRegistry
//						.addService(user.getUrlcl().loadClass("minh.ServiceInversion").asSubclass(Service.class));
//				ServiceRegistry.addService(user.getUrlcl().loadClass("minh.ServiceXML").asSubclass(Service.class));
//
//			} catch (ClassNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (ValidationException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			msg += "Bienvenue dans votre gestionnaire dynamique d'activité BRi##";
			msg += "1) Fournir un nouveau service##";
			msg += "2) Mettre-a-jour un service##";
			msg += "3) Declarer un changement d’adresse de son serveur ftp##";

			do {
				out.println(msg);
				int choix = 0;

				try {
					choix = Integer.parseInt(in.readLine());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					choix = 0;
				}
				if (choix == 1) {

					Thread thread = new Thread(new ServiceFournir(client, user));
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (choix == 2) {
					Thread thread = new Thread(new ServiceMaJ(client, user));
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (choix == 3) {
					Thread thread = new Thread(new ServiceFtp(client, user));
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} while (true);

		} catch (IOException e) {
			// Fin du service
		}

		try {
			client.close();
		} catch (IOException e2) {
		}

	}

}
