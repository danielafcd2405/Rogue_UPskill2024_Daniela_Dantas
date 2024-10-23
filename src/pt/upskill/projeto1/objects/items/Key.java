package pt.upskill.projeto1.objects.items;

import javafx.geometry.Pos;
import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Key extends Item {

    private String keyName;

    public Key(Position position) {
        super(position);
    }

    public String getKeyName() {
        return keyName;
    }

    @Override
    protected boolean isPickable(MovingObject movingObject) {
        if (movingObject instanceof Hero) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isConsumable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Key";
    }

}
