package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorWay extends GameObject {

    private final Position position;
    private final int doorNumber;
    protected String nextRoom;
    protected int nextDoor;
    protected String key;


    public DoorWay(Position position, int doorNumber) {
        this.position = position;
        this.doorNumber = doorNumber;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public String getNextRoom() {
        return nextRoom;
    }

    public int getNextDoor() {
        return nextDoor;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return true;
    }

    @Override
    public String getName() {
        return "DoorWay";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
