package model.card.standard;

import java.util.ArrayList;

import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.card.Card;
import model.player.Marble;

public class Standard extends Card {
	private final int rank;
	private final Suit suit;
	
	public Standard(String name, String description, int rank, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, boardManager, gameManager);
		this.rank = rank;
		this.suit = suit;
	}
	
	public boolean validateMarbleSize(ArrayList<Marble> marbles) {
		return marbles.size() == 1;
	}

	public void act(ArrayList<Marble> marbles) throws ActionException, InvalidMarbleException {
		if (marbles.size() == 1) {
			boardManager.moveBy(marbles.get(0), rank, false);
		}
	}

	public int getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}
}
