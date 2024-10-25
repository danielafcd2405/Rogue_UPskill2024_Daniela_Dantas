package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
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
        Hero hero = null;

        for (ImageTile tile : tiles) {
            if (tile instanceof Hero) {
                hero = (Hero) tile;
                break;
            }
        }

        if (hero != null) {
            // O hero move primeiro
            hero.move(vector2D);
            // Só depois movem os enimigos
            // Esta lista vai guardar todos os inimigos para depois aplicar o method moveEnemy() a cada um dos objetos da lista
            List<Enemy> enemies = new ArrayList<>();
            for (ImageTile tile : tiles) {
                if(tile instanceof Enemy) {
                    enemies.add((Enemy) tile);
                }
            }
            for (Enemy enemy : enemies) {
                enemy.moveEnemy();
            }
        }
    }


    public static void removeItems(Position position) {
        StatusBar.removeItemFromStatusBar(position);

        // O hero é guardado, removido e volta a ser adicionado, para o hero ficar por cima do item que foi largado no mapa
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        Hero hero = null;
        for (ImageTile tile : tiles) {
            if (tile instanceof Hero) {
                hero = (Hero) tile;
                tiles.remove(tile);
                gui.removeImage(tile);
                break;
            }
        }
        tiles.add(hero);
        gui.addImage(hero);
    }


    public static void lauchFireBall() {
        if (StatusBar.hasFireBalls()) {
            StatusBar.removeFireBall();
            List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
            Hero hero = null;
            for (ImageTile tile : tiles) {
                if (tile instanceof Hero) {
                    hero = (Hero) tile;
                    break;
                }
            }
            if (hero.enemyInRangeOfFireBall(hero.getPosition())) {
                hero.castFireBall();
            }
        } else {
            Engine.mensagensStatus += "Não tens bolas de fogo | ";
        }
    }


}
