package pt.upskill.projeto1.gui;

import javafx.geometry.Pos;
import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.objects.items.Fire;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.items.Item;
import pt.upskill.projeto1.objects.items.Key;
import pt.upskill.projeto1.objects.items.Weapon;
import pt.upskill.projeto1.objects.stationary.DoorWay;
import pt.upskill.projeto1.objects.status.Black;
import pt.upskill.projeto1.objects.status.Green;
import pt.upskill.projeto1.objects.status.Red;
import pt.upskill.projeto1.objects.status.RedGreen;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatusBar {

    private static List<ImageTile> statusBarTiles = buildStatusBar();
    private static List<ImageTile> savedStatusBarTiles;

    public static List<ImageTile> getStatusBarTiles() {
        return statusBarTiles;
    }

    public static void setStatusBarTiles(List<ImageTile> statusBarTiles) {
        StatusBar.statusBarTiles = statusBarTiles;
    }

    public static List<ImageTile> getSavedStatusBarTiles() {
        return savedStatusBarTiles;
    }

    public static void setSavedStatusBarTiles(List<ImageTile> savedStatusBarTiles) {
        StatusBar.savedStatusBarTiles = savedStatusBarTiles;
    }

    public static List<ImageTile> buildStatusBar() {
        List<ImageTile> statusBarTiles = new ArrayList<>();

        statusBarTiles.add(new Black(new Position(0, 0)));
        statusBarTiles.add(new Black(new Position(1, 0)));
        statusBarTiles.add(new Black(new Position(2, 0)));

        statusBarTiles.add(new Green(new Position(3, 0)));
        statusBarTiles.add(new Green(new Position(4, 0)));
        statusBarTiles.add(new Green(new Position(5, 0)));
        statusBarTiles.add(new Green(new Position(6, 0)));

        statusBarTiles.add(new Black(new Position(7, 0)));
        statusBarTiles.add(new Black(new Position(8, 0)));
        statusBarTiles.add(new Black(new Position(9, 0)));

        return statusBarTiles;
    }

    public static void updateStatusBar(int heroMaxHP, int heroCurrentHP) {
        // 4 tiles de HP Bar
        // percentagens de HP que é possível representar:
        // 0.00; 0.125; 0.25; 0.375; 0.50; 0.625; 0.75; 0.875; 1.00

        double hpPercentage = (double) heroCurrentHP / heroMaxHP;

        if (hpPercentage <= 0) {
            statusBarTiles.add(new Red(new Position(3, 0)));
            statusBarTiles.add(new Red(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage > 0.0 && hpPercentage < 0.125) {
            statusBarTiles.add(new RedGreen(new Position(3, 0)));
            statusBarTiles.add(new Red(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.125 && hpPercentage < 0.25) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Red(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.25 && hpPercentage < 0.375) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new RedGreen(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.375 && hpPercentage < 0.50) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Red(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.50 && hpPercentage < 0.625) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new RedGreen(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.625 && hpPercentage < 0.75) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Green(new Position(5, 0)));
            statusBarTiles.add(new Red(new Position(6, 0)));
        } else if (hpPercentage >= 0.75 && hpPercentage < 0.875) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Green(new Position(5, 0)));
            statusBarTiles.add(new RedGreen(new Position(6, 0)));
        } else if (hpPercentage >= 0.875) {
            statusBarTiles.add(new Green(new Position(3, 0)));
            statusBarTiles.add(new Green(new Position(4, 0)));
            statusBarTiles.add(new Green(new Position(5, 0)));
            statusBarTiles.add(new Green(new Position(6, 0)));
        }

        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearStatus();
        gui.newStatusImages(statusBarTiles);

    }


    public static boolean hasItemSpace() {
        if (isEmptySlot(new Position(7, 0)) || isEmptySlot(new Position(8, 0)) || isEmptySlot(new Position(9, 0))) {
            return true;
        }
        return false;
    }

    public static void addItemToStatusBar(Item item) {
        for (ImageTile tile : statusBarTiles) {
            if (tile.getPosition().equals(new Position(7, 0)) && isEmptySlot(tile.getPosition())) {
                item.setPosition(tile.getPosition());
                break;
            } else if (tile.getPosition().equals(new Position(8, 0)) && isEmptySlot(tile.getPosition())) {
                item.setPosition(tile.getPosition());
                break;
            } else if (tile.getPosition().equals(new Position(9, 0))) {
                item.setPosition(tile.getPosition());
            }
        }

        statusBarTiles.add(item);
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.addStatusImage(item);

    }

    public static boolean hasFireBallSpace() {
        if (isEmptySlot(new Position(0, 0)) || isEmptySlot(new Position(1, 0)) || isEmptySlot(new Position(2, 0))) {
            return true;
        }
        return false;
    }

    public static void addFireBallToStatusBar() {
        Position position = null;
        for (ImageTile tile : statusBarTiles) {
            if (tile.getPosition().equals(new Position(0, 0)) && isEmptySlot(tile.getPosition())) {
                position = new Position(0, 0);
                break;
            } else if (tile.getPosition().equals(new Position(1, 0)) && isEmptySlot(tile.getPosition())) {
                position = new Position(1, 0);
                break;
            } else if (tile.getPosition().equals(new Position(2, 0))) {
                position = new Position(2, 0);
            }
        }

        Fire fireBall = new Fire(position);
        statusBarTiles.add(fireBall);
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.addStatusImage(fireBall);
    }

    public static boolean hasFireBalls() {
        if (!isEmptySlot(new Position(0, 0)) || !isEmptySlot(new Position(1, 0)) || !isEmptySlot(new Position(2, 0))) {
            return true;
        }
        return false;
    }

    public static void removeFireBall() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        for (ImageTile statusTile : statusBarTiles) {
            if (statusTile instanceof Fire) {
                // Remove a primeira fire ball que encontrar
                statusBarTiles.remove(statusTile);
                gui.removeStatusImage(statusTile);
                break;
            }
        }
    }


    public static void removeItemFromStatusBar(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        // Vai buscar o hero, para depois remover o bonusATK, caso o item seja uma arma
        Hero hero = Engine.hero;

        // Larga o item num tile vazio (apenas Floor) em redor do hero
        // O item volta a aparecer no mapa, para evitar situações em que uma chave é eliminada sem querer
        // O jogador pode voltar atrás para recolher os itens que largou
        List<Position> heroRange = Hero.getHeroRange(); // Posições sem mais nenhum item
        List<Position> posicoesLivres = new ArrayList<>();
        for (Position p : heroRange) {
            if (isEmptyFloor(p)) {
                posicoesLivres.add(p);
            }
        }

        if (!posicoesLivres.isEmpty()) {
            for (ImageTile tile : statusBarTiles) {
                if (tile.getPosition().equals(position) && tile instanceof Item) {
                    if (tile instanceof Weapon) {
                        // Remove o bonusATK da arma que foi descartada
                        hero.setAtk(hero.getAtk() - ((Weapon) tile).getBonusATK());
                        Engine.mensagensStatus += "Arma descartada - " + ((Weapon) tile).getBonusATK() + " ATK | ";
                    }

                    // O tile onde vai ser largado o item é escolhido aleatoriamente
                    Random random = new Random();
                    int randomIndex = random.nextInt(posicoesLivres.size());
                    Position itemPosition = posicoesLivres.get(randomIndex);
                    // Alterar a posição do item
                    ((Item) tile).setPosition(itemPosition);
                    // Adicionar ao mapa
                    tiles.add(tile);
                    gui.addImage(tile);

                    // Remover da StatusBar
                    statusBarTiles.remove(tile);
                    gui.removeStatusImage(tile);
                    return;
                }
            }
        } else {
            Engine.mensagensStatus += "Não há espaço neste local para descartar itens. | ";
        }

    }

    public static boolean isEmptyFloor(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) &&
                    (tile instanceof Item || tile instanceof Enemy || tile instanceof DoorWay || tile instanceof Hero)) {
                return false;
            }
        }
        return true;
    }


    public static void removeKeyFromStatusBar(String keyName) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        for (ImageTile tile : statusBarTiles) {
            if (tile instanceof Key && ((Key) tile).getKeyName().equals(keyName)) {
                statusBarTiles.remove(tile);
                gui.removeStatusImage(tile);
                break;
            }
        }
    }

    private static boolean isEmptySlot(Position position) {
        // Verifica se a posição dada contém algum item
        for (ImageTile tile : statusBarTiles) {
            if (tile.getPosition().equals(position) && tile instanceof Item) {
                return false;
            }
        }
        return true;
    }


}
