package com.Battleship;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    Socket socket;

    public Client() {
        try{
            socket = new Socket("localhost",9999);
            System.out.println("Connected to the host");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }catch(Exception e){
            System.out.println(e);
        }
    }



    public char[][] getServerBoard() throws IOException, ClassNotFoundException {
        char[][] board = (char[][]) objectInputStream.readObject();
        return board;
    }


    public void sendBoard(char[][] board) throws IOException {
        objectOutputStream.writeObject(board);
    }
    public char[][] getNewBoard() throws IOException, ClassNotFoundException {
        char[][] board = (char[][]) objectInputStream.readObject();
        return board;
    }
    public void closeSocket() throws IOException {
        dataOutputStream.flush();
        dataOutputStream.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client  = new Client();
        Grid player2 = new Grid(10,10);
        Scanner input = new Scanner(System.in);

        while (!player2.winCondition()) {

            player2.printGrid();
            char[][] opponentBoard = client.getServerBoard();

            client.sendBoard(player2.boardForOpponent());
            System.out.println("Waiting for player 1 to shoot");

            player2.setBoard(client.getNewBoard());
            player2.printGrid();

            String param = shoot(input);
            char[][] newOpponentBoard = player2.play(param, opponentBoard);
            //get player2 boards
            //left player2 board, right player 1 board

            client.objectOutputStream.writeObject(newOpponentBoard);
            client.objectOutputStream.flush();

            System.out.println("Waiting for player 1 to shoot...");
        }
        if (player2.winCondition()) {
            System.out.println("you've won the game congrats");
            client.closeSocket();
        }

    }

    public static String shoot(Scanner input){
        System.out.print("shoot a place>");
        return input.next();
    }
}
