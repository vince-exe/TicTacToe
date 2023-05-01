package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	
	// take the data from the System.in
	private BufferedReader inputKeyBoard;
	
	// take the data from the server
	private DataInputStream inputServer;
	
	// send the message to the server
	private DataOutputStream outputServer;
	
	private String address;
	private int port;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public boolean shutdown() {
		try {
			inputKeyBoard.close();
			inputServer.close();
			outputServer.close();
			socket.close();
			
			return true;

		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean connect() {
		try {
			socket = new Socket(address, port);
			
			// we 'ready' the input reader 
			inputKeyBoard = new BufferedReader(new InputStreamReader(System.in));

			// and the output that is connected to the Socket
			outputServer = new DataOutputStream(socket.getOutputStream()); 
			
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
