package model.player;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;

public class Player {
	private final String name;
	private final Colour colour;
	private ArrayList<Card> hand;
	private final ArrayList<Marble> marbles;
	private Card selectedCard;
	private final ArrayList<Marble> selectedMarbles;
	
	public Player(String name, Colour colour) {
		this.name = name;
		this.colour = colour;
		this.hand = new ArrayList<>();
		this.selectedMarbles = new ArrayList<>();
		this.marbles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.marbles.add(new Marble(colour));
        }
        this.selectedCard = null;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public String getName() {
		return name;
	}

	public Colour getColour() {
		return colour;
	}

	public ArrayList<Marble> getMarbles() {
		return marbles;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}
	
}
