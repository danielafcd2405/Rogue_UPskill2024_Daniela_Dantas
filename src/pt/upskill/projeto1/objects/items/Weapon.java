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

    public void setBonusATK(int bonusATK) {
        this.bonusATK = bonusATK;
    }

    @Override
    public boolean isPickable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConsumable(MovingObject movingObject) {
        return false;
    }

}
