package model.card.wild;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

public class Burner extends Wild {

	public Burner(String name, String description, BoardManager boardManager, GameManager gameManager) {
		super(name, description, boardManager, gameManager);
	}
	
	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
	    for (Marble marble : marbles) {
	        if (marble.getColour() == gameManager.getActivePlayerColour()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
		
		boardManager.destroyMarble(marbles.get(0));
	}
}