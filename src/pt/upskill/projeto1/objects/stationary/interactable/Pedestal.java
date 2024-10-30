package pt.upskill.projeto1.objects.stationary.interactable;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Pedestal extends GameObject {
    public Pedestal(Position position) {
        super(position);
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Pedestal";
    }
}
