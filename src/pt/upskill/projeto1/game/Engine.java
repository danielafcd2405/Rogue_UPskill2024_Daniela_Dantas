package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Engine {

    public static String mensagensStatus = "";

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        Map<String, List<ImageTile>> dungeonMap = Dungeon.getDungeonMap();
        List<ImageTile> tiles = dungeonMap.get("room0.txt");
        Dungeon.setCurrentRoom("room0.txt");

        Hero hero = new Hero(new Position(3, 6));
        tiles.add(hero);

        gui.setEngine(this);
        gui.newImages(tiles);

        List<ImageTile> statusBarTiles = StatusBar.getStatusBarTiles();
        gui.newStatusImages(statusBarTiles);

        gui.go();

        gui.setStatus("O jogo começou!");

        while (true){
            gui.update();
        }
    }

    public void notify(int keyPressed){
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        Hero hero = null;

        for (ImageTile tile : tiles) {
            if (tile instanceof Hero) {
                hero = (Hero) tile;
                break;
            }
        }

        Vector2D vector2D = null;
        if (keyPressed == KeyEvent.VK_DOWN){
            vector2D = Direction.DOWN.asVector();
        }
        if (keyPressed == KeyEvent.VK_UP){
            vector2D = Direction.UP.asVector();
        }
        if (keyPressed == KeyEvent.VK_LEFT){
            vector2D = Direction.LEFT.asVector();
        }
        if (keyPressed == KeyEvent.VK_RIGHT){
            vector2D = Direction.RIGHT.asVector();
        }

        if (vector2D != null && hero != null) {
            // O hero move primeiro
            hero.move(vector2D);
            // Só depois movem os enimigos
            // Esta lista vai guardar todos os inimigos para depois aplicar o método move() a cada um dos objetos da lista
            List<Enemy> enemies = new ArrayList<>();
            for (ImageTile tile : tiles) {
                if(tile instanceof Enemy) {
                    enemies.add((Enemy) tile);
                } else if (tile instanceof Hero) {
                    hero = (Hero) tile;
                }
            }

            for (Enemy enemy : enemies) {
                enemy.moveEnemy();
            }
        }

        // Remover itens da StatusBar
        Position statusBarPosition = null;
        if (keyPressed == KeyEvent.VK_1) {
            statusBarPosition = new Position(7, 0);
        }
        if (keyPressed == KeyEvent.VK_2) {
            statusBarPosition = new Position(8, 0);
        }
        if (keyPressed == KeyEvent.VK_3) {
            statusBarPosition = new Position(9, 0);
        }

        if (statusBarPosition != null) {
            StatusBar.removeItemFromStatusBar(statusBarPosition);
        }

        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.setStatus(mensagensStatus);
        mensagensStatus = "";

    }


    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
