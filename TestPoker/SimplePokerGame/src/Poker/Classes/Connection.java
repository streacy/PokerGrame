/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker.Classes;


import java.util.Arrays;
import java.util.Scanner;

import java.net.ServerSocket;
import java.security.PublicKey;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author Brian
 */
public class Connection {
    private ServerSocket serverSocket;
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    private int count=-1;
    private ArrayList<Thread> playerList = new ArrayList<Thread>();
    private static Connection connect = null;
    
    
      private Connection() {
     
    }
    
    public static Connection getConnection(){
        
     if(connect == null){
         
         connect = new Connection();
     }
     
     return connect;
    } 
    
    public ArrayList<Thread> getConnectionList(){
      
     return playerList;
    } 
    
    
     public void start(int port) throws IOException {
        
        serverSocket = new ServerSocket(port);
        
      
       
                
        while(true){
            Socket s = null;
            try{
                if (playerList.size() == 5) {
                    //System.out.println("Too many players playing");
                    continue;
                }
                
                s = serverSocket.accept();
                count++;
                System.out.println("Client " + count +" is connected");
               
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                
                System.out.println("Assigning  a new thread to the client");
                
                //create new thread for client
                Thread t = new ClientHandler(s,dis,dos);

                
                playerList.add(t);
                t.join();
                t.start();
               
               
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
       
        }
    }
 
    
    public void stop() throws IOException {
        in.close();
        out.close();
        s.close();
        serverSocket.close();
    }
    
    
    public void removeConnection(String s ){
        Thread temp;
        for (int counter = 0; counter < playerList.size(); counter++) { 
            temp = playerList.get(counter);
            if((temp.getName()).equals(s)){
                playerList.remove(counter);
                System.out.println("Connection with thread name "+temp.getName()+ " removed.");
            }
            
        }
    }
    
    
    
    public static void main(String[] args) throws IOException {
        Connection server= Connection.getConnection();
        server.start(6789);
    }
    
}
