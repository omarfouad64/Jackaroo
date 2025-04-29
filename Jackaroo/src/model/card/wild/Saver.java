package model.card.wild;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

public class Saver extends Wild {

	public Saver(String name, String description, BoardManager boardManager, GameManager gameManager) {
		super(name, description, boardManager, gameManager);
	}
	
	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
	    for (Marble marble : marbles) {
	        if (marble.getColour() != gameManager.getActivePlayerColour()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
		if (!boardManager.getActionableMarbles().contains(marbles.get(0))) {
			throw new InvalidMarbleException("ay 7aga");
		}
		boardManager.sendToSafe(marbles.get(0));
	}
}
