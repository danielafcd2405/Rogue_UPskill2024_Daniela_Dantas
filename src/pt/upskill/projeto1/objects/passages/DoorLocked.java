package pt.upskill.projeto1.objects.passages;

import pt.upskill.projeto1.rogue.utils.Position;

public class DoorLocked extends DoorClosed {
    public DoorLocked(Position position, int doorNumber, String nextRoom, int nextDoor, String key) {
        super(position, doorNumber, nextRoom, nextDoor, key);
    }

    @Override
    public String getName() {
        return "DoorLocked";
    }
}
