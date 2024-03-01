import java.io.IOException;
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

  ServerNetworking(int thePortNumber, Consumer<Serializable> data) { // server networking constructor
    portNumber = thePortNumber;
    sender = data;
    server = new Server();
    server.start();
  }

  public class Server extends Thread {

    public void run() {  // creates serversocket
      try(ServerSocket serverSocket = new ServerSocket(portNumber);) {
      System.out.println("Server "+portNumber +" Has Started. May the Odds forever be in your favor.");
        while(true) {
          ClientThread client = new ClientThread(serverSocket.accept(), num_of_clients);

          sender.accept("CLIENT " + num_of_clients + " HAS CONNECTED");
          clients.add(client);
          client.start(); // displays newly connected client to the server

          num_of_clients++;
        }
      }
      catch(Exception e) {
        System.out.println("Socket has failed to launch somehow...");
      }
    }
  }

  class ClientThread extends Thread { // clientthread class
    GameLogic gameHandler = new GameLogic();
      Socket socket;
      int count = 0;
      int rounds = 1;
      boolean logIn = false;
      boolean wordWin = false;
      ObjectInputStream in;
      ObjectOutputStream out;

      ClientThread(Socket a, int count) { //clintthread constructor
        this.socket = a;
        this.count = count;
      }

      public void sendSerializedClass(SerializedClass a) { // sends serializedClass
        try {
          SerializedClass b = a;
          out.writeObject(b);

        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      public void run() { // creates input and output streams

        try {
          in = new ObjectInputStream(socket.getInputStream());
          out = new ObjectOutputStream(socket.getOutputStream());
          socket.setTcpNoDelay(true);
        }
        catch(Exception e) {
          System.out.println("Stream did not open");
        }

        SerializedClass clientInfo = new SerializedClass();
        clientInfo.message = "new client on server: "+ count;
        sendSerializedClass(clientInfo); //tells client they are connected
        while (true) {
          try {	
            int gamePhase = 1; // Initial category pick. Server and client both receive picked category
              SerializedClass chosenCategory = (SerializedClass)in.readObject();//Server listens for client category pick
              SerializedClass msg1 = new SerializedClass();  // serializedclass objects to send to client 
              SerializedClass msg2 = new SerializedClass();

              String message = "Client " +count+ " chose category "+chosenCategory.getCategory();
              sender.accept(message);
              String clientMessage = "You chose category " +chosenCategory.getCategory();

              msg1.message = ""; 
              msg1.setMessage(clientMessage); 
              sendSerializedClass(msg1); // tells client what they picked

              gameHandler.startRound(chosenCategory.getCategory()); // starts round with client pick
              msg2.setMessage(Integer.toString(gameHandler.catHandler.currentWord.length())+": the Word Length");
              sendSerializedClass(msg2); // sends word length to client
              sender.accept("Client " +count+"'s word is: " +gameHandler.catHandler.currentWord);
              gamePhase = 2;

              while (gamePhase == 2) { // main game phase
                if (gameHandler.passive_CheckRound() == false) { // checks if client can keep playing. client cannot play
                  SerializedClass switchCategories = new SerializedClass();			
                  switchCategories.message = "Game over!";
                  sendSerializedClass(switchCategories); // game over flag. kicks client out of game
                  gamePhase = 3;
                if (gameHandler.cat1win == true && gameHandler.c1winAlready == false) { // if they won a category
                  gameHandler.c1winAlready = true;
                  SerializedClass chosenCat = new SerializedClass();
                  chosenCat.catOneFinish = (Boolean)true;
                  sendSerializedClass(chosenCat); // send category they won to clientgui

                } else if (gameHandler.cat2win == true && gameHandler.c2winAlready == false) {
                  gameHandler.c2winAlready = true;
                  SerializedClass chosenCat = new SerializedClass();
                  chosenCat.catTwoFinish = (Boolean)true;
                  sendSerializedClass(chosenCat);

                }  else if (gameHandler.cat3win == true && gameHandler.c3winAlready == false) {
                  gameHandler.c3winAlready = true;
                  SerializedClass chosenCat = new SerializedClass();
                  chosenCat.catThreeFinish = (Boolean)true;
                  sendSerializedClass(chosenCat);

                }else if(gameHandler.c1Tries == 3 || gameHandler.c2Tries == 3 || gameHandler.c3Tries == 3) { // checks if they lost the game
                  SerializedClass youLose = new SerializedClass();
                  youLose.haveTheyLost = (Boolean)true;
                  sendSerializedClass(youLose); // tells client they lost the game
                }
                SerializedClass sendGameOver = new SerializedClass(); // Tells client the round is over
                SerializedClass chooseNewCat = new SerializedClass(); // Asks them to pick another category
                sendGameOver.message = "Game Over!";
                sendSerializedClass(sendGameOver);
                chooseNewCat.message = "Choose a new category by selecting Play Again!";
                sendSerializedClass(chooseNewCat);

                gamePhase = 1;

                } else if (gameHandler.passive_CheckRound() == true) { // client can keep playing
                  SerializedClass clientChar = (SerializedClass)in.readObject();

                  if (gameHandler.takeGuess(clientChar.getCharacter()) == true) { //If client guesses a correct letter
                    sender.accept("Client correctly guessed: "+ clientChar.getCharacter()); // tell client what letter they guessed
                    SerializedClass correctGuess = new SerializedClass();
                    SerializedClass wordStatus = new SerializedClass();
                    correctGuess.message = "You guessed correctly!"; 
                    sendSerializedClass(correctGuess);// Tell them they guessed right
                    wordStatus.message="Word status: "+gameHandler.starVersion;
                    sendSerializedClass(wordStatus); // tell them new status on word
                  } else { // if client guesses a wrong letter
                    sender.accept("Client "+count+" incorrectly guessed: "+ clientChar.getCharacter()); // tell client what they incorectly guess
                    SerializedClass incorrectGuess = new SerializedClass();
                    SerializedClass wordStatus = new SerializedClass();
                    SerializedClass guessesRemaining = new SerializedClass();
                    incorrectGuess.message = "You guessed incorrectly!"; // tell client they guessed wrong
                    sendSerializedClass(incorrectGuess);
                    wordStatus.message = "Word status: "+gameHandler.starVersion; // tell client status on word
                    sendSerializedClass(wordStatus);

                    if (chosenCategory.getCategory() == 1) { // checks which client is selected
                      guessesRemaining.message = "You have " + (6-gameHandler.cat1Guesses) + " remaining";  // tells client how many guesses left
                      sendSerializedClass(guessesRemaining);
                    } else if (chosenCategory.getCategory() == 2) {
                      guessesRemaining.message = "You have " + (6-gameHandler.cat2Guesses) + " remaining";
                      sendSerializedClass(guessesRemaining);
                    } else if (chosenCategory.getCategory() == 3) {
                      guessesRemaining.message = "You have " + (6-gameHandler.cat3Guesses) + " remaining";
                      sendSerializedClass(guessesRemaining);
                    }
                  }
                } 
              }

          }
          catch(Exception e) {
            break;
          }
        }
      }
  }


}
