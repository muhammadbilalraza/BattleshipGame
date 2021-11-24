package com.Battleship;

public class Main {

    public static void main(String[] args) {

        Grid grid = new Grid(10,10);
        grid.printGrid();
        grid.placeShip();
        grid.printGrid();

    }
}
