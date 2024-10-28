package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.objects.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameOver {

    public static void gameOver() {
        // guardar e mostrar a pontuação do jogo
        Hero hero = Engine.hero;
        int finalScore = hero.getPoints();

        ImageMatrixGUI.getInstance().showMessage("GameOver", "Pontuação final: \n" + finalScore);

        recordGameScore(finalScore);

        restartGame();

    }

    public static void recordGameScore(int score) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("scores/game_scores.txt", true);
            fileWriter.write(score + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Não foi possível gravar a pontuação final.");
        }
    }

    public static void restartGame() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearImages();
        gui.clearStatus();

        // Novo hero
        Hero hero = Engine.hero = Hero.setHero();

        // Novo dungeonMap
        Map<String, List<ImageTile>> dungeonMap = Dungeon.buildDungeon();
        Dungeon.setDungeonMap(dungeonMap);
        List<ImageTile> tiles = dungeonMap.get("room0.txt");
        tiles.add(hero);
        Dungeon.setCurrentRoom("room0.txt");

        // Nova StatusBar
        List<ImageTile> statusTiles = StatusBar.buildStatusBar();
        StatusBar.setStatusBarTiles(statusTiles);

        gui.newImages(tiles);
        gui.newStatusImages(statusTiles);

        gui.setStatus("O jogo começou!");

    }



}
