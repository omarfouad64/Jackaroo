package model.card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.standard.Ace;
import model.card.standard.Five;
import model.card.standard.Four;
import model.card.standard.Jack;
import model.card.standard.King;
import model.card.standard.Queen;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.standard.Ten;
import model.card.wild.Burner;
import model.card.wild.Saver;

public class Deck {
	private static final String CARDS_FILE = "Cards.csv";
	private static ArrayList<Card> cardsPool = new ArrayList<>();
	
	public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
		cardsPool = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",",-1);

	            int code = Integer.parseInt(data[0]);
	            int frequency = Integer.parseInt(data[1]);
	            String name = data[2];
	            String description = data[3];

	            Card card;

	            if (code == 14) {
	            	description += data[4] += data[5];
	            	System.out.println("Burner Description: " + description);
                    card = new Burner(name, description, boardManager, gameManager);
	          } else if (code == 15) {
	            	description += data[4] += data[5];
	        	    System.out.println("Saver Description: " + description);
                    card = new Saver(name, description, boardManager, gameManager);
                }
	            else {
	                int rank = Integer.parseInt(data[4]);
	                Suit suit = Suit.valueOf(data[5].toUpperCase());
	                switch (code) {
	                    case 1:
	                        card = new Ace(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 4:
	                        card = new Four(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 5:
	                        card = new Five(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 7:
	                        card = new Seven(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 10:
	                        card = new Ten(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 11:
	                        card = new Jack(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 12:
	                        card = new Queen(name, description, suit, boardManager, gameManager);
	                        break;
	                    case 13:
	                        card = new King(name, description, suit, boardManager, gameManager);
	                        break;
	                    default:
	                    	 card = new Standard(name, description, rank, suit, boardManager, gameManager);
	                    	 break;
	                }
	            } 

	            if (card != null) {
	                for (int i = 0; i < frequency; i++) {
	                    cardsPool.add(card);
	                }
	            }
	        }
	    }
	}

	
	public static ArrayList<Card> drawCards() {
        Collections.shuffle(cardsPool);
        ArrayList<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            drawnCards.add(cardsPool.remove(0));
        }
        return drawnCards;
    }
	
}
