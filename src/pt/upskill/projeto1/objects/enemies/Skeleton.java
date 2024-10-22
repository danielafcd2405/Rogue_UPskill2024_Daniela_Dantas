package pt.upskill.projeto1.objects.enemies;

import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

public class Skeleton extends Enemy {

    public Skeleton(Position position) {
        super(position);
        currentHP = maxHP = 20;
        atk = 10;
        expPoints = 10;
    }

    @Override
    public String getName() {
        return "Skeleton";
    }


    @Override
    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        if (canMove(novaPosicao)) {
            this.setPosition(novaPosicao);
        } else if (isHero(novaPosicao)) {
            System.out.println("Skeleton atacou");
            attackHero(novaPosicao);
        }

    }

    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }
}
