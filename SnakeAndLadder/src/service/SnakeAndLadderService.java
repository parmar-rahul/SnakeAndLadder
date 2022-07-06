package service;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import model.Ladder;
import model.Player;
import model.Snake;
import model.SnakeAndLadderBoard;

import java.util.*;

public class SnakeAndLadderService {

    int boardSize = 30;
    Logger logger = LoggerFactory.getLogger(SnakeAndLadderService.class);

    public SnakeAndLadderService(int boardSize) {
        this.snakeAndLadderBoard = new SnakeAndLadderBoard(boardSize);  //Optional Rule 2
        this.players = new LinkedList<Player>();
    }

    /* ======= Initialize Board =========== */
    private SnakeAndLadderBoard snakeAndLadderBoard;
    private int initialNumberOfPlayers;
    private Queue<Player> players;

    public void setPlayers(List <Player> players) {
        this.players = new LinkedList<Player>();
        this.initialNumberOfPlayers = players.size();
        HashMap<String, Integer> playerPieces = new HashMap<String,Integer>();
        for(Player player : players){
            this.players.add(player);
            playerPieces.put(player.getId(), 0);  //Each Player has a piece which initially kept at 0 i.e outside the board
        }
        snakeAndLadderBoard.setPlayerPieces(playerPieces);
    }

    public void setSnakes(List<Snake> snakes){
        snakeAndLadderBoard.setSnakes(snakes);
    }

    public  void setLadders(List<Ladder> ladders) {
        //logger.error("Error in setLadders of Service");
        try {
            snakeAndLadderBoard.setLadders(ladders);
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }

    }

    /* ======= Core Business Logic for game =========== */
    private void movePlayer(Player player, int positions){
        int oldPosition = snakeAndLadderBoard.getPlayerPieces().get(player.getId());
        int newPosition = oldPosition + positions;
        if(newPosition > boardSize) {
            newPosition = oldPosition;
        }
        else {
            newPosition = getNewPositionAfterSnakeAndLadder(newPosition);
        }
        snakeAndLadderBoard.getPlayerPieces().put(player.getId(), newPosition);

        System.out.println(player.getName()+" rolled a "+ positions +" and moved from " + oldPosition + " to " + newPosition);
    }

    private int getNewPositionAfterSnakeAndLadder(int newPosition) {
        for(Snake snake : snakeAndLadderBoard.getSnakes()){
            if(snake.getStart() == newPosition){
                newPosition = snake.getEnd();
            }
        }
        for(Ladder ladder : snakeAndLadderBoard.getLadders()){
            if(ladder.getStart() == newPosition){
                newPosition = ladder.getEnd();
            }
        }
        return newPosition;
    }

    public void startGame(){
        while(!isGameCompleted()){
            int totalDiceValue = DiceService.roll();  //Each player rolls the dice when their turns come
            Player currentPlayer = players.poll();
            movePlayer(currentPlayer, totalDiceValue);
            if(hasPlayerWon(currentPlayer)){
                System.out.println(currentPlayer.getName() + " wins the game");
            }
            else{
                players.add(currentPlayer);
            }
        }
    }

    private boolean hasPlayerWon(Player currentPlayer) {
        int playerPosition = snakeAndLadderBoard.getPlayerPieces().get(currentPlayer.getId());
        int winningPosition = snakeAndLadderBoard.getSize();
        return (playerPosition == winningPosition);
    }

    private boolean isGameCompleted() {
        int currentNumberOfPlayers = players.size();
        return currentNumberOfPlayers < initialNumberOfPlayers;
    }
}
