package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;

public class Seven extends Standard {
	public Seven(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 7, suit, boardManager, gameManager);
	}
}
