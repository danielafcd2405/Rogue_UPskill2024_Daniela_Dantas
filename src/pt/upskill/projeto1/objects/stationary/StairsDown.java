package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.rogue.utils.Position;

public class StairsDown extends DoorOpen{
    public StairsDown(Position position, int doorNumber, String nextRoom, int nextDoor) {
        super(position, doorNumber, nextRoom, nextDoor);
    }

    @Override
    public String getName() {
        return "StairsDown";
    }
}
