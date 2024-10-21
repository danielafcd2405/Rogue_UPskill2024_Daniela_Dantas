package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.enemies.Skeleton;
import pt.upskill.projeto1.objects.stationary.DoorClosed;
import pt.upskill.projeto1.objects.stationary.Floor;
import pt.upskill.projeto1.objects.stationary.Wall;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {

    public List<ImageTile> readRoomMap(String room) {

        List<ImageTile> tiles = new ArrayList<>();

        // Adiciona Floor 10x10 à sala, de modo a garantir que os tiles do tipo Floor estão sempre por baixo
        // O resto dos tiles são adicionados por cima do Floor
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }

        try {
            Scanner roomScanner = new Scanner(new File("rooms/" + room));

            // Lista para guardar apenas as linhas que representam a sala
            // Ignora as linhas que começam com #
            List<String> linhasDeSala = new ArrayList<>();
            while (roomScanner.hasNextLine()) {
                String linha = roomScanner.nextLine();
                if (linha.startsWith("W")) {
                    linhasDeSala.add(linha);
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
                        case "0":
                            tiles.add(new DoorClosed(new Position(i, j)));
                            break;
                        case "S":
                            tiles.add(new Skeleton(new Position(i, j)));
                            break;
                        case "H":
                            tiles.add(new Hero(new Position(i, j)));
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado.");;
        }

        return tiles;
    }




}
