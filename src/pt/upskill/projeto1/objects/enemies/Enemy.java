package pt.upskill.projeto1.objects.enemies;

import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.game.Tiles;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.objects.items.GoodMeat;
import pt.upskill.projeto1.objects.items.Potion;
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
    private ImageTile[] drops = new ImageTile[10];

    public Enemy(Position position) {
        super(position);
    }

    public ImageTile[] getDrops() {
        return drops;
    }

    public void setDrops() {
        this.drops[0] = new GoodMeat(this.getPosition());
        this.drops[1] = new GoodMeat(this.getPosition());
        this.drops[2] = new Potion(this.getPosition());
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

    public void dropEnemyItem() {
        // o setDrops() é apenas chamado neste momento, para que a posição do item seja a do enemy neste instante
        this.setDrops();
        // gera um índice aleatoriamente
        Random random = new Random();
        int randomIndex = random.nextInt(this.getDrops().length);
        ImageTile drop = this.getDrops()[randomIndex];
        if (drop != null) {
            // Adicionar o item ao mapa
            Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom()).add(drop);
            ImageMatrixGUI.getInstance().addImage(drop);
        }
    }

    private List<Position> posicoesPossiveis() {
        List<Position> posicoesPossiveis = new ArrayList<>();
        for (Vector2D vector2D : getMovimentosPossiveis()) {
            Position position = this.getPosition().plus(vector2D);
            if (canMove(position) || Tiles.isHero(position)) {
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
    }


    private void movimentoConvergente() {
        List<Position> posicoesPossiveisEnemy = posicoesPossiveis();
        List<Position> heroRange = Hero.getHeroRange();
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
                if (Tiles.isHero(p)) {
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
        List<Position> heroRange = Hero.getHeroRange();
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


    protected void attackHero(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Hero) {
                ((Hero) tile).setCurrentHP(((Hero) tile).getCurrentHP() - this.getAtk());
                ((Hero) tile).setPoints(((Hero) tile).getPoints() - 3);
                Engine.mensagensStatus += "Ouch! - 3 pontos | ";
                System.out.println("Hero recebeu dano. currentHP: " + ((Hero) tile).getCurrentHP());
            }
        }
    }




}
