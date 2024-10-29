package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.objects.enemies.Bat;
import pt.upskill.projeto1.rogue.utils.Position;

public class VoidLeft extends GameObject {
    public VoidLeft(Position position) {
        super(position);
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        if (movingObject instanceof Bat) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "VoidLeft";
    }
}
