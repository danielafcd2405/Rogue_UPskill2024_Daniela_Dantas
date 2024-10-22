package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class GoodMeat extends GameObject {
    private final Position position;

    public GoodMeat(Position position) {
        this.position = position;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return true;
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
