package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.status.attemptCounter.LifeFull;
import pt.upskill.projeto1.objects.status.attemptCounter.LifeHalf;
import pt.upskill.projeto1.objects.status.attemptCounter.LifeLast;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public class AttemptCounter {

    // Method para adicionar os tiles com o indicador das tentativas restantes
    // É chamado no início do jogo, sempre que se muda de sala, e quando se faz loadLastSave()
    public static void attemptIndicator(int attemptCounter) {
        ImageTile tile = null;
        switch (attemptCounter) {
            case 0:
                tile = new LifeFull(new Position(9, 0));
                break;
            case 1:
                tile = new LifeHalf(new Position(9, 0));
                break;
            case 2:
                tile = new LifeLast(new Position(9, 0));
                break;
        }
        Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom()).add(tile);
        ImageMatrixGUI.getInstance().addImage(tile);
    }

}
