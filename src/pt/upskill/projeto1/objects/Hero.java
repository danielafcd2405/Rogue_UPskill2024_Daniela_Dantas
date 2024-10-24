package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.Dungeon;
import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.game.StatusBar;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.objects.items.Consumable;
import pt.upskill.projeto1.objects.items.Key;
import pt.upskill.projeto1.objects.items.Weapon;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.List;

public class Hero extends MovingObject {

    private int points = 50;
    private final int maxHP = 80;
    private int currentHP;
    private int atk = 10;

    public Hero(Position position) {
        super(position);
        this.currentHP = maxHP;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        if (this.points < 0) {
            this.points = 0;
        }
        System.out.println("Points: " + getPoints() + " (" + points + ") | ");
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
        if (currentHP > maxHP) {
            this.currentHP = maxHP;
        }
        // chamar método para alterar a Status Bar
        StatusBar.updateStatusBar(this.maxHP, this.currentHP);
    }

    @Override
    public String getName() {
        return "Hero";
    }

    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        // Se a novaPosicao estiver fora das coordenadas da sala, avança para uma nova sala
        if (novaPosicao.getX() < 0 || novaPosicao.getX() > 9 || novaPosicao.getY() < 0 || novaPosicao.getY() > 9) {
            Dungeon.changeRoom(this.getPosition());
            return;
        }

        if (canMove(novaPosicao)) {
            this.setPosition(novaPosicao);
            this.setPoints(this.getPoints() -1); // cada movimento remove 1 ponto
            Engine.mensagensStatus += "Pontuação: " + getPoints() + " | ";
            // Se na próxima posição estiver um item, faz a ação correspondente
            if (canConsume()) {
                consumeItem();
            } else if (canPickUp()) {
                pickUpItem();
            }
        } else if (isEnemy(novaPosicao)) {
            System.out.println("Hero atacou");
            Engine.mensagensStatus += "Ataque!! | ";
            attackEnemy(novaPosicao);
        } else {
            Engine.mensagensStatus += "Ouch! Esta parede é sólida. | ";
        }

    }

    private boolean canConsume() {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(this.getPosition()) && tile instanceof Consumable) {
                return true;
            }
        }
        return false;
    }

    private void consumeItem() {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(this.getPosition()) && tile instanceof Consumable) {
                // Restora HP do hero
                this.setCurrentHP(this.getCurrentHP()+((Consumable) tile).getRestoreHP());
                System.out.println("Restored HP");
                Engine.mensagensStatus += "Sentes um fluxo de vitalidade a correr pelas tuas veias: + " + ((Consumable) tile).getRestoreHP() + " HP | ";
                // Adiciona os expPoints do item que apanhou
                this.setPoints(this.getPoints() + ((Consumable) tile).getExpPoints());
                Engine.mensagensStatus += "Item consumido: + " + ((Consumable) tile).getExpPoints() + " pontos | ";
                // Remover o item do mapa
                tiles.remove(tile);
                ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
                gui.removeImage(tile);
                break;
            }
        }
    }

    private boolean canPickUp() {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(this.getPosition()) && (tile instanceof Weapon || tile instanceof Key)) {
                return true;
            }
        }
        return false;
    }


    private void pickUpItem() {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Key key = null;
        Weapon weapon = null;
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(this.getPosition()) && tile instanceof Key) {
                key = (Key) tile;
                if (StatusBar.hasItemSpace()) {
                    // Adicionar a chave à StatusBar
                    StatusBar.addItemToStatusBar(key);
                    gui.removeImage(tile);
                    tiles.remove(tile);

                    // Adicionar ao hero os pontos do item apanhado
                    this.setPoints(this.getPoints() + key.getExpPoints());
                    Engine.mensagensStatus += "Esta chave pode ser útil para alguma coisa! + " + key.getExpPoints() + " pontos | ";
                } else {
                    Engine.mensagensStatus += "Não tens mais espaço. Larga um dos itens que tens contigo. ";
                }
                return;
            }

            if (tile.getPosition().equals(this.getPosition()) && tile instanceof Weapon) {
                weapon = (Weapon) tile;
                if (StatusBar.hasItemSpace()) {
                    StatusBar.addItemToStatusBar(weapon);
                    gui.removeImage(tile);
                    tiles.remove(tile);

                    this.setPoints(this.getPoints() + weapon.getExpPoints());
                    this.setAtk(this.getAtk() + weapon.getBonusATK());
                    Engine.mensagensStatus += "Isto deve ser melhor do que usar os punhos vazios. " +
                            weapon.getBonusATK() + " ATK bonus + " + weapon.getExpPoints() + " pontos | ";
                } else {
                    Engine.mensagensStatus += "Não tens mais espaço. Larga um dos itens que tens contigo. ";
                }
                return;
            }
        }
    }




    private boolean isEnemy(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    private void attackEnemy(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof Enemy) {
                ((Enemy) tile).setCurrentHP(((Enemy) tile).getCurrentHP() - this.getAtk());
                System.out.println("Inimigo recebeu dano. currentHP: " + ((Enemy) tile).getCurrentHP());
                // Se o HP ficar a 0, o inimigo é removido da sala e o hero recebe os pontos correspondentes
                if (((Enemy) tile).getCurrentHP() <= 0) {
                    this.setPoints(this.getPoints() + ((Enemy) tile).getExpPoints());
                    tiles.remove(tile);
                    // Tenho que remover do dungeonMap e dos tiles que estão na gui
                    // Se remover só do dungeonMap, o inimigo fica visível após ser removido
                    // É impossível interagir com ele e após sair e voltar entrar na sala ele desaparece de vez
                    // Removendo da gui também, ele dasaparece logo após ser derrotado
                    gui.removeImage(tile);
                    System.out.println("Inimigo derrotado");
                    Engine.mensagensStatus += "Inimigo derrotado! + " + ((Enemy) tile).getExpPoints() + " pontos | ";
                    return;
                }
            }
        }
    }


    @Override
    public boolean isTraversable(MovingObject movingObject) {
        return false;
    }
}
