package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Item extends GameObject implements Interactable {

    protected int expPoints;

    public Item(Position position) {
        super(position);
    }

    public int getExpPoints() {
        return expPoints;
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

}
