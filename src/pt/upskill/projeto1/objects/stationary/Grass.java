package pt.upskill.projeto1.objects.stationary;

import javafx.geometry.Pos;
import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Grass extends GameObject {
    private final Position position;

    public Grass(Position position) {
        this.position = position;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return true;
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
