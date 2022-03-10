package bri;

import java.io.IOException;
import java.net.ServerSocket;

import clientama.ServiceClientAma;
import clientprog.ServiceClientProg;

public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	private int port;

	// Cree un serveur TCP - objet de la classe ServerSocket
	public ServeurBRi(int port) throws IOException {
		listen_socket = new ServerSocket(port);
		this.port = port;
	}

	// restituer les ressources --> finalize
	@Override
	protected void finalize() throws Throwable {
		try {
			listen_socket.close();
		} catch (IOException e1) {
		}
	}

	// lancement du serveur
	public void lancer() {
		new Thread(this).start();
	}

	// Le serveur ecoute et accepte les connections.
	// pour chaque connection, il cree un ServiceInversion,
	// qui va la traiter.
	@Override
	public void run() {
		try {
			System.out.println("Serveur demarre...");
			while (true) {
				if (port == 3000) {
					new Thread(new ServiceClientProg(listen_socket.accept())).start();
					System.out.println("Un programmeur a connecte");
				}
				if (port == 4000) {
					new Thread(new ServiceClientAma(listen_socket.accept())).start();
					System.out.println("Un amateur a connecte");
				}
			}
		} catch (IOException e) {
			try {
				listen_socket.close();
			} catch (IOException e1) {
			}
			System.err.println("Pb sur le port d'écoute :" + e);
		}
	}
}
