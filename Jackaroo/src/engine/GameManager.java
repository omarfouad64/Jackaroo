package engine;

import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import model.Colour;
import model.player.Marble;

public interface GameManager {
	void sendHome(Marble marble);
	
	void fieldMarble() throws CannotFieldException, IllegalDestroyException;
	
	void discardCard(Colour colour) throws CannotDiscardException;
	
	void discardCard() throws CannotDiscardException;
	
	Colour getActivePlayerColour();
	
	Colour getNextPlayerColour();
}
