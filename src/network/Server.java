package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private Socket socket;
	private ServerSocket server;
	
	// read the message from the client
	private DataInputStream inputClient;

	// send the message to the client socket
	private DataOutputStream outputClient;
	
	public Server(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip);
		
		server = new ServerSocket(port, 50, addr); 
	} 
	
	public void shutdown() throws IOException {	
		inputClient.close();
		outputClient.close();
		socket.close();
		server.close();
	}
	
	public void accept(int timeout) throws IOException {
		server.setSoTimeout(timeout);
			
		socket = server.accept(); 
			
		// takes input from the client socket 
		inputClient = new DataInputStream(socket.getInputStream()); 
		// send messages to the client socket
		outputClient = new DataOutputStream(socket.getOutputStream());
	}	
	
	public void send(String msg) throws IOException {
		outputClient.writeUTF(msg);
	}
	
	public String read() throws IOException {
		return inputClient.readUTF();
	}
}
