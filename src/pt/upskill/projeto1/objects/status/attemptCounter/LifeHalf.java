package pt.upskill.projeto1.objects.status.attemptCounter;

import pt.upskill.projeto1.objects.status.StatusBarObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class LifeHalf extends StatusBarObject {
    public LifeHalf(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "LifeHalf";
    }
}
