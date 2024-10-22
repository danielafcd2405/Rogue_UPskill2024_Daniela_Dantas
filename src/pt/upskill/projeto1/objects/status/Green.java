package pt.upskill.projeto1.objects.status;

import pt.upskill.projeto1.rogue.utils.Position;

public class Green extends StatusBarObject{

    public Green(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Green";
    }
}
