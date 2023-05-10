package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	private Socket socket;
	
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
		outputServer.close();
		inputServer.close();
		socket.close();	
	}
	
	public void connect(int conn_timeout) throws IOException {
		socket.connect(new InetSocketAddress(this.address, this.port), conn_timeout);

		// used to read message from the server
		inputServer = new DataInputStream(socket.getInputStream()); 
		// used to write messages at the server
		outputServer = new DataOutputStream(socket.getOutputStream());
	}
	
	public void send(String msg) throws IOException {
		outputServer.writeUTF(msg);
	}
	
	public String read() throws IOException {
		return inputServer.readUTF();
	}
}
