package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorOpen extends DoorWay {

    public DoorOpen(Position position, int doorNumber, String nextRoom, int nextDoor, String key) {
        super(position, doorNumber);
        this.nextRoom = nextRoom;
        this.nextDoor = nextDoor;
        this.key = key;
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
        return "DoorOpen";
    }

}
