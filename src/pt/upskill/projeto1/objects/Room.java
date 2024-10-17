package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {

    public List<ImageTile> readRoomMap(String room) {

        List<ImageTile> tiles = new ArrayList<>();

        try {
            Scanner roomScanner = new Scanner(new File("rooms/" + room));
            while (roomScanner.hasNextLine()) {
                for (int j = 0; j < 10; j++) {
                    // y = j => j percorre cada linha do ficheiro room
                    String[] paineis = roomScanner.nextLine().split("");
                    for (int i = 0; i < 10; i++) {
                        // x = i -> i percorre cada coluna do ficheiro room
                        String painel = paineis[i];

                        switch (painel) {
                            case "W":
                                tiles.add(new Wall(new Position(i, j)));
                                break;
                            case " ":
                                tiles.add(new Floor(new Position(i, j)));
                                break;
                            case "0":
                                tiles.add(new DoorOpen(new Position(i, j)));
                                break;
                            default:
                                tiles.add(new Floor(new Position(i, j)));
                        }

                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado.");;
        }

        return tiles;

    }

}
