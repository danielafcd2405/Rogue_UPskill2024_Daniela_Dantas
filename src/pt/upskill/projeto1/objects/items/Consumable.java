package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Consumable extends Item{

    protected int restoreHP;

    public Consumable(Position position) {
        super(position);
    }

    public int getRestoreHP() {
        return restoreHP;
    }

    @Override
    public boolean isPickable(MovingObject movingObject) {
        return false;
    }

    @Override
    public boolean isConsumable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

}
