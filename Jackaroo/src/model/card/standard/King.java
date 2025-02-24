package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class King extends Standard {

	public King(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 13, suit, boardManager, gameManager);
	}

}
