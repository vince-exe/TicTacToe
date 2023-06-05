package network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
		socket = null;
		
		inputClient = null; 
		outputClient = null; 
	} 
	
	public String shutdown() {	
		try {
			if(inputClient != null) {
				inputClient.close();
			}
			if(outputClient != null) {
				outputClient.close();
			}
			if(socket != null) {
				socket.close();
			}
			if(server != null) {
				server.close();
			}
			
			return "ok";
		}
		catch(Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public void accept(int timeout) throws IOException, SocketTimeoutException {
		server.setSoTimeout(timeout);
		
		socket = server.accept(); 
		
		inputClient = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
		outputClient = new DataOutputStream(socket.getOutputStream());
	}	
	
	public void send(String msg) throws IOException {
		outputClient.writeUTF(msg);
		outputClient.flush();
	}
	
	public void sendByte(int b) throws IOException {
		outputClient.writeByte(b);
		outputClient.flush();
	}
	
	public Byte readByte() throws IOException {
		return inputClient.readByte();
	}
	
	public String read() throws IOException {
		return inputClient.readUTF();	
	}
	
	public boolean isClosed() {
		return this.socket.isClosed();
	}
	
	public DataOutputStream getDataOutputStream() {
		return this.outputClient;
	}
	
	public DataInputStream getDataInputStream() {
		return this.inputClient;
	}
	
	public void openDataInputStream() throws IOException {
		inputClient = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
	}
	
	public void opendataOutputStream() throws IOException {
		outputClient = new DataOutputStream(socket.getOutputStream());
	}
	
	public String closeServer() {
		try {
			this.server.close();
			return "ok";
			
		} 
		catch (IOException e) {
			return e.getMessage();
		}
	}
}
