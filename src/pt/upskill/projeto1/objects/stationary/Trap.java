package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Trap extends GameObject {

    private boolean isSafe;
    private final int damage = 5;

    public Trap(Position position, boolean isSafe) {
        super(position);
        this.isSafe = isSafe;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        return true;
    }

    @Override
    public String getName() {
        return "Trap";
    }
}
