package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

public class Ace extends Standard {
	public Ace(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 1, suit, boardManager, gameManager);
	}
	
	public boolean validateMarbleSize(ArrayList<Marble> marbles) {
		return marbles.size() == 0 || marbles.size() == 1;
	}
	
	public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
		if (marbles.size() == 0) {
			gameManager.fieldMarble();
		}
		if (marbles.size() == 1) {
			boardManager.moveBy(marbles.get(0), 1, false);
		}
	}
}