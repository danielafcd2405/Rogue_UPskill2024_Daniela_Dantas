package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Floor;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Room;
import pt.upskill.projeto1.objects.Wall;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    // Para ter o hero acessível para o método notify
    private Hero hero;

    public void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        //List<ImageTile> tiles = new ArrayList<>();
        Room room = new Room();
        List<ImageTile> tiles = room.readRoomMap("room0.txt");
        /*
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }

         */

        hero = new Hero(new Position(4, 7));
        tiles.add(hero);

        gui.setEngine(this);
        gui.newImages(tiles);
        gui.go();

        gui.setStatus("O jogo começou!");

        while (true){
            gui.update();
        }
    }

    public void notify(int keyPressed){
        if (keyPressed == KeyEvent.VK_DOWN){
            hero.move(Direction.DOWN.asVector());
        }
        if (keyPressed == KeyEvent.VK_UP){
            hero.move(Direction.UP.asVector());
        }
        if (keyPressed == KeyEvent.VK_LEFT){
            hero.move(Direction.LEFT.asVector());
        }
        if (keyPressed == KeyEvent.VK_RIGHT){
            hero.move(Direction.RIGHT.asVector());
        }
    }


    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
