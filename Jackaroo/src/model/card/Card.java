package model.card;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
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

	public boolean validateMarbleSize(ArrayList<Marble> marbles) {
		return false;
	}
	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
		return false;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}
