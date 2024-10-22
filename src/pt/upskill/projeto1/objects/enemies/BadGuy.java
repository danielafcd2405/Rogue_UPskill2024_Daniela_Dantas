package pt.upskill.projeto1.objects.enemies;

import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

public class BadGuy extends Enemy {

    private Position position;

    public BadGuy(Position position) {
        super(position);
        currentHP = maxHP = 30;
        atk = 20;
        expPoints = 20;
    }

    @Override
    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        if (canMove(novaPosicao)) {
            this.setPosition(novaPosicao);
        } else if (isHero(novaPosicao)) {
            System.out.println("BadGuy atacou");
            attackHero(novaPosicao);
        }
    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "BadGuy";
    }

}
