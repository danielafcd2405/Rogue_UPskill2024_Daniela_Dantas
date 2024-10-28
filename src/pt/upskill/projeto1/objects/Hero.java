package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.game.GameOver;
import pt.upskill.projeto1.game.SaveGame;
import pt.upskill.projeto1.gui.*;
import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.game.FireBallThread;
import pt.upskill.projeto1.objects.enemies.Enemy;
import pt.upskill.projeto1.objects.items.Consumable;
import pt.upskill.projeto1.objects.items.Fire;
import pt.upskill.projeto1.objects.items.Key;
import pt.upskill.projeto1.objects.items.Weapon;
import pt.upskill.projeto1.objects.stationary.DoorClosed;
import pt.upskill.projeto1.objects.stationary.DoorWay;
import pt.upskill.projeto1.objects.stationary.SavePoint;
import pt.upskill.projeto1.objects.stationary.Wall;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Hero extends MovingObject {

    private int points;
    private final int maxHP;
    private int currentHP;
    private int atk;

    public Hero(Position position) {
        super(position);
        this.points = 50;
        this.maxHP = 80;
        this.atk = 10;
        this.currentHP = maxHP;
    }

    public static Hero setHero() {
        return new Hero(new Position(3, 6));
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
        // chamar method para alterar a Status Bar
        StatusBar.updateStatusBar(this.maxHP, this.currentHP);

        if (currentHP <= 0) {
            // TODO
            //GameOver.gameOver();
            //SaveGame.loadLastSave();
        }

    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        // Abre uma porta (destrancada) para a porta ficar aberta depois de passar por ela
        // É útil deixar uma porta aberta depois de passar por ela, para ajudar o jogador a navegar no mapa e saber por onde já passou
        if (isDoorClosed(this.getPosition())) {
            openTheDoor(this.getPosition());
        }
    }

    public void move(Vector2D vector2D) {
        Position novaPosicao = this.getPosition().plus(vector2D);

        // Se a novaPosicao estiver fora das coordenadas da sala, avança para uma nova sala
        // Os únicos pontos onde isto pode acontecer é depois de passar por uma porta, o resta da sala está rodeada por
        // Walls, que são intransponíveis
        if (novaPosicao.getX() < 0 || novaPosicao.getX() > 9 || novaPosicao.getY() < 0 || novaPosicao.getY() > 9) {
            Dungeon.changeRoom(this.getPosition());
            return;
        }

        if (canMove(novaPosicao)) {
            if (isDoorClosed(novaPosicao) && isDoorLocked(novaPosicao)) {
                if (hasKey(novaPosicao)) {
                    unlockDoor(novaPosicao);
                    System.out.println("Porta foi destrancada");
                } else {
                    Engine.mensagensStatus += "Ouch! É para puxar em vez de empurrar? Não! Esta porta está trancada! Tenta procurar a chave certa. | ";
                    return;
                }
            }
            this.setPosition(novaPosicao);
            this.setPoints(this.getPoints() - 1); // cada movimento remove 1 ponto
            Engine.mensagensStatus += "Pontuação: " + getPoints() + " | ";
            // Se na próxima posição estiver um item, faz a ação correspondente
            if (canConsume()) {
                consumeItem();
            } else if (canPickUp()) {
                pickUpItem();
            } else if (isSavePoint()) {
                SaveGame.saveGame();
            }

        } else if (isEnemy(novaPosicao)) {
            System.out.println("Hero atacou");
            Engine.mensagensStatus += "Ataque!! | ";
            attackEnemy(novaPosicao);
        } else {
            Engine.mensagensStatus += "Ouch! Esta parede é sólida. | ";
        }

    }

    private boolean isSavePoint() {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(this.getPosition()) && tile instanceof SavePoint) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoorLocked(Position position) {
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

    private boolean hasKey(Position position) {
        // Vai buscar o nome da chave à porta
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        String keyName = "";
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorClosed) {
                keyName = ((DoorClosed) tile).getKey();
            }
        }

        // Vai buscar as chaves presentes no inventário e verifica se alguma tem o nome certo
        List<ImageTile> statusBarTiles = StatusBar.getStatusBarTiles();
        for (ImageTile statusTile : statusBarTiles) {
            if (statusTile instanceof Key && ((Key) statusTile).getKeyName().equals(keyName)) {
                System.out.println("Chave: " + ((Key) statusTile).getKeyName());
                return true;
            }
        }
        return false;
    }

    private void unlockDoor(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        // Muda o atributo isLocked para false
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorClosed) {
                ((DoorClosed) tile).setLocked(false);
                Engine.mensagensStatus += "Porta destrancada! | ";
                StatusBar.removeKeyFromStatusBar(((DoorClosed) tile).getKey());
                break;
            }
        }
    }


    private boolean isDoorClosed(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorClosed) {
                return true;
            }
        }
        return false;
    }


    private void openTheDoor(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorClosed) {
                tiles.remove(tile);
                gui.removeImage(tile);
                break;
            }
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
                this.setCurrentHP(this.getCurrentHP() + ((Consumable) tile).getRestoreHP());
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
            if (tile.getPosition().equals(this.getPosition()) && (tile instanceof Weapon || tile instanceof Key || tile instanceof Fire)) {
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
                    System.out.println("Hero ATK: " + this.getAtk());
                    Engine.mensagensStatus += "Isto deve ser melhor do que usar os punhos vazios. " +
                            weapon.getBonusATK() + " ATK bonus + " + weapon.getExpPoints() + " pontos | ";
                } else {
                    Engine.mensagensStatus += "Não tens mais espaço. Larga um dos itens que tens contigo. ";
                }
                return;
            }

            if (tile.getPosition().equals(this.getPosition()) && tile instanceof Fire) {
                if (StatusBar.hasFireBallSpace()) {
                    StatusBar.addFireBallToStatusBar();
                    this.setPoints(this.getPoints() + ((Fire) tile).getExpPoints());
                    Engine.mensagensStatus += "Agora tens o poder do fogo do teu lado! + " + ((Fire) tile).getExpPoints() + " pontos |";
                    tiles.remove(tile);
                    gui.removeImage(tile);
                } else {
                    Engine.mensagensStatus += "Não tens mais espaço. Larga um dos itens que tens contigo. ";
                }
                return;
            }
        }
    }

    public void castFireBall() {
        FireTile fireBall = new Fire(this.getPosition());
        FireBallThread fireBallThread = null;
        if (checkUp(this.getPosition())) {
            fireBallThread = new FireBallThread(Direction.UP, fireBall);
        } else if (checkDown(this.getPosition())) {
            fireBallThread = new FireBallThread(Direction.DOWN, fireBall);
        } else if (checkLeft(this.getPosition())) {
            fireBallThread = new FireBallThread(Direction.LEFT, fireBall);
        } else if (checkRight(this.getPosition())) {
            fireBallThread = new FireBallThread(Direction.RIGHT, fireBall);
        }

        // Para a fireBall aparecer no mapa
        ImageMatrixGUI.getInstance().addImage(fireBall);
        fireBallThread.run();

    }


    public boolean enemyInRangeOfFireBall(Position position) {
        if (checkUp(position) || checkDown(position) || checkRight(position) || checkLeft(position)) {
            return true;
        }
        return false;
    }

    public boolean checkUp(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && (tile instanceof Wall || tile instanceof DoorWay)) {
                return false;
            }
        }
        if (isEnemy(position)) {
            return true;
        } else {
            return checkUp(position.plus(Direction.UP.asVector()));
        }
    }

    public boolean checkDown(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && (tile instanceof Wall || tile instanceof DoorWay)) {
                return false;
            }
        }
        if (isEnemy(position)) {
            return true;
        } else {
            return checkDown(position.plus(Direction.DOWN.asVector()));
        }
    }

    public boolean checkLeft(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && (tile instanceof Wall || tile instanceof DoorWay)) {
                return false;
            }
        }
        if (isEnemy(position)) {
            return true;
        } else {
            return checkLeft(position.plus(Direction.LEFT.asVector()));
        }
    }

    public boolean checkRight(Position position) {
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && (tile instanceof Wall || tile instanceof DoorWay)) {
                return false;
            }
        }
        if (isEnemy(position)) {
            return true;
        } else {
            return checkRight(position.plus(Direction.RIGHT.asVector()));
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



    public static List<Position> getHeroRange() {
        // Obter a posição atual do hero
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());
        Hero hero = Engine.hero;

        // Lista com os tiles à volta do hero, incluindo as diagonais, e a posição atual do hero
        List<Position> heroRange = new ArrayList<>();
        // Posição atual do hero
        Position posicaoHero = hero.getPosition();
        heroRange.add(posicaoHero);

        Vector2D[] posicoesPossiveis = {
                new Vector2D(1, 0),
                new Vector2D(-1, 0),
                new Vector2D(0, 1),
                new Vector2D(0, -1),
                new Vector2D(1, 1),
                new Vector2D(1, -1),
                new Vector2D(-1, 1),
                new Vector2D(-1, -1)
        };

        // Retorna apenas as posições para onde é possível mover
        for (Vector2D vector2D : posicoesPossiveis) {
            Position position = posicaoHero.plus(vector2D);
            if (hero.canMove(position)) {
                heroRange.add(position);
            }
        }

        return heroRange;
    }
}
