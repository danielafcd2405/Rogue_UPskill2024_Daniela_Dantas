package pt.upskill.projeto1.objects.enemies;

import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.gui.StatusBar;
import pt.upskill.projeto1.objects.GameObject;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.MovingObject;
import pt.upskill.projeto1.objects.items.*;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Thief extends Enemy{

    private Item stolenItem = null;
    private Item[] drops = new Item[1];

    // O Thief move na diagonal
    private final Vector2D[] movimentosPossiveis = {
            new Vector2D(1, 1),
            new Vector2D(-1, -1),
            new Vector2D(-1, 1),
            new Vector2D(1, -1)
    };

    public Thief(Position position) {
        super(position);
        currentHP = maxHP = 10;
        atk = 5;
        expPoints = 15;
    }

    public Item getStolenItem() {
        return stolenItem;
    }

    public void setStolenItem(Item stolenItem) {
        this.stolenItem = stolenItem;
    }

    @Override
    public Item[] getDrops() {
        return this.drops;
    }

    @Override
    public void setDrops() {
        if (this.getStolenItem() != null) {
            this.drops[0] = this.getStolenItem();
            this.drops[0].setPosition(this.getPosition());
        }
    }

    @Override
    protected void attackHero(Position position) {
        super.attackHero(position);
        if (heroHasItems() && this.getStolenItem() == null) {
            // O Thief apenas pode roubar 1 item de cada vez
            stealItem();
        }
    }

    private boolean heroHasItems() {
        List<ImageTile> statusTiles = StatusBar.getStatusBarTiles();
        for (ImageTile tile : statusTiles) {
            if (tile instanceof Weapon || tile instanceof Key) {
                return true;
            }
        }
        return false;
    }

    private void stealItem() {
        // Rouba um dos itens do hero, aleatoriamente
        List<Position> heroInventoryPositions = new ArrayList<>();
        heroInventoryPositions.add(new Position(7, 0));
        heroInventoryPositions.add(new Position(8, 0));
        heroInventoryPositions.add(new Position(9, 0));

        Random random = new Random();
        int randomIndex = 0;
        Position position = null;
        do {
            randomIndex = random.nextInt(heroInventoryPositions.size());
            position = heroInventoryPositions.get(randomIndex);
        } while (StatusBar.isEmptySlot(position));

        List<ImageTile> statusBarTiles = StatusBar.getStatusBarTiles();
        Hero hero = Engine.hero;
        for (ImageTile tile : statusBarTiles) {
            if (tile.getPosition().equals(position) && tile instanceof Item) {
                Item item = null;
                if (tile instanceof Weapon) {
                    // Remove o bonusATK da arma que foi descartada
                    hero.setAtk(hero.getAtk() - ((Weapon) tile).getBonusATK());
                    Engine.mensagensStatus += "Arma roubada - " + ((Weapon) tile).getBonusATK() + " ATK | ";
                    if (tile instanceof Sword) {
                        item = new Sword(tile.getPosition());
                    } else if (tile instanceof Hammer) {
                        item = new Hammer(tile.getPosition());
                    } //TODO mais armas
                } else if (tile instanceof Key) {
                    item = new Key(tile.getPosition(), ((Key) tile).getKeyName());
                } //TODO outras chaves

                this.setStolenItem(item);

                // Remover da StatusBar
                statusBarTiles.remove(tile);
                ImageMatrixGUI.getInstance().removeStatusImage(tile);
                return;
            }
        }

    }

    // Para is buscar os movimentosPossiveis definidos nesta classe
    @Override
    public Vector2D[] getMovimentosPossiveis() {
        return movimentosPossiveis;
    }

    @Override
    protected boolean isTraversable(MovingObject movingObject) {
        return false;
    }

    @Override
    public String getName() {
        return "Thief";
    }
}
