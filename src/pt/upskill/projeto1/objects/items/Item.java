package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Item extends GameObject {
    public Item(Position position) {
        super(position);
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    protected abstract boolean isPickable(MovingObject movingObject);

    protected abstract boolean isConsumable(MovingObject movingObject);

}
