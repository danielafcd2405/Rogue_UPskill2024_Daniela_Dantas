package pt.upskill.projeto1.objects.status;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class StatusBar {

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

}
