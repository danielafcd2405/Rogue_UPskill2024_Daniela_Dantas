package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.List;

public class Hero implements ImageTile {

    private Position position;


    public Hero(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(novaPosicao)
                    && (tile instanceof Floor || tile instanceof DoorOpen)) {
                this.setPosition(novaPosicao);
                break;
            }
        }

    }

}
