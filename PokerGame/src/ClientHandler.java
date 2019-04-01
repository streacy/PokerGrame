/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author stephanietreacy
 */
import java.net.ServerSocket;
import java.security.PublicKey;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class ClientHandler extends Thread{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    final int number;
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, int number)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
        this.number = number;
    }
    
    @Override
    public void run()  
    { 
        String received; 
        String toreturn; 
        while (true)  
        { 
            try { 
  
                // Ask user what he wants 
                //dos.writeUTF("Client move\n"+ 
                         //   "Type Exit to terminate connection."); 
                  
                // receive the answer from client 
                //received = dis.readUTF(); 
                
                //System.out.println(received);
                toreturn = "hello player " + number; 
                 dos.writeUTF(toreturn);
                  
                if(toreturn.equals("Exit")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                
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
