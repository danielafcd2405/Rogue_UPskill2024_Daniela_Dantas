package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorClosed extends DoorWay {
    private boolean isLocked;

    public DoorClosed(Position position, int doorNumber, String nextRoom, int nextDoor, String key) {
        super(position, doorNumber);
        this.nextRoom = nextRoom;
        this.nextDoor = nextDoor;
        this.key = key;
        if (!key.isEmpty()) {
            this.isLocked = true;
        } else {
            this.isLocked = false;
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }



}
