package model.card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import engine.GameManager;
import engine.board.BoardManager;

public class Deck {
	private static final String CARDS_FILE = "Cards.csv";
	public static ArrayList<Card> cardsPool = new ArrayList<>();
	
	public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
		
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
