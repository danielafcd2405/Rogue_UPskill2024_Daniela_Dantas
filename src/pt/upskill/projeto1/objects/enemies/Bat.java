package pt.upskill.projeto1.objects.enemies;

import javafx.geometry.Pos;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

public class Bat extends MovingObject {

    private Position position;

    public Bat(Position position) {
        this.position = position;
    }

    @Override
    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        if (canMove(novaPosicao)) {
            this.setPosition(novaPosicao);
        }
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Bat";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
