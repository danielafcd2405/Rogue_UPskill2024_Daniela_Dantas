package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.objects.enemies.Skeleton;
import pt.upskill.projeto1.rogue.utils.Direction;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    // Para ter o hero acessível para o método notify
    private Hero hero;

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        Room room = new Room();
        List<ImageTile> tiles = room.readRoomMap("room0.txt");

        for (ImageTile tile : tiles) {
            if (tile instanceof Hero) {
                hero = (Hero) tile;
            }
        }

        gui.setEngine(this);
        gui.newImages(tiles);
        gui.go();

        gui.setStatus("O jogo começou!");

        while (true){
            gui.update();
        }
    }

    public void notify(int keyPressed){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        // Esta lista vai guardar o hero e todos os inimigos para depois aplicar o método move() a cada um dos objectos da lista
        List<MovingObject> movingObjects = new ArrayList<>();
        for (ImageTile tile : tiles) {
            if(tile instanceof MovingObject) {
                movingObjects.add((MovingObject) tile);
            }
        }

        if (keyPressed == KeyEvent.VK_DOWN){
            for (MovingObject movingObject : movingObjects) {
                movingObject.move(Direction.DOWN.asVector());
            }
        }
        if (keyPressed == KeyEvent.VK_UP){
            for (MovingObject movingObject : movingObjects) {
                movingObject.move(Direction.UP.asVector());
            }
        }
        if (keyPressed == KeyEvent.VK_LEFT){
            for (MovingObject movingObject : movingObjects) {
                movingObject.move(Direction.LEFT.asVector());
            }
        }
        if (keyPressed == KeyEvent.VK_RIGHT){
            for (MovingObject movingObject : movingObjects) {
                movingObject.move(Direction.RIGHT.asVector());
            }
        }


    }


    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
