package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import engine.board.Board;
import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.GameException;
import exception.IllegalDestroyException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
import model.player.Marble;
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

	void selectCard(Card card) throws InvalidCardException {
		players.get(currentPlayerIndex).selectCard(card);
	}

	void selectMarble(Marble marble) throws InvalidMarbleException {
		players.get(currentPlayerIndex).selectMarble(marble);
	}

	void deselectAll() {
		players.get(currentPlayerIndex).deselectAll();
	}

	void editSplitDistance(int splitDistance) throws SplitOutOfRangeException {
		if (splitDistance < 1 || splitDistance > 6) {
			throw new SplitOutOfRangeException("Split distance out of range: " + splitDistance);
		}
		else {
			board.setSplitDistance(splitDistance);
		}
	}

	public boolean canPlayTurn() {
		
	}

	public void playPlayerTurn() throws GameException {

	}
	

	void endPlayerTurn() {
		firePit.add(players.get(currentPlayerIndex).getSelectedCard());
		for (int i = 0  ; i < players.get(currentPlayerIndex).getHand().size() ; i++) {
			if (players.get(currentPlayerIndex).getHand().get(i).equals(players.get(currentPlayerIndex).getSelectedCard())) {
				players.get(currentPlayerIndex).getHand().remove(i);
				break;
			}
		}
		players.get(currentPlayerIndex).deselectAll();
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		if (currentPlayerIndex == 0) {
    		turn++;
		}
		if (turn == 4) {
			turn = 0;
	
			for (Player player : players) {
				ArrayList<Card> newHand = Deck.drawCards();
				player.setHand(newHand);
			}
		}
		if (Deck.getPoolSize() < 4) {
			Deck.refillPool(firePit);
			firePit.clear();
		}
	}

	Colour checkWin() {
		for (int i = 0 ; i < 4 ; i++) {
			if (board.getSafeZones().get(i).isFull()) {
				return board.getSafeZones().get(i).getColour();
			}
		}
		return null;
	}

	public void sendHome(Marble marble) {
		players.get(currentPlayerIndex).regainMarble(marble);
	}

	public void fieldMarble() throws CannotFieldException, IllegalDestroyException {
		if (players.get(currentPlayerIndex).getMarbles().isEmpty()) {
			throw new CannotFieldException("No marbles available to field!");
		}
		if (players.get(currentPlayerIndex).getMarbles().get(0) == null) {
			throw new CannotFieldException("Selected marble is null!");
		}
		board.sendToBase(players.get(currentPlayerIndex).getMarbles().get(0));
		players.get(currentPlayerIndex).getMarbles().remove(players.get(currentPlayerIndex).getMarbles().get(0));
	}

	public void discardCard(Colour colour) throws CannotDiscardException {
		Random r = new Random();
		for (int i = 0 ; i < players.size() ; i++) {
			if (players.get(i).getColour() == colour) {
				if (players.get(i).getHand() == null) {
					throw new CannotDiscardException("No card selected!");
				}
				int randomIndex = r.nextInt(players.get(i).getHand().size());
				firePit.add(players.get(i).getHand().get(randomIndex));
				players.get(i).getHand().remove(randomIndex);
				break;
			}
		}
	}

	public void discardCard() throws CannotDiscardException {
		Random r = new Random();
		int randomPlayer = r.nextInt(players.size());
		while (randomPlayer == currentPlayerIndex) {
			randomPlayer = r.nextInt(players.size());
		}
		if (players.get(randomPlayer).getHand() == null) {
			throw new CannotDiscardException("No card selected!");
		}
		int randomIndex = r.nextInt(players.get(randomPlayer).getHand().size());
		firePit.add(players.get(randomPlayer).getHand().get(randomIndex));
		players.get(randomPlayer).getHand().remove(randomIndex);
	}

	public Colour getActivePlayerColour() {
		return players.get(currentPlayerIndex).getColour();
	}

	public Colour getNextPlayerColour() {
		int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
		return players.get(nextPlayerIndex).getColour();
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
