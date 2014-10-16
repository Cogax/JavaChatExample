package server;

import java.net.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

public class TCPServer extends Thread
{
	private static final int port = 2000;
	private HashMap<String, Socket> clients;
	public JTextArea console;
	
	public TCPServer(JTextArea console)
	{
		clients = new HashMap<String, Socket>();
		this.console = console;
	}
	
	public void run()
	{	
		try {		
			// ServerSocket erzeugen
			ServerSocket srvSocket = new ServerSocket(port);

			while(true) {
			
				// Warten auf Verbindung mit Client und akzeptieren
				Socket client = srvSocket.accept();
				
				// Namen empfangen und auslesen
				byte[] b = new byte[128];
				InputStream nameInput = client.getInputStream();
				nameInput.read(b);
				String name = new String(b).trim();
				System.out.println(new String(b));
				
				// Client speichern
				clients.put(name, client);

				ServerThread t1 = new ServerThread(client, this);
				t1.start();
			}
		}
		catch (UnknownHostException e)
		{
			console.append("Rechenername unbekannt:\n" + e.getMessage() + "\n");
		}
		catch (IOException e)
		{
			console.append("Fehler während der Kommunikation:\n" + e.getMessage() + "\n");
		}
	}
	
	private String getClientName(Socket c) {
		Set set = clients.entrySet();
		Iterator i = set.iterator();
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
	        String name = (String) me.getKey();
	        Socket client = (Socket) me.getValue();
	        
			if(c == client) {
				return name;
			}
		}
		return "";
	}
	
	public void writeAnswers(String msg, Socket client) {
		Set set = clients.entrySet();
		Iterator i = set.iterator();
		String name = getClientName(client);
		String out = new String(name + ": " + msg);
		console.append(out);
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
	        Socket c = (Socket) me.getValue();
	        
			if(c != client) {
				try {
					// Dem Client antworten
					OutputStream output = c.getOutputStream();
					output.write(out.getBytes());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
