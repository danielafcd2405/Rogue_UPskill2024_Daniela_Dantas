package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer extends Item {

    public Hammer(Position position) {
        super(position);
    }

    @Override
    protected boolean isPickable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isConsumable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

}
