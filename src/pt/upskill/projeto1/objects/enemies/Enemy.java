package pt.upskill.projeto1.objects.enemies;

import javafx.geometry.Pos;
import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.game.Dungeon;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends MovingObject {

    protected int maxHP;
    protected int currentHP;
    protected int atk;
    protected int expPoints;
    private final Vector2D[] movimentosPossiveis = {
            new Vector2D(1, 0),
            new Vector2D(-1, 0),
            new Vector2D(0, 1),
            new Vector2D(0, -1)
    };

    public Enemy(Position position) {
        super(position);
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getAtk() {
        return atk;
    }

    public int getExpPoints() {
        return expPoints;
    }

    public Vector2D[] getMovimentosPossiveis() {
        return movimentosPossiveis;
    }

    private List<Position> posicoesPossiveis() {
        List<Position> posicoesPossiveis = new ArrayList<>();
        for (Vector2D vector2D : getMovimentosPossiveis()) {
            Position position = this.getPosition().plus(vector2D);
            if (canMove(position) || isHero(position)) {
                posicoesPossiveis.add(position);
            }
        }
        return posicoesPossiveis;
    }

    public void moveEnemy() {

        if (isCloseToHero()) {
            movimentoConvergente();
        } else {
            movimentoAleatorio(posicoesPossiveis());
        }

    }

    private void movimentoAleatorio(List<Position> posicoesPossiveis) {
        // Movimento aleatório
        // Gerar um número aleatório entre 0 e o tamanho da lista de posicoes possiveis
        Random random = new Random();
        int randomIndex;
        Position novaPosicao;

        // Vai gerar uma nova posição até ter uma posição válida, assim o inimigo nunca fica parado
        do {
            randomIndex = random.nextInt(posicoesPossiveis.size());
            novaPosicao = posicoesPossiveis.get(randomIndex);
        } while (!canMove(novaPosicao));

        this.setPosition(novaPosicao);
        System.out.println("Enemy moved: " + novaPosicao);
    }


    private void movimentoConvergente() {
        List<Position> posicoesPossiveisEnemy = posicoesPossiveis();
        List<Position> heroRange = getHeroRange();
        // Lista para guardar as posições que se sobrepõe entre a posicoesPossiveisEnemy e heroRange
        List<Position> posicoesConvergentes = new ArrayList<>();
        for (Position enemyPosition : posicoesPossiveisEnemy) {
            for (Position heroPosition : heroRange) {
                if (enemyPosition.equals(heroPosition)) {
                    posicoesConvergentes.add(enemyPosition);
                }
            }
        }

        if (! posicoesConvergentes.isEmpty()) {

            for (Position p : posicoesConvergentes) {
                if (isHero(p)) {
                    // Se o hero estiver num tile adjacente ao inimigo, o inimigo ataca
                    // Faz return para não se mover a seguir, porque um ataque conta como uma jogada do inimigo
                    attackHero(p);
                    return;
                }
            }

            movimentoAleatorio(posicoesConvergentes);

        } else {
            // Se a lista de posições convergentes estiver vazia, significa que entre o hero e o inimigo existem obstáculos (paredes)
            // Para o inimigo não ficar parado, faz um movimento aleatório normal
            movimentoAleatorio(posicoesPossiveis());
        }
    }

    private boolean isCloseToHero() {
        // Para cada movimento possível do inimigo, verifica se corresponde a algum dos tiles do heroRange
        List<Position> heroRange = getHeroRange();
        List<Position> enemyRange = posicoesPossiveis();
        for (Position enemyPosition : enemyRange) {
            for (Position heroPosition : heroRange) {
                if (enemyPosition.equals(heroPosition)) {
                    return true;
                }
            }
        }
        return false;
    }


    private List<Position> getHeroRange() {
        // Obter a posição atual do hero
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        Hero hero = null;
        for (ImageTile tile : tiles) {
            if (tile instanceof Hero) {
                hero = (Hero) tile;
            }
        }

        // Lista com os tiles à volta do hero, incluindo as diagonais, e a posição atual do hero
        List<Position> heroRange = new ArrayList<>();
        // Posição atual do hero
        Position posicaoHero = hero.getPosition();
        heroRange.add(posicaoHero);

        Vector2D[] posicoesPossiveis = {
                new Vector2D(1, 0),
                new Vector2D(-1, 0),
                new Vector2D(0, 1),
                new Vector2D(0, -1),
                new Vector2D(1, 1),
                new Vector2D(1, -1),
                new Vector2D(-1, 1),
                new Vector2D(-1, -1)
        };

        // Retorna apenas as posições para onde é possível mover
        for (Vector2D vector2D : posicoesPossiveis) {
            Position position = posicaoHero.plus(vector2D);
            if (canMove(position)) {
                heroRange.add(position);
            }
        }

        return heroRange;
    }


    private boolean isHero(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Hero) {
                return true;
            }
        }
        return false;
    }


    private void attackHero(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Hero) {
                ((Hero) tile).setCurrentHP(((Hero) tile).getCurrentHP() - this.getAtk());
                ((Hero) tile).setPoints(((Hero) tile).getPoints() - 5);
                Engine.mensagensStatus += "Ouch! - 5 pontos | ";
                System.out.println("Hero recebeu dano. currentHP: " + ((Hero) tile).getCurrentHP());
                // Se o HP ficar a 0, Game Over
                if (((Hero) tile).getCurrentHP() <= 0) {
                    //gui.clearImages();

                    System.out.println("GAME OVER");
                    break;
                }
            }
        }
    }




}
