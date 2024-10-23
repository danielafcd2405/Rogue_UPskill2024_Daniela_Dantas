package pt.upskill.projeto1.objects.enemies;

import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.List;

public class Thief extends Enemy{

    // O Thief move na diagonal
    private final Vector2D[] movimentosPossiveis = {
            new Vector2D(1, 1),
            new Vector2D(-1, -1),
            new Vector2D(-1, 1),
            new Vector2D(1, -1)
    };

    public Thief(Position position) {
        super(position);
        currentHP = maxHP = 10;
        atk = 5;
        expPoints = 15;
    }

    // Para is buscar os movimentosPossiveis definidos nesta classe
    @Override
    public Vector2D[] getMovimentosPossiveis() {
        return movimentosPossiveis;
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Thief";
    }
}
