package clientprog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;

import bri.Service;
import bri.User;

public class ServiceFtp implements Service {
	private Socket client;
	private User user;

	public ServiceFtp(Socket socket, User user) {
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
				msg += "Votre choix: Changer l'adresse ftp##";
				msg += "Votre adresse actuelle: " + user.getFtp() + "##";
				msg += "Tapez 'menu' pour rentrer au menu##";
				msg += "Tapez votre nouvelle adresse ftp :";
				out.println(errString + "##" + msg);
				String choixString = in.readLine();
				if (choixString.equals("menu")) {
					break;
				}

				errString = "";

				try {
					user.setfileNameURL(choixString);
				} catch (MalformedURLException e) {
					errString += "L'adress ftp invalide";
				} catch (Exception e) {
					errString += e.toString();
				}

			} while (true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
