package pt.upskill.projeto1.objects.enemies;

import javafx.geometry.Pos;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

public class Bat extends Enemy {

    public Bat(Position position) {
        super(position);
        currentHP = maxHP = 15;
        atk = 5;
        expPoints = 5;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Bat";
    }

}
