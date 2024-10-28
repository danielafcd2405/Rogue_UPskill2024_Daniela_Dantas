package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.objects.Hero;

import java.io.*;
import java.util.*;

public class GameOver {

    public static void gameOver() {
        // guardar e mostrar a pontuação do jogo
        Hero hero = Engine.hero;
        int finalScore = hero.getPoints();

        recordGameScore(finalScore);

        int[] scores = orderGameScores(readGameScoresFile());
        String leaderBoard = "\nLeader Board\n";
        for (int i = 0; i < 5; i++) {
            leaderBoard += scores[i] + "\n";
        }

        ImageMatrixGUI.getInstance().showMessage("GameOver", "Pontuação final: \n" + finalScore +
                leaderBoard);

        restartGame();

    }

    private static void recordGameScore(int score) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("scores/game_scores.txt", true);
            fileWriter.write(score + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Não foi possível gravar a pontuação final.");
        }
    }

    private static void restartGame() {
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

    private static void getLeaderBoard() {

    }

    private static List<Integer> readGameScoresFile() {
        // Ler as pontuações gravadas no ficheiro
        List<Integer> scores = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("scores/game_scores.txt"));
            while (scanner.hasNextLine()) {
                scores.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado.");
        }

        return scores;
    }

    private static int[] orderGameScores(List<Integer> scores) {
        // Adicionar as pontuações da lista para um array
        int[] scoresArray = new int[scores.size()];
        for (int i = 0; i < scoresArray.length; i++) {
            scoresArray[i] = scores.get(i);
        }
        // Ordenar, por ordem decrescente, as pontuações
        int aux;

        for (int n = 0; n < scoresArray.length; n++) {
            for (int i = 0; i < scoresArray.length - 1; i++) {
                if (scoresArray[i] < scoresArray[i + 1]) {
                    aux = scoresArray[i];
                    scoresArray[i] = scoresArray[i + 1];
                    scoresArray[i + 1] = aux;
                }
            }
        }


        return scoresArray;
    }



}
