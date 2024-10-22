package pt.upskill.projeto1.objects.status;

import pt.upskill.projeto1.rogue.utils.Position;

public class Black extends StatusBarObject{

    public Black(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Black";
    }
}
