package model.card.standard;

import java.util.ArrayList;

import engine.Game;
import engine.GameManager;
import engine.board.BoardManager;
import model.Colour;
import model.player.Marble;
import model.player.Player;

public class Ace extends Standard {

	public Ace(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
		super(name, description, 1, suit, boardManager, gameManager);
	}
	
	public boolean validateMarbleSize(ArrayList<Marble> marbles) {
		return marbles.size() == 1;
	}
	
	public boolean validateMarbleColours(ArrayList<Marble> marbles) {
		for (int i = 0 ; i < marbles.size(); i++ ) {
			if (marbles.get(i).getColour() != Game.getActivePlayerColour() {
				return false;
			}
		}
		return true;
	}
}