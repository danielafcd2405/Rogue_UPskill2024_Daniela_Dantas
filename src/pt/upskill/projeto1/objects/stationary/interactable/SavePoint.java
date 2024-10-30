package pt.upskill.projeto1.objects.stationary.interactable;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class SavePoint extends GameObject {
    public SavePoint(Position position) {
        super(position);
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "SavePoint";
    }
}
