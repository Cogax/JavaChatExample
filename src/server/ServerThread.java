package server;

import java.io.InputStream;
import java.net.*;


public class ServerThread extends Thread {
	
	private Socket client;
	private TCPServer server;
	
	public ServerThread(Socket client, TCPServer srv) {
		this.client = client;
		this.server = srv;
	}
	
	public void run() {
		while(!isInterrupted()) {
			try {
				byte[] b = new byte[128];
				InputStream input;

				// Datenstrom zum Lesen verwenden
				input = client.getInputStream();
				
				// Ankommende Daten lesen
				input.read(b);
				String msg = new String(b).trim() + "\n";
			
				// Den Clients antworten & in Server Konsole schreiben
				server.writeAnswers(msg, client);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
