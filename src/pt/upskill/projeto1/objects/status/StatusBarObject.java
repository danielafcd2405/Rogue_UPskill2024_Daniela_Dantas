package pt.upskill.projeto1.objects.status;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class StatusBarObject implements ImageTile {
    protected Position position;

    public StatusBarObject(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

}
