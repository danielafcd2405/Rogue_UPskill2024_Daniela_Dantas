package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
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
        List<ImageTile> tiles = Room.readRoomMap("room0.txt");
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
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        List<Skeleton> skeletons = new ArrayList<>();
        // Para guardar os tiles depois de serem atualizados após o movimento dos inimigos
        List<ImageTile> updatedTiles = new ArrayList<>();

        for (ImageTile tile : tiles) {
            if (tile instanceof Skeleton) {
                Skeleton s = (Skeleton) tile;
                skeletons.add(s);
            } else {
                updatedTiles.add(tile);
            }
        }

        if (keyPressed == KeyEvent.VK_DOWN){
            hero.move(Direction.DOWN.asVector());

            for (Skeleton skeleton : skeletons) {
                skeleton.move(Direction.DOWN.asVector());
                updatedTiles.add(skeleton);
            }

        }
        if (keyPressed == KeyEvent.VK_UP){
            hero.move(Direction.UP.asVector());

            for (Skeleton skeleton : skeletons) {
                skeleton.move(Direction.UP.asVector());
                updatedTiles.add(skeleton);
            }

        }
        if (keyPressed == KeyEvent.VK_LEFT){
            hero.move(Direction.LEFT.asVector());

            for (Skeleton skeleton : skeletons) {
                skeleton.move(Direction.LEFT.asVector());
                updatedTiles.add(skeleton);
            }

        }
        if (keyPressed == KeyEvent.VK_RIGHT){
            hero.move(Direction.RIGHT.asVector());

            for (Skeleton skeleton : skeletons) {
                skeleton.move(Direction.RIGHT.asVector());
                updatedTiles.add(skeleton);
            }

        }

        // Atualiza os tiles da gui
        tiles.clear();
        tiles.addAll(updatedTiles);
        gui.update();
    }


    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
