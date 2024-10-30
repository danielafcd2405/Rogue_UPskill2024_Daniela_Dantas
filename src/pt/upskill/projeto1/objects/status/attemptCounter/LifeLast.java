package pt.upskill.projeto1.objects.status.attemptCounter;

import pt.upskill.projeto1.objects.status.StatusBarObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class LifeLast extends StatusBarObject {
    public LifeLast(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "LifeLast";
    }
}
