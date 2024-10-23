package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer extends Weapon {

    public Hammer(Position position) {
        super(position);
        expPoints = 20;
        bonusATK = 10;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

}
