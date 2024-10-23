package pt.upskill.projeto1.objects.enemies;

import javafx.geometry.Pos;
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

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getExpPoints() {
        return expPoints;
    }


    public void moveEnemy() {

        List<Position> posicoesPossiveis = new ArrayList<>();
        int numPosicoesPossiveis = 0;

        if (isCloseToHero()) {
            // Obter uma lista das posições em que os movimentos possíveis do inimigo se sobrepôem ao heroRange
            List<Position> posicoesConvergentes = new ArrayList<>();
            List<Position> heroRange = getHeroRange();
            for (Vector2D vector2D : movimentosPossiveis) {
                for (Position positionHero : heroRange) {
                    if (this.getPosition().plus(vector2D).equals(positionHero)) {
                        posicoesConvergentes.add(this.getPosition().plus(vector2D));
                    }
                }
            }

            // Podem existir 1, 2 ou 3 posições na lista posicoesConvergentes
            // Se alguma das posições corresponder ao hero, ataca e não realiza movimento
            // o ataque conta como uma jogada
            for (Position p : posicoesConvergentes) {
                numPosicoesPossiveis++;
                if (isHero(p)) {
                    attackHero(p);
                    System.out.println("Enemy attacked!");
                    return;
                }
            }

            posicoesPossiveis = posicoesConvergentes;

        } else {
            for (Vector2D vector2D : movimentosPossiveis) {
                posicoesPossiveis.add(this.getPosition().plus(vector2D));
                numPosicoesPossiveis++;
            }
        }

        // Movimento aleatório

        // Gerar um número aleatório entre 0 e o numPosicoesPossiveis (índices da lista posicoesPossiveis)
        Random random = new Random();
        int randomIndex;
        Position novaPosicao;

        // Vai gerar uma nova posição até ter uma posição válida, assim o inimigo nunca fica parado
        do {
            randomIndex = random.nextInt(numPosicoesPossiveis);
            novaPosicao = posicoesPossiveis.get(randomIndex);
        } while (!canMove(novaPosicao));

        this.setPosition(novaPosicao);
        System.out.println("New enemy position: " + novaPosicao);

    }

    private boolean isCloseToHero() {
        // Para cada movimento possível do inimigo, verifica se correnponde a algum dos tiles do heroRange
        List<Position> heroRange = getHeroRange();
        for (Vector2D vector2D : movimentosPossiveis) {
            for (Position position : heroRange) {
                if (this.getPosition().plus(vector2D).equals(position)) {
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
        heroRange.add(posicaoHero.plus(new Vector2D(1, 0)));
        heroRange.add(posicaoHero.plus(new Vector2D(-1, 0)));
        heroRange.add(posicaoHero.plus(new Vector2D(0, 1)));
        heroRange.add(posicaoHero.plus(new Vector2D(0, -1)));
        heroRange.add(posicaoHero.plus(new Vector2D(1, 1)));
        heroRange.add(posicaoHero.plus(new Vector2D(1, -1)));
        heroRange.add(posicaoHero.plus(new Vector2D(-1, 1)));
        heroRange.add(posicaoHero.plus(new Vector2D(-1, -1)));

        return heroRange;
    }


    protected boolean isHero(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Hero) {
                return true;
            }
        }
        return false;
    }


    protected void attackHero(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Hero) {
                ((Hero) tile).setCurrentHP(((Hero) tile).getCurrentHP() - this.getAtk());
                System.out.println("Hero recebeu dano. currentHP: " + ((Hero) tile).getCurrentHP());
                // Se o HP ficar a 0, Game Over
                if (((Hero) tile).getCurrentHP() <= 0) {
                    gui.clearImages();
                    System.out.println("GAME OVER");
                    break;
                }
            }
        }
    }




}
