package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingObject extends GameObject{

    public abstract void move(Vector2D vector2D);


    // Verifica se é possível mover para a nova posição
    public boolean canMove(Position novaPosicao) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        // Se algum dos tiles nessa posição for intransponível, retorna false
        // Pode existir mais do que um tile na mesma posição: Floor e Wall
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(novaPosicao) && !((GameObject) tile).isTraversable(this)) {
                return false;
            }
        }

        return true;
    }

}
