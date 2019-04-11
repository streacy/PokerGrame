/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker.Classes;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Brian
 */
public class Client {
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    
    
    
    public Client (InetAddress ip, int port) throws IOException{
        this.s = new Socket(ip,port);
        dis= new DataInputStream(s.getInputStream()); 
        dos=new DataOutputStream(s.getOutputStream());
        
    }
    
    public void startConnection(InetAddress ip, int port) throws IOException {
        s = new Socket(ip, port);

    }
 
    public void sendMessage() throws IOException {
        String received;
        received = dis.readUTF();
        System.out.println(received);
        /*dos.writeUTF(msg);
        String resp = dis.readUTF();
        return resp;*/
    }
    
    public void writeMessage(String message) throws IOException {
        String received;
        dos.writeUTF(message);
        
        /*dos.writeUTF(msg);
        String resp = dis.readUTF();
        return resp;*/
    }
 
    public void stopConnection() throws IOException {
        dis.close();
        dos.close();
        s.close();
    }
    
    @SuppressWarnings("NotifyNotInSynchronizedContext")
    public static void main(String[] args) throws IOException{
        InetAddress ip = InetAddress.getByName("localhost"); 
        Scanner scn = new Scanner(System.in); 
        Client playerClient = new Client(ip,6789);
        Game game = Game.getGame();
        Connection connect = Connection.getConnection();
        ArrayList<Thread> playlist = new ArrayList<Thread>();
        
        playlist = connect.getConnectionList();
        
        int out =0;
        String received;
        while(true){
            playerClient.sendMessage();
            String tosend = scn.nextLine();
            playerClient.writeMessage(tosend);
            if(tosend.equals("Exit")) 
                {  
                    playerClient.stopConnection();
                    System.out.println("Connection closed"); 
                    break; 
                }
            if(tosend.equals("Play")) 
                {  
                    System.out.println("Starting Game"); 
//                    game.play();
//                    playlist.notify();
                    
                }
            
          
           
        }

    
    }
}
