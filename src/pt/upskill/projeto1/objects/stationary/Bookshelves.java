package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Bookshelves extends GameObject {
    public Bookshelves(Position position) {
        super(position);
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Bookshelves";
    }
}
