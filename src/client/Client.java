package client;

import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
	private JButton buttonSend;
	private JButton buttonConnect;
	private JTextArea txtConsole;
	private JLabel labelHost;
	private JLabel labelPort;
	private JLabel labelName;
	private JLabel labelMessage;
	private JTextField txtHost;
	private JTextField txtPort;
	private JTextField txtName;
	private JTextField txtMessage;
	private TCPClient tcpclient;
	
	public static void main(String[] args) {
		Client srv = new Client();
	}
	
	public Client() {
		super("Chat Client");
		setLayout(null);
		setSize(600, 400);
		
		buttonSend = new JButton("send");
		buttonSend.addActionListener(this);
		buttonConnect = new JButton("connect");
		buttonConnect.addActionListener(this);
		txtConsole = new JTextArea();
		labelHost = new JLabel("Host:");
		labelPort = new JLabel("Port:");
		labelName = new JLabel("Name:");
		labelMessage = new JLabel("Message:");
		txtHost = new JTextField();
		txtHost.setColumns(12);
		txtPort = new JTextField();
		txtPort.setColumns(5);
		txtName = new JTextField();
		txtName.setColumns(10);
		txtMessage = new JTextField();
		txtMessage.setColumns(30);
		
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new FlowLayout());
		panelTop.add(labelHost);
		panelTop.add(txtHost);
		panelTop.add(labelPort);
		panelTop.add(txtPort);
		panelTop.add(labelName);
		panelTop.add(txtName);
		panelTop.add(buttonConnect);
		panelTop.setBounds(0, 5, 600, 50);
		add(panelTop);
		
		JPanel panelMiddle = new JPanel();
		panelMiddle.setLayout(new FlowLayout());
		panelMiddle.add(labelMessage);
		panelMiddle.add(txtMessage);
		panelMiddle.add(buttonSend);
		panelMiddle.setBounds(0, 50, 600, 50);
		add(panelMiddle);
		
		add(txtConsole);
		txtConsole.setBounds(0, 100, 600, 300);
		txtConsole.setEditable(false);
		
		// default vlaues
		txtHost.setText("localhost");
		txtName.setText("test");
		txtPort.setText("2000");
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		
		// ActionEvent from "connect" Button
		if(event.getSource() == buttonConnect) {
			String host = txtHost.getText();
			int port = Integer.parseInt(txtPort.getText());
			try {
				// Generate a TCP Client and start it as a Thread
				tcpclient = new TCPClient(new Socket(host, port), txtConsole);
				tcpclient.start();
				tcpclient.send(txtName.getText());
				
				// Update UI
				txtHost.setEnabled(false);
				txtPort.setEnabled(false);
				txtName.setEnabled(false);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung hergestellt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// ActionEvent from "send" Button
		if(event.getSource() == buttonSend) {
			String msg = txtMessage.getText();
			tcpclient.send(msg);
		}
	}
}
