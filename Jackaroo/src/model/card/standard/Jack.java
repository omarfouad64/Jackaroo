package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class Jack extends Standard {
	public Jack(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 12, suit, boardManager, gameManager);
	}
}
