package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Server extends JFrame implements ActionListener {
	private JButton buttonServerSwitch;
	private JTextArea txtConsole;
	
	public static void main(String[] args) {
		Server srv = new Server();
	}
	
	public Server() {
		super("Chat Server");
		
		setLayout(null);
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Elements
		buttonServerSwitch = new JButton("Start Server");
		buttonServerSwitch.addActionListener(this);
		txtConsole = new JTextArea(40, 5);
		
		add(buttonServerSwitch);
		add(txtConsole);
		
		// Positions
		buttonServerSwitch.setBounds(250, 20, 100, 40);
		txtConsole.setBounds(5, 100, 590, 270);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		TCPServer TCPserver = new TCPServer(txtConsole);
		TCPserver.start();
		txtConsole.setText("Server startet\n");
	}
}
