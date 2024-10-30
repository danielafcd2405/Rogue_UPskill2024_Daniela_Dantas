package pt.upskill.projeto1.gui;

import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.objects.items.*;
import pt.upskill.projeto1.objects.Hero;
import pt.upskill.projeto1.objects.enemies.BadGuy;
import pt.upskill.projeto1.objects.enemies.Bat;
import pt.upskill.projeto1.objects.enemies.Skeleton;
import pt.upskill.projeto1.objects.enemies.Thief;
import pt.upskill.projeto1.objects.passages.*;
import pt.upskill.projeto1.objects.stationary.*;
import pt.upskill.projeto1.objects.stationary.interactable.*;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dungeon {

    // Guarda todas as salas de acordo com o nome do ficheiro de configuração da sala correspondente
    private static Map<String, List<ImageTile>> dungeonMap = buildDungeon();
    private static String currentRoom;
    private static Map<String, List<ImageTile>> savedDungeonMap;
    private static String savedCurrentRoom;

    public static Map<String, List<ImageTile>> getDungeonMap() {
        return dungeonMap;
    }

    public static void setDungeonMap(Map<String, List<ImageTile>> dungeonMap) {
        Dungeon.dungeonMap = dungeonMap;
    }

    public static String getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(String currentRoom) {
        Dungeon.currentRoom = currentRoom;
    }

    public static Map<String, List<ImageTile>> getSavedDungeonMap() {
        return savedDungeonMap;
    }

    public static void setSavedDungeonMap(Map<String, List<ImageTile>> savedDungeonMap) {
        Dungeon.savedDungeonMap = savedDungeonMap;
    }

    public static String getSavedCurrentRoom() {
        return savedCurrentRoom;
    }

    public static void setSavedCurrentRoom(String savedCurrentRoom) {
        Dungeon.savedCurrentRoom = savedCurrentRoom;
    }

    // Lê todos os ficheiros da pasta rooms e para cada ficheiro, guarda uma lista de ImageTiles da sala
    // no mapa dungeonMap
    public static Map<String, List<ImageTile>> buildDungeon() {
        Map<String, List<ImageTile>> dungeonMap = new HashMap<>();

        File[] files = null;
        try {
            File f = new File("rooms");
            // Listar todos os nomes dos ficheiros na pasta rooms
            files = f.listFiles();
        }
        catch (Exception e) {
            System.out.println("Erro ao ler ficheiros");
        }

        System.out.println(files.length);

        for (File file : files) {
            System.out.println(file.getName());
            dungeonMap.put(file.getName(), readRoomMap(file.getName()));
        }

        return dungeonMap;

    }



    public static List<ImageTile> readRoomMap(String room) {

        // Lista para guardar as definições das portas
        List<String> linhasDefinicaoPortas = new ArrayList<>();
        // Lista para guardar as definições das chaves
        List<String> linhasDefinicaoChaves = new ArrayList<>();
        // Lista para guardar apenas as linhas que representam a sala
        List<String> linhasDeSala = new ArrayList<>();

        try {
            Scanner roomScanner = new Scanner(new File("rooms/" + room));
            while (roomScanner.hasNextLine()) {
                String linha = roomScanner.nextLine();
                if (linha.startsWith("# 0") || linha.startsWith("# 1") || linha.startsWith("# 2")) {
                    linhasDefinicaoPortas.add(linha);
                } else if (linha.startsWith("# k")) {
                    linhasDefinicaoChaves.add(linha);
                } else if (! linha.startsWith("#")) {
                    linhasDeSala.add(linha);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado.");;
        }


        // Nome da chave; Apenas deve existir uma única chave por sala
        String keyName = "";
        if ( ! linhasDefinicaoChaves.isEmpty()) {
            String[] linha = linhasDefinicaoChaves.get(0).split(" ");
            keyName = linha[2];
        }

        List<ImageTile> tiles = buildRoom(linhasDeSala, keyName);
        addRoomConnections(tiles, linhasDefinicaoPortas);
        addEnemies(tiles, linhasDeSala);

        return tiles;
    }


    private static List<ImageTile> buildRoom(List<String> linhasDeSala, String keyName) {
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
                    case "g":
                        tiles.add(new Grass(new Position(i, j)));
                        break;
                    case "s":
                        tiles.add(new Statue(new Position(i, j)));
                        break;
                    case "u":
                        tiles.add(new TrapSafe(new Position(i, j)));
                        tiles.add(new Trap(new Position(i, j), true));
                        break;
                    case "t":
                        tiles.add(new TrapTriggered(new Position(i, j)));
                        tiles.add(new Trap(new Position(i, j), false));
                        break;
                    case "d":
                        tiles.add(new Sword(new Position(i, j)));
                        break;
                    case "v":
                        tiles.add(new VoidLeft(new Position(i, j)));
                        break;
                    case "o":
                        tiles.add(new VoidRight(new Position(i, j)));
                        break;
                    case "p":
                        tiles.add(new Potion(new Position(i, j)));
                        break;
                    case "A":
                        tiles.add(new Pedestal(new Position(i, j)));
                        break;
                    case "O":
                        tiles.add(new Bookshelves(new Position(i, j)));
                        break;
                    case "P":
                        tiles.add(new SavePoint(new Position(i, j)));
                        break;
                    case "k":
                        tiles.add(new Key(new Position(i, j), keyName));
                        break;
                    case "f":
                        tiles.add(new Fire(new Position(i, j)));
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

    private static List<ImageTile> addEnemies(List<ImageTile> tiles, List<String> linhasDeSala) {
        for (int j = 0; j < 10; j++) {
            // y = j => j percorre as linhas
            String[] paineis = linhasDeSala.get(j).split("");
            for (int i = 0; i < 10; i++) {
                // x = i -> i percorre as colunas
                String painel = paineis[i];

                switch (painel) {
                    case "S":
                        tiles.add(new Skeleton(new Position(i, j)));
                        break;
                    case "B":
                        tiles.add(new Bat(new Position(i, j)));
                        break;
                    case "G":
                        tiles.add(new BadGuy(new Position(i, j)));
                        break;
                    case "T":
                        tiles.add(new Thief(new Position(i, j)));
                        break;
                }
            }
        }

        return tiles;
    }


    private static void addRoomConnections(List<ImageTile> tiles, List<String> definicoes) {

        // Extrair os tiles do tipo DoorWay e guardar num HashMap
        java.util.Map<Integer, DoorWay> doorsMap = new HashMap<>();
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
                    tiles.add(new DoorOpen(posicao, doorNumber, nextRoom, nextDoor));
                    tiles.add(new DoorClosed(posicao, doorNumber, nextRoom, nextDoor, key));
                    break;
                case "E":
                    tiles.add(new DoorOpen(posicao, doorNumber, nextRoom, nextDoor));
                    break;
                case "L":
                    tiles.add(new DoorOpen(posicao, doorNumber, nextRoom, nextDoor));
                    tiles.add(new DoorLocked(posicao, doorNumber, nextRoom, nextDoor, key));
                    break;
                case "w":
                    tiles.add(new StairsDown(posicao, doorNumber, nextRoom, nextDoor));
                    break;
                case "z":
                    tiles.add(new StairsUp(posicao, doorNumber, nextRoom, nextDoor));
                    break;
                case "e":
                    tiles.add(new DoorOpen(posicao, doorNumber, nextRoom, nextDoor));
                    tiles.add(new SecretPassage(posicao, doorNumber, nextRoom, nextDoor));
                    break;
            }
        }

    }


    public static void changeRoom(Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        List<ImageTile> tiles = Dungeon.getDungeonMap().get(Dungeon.getCurrentRoom());

        Hero hero = Engine.hero;
        String nextRoom = "";
        int nextDoor = 0;
        // Extrai a informação de configuração da porta por onde passou
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(position) && tile instanceof DoorWay) {
                nextRoom = ((DoorWay) tile).getNextRoom();
                nextDoor = ((DoorWay) tile).getNextDoor();
            }
        }

        // Limpa a sala antiga
        gui.clearImages();
        List<ImageTile> newTiles = dungeonMap.get(nextRoom);
        setCurrentRoom(nextRoom);

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
