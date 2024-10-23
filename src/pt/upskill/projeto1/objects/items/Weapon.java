package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Weapon extends Item{

    protected int bonusATK;

    public Weapon(Position position) {
        super(position);
    }

    public int getBonusATK() {
        return bonusATK;
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

}
