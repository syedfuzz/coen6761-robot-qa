package com.coen6761.MovementHistory;

import java.util.LinkedList;

import com.coen6761.Robot.Directions;
import com.coen6761.Robot.RobotService;
import com.coen6761.RobotFloor.FloorMarkingService;

public class MovementHistoryService {
    private LinkedList<MovementEvent> movementList;
    private RobotService robotService;
    private FloorMarkingService floorMarkingService;

    public MovementHistoryService() {
        this.movementList = new LinkedList<>();
    }

    public LinkedList<MovementEvent> getMovementList() {
        return movementList;
    }

    private void addMovement(MovementEvent event){
        movementList.add(event);
    }

    public void addLeftTurnEvent(boolean penUpStatus){
        MovementEvent event = new MovementEvent(MovementType.LEFT, -1, penUpStatus);
        addMovement(event);
    }

    public void addRightTurnEvent(boolean penUpStatus){
        MovementEvent event = new MovementEvent(MovementType.RIGHT, -1, penUpStatus);
        addMovement(event);
    }

    public void addMoveEvent(int stepCount, boolean penUpStatus){
        MovementEvent event = new MovementEvent(MovementType.MOVE, stepCount, penUpStatus);
        addMovement(event);
    }

    public void addPenUpEvent(){
        MovementEvent event = new MovementEvent(MovementType.PENUP, -1, true);
        addMovement(event);
    }

    public void addPenDownEvent(){
        MovementEvent event = new MovementEvent(MovementType.PENDOWN, -1, false);
        addMovement(event);
    }

    public void addInitializeEvent(int floorDim){
        MovementEvent event = new MovementEvent(MovementType.INITIALIZE, floorDim);
        addMovement(event);
    }

    public void display(){
        if(movementList.size()>0){
            int count=1;
            for(MovementEvent event: movementList){
                System.out.println(count+". "+event.toString());
                eventTranslator(event);
                count++;
            }
        } else {
            System.out.println("No events recorded yet.");
        }
    }

    private void eventTranslator(MovementEvent event){
        if(event.getMovement()==MovementType.INITIALIZE){
            this.robotService = new RobotService(event.getFloorDim());
            this.floorMarkingService = new FloorMarkingService(event.getFloorDim());
        } else if(event.getMovement()==MovementType.LEFT){
            robotService.turnLeft();
        } else if(event.getMovement()==MovementType.RIGHT){
            robotService.turnRight();
        } else if(event.getMovement()==MovementType.PENUP){
            robotService.getRobot().setpenUp();
        } else if(event.getMovement()==MovementType.PENDOWN){
            robotService.getRobot().setPenDown();
        } else {
            int[] startPos = new int[]{robotService.getRobot().getRow(), robotService.getRobot().getCol()};
            switch (robotService.getRobot().getDirection()) {
                case NORTH:
                    robotService.moveNorth(event.getStepCount());
                    markingTheFloor(startPos, event.getStepCount(), Directions.NORTH);
                    break;
                case SOUTH:
                    robotService.moveSouth(event.getStepCount());
                    markingTheFloor(startPos, event.getStepCount(), Directions.SOUTH);
                    break;
                case EAST:
                    robotService.moveEast(event.getStepCount());
                    markingTheFloor(startPos, event.getStepCount(), Directions.EAST);
                    break;
                case WEST:
                robotService.moveWest(event.getStepCount());
                    markingTheFloor(startPos, event.getStepCount(), Directions.WEST);
                    break;
            }
        }
        floorMarkingService.printFloorFunction(new int[]{robotService.getRobot().getRow(),robotService.getRobot().getCol()}, robotService.getRobot().getDirection());
        System.out.println("");
    }

    private void markingTheFloor(int[] startPos, int  moveMentSteps, Directions direction){
        if(robotService.getRobot().getPenUpStatus()==false){
            floorMarkingService.markFloor(direction, startPos, moveMentSteps);
        }
    }
}
