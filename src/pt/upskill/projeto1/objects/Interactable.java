package pt.upskill.projeto1.objects;

public interface Interactable {

    boolean isPickable(MovingObject movingObject);

    boolean isConsumable(MovingObject movingObject);

}
