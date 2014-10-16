package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;


public class TCPClient extends Thread {
	private JTextArea console;
	private Socket socket;
	
	public TCPClient(Socket socket, JTextArea console) {
		this.socket = socket;
		this.console = console;
	}

	public void run() {
		byte[] b = new byte[128];

		InputStream input;

		try
		{
			while(true) {
				// Input- und Qutputstream zum Lesen und Schreiben erzeugen
				input = socket.getInputStream();

				// Antwort über einen Stream lesen
				input.read(b);
				console.append(new String(b).trim() + "\n");
			}
		}
		catch (Exception e)
		{
			System.out.println("Fehler während der Kommunikation:\n" + e.getMessage());
		}
	}
	
	public void send(String msg) {
		OutputStream output;

		try
		{
			// Input- und Qutputstream zum Lesen und Schreiben erzeugen
			output = socket.getOutputStream();
			
			// Senden einer Nachricht über einen Stream
			output.write(msg.getBytes());
		}
		catch (UnknownHostException e)
		{
			System.out.println("Rechenername unbekannt:\n" + e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("Fehler während der Kommunikation:\n" + e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println("Fehler während der Kommunikation:\n" + e.getMessage());
		}
	}
}
