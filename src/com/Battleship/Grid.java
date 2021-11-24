package com.Battleship;

import java.util.Random;

public class Grid {

    private final int rows;
    private final int columns;
    private final char [][] grid;

    private char[] carrier = {'C','C','C','C','C'}; //length = 5
    private char[] battleship = {'B','B','B','B'}; //length = 4
    private char[] submarine = {'S','S','S'}; //length = 3
    private char[] destroyer = {'D','D'}; //length = 2

    private boolean carriesDestroyed = false;
    private boolean battleshipDestroyed = false;
    private boolean submarineDestroyed = false;
    private boolean destroyerDestroyed = false;

    private Random random = new Random();

    //parameterized constructor
    public Grid (int rows, int columns) {

        this.rows = rows;
        this.columns = columns;

        grid = new char[rows][columns];

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                grid [i][j] = '-';
            }
        }
    }

    public void printGrid() {

        String columnNumbers = "  |  ";
        for (int i = 0; i < columns; i++)
            if (i < columns-1)
                columnNumbers += "" + (i+1) + " , ";
            else
                columnNumbers += "" + (i+1) + " |";

        System.out.println(columnNumbers);

        //printing grid
        for (int i=0; i < rows; i++) {
            //printing row's index
            System.out.print("" + ((char)(i+'A')) + " |  ");
            for (int j=0; j < columns; j++) {
                if (j == columns - 1)
                    System.out.print(grid[i][j] + "  |");
                else
                    System.out.print(grid[i][j] + " , ");
            }
            System.out.println();
        }

    }

    //place ships randomly
    public void placeShip() {


        //generate random number between 0-9
//        Random random = new Random();
        int randCol = random.nextInt(10);
        int randRow = random.nextInt(10);
        //0 - horizontal, 1 - vertical
        int choice;

        boolean carrierPlaced = false;
        boolean battleshipPlaced = false;
        boolean submarinePlaced = false;
        boolean destroyerPlaced = false;

        int [] index = new int[2];

        do {
            choice = random.nextInt(2);
            if (carrierPlaced == false) {
                index = getPlacement(0, randRow, randCol, choice);
                if (choice == 0){
                    for (int i=0 ; i < carrier.length; i++){
                        grid[index[0]][index[1]+i] = carrier[i];
                    }
                    carrierPlaced = true;
                }
                else {
                    for (int i=0 ; i < carrier.length; i++){
                        grid[index[0]+i][index[1]] = carrier[i];
                    }
                    carrierPlaced = true;
                }
            } else if (battleshipPlaced == false) {
                index = getPlacement(1, randRow, randCol, choice);
                if (choice == 0){
                    for (int i=0 ; i < battleship.length; i++){
                        grid[index[0]][index[1]+i] = battleship[i];
                    }
                    battleshipPlaced = true;
                }
                else {
                    for (int i=0 ; i < battleship.length; i++){
                        grid[index[0]+i][index[1]] = battleship[i];
                    }
                    battleshipPlaced = true;
                }
            } else if (submarinePlaced == false) {
                index = getPlacement(2, randRow, randCol, choice);
                if (choice == 0){
                    for (int i=0 ; i < submarine.length; i++){
                        grid[index[0]][index[1]+i] = submarine[i];
                    }
                    submarinePlaced = true;
                }
                else {
                    for (int i=0 ; i < submarine.length; i++){
                        grid[index[0]+i][index[1]] = submarine[i];
                    }
                    submarinePlaced = true;
                }
            } else if (destroyerPlaced == false) {
                index = getPlacement(3, randRow, randCol, choice);
                if (choice == 0){
                    for (int i=0 ; i < destroyer.length; i++){
                        grid[index[0]][index[1]+i] = destroyer[i];
                    }
                    destroyerPlaced = true;
                }
                else {
                    for (int i=0 ; i < destroyer.length; i++){
                        grid[index[0]+i][index[1]] = destroyer[i];
                    }
                    destroyerPlaced = true;
                }
            }
        } while (carrierPlaced == false && battleshipPlaced == false && submarinePlaced == false && destroyerDestroyed == false);

    }

    public int[] getPlacement(int type, int randRow, int randCol, int choice) {

       int [] index = new int[2];

        // 0 = horizontal, row fixed, column changed
       if (choice == 0) {
           switch (type) {
               //carrier
               case 0:
                   while (!(randCol < (this.columns - this.carrier.length))) {
                       randCol = random.nextInt(6);
                   }
                   randRow = checkShipPlacementHorizontal(randRow,randCol, "carrier");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 1:
                   //battleship
                   while (!(randCol < (this.columns - this.battleship.length))) {
                       randCol = random.nextInt(7);
                   }
                   randRow = checkShipPlacementHorizontal(randRow,randCol, "battleship");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 2:
                   //submarine
                   while (!(randCol < (this.columns - this.submarine.length))) {
                       randCol = random.nextInt(8);
                   }
                   randRow = checkShipPlacementHorizontal(randRow,randCol, "submarine");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 3:
                   //destroyer
                   while (!(randCol < (this.columns - this.destroyer.length))) {
                       randCol = random.nextInt(9);
                   }
                   randRow = checkShipPlacementHorizontal(randRow,randCol, "destroyer");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               default:
                   throw new IllegalStateException("Unexpected value: " + type);
           }
       }
       else {
           switch (type) {
               case 0:
                   //carrier
                   while (!(randRow < (this.rows - this.carrier.length))) {
                       randRow = random.nextInt(6);
                   }
                   randCol = checkShipPlacementVertical(randRow,randCol, "carrier");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 1:
                   //battleship
                   while (!(randRow < (this.rows - this.battleship.length))) {
                       randRow = random.nextInt(7);
                   }
                   randCol = checkShipPlacementVertical(randRow,randCol, "battleship");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 2:
                   //submarine
                   while (!(randRow < (this.rows - this.submarine.length))) {
                       randRow = random.nextInt(8);
                   }
                   randCol = checkShipPlacementVertical(randRow,randCol, "submarine");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 3:
                   //destroyer
                   while (!(randRow < (this.rows - this.destroyer.length))) {
                       randRow = random.nextInt(9);
                   }
                   randCol = checkShipPlacementVertical(randRow,randCol, "destroyer");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               default:
                   throw new IllegalStateException("Unexpected value: " + type);
           }
       }
        return index;
    }

    //functions to check if the ith, jth position on grid is filled
    public boolean checkFilledCarrier (int row, int col) {

        if (grid[row][col] == carrier[0])
            return true;
        return  false;
    }
    public boolean checkFilledBattleship (int row, int col) {

        if (grid[row][col] == battleship[0])
            return true;
        return  false;
    }
    public boolean checkFilledSubmarine (int row, int col) {

        if (grid[row][col] == submarine[0])
            return true;
        return  false;
    }
    public boolean checkFilledDestroyer (int row, int col) {

        if (grid[row][col] == destroyer[0])
            return true;
        return  false;
    }

    //function to check if at the horizontal position a ship is already placed
    public int checkShipPlacementHorizontal (int row, int col, String shipType) {

        if (shipType.equals("carrier")) {
            if (checkFilledBattleship(row, col) == true && checkFilledSubmarine(row, col) == true && checkFilledDestroyer(row, col) == true)
                checkShipPlacementHorizontal(random.nextInt(rows), col, "carrier");
        } else if (shipType.equals("battleship")) {
            if (checkFilledCarrier(row, col) == true && checkFilledSubmarine(row, col) == true && checkFilledDestroyer(row, col) == true)
                checkShipPlacementHorizontal(random.nextInt(rows), col, "battleship");
        } else if (shipType.equals("submarine")) {
            if (checkFilledCarrier(row, col) == true && checkFilledBattleship(row, col) == true && checkFilledDestroyer(row, col) == true)
                checkShipPlacementHorizontal(random.nextInt(rows), col, "submarine");
        } else if (shipType.equals("destroyer")) {
            if (checkFilledCarrier(row, col) == true && checkFilledBattleship(row, col) == true && checkFilledSubmarine(row, col) == true)
                checkShipPlacementHorizontal(random.nextInt(rows), col, "submarine");
        }
        return row;
    }


    //function to check if at the vertical position a ship is already placed
    public int checkShipPlacementVertical (int row, int col, String shipType) {

        if (shipType.equals("carrier")) {
            if (checkFilledBattleship(row, col) == true && checkFilledSubmarine(row, col) == true && checkFilledDestroyer(row, col) == true)
                checkShipPlacementVertical(row, random.nextInt(columns), "carrier");
        } else if (shipType.equals("battleship")) {
            if (checkFilledCarrier(row, col) == true && checkFilledSubmarine(row, col) == true && checkFilledDestroyer(row, col) == true)
                checkShipPlacementVertical(row, random.nextInt(columns), "battleship");
        } else if (shipType.equals("submarine")) {
            if (checkFilledCarrier(row, col) == true && checkFilledBattleship(row, col) == true && checkFilledDestroyer(row, col) == true)
                checkShipPlacementVertical(row, random.nextInt(columns), "submarine");
        } else if (shipType.equals("destroyer")) {
            if (checkFilledCarrier(row, col) == true && checkFilledBattleship(row, col) == true && checkFilledSubmarine(row, col) == true)
                checkShipPlacementVertical(row, random.nextInt(columns), "submarine");
        }
        return col;
    }
} //class end

