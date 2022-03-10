package clientama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import bri.Service;
import bri.ServiceRegistry;

public class ServiceClientAma implements Service {

	private Socket client;

	public ServiceClientAma(Socket socket) {
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

			String msg = "";
			String errString = "";
			Class<? extends Service> classe = null;
			int choix = 0;
			do {
				msg = "";
				msg += "Bienvenue dans l'espace Amateur##";
				msg += ServiceRegistry.toStringue();
				msg += "Tapez le numéro de service désiré:";
				out.println(errString + "##" + msg);
				errString = "";
				try {
					choix = Integer.parseInt(in.readLine());
				} catch (Exception e) {
					choix = 0;
				}
				try {
					classe = ServiceRegistry.getServiceClass(choix);

				} catch (Exception e) {
					errString += "Choix invalide";
					choix = 0;
				}
				if (choix != 0) {
					try {
						Constructor<? extends Service> niou = classe.getConstructor(java.net.Socket.class);
						Service service = niou.newInstance(client);
						Thread thread = new Thread(service);
						thread.start();
						try {
							thread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} catch (SecurityException | InstantiationException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
						System.out.println(e);
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
