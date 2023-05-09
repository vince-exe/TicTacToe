package network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
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
	
	// read the message from the System.in
	private BufferedReader inputKeyBoard;
	
	// send the message to the client socket
	private DataOutputStream outputClient;
	
	public Server(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip);
		
		server = new ServerSocket(port, 50, addr); 
	} 
	
	public boolean shutdown() {
		try {
			inputClient.close();
			inputKeyBoard.close();
			outputClient.close();
			
			server.close();
			socket.close();
			
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean accept(int timeout) throws IOException {
		server.setSoTimeout(timeout);
			
		socket = server.accept(); 
			
		// takes input from the client socket 
		inputClient = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
			
		return true;
	}	
}
