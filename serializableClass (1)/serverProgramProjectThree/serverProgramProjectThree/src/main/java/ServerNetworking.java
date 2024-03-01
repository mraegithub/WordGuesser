import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class ServerNetworking {
	int portNumber = 0;
	int num_of_clients = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	Server server;
	private Consumer<Serializable> sender;
	
	ServerNetworking(int thePortNumber, Consumer<Serializable> data) {
		portNumber = thePortNumber;
		sender = data;
		server = new Server();
		server.start();
	}
	
	public class Server extends Thread {
		
		public void run() {
			try(ServerSocket serverSocket = new ServerSocket(portNumber);) {
			System.out.println("Server "+portNumber +" Has Started. May the Odds forever be in your favor.");
				while(true) {
					ClientThread client = new ClientThread(serverSocket.accept(), num_of_clients);
					
					sender.accept("CLIENT " + num_of_clients + " HAS CONNECTED");
					clients.add(client);
					client.start();
					
					num_of_clients++;
				}
			}
			catch(Exception e) {
				System.out.println("Socket has failed to launch somehow...");
			}
		}
	}
	
	class ClientThread extends Thread {
			Socket socket;
			int count = 0;
			int rounds = 1;
			boolean logIn = false;
			boolean wordWin = false;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket a, int count) {
				this.socket = a;
				this.count = count;
			}
			
			//sends word length
			public void sendWord(String word) {
				ClientThread a = clients.get(count-1);
				try {
					a.out.writeObject(Integer.toString(word.length())+": the Word Length");
				}
				catch(Exception e) {}
			}
			
			public void sendMessage(String message) {
				ClientThread a = clients.get(count-1);
				try {
					a.out.writeObject(message);
				}
				catch(Exception e) {}
			}
			
			public void run() {
				
				try {
					in = new ObjectInputStream(socket.getInputStream());
					out = new ObjectOutputStream(socket.getOutputStream());
					socket.setTcpNoDelay(true);
				}
				catch(Exception e) {
					System.out.println("Stream did not open");
				}
				
				sendMessage("new client on server: "+ count);
				//login gate
				/**while(!logIn) {
					try {
						int portNum = Integer.parseInt(in.readObject().toString());
						if (portNum == portNumber) {
							logIn = true;
							System.out.println("Successful login!");
						} else {
							System.out.println("Incorrect port number");
						}
					}
					catch(Exception e) {
						System.out.println("Value is incorrect");
					}
				}
				**/
				while (true) {
					//placeholder till needs to be adjusted
					try {
						//basically just takes in input for whatever is needed here
						String message = in.readObject().toString();
						sender.accept(message);
						sendWord(message);
					}
					catch(Exception e) {
						break;
					}
				}
			}
	}

	public void setPortNumber(int a) {
		portNumber = a;
		
	}
}
