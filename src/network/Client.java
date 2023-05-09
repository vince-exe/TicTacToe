package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

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
		
		this.socket = new Socket();
	}
	
	public void shutdown() throws IOException {
		inputKeyBoard.close();
		outputServer.close();
		inputServer.close();
		socket.close();	
	}
	
	public void close() throws IOException {
		inputKeyBoard.close();
		outputServer.close();
		socket.close();
	}
	
	public void connect(int conn_timeout) throws IOException {
		socket.connect(new InetSocketAddress(this.address, this.port), conn_timeout);
		
		// we 'ready' the input reader 
		inputKeyBoard = new BufferedReader(new InputStreamReader(System.in));

		// and the output that is connected to the Socket
		outputServer = new DataOutputStream(socket.getOutputStream()); 
	}
}
