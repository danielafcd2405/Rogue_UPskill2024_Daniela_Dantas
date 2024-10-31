package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.gui.StatusBar;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Actions {

    public static void movement(Vector2D vector2D) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        Hero hero = Engine.hero;
        // O hero move primeiro
        hero.move(vector2D);
        // Só depois movem os enimigos
        List<Enemy> enemies = new ArrayList<>();
        for (ImageTile tile : tiles) {
            if (tile instanceof Enemy) {
                ((Enemy) tile).moveEnemy();
            }
        }

    }


    public static void removeItems(Position position) {
        StatusBar.removeItemFromStatusBar(position);
        // O hero é guardado, removido e volta a ser adicionado, para o hero ficar por cima do item que foi largado no mapa
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        Hero hero = Engine.hero;
        for (ImageTile tile : tiles) {
            if (tile instanceof Hero) {
                tiles.remove(tile);
                gui.removeImage(tile);
                break;
            }
        }
        tiles.add(hero);
        gui.addImage(hero);
    }


    public static void lauchFireBall() {
        Hero hero = Engine.hero;
        if (hero.enemyInRangeOfFireBall(hero.getPosition())) {
            if (StatusBar.hasFireBalls()) {
                StatusBar.removeFireBall();
                hero.castFireBall();
            } else {
                Engine.mensagensStatus += "Não tens bolas de fogo | ";
            }
        } else {
            Engine.mensagensStatus += "Não tens inimigos na tua linha de ataque. | ";
        }
    }


}
