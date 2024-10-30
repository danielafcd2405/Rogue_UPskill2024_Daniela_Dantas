package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

public class Potion extends Consumable{
    public Potion(Position position) {
        super(position);
        expPoints = 20;
    }

    @Override
    public String getName() {
        return "Potion";
    }
}
