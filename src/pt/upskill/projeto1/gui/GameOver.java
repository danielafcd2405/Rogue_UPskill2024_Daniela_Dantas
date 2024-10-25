package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.objects.Hero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameOver {

    public static void gameOver() {
        // guardar e mostrar a pontuação do jogo
        Hero hero = Hero.getINSTANCE();
        int finalScore = hero.getPoints();

        ImageMatrixGUI.getInstance().showMessage("GameOver", "Pontuação final: \n" + finalScore);

        loadLastSave();

    }


    public static void loadLastSave() {
        // Vai buscar o savedDungeonMap
        // O dungeonMap passa a ser igual ao savedDungeonMap
        Dungeon.setDungeonMap(Dungeon.buildDungeon());
        ImageMatrixGUI.getInstance().clearImages();
        ImageMatrixGUI.getInstance().newImages(Dungeon.getDungeonMap().get("room0.txt"));

    }

}
