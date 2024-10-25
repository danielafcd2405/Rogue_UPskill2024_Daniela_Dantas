package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public abstract class MovingObject extends GameObject{

    public MovingObject(Position position) {
        super(position);
    }

    // Verifica se é possível mover para a nova posição
    protected boolean canMove(Position novaPosicao) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

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
