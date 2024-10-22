package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.enemies.BadGuy;
import pt.upskill.projeto1.objects.enemies.Bat;
import pt.upskill.projeto1.objects.enemies.Skeleton;
import pt.upskill.projeto1.objects.items.GoodMeat;
import pt.upskill.projeto1.objects.items.Hammer;
import pt.upskill.projeto1.objects.items.Key;
import pt.upskill.projeto1.objects.stationary.*;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Room {

    public static List<ImageTile> readRoomMap(String room) {

        // Lista para guardar as definições das portas
        List<String> linhasDefinicaoPortas = new ArrayList<>();
        // Lista para guardar apenas as linhas que representam a sala
        List<String> linhasDeSala = new ArrayList<>();

        try {
            Scanner roomScanner = new Scanner(new File("rooms/" + room));
            while (roomScanner.hasNextLine()) {
                String linha = roomScanner.nextLine();
                if (linha.startsWith("# 0") || linha.startsWith("# 1") || linha.startsWith("# 2")) {
                    linhasDefinicaoPortas.add(linha);
                } else if (! linha.startsWith("#")) {
                    linhasDeSala.add(linha);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado.");;
        }

        List<ImageTile> tiles = buildRoom(linhasDeSala);
        addRoomConnections(tiles, linhasDefinicaoPortas);

        return tiles;
    }


    private static List<ImageTile> buildRoom(List<String> linhasDeSala) {
        List<ImageTile> tiles = new ArrayList<>();

        // Adiciona Floor 10x10 à sala, de modo a garantir que os tiles do tipo Floor estão sempre por baixo
        // O resto dos tiles são adicionados por cima do Floor
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }

        for (int j = 0; j < 10; j++) {
            // y = j => j percorre as linhas
            String[] paineis = linhasDeSala.get(j).split("");
            for (int i = 0; i < 10; i++) {
                // x = i -> i percorre as colunas
                String painel = paineis[i];

                switch (painel) {
                    case "W":
                        tiles.add(new Wall(new Position(i, j)));
                        break;
                    case "S":
                        tiles.add(new Skeleton(new Position(i, j)));
                        break;
                    case "B":
                        tiles.add(new Bat(new Position(i, j)));
                        break;
                    case "G":
                        tiles.add(new BadGuy(new Position(i, j)));
                        break;
                    case "k":
                        tiles.add(new Key(new Position(i, j)));
                        break;
                    case "h":
                        tiles.add(new Hammer(new Position(i, j)));
                        break;
                    case "m":
                        tiles.add(new GoodMeat(new Position(i, j)));
                        break;
                    case "0":
                    case "1":
                    case "2":
                        tiles.add(new DoorWay(new Position(i, j), Integer.parseInt(painel)));
                }
            }
        }

        return tiles;
    }


    private static void addRoomConnections(List<ImageTile> tiles, List<String> definicoes) {

        // Extrair os tiles do tipo DoorWay e guardar num HashMap
        Map<Integer, DoorWay> doorsMap = new HashMap<>();
        for (ImageTile tile : tiles) {
            if (tile instanceof DoorWay) {
                doorsMap.put(((DoorWay) tile).getDoorNumber(), (DoorWay) tile);
            }
        }

        // A cada linha das definições corresponde uma porta nova
        // A Position dessa porta é dada pela DoorWay com o doorNumber correspondente
        for (String linha : definicoes) {
            String[] definicaoDePorta = linha.split(" ");

            int doorNumber = Integer.parseInt(definicaoDePorta[1]);
            String doorType = definicaoDePorta[2];
            String nextRoom = definicaoDePorta[3];
            int nextDoor = Integer.parseInt(definicaoDePorta[4]);
            String key = "";
            if (definicaoDePorta.length == 6) {
                key = definicaoDePorta[5];
            }

            Position posicao = doorsMap.get(doorNumber).getPosition();

            switch (doorType) {
                case "D":
                    tiles.add(new DoorClosed(posicao, doorNumber, nextRoom, nextDoor, key));
                    break;
                case "E":
                    tiles.add(new DoorOpen(posicao, doorNumber, nextRoom, nextDoor, key));
                    break;
            }
        }

    }


    public static void buildNewRoom(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = gui.getImages();

        String nextRoom = "";

        Hero hero = new Hero(new Position(0, 0));

        int nextDoor = 0;
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorWay) {
                nextRoom = ((DoorWay) tile).getNextRoom();
                nextDoor = ((DoorWay) tile).getNextDoor();
            }
            if (tile instanceof Hero) {
                hero = (Hero) tile;
            }
        }

        // Limpa a sala antiga
        gui.clearImages();
        List<ImageTile> newTiles = readRoomMap(nextRoom);

        // Vai buscar a posição da porta por onde o hero entrou
        Position newHeroPosition = null;
        for (ImageTile tile : newTiles) {
            if (tile instanceof DoorWay && ((DoorWay) tile).getDoorNumber() == nextDoor) {
                newHeroPosition = tile.getPosition();
                break;
            }
        }

        if (newHeroPosition != null) {
            hero.setPosition(newHeroPosition);
        }

        newTiles.add(hero);
        gui.newImages(newTiles);

    }


}
