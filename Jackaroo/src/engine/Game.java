package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import engine.board.Board;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
import model.player.Player;

public class Game implements GameManager {
	private final Board board;
	private final ArrayList<Player> players;
	private final ArrayList<Card> firePit;
	private int currentPlayerIndex;
	private int turn;
	
	public Game(String playerName) throws IOException {
		ArrayList<Colour> shuffledColours = new ArrayList<>();
        Collections.addAll(shuffledColours, Colour.values());
        Collections.shuffle(shuffledColours);
        this.board = new Board(shuffledColours, this);
        
        Deck.loadCardPool(this.board, this);
        
        this.players = new ArrayList<>();
        Player humanPlayer = new Player(playerName, shuffledColours.get(0));
        players.add(humanPlayer);
        
        for (int i = 1; i < 4; i++) {
			CPU cpuPlayer = new CPU("CPU " + i, shuffledColours.get(i), this.board);
			players.add(cpuPlayer);
		}
        
        for (Player player : players) {
            ArrayList<Card> playerCards = Deck.drawCards();
            player.setHand(playerCards);
        }
        
        this.currentPlayerIndex = 0;
        this.turn = 0;
        
        this.firePit = new ArrayList<>();
	}

	public Board getBoard() {
		return board;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getFirePit() {
		return firePit;
	}

}
