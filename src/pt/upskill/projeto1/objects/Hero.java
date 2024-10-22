package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.List;

public class Hero extends MovingObject {

    private Position position;
    private int points = 50;
    private final int maxHP = 80;
    private int currentHP;
    private final int baseATK = 10;
    private int totalATK; // Calculado com base no baseATK + bonus de armas

    public Hero(Position position) {
        this.position = position;
        this.currentHP = maxHP;
        this.totalATK = baseATK;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
        if (this.points < 0) {
            this.points = 0;
        }
        System.out.println("Points: " + getPoints() + " (" + points + ")");
    }

    public int getBaseATK() {
        return baseATK;
    }

    public int getTotalATK() {
        return totalATK;
    }

    public void setTotalATK(int totalATK) {
        this.totalATK = totalATK;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
        // chamar método para alterar a Status Bar
    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        System.out.println("Hero moved to " + getPosition());
    }

    @Override
    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        // Se a novaPosicao estiver fora das coordenadas da sala, avança para uma nova sala
        if (novaPosicao.getX() < 0 || novaPosicao.getX() > 9 || novaPosicao.getY() < 0 || novaPosicao.getY() > 9) {
            Dungeon.changeRoom(this.getPosition());
            return;
        }

        if (canMove(novaPosicao)) {
            this.setPosition(novaPosicao);
            this.setPoints(-1); // cada movimento remove 1 ponto
        } else if (isEnemy(novaPosicao)) {
            System.out.println("Hero atacou");
            attackEnemy(novaPosicao);
        }

    }

    private boolean isEnemy(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    private void attackEnemy(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Enemy) {
                ((Enemy) tile).setCurrentHP(((Enemy) tile).getCurrentHP() - this.getTotalATK());
                System.out.println("Inimigo recebeu dano. currentHP: " + ((Enemy) tile).getCurrentHP());
                // Se o HP ficar a 0, o inimigo é removido da sala e o hero recebe os pontos correspondentes
                if (((Enemy) tile).getCurrentHP() <= 0) {
                    this.setPoints(((Enemy) tile).getExpPoints());
                    tiles.remove(tile);
                    System.out.println("Inimigo derrotado");
                    break;
                }
            }
        }
    }


    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }
}
