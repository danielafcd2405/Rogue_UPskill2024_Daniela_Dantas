package pt.upskill.projeto1.objects.items;

import javafx.geometry.Pos;
import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Key extends GameObject {

    private final Position position;

    public Key(Position position) {
        this.position = position;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return true;
    }

    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
