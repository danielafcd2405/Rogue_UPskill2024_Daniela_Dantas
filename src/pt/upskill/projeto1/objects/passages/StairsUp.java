package pt.upskill.projeto1.objects.passages;

import pt.upskill.projeto1.rogue.utils.Position;

public class StairsUp extends DoorOpen {
    public StairsUp(Position position, int doorNumber, String nextRoom, int nextDoor) {
        super(position, doorNumber, nextRoom, nextDoor);
    }

    @Override
    public String getName() {
        return "StairsUp";
    }
}
