package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

public class Five extends Standard {
	public Five(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 5, suit, boardManager, gameManager);
	}

	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
		return true;
	}

	public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
		if (marbles.size() == 1) {
			boardManager.moveBy(marbles.get(0), 5, false);
		}
	}
}
