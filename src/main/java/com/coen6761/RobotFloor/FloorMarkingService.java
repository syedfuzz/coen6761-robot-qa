package com.coen6761.RobotFloor;

import com.coen6761.Robot.Directions;

public class FloorMarkingService {
    private Floor floor;

    public Floor getFloor() {
        return floor;
    }

    public FloorMarkingService(int floorDim) {
        Floor floor = new Floor(floorDim);
        this.floor = floor;
    }

    public Floor markFloor(Directions direction, int[] curPos, int steps){
        int[][] floorArray = floor.getFloorArray();
        int floorDim = floorArray.length;
        int stepCount = 0;

        switch (direction) {
            case NORTH:
                stepCount = curPos[0] - steps > 0 ? curPos[0] - steps : 0;
                for(int i = curPos[0] ; i >= stepCount ; i--){
                    floorArray[i][curPos[1]] = 1;
                }
                break;

            case SOUTH:
                stepCount = curPos[0] + steps < floorDim ? curPos[0] + steps : floorDim - 1;
                for(int i = curPos[0] ; i <= stepCount ; i++){
                    floorArray[i][curPos[1]] = 1;
                }
                break;

            case EAST:
                stepCount = curPos[1] + steps < floorDim ? curPos[1] + steps : floorDim - 1;
                for(int i = curPos[1] ; i <= stepCount ; i++){
                    floorArray[curPos[0]][i] = 1;
                }
                break;

            case WEST:
                stepCount = curPos[1] - steps > 0 ? curPos[1] - steps : 0;
                for(int i = curPos[1] ; i >= stepCount ; i--){
                    floorArray[curPos[0]][i] = 1;
                }
                break;
        }
        floor.setFloorArray(floorArray);
        return floor;
    }

    public void printFloorFunction(int[] robotCoordinates, Directions robotDirection){
        int[][] floorArray = floor.getFloorArray();
            
        for(int i = 0 ; i < floorArray.length ; i++){
            for(int j = 0 ; j < floorArray.length ; j++){
                if(i == robotCoordinates[0] && j == robotCoordinates[1]){
                    String symbol = getSymbolByDirection(robotDirection);
                    System.out.print(symbol + " ");
                }
                else if(floorArray[i][j] == 1){
                    System.out.print("* ");
                } else {
                    System.out.print("_ ");
                } 
            }
            System.out.print("\n");
        }
    }

    private String getSymbolByDirection(Directions direction){
        if(direction == Directions.NORTH){
            return "▲";
        } else if(direction == Directions.EAST){
            return "▶";
        } else if(direction == Directions.WEST){
            return "◀";
        } else {
            return "▼";
        }
    }
}
