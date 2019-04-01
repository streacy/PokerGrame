/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pokergame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.*;
import java.io.*;

/**
 *
 * @author stephanietreacy
 */
public class Player  {
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    
    public Player (InetAddress ip, int port) throws IOException{
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
 
    public void stopConnection() throws IOException {
        dis.close();
        dos.close();
        s.close();
    }
    
    public static void main(String[] args) throws IOException{
        InetAddress ip = InetAddress.getByName("localhost"); 
        Player client = new Player(ip,6789);
        String received;
        while(true){
            client.sendMessage();
            continue;
        }
        //String response = client.sendMessage("hello server");
        //System.out.println(response);
    
    }
}

