package model.card;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

public abstract class Card {
	private final String name;
	private final String description;
	protected BoardManager boardManager;
	protected GameManager gameManager;
	
	public Card(String name, String description, BoardManager boardManager, GameManager gameManager) {
		super();
		this.name = name;
		this.description = description;
		this.boardManager = boardManager;
		this.gameManager = gameManager;
	}

	public abstract boolean validateMarbleSize(ArrayList<Marble> marbles);

	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
		for (int i = 0 ; i < marbles.size(); i++ ) {
			if (marbles.get(i).getColour() != gameManager.getActivePlayerColour()) {
				return false;
			}
		}
		return true;
	}

	public abstract void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException;
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
