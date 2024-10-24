package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.Dungeon;
import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.objects.items.Item;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public class Fire extends Item implements FireTile {

    public Fire(Position position) {
        super(position);
        expPoints = 10;
    }

    @Override
    public boolean validateImpact() {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(this.getPosition()) && tile instanceof Enemy) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPickable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConsumable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Fire";
    }
}
