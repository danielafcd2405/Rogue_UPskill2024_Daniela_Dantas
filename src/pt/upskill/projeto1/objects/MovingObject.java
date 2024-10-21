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

        // Lista dos tiles na novaPosição
        // Pode existir mais do que 1, Floor e Skeleton, por exemplo
        List<ImageTile> tilesInNovaPosicao = new ArrayList<>();

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(novaPosicao)) {
                tilesInNovaPosicao.add(tile);
            }
        }

        // Verifica se algum desses tiles é um tile para onde não se pode mover
        boolean podeMover = true;
        for (ImageTile tile : tilesInNovaPosicao) {
            if ( !((GameObject)tile).isTraversable(this) ) {
                podeMover = false;
            }
        }

        return podeMover;
    }

}
