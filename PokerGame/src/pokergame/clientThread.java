package pokergame;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

class clientThread extends Thread {

  private String playerName;
  private final Card[] cards = new Card[5];
  private DataInputStream input = null;
  private PrintStream output = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private final int maxNumPlayers;
  private int bet = 0;
  private int wallet = 0;
  private boolean fold = false;
  private static int mostPoints = 0;
  private final House house = new House();
  static PrivateKey privateKey = null;
  static PublicKey publicKey = null;
  private static int threadId;
  static RSA oAuth = new RSA();
  

  public clientThread(Socket clientSocket, clientThread[] threads, PublicKey publicKey, PrivateKey privateKey, int threadId) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxNumPlayers = threads.length;
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.threadId = threadId;
  }

  public void run() {
    String line;
    try {
      input = new DataInputStream(clientSocket.getInputStream());
      output = new PrintStream(clientSocket.getOutputStream());
      output.println("Enter your name.");
      String name = input.readLine();
      house.setSessionKey(name, threadId);
       String encrypt = oAuth.encrypt(name,publicKey);
       String decrypt = oAuth.decrypt(encrypt,privateKey);
      playerName = decrypt;
      System.out.println("*** A new player " + playerName + " has been Authenticated !!! ***");
      output.println("Welcome " + playerName + " to our Poker Game, There is a $10 Enterance Fee");
      output.println("How much money would you like to start with?");
      String money = input.readLine();
      wallet += Integer.parseInt(money);
      wallet -= 10;
      house.setPot(10);
      output.println("pot is $" + house.getPot());
      output.println("Your cards are: ");
      for (int k = 0; k < 5; k++) {
        cards[k] = house.getCard();
        output.println(cards[k].toString());
      }

      while (true) {
//        if (threads[maxNumPlayers-1].wallet > 0){
        threads[house.getTurn()].output.println(" Please enter 1 to check, 2 to bet or 3 to fold");
        encrypt = oAuth.encrypt(input.readLine(),publicKey);
        decrypt = oAuth.decrypt(encrypt,privateKey);
        line = decrypt;
        for (int i = 0; i < maxNumPlayers; i++) {
          if (threads[i] != null && threads[i].fold != true && house.getTurn() == i && threads[i] == this) {
            if (line.startsWith("1")) {
              threads[i].output.println("You have checked");
              house.setCheck();
        synchronized (this) {
            for (int k = 0; k < maxNumPlayers; k++) {
              if (threads[k] != null && k != i) {
                threads[k].output.println(threads[i].playerName + " has checked");
              }
            }
          }
            } else if (line.startsWith("2")) {
              threads[i].output.println("How much would you like to bet?");
              line = input.readLine();
              while (Integer.parseInt(line) > threads[i].wallet){
                  output.println("You only have $" + threads[i].wallet + " in your wallet, Please bet again");
                  line = input.readLine();
              }
              threads[i].bet += Integer.parseInt(line);
              threads[i].wallet -= Integer.parseInt(line);
              house.setPot(Integer.parseInt(line));
              output.println("The Pot is now " + house.getPot());
              output.println("Your wallet is now " + threads[i].wallet);
          synchronized (this) {
            for (int k = 0; k < maxNumPlayers; k++) {
              if (threads[k] != null && k != i) {
                threads[k].output.println(threads[i].playerName + " has bet $" + line);
                threads[k].output.println("The Pot is now " + house.getPot());
              }
            }
          }
            } else if (line.startsWith("3")) {
              threads[i].output.println("You have folded");
              threads[i].fold = true;
              house.setFolded();
         synchronized (this) {
            for (int k = 0; k < maxNumPlayers; k++) {
              if (threads[k] != null && k != i) {
                threads[k].output.println(threads[i].playerName + " has folded");
              }
            }
          }
            }
            house.setTurn();
          }
        }
        if (house.getFolded() == maxNumPlayers-1){
                for(int j =0;j<maxNumPlayers; j ++){
                     if (threads[j].fold != true){
                         house.setWinner(j);
                     }
                }
        }
        
        if (house.getCheck() == maxNumPlayers || house.getFolded() == maxNumPlayers-1) {
          for (int i = 0; i < maxNumPlayers; i++) {
              int points = house.getHandValue(threads[i].cards);
              if (points > mostPoints && house.getFolded() != maxNumPlayers-1){
                  mostPoints = points;
                  house.setWinner(i);
              }
          }
              for (int k = 0; k < maxNumPlayers; k++) {
            if (threads[k] != null) {                
              threads[k].output.println("Winner of the game is " + house.getWinner().playerName + " and they win $" + house.getPot());
              threads[k].output.println("Game Over!");
            }
              }
          
          break;
        }
//      }
      }
    

      synchronized (this) {
        for (int i = 0; i < maxNumPlayers; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }

      input.close();
      output.close();
      clientSocket.close();
    } catch (IOException e) {
    } catch (Exception ex) {
          Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}
