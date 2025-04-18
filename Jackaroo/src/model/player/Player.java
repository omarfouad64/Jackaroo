package model.player;

import java.util.ArrayList;

import exception.GameException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
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

	public void regainMarble(Marble marble) {
		marbles.add(marble);
	}

	Marble getOneMarble() {
		if (marbles.size() > 0) {
			return marbles.get(0);
		}
		return null;
	}

	public void selectCard(Card card) throws InvalidCardException {
		if (hand.contains(card)) {
			selectedCard = card;
		} else {
			throw new InvalidCardException("Card not in hand");
		}
	}

	public void selectMarble(Marble marble) throws InvalidMarbleException {
		if (selectedMarbles.size() < 2) {
			selectedMarbles.add(marble);
		} else {
			throw new InvalidMarbleException("No card selected or card has no size");
		}
	 }
	
	public void deselectAll() {
		selectedMarbles.clear();
		selectedCard = null;
	}

	void play() throws GameException {
		if (selectedCard == null) {
			throw new InvalidCardException("No card selected");
		}
		switch (selectedCard.getName()) {
			case "Ace":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for Ace card");
			}
			break;
			case "Five":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for Five card");
			}
			break;
			case "Four":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for Four card");
			}
			break;
			case "Jack":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for Jack card");
			}
			break;
			case "King":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for King card");
			}
			break;
			case "Queen":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for Queen card");
			}
			break;
			case "Seven":
			if (!selectedCard.validateMarbleSize(selectedMarbles)) {
				throw new InvalidMarbleException("Invalid marble size for Seven card");
			}
			break;
			case "Standard":
				if (!selectedCard.validateMarbleSize(selectedMarbles)) {
					throw new InvalidMarbleException("Invalid marble size for Standard card");
				}
				break;
			case "Ten":
				if (!selectedCard.validateMarbleSize(selectedMarbles)) {
					throw new InvalidMarbleException("Invalid marble size for Ten card");
				}
				break;
			default:
				throw new InvalidMarbleException("Invalid card type");
		}
		if (!selectedCard.validateMarbleColours(selectedMarbles)) {
			throw new InvalidMarbleException("Invalid marble colours for selected card");
		}
		selectedCard.act(selectedMarbles);
		deselectAll();
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
