package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

public class Jack extends Standard {
	public Jack(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 11, suit, boardManager, gameManager);
	}

	public boolean validateMarbleSize(ArrayList<Marble> marbles) {
		return marbles.size() == 1 || marbles.size() == 2;
	}
	
	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
		if (marbles.size() == 1) {
			return marbles.get(0).getColour() == gameManager.getActivePlayerColour();
		}
		else {
			return marbles.get(0).getColour() != marbles.get(1).getColour();
		}
	}

	public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
		if (marbles.size() == 1) {
			boardManager.moveBy(marbles.get(0), 11, false);
		}
		if (marbles.size() == 2) {
			boardManager.swap(marbles.get(0), marbles.get(1));
		}
	}
}
