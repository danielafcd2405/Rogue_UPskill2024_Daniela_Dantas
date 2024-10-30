package pt.upskill.projeto1.objects.passages;

import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class SecretPassage extends DoorOpen {
    public SecretPassage(Position position, int doorNumber, String nextRoom, int nextDoor) {
        super(position, doorNumber, nextRoom, nextDoor);
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "SecretPassage";
    }
}
