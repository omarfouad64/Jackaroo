package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import model.player.Marble;

public class Queen extends Standard {
	public Queen(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 12, suit, boardManager, gameManager);
	}
	
	public boolean validateMarbleSize(ArrayList<Marble> marbles) {
		return marbles.size() == 12;
	}
}
