package model.player;

import java.util.ArrayList;
import java.util.Collections;

import engine.board.BoardManager;
import exception.GameException;
import model.Colour;
import model.card.Card;

public class CPU extends Player {
    
    private final BoardManager boardManager;

    public CPU(String name, Colour colour, BoardManager boardManager) {
        super(name, colour);
        this.boardManager = boardManager;
    }

    @Override
    public void play() throws GameException {
        // Retrieve a list of actionable marbles from the board manager.
        ArrayList<Marble> actionableMarbles = boardManager.getActionableMarbles();
        
        // Retrieve the current hand of cards and shuffle them to ensure randomness.
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(this.getHand());
        int initialHandSize = cards.size();
        Collections.shuffle(cards);
        
        // Iterate through each card in the shuffled hand.
        for (Card card : cards) {
            // Select the card to be played.
            this.selectCard(card);
            
            // Prepare a list to keep track of valid marble counts for the action.
            ArrayList<Integer> counts = new ArrayList<>();
            for(int i = 0; i < 3; i++) { // Check for 0 or 1 or 2 marbles to act upon.
                if(actionableMarbles.size() >= i) {
                    ArrayList<Marble> testMarbles = new ArrayList<>();
                    for(int j = 0; j < i; j++) {
                        testMarbles.add(actionableMarbles.get(j));
                    }
                    
                    // Validate the size of the marble group against the card's rules.
                    if(card.validateMarbleSize(testMarbles)) {
                        counts.add(i);
                    }
                }
            }
            
            // Shuffle the counts to randomize the selection process.
            Collections.shuffle(counts);
            for(int i = 0; i < counts.size(); i++) {   
                if(counts.get(i) == 0) {
                    try {
                        // Attempt to act with no marbles if the count is 0.
                        getSelectedCard().act(new ArrayList<>());
                        return; // Return after successful action.
                    }
                    catch(Exception e) {
                        // Ignore exceptions and continue trying other possibilities.
                    }
                }
                else if(counts.get(i) == 1) {
                    // Attempt to act with one marble.
                    ArrayList<Marble> toSend = new ArrayList<>();
                    Collections.shuffle(actionableMarbles); // Shuffle marbles for random selection.
                    for(Marble marble : actionableMarbles) {
                        toSend.add(marble);
                        if(card.validateMarbleColours(toSend)) {
                            try {
                                getSelectedCard().act(toSend);
                                return; // Return after successful action.
                            }
                            catch(Exception e) {
                                // Ignore exceptions and continue.
                            }
                        }
                        toSend.clear();
                    }
                }
                else {
                    // Attempt to act with two marbles.
                    ArrayList<Marble> toSend = new ArrayList<>();
                    Collections.shuffle(actionableMarbles);
                    for(int j = 0; j < actionableMarbles.size(); j++) {
                        for(int k = j+1; k < actionableMarbles.size(); k++) {
                            toSend.add(actionableMarbles.get(j));
                            toSend.add(actionableMarbles.get(k));
                            if(card.validateMarbleColours(toSend)) {
                                try {
                                    getSelectedCard().act(toSend);
                                    return; // Return after successful action.
                                }
                                catch(Exception e) {
                                    // Ignore exceptions and continue.
                                }
                            }
                            toSend.clear();
                        }
                    }
                }
            }
        }
        
        // If no cards were played, select the first card by default.
        if (cards.size() == initialHandSize)
            this.selectCard(this.getHand().get(0));
    }

}
