package com.Battleship;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    DataInputStream dataInputStream;
    ObjectOutputStream objectOutputStream;
    DataOutputStream dataOutputStream;

    public Server() {
    }

    public static void main(String[] args) {
        try{
            Scanner input = new Scanner(System.in);
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket socket;

            Grid host = new Grid(10,10);
            System.out.println("Waiting for client...");
            socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());


//            dataOutputStream.writeUTF(host.getUsername());
//            dataOutputStream.flush();
//            String opponentName = (String) dataInputStream.readUTF();
//            host.setOpponentName(opponentName);

}
