package com.coen6761.Robot;

public class Robot {
    private int floorDim;
    private int row;
    private int col;
    private boolean penUp;
    private Directions direction;

    public Robot(int floorDim){
        this.floorDim = floorDim - 1;
        this.row = floorDim - 1;
        this.col = 0;
        this.penUp = true;
        this.direction = Directions.NORTH;

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPenDown(){
        if(this.penUp == true){
            this.penUp = false;
        }
    }
    
    public void setpenUp(){
        if(this.penUp == false){
            this.penUp = true;
        }
    }

    public boolean getPenUpStatus(){
        return this.penUp;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public Directions getDirection() {
        return direction;
    }

    public String printRobotStatus(){
//        System.out.println("Current Bot Position: [" + col + (floorDim - row) + "]");
//        System.out.println("Current Pen Position: " + (penUp == true ? "Pen Up" : "Pen Down"));
//        System.out.println("Current Bot Direction: " + direction);
        return "Current Bot Position: [" + col + (floorDim - row) + "]\n" +
                "Current Pen Position: " + (penUp ? "Pen Up" : "Pen Down") + "\n" +
                "Current Bot Direction: " + direction;
    }
}
