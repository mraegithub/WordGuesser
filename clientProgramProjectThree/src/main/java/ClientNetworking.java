import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientNetworking extends Thread {
  int serverPort;
  int numWords;
  String switched = "";
  Socket socketClient;
  ObjectOutputStream out;
  ObjectInputStream in;

  public Consumer<Serializable> sender;

  ClientNetworking(int thePortNumber, Consumer<Serializable> data) {
    serverPort = thePortNumber;
    sender = data;
  }

  public void run() {
    try {
      socketClient = new Socket("127.0.0.1", serverPort);
      out = new ObjectOutputStream(socketClient.getOutputStream());
      in = new ObjectInputStream(socketClient.getInputStream());
      socketClient.setTcpNoDelay(true);
    } catch (Exception e) {
      System.out.println("Port could not be found...");
    }

    while (true) {
      try {
        SerializedClass a = (SerializedClass) in.readObject();
       // String message = a.getMessage();
       // sender.accept(message);
        sender.accept(a);

      } catch (Exception e) {
      }
    }
  }

  public void sendLetter(char data) {
    try {
      String newString = data + "";
      out.writeObject(newString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // just in case emergency use string
  public void sentBasicString(String data) {
    try {
      out.writeObject(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendPortNum(int num) {
    try {
      String newString = num + "";
      out.writeObject(newString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendSerializedClass(SerializedClass a) {
    try {
      SerializedClass b = a;
      out.writeObject(b);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
