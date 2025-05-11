package model.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public Marble getOneMarble() {
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
	    if (selectedMarbles.size() < 2 && !selectedMarbles.contains(marble)) {
	        selectedMarbles.add(marble);
	    } else if (selectedMarbles.size() == 2 && !selectedMarbles.contains(marble)) {
	        throw new InvalidMarbleException("Cannot select more than 2 distinct marbles");
	    }
	}
	
	public void deselectAll() {
		selectedMarbles.clear();
		selectedCard = null;
	}

	public void play() throws GameException {
	    if (selectedCard == null) {
	        throw new InvalidCardException("No card selected");
	    }
	    String cardName = selectedCard.getName().trim().toLowerCase();
	    List<String> validCardNames = Arrays.asList(
	            "ace card", "five card", "four card", "jack card",
	            "king card", "queen card", "seven card", "standard", 
	            "ten card", "burner", "saver"
	        );

	    if (!validCardNames.contains(cardName)) {
	        throw new InvalidMarbleException("Invalid card type: " + selectedCard.getName());
	    }

	    if (!selectedCard.validateMarbleSize(selectedMarbles)) {
	        throw new InvalidMarbleException("Invalid marble size for " + selectedCard.getName());
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
