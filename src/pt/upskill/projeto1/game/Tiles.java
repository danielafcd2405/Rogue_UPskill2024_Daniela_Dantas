package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.Dungeon;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.objects.passages.DoorClosed;
import pt.upskill.projeto1.objects.passages.SecretPassage;
import pt.upskill.projeto1.objects.passages.StairsDown;
import pt.upskill.projeto1.objects.passages.StairsUp;
import pt.upskill.projeto1.objects.stationary.interactable.Pedestal;
import pt.upskill.projeto1.objects.stationary.interactable.SavePoint;
import pt.upskill.projeto1.objects.stationary.interactable.Trap;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public class Tiles {
    // Methods relacionados com fazer verificações dos tiles
    // verificar se um tile é uma instanceOf alguma coisa

    public static boolean isHero(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Hero) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPedestal(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile instanceof Pedestal) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSecretPassage(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof SecretPassage) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTrap(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Trap) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStairs(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && (tile instanceof StairsDown || tile instanceof StairsUp)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSavePoint(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof SavePoint) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDoorLocked(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorClosed) {
                if (((DoorClosed) tile).isLocked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isDoorClosed(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && (tile instanceof DoorClosed)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnemy(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

}
