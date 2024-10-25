package pt.upskill.projeto1.objects.items;

import pt.upskill.projeto1.objects.MovingObject;

public interface Interactable {

    boolean isPickable(MovingObject movingObject);

    boolean isConsumable(MovingObject movingObject);

}
