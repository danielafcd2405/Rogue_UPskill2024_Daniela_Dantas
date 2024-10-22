package pt.upskill.projeto1.objects.enemies;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.game.Dungeon;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public abstract class Enemy extends MovingObject {

    protected int maxHP;
    protected int currentHP;
    protected int atk;
    protected int expPoints;

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
