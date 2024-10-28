package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.gui.StatusBar;
import pt.upskill.projeto1.objects.Hero;

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

    public static void saveDungeonMap() {
        Map<String, List<ImageTile>> savedDungeonMap = new HashMap<>(Dungeon.getDungeonMap());
        Dungeon.setSavedDungeonMap(savedDungeonMap);
        Dungeon.setSavedCurrentRoom(Dungeon.getCurrentRoom());
    }

    public static void saveHero() {
        Hero savedHero = new Hero(Engine.hero.getPosition());
        savedHero.setPoints(Engine.hero.getPoints());
        savedHero.setCurrentHP(Engine.hero.getCurrentHP());
        savedHero.setAtk(Engine.hero.getAtk());
        Engine.savedHero = savedHero;
    }

    public static void saveStatusBar() {
        List<ImageTile> savedStatusBar = new ArrayList<>(StatusBar.getStatusBarTiles());
        StatusBar.setSavedStatusBarTiles(savedStatusBar);
    }


}
