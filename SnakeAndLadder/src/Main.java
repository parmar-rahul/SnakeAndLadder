import model.Ladder;
import model.Player;
import model.Snake;
import service.SnakeAndLadderService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SnakeAndLadderService snakeAndLadderService = new SnakeAndLadderService(30);

        Scanner sc = new Scanner(System.in);
        int noOfSnakes = sc.nextInt();
        List<Snake> snakes = new ArrayList<Snake>();
        for(int i=0; i<noOfSnakes ; i++){
            snakes.add(new Snake(sc.nextInt(),sc.nextInt()));
        }

        int noOfLadders = sc.nextInt();
        List<Ladder> ladders= new ArrayList<Ladder>();
        for(int i=0; i < noOfLadders ; i++){
            ladders.add(new Ladder(sc.nextInt(),sc.nextInt()));
        }

        int noOfPlayers = sc.nextInt();
        List<Player> players = new ArrayList<Player>();
        for (int i=0; i< noOfPlayers; i++){
            players.add(new Player(sc.next()));
        }
        snakeAndLadderService.setLadders(ladders);
        snakeAndLadderService.setSnakes(snakes);
        snakeAndLadderService.setPlayers(players);

        snakeAndLadderService.startGame();
    }
}