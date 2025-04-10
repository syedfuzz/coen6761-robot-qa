package com.coen6761.RobotFloor;

public class Floor {
    private int[][] floorArray;

    public Floor(int floorSize) {
        this.floorArray = new int[floorSize][floorSize];
        setToBlanks();
    }

    private void setToBlanks(){
        for(int i = 0 ; i < floorArray.length ; i++){
            for(int j = 0 ; j < floorArray.length ; j++){
                floorArray[i][j] = 0;
            }
        }
    }

    public int[][] getFloorArray(){
        return this.floorArray;
    }

    public void setFloorArray(int[][] floorArray){
        this.floorArray = floorArray;
    }
}
