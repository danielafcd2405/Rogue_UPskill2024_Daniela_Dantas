package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;

public abstract class GameObject implements ImageTile {

    // Verifica se é possível atravessar o objeto
    // Recebe um MovingObject para fazer a verificação
    // Um Hero pode passar por uma DoorOpen mas um Skeleton não, por exemplo
    public abstract boolean isTraversable(MovingObject movingObject);

}
