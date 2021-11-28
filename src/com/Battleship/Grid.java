package com.Battleship;

import java.util.Random;

public class Grid {
    private final int rows;
    private final int columns;
    private char [][] grid;

    private char[] carrier = {'C','C','C','C','C'}; //length = 5
    private char[] battleship = {'B','B','B','B'}; //length = 4
    private char[] submarine = {'S','S','S'}; //length = 3
    private char[] destroyer = {'D','D'}; //length = 2

//    private boolean carriesDestroyed = false;
//    private boolean battleshipDestroyed = false;
//    private boolean submarineDestroyed = false;
//    private boolean destroyerDestroyed = false;

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

    public char[][] printGrid() {

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

        return grid;
    }

    //place ships randomly
    public void placeShip() {

        int randCol = random.nextInt(columns);
        int randRow = random.nextInt(rows);

        boolean carrierPlaced = false;
        boolean battleshipPlaced = false;
        boolean submarinePlaced = false;
        boolean destroyerPlaced = false;

        int[] index = new int[2];

        //0 - horizontal, 1 - vertical
        int choice;
        for (int iter = 0; iter < 4; iter++) {
            choice = random.nextInt(1+1);
            if (carrierPlaced == false) {
                index = getValidPlacement(0, randRow, randCol, choice);
                if (choice == 0) {
                        for (int i = 0; i < carrier.length; i++)
                            grid[index[0]][index[1] + i] = carrier[0];
                        carrierPlaced = true;
//                    System.out.println("First ship placed horizontal");
                } else {
                        for (int i = 0; i < carrier.length; i++)
                            grid[index[0] + i][index[1]] = carrier[0];
                        carrierPlaced = true;
                    //System.out.println("First ship placed vertical");
                }
            } else if (carrierPlaced == true && battleshipPlaced == false) {
                index = getValidPlacement(1, randRow, randCol, choice);
                if (choice == 0) {
                        for (int i = 0; i < battleship.length; i++)
                            grid[index[0]][index[1] + i] = battleship[i];
                        battleshipPlaced = true;
//                    System.out.println("Second ship placed horizontal");
                } else {
                    for (int i = 0; i < battleship.length; i++)
                        grid[index[0] + i][index[1]] = battleship[i];
                    battleshipPlaced = true;
//                    System.out.println("Second ship placed vertical");
                }
            } else if (battleshipPlaced == true && carrierPlaced == true && submarinePlaced == false) {
                index = getValidPlacement(2, randRow, randCol, choice);
                if (choice == 0) {
                        for (int i = 0; i < submarine.length; i++)
                            grid[index[0]][index[1] + i] = submarine[i];
                        submarinePlaced = true;
//                    System.out.println("Third ship placed horizontal");
                } else {
                    for (int i = 0; i < submarine.length; i++)
                        grid[index[0] + i][index[1]] = submarine[i];
                    submarinePlaced = true;
//                    System.out.println("Third ship placed vertical");
                }
            } else if (carrierPlaced == true && battleshipPlaced == true && submarinePlaced == true && destroyerPlaced == false) {
                index = getValidPlacement(3, randRow, randCol, choice);
                if (choice == 0) {
                        for (int i = 0; i < destroyer.length; i++)
                            grid[index[0]][index[1] + i] = destroyer[i];
                        destroyerPlaced = true;
//                    System.out.println("Fourth ship placed horizontal");
                } else {
                        for (int i = 0; i < destroyer.length; i++)
                            grid[index[0] + i][index[1]] = destroyer[i];
                        destroyerPlaced = true;

//                    System.out.println("Fourth ship placed vertical");
                }
            }
        }
    }



