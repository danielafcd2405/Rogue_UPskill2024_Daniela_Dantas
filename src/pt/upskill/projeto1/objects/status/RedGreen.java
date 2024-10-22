package pt.upskill.projeto1.objects.status;

import pt.upskill.projeto1.rogue.utils.Position;

public class RedGreen extends StatusBarObject{

    public RedGreen(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "RedGreen";
    }
}
