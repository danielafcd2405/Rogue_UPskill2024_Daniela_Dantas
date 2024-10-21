package pt.upskill.projeto1.objects.stationary;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Wall extends GameObject {

    private Position position;

    public Wall(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }
}
