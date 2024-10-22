package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.status.Black;
import pt.upskill.projeto1.objects.status.Green;
import pt.upskill.projeto1.objects.status.Red;
import pt.upskill.projeto1.objects.status.RedGreen;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class StatusBar {

    private static final List<ImageTile> statusBarTiles = buildStatusBar();

    public static List<ImageTile> getStatusBarTiles() {
        return statusBarTiles;
    }

    public static List<ImageTile> buildStatusBar() {
        List<ImageTile> statusBarTiles = new ArrayList<>();

        statusBarTiles.add(new Black(new Position(0, 0)));
        statusBarTiles.add(new Black(new Position(1, 0)));
        statusBarTiles.add(new Black(new Position(2, 0)));

        statusBarTiles.add(new Green(new Position(3, 0)));
        statusBarTiles.add(new Green(new Position(4, 0)));
        statusBarTiles.add(new Green(new Position(5, 0)));
        statusBarTiles.add(new Green(new Position(6, 0)));

        statusBarTiles.add(new Black(new Position(7, 0)));
        statusBarTiles.add(new Black(new Position(8, 0)));
        statusBarTiles.add(new Black(new Position(9, 0)));

        return statusBarTiles;
    }

    public static void updateStatusBar(int heroMaxHP, int heroCurrentHP) {
        // 4 tiles de HP Bar
        // percentagens de HP que é possível representar:
        // 0.00; 0.125; 0.25; 0.375; 0.50; 0.625; 0.75; 0.875; 1.00

        double hpPercentage = (double) heroCurrentHP / heroMaxHP;

        if (hpPercentage >= 0 && hpPercentage < 0.125) {
            statusBarTiles.add(new RedGreen(new Position(3, 0)));
            statusBarTiles.add(new Red(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.125 && hpPercentage < 0.25) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Red(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.25 && hpPercentage < 0.375) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new RedGreen(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.375 && hpPercentage < 0.50) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.50 && hpPercentage < 0.625) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new RedGreen(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.625 && hpPercentage < 0.75) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Green(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.75 && hpPercentage < 0.875) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Green(new Position(5, 0)));
            statusBarTiles.add(new RedGreen(new Position(6, 0)));
        } else {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Green(new Position(5, 0)));
            statusBarTiles.add(new Green(new Position(6, 0)));
        }

        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearStatus();
        gui.newStatusImages(statusBarTiles);

    }

}
