package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Engine {

    public static String mensagensStatus = "";
    public static Hero hero = Hero.setHero();
    public static Hero savedHero;
    public static int attemptCounter = 0;

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        Map<String, List<ImageTile>> dungeonMap = Dungeon.getDungeonMap();
        List<ImageTile> tiles = dungeonMap.get("room0.txt");
        Dungeon.setCurrentRoom("room0.txt");
        tiles.add(hero);

        gui.setEngine(this);
        gui.newImages(tiles);

        List<ImageTile> statusBarTiles = StatusBar.getStatusBarTiles();
        gui.newStatusImages(statusBarTiles);

        SaveGame.saveGame();

        gui.go();

        gui.setStatus("O jogo começou!");

        while (true){
            gui.update();
        }
    }


    public void actions(int keyPressed) {
        // Movimento do hero e dos inimigos
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
            Actions.movement(vector2D);
        }


        // Lançar bolas de fogo
        if (keyPressed == KeyEvent.VK_SPACE) {
            Actions.lauchFireBall();
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
            Actions.removeItems(statusBarPosition);
        }


        // Mostrar as mensagens de Status
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.setStatus(mensagensStatus);
        mensagensStatus = "";

        if (hero.getCurrentHP() <= 0) {
            if (attemptCounter < 2) {
                SaveGame.loadLastSave();
                attemptCounter++;
            } else {
                GameOver.gameOver();
            }
        }


    }




    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
