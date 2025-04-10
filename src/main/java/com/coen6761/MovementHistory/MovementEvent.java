package com.coen6761.MovementHistory;

public class MovementEvent {
    private MovementType movement;
    private int stepCount;
    private boolean penUpStatus;
    private int floorDim;
    
    //for move events
    public MovementEvent(MovementType movement, int stepCount, boolean penUpStatus) {
        this.movement = movement;
        this.stepCount = stepCount;
        this.penUpStatus = penUpStatus;
    }

    //for intialise event
    public MovementEvent(MovementType movement, int floorDim) {
        this.movement = movement;
        this.floorDim = floorDim;
    }

    public MovementType getMovement() {
        return movement;
    }
    public int getStepCount() {
        return stepCount;
    }
    public boolean getPenUpStatus() {
        return penUpStatus;
    }
    public int getFloorDim() {
        return floorDim;
    }
    @Override
    public String toString() {
        if(movement == MovementType.INITIALIZE){
            return "MovementEvent: [ Action = " + movement + ", floorDim = " + floorDim + " ]";
        }
        if(stepCount != -1){
            return "MovementEvent: [ Movement = " + movement + ", stepCount = " + stepCount + " ]";
        }
        return "MovementEvent: [ Action = " + movement + " ]";
    }

    
}
