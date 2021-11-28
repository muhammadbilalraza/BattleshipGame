package com.Battleship;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try{
            Scanner input = new Scanner(System.in);
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket socket;

            Grid player1 = new Grid(10,10);
            System.out.println("Waiting for client...");
            socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());


            System.out.println("Client has been connected");

            while (!player1.winCondition()) {
                //sending board to client
                objectOutputStream.writeObject(player1.boardForOpponent());
                objectOutputStream.flush();

                player1.printGrid();
                //Reading the board from client
                char[][] opponentBoard = (char[][]) objectInputStream.readObject();

                //Taking the first shot
                String param = turnInput(input);
                char[][] newOpponentBoard = player1.play(param, opponentBoard);

                //Sending new opponent board to the client
                objectOutputStream.writeObject(newOpponentBoard);
                objectOutputStream.flush();

                System.out.println("Waiting for player 2 to shoot...");

                player1.setBoard((char[][]) objectInputStream.readObject());
                player1.printGrid();


            }
            if (player1.winCondition()) {
                System.out.println("you've won the game congrats");
                serverSocket.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static String turnInput(Scanner input){
        System.out.print("Enter coordinates to shoot: ");
        return input.next();
    }

}