    public int[] getValidPlacement(int type, int randRow, int randCol, int choice) {

       int[] index = new int[2];

       if (choice == 0) {
           switch (type) {
               //carrier
               case 0:
                   while (!(randCol <= (this.columns - this.carrier.length)))
                       randCol = random.nextInt(6);

                   randRow = checkShipPlacementHorizontal(randRow,randCol, "carrier");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 1:
                   //battleship
                   while (!(randCol <= (this.columns - this.battleship.length)))
                       randCol = random.nextInt(6+1);

                   randRow = checkShipPlacementHorizontal(randRow,randCol, "battleship");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 2:
                   //submarine
                   while (!(randCol < (this.columns - this.submarine.length)))
                       randCol = random.nextInt(7+1);

                   randRow = checkShipPlacementHorizontal(randRow,randCol, "submarine");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 3:
                   //destroyer
                   while (!(randCol < (this.columns - this.destroyer.length)))
                       randCol = random.nextInt(8+1);

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
                   while (!(randRow < (this.rows - this.carrier.length)))
                       randRow = random.nextInt(6);


                   randCol = checkShipPlacementVertical(randRow,randCol, "carrier");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 1:
                   //battleship
                   while (!(randRow < (this.rows - this.battleship.length)))
                       randRow = random.nextInt(7);


                   randCol = checkShipPlacementVertical(randRow,randCol, "battleship");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 2:
                   //submarine
                   while (!(randRow < (this.rows - this.submarine.length)))
                       randRow = random.nextInt(8);


                   randCol = checkShipPlacementVertical(randRow,randCol, "submarine");
                   index[0] = randRow;
                   index[1] = randCol;
                   break;
               case 3:
                   //destroyer
                   while (!(randRow < (this.rows - this.destroyer.length)))
                       randRow = random.nextInt(9);


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

    public boolean checkEmptyHorizontal (int row, int col, int shipLength){
        for (int i=col; i<shipLength; i++) {
            if (grid[row][col] != '-')
                return false;
        }
        return true;
    }

    public boolean checkEmptyVertical (int row, int col, int shipLength){
        for (int i=row; i<shipLength; i++) {
            if (grid[row][col] != '-')
                return false;
        }
        return true;
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


    public int checkShipPlacementHorizontal (int row, int col, String shipType) {

        if (shipType.equals("carrier")) {
            for (int i=col; i<= carrier.length; ++i){
                if (checkFilledBattleship(row, i) || checkFilledSubmarine(row, i) || checkFilledDestroyer(row, i)) {
                      row = random.nextInt(rows);
                      i = col;
                }
            }
        } else if (shipType.equals("battleship")) {
            for (int i=col; i<= battleship.length; ++i) {
                if (checkFilledCarrier(row, i) || checkFilledSubmarine(row, i) || checkFilledDestroyer(row, i)) {
                       row = random.nextInt(rows);
                       i = col;
                }
            }
        } else if (shipType.equals("submarine")) {
            for (int i = col; i <= submarine.length; ++i) {
                if (checkFilledCarrier(row, i) || checkFilledBattleship(row, i) || checkFilledDestroyer(row, i)) {
                        row = random.nextInt(rows);
                        i = col;
                }
            }
        } else if (shipType.equals("destroyer")) {
            for (int i = col; i <= destroyer.length; ++i) {
                    if(!checkEmptyHorizontal(row,col, destroyer.length)) {
                        row = random.nextInt(rows);
                        i = col;
                }
            }
        }
        return row;
    }

    public int checkShipPlacementVertical (int row, int col, String shipType) {

        if (shipType.equals("carrier")) {
            for (int i = row; i <= carrier.length; ++i) {
                if (checkFilledBattleship(i, col) == true || checkFilledSubmarine(i, col) == true || checkFilledDestroyer(i, col) == true) {
                        col = random.nextInt(columns);
                        i = row;
                }
            }
        } else if (shipType.equals("battleship")) {
            for (int i = row; i <= battleship.length; ++i) {
                if (checkFilledCarrier(i, col) == true || checkFilledSubmarine(i, col) == true || checkFilledDestroyer(i, col) == true) {
                        col = random.nextInt(columns);
                        i = row;
                }
            }
        } else if (shipType.equals("submarine")) {
            for (int i = row; i <= submarine.length; ++i) {
                if (checkFilledCarrier(i, col) == true || checkFilledBattleship(i, col) == true || checkFilledDestroyer(i, col) == true) {
                        col = random.nextInt(columns);
                        i = row;
                }
            }
        } else if (shipType.equals("destroyer")) {
            for (int i = row; i <= submarine.length; ++i) {
                if (checkFilledCarrier(i, col) == true || checkFilledBattleship(i, col) == true || checkFilledSubmarine(i, col) == true) {
                       col = random.nextInt(columns);
                       i = row;
                }
            }
        }
        return col;
    }

    //print board with hits and misses
    public char[][] boardForOpponent () {
        char[][] opponentBoard = new char[rows][columns];
        for (int i = 0; i<rows; i++){
            for (int j = 0; j< columns; j++){
                opponentBoard[i][j] = grid[i][j];
            }
        }

        for (int i = 0; i<rows; i++ ){
            for (int j = 0; j<columns; j++){
                if (opponentBoard[i][j] == 'C'){
                    opponentBoard[i][j] = '-';
                }
                else if (opponentBoard[i][j] == 'B'){
                    opponentBoard[i][j] = '-';
                }
                else if (opponentBoard[i][j] == 'S'){
                    opponentBoard[i][j] = '-';
                }
                else if (opponentBoard[i][j] == 'D'){
                    opponentBoard[i][j] = '-';
                }
            }
        }

        //printing board
        System.out.println("\nOpportunities to hit the opponent\n");


        String columnNumbers = "  |  ";
        for (int i = 0; i < columns; i++)
            if (i < columns-1)
                columnNumbers += "" + (i+1) + " , ";
            else
                columnNumbers += "" + (i+1) + " |";

        System.out.println(columnNumbers);


        for (int i=0; i < rows; i++) {
            //printing row's index
            System.out.print("" + ((char)(i+'A')) + " |  ");
            for (int j=0; j < columns; j++) {
                if (j == columns - 1)
                    System.out.print(opponentBoard[i][j] + "  |");
                else
                    System.out.print(opponentBoard[i][j] + " , ");
            }
            System.out.println();
        }


        return opponentBoard;
    }

    public char[][] play(String param, char[][] opponentBoard){
        char gridCol = param.charAt(0);
        int gridRow = (int)param.charAt(1) - 1;

        if (gridCol>='A' && gridCol<='J')
            if (gridRow>=0 && gridRow<=9)
                for (int i = 0; i < rows; i++ )
                    for (int j = 0; j < columns; j++)
                        if (opponentBoard[i][j] == 'C' || opponentBoard[i][j] == 'B' || opponentBoard[i][j] == 'S' || opponentBoard[i][j] == 'D')
                            opponentBoard[i][j]='H';
                        else opponentBoard[i][j]='X';
        return opponentBoard;
    }

    public boolean winCondition(){

        for (int i=0; i<rows; i++)
            for (int j=0; j<columns; j++)
                if (grid[i][j]=='C' || grid[i][j]=='B' || grid[i][j]=='S'|| grid[i][j]=='D')
                    return false;
        return true;
    }

    public void setBoard(char[][] newBoard) {
        this.grid = newBoard;
    }
}

