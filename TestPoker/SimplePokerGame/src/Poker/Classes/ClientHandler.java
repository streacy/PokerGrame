/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker.Classes;

import java.net.ServerSocket;
import java.security.PublicKey;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tgreenid
 */
public class ClientHandler extends Thread{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
     long number;
    final Connection mainconnect = Connection.getConnection();
    
    
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos;
        this.number = this.getId();
    }
    
  
    @Override
    public void run()  
    {   Game game = Game.getGame();
        String received; 
        String toreturn; 
        while (true)  
        { 
            try { 
                
                // Ask user what he wants 
                dos.writeUTF("Type Play to start game\n"+  "Type Exit to terminate connection.");
                System.out.println("Thread ID is " + this.getId());
                  
                // receive the answer from client 
                received = dis.readUTF(); 
                
                if(received.equals("Exit")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close();
                    mainconnect.removeConnection(this.getName());
                    System.out.println("Connection closed"); 
                    break; 
                } 
                
                if(received.equals("Play")) 
                {  
                    
                }
                  
                System.out.println(received);
                toreturn = "hello player " + number; 
                dos.writeUTF(toreturn);
                  
                
                
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
    public void sendMessage (String msg) throws IOException{
        dos.writeUTF(msg);
    }
    
    
}
