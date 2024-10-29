package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.rogue.utils.Position;

public class Sword extends Weapon{
    public Sword(Position position) {
        super(position);
        expPoints = 30;
        bonusATK = 15;
    }

    @Override
    public String getName() {
        return "Sword";
    }
}
