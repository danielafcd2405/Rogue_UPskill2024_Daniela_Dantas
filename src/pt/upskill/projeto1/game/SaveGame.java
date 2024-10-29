package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.gui.StatusBar;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.enemies.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveGame {

    public static void loadLastSave() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearImages();
        gui.clearStatus();

        // Novo hero
        Engine.hero.setPosition(Engine.savedHero.getPosition());
        Engine.hero.setAtk(Engine.savedHero.getAtk());
        Engine.hero.setCurrentHP(Engine.savedHero.getCurrentHP());
        Engine.hero.setPoints(Engine.savedHero.getPoints());

        // Novo dungeonMap
        Map<String, List<ImageTile>> dungeonMap = Dungeon.getSavedDungeonMap();
        Dungeon.setDungeonMap(dungeonMap);
        List<ImageTile> tiles = dungeonMap.get(Dungeon.getSavedCurrentRoom());
        Dungeon.setCurrentRoom(Dungeon.getSavedCurrentRoom());

        // Nova StatusBar
        List<ImageTile> statusTiles = StatusBar.getSavedStatusBarTiles();
        StatusBar.setStatusBarTiles(statusTiles);

        gui.newImages(tiles);
        gui.newStatusImages(statusTiles);

        gui.setStatus("");
    }

    public static void saveGame() {
        saveHero();
        saveDungeonMap();
        saveStatusBar();

        System.out.println("Game was saved");
    }

    private static void saveDungeonMap() {
        //Map<String, List<ImageTile>> savedDungeonMap = new HashMap<>(Dungeon.getDungeonMap());
        //Dungeon.setSavedDungeonMap(savedDungeonMap);
        //Dungeon.setSavedCurrentRoom(Dungeon.getCurrentRoom());

        Map<String, List<ImageTile>> savedDungeonMap = new HashMap<>();

        File[] files = null;
        try {
            File f = new File("rooms");
            // Listar todos os nomes dos ficheiros na pasta rooms
            files = f.listFiles();
        }
        catch (Exception e) {
            System.out.println("Erro ao ler ficheiros");
        }

        for (File file : files) {
            List<ImageTile> savedRoom = new ArrayList<>(saveRoom(file.getName()));
            savedDungeonMap.put(file.getName(), savedRoom);
        }

        Dungeon.setSavedDungeonMap(savedDungeonMap);
        Dungeon.setSavedCurrentRoom(Dungeon.getCurrentRoom());

    }

    private static List<ImageTile> saveRoom(String roomName) {
        // Tive que copiar desta forma, para ter novos objetos de inimigos no savedDungeonMap
        // O que estava a acontecer é que as posições dos inimigos não estavam a ser guardadas
        List<ImageTile> room = Dungeon.getDungeonMap().get(roomName);
        List<ImageTile> savedRoom = new ArrayList<>();
        for (ImageTile tile : room) {
            if (tile instanceof Enemy) {
                if (tile instanceof Skeleton) {
                    Skeleton skeleton = new Skeleton(tile.getPosition());
                    skeleton.setCurrentHP(((Skeleton) tile).getCurrentHP());
                    savedRoom.add(skeleton);
                } else if (tile instanceof Bat) {
                    Bat bat = new Bat(tile.getPosition());
                    bat.setCurrentHP(((Bat) tile).getCurrentHP());
                    savedRoom.add(bat);
                } else if (tile instanceof BadGuy) {
                    BadGuy badGuy = new BadGuy(tile.getPosition());
                    badGuy.setCurrentHP(((BadGuy) tile).getCurrentHP());
                    savedRoom.add(badGuy);
                } else if (tile instanceof Thief) {
                    Thief thief = new Thief(tile.getPosition());
                    thief.setCurrentHP(((Thief) tile).getCurrentHP());
                    savedRoom.add(thief);
                }
                // TODO mais inimigos
            } else {
                savedRoom.add(tile);
            }
        }
        return savedRoom;
    }

    private static void saveHero() {
        Hero savedHero = new Hero(Engine.hero.getPosition());
        savedHero.setPoints(Engine.hero.getPoints());
        savedHero.setCurrentHP(Engine.hero.getCurrentHP());
        savedHero.setAtk(Engine.hero.getAtk());
        Engine.savedHero = savedHero;
    }

    private static void saveStatusBar() {
        List<ImageTile> savedStatusBar = new ArrayList<>();
        List<ImageTile> statusBar = StatusBar.getStatusBarTiles();
        for (ImageTile tile : statusBar) {
            savedStatusBar.add(tile);
        }
        StatusBar.setSavedStatusBarTiles(savedStatusBar);
    }


}
