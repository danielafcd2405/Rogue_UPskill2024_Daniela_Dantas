package pt.upskill.projeto1.objects;

import javafx.geometry.Pos;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class GameObject implements ImageTile {

    // Verifica se é possível atravessar o objeto
    // Recebe um MovingObject para fazer a verificação
    // Um Hero pode passar por uma DoorOpen mas um Skeleton não, por exemplo
    public abstract boolean isTraversable(MovingObject movingObject);

}
