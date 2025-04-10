package com.coen6761.Robot;

public class RobotService{

    private int floorDim;
    private Robot robot;

    public RobotService(int floorDim) {
        this.robot = new Robot(floorDim);
        this.floorDim = floorDim;
    }

    //move left
    public Robot moveWest(int step) {
        int curCol = robot.getCol();
        if(curCol - step > 0){
            robot.setCol(curCol - step);
        } else {
            robot.setCol(0);
        }
        return robot; 
    }

    //move right
    public Robot moveEast(int step) {
        int curCol = robot.getCol();
        if(curCol + step < floorDim){
            robot.setCol(curCol + step);
        } else {
            robot.setCol(floorDim - 1);
        }
        return robot;  
    }

    public Robot moveNorth(int step) {
        int curRow = robot.getRow();
        if(curRow - step > 0){
            robot.setRow(curRow - step);
        } else {
            robot.setRow(0);
        }
        return robot;  
    }

    public Robot moveSouth(int step) {
        int curRow = robot.getRow();
        if(curRow + step < floorDim){
            robot.setRow(curRow + step);
        } else {
            robot.setRow(floorDim - 1);
        }
        return robot;  
    }

        // public Robot turnNorth(){
    //     Directions direction = robot.getDirection();
    //     if(direction!= Directions.NORTH){
    //         robot.setDirection(Directions.NORTH);
    //     }
    //     return robot;
    // }

    // public Robot turnSouth(){
    //     Directions direction = robot.getDirection();
    //     if(direction!= Directions.SOUTH){
    //         robot.setDirection(Directions.SOUTH);
    //     }
    //     return robot;
    // }

    //turn left
    public Robot turnLeft(){
        Directions direction = robot.getDirection();
        robot.setDirection(directionMappingForLeft(direction));
        return robot;
    }

    //turn right
    public Robot turnRight(){
        Directions direction = robot.getDirection();
        robot.setDirection(directionMappingForRight(direction));
        return robot;
    }

    public Directions directionMappingForLeft(Directions direction){
        if(direction == Directions.NORTH){
            return Directions.WEST;
        } else if(direction == Directions.EAST){
            return Directions.NORTH;
        } else if(direction == Directions.WEST){
            return Directions.SOUTH;
        } else {
            return Directions.EAST;
        }
    }

    public Directions directionMappingForRight(Directions direction){
        if(direction == Directions.NORTH){
            return Directions.EAST;
        } else if(direction == Directions.EAST){
            return Directions.SOUTH;
        } else if(direction == Directions.WEST){
            return Directions.NORTH;
        } else {
            return Directions.WEST;
        }
    }

    public Robot getRobot() {
        return robot;
    }
}
