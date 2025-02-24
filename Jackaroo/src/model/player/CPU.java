package model.player;

import engine.board.BoardManager;
import model.Colour;

public class CPU extends Player {
	private final BoardManager boardManager;

	public CPU(String name, Colour colour, BoardManager boardManager) {
		super(name, colour);
		this.boardManager = boardManager;
	}
	
}
