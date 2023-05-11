package network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
		
		inputServer = null;
		outputServer = null;
	}
	
	public String shutdown()  {
		try {
			if(inputServer != null) {
				inputServer.close();
			}
			if(outputServer != null) {
				outputServer.close();
			}
			if(socket != null) {
				socket.close();
			}
			
			return "ok";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public void connect(int conn_timeout) throws IOException, SocketTimeoutException {
		socket.connect(new InetSocketAddress(this.address, this.port), conn_timeout);

		inputServer = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
		outputServer = new DataOutputStream(socket.getOutputStream()); 
	}
	
	public void send(String msg) throws IOException {
		outputServer.writeUTF(msg);
		outputServer.flush();
	}
	
	public String read() throws IOException {
		return inputServer.readUTF();
	}
}
