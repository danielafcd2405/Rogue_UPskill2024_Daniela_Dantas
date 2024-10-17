package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorOpen implements ImageTile {

    private Position position;

    public DoorOpen(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DoorOpen";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
