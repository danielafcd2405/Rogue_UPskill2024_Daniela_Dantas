package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Floor;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.Room;
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

        hero = new Hero(new Position(4, 3));
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
            System.out.println("User pressed down key!");
            hero.setPosition(new Position(hero.getPosition().getX() + 0, hero.getPosition().getY() + 1));
        }
        if (keyPressed == KeyEvent.VK_UP){
            System.out.println("User pressed up key!");
            hero.setPosition(new Position(hero.getPosition().getX() + 0, hero.getPosition().getY() - 1));
        }
        if (keyPressed == KeyEvent.VK_LEFT){
            System.out.println("User pressed left key!");
            hero.setPosition(new Position(hero.getPosition().getX() - 1, hero.getPosition().getY() + 0));
        }
        if (keyPressed == KeyEvent.VK_RIGHT){
            System.out.println("User pressed right key!");
            hero.setPosition(new Position(hero.getPosition().getX() + 1, hero.getPosition().getY() + 0));
        }
    }


    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
