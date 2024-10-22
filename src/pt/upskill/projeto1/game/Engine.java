package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.objects.enemies.Skeleton;
import pt.upskill.projeto1.objects.stationary.DoorWay;
import pt.upskill.projeto1.objects.status.StatusBar;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    // Para ter o hero acessível para o método notify
    //private Hero hero;

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        List<ImageTile> tiles = Room.readRoomMap("room0.txt");

        Hero hero = new Hero(new Position(3, 6));
        tiles.add(hero);

        gui.setEngine(this);
        gui.newImages(tiles);

        List<ImageTile> statusBarTiles = StatusBar.buildStatusBar();
        gui.newStatusImages(statusBarTiles);

        gui.go();

        gui.setStatus("O jogo começou!");

        while (true){
            gui.update();
        }
    }

    public void notify(int keyPressed){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        Hero hero = null;

        // Esta lista vai guardar todos os inimigos para depois aplicar o método move() a cada um dos objetos da lista
        List<Enemy> enemies = new ArrayList<>();
        for (ImageTile tile : tiles) {
            if(tile instanceof Enemy) {
                enemies.add((Enemy) tile);
            } else if (tile instanceof Hero) {
                hero = (Hero) tile;
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

        if (vector2D != null) {
            // O hero move primeiro
            hero.move(vector2D);
            // Só depois movem os enimigos
            for (Enemy enemy : enemies) {
                enemy.move(vector2D);
            }
        }

    }


    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
