package pt.upskill.projeto1.objects.status;

import pt.upskill.projeto1.rogue.utils.Position;

public class Red extends StatusBarObject{

    public Red(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Red";
    }

}
