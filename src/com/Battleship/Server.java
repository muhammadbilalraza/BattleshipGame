//package com.Battleship;
//
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server {
//
//    private int port = 9999;
//
//    ServerSocket serverSocket;
//    Socket socket;
//
//    DataInputStream dataInputStream;
//    DataOutputStream dataOutputStream;
//    ObjectInputStream objectInputStream;
//    ObjectOutputStream objectOutputStream;
//
//    public Server(int port) {
//        try {
//            serverSocket = new ServerSocket(port);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void main(String args[]) throws IOException {
//
//
//
//        Socket socket = serverSocket.accept();
//    }
//}
