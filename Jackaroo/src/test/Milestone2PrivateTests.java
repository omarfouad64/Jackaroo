package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import java.util.Random;
import java.util.Set;

import org.junit.Test;


public class Milestone2PrivateTests {
	private String playerPath = "model.player.Player";
	private String CPUPath = "model.player.CPU";
	private String marblePath = "model.player.Marble";

	private String burnerPath = "model.card.wild.Burner";
	private String saverPath = "model.card.wild.Saver";
	private String wildPath = "model.card.wild.Wild";

	private String acePath = "model.card.standard.Ace";
	private String fivePath = "model.card.standard.Five";
	private String fourPath = "model.card.standard.Four";
	private String jackPath = "model.card.standard.Jack";
	private String kingPath = "model.card.standard.King";
	private String queenPath = "model.card.standard.Queen";
	private String sevenPath = "model.card.standard.Seven";
	private String standardPath = "model.card.standard.Standard";
	private String suitPath = "model.card.standard.Suit";
	private String tenPath = "model.card.standard.Ten";

	private String cardPath = "model.card.Card";
	private String deckPath = "model.card.Deck";

	private String colourPath = "model.Colour";

	private String ActionExceptionExceptionPath = "exception.ActionException";
	private String CannotDiscardExceptionExceptionPath = "exception.CannotDiscardException";
	private String CannotFieldExceptionExceptionPath = "exception.CannotFieldException";
	private String GameExceptionExceptionPath = "exception.GameException";
	private String IllegalDestroyExceptionExceptionPath = "exception.IllegalDestroyException";
	private String IllegalMovementExceptionExceptionPath = "exception.IllegalMovementException";
	private String IllegalSwapExceptionExceptionPath = "exception.IllegalSwapException";
	private String InvalidCardExceptionExceptionPath = "exception.InvalidCardException";
	private String InvalidMarbleExceptionExceptionPath = "exception.InvalidMarbleException";
	private String InvalidSelectionExceptionExceptionPath = "exception.InvalidSelectionException";
	private String SplitOutOfRangeExceptionExceptionPath = "exception.SplitOutOfRangeException";

	private String boardPath = "engine.board.Board";
	private String boardManagerPath = "engine.board.BoardManager";
	private String cellPath = "engine.board.Cell";
	private String safeZonePath = "engine.board.SafeZone";
	private String cellTypePath = "engine.board.CellType";

	private String gamePath = "engine.Game";
	private String gameManagerPath = "engine.GameManager";




	@Test(timeout=1000)
	public void testPlayPlayerFailNullCard(){
		Class<?> InvalidCardException = null;
		try {	

			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}

			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game,players);


			Class<?> marbleClass = Class.forName(marblePath);
			Class<?> colourClass = Class.forName(colourPath);

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			int positionInPath = (int) (Math.random() * 100);
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player, null);

			Field selectedMarbles = player_class.getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			ArrayList<Object> marbles = (ArrayList<Object>) selectedMarbles.get(player);
			marbles.add(marbleInstance);
			selectedMarbles.set(player, marbles);


			InvalidCardException = Class.forName(InvalidCardExceptionExceptionPath);

			Method playMethod=Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.setAccessible(true);
			playMethod.invoke(player);

			fail("Expected InvalidCardException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidCardException was not thrown",
					InvalidCardException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+"  occurred while accessing method play in class Player.");
		} catch (NoSuchFieldException e) {
			fail(e.getClass()+"  occurred while accessing method play in class Player.");

		}		
	}

	@Test(timeout=1000)
	public void testMoveBoardDestroyCell(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}
			Class<?> colourClass = Class.forName(colourPath);

			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));
			Object marbleInstance2 = marbleConstructor.newInstance(colours.get(1));
			Object marbleInstance3 = marbleConstructor.newInstance(colours.get(2));
			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Object cell_object = null;
			Object new_cell_object = null;

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			marbleField.set(track.get(0),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);




			boolean destroy = true;

			ArrayList<Object> path = new ArrayList<Object>();
			Object currentCell = track.get(0);
			Object cell = track.get(1);
			Object targetCell = track.get(2);
			path.add(currentCell);
			path.add(cell);
			path.add(targetCell);
			marbleField.set(track.get(1), marbleInstance2);
			marbleField.set(track.get(2),marbleInstance3);


			Method moveMethod= Class.forName(boardPath).getDeclaredMethod("move", Class.forName(marblePath) , ArrayList.class, boolean.class);
			moveMethod.setAccessible(true);
			moveMethod.invoke(board, marbleInstance ,path, destroy);

			trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			Object returnMarble = marbleField.get(trackreturnValue.get(1));


			assertEquals("move method in class board should handle marble destroying marble",null,returnMarble);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method move in class Board.");
		}
	}
	
	@Test(timeout=1000)
	public void testEndPlayerTurnGameNotRefillingHands2(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);
			Object player1Colour = playerColourField.get(player1);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);					

			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));

			Class safeZoneClass = Class.forName(safeZonePath);
			Field cellsField = safeZoneClass.getDeclaredField("cells");
			cellsField.setAccessible(true);
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");

			Class cellClass = Class.forName(cellPath);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			ArrayList<Object>  marbles = new ArrayList<Object>();
			for(int i=0;i<2;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(0);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<2;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);

			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player1, card);


			int currentPlayerIndex = 3;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex);

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 3);

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));


			Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
			String input_name = new Random().nextInt(10) +"card";
			Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card_instance ;
			ArrayList<Object> cardsFirePit = new ArrayList();
			for (int i=0; i< 20; i++) {
				card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);
				cardsFirePit.add(card_instance);
			}



			Field firepit_field = Class.forName(gamePath).getDeclaredField("firePit");
			firepit_field.setAccessible(true);
			firepit_field.set(game,cardsFirePit);

			ArrayList<Object> deck =  new ArrayList();

			int randomNumber = new Random().nextInt(97)+16;

			for(int i=0; i<randomNumber;i++){
				Object c = burnerConstructor.newInstance("Burner","description",board,game);
				deck.add(c);
			}

			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,deck );



			ArrayList<Object> playerHand = new ArrayList<Object>();
			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");

			hand_field.setAccessible(true);
			for(int i=0;i<players.size();i++)
			{
				Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
						Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
				Object card2 = saverConstructor.newInstance("Burner","description",board,game);

				playerHand = new ArrayList<Object>();
				playerHand.add(card);
				playerHand.add(card1);

				hand_field.set(players.get(i),playerHand);

			}



			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);

			ArrayList<Object> result = (ArrayList<Object>) cardsPool_field.get(null);
			
			int expected = randomNumber - 4 * (Math.min((randomNumber/4),4) );
			assertTrue("endPlayerTurn method in class game should not refill the Deck’s card pool with the cards in the firepit if the pool size was greater than or equal to 4. Expected size is: " + expected + " but was: " + result.size(), result.size() == expected );


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}


	@Test(timeout=1000)
	public void testEndPlayerTurnGameNotRefillingHands3(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);
			Object player1Colour = playerColourField.get(player1);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);					

			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));

			Class safeZoneClass = Class.forName(safeZonePath);
			Field cellsField = safeZoneClass.getDeclaredField("cells");
			cellsField.setAccessible(true);
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");

			Class cellClass = Class.forName(cellPath);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			ArrayList<Object>  marbles = new ArrayList<Object>();
			for(int i=0;i<2;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(0);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<2;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);

			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player1, card);


			int currentPlayerIndex = 3;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 3);

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));


			Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
			String input_name = new Random().nextInt(10) +"card";
			Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card_instance ;
			ArrayList<Object> cardsFirePit = new ArrayList();
			for (int i=0; i< 20; i++) {
				card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);
				cardsFirePit.add(card_instance);
			}



			Field firepit_field = Class.forName(gamePath).getDeclaredField("firePit");
			firepit_field.setAccessible(true);
			firepit_field.set(game,cardsFirePit);

			ArrayList<Object> deck =  new ArrayList();

			int randomNumber = (int)(Math.random() * (12)) + 4;

			for(int i=0; i<randomNumber;i++){
				Object c = burnerConstructor.newInstance("Burner","description",board,game);
				deck.add(c);
			}

			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,deck );



			ArrayList<Object> playerHand = new ArrayList<Object>();
			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");

			hand_field.setAccessible(true);
			for(int i=0;i<players.size();i++)
			{
				Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
						Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
				Object card2 = saverConstructor.newInstance("Burner","description",board,game);

				playerHand = new ArrayList<Object>();
				playerHand.add(card);
				playerHand.add(card1);

				hand_field.set(players.get(i),playerHand);

			}

			Object Current_player= players.get(currentPlayerIndex);
			Field selectedCardField= playerClass.getDeclaredField("selectedCard");
			selectedCardField.setAccessible(true);
			selectedCardField.set(Current_player, createAceCard(game, board));
			


			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);

			ArrayList<Object> result = (ArrayList<Object>) cardsPool_field.get(null);
			
			int expected = randomNumber + 21 - 4 * (Math.min((randomNumber+21/4),4) );
			assertTrue("endPlayerTurn method in class game should not refill the Deck’s card pool with the cards in the firepit if the pool size was greater than or equal to 4. Expected size is: " + expected + " but was: " + result.size(), result.size() == expected );


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}


	@Test(timeout = 1000)
	public void testValidateDestroyFailCaseSafeMarble() {

		Class<?> IllegalDestroyException = null;
		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}


			Class<?> colour_class = Class.forName(colourPath);
			Class<?> playerClass = Class.forName(playerPath);
			Field marblesField = playerClass.getDeclaredField("marbles");
			marblesField.setAccessible(true);
			marblesField.set(player, new ArrayList<Object>());



			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);


			Class<?> colourClass = Class.forName(colourPath);


			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);



			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			int positionInPath = 0;
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			IllegalDestroyException = Class.forName(IllegalDestroyExceptionExceptionPath);

			Method validateDestroymethod=Class.forName(boardPath).getDeclaredMethod("validateDestroy",  int.class);
			validateDestroymethod.setAccessible(true);
			validateDestroymethod.invoke(board,positionInPath);

			fail("Expected IllegalDestroyException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalDestroyException was not thrown",
					IllegalDestroyException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method validateDestroy in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurredwhile accessing method validateDestroy in class Board.");

		}
	}	

	@Test(timeout=1000)
	public void testgetActionableMarblesBoard(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> playerClass = Class.forName(playerPath);
			Field marblesField = playerClass.getDeclaredField("marbles");
			marblesField.setAccessible(true);
			marblesField.set(player, new ArrayList<Object>());


			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);


			Class<?> colourClass = Class.forName(colourPath);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);



			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			ArrayList <Object> safeZones = new ArrayList<>();

			Object marbleInstance1 = marbleConstructor.newInstance(colours.get(0));
			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
			Object safe_zone_instance = null;
			for (int i = 0; i < 4; i++)
			{
				safe_zone_instance = safe_zone_constructor.newInstance(colours.get(i));

				Class safeZoneClass = Class.forName(safeZonePath);
				Field f1 = safeZoneClass.getDeclaredField("cells");
				f1.setAccessible(true);
				Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");
				Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
				Object cell = cellConstructor.newInstance(cellType);
				Class cellClass = Class.forName(cellPath);
				ArrayList<Object> l = new ArrayList();
				Field marbleField1 = Class.forName(cellPath).getDeclaredField("marble");
				marbleField1.setAccessible(true);
				marbleField1.set(cell, marbleInstance1);
				l.add(cell);
				f1.set(safe_zone_instance, l);



				safeZones.add(safe_zone_instance);
			}
			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			safeZones_field.set(board, safeZones);


			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));

			ArrayList<Object> expectedMarbles = new ArrayList<>();
			ArrayList<Object> setMarbles = new ArrayList<>();

			int positionInPath = (int) (Math.random() * 100);
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			expectedMarbles.add(marbleInstance1);
			expectedMarbles.add(marbleInstance);

			setMarbles.add(marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game, players);

			Object playersInst = players_field.get(game);

			ArrayList<Object> p = (ArrayList<Object>)playersInst;


			marblesField.set(p.get(0), setMarbles);


			Method getActionableMarblesMethod =Class.forName(boardPath).getDeclaredMethod("getActionableMarbles");
			getActionableMarblesMethod.setAccessible(true);
			ArrayList<Object> returnMarbles = (ArrayList<Object>) getActionableMarblesMethod.invoke(board);



			if (expectedMarbles.size() != returnMarbles.size()) {
				fail("getActionableMarbles method should return all the marbles that are on the track as well as marbles in the Safe Zone of the current player: expected arraylist size is " + expectedMarbles.size() 
				+ " but was " + returnMarbles.size());
			}
			for (int i = 0; i < expectedMarbles.size(); i++) {
				Object expectedMarble = expectedMarbles.get(i);
				boolean found = false;
				for(int j=0;j<returnMarbles.size();j++){
					Object actualMarble = returnMarbles.get(j);
					if (expectedMarble.equals(actualMarble)) {
						found=true;
					}

				}
				if(!found)
					fail(""+expectedMarble + " is not found in the returned list of marbles." );

			}



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method getActionableMarbles in class Board.");
		}

	}

	@Test(timeout=1000)
	public void testMoveBoardNewLocation(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}


			Class<?> colourClass = Class.forName(colourPath);

			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Object cell_object = null;
			Object new_cell_object = null;

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			Field trap_field = Class.forName(cellPath).getDeclaredField("trap");
			trap_field.setAccessible(true);
			trap_field.set(track.get(2), false);

			marbleField.set(track.get(0),marbleInstance);


			track_field.setAccessible(true);


			Class<?> boardClass = Class.forName(boardPath);


			boolean destroy = false;

			ArrayList<Object> path = new ArrayList<Object>();
			Object currentCell = track.get(0);
			Object cell = track.get(1);
			Object targetCell = track.get(2);
			path.add(currentCell);
			path.add(cell);
			path.add(targetCell);

			marbleField.set(currentCell, marbleInstance);
			Object expectedMarble =  marbleField.get(track.get(0));

			Method moveMethod= Class.forName(boardPath).getDeclaredMethod("move", Class.forName(marblePath) , ArrayList.class, boolean.class);
			moveMethod.setAccessible(true);
			moveMethod.invoke(board, marbleInstance ,path, false);

			track =  (ArrayList<Object>) track_field.get(board);

			Object returnMarble = marbleField.get(track.get(2));

			assertEquals("move method in class board should set the new position of the marble in the track",expectedMarble,returnMarble);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method move in class Board.");
		}
	}

	@Test(timeout=1000)
	public void testPlayPlayerActBurner(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}


			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game,players);


			Class<?> marbleClass = Class.forName(marblePath);
			Class<?> colourClass = Class.forName(colourPath);

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(1));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);



			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			int positionInPath = 1;
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);



			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player, card);

			Field selectedMarbles = player_class.getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			ArrayList<Object> marbles = (ArrayList<Object>) selectedMarbles.get(player);
			marbles.add(marbleInstance);
			selectedMarbles.set(player, marbles);


			Method playMethod=Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.setAccessible(true);
			playMethod.invoke(player);

			Object m = marbleField.get(track.get(positionInPath));

			assertEquals("Play method in class Player should allow the selected card to act with the selected marbles.",null,m);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method play in class Player.");
		}

	}

	@Test(timeout=1000)
	public void testPlayPlayerTurnGameSaver(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}

			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game,players);


			Class<?> marbleClass = Class.forName(marblePath);
			Class<?> colourClass = Class.forName(colourPath);

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);

			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			int positionInPath = (int) (Math.random() * 100);
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);


			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			Constructor<?> cardConstructor = Class.forName(saverPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Saver","description",board,game);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player, card);

			Field selectedMarbles = player_class.getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			ArrayList<Object> marbles = (ArrayList<Object>) selectedMarbles.get(player);
			marbles.add(marbleInstance);
			selectedMarbles.set(player, marbles);


			Method playMethod=Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
			playMethod.setAccessible(true);
			playMethod.invoke(game);

			Object m = marbleField.get(track.get(positionInPath));	

			assertEquals("playPlayerTurn method in class Game should allow the current player to play their turn.",null,m);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method playPlayerTurn in class Game.");
		}

	}

	@Test(timeout=1000)
	public void testGetActivePlayerColourGame(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}

			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game,players);


			Class<?> marbleClass = Class.forName(marblePath);
			Class<?> colourClass = Class.forName(colourPath);

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			Method playMethod=Class.forName(gamePath).getDeclaredMethod("getActivePlayerColour");
			playMethod.setAccessible(true);
			Object returnedColour= playMethod.invoke(game);


			assertEquals("getActivePlayerColour method in class Game should return the colour of the current player.",colours.get(0),returnedColour);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method getActivePlayerColour in class Game. ");
		}

	}

	@Test(timeout=1000)
	public void testEndPlayerTurnGameSameTurn(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);
			Object player1Colour = playerColourField.get(player1);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);					

			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));

			Class safeZoneClass = Class.forName(safeZonePath);
			Field cellsField = safeZoneClass.getDeclaredField("cells");
			cellsField.setAccessible(true);
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");

			Class cellClass = Class.forName(cellPath);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			ArrayList<Object>  marbles = new ArrayList<Object>();
			for(int i=0;i<2;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(0);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<2;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);



			int currentPlayerIndex = 1;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 2);


			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);


			int newTurn = (int) turn_field.get(game);



			assertEquals("endPlayerTurn method in class game should not start a new turn except once all players have played a card and the play order is back to the the first player.",2,newTurn);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}

	@Test(timeout=1000)
	public void testEndPlayerTurnGameRemoveSelectedCardOnly(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);
			Object player1Colour = playerColourField.get(player1);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);					

			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));

			Class safeZoneClass = Class.forName(safeZonePath);
			Field cellsField = safeZoneClass.getDeclaredField("cells");
			cellsField.setAccessible(true);
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");

			Class cellClass = Class.forName(cellPath);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			ArrayList<Object>  marbles = new ArrayList<Object>();
			for(int i=0;i<2;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(0);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<2;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);

			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player1, card);


			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 1);

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));

			Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
			Object card2 = saverConstructor.newInstance("Burner","description",board,game);
			ArrayList<Object> playerHand = new ArrayList<Object>();
			playerHand.add(card);
			playerHand.add(card1);
			playerHand.add(card2);

			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");
			hand_field.setAccessible(true);
			hand_field.set(player1,playerHand);


			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);


			ArrayList<Object> newPlayerHand = (ArrayList<Object>) hand_field.get(player1);


			boolean found=true;
			for(int i=0;i<newPlayerHand.size();i++){
				found=true;
				for(int j=1;j<playerHand.size();j++){
					if(newPlayerHand.get(i)!=playerHand.get(j)){
						found=false;
					}

				}

			}



			assertEquals("endPlayerTurn method in class game should not remove any other card except the current player’s selected card from their hand",true,found);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}

	@Test(timeout=1000)
	public void testEndPlayerTurnGameNewCurrentPlayer(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);
			Object player1Colour = playerColourField.get(player1);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);					

			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));

			Class safeZoneClass = Class.forName(safeZonePath);
			Field cellsField = safeZoneClass.getDeclaredField("cells");
			cellsField.setAccessible(true);
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");

			Class cellClass = Class.forName(cellPath);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			ArrayList<Object>  marbles = new ArrayList<Object>();
			for(int i=0;i<2;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(0);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<2;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);

			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player1, card);


			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 1);

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));

			Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
			Object card2 = saverConstructor.newInstance("Burner","description",board,game);
			ArrayList<Object> playerHand = new ArrayList<Object>();
			playerHand.add(card);
			playerHand.add(card1);
			playerHand.add(card2);

			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");
			hand_field.setAccessible(true);
			hand_field.set(player1,playerHand);


			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);


			int newCurrentPlayer= (int) current_field.get(game);
			assertEquals("endPlayerTurn method in class game should move on the next player and setting them as the current player..",1,newCurrentPlayer);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}
	@Test(timeout=1000)

	public void testEndPlayerTurnGameNotRefillingDeck(){

		try {

			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);

			Class<?> card_class = Class.forName(cardPath);
			Class<?> game_manager_class = Class.forName(gameManagerPath);
			Class<?> board_manager_class = Class.forName(boardManagerPath);

			Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
			String input_name = new Random().nextInt(10) +"card";
			Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),board_manager_class,game_manager_class);

			int currentPlayerIndex = 1;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 3);

			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null, new ArrayList());


			Object card_instance ;
			ArrayList<Object> cardsFirePit = new ArrayList();
			for (int i=0; i< 20; i++) {
				card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);
				cardsFirePit.add(card_instance);
			}



			Field firepit_field = Class.forName(gamePath).getDeclaredField("firePit");
			firepit_field.setAccessible(true);
			firepit_field.set(game,cardsFirePit);



			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);


			ArrayList<Object> result = (ArrayList<Object>) cardsPool_field.get(null);


			assertTrue("endPlayerTurn method in class game should not refil the Deck’s card pool with the cards in the firepit except when the cards pool has fewer than 4 cards to draw. Expected size is: 0"
					+ " but was: " + result.size(), result.size() != 5);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred while accessing method endPlayerTurn in class Game." + 
					e.getMessage());
		}
	}
	///////


	@Test(timeout = 1000)
	public void testMoveIsCalledInMethodMoveByInBoardClass()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);

			//Cell2 that the marble will move to
			Object Cell2 = track.get(2);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell2);

			// Invoke MoveByMethod (with 2 steps)
			Method moveByMethod= Class.forName(boardPath).getDeclaredMethod("moveBy", Class.forName(marblePath) , int.class, boolean.class);
			moveByMethod.invoke(board_object, marble ,2, false);


			// Check position 0 is null
			assertEquals("moveBy method in class Board must call method move", getMarbleMethod.invoke(Cell0) ,null);


			// check position 2 now contains the marbleif it wasn't a trap
			if( !isTrap) 
				assertEquals("moveBy method in class Board must call method move with the correct marble", marble, getMarbleMethod.invoke(Cell2) );
		} 
		catch(Exception e){
			fail(e.getCause()+ " occured while accessing method moveBy in class board");
		}	
	}


	@Test(timeout = 1000)
	public void testPlayPlayerTurnMethodInClassGameWithCardQueen(){
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object activePlayerColour = getActiveColor(game);

			// Get the current player
			Object current_player = getActivePlayer(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(current_player, hand);

			// Select a card
			Field selected_card = Class.forName(playerPath).getDeclaredField("selectedCard");
			selected_card.setAccessible(true);
			selected_card.set(current_player, hand.get(0));

			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// added to Selected Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(current_player, marbles);

			// Add marbles to the track to cell 0
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);

			//Cell 12 that the marble will move to
			Object Cell12 = track.get(12);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell12);

			Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
			playPlayerTurn.invoke(game);

			// Check position 0 is null
			assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );

			// check new position now contains the marble if it wasn't a trap
			if(!isTrap) 
				assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell12) );
		}
		catch(Exception e){
			fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
		}
	}

	@Test(timeout = 1000)
	public void testPlayPlayerTurnMethodInClassGameWithCardFour(){
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object activePlayerColour = getActiveColor(game);

			// Get the current player
			Object current_player = getActivePlayer(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(current_player, hand);

			// Select a card
			Field selected_card = Class.forName(playerPath).getDeclaredField("selectedCard");
			selected_card.setAccessible(true);
			selected_card.set(current_player, hand.get(3));

			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// added to Selected Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(current_player, marbles);

			// Add marbles to the track to cell 5
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble);

			//Cell 1 that the marble will move to
			Object Cell1 = track.get(1);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell1);

			Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
			playPlayerTurn.invoke(game);

			// Check position 5 is null
			assertEquals("Method playPlayerTurn in class Game must call method play", null,  getMarbleMethod.invoke(Cell5));

			// check new position now contains the marble if it wasn't a trap
			if(!isTrap) 
				assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell1) );
		}
		catch(Exception e){
			fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
		}
	}

	// case 2.2: where there is a base cell in the path and has Green marble and
	// this base cell belong to Green
	@Test(timeout = 1000)
	public void testValidatePathCase2_2InBoardClass() {
		Class<?> IllegalMovementException = null;
		try {
			IllegalMovementException = Class
					.forName(IllegalMovementExceptionExceptionPath);
			Object game = createGame();

			Object board_object = createBoard(game);

			// create GREEN marble
			Object colour_Object = createColor("GREEN");
			Object marble = createMarble(colour_Object);

			// get the track field
			ArrayList<Object> track = getTrack(board_object);

			Object marble_1 = createMarbleForActivePlayer(game);

			// add normal cell to the path, so you make the path more realistic
			Object cell_type = createCellType("NORMAL");
			Object cell_0 = createCell(cell_type);

			// get base cell of green marble from the track
			int basePosition = getBasePosition(board_object, colour_Object);
			Object baseCell = track.get(basePosition);

			// set the base cell of the green to green marble
			Method setMarbleMethod = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));
			setMarbleMethod.setAccessible(true);
			setMarbleMethod.invoke(baseCell, marble);

			ArrayList<Object> path = new ArrayList<>();
			path.add(cell_0);
			path.add(baseCell);
			Method validatePath = Class.forName(boardPath).getDeclaredMethod(
					"validatePath", Class.forName(marblePath), ArrayList.class,
					boolean.class);
			validatePath.setAccessible(true);
			validatePath.invoke(board_object, marble_1, path, true);
			fail("The given path is invalid because it contains a base cell that holds its corresponding marble; therefore,an IllegalMovementException is expected to be thrown, but it is not.");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown",
					IllegalMovementException.isInstance(thrownException));
			try{
				assertTrue("You should assign exception message by passing to the exception constructor a valid message", (thrownException.getMessage()).length() > 0);
			}catch(NullPointerException e_1){
				fail("You should use the exception constructor that assigns a message to the exception.");
			}
		} catch (Exception e) {
			fail(e.getClass()
					+ " occurred while accessing method validatePath method in class Board.");
		}
	}

	// case 2.3: where there is a base cell in the path and has blue marble and
	// this base cell belong to blue
	@Test(timeout = 1000)
	public void testValidatePathCase2_3InBoardClass() {
		Class<?> IllegalMovementException = null;
		try {
			IllegalMovementException = Class
					.forName(IllegalMovementExceptionExceptionPath);
			Object game = createGame();

			Object board_object = createBoard(game);

			// create BLUE marble

			Object colour_Object = createColor("BLUE");
			Object marble = createMarble(colour_Object);

			// get the track field
			ArrayList<Object> track = getTrack(board_object);

			Object marble_1 = createMarbleForActivePlayer(game);

			// add normal cell to the path, so you make the path more realistic
			Object cell_type = createCellType("NORMAL");
			Object cell_0 = createCell(cell_type);

			// get base cell of green marble from the track
			int basePosition = getBasePosition(board_object, colour_Object);
			Object baseCell = track.get(basePosition);

			// set the base cell of the BLUE to BLUE marble
			Method setMarbleMethod = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));
			setMarbleMethod.setAccessible(true);
			setMarbleMethod.invoke(baseCell, marble);

			ArrayList<Object> path = new ArrayList<>();
			path.add(cell_0);
			path.add(baseCell);
			Method validatePath = Class.forName(boardPath).getDeclaredMethod(
					"validatePath", Class.forName(marblePath), ArrayList.class,
					boolean.class);
			validatePath.setAccessible(true);
			validatePath.invoke(board_object, marble_1, path, true);
			fail("The given path is invalid because it contains a base cell that holds its corresponding marble; therefore,an IllegalMovementException is expected to be thrown, but it is not.");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown",
					IllegalMovementException.isInstance(thrownException));
			try{
				assertTrue("You should assign exception message by passing to the exception constructor a valid message", (thrownException.getMessage()).length() > 0);
			}catch(NullPointerException e_1){
				fail("You should use the exception constructor that assigns a message to the exception.");
			}
		} catch (Exception e) {
			fail(e.getClass()
					+ " occurred while accessing method validatePath method in class Board.");
		}
	}

	// case 2.4: where there is a base cell in the path and has YELLOW marble
	// and this base cell belong to YELLOW
	@Test(timeout = 1000)
	public void testValidatePathCase2_4InBoardClass() {
		Class<?> IllegalMovementException = null;
		try {
			IllegalMovementException = Class
					.forName(IllegalMovementExceptionExceptionPath);
			Object game = createGame();

			Object board_object = createBoard(game);

			Object marble_1 = createMarbleForActivePlayer(game);

			// create YELLOW marble
			Object colour_Object = createColor("YELLOW");
			Object marble = createMarble(colour_Object);

			// get the track field
			ArrayList<Object> track = getTrack(board_object);

			// add normal cell to the path, so you make the path more realistic
			Object cell_type = createCellType("NORMAL");
			Object cell_0 = createCell(cell_type);

			// get base cell of yellow marble from the track
			int basePosition = getBasePosition(board_object, colour_Object);
			Object baseCell = track.get(basePosition);

			// set the base cell of the YELLOW to YELLOW marble
			Method setMarbleMethod = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));
			setMarbleMethod.setAccessible(true);
			setMarbleMethod.invoke(baseCell, marble);

			ArrayList<Object> path = new ArrayList<>();
			path.add(cell_0);
			path.add(baseCell);
			Method validatePath = Class.forName(boardPath).getDeclaredMethod(
					"validatePath", Class.forName(marblePath), ArrayList.class,
					boolean.class);
			validatePath.setAccessible(true);
			validatePath.invoke(board_object, marble_1, path, true);
			fail("The given path is invalid because it contains a base cell that holds its corresponding marble; therefore,an IllegalMovementException is expected to be thrown, but it is not.");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown",
					IllegalMovementException.isInstance(thrownException));
			try{
				assertTrue("You should assign exception message by passing to the exception constructor a valid message", (thrownException.getMessage()).length() > 0);
			}catch(NullPointerException e_1){
				fail("You should use the exception constructor that assigns a message to the exception.");
			}		} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}

	}

	// checking if the student include the condition of marble != null or not
	@Test(timeout = 1000)
	public void testValidatePathCheckingTheConditionOfMarbleNotNullInBoardClass() {
		try {
			Object game = createGame();
			Object board_object = createBoard(game);

			Object marble = createMarbleForActivePlayer(game);

			Object cell_type_0 = createCellType("NORMAL");
			Object normal_cell_0 = createCell(cell_type_0);
			Object normal_cell_1 = createCell(cell_type_0);

			Object cell_type = createCellType("SAFE");
			Object cell = createCell(cell_type);

			ArrayList<Object> path = new ArrayList<>();
			path.add(normal_cell_0);
			path.add(cell);
			path.add(normal_cell_1);
			Method validatePath = Class.forName(boardPath).getDeclaredMethod(
					"validatePath", Class.forName(marblePath), ArrayList.class,
					boolean.class);
			validatePath.setAccessible(true);
			validatePath.invoke(board_object, marble, path, true);
		} catch (InvocationTargetException e) {
			fail(" The path given shouldn't throw any exception but got: "
					+ e.getCause());
		} catch (Exception e) {
			fail(e.getClass()
					+ " occurred while accessing method validatePath method in class Board.");
		}

	}

	@Test(timeout = 1000)
	public void testValidatePathMehtodIsPrivateInBoardClass() {
		// Get the class
		try {
			Class<?> boardClass = Class.forName(boardPath);
			Method method = boardClass.getDeclaredMethod("validatePath",
					Class.forName(marblePath), ArrayList.class, boolean.class);
			method.setAccessible(true);
			assertTrue("Method ValidatePath in Class Board should be private ",
					Modifier.isPrivate(method.getModifiers()));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalArgumentException e) {

			fail(e.getClass()
					+ " occurred while accessing method validatePath method in class Board.");
		}
	}

	@Test(timeout = 1000)
	public void testValidateStepMehtodIsPrivateInBoardClass() {
		try {
			Class<?> boardClass = Class.forName(boardPath);
			Method method = boardClass.getDeclaredMethod("validateSteps",
					Class.forName(marblePath), int.class);
			method.setAccessible(true);
			assertTrue("Method validateStep in Class Board should be private ",
					Modifier.isPrivate(method.getModifiers()));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalArgumentException e) {

			fail(e.getClass()
					+ " occurred while accessing method validateStep method in class Board.");
		}
	}

	@Test(timeout = 1000)
	public void testMoveMehtodIsPrivateInBoardClass() {
		try {
			Class<?> boardClass = Class.forName(boardPath);
			Method method = boardClass.getDeclaredMethod("move",
					Class.forName(marblePath), ArrayList.class, boolean.class);
			method.setAccessible(true);
			assertTrue("Method move in Class Board should be private ",
					Modifier.isPrivate(method.getModifiers()));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalArgumentException e) {

			fail(e.getClass()
					+ " occurred while accessing method move method in class Board.");
		}
	}
	// checking if the student check only if the path containing entry cell
	// without checking if it is the last or not(giving here the path contain
	// entry cell and it's the last cell in the path)
	@Test(timeout = 1000)
	public void testValidatePathRemovingConditionNotLastCellFromCase4InBoardClass() {
		Class<?> IllegalMovementException = null;

		try {
			IllegalMovementException = Class
					.forName(IllegalMovementExceptionExceptionPath);

			Object game = createGame();
			Object board_object = createBoard(game);

			Object cell_type_1 = createCellType("ENTRY");
			Object cell_type_2 = createCellType("NORMAL");

			Object marble_1 = createMarbleForActivePlayer(game);
			// create opponent marble

			Object marble_for_entry_cell = createOpponentMarble(game);

			// add normal cell to the path, so you make the path more realistic
			Object cell_type_0 = createCellType("NORMAL");
			Object cell_0 = createCell(cell_type_0);

			// Create a cell
			Object entry_cell = createCell(cell_type_1);
			Method setMarble = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));
			setMarble.invoke(entry_cell, marble_for_entry_cell);

			ArrayList<Object> path = new ArrayList<>();
			path.add(cell_0);
			path.add(entry_cell);
			Method validatePath = Class.forName(boardPath).getDeclaredMethod(
					"validatePath", Class.forName(marblePath), ArrayList.class,
					boolean.class);
			validatePath.setAccessible(true);
			validatePath.invoke(board_object, marble_1, path, false);
		} catch (InvocationTargetException e) {
			fail("The path given shouldn't throw any exception but got:  "
					+ e.getCause());
		} catch (Exception e) {
			fail(e.getClass()
					+ " occurred while accessing method validatePath method in class Board.");
		}
	}

	@Test(timeout = 1000)
	public void testValidatePathIsCalledInMethodMoveByInBoardClassWithCorrectDestroyFlag_2() {

		try {

			Object game = createGame();
			Object board_object = createBoard(game);

			// Get the track of the board
			ArrayList<?> track = getTrack(board_object);

			// Get active player color

			Object activePlayerColour = getActiveColor(game);

			// Create a marble with active player color
			Object marble = createMarbleForActivePlayer(game);
			Object marble_2 = createMarbleForActivePlayer(game);
			Method setMarbleMethod = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));
			setMarbleMethod.setAccessible(true);

			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			Object Cell3 = track.get(3);
			setMarbleMethod.invoke(Cell3, marble_2);

			// Invoke MoveByMethod
			Method moveByMethod = Class.forName(boardPath).getDeclaredMethod(
					"moveBy", Class.forName(marblePath), int.class,
					boolean.class);
			moveByMethod.invoke(board_object, marble, 6, false);
			fail("moveBy method in class Board must call method ValidatePath with the correct destroy flag");
		} catch (InvocationTargetException e) {

			Throwable cause = e.getCause();

			if (cause.getClass().getName()
					.equals(IllegalMovementExceptionExceptionPath)) {
			} else {
				fail("moveBy method in class Board must call method ValidatePath");
			}
		} catch (Exception e) {
			fail(e.getClass()
					+ " occured while accessing method moveBy in class board");
		}
	}

	@Test(timeout = 1000)
	public void testValidatePathIsCalledInMethodMoveByInBoardClassWithCorrectDestroyFlag() {

		try {

			Object game = createGame();
			Object board_object = createBoard(game);

			// Get the track of the board
			ArrayList<?> track = getTrack(board_object);

			// Get active player color

			Object activePlayerColour = getActiveColor(game);

			// Create a marble with active player color
			Object marble = createMarbleForActivePlayer(game);
			Object marble_2 = createMarbleForActivePlayer(game);
			Method setMarbleMethod = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));
			setMarbleMethod.setAccessible(true);

			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			Object Cell3 = track.get(3);
			setMarbleMethod.invoke(Cell3, marble_2);

			// Invoke MoveByMethod
			Method moveByMethod = Class.forName(boardPath).getDeclaredMethod(
					"moveBy", Class.forName(marblePath), int.class,
					boolean.class);
			moveByMethod.invoke(board_object, marble, 6, true);
			// fail("moveBy method in class Board must call method ValidatePath");
		} catch (InvocationTargetException e) {

			Throwable cause = e.getCause();

			if (cause.getClass().getName()
					.equals(IllegalMovementExceptionExceptionPath)) {
				fail("moveBymethod in class Board must call method ValidatePath with the correct value for the destroy flag");
			} else {
				fail("moveBy method in class Board must call method ValidatePath");
			}
		} catch (Exception e) {
			fail(e.getClass()
					+ " occured while accessing method moveBy in class board");
		}
	}




	@Test(timeout=1000)
	public void testGetOneMarblePlayer(){

		Object player= createPlayer("RED");
		try {
			Field marbleField= Class.forName(playerPath).getDeclaredField("marbles");
			marbleField.setAccessible(true);
			Field selectedMarblesField= Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			ArrayList<Object> marbleList= (ArrayList) marbleField.get(player);
			ArrayList<Object> selectedMarbleList= (ArrayList) selectedMarblesField.get(player);

			Object firstMarble= marbleList.get(0);

			Method getOneMarbleMethod= Class.forName(playerPath).getDeclaredMethod("getOneMarble", null);
			getOneMarbleMethod.setAccessible(true);

			Object returnedMarble= getOneMarbleMethod.invoke(player, null);
			assertTrue("getOneMarble in class Player should return the first marble from the player's collection",firstMarble.equals(returnedMarble));



		} catch (NoSuchFieldException |ClassNotFoundException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" occured when accessing player attributes a new Player");
		}
		catch(InvocationTargetException | NoSuchMethodException e) {
			fail(e.getCause()+" occured when calling getOneMarble");

		}



	}
	@Test(timeout=1000)
	public void testAceActSendToBase(){
		Object game = createGame();
		try {
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);

			Field marblesField= Class.forName(playerPath).getDeclaredField("marbles");
			marblesField.setAccessible(true);

			ArrayList<Object> marblesList= (ArrayList<Object>)marblesField.get(currentPlayer);
			Object marbleObject= marblesList.get(0);

			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colourPath), playerColour);




			Field boadField= Class.forName(gamePath).getDeclaredField("board");
			boadField.setAccessible(true);
			Object board= boadField.get(game);

			((ArrayList<Object>) playersField.get(game)).set(currentPlayerIndex, currentPlayer);
			Object aceCard= createAceCard(game, board);

			Method getBasePosition= Class.forName(boardPath).getDeclaredMethod("getBasePosition", Class.forName(colourPath));
			getBasePosition.setAccessible(true);
			int position= (int) getBasePosition.invoke(board, color);


			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell, null);

			Field trackFiled= Class.forName(boardPath).getDeclaredField("track");
			trackFiled.setAccessible(true);
			((ArrayList)trackFiled.get(board)).set(position, cell);


			Method actMethod= Class.forName(acePath).getDeclaredMethod("act",ArrayList.class);
			actMethod.setAccessible(true);
			try{
				actMethod.invoke(aceCard, new ArrayList<>());
				trackFiled.setAccessible(true);
				Object cellUpdate=((ArrayList)trackFiled.get(board)).get(position);
				assertTrue("Check the game description for how the ACE card acts; fieldMarble Should set the base cell's marble and not create a new Cell", cell.equals(cellUpdate));

				marbleField= Class.forName(cellPath).getDeclaredField("marble");
				marbleField.setAccessible(true);
				Object marbleUpdated= marbleField.get(cellUpdate);
				assertTrue("Check the game description for how the ACE card acts; when the Ace act is called it should set the base cell's marble of the current player to the correct marble", marbleObject.equals(marbleUpdated));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when calling act on a Ace card with empty marbles input");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | NoSuchFieldException 
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}
	}

	@Test(timeout=1000)
	public void testKingActEmptyExceptionThrown(){

		Object game = createGame();

		try {
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object player= playersList.get(currentPlayerIndex);

			Field marbleField= Class.forName(playerPath).getDeclaredField("marbles");
			marbleField.setAccessible(true);
			Field selectedMarblesField= Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			marbleField.set(player, new ArrayList<>());

			((ArrayList) selectedMarblesField.get(player)).add(createMarble("GREEN"));
			((ArrayList) selectedMarblesField.get(player)).add(createMarble("GREEN"));
			ArrayList<Object> selectedMarbleListBefore= (ArrayList) selectedMarblesField.get(player);




			Field boadField= Class.forName(gamePath).getDeclaredField("board");
			boadField.setAccessible(true);
			Object board= boadField.get(game);

			((ArrayList<Object>) playersField.get(game)).set(currentPlayerIndex, player);
			Object kingCard= createKingCard(game, board);

			Method actMethod;
			try {
				actMethod = Class.forName(kingPath).getDeclaredMethod("act", ArrayList.class);
				actMethod.setAccessible(true);

				try {

					actMethod.invoke(kingCard, new ArrayList<>());
					fail("King's act should throw a CannotFieldException if the player's marble collection and selected marbles are empty.");
				}catch (Exception e) {

					if(!e.getCause().toString().contains("CannotFieldException"))
						fail("King's act should throw only a CannotFieldException if the player's marble collection and selected marbles are empty.");

				}

			} catch (NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				fail("Cannot access and call method Act on a king's Card");
			}





		} catch (NoSuchFieldException| ClassNotFoundException |IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing attributes, make sure the namings are correct and no typos");
		}



	}

	@Test(timeout=1000)
	public void testSendHomeGameMarbleOnlyAddedToCorrectPlayer(){

		Object game=null;
		game= createGame();

		Object marble=null;
		String[] colours={"GREEN", "RED", "YELLOW", "BLUE"};
		String colour= colours[(int) (Math.random()*4)];

		marble= createMarble(colour);


		try {
			Field marbelsField= Class.forName(playerPath).getDeclaredField("marbles");
			marbelsField.setAccessible(true);
			try {
				Field players= Class.forName(gamePath).getDeclaredField("players");
				players.setAccessible(true);
				Field colourFiled= Class.forName(playerPath).getDeclaredField("colour");
				colourFiled.setAccessible(true);
				Field selectedMarblesField= Class.forName(playerPath).getDeclaredField("selectedMarbles");
				selectedMarblesField.setAccessible(true);

				ArrayList<Object> playersList= (ArrayList<Object>) players.get(game);
				for (Object playerObj: playersList) {
					if(colourFiled.get(playerObj).toString().equals(colour))
					{
						ArrayList<Object> marbles= (ArrayList<Object>) marbelsField.get(playerObj);
						ArrayList<Object> selectedMarbles= (ArrayList<Object>) selectedMarblesField.get(playerObj);
						int sizeMarbles=marbles.size();
						int sizeSelectedMarbles=selectedMarbles.size();
						Method sendHomeMethod= Class.forName(gamePath).
								getDeclaredMethod("sendHome", Class.forName(marblePath));
						sendHomeMethod.invoke(game, marble);
						ArrayList<Object> marblesAfter= (ArrayList<Object>) marbelsField.get(playerObj);
						ArrayList<Object> selectedMarblesAfter= (ArrayList<Object>) selectedMarblesField.get(playerObj);
						assertTrue("sendHome should regain the given marble to the player's collection of marbles",
								marblesAfter.contains(marble));
						assertTrue("sendHome should regain the given marble to the player's collection of marbles,"
								+ " without changing the existing marbles in the collection",
								marblesAfter.size()==sizeMarbles+1);
						assertTrue("sendHome should NOT add the given marble to the player's collection of "
								+ "SELECTED marbles", !selectedMarblesAfter.contains(marble));
						assertTrue("sendHome should NOT add the given marble to the player's collection of "
								+ "SELECTED marbles", sizeSelectedMarbles==selectedMarblesAfter.size());
					}
					else{
						ArrayList<Object> marbles= (ArrayList<Object>) marbelsField.get(playerObj);
						ArrayList<Object> selectedMarbles= (ArrayList<Object>) selectedMarblesField.get(playerObj);
						int sizeMarbles=marbles.size();
						int sizeSelectedMarbles=selectedMarbles.size();
						Method sendHomeMethod= Class.forName(gamePath).
								getDeclaredMethod("sendHome", Class.forName(marblePath));
						sendHomeMethod.invoke(game, marble);
						ArrayList<Object> marblesAfter= (ArrayList<Object>) marbelsField.get(playerObj);
						ArrayList<Object> selectedMarblesAfter= (ArrayList<Object>) selectedMarblesField.get(playerObj);
						assertFalse("sendHome should only add the marble to the correct player",
								marblesAfter.contains(marble));
						assertTrue("sendHome should only add the marble to the correct player,"
								+ " without changing the existing marbles in the collection",
								marblesAfter.size()==sizeMarbles);
						assertTrue("sendHome should only add the marble to the correct player"
								, !selectedMarblesAfter.contains(marble));
						assertTrue("sendHome should only add the marble to the correct player "
								, sizeSelectedMarbles==selectedMarblesAfter.size());
					}
				}






			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				fail(e.getCause()+" occured when creating calling regainMarble in Player");
			}
		}
		catch (SecurityException
				| ClassNotFoundException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when getting the player from game");
		}
	}

	@Test(timeout=1000)
	public void testAceActFieldMarbleRemoveMarble(){
		Object game = createGame();
		try {
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);

			Field marblesField= Class.forName(playerPath).getDeclaredField("marbles");
			marblesField.setAccessible(true);

			ArrayList<Object> marblesList= (ArrayList<Object>)marblesField.get(currentPlayer);
			Object marbleObject= marblesList.get(0);

			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colourPath), playerColour);



			Field boadField= Class.forName(gamePath).getDeclaredField("board");
			boadField.setAccessible(true);
			Object board= boadField.get(game);

			((ArrayList<Object>) playersField.get(game)).set(currentPlayerIndex, currentPlayer);
			Object aceCard= createAceCard(game, board);

			Method getBasePosition;
			int position=0;
			try {
				getBasePosition = Class.forName(boardPath).getDeclaredMethod("getBasePosition", Class.forName(colourPath));
				getBasePosition.setAccessible(true);
				position= (int) getBasePosition.invoke(board, color);
			} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				fail(e1.getMessage()+" occurred when accessing and calling getBasePosition in Board ");
			}

			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell, null);

			Field trackFiled= Class.forName(boardPath).getDeclaredField("track");
			trackFiled.setAccessible(true);
			((ArrayList)trackFiled.get(board)).set(position, cell);


			Method actMethod= Class.forName(acePath).getDeclaredMethod("act",ArrayList.class);
			actMethod.setAccessible(true);
			try{
				actMethod.invoke(aceCard, new ArrayList<>());
				marblesList= (ArrayList<Object>)marblesField.get(currentPlayer);
				assertFalse("Check the game description for how the Ace card acts; fieldMarble should remove the first marble from that player’s Home Zone after sending it to base", marblesList.contains(marbleObject));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("Check the game description for how the Ace card acts; CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when a Ace card is acting");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | NoSuchFieldException
				| IllegalAccessException | IllegalArgumentException
				e) {

			fail(e.getMessage()+" occured when accessing an object attribute/Method, make sure there arent any typos with the attribute names");
		}
	}

	@Test(timeout=1000)
	public void testKingActFieldMarbleRemoveMarble(){
		Object game = createGame();
		try {
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);

			Field marblesField= Class.forName(playerPath).getDeclaredField("marbles");
			marblesField.setAccessible(true);

			ArrayList<Object> marblesList= (ArrayList<Object>)marblesField.get(currentPlayer);
			Object marbleObject= marblesList.get(0);

			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colourPath), playerColour);



			Field boadField= Class.forName(gamePath).getDeclaredField("board");
			boadField.setAccessible(true);
			Object board= boadField.get(game);

			((ArrayList<Object>) playersField.get(game)).set(currentPlayerIndex, currentPlayer);
			Object kingCard= createKingCard(game, board);

			Method getBasePosition;
			int position=0;
			try {
				getBasePosition = Class.forName(boardPath).getDeclaredMethod("getBasePosition", Class.forName(colourPath));
				getBasePosition.setAccessible(true);
				position= (int) getBasePosition.invoke(board, color);
			} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				fail(e1.getMessage()+" occurred when accessing and calling getBasePosition in Board ");
			}

			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell, null);

			Field trackFiled= Class.forName(boardPath).getDeclaredField("track");
			trackFiled.setAccessible(true);
			((ArrayList)trackFiled.get(board)).set(position, cell);


			Method actMethod= Class.forName(kingPath).getDeclaredMethod("act",ArrayList.class);
			actMethod.setAccessible(true);
			try{
				actMethod.invoke(kingCard, new ArrayList<>());
				marblesList= (ArrayList<Object>)marblesField.get(currentPlayer);
				assertFalse("Check the game description for how the king card acts; fieldMarble should remove the first marble from that player’s Home Zone after sending it to base", marblesList.contains(marbleObject));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("Check the game description for how the king card acts; CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when a king card is acting");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | NoSuchFieldException
				| IllegalAccessException | IllegalArgumentException
				e) {

			fail(e.getMessage()+" occured when accessing an object attribute/Method, make sure there arent any typos with the attribute names");
		}
	}


	@Test(timeout=1000)
	public void testDiscardCardColourInGameException() {
		Object game= createGame();
		try {
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList=(ArrayList<Object>) playersField.get(game);
			ArrayList<Object> colourOrder= new ArrayList<>();
			Field colourField = Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);

			for (Object player : playersList) {
				colourOrder.add(colourField.get(player));

			}
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentIndex=(int) (Math.random()*4);
			currentPlayerIndexField.set(game, currentIndex);
			int randIndex = (int) (Math.random() * 4);
			while(randIndex == currentIndex)
				randIndex = (int) (Math.random() * 4);
			Field handField= Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			handField.set(playersList.get(randIndex),new ArrayList<>() );


			try {

				Method discardCardMethod= Class.forName(gamePath).getDeclaredMethod("discardCard",Class.forName(colourPath));
				discardCardMethod.setAccessible(true);
				discardCardMethod.invoke(game, colourOrder.get(randIndex));
				fail("CannotDiscardException should be thrown when the player of the given colour to dicardCard has an empty hand!");


			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing discardCard method in Game. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				if(!e.getCause().toString().contains("CannotDiscardException"))
					fail("CannotDiscardException should be thrown when the player of the given colour to dicardCard has an empty hand!"+e.getCause()+" occurred when calling discardCard method on a Game object");
			}


		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}

	@Test(timeout=1000)
	public void testGetNextPlayerColourInGame1() {
		Object game= createGame();
		try {
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList=(ArrayList<Object>) playersField.get(game);
			ArrayList<Object> colourOrder= new ArrayList<>();
			Field colourField = Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);

			for (Object player : playersList) {
				colourOrder.add(colourField.get(player));

			}
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentIndex=(int) (Math.random()*3);
			currentPlayerIndexField.set(game, currentIndex);
			try {

				Method getNextPlayerColourMethod= Class.forName(gamePath).getDeclaredMethod("getNextPlayerColour",null);
				getNextPlayerColourMethod.setAccessible(true);
				Object returnedColour=getNextPlayerColourMethod.invoke(game, null);
				assertEquals("getNextPlayerColour should retrun the colour of the next player in the list, according to the correct turn.", colourOrder.get(currentIndex+1), returnedColour);
				assertEquals("The currentPlayerIndex should not be updated after calling getNextPlayerColour", currentIndex,(int) (currentPlayerIndexField.get(game)));

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing getNextPlayerColour method in Game. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				fail(e.getCause()+" occurred when calling getNextPlayerColour method on a Game object");
			}


		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}

	@Test(timeout=1000)
	public void testDiscardCardInGameDeclaration() {

		Method m;
		try {
			m = Class.forName(gamePath).getDeclaredMethod("discardCard",null);
			assertTrue("discardCard expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("discardCard should be void. ",void.class, returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing discardCard method declaration, make sure there are no typos");
		}
	}

	@Test(timeout=1000)
	public void testRegainMarbleInPlayerDeclaration() {

		Method m;
		try {
			m = Class.forName(playerPath).getDeclaredMethod("regainMarble",Class.forName(marblePath));
			assertTrue("regainMarble expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("regainMarble should be void. ",void.class, returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing regainMarble method declaration, make sure there are no typos");
		}
	}

	@Test(timeout=2000)
	public void testDiscardCardRandom() {
		Object game= createGame();
		try {
			Field players_field= Class.forName(gamePath).getDeclaredField("players");
			players_field.setAccessible(true);

			Field board_field=  Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);
			ArrayList<Object> playersList= (ArrayList<Object>) players_field.get(game);

			Field HandField= Class.forName(playerPath).getDeclaredField("hand");
			HandField.setAccessible(true);
			ArrayList<Object> hands= new ArrayList<>();
			hands.add(createAceCard(game, board));
			hands.add(createAceCard(game, board));
			hands.add(createAceCard(game, board));
			hands.add(createAceCard(game, board));
			hands.add(createKingCard(game, board));
			hands.add(createKingCard(game, board));
			hands.add(createKingCard(game, board));
			hands.add(createKingCard(game, board));
			hands.add(createFiveCard(game, board));
			hands.add(createFiveCard(game, board));
			hands.add(createFiveCard(game, board));
			hands.add(createFiveCard(game, board));
			hands.add(createFiveCard(game, board));
			hands.add(createFourCard(game, board));
			hands.add(createFourCard(game, board));
			hands.add(createFourCard(game, board));
			hands.add(createFourCard(game, board));
			hands.add(createFourCard(game, board));

			ArrayList<Object> hand1= new ArrayList<>();

			ArrayList<Object> hand2= new ArrayList<>();

			ArrayList<Object> hand3= new ArrayList<>();


			ArrayList<Object> hand4= new ArrayList<>();


			hand1.addAll(hands.subList(0, 4));
			hand2.addAll(hands.subList(4, 8));
			hand3.addAll(hands.subList(8, 12));
			hand4.addAll(hands.subList(12, 16));

			HashMap<Object, HashSet<ArrayList<Object>>> handsMap= new HashMap<>();
			HashSet<ArrayList<Object>> hashet1= new HashSet<>();
			hashet1.add(hand1);
			handsMap.put(playersList.get(0), hashet1);

			HashSet<ArrayList<Object>> hashet2= new HashSet<>();
			hashet2.add(hand2);
			handsMap.put(playersList.get(1), hashet2);

			HashSet<ArrayList<Object>> hashet3= new HashSet<>();
			hashet3.add(hand3);
			handsMap.put(playersList.get(2), hashet3);

			HashSet<ArrayList<Object>> hashet4= new HashSet<>();
			hashet4.add(hand4);
			handsMap.put(playersList.get(3), hashet4);


			try {
				Method discardCardMethod= Class.forName(gamePath).getDeclaredMethod("discardCard", null);
				discardCardMethod.setAccessible(true);
				for (int i = 0; i < 20; i++) {

					HandField.set(playersList.get(0), hand1);
					HandField.set(playersList.get(1), hand2);
					HandField.set(playersList.get(2), hand3);
					HandField.set(playersList.get(3), hand4);

					discardCardMethod.invoke(game, null);
					playersList= (ArrayList<Object>) players_field.get(game);
					for (int j = 0; j < playersList.size(); j++) {

						handsMap.get(playersList.get(j)).add((ArrayList<Object>) HandField.get(playersList.get(j)));

					}
					hand1=new ArrayList<>();
					hand2=new ArrayList<>();
					hand3=new ArrayList<>();
					hand4=new ArrayList<>();

					hand1.addAll(hands.subList(0, 4));
					hand2.addAll(hands.subList(4, 8));
					hand3.addAll(hands.subList(8, 12));
					hand4.addAll(hands.subList(12, 16));

				}

				assertTrue("discardCard should select a random player between all players to discard their card", 
						handsMap.get(playersList.get(1)).size()>1 &&
						handsMap.get(playersList.get(2)).size()>1 &&
						handsMap.get(playersList.get(3)).size()>1 );

			} catch (NoSuchMethodException |InvocationTargetException e) {
				// TODO Auto-generated catch block
				fail("Error "+e.getCause()+" occurred when accessing and calling discardCard");
			}


		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail("Error "+e.getMessage()+" occurred when accessing attributes of the game object");
		}


	}



	// Method Logic
	@Test(timeout = 1000)
	public void testGetSafeZoneMethodLogicInBoardClass() {


		// Set test environment
		Object game = constructGame();
		Object board = constructBoard(game); // Create board instance


		try {

			// Select a random Colour enum value
			String[] enumValues = {"YELLOW", "RED", "GREEN", "BLUE"};
			Object colour = returnEnumValue(colourPath, enumValues[new Random().nextInt(enumValues.length)]);

			// Get the private getSafeZone method
			Method getSafeZoneMethod = board.getClass().getDeclaredMethod("getSafeZone", Class.forName(colourPath));
			getSafeZoneMethod.setAccessible(true); // Required for private methods

			// Invoke the method
			Object result = getSafeZoneMethod.invoke(board, colour);
			ArrayList<?> cells = (ArrayList<?>) result;

			// Ensure it has exactly 4 elements
			assertEquals("getSafeZone should return an ArrayList<Cell> with 4 SAFE cells", 4, cells.size());

			// Check cell type
			Class<?> cellClass = Class.forName(cellPath);
			Method getCellTypeMethod = cellClass.getMethod("getCellType");


			Object safeEnumValue = returnEnumValue(cellTypePath, "SAFE"); // Expected enum value

			boolean isSafeZone = true;
			for (Object o : cells) {
				Object cellType = getCellTypeMethod.invoke(o); 
				if (!cellType.equals(safeEnumValue)) {
					isSafeZone = false;
					break;
				}
			}

			// Final assertion
			assertTrue("All cells should have the SAFE type", isSafeZone);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException  e) {
			e.printStackTrace();
			fail(e.getCause() + " occurred while testing getSafeZone method");
		}
	}


	@Test(timeout=1000)
	public void testGetSafeZoneMethodExistenceInBoardClass() {

		Class<?> board_class = null;
		Class<?> colour_class = null;

		try {
			board_class = Class.forName(boardPath);
		}

		catch (ClassNotFoundException e) {
			fail("Class Not Fount " +e.getMessage() );
		}


		try {
			colour_class = Class.forName(colourPath);
		}

		catch (ClassNotFoundException e) {	
			fail("Class not found: " + e.getMessage());
		}

		testMethodExistence(board_class, "getSafeZone", ArrayList.class,
				new Class[] { colour_class });

	}

	// Method Private
	@Test(timeout = 1000)
	public void testGetPositionInPathIsPrivateInClassBoard()  {
		Class<?> board_class = null;
		Class<?> marble_class = null;
		try {
			board_class = Class.forName(boardPath);
		}

		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}
		try {
			marble_class = Class.forName(marblePath);
		}

		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Marble");
		}
		Method m = null;
		try {
			m = board_class.getDeclaredMethod("getPositionInPath",new Class[]  { ArrayList.class,marble_class});
		} catch (NoSuchMethodException | SecurityException e) {
			fail(e.getClass() + " occurred when searching  getPositionInPath method, Error Message: " + 
					e.getMessage());
		}
		int modifiers = m.getModifiers();
		assertTrue("getPositionInPath method should be private",Modifier.isPrivate(modifiers));
	}

	@Test(timeout = 1000)
	public void testGetPositionInPathLowerBoundryBoardClass()  {


		// Set Test Environment
		Object game = constructGame(); 
		Object board = constructBoard(game);

		// Create a track 
		ArrayList<Object> track = new ArrayList<>();
		Class<?> cellClass = null;

		try {
			Class<?> cell_type_class = Class.forName(cellTypePath);
			cellClass = Class.forName(cellPath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);
			Object cell_object = null;


			for (int i = 0; i < 100; i++)
			{
				cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL"));
				track.add(cell_object);
				if (i % 25 == 0) {
					cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "BASE"));
					track.set(i, cell_object);
				}
				else if ((i+2) % 25 == 0) {
					cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY"));
					track.set(i, cell_object);
				}
			}
		}
		catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) 
		{
			fail(e.getCause() + "occured while craeting object of type Cell" );
		}


		// Create marble of any colour
		Class<?> marbleClass = null;
		Object marble = null;
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			marble = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));

		}
		catch(ClassNotFoundException | IllegalAccessException |
				InvocationTargetException | NoSuchMethodException | InstantiationException e) 
		{
			fail(e.getCause() + "occured while craeting object of type Marble" );
		}


		// Assign created marble to 0  
		int rand = 0;
		Object cell = track.get(rand);

		try {

			Field marbleField = cellClass.getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell,marble);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			fail(e.getCause() + "occured while setting cell marble" );

		}

		try {
			//Get Method getPositionInPath
			Method getPositionInPathMethod = board.getClass().getDeclaredMethod("getPositionInPath", new Class[]  { ArrayList.class,marbleClass} );
			getPositionInPathMethod.setAccessible(true); // Required for private methods

			// Invoke the method 
			int result = (int)getPositionInPathMethod.invoke(board, track,marble);



			// Final assertion
			assertTrue("Method should return index of the given marble " + 
					"Expected: "+rand + " But was: " + result,
					result == rand);


		} catch (  IllegalAccessException | NoSuchMethodException |
				InvocationTargetException  e) {
			fail( e.getCause() + "Error occurred while testing getPositionInPath method.");
		}
	}



	// Method Existence
	@Test(timeout=1000)
	public void testGetBasePositionMethodExistenceInBoardClass() {
		Class<?> board_class;
		try {
			board_class = Class.forName(boardPath);
		}

		catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}

		try {
			testMethodExistence(Class.forName(boardPath), "getBasePosition", int.class,
					new Class[] { Class.forName(colourPath) });
		} 
		catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	// Method Private
	@Test(timeout = 1000)
	public void testGetBasePositionIsPrivateInClassBoard()  {
		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}

		Method m = null;
		try {
			m = board_class.getDeclaredMethod("getBasePosition",new Class[] { Class.forName(colourPath)});
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred when searching  getBasePosition method, Error Message: " + 
					e.getMessage());		}
		int modifiers = m.getModifiers();
		assertTrue("getBasePosition method should be private",Modifier.isPrivate(modifiers));
	}

	@Test(timeout=1000)
	public void testGetEntryPositionMethodExistenceInBoardClass() {
		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}

		Method m = null;
		try {
			testMethodExistence(Class.forName(boardPath), "getEntryPosition", int.class,
					new Class[] { Class.forName(colourPath) });
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}
	}


	// case marble is away from safe zone and moving along the track. Position on track is 2 and steps are 3

	@Test(timeout = 1000)
	public void testValidateStepsMethodReturnOnTrackOnlyInBoardClass() {

		// Set test environment
		Object game = constructGame(); // Create game instance

		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}



		Object colour = null;
		try {

			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			int index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current player colour 
		Class<?> marbleClass;
		Object marble = null; 
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			marble = marbleConstructor.newInstance(colour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}



		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);

			//Set marble
			Field marbleField = track.get(2).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(2), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track attribute, Error Message: " + 
					e.getMessage());
		}




		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 
			// Invoke the method
			int steps = 6;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			// Ensure the result is the correct cells size
			assertTrue("validateSteps should return an arraylist of size  "
					+ "Expected:  "+ (steps+1) + " but was: " + result.size() ,result.size()== steps+1);


		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());

		}
		}


	}	

	@Test(timeout = 1000)

	public void testGetValidateStepsMethodAfterEntryOnTrackInBoardClass() {


		Class<?> IllegalMovementException = null;


		try {


			IllegalMovementException = Class.forName(IllegalMovementExceptionExceptionPath);


		} catch (ClassNotFoundException e) {


			fail(e.getClass() + " occurred while creating object of type IllegalMovementException");



		}


		// Set test environment


		Object game = constructGame(); // Create game instance

		ArrayList<Object> colours = new ArrayList<Object>();


		try {


			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");


			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");


			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");


			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");


			colours.add(colour1);


			colours.add(colour2);


			colours.add(colour3);


			colours.add(colour4);


		} catch (ClassNotFoundException e) {


			fail(e.getClass() + " occurred while creating object of type Colour");


		}

		Object board = null ;


		Constructor<?> boardConstructor;

		try {


			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));


			board = boardConstructor.newInstance(colours,game);


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 


				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {


			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 


					e.getMessage());


		}	


		catch (InvocationTargetException e) {


			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 


						e.getMessage());


		}


		Object colour = null;


		try {


			// get cuurent player colour


			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");


			currentPlayerIndexField.setAccessible(true); ;


			int index =  (int) currentPlayerIndexField.get(game);



			// get  players


			Field playersField = game.getClass().getDeclaredField("players");


			playersField.setAccessible(true); ;


			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);


			// get colour


			Field colourField = players.get(index).getClass().getDeclaredField("colour");


			colourField.setAccessible(true);


			colour = colourField.get(players.get(index));



		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {


			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 


					e.getMessage());


		}



		// Create marble of current player colour 


		Class<?> marbleClass;


		Object marble = null; 


		try {


			marbleClass = Class.forName(marblePath);


			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));


			marble = marbleConstructor.newInstance(colour);


		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {


			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 


					e.getMessage());


		}


		try {


			// GetTrack


			Field trackField = board.getClass().getDeclaredField("track");


			trackField.setAccessible(true);


			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);





			// get safeZones


			Field safeZonesField = board.getClass().getDeclaredField("safeZones");


			safeZonesField.setAccessible(true);


			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour


			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");


			colourField.setAccessible(true);

			int entryPosition = 0;


			for(int i=0; i<safeZones.size();i++)


				if (colour == colourField.get(safeZones.get(i)) )


					entryPosition = (i*25 - 2 + 100) % 100; ;


					//Set marble


					Field marbleField = track.get(entryPosition - 2).getClass().getDeclaredField("marble");


					marbleField.setAccessible(true);


					marbleField.set(track.get(entryPosition  + 1 ), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {


			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 


					e.getMessage());}


		try {


			// Get the private validateSteps Method 


			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});


			validateStepsMethod.setAccessible(true); 


			// Invoke the method


			int steps = 7;


			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			// Ensure the result is the correct cells size


			assertTrue("validateSteps should return an arraylist of size  "


			+ "Expected:  "+ (steps+1) + " but was: " + result.size() ,result.size()== steps+1);


		} catch (NoSuchMethodException | SecurityException


				| ClassNotFoundException | IllegalAccessException e) {





			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 


					e.getMessage());


		}


		catch(InvocationTargetException e){{


			fail(e.getCause() + " occurred  while testing validateSteps method, current player marble is ahead of entry with 1 cell  Error Message: " + 


					e.getMessage());


		}


		}




	}


	@Test(timeout = 1000)
	public void testValidateStepsMethodReturn1SafeZonesCellsInBoardClass() {

		// Set test environment
		Object game = constructGame(); // Create game instance
		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}

		int index = 1000;
		Object colour = null;
		try {
			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current player colour 
		Class<?> marbleClass;
		Object marble = null; 
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			marble = marbleConstructor.newInstance(colour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}



		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);

			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);


			Object currentPlayerSafezone = null ; 
			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					currentPlayerSafezone = safeZones.get(i) ;

			//Set marble to cells inside safeZone
			Field cellsField = currentPlayerSafezone.getClass().getDeclaredField("cells");
			cellsField.setAccessible(true);
			ArrayList <Object> cells =( ArrayList<Object>) cellsField.get(currentPlayerSafezone);

			Field marbleField = cells.get(0).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cells.get(0), marble);


		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		int steps = 0;


		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 


			// Invoke the method

			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);
			// Ensure the result is the correct cells size
			assertTrue("validateSteps should return an arraylist of size  "
					+ "Expected:  "+ (steps+1) + " but was: " + result.size() ,result.size()== steps+1);


			// Get result cellType 
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int firstIndex = 0;
			Object cellType =  cellTypeField.get(result.get(firstIndex));




			assertTrue("Player marble should be inside safezone "   , cellType.equals(returnEnumValue(cellTypePath,"SAFE")));



		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method moving "+ steps + " steps from safezone cell index 0, should return an arraylist of size "+ (steps+1) +  " ,Error Message: " + 
					e.getMessage());

		}
		}





	}

	@Test(timeout=1000)
	public void testGetPoolSizeExistenceInDeckClass() {
		try {
			testMethodExistence(Class.forName(deckPath), "getPoolSize", int.class,
					new Class[] { });
		} catch (ClassNotFoundException e) {

			fail("Class not found: " + e.getMessage());
		}
	}


	@Test(timeout = 1000)
	public void testGetPoolSizeLogicInSafeDeckClass() {
		try {
			// Set test environment
			Object game = constructGame(); // Create game instance
			Object board = constructBoard(game); // Create board instance

			Class<?> card_class = Class.forName(cardPath);
			Class<?> game_manager_class = Class.forName(gameManagerPath);
			Class<?> board_manager_class = Class.forName(boardManagerPath);

			Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
			String input_name = new Random().nextInt(10) +"card";
			Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),board_manager_class,game_manager_class);
			int randSize = new Random().nextInt(15);

			Object card_instance ;
			ArrayList<Object> cards = new ArrayList();
			for (int i=0; i< randSize; i++) {
				card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);
				cards.add(card_instance);
			}

			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null, cards);



			Method getPoolSizeMethod =  Class.forName(deckPath).getDeclaredMethod("getPoolSize",new Class[] { });
			int result = (int)getPoolSizeMethod.invoke(null);

			assertTrue("getPoolSize expected return " + randSize + " but was " + result, result == randSize);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred  while testing getPoolSize method, Error Message: " + 
					e.getMessage());
		}

	}

	@Test(timeout = 1000)
	public void testRefillPoolIsPublicInSafeDeckClass() {
		Class<?> deck_class = null;
		try {
			deck_class = Class.forName(deckPath);
		} catch (ClassNotFoundException e) {
			fail("class not found:  " + e.getMessage());

		}
		Method m = null;
		try {
			m = deck_class.getDeclaredMethod("refillPool",new Class[] {ArrayList.class });
		} catch (NoSuchMethodException | SecurityException e) {
			fail(e.getClass() + " occurred when searching  refillPool method, Error Message: " + 
					e.getMessage());
		}
		int modifiers = m.getModifiers();
		assertTrue("refillPool method should be public",Modifier.isPublic(modifiers));
	}

	@Test(timeout = 1000)
	public void testValidateStepsMethodReturn3SafeZonesCellsInBoardClass() {

		// Set test environment
		Object game = constructGame(); // Create game instance
		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}

		int index = 1000;
		Object colour = null;
		try {
			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current player colour 
		Class<?> marbleClass;
		Object marble = null; 
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			marble = marbleConstructor.newInstance(colour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}



		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);

			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);


			Object currentPlayerSafezone = null ; 
			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					currentPlayerSafezone = safeZones.get(i) ;

			//Set marble to cells inside safeZone
			Field cellsField = currentPlayerSafezone.getClass().getDeclaredField("cells");
			cellsField.setAccessible(true);
			ArrayList <Object> cells =( ArrayList<Object>) cellsField.get(currentPlayerSafezone);

			Field marbleField = cells.get(0).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cells.get(0), marble);


		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		int steps = 2;


		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 


			// Invoke the method

			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);
			// Ensure the result is the correct cells size
			assertTrue("validateSteps should return an arraylist of size  "
					+ "Expected:  "+ (steps+1) + " but was: " + result.size() ,result.size()== steps+1);


			// Get result cellType 
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int firstIndex = 0;
			Object cellType =  cellTypeField.get(result.get(firstIndex));




			assertTrue("Player marble should be inside safezone "   , cellType.equals(returnEnumValue(cellTypePath,"SAFE")));



		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method moving 2 steps from safezone cell index 0, should return an arraylist of size "+ (steps+1) +  " ,Error Message: " + 
					e.getMessage());

		}
		}
	}


	@Test(timeout = 1000)

	public void testValidateStepsMethodNegativeStepsInSafeZoneExceptionInBoardClass() {
		Class<?> IllegalMovementException = null;

		try {
			IllegalMovementException = Class.forName(IllegalMovementExceptionExceptionPath);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type IllegalMovementException");

		}
		// Set test environment
		Object game = constructGame(); // Create game instance

		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}

		int index = 1000;
		Object colour = null;
		try {
			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current player colour 
		Class<?> marbleClass;
		Object marble = null; 
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			marble = marbleConstructor.newInstance(colour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}



		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);

			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);


			Object currentPlayerSafezone = null ; 
			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					currentPlayerSafezone = safeZones.get(i) ;

			//Set marble to cells inside safeZone
			Field cellsField = currentPlayerSafezone.getClass().getDeclaredField("cells");
			cellsField.setAccessible(true);
			ArrayList <Object> cells =( ArrayList<Object>) cellsField.get(currentPlayerSafezone);

			Field marbleField = cells.get(0).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cells.get(0), marble);


		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}





		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 


			// Invoke the method

			int steps = -2;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			fail("Expected IllegalMovementException was not thrown when when negative steps is used inside safeZone");


		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}







		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown when negative steps are used inside safeZone" ,
					IllegalMovementException.isInstance(thrownException));
			assertTrue("Expected IllegalMovementException should have a message",
					thrownException.getMessage()!= null);
		}

	}



	@Test(timeout = 1000)
	public void testValidateSwapIsPrivateInClassBoard() {

		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}	
		Method m = null;
		try {
			m = board_class.getDeclaredMethod("validateSwap",new Class[] { Class.forName(marblePath), Class.forName(marblePath)});
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred while searching validateSwap method, Error Message: " + 
					e.getMessage());
		}
		int modifiers = m.getModifiers();
		assertTrue("validateSwap method should be private",Modifier.isPrivate(modifiers));
	}

	@Test(timeout = 1000)
	public void testValidateSwapMethodMarble1NotOnTrackInBoardClass() {
		Class<?> IllegalSwapExceptionException = null;

		// Set test environment
		Object game = constructGame(); // Create game instance

		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			IllegalSwapExceptionException = Class.forName(IllegalSwapExceptionExceptionPath);
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}


		Object colour = null;
		try {
			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			int index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current other colour 
		Class<?> marbleClass;
		Object marbleOther = null; 
		Object marbleActive = null; 

		Object otherColour = null;
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));

			for (Object col: colours){
				if (!col.equals(colour)) {
					otherColour = col;	
					break;}
			}
			marbleOther = marbleConstructor.newInstance(otherColour);
			marbleActive = marbleConstructor.newInstance(colour);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}


		int pose2 = 77;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			//Set marbles
			marbleField = track.get(pose2).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose2), marbleOther);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}


		try {
			// Get the private  method
			Method validateSwapMethod = board.getClass().getDeclaredMethod("validateSwap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			validateSwapMethod.setAccessible(true); 


			// Invoke the method
			validateSwapMethod.invoke(board,marbleActive, marbleOther);

			fail("Expected IllegalSwapException was not thrown, marble1 is not on track");


		}




		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalSwapException was not thrown, but was " + e.getCause() ,
					IllegalSwapExceptionException.isInstance(thrownException));
		}

		catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException  e) {
			fail(e.getClass() + " occurred while testing validateSwap method, Error Message: " + 
					e.getMessage());	
		}
	}

	@Test(timeout = 1000)
	public void testValidateStepsMethodReturn4SafeZonesCellsInBoardClass() {

		// Set test environment
		Object game = constructGame(); // Create game instance
		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}

		int index = 1000;
		Object colour = null;
		try {
			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current player colour 
		Class<?> marbleClass;
		Object marble = null; 
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			marble = marbleConstructor.newInstance(colour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}



		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);

			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);


			Object currentPlayerSafezone = null ; 
			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					currentPlayerSafezone = safeZones.get(i) ;

			//Set marble to cells inside safeZone
			Field cellsField = currentPlayerSafezone.getClass().getDeclaredField("cells");
			cellsField.setAccessible(true);
			ArrayList <Object> cells =( ArrayList<Object>) cellsField.get(currentPlayerSafezone);

			Field marbleField = cells.get(0).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cells.get(0), marble);


		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		int steps = 3;


		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 


			// Invoke the method
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);
			// Ensure the result is the correct cells size
			assertTrue("validateSteps should return an arraylist of size  "
					+ "Expected:  "+ (steps+1) + " but was: " + result.size() ,result.size()== steps+1);


			// Get result cellType 
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int firstIndex = 0;
			Object cellType =  cellTypeField.get(result.get(firstIndex));




			assertTrue("Player marble should be inside safezone "   , cellType.equals(returnEnumValue(cellTypePath,"SAFE")));



		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + "occurred  while testing validateSteps method moving "+ steps + " steps from safezone cell index 0, should return an arraylist of size "+ (steps+1) +" ,Error Message: " + 
					e.getMessage());

		}
		}

	}

	@Test(timeout = 1000)
	public void testValidateSwapMethodActivePlayer1MarbleInBaseInBoardClass() {
		Class<?> IllegalSwapExceptionException = null;

		// Set test environment
		Object game = constructGame(); // Create game instance

		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			IllegalSwapExceptionException = Class.forName(IllegalSwapExceptionExceptionPath);
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred while creating object of type Board, Error Message: " + 
					e.getMessage());
		}


		Object colour = null;
		try {
			// get cuurent player colour
			Field currentPlayerIndexField = game.getClass().getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true); ;
			int index =  (int) currentPlayerIndexField.get(game);

			// get  players
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true); ;
			ArrayList<Object> players =  (ArrayList<Object>) playersField.get(game);

			// get colour
			Field colourField = players.get(index).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			colour = colourField.get(players.get(index));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching currentPlayerIndex, players, colour, attributes, Error Message: " + 
					e.getMessage());
		}


		// Create marble of current other colour 
		Class<?> marbleClass;
		Object marbleOther = null; 
		Object marbleActive = null; 

		Object otherColour = null;
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));

			for (Object col: colours){
				if (!col.equals(colour)) {
					otherColour = col;	
					break;}
			}
			marbleOther = marbleConstructor.newInstance(otherColour);
			marbleActive = marbleConstructor.newInstance(colour);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}

		int pose2 = 12;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			// Set other player marble anywhere not base
			marbleField = track.get(pose2).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose2), marbleOther);

			//Set active player marble in base marble
			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			int basePosition = 0;
			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					basePosition = i*25  ;
			marbleField.set(track.get(basePosition), marbleActive);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}


		try {
			// Get the private  method
			Method validateSwapMethod = board.getClass().getDeclaredMethod("validateSwap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			validateSwapMethod.setAccessible(true); 


			// Invoke the method
			validateSwapMethod.invoke(board,marbleActive, marbleOther);

		}

		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertTrue("IllegalSwapExceptionException should not be thrown when active player (marble 1) is in base and other player in normal cell",
					! IllegalSwapExceptionException.isInstance(thrownException));
			fail(e.getCause() + " occurred while testing validateSwap method, Error Message: " + 
					e.getMessage());
		}

		catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException  e) {
			fail(e.getClass() + " occurred while testing validateSwap method, Error Message: " + 
					e.getMessage());	
		}

	}

	@Test(timeout = 1000)
	public void testSwapIsPublicInClassBoard()  {

		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}
		Method m = null;
		try {
			m = board_class.getDeclaredMethod("swap",new Class[] { Class.forName(marblePath), Class.forName(marblePath)});
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred while searching swap method, Error Message: " + 
					e.getMessage());	

		}
		int modifiers = m.getModifiers();
		assertTrue("Swap method should be public",Modifier.isPublic(modifiers));
	}
	@Test(timeout=1000)
	public void testSendHomeExistenceInGameManagerClass() {
		try {
			testInterfaceMethod(Class.forName(gameManagerPath), "sendHome", void.class,
					new Class[] { Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class not found: " + e.getMessage()	)	;
		}

	}
	@Test(timeout=1000)
	public void testFieldMarbleExistenceInGameManagerClass() {

		try {
			testInterfaceMethod(Class.forName(gameManagerPath), "fieldMarble", void.class,
					new Class[] {});
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}



	@Test(timeout=1000)
	public void testDiscardCardWithColourExistenceInGameManagerClass() {
		try {
			testInterfaceMethod(Class.forName(gameManagerPath), "discardCard", void.class,
					new Class[] { Class.forName(colourPath) });
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}



	@Test(timeout=1000)
	public void testSendToBaseExistenceInBoardManagerClass() {
		try {
			testInterfaceMethod(Class.forName(boardManagerPath), "sendToBase", void.class,
					new Class[] { Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	@Test(timeout=1000)
	public void testSendToSafeExistenceInBoardManagerClass() {
		try {
			testInterfaceMethod(Class.forName(boardManagerPath), "sendToSafe", void.class,
					new Class[] { Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	@Test(timeout=1000)
	public void testGetActionableMarblesExistenceInBoardManagerClass() {
		try {
			testInterfaceMethod(Class.forName(boardManagerPath), "getActionableMarbles", ArrayList.class,
					new Class[] {});
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}




	@Test(timeout = 1000)
	public void testGetSafeZoneMethodRightColour1InBoardClass() {
	   
	    	
	    	// Set test environment
	         Object game = constructGame();
	         Object board = constructBoard(game); // Create board instance
			
	        
	        try {
	      
	       
	       // get safeZones from board 
	        Field safeZonesField = Class.forName(boardPath).getDeclaredField("safeZones");
	        safeZonesField.setAccessible(true);
			ArrayList testedSafeZone  = (ArrayList) safeZonesField.get(board);
			
	      // get the first safezone colour 
			  Field ColourField = Class.forName(safeZonePath).getDeclaredField("colour");
		        ColourField.setAccessible(true);
				Object resultColour  = ColourField.get(testedSafeZone.get(0));
			
			
			

	        // Get the private getSafeZone method
	        Method getSafeZoneMethod = board.getClass().getDeclaredMethod("getSafeZone", Class.forName(colourPath));
	        getSafeZoneMethod.setAccessible(true); // Required for private methods

	        // Invoke the method
	        Object result = getSafeZoneMethod.invoke(board, resultColour);
	        ArrayList<?> cells = (ArrayList<?>) result;

	        // get cells of the first safezone
	     // get the first safezone colour 
			  Field cellsField = Class.forName(safeZonePath).getDeclaredField("cells");
			  cellsField.setAccessible(true);
				ArrayList actualCells  = (ArrayList) cellsField.get(testedSafeZone.get(0));
			
	        
	      

	        // Final assertion
	        assertTrue("Returned cells are not of the correct safezone for the given  colour", actualCells == cells);

	    } catch (SecurityException | ClassNotFoundException | 
	             IllegalArgumentException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException|
	             InvocationTargetException  e) {
	        e.printStackTrace();
	        fail(e.getCause() + " occurred while testing getSafeZone method");
	    }
	}
	
	@Test(timeout = 1000)
	public void testGetSafeZoneMethodRightColour2InBoardClass() {
	   
	    	
	    	// Set test environment
	         Object game = constructGame();
	         Object board = constructBoard(game); // Create board instance
			
	        
	        try {
	      
	       
	       // get safeZones from board 
	        Field safeZonesField = Class.forName(boardPath).getDeclaredField("safeZones");
	        safeZonesField.setAccessible(true);
			ArrayList testedSafeZone  = (ArrayList) safeZonesField.get(board);
			
	      // get the first safezone colour 
			  Field ColourField = Class.forName(safeZonePath).getDeclaredField("colour");
		        ColourField.setAccessible(true);
				Object resultColour  = ColourField.get(testedSafeZone.get(1));
			
			
			

	        // Get the private getSafeZone method
	        Method getSafeZoneMethod = board.getClass().getDeclaredMethod("getSafeZone", Class.forName(colourPath));
	        getSafeZoneMethod.setAccessible(true); // Required for private methods

	        // Invoke the method
	        Object result = getSafeZoneMethod.invoke(board, resultColour);
	        ArrayList<?> cells = (ArrayList<?>) result;

	        // get cells of the first safezone
	     // get the first safezone colour 
			  Field cellsField = Class.forName(safeZonePath).getDeclaredField("cells");
			  cellsField.setAccessible(true);
				ArrayList actualCells  = (ArrayList) cellsField.get(testedSafeZone.get(1));
			
	        
	      

	        // Final assertion
	        assertTrue("Returned cells are not of the correct safezone for the given  colour", actualCells == cells);

	    } catch (SecurityException | ClassNotFoundException | 
	             IllegalArgumentException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException|
	             InvocationTargetException  e) {
	        e.printStackTrace();
	        fail(e.getCause() + " occurred while testing getSafeZone method");
	    }
	}
	
	@Test(timeout = 1000)
	public void testGetSafeZoneMethodRightColour3InBoardClass() {
	   
	    	
	    	// Set test environment
	         Object game = constructGame();
	         Object board = constructBoard(game); // Create board instance
			
	        
	        try {
	      
	       
	       // get safeZones from board 
	        Field safeZonesField = Class.forName(boardPath).getDeclaredField("safeZones");
	        safeZonesField.setAccessible(true);
			ArrayList testedSafeZone  = (ArrayList) safeZonesField.get(board);
			
	      // get the first safezone colour 
			  Field ColourField = Class.forName(safeZonePath).getDeclaredField("colour");
		        ColourField.setAccessible(true);
				Object resultColour  = ColourField.get(testedSafeZone.get(2));
			
			
			

	        // Get the private getSafeZone method
	        Method getSafeZoneMethod = board.getClass().getDeclaredMethod("getSafeZone", Class.forName(colourPath));
	        getSafeZoneMethod.setAccessible(true); // Required for private methods

	        // Invoke the method
	        Object result = getSafeZoneMethod.invoke(board, resultColour);
	        ArrayList<?> cells = (ArrayList<?>) result;

	        // get cells of the first safezone
	     // get the first safezone colour 
			  Field cellsField = Class.forName(safeZonePath).getDeclaredField("cells");
			  cellsField.setAccessible(true);
				ArrayList actualCells  = (ArrayList) cellsField.get(testedSafeZone.get(2));
			
	        
	      

	        // Final assertion
	        assertTrue("Returned cells are not of the correct safezone for the given  colour", actualCells == cells);

	    } catch (SecurityException | ClassNotFoundException | 
	             IllegalArgumentException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException|
	             InvocationTargetException  e) {
	        e.printStackTrace();
	        fail(e.getCause() + " occurred while testing getSafeZone method");
	    }
	}
	
	@Test(timeout = 1000)
	public void testGetSafeZoneMethodRightColour4InBoardClass() {
	   
	    	
	    	// Set test environment
	         Object game = constructGame();
	         Object board = constructBoard(game); // Create board instance
			
	        
	        try {
	      
	       
	       // get safeZones from board 
	        Field safeZonesField = Class.forName(boardPath).getDeclaredField("safeZones");
	        safeZonesField.setAccessible(true);
			ArrayList testedSafeZone  = (ArrayList) safeZonesField.get(board);
			
	      // get the first safezone colour 
			  Field ColourField = Class.forName(safeZonePath).getDeclaredField("colour");
		        ColourField.setAccessible(true);
				Object resultColour  = ColourField.get(testedSafeZone.get(3));
			
			
			

	        // Get the private getSafeZone method
	        Method getSafeZoneMethod = board.getClass().getDeclaredMethod("getSafeZone", Class.forName(colourPath));
	        getSafeZoneMethod.setAccessible(true); // Required for private methods

	        // Invoke the method
	        Object result = getSafeZoneMethod.invoke(board, resultColour);
	        ArrayList<?> cells = (ArrayList<?>) result;

	        // get cells of the first safezone
	     // get the first safezone colour 
			  Field cellsField = Class.forName(safeZonePath).getDeclaredField("cells");
			  cellsField.setAccessible(true);
				ArrayList actualCells  = (ArrayList) cellsField.get(testedSafeZone.get(3));
			
	        
	      

	        // Final assertion
	        assertTrue("Returned cells are not of the correct safezone for the given  colour", actualCells == cells);

	    } catch (SecurityException | ClassNotFoundException | 
	             IllegalArgumentException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException|
	             InvocationTargetException  e) {
	        e.printStackTrace();
	        fail(e.getCause() + " occurred while testing getSafeZone method");
	    }
	}


	@Test(timeout=1000)
	public void testSelectCardMethodInGameClassThrowsException() {
		Class invalidCardException = null;
		try {
			//create new game instance
			Object game = createGame();

			//create new board instance
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//create new card object
			Object card = createStandardCard(game,board);

			invalidCardException = Class.forName(InvalidCardExceptionExceptionPath);

			//try the selectCard method
			Method m = Class.forName(gamePath).getDeclaredMethod("selectCard", Class.forName(cardPath));
			m.invoke(game, card);
			fail("Expected InvalidCardException was not thrown");

		}
		catch(InvocationTargetException e) {
			// Check if the thrown exception is InvalidCardException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidCardException was not thrown",
					invalidCardException.isInstance(thrownException));
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	/////// Select Card Method Game Class Tests/////////////

	/////// Select Marble Method Player Class Tests/////////////

	@Test(timeout=1000)
	public void testSelectMarbleMethodInPlayerClassLogicWithListOfOneMarble() {

		try {			
			//Create new player instance
			Object colour = generateRandomColour();
			Object player = createPlayer(colour);

			//Create new marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble0 = marbleConstructor.newInstance(colour);
			Object marble1 = marbleConstructor.newInstance(colour);
			Object marble2 = marbleConstructor.newInstance(colour);
			Object marble3 = marbleConstructor.newInstance(colour);
			Object marble4 = marbleConstructor.newInstance(colour);
			Object marble5 = marbleConstructor.newInstance(colour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			marblesFixed.add(marble0);
			marblesFixed.add(marble1);
			marblesFixed.add(marble2);
			marblesFixed.add(marble3);
			Field playersMarbles = Class.forName(playerPath).getDeclaredField("marbles");
			playersMarbles.setAccessible(true);
			playersMarbles.set(player, marblesFixed);

			//Initialize a new array list with the marble created
			ArrayList<Object> selectedMarbles1 = new ArrayList<Object>();
			selectedMarbles1.add(marble4);

			//Access the selectedMarbles field in player class
			Field playerSelectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			playerSelectedMarbles.setAccessible(true);
			playerSelectedMarbles.set(player, selectedMarbles1);


			ArrayList<Object> selectedMarbles2 = new ArrayList<Object>();
			selectedMarbles2.add(marble4);
			selectedMarbles2.add(marble5);

			//Get the select marble method
			Method m = Class.forName(playerPath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(player, marble5);

			assertEquals("The marble should be added to your selection ",selectedMarbles2,playerSelectedMarbles.get(player));

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testSelectMarbleMethodInPlayerClassThrowsException () {

		Class invalidMarbleException = null;

		try {			
			//Create new player instance
			Object colour = generateRandomColour();
			Object player = createPlayer(colour);

			//Create new marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble0 = marbleConstructor.newInstance(colour);
			Object marble1 = marbleConstructor.newInstance(colour);
			Object marble2 = marbleConstructor.newInstance(colour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			Field playersMarbles = Class.forName(playerPath).getDeclaredField("marbles");
			playersMarbles.setAccessible(true);
			playersMarbles.set(player, marblesFixed);

			//Initialize a new array list with the marble created
			ArrayList<Object> selectedMarbles = new ArrayList<Object>();
			selectedMarbles.add(marble0);
			selectedMarbles.add(marble1);

			//Access the selectedMarbles field in player class
			Field playerSelectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			playerSelectedMarbles.setAccessible(true);
			playerSelectedMarbles.set(player, selectedMarbles);

			invalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			//Get the select marble method
			Method m = Class.forName(playerPath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(player, marble2);
			fail("Expected InvalidMarbleException was not thrown");
		}
		catch(InvocationTargetException e) {
			// Check if the thrown exception is InvalidMarbleException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					invalidMarbleException.isInstance(thrownException));
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	/////// Select Marble Method Player Class Tests/////////////

	/////// Select Marble Method Game Class Tests/////////////



	@Test(timeout=1000)
	public void testSelectMarbleMethodInGameClassLogicWithEmptyList() {

		try {		
			//create new game instance
			Object game = createGame();

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create new marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble0 = marbleConstructor.newInstance(playerColour);
			Object marble1 = marbleConstructor.newInstance(playerColour);
			Object marble2 = marbleConstructor.newInstance(playerColour);
			Object marble3 = marbleConstructor.newInstance(playerColour);
			Object marble4 = marbleConstructor.newInstance(playerColour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			marblesFixed.add(marble0);
			marblesFixed.add(marble1);
			marblesFixed.add(marble2);
			marblesFixed.add(marble3);
			Field playersMarbles = Class.forName(playerPath).getDeclaredField("marbles");
			playersMarbles.setAccessible(true);
			playersMarbles.set(player, marblesFixed);

			//Initialize a new array list with the marble created
			ArrayList<Object> selectedMarbles = new ArrayList<Object>();


			//Access the selectedMarbles field in player class
			Field playerSelectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			playerSelectedMarbles.setAccessible(true);


			selectedMarbles.add(marble4);

			//Get the select marble method
			Method m = Class.forName(gamePath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(game, marble4);

			assertEquals("The marble should be added to your selection ",selectedMarbles,playerSelectedMarbles.get(player));

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testSelectMarbleMethodInGameClassLogicMarbleAlreadyExists() {

		try {			
			//create new game instance
			Object game = createGame();

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create new marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble0 = marbleConstructor.newInstance(playerColour);
			Object marble1 = marbleConstructor.newInstance(playerColour);
			Object marble2 = marbleConstructor.newInstance(playerColour);
			Object marble3 = marbleConstructor.newInstance(playerColour);
			Object marble4 = marbleConstructor.newInstance(playerColour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			marblesFixed.add(marble0);
			marblesFixed.add(marble1);
			marblesFixed.add(marble2);
			marblesFixed.add(marble3);
			Field playersMarbles = Class.forName(playerPath).getDeclaredField("marbles");
			playersMarbles.setAccessible(true);
			playersMarbles.set(player, marblesFixed);

			//Initialize a new array list with the marble created
			ArrayList<Object> selectedMarbles1 = new ArrayList<Object>();
			selectedMarbles1.add(marble4);

			//Access the selectedMarbles field in player class
			Field playerSelectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			playerSelectedMarbles.setAccessible(true);
			playerSelectedMarbles.set(player, selectedMarbles1);

			ArrayList<Object> selectedMarbles2 = new ArrayList<Object>();
			selectedMarbles2.add(marble4);

			//Get the select marble method
			Method m = Class.forName(gamePath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(game, marble4);


			assertEquals("Selecting an already selected marble should not modify the selection",selectedMarbles2,playerSelectedMarbles.get(player));

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	/////// Select Marble Method Game Class Tests/////////////

	/////// deselectAll Method Game Class Tests/////////////
	@Test(timeout=1000)
	public void testDeselectAllMethodInGameClassLogic(){


		try {
			//create new game instance
			Object game = createGame();

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create new marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(playerColour);

			//create new board instance
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//create new card object
			Object card = createStandardCard(game,board);

			//get the selectedCard 
			Field selectedCardField = Class.forName(playerPath).getDeclaredField("selectedCard");
			selectedCardField.setAccessible(true);
			selectedCardField.set(player, card);

			//Initialize a new array list with the marble created
			ArrayList<Object> selectedMarbles = new ArrayList<Object>();
			selectedMarbles.add(marble);

			//Access the selectedMarbles field in player class
			Field selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			selectedMarblesField.set(player, selectedMarbles);

			assertEquals("The card is null",card,selectedCardField.get(player));
			assertEquals("No marbles are selected",selectedMarbles,selectedMarblesField.get(player));

			Method m = Class.forName(gamePath).getDeclaredMethod("deselectAll");
			m.invoke(game);

			ArrayList<Object> selectedMarblesClear = new ArrayList<Object>();

			assertEquals("The card should be updated correctly",null,selectedCardField.get(player));
			assertEquals("No marbles should be selected",selectedMarblesClear,selectedMarblesField.get(player));




		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}

	}

	@Test(timeout=1000)
	public void testEndPlayerTurnGameRemoveSelectedCard(){

		try {		
			Class<?> gameClass = Class.forName(gamePath);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);

			int randomNum = (int) (Math.random() * 4) + 1;
			Object game = gameConstructor.newInstance("PlayerName" + randomNum);

			Field board_field = Class.forName(gamePath).getDeclaredField("board");
			board_field.setAccessible(true);
			Object board = board_field.get(game);


			Field playerField= Class.forName(gamePath).getDeclaredField("players");
			playerField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playerField.get(game);

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);
			Object player1Colour = playerColourField.get(player1);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);					

			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));

			Class safeZoneClass = Class.forName(safeZonePath);
			Field cellsField = safeZoneClass.getDeclaredField("cells");
			cellsField.setAccessible(true);
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");

			Class cellClass = Class.forName(cellPath);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			ArrayList<Object>  marbles = new ArrayList<Object>();
			for(int i=0;i<2;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(0);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<2;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);

			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);

			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player1, card);


			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 1);

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));

			Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
			Object card2 = saverConstructor.newInstance("Burner","description",board,game);
			ArrayList<Object> playerHand = new ArrayList<Object>();
			playerHand.add(card);
			playerHand.add(card1);
			playerHand.add(card2);

			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");
			hand_field.setAccessible(true);
			hand_field.set(player1,playerHand);


			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);


			playerHand = (ArrayList<Object>) hand_field.get(player1);

			boolean found=false;
			for(int i=0;i<playerHand.size();i++){
				if(playerHand.get(i)==card)
					found=true;
			}



			assertEquals("endPlayerTurn method in class game should remove the current player’s selected card from their hand",false,found);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}




	/////// deselectAll Method Game Class Tests/////////////

	/////validateMarbleSize Method Card and All Subclasses Tests//////


	@Test(timeout=1000)
	public void testValidateMarbleSizeInAceClassLogicFailCase() {
		try {
			//Create game
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(acePath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Ace","description1",suit,board,game);

			//Create Random Colour
			Object colour = generateRandomColour();

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));


			//Create a list with marble(s)
			int random = new Random().nextInt(11)+2;
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = null;
			for(int i=0; i<random; i++) {
				marble = marbleConstructor.newInstance(colour);
				marbles.add(marble);
			}


			//Invoke the validateMarbleSize method
			Method m = Class.forName(acePath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);

			assertFalse("The size of marbles should be 0 or 1",returnValue);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleSizePresentInKingClass() {
		try {
			testMethodExistence(Class.forName(kingPath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testValidateMarbleSizeInQueenClassLogicWithNoMarble() {
		try {
			//Create game
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(queenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Queen","description12",suit,board,game);

			//Create Random Colour
			Object colour = generateRandomColour();


			//Create a list with marble(s)
			ArrayList<Object> marbles = new ArrayList<Object>();

			//Invoke the validateMarbleSize method
			Method m = Class.forName(queenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);

			assertTrue("The size of marbles should be 0",returnValue);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleSizeInTenClassLogicWithOneMarble() {
		try {
			//Create game
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(tenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Ten","description10",suit,board,game);

			//Create Random Colour
			Object colour = generateRandomColour();

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(colour);

			//Create a list with marble(s)
			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marble);

			//Invoke the validateMarbleSize method
			Method m = Class.forName(tenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);

			assertTrue("The size of marbles should be 1",returnValue);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleSizePresentInJackClass() {
		try {
			testMethodExistence(Class.forName(jackPath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleSizeInJackClassLogicWithOneMarble() {
		try {
			//Create game
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(jackPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Jack","description12",suit,board,game);

			//Create Random Colour
			Object colour = generateRandomColour();

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(colour);

			//Create a list with marble(s)
			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marble);

			//Invoke the validateMarbleSize method
			Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);

			assertTrue("The size of marbles should be 1",returnValue);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleSizeInSevenClassLogicWithNoMarble() {
		try {
			//Create game
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(sevenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Seven","description7",suit,board,game);

			//Create Random Colour
			Object colour = generateRandomColour();



			ArrayList<Object> marbles = new ArrayList<Object>();

			//Invoke the validateMarbleSize method
			Method m = Class.forName(sevenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);

			assertFalse("The size of marbles should be 1 or 2",returnValue);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleSizeInSevenClassFailCase() {
		try {
			//Create game
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(sevenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Seven","description7",suit,board,game);

			//Create Random Colour
			Object colour = generateRandomColour();

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));

			//Create a list with marble(s)
			int random = new Random().nextInt(11)+3;
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = null;
			for(int i=0; i<random; i++) {
				marble = marbleConstructor.newInstance(colour);
				marbles.add(marble);
			}

			//Invoke the validateMarbleSize method
			Method m = Class.forName(sevenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);

			assertFalse("The size of marbles should be 1 or 2",returnValue);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	///Four Class///

	@Test(timeout=1000)
	public void testValidateMarbleSizeAbsentInFourClass() {
		try {
			testMethodAbsence(Class.forName(fourPath),"validateMarbleSize");
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	///Burner Class///

	@Test(timeout=1000)
	public void testValidateMarbleSizeAbsentInBurnerClass() {
		try {
			testMethodAbsence(Class.forName(burnerPath),"validateMarbleSize");
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	/////validateMarbleSize Method Card and All Subclasses Tests//////


	///Play Method Exceptions Check///
	@Test(timeout=1000)
	public void testPlayMethodSelectedCardQueenCaseMarbleSize() {

		Class invalidMarbleException = null;
		try {

			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(queenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Queen","description12",suit,board,game);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(playerColour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			marblesFixed.add(marble);
			Field palyerMarblesField = Class.forName(playerPath).getDeclaredField("marbles");
			palyerMarblesField.setAccessible(true);
			palyerMarblesField.set(player, marblesFixed);

			//Create a list with marble(s)
			int random = new Random().nextInt(11)+2;
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble1 = null;
			for(int i=0; i<random; i++) {
				marble1 = marbleConstructor.newInstance(playerColour);
				marbles.add(marble1);
			}

			//get marbles list of player
			Field selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			selectedMarblesField.set(player, marbles);

			//get the selected card field
			Field selectedCardField = Class.forName(playerPath).getDeclaredField("selectedCard");
			selectedCardField.setAccessible(true);
			selectedCardField.set(player, card);


			invalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			//get play method
			Method m = Class.forName(playerPath).getDeclaredMethod("play");
			m.invoke(player,null);

			fail("Expected InvalidMarbleException was not thrown");

		}catch(InvocationTargetException e) {
			// Check if the thrown exception is InvalidMarbleException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					invalidMarbleException.isInstance(thrownException));


		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}

	@Test(timeout=1000)
	public void testPlayMethodSelectedCardJackCaseMarbleSize() {

		Class invalidMarbleException = null;
		try {

			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(jackPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Jack","description11",suit,board,game);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(playerColour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			marblesFixed.add(marble);
			Field palyerMarblesField = Class.forName(playerPath).getDeclaredField("marbles");
			palyerMarblesField.setAccessible(true);
			palyerMarblesField.set(player, marblesFixed);

			//Create a list with marble(s)
			int random = new Random().nextInt(11)+3;
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble1 = marbleConstructor.newInstance(playerColour);
			for(int i=0; i<random; i++) {
				marble1 = null;
				marbles.add(marble1);
			}

			//get selected marbles list of player
			Field selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			selectedMarblesField.set(player, marbles);

			//get the selected card field
			Field selectedCardField = Class.forName(playerPath).getDeclaredField("selectedCard");
			selectedCardField.setAccessible(true);
			selectedCardField.set(player, card);

			invalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			//get play method
			Method m = Class.forName(playerPath).getDeclaredMethod("play");
			m.invoke(player);

			fail("Expected InvalidMarbleException was not thrown");

		}catch(InvocationTargetException e) {
			// Check if the thrown exception is InvalidMarbleException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					invalidMarbleException.isInstance(thrownException));


		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}


	@Test(timeout=1000)
	public void testPlayMethodSelectedCardStandardCaseEmptyListMarbleSize() {

		Class invalidMarbleException = null;
		try {

			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Standard","description",1,suit,board,game);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(playerColour);

			//marbles list fixed value
			ArrayList<Object> marblesFixed = new ArrayList<Object>();
			marblesFixed.add(marble);
			Field palyerMarblesField = Class.forName(playerPath).getDeclaredField("marbles");
			palyerMarblesField.setAccessible(true);
			palyerMarblesField.set(player, marblesFixed);


			//get selected marbles list of player
			ArrayList<Object> marbles = new ArrayList<Object>();
			Field selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			selectedMarblesField.set(player, marbles);

			//get the selected card field
			Field selectedCardField = Class.forName(playerPath).getDeclaredField("selectedCard");
			selectedCardField.setAccessible(true);
			selectedCardField.set(player, card);

			invalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			//get play method
			Method m = Class.forName(playerPath).getDeclaredMethod("play");
			m.invoke(player);

			fail("Expected InvalidMarbleException was not thrown");

		}catch(InvocationTargetException e) {
			// Check if the thrown exception is InvalidMarbleException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					invalidMarbleException.isInstance(thrownException));


		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}


	///Play Method Exceptions Check///

	/////Validate Marble Colour Method Tests////////

	@Test(timeout=1000)
	public void testValidateMarbleColourMethodInStandardClassFail() {

		try {
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Standard","description",1,suit,board,game);

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = null;
			if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN")))
				marble = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
			else{
				if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED")))
					marble = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
				else{
					if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW")))
						marble = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
					else
						marble = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED"));
				}
			}

			//Create a list with marble(s)
			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marble);

			//get validateMarbleColours method
			Method m = Class.forName(cardPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card,marbles);

			assertFalse("The colour of marble is invalid",returnValue);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}


	@Test(timeout=1000)
	public void testValidateMarbleColourMethodInJackClassLogicWithOneMarble() {

		try {
			Object game = createGame();

			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);

			//get players list in game
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> players = (ArrayList<Object>) playersField.get(game);

			//get the index of the current player
			Field currentPlayerIndexField = Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex = (int) currentPlayerIndexField.get(game);

			Object player = players.get(currentPlayerIndex);

			//get current player colour
			Field ActiveplayerColour = Class.forName(playerPath).getDeclaredField("colour");
			ActiveplayerColour.setAccessible(true);
			Object playerColour = ActiveplayerColour.get(player);

			//Create StandardCard
			Constructor<?> cardConstructor = Class.forName(jackPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
			Object card = cardConstructor.newInstance("Jack","description11",suit,board,game);

			//Create Marble
			Class marbleClass = Class.forName(marblePath);
			Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(playerColour);

			//Create a list with marble(s)
			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marble);

			//get validateMarbleColours method
			Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card,marbles);

			assertTrue("The colour of marble should belong to the current player",returnValue);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
			// TODO Auto-generated catch block

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");

		}
	}


	@Test(timeout=1000)
	public void testValidateMarbleColoursAbsentInTenClass() {
		try {
			testMethodAbsence(Class.forName(tenPath),"validateMarbleColours");
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleColoursAbsentInSevenClass() {
		try {
			testMethodAbsence(Class.forName(sevenPath),"validateMarbleColours");
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testValidateMarbleColoursAbsentInSaverClass() {
		try {
			testMethodAbsence(Class.forName(saverPath),"validateMarbleColours");
		}
		catch(ClassNotFoundException e){

			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testActMethodInClassKingMovesMarble13Steps()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			Constructor<?> king_constructor = Class.forName(kingPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object king_card = king_constructor.newInstance("king card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);


			//Cell13 that the marble will move to in case of king card
			Object Cell13 = track.get(13);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell13);

			// Invoke King.act 
			Method actMethod= Class.forName(kingPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(king_card, marbles);


			// Check position 0 is null
			assertEquals("Method act in class King must call method moveBy in case given a list of non empty marbles", null, getMarbleMethod.invoke(Cell0));


			// check position 13 now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method act in class King must call method MoveBy with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell13) );
		} 
		catch(Exception e){
			fail(e.getCause()+ " occured while accessing method act in class King");
		}	
	}

	@Test(timeout = 1000)
	public void testActMethodInClassQueenMovesMarble12Steps()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			Constructor<?> queen_constructor = Class.forName(queenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object queen_card = queen_constructor.newInstance("queen card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);


			//Cell12 that the marble will move to in case of queen card
			Object Cell12 = track.get(12);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell12);

			// Invoke King.act 
			Method actMethod= Class.forName(queenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(queen_card, marbles);


			// Check position 0 is null
			assertEquals("Method act in class Queen must call method moveBy in case given a list of non empty marbles", null, getMarbleMethod.invoke(Cell0) );


			// check position 12 now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method act in class Queen must call method MoveBy with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell12) );
		} 
		catch(Exception e){
			fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Queen");
		}	
	}

	@Test(timeout = 1000)
	public void testActMethodInClassQueenDoesntDestroysMarblesInPath()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);


			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);

			Constructor<?> queen_constructor = Class.forName(queenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object queen_card = queen_constructor.newInstance("queen card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 0 & 5 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);

			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble2);


			// Invoke Qeen.act 
			Method actMethod= Class.forName(queenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(queen_card, marbles);


			// Check position 5 didn't change
			assertEquals("Method act in class Queen must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell5) );

		} 
		catch(Exception e){
			fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Queen");
		}	
	}

	@Test(timeout = 1000)
	public void testActMethodInClassTenDoesntDestroysMarblesInPath()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);

			Constructor<?> ten_constructor = Class.forName(tenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object ten_card = ten_constructor.newInstance("ten card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 0 & 5 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);

			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble2);


			// Invoke Ten.act 
			Method actMethod= Class.forName(tenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(ten_card, marbles);

			// Check position 5 didn't change
			assertEquals("Method act in class Ten must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell5));

		} 
		catch(Exception e){
			fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Ten");
		}	
	}

	@Test(timeout = 1000)
	public void testActMethodInClassSevenDoesntDestroysMarblesInPathWhenMoving2Marbles()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			Object marble3 = createMarbleForActivePlayer(game);

			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);

			Constructor<?> seven_constructor = Class.forName(sevenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object seven_card = seven_constructor.newInstance("seven card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			marbles.add(marble);marbles.add(marble3);

			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 0 & 6 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			Object Cell6 = track.get(6);
			setMarbleMethod.invoke(Cell6, marble3);

			Object Cell7 = track.get(7);
			setMarbleMethod.invoke(Cell7, marble2);

			// Get Split Distance
			Method getSplitDistanceMethod= Class.forName(boardPath).getDeclaredMethod("getSplitDistance");
			int split = (int) getSplitDistanceMethod.invoke(board_object);

			// Invoke Seven.act 
			Method actMethod= Class.forName(sevenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(seven_card, marbles);

			// Check position 5 didn't change
			assertEquals("Method act in class Seven must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell7));

		} 
		catch(Exception e){
			fail(e.getMessage() +  " occured while accessing method act in class Seven");
		}	
	}
	@Test(timeout = 1000)
	public void testActMethodInClassFourMovesMarble4Steps()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			Object marble3 = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);


			Constructor<?> four_constructor = Class.forName(fourPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object four_card = four_constructor.newInstance("four card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);



			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 5 on track
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble);


			//Cell1 that the marble will move to in case of four card
			Object Cell1 = track.get(1);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell1);

			// Invoke Ten.act 
			Method actMethod= Class.forName(fourPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(four_card, marbles);


			// Check position 5 is null
			assertEquals("Method act in class Four must call method moveBy", null, getMarbleMethod.invoke(Cell5) );


			// check position 1 now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method act in class Four must call method act with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell1) );
		} 
		catch(Exception e){
			fail(e.getMessage() + " occured while accessing method act in class Four");
		}	
	}

	@Test(timeout = 1000)
	public void testActMethodInClassFourDoesntDestroysMarblesInPath()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			Object marble3 = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);


			Constructor<?> four_constructor = Class.forName(fourPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object four_card = four_constructor.newInstance("four card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);



			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 3 & 5 on track
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble);

			Object Cell3 = track.get(3);
			setMarbleMethod.invoke(Cell3, marble2);


			// Invoke Ten.act 
			Method actMethod= Class.forName(fourPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(four_card, marbles);

			// Check position 5 didn't change
			assertEquals("Method act in class Four must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell3) );

		} 
		catch(Exception e){
			fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Four");
		}	
	}
	@Test(timeout = 1000)
	public void testActMethodInClassFiveDoesntDestroysMarblesInPath()  {
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			Constructor<?> five_constructor = Class.forName(fivePath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object five_card = five_constructor.newInstance("five card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);


			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);
			marbles.add(marble);

			// Methods to get and set marbles in a cell
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Add the marble to cell 3 & 5 on track
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble2);

			Object Cell3 = track.get(3);
			setMarbleMethod.invoke(Cell3, marble);



			Method actMethod= Class.forName(standardPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(five_card, marbles);

			// Check position 5 didn't change
			assertEquals("Method act in class Five must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell5));

		} 
		catch(Exception e){
//			System.out.println(e.getCause());
			fail(e.getMessage() + " occured while accessing method act in class Five");
		}	
	}

	@Test(timeout = 1000)
	public void testPlayMethodInClassPlayerWithCardAce()  {

		try {
			Object game = createGame();
			//			Object board_object = createBoard(game);
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object player = getActivePlayer(game);
			Object activePlayerColour = getActiveColor(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet2(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(player, hand);


			// Add marbles to the board to cell 0
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);


			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);


			//Cell 1 that the marble will move to
			Object Cell1 = track.get(1);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell1);


			// Select a card
			Field card = Class.forName(playerPath).getDeclaredField("selectedCard");
			card.setAccessible(true);
			card.set(player, hand.get(0));


			// Select Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(player, marbles);

			// Play the selected card on the selected marbles

			Method playMethod= Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.invoke(player);


			// Check position 0 is null
			assertEquals("Method play in class Player must call method act", null, getMarbleMethod.invoke(Cell0) );


			// check new position now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble , getMarbleMethod.invoke(Cell1) );

		} catch (Exception e) {
			fail(e.getMessage() + " occured while accessing method play in class Player");

		}
	}

	@Test(timeout = 1000)
	public void testPlayMethodInClassPlayerWithCardKing()  {

		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object player = getActivePlayer(game);
			Object activePlayerColour = getActiveColor(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet2(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(player, hand);


			// Add marbles to the board to cell 0
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);


			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);


			//Cell 13 that the marble will move to 
			Object Cell13 = track.get(13);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell13);


			// Select a card
			Field card = Class.forName(playerPath).getDeclaredField("selectedCard");
			card.setAccessible(true);
			card.set(player, hand.get(1));


			// Select Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(player, marbles);

			// Play the selected card on the selected marbles

			Method playMethod= Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.invoke(player);


			// Check position 0 is null
			assertEquals("Method play in class Player must call method act", null, getMarbleMethod.invoke(Cell0) );


			// check new position now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble, getMarbleMethod.invoke(Cell13) );

		} catch (Exception e) {
			fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

		}
	}

	@Test(timeout = 1000)
	public void testPlayMethodInClassPlayerWithCardQueen()  {

		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object player = getActivePlayer(game);
			Object activePlayerColour = getActiveColor(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(player, hand);


			// Add marbles to the board to cell 0
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);


			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);


			//Cell 12 that the marble will move to in case of queen card played
			Object Cell12 = track.get(12);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell12);


			// Select a card
			Field card = Class.forName(playerPath).getDeclaredField("selectedCard");
			card.setAccessible(true);
			card.set(player, hand.get(0));


			// Select Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(player, marbles);

			// Play the selected card on the selected marbles

			Method playMethod= Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.invoke(player);


			// Check position 0 is null
			assertEquals("Method play in class Player must call method act", null, getMarbleMethod.invoke(Cell0) );


			// check new position now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble,  getMarbleMethod.invoke(Cell12));

		} catch (Exception e) {
			fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

		}
	}

	@Test(timeout = 1000)
	public void testPlayMethodInClassPlayerWithCardFour()  {

		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object player = getActivePlayer(game);
			Object activePlayerColour = getActiveColor(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(player, hand);


			// Add marbles to the board to cell 0
			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);


			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble);


			//Cell 1 that the marble will move to in case of four card played
			Object Cell1 = track.get(1);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell1);


			// Select a card
			Field card = Class.forName(playerPath).getDeclaredField("selectedCard");
			card.setAccessible(true);
			card.set(player, hand.get(3));


			// Select Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(player, marbles);

			// Play the selected card on the selected marbles

			Method playMethod= Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.invoke(player);


			// Check position 0 is null
			assertEquals("Method play in class Player must call method act", null, getMarbleMethod.invoke(Cell5) );


			// check new position now contains the marble if it wasn't a trap
			if( !isTrap) 
				assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble, getMarbleMethod.invoke(Cell1) );

		} catch (Exception e) {
			fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

		}
	}

	@Test(timeout = 1000)
	public void testPlayPlayerTurnMethodInClassGameWithCardKing(){
		try {
			Object game = createGame();
			//				Object board_object = createBoard(game);
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			Object activePlayerColour = getActiveColor(game);

			// Get the current player
			Object current_player = getActivePlayer(game);

			// Set the Hand of the Player
			ArrayList<Object> hand = fourCardsSet2(board_object, game);
			Method setHandMethod= Class.forName(playerPath).getDeclaredMethod("setHand", ArrayList.class);
			setHandMethod.invoke(current_player, hand);

			// Select a card
			Field selected_card = Class.forName(playerPath).getDeclaredField("selectedCard");
			selected_card.setAccessible(true);
			selected_card.set(current_player, hand.get(1));

			Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
			Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			// added to Selected Marbles 
			Field selectedMarbles = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarbles.setAccessible(true);
			selectedMarbles.set(current_player, marbles);

			// Add marbles to the track to cell 0
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);

			//Cell 13 that the marble will move to
			Object Cell13 = track.get(13);
			Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			boolean isTrap = (boolean) isTrapMethod.invoke(Cell13);

			Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
			playPlayerTurn.invoke(game);

			// Check position 0 is null
			assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );

			// check new position now contains the marble if it wasn't a trap
			if(!isTrap) 
				assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell13) );
		}
		catch(Exception e){
			fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
		}
	}




	// //////////////////////////////////////////////////////////HELPER METHODS/////////////////////////////////////////////////////////////////



	private Object CreateCell(String typeString) {
		try {
			Object type = Enum.valueOf(
					(Class<Enum>) Class.forName(cellTypePath), typeString);
			Constructor<?> cellConstructor = Class.forName(cellPath)
					.getConstructor(Class.forName(cellTypePath));
			Object cell = cellConstructor.newInstance(type);
			return cell;

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			fail(e.getCause() + " occured when creating a new cell");

		}

		return null;
	}

	private Object createCard(Object game, Object board) {
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}

		// Card constructor
		Constructor<?> cardStandardConstructor;
		Object card = null;
		try {
			cardStandardConstructor = Class.forName(fourPath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));

			try {
				card = cardStandardConstructor.newInstance("card",
						"description", suit, board, game);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				fail(e.getMessage() + "occured when creating a new Four card");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				fail(e.getMessage() + "occured when creating a new Four card");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				fail(e.getMessage() + "occured when creating a new Four card");
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				fail(e.getMessage() + "occured when creating a new Four card");
			}
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return card;
	}

	private Object createMarble(String colour) {

		Object marbelColour = null;
		try {
			marbelColour = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), colour);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Colour is not found!");
		}

		Constructor<?> marbleConstructor = null;
		try {
			marbleConstructor = Class.forName(marblePath).getConstructor(
					Class.forName(colourPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Marble constructor!");
		}
		Object marble = null;
		try {
			marble = marbleConstructor.newInstance(marbelColour);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Marbel!");
		}
		return marble;
	}

	private Object createPlayer(String colour) {
		Object marbelColour = null;
		try {
			marbelColour = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), colour);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Colour is not found!");

		}

		String name = "superMario " + (int) (Math.random() * (10) + 1) + 5;
		Constructor<?> playerConstructor = null;
		try {
			playerConstructor = Class.forName(playerPath).getConstructor(
					String.class, Class.forName(colourPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Player constructor!");

		}
		Object player = null;
		try {
			player = playerConstructor.newInstance(name, marbelColour);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Player!");
		}
		return player;
	}

	private Object getBoardObjectFromGame(Object Game)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Method getBoard = Class.forName(gamePath).getDeclaredMethod("getBoard");
		Object board = getBoard.invoke(Game);
		return board;
	}

	private Object createGame() {

		Constructor<?> gameConstructor = null;
		try {
			gameConstructor = Class.forName(gamePath).getConstructor(
					String.class);
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Game constructor!");
		}
		Object game = null;
		try {
			game = gameConstructor.newInstance("PlayerName");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Game!");

		}

		return game;
	}

	private Object createQueenCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(queenPath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Queen constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");

		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("Queen", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Queen Card!");

		}

		return card;
	}

	private Object createKingCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(kingPath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the King constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("King", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new King Card!");
		}

		return card;
	}

	private Object createAceCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(acePath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the ACE constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("ACE", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Ace Card!");
		}

		return card;
	}

	private Object createBurnerCard(Object game, Object board)
			throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		fail("An error occured when acessing the King constructor!");

		fail("Error occured when constructing a new King Card!");

		Constructor<?> cardConstructor = Class.forName(burnerPath)
				.getConstructor(String.class, String.class,
						Class.forName(boardManagerPath),
						Class.forName(gameManagerPath));
		Object card = cardConstructor.newInstance("Burner", "description",
				board, game);

		return card;
	}

	private Object createSaverCard(Object game, Object board)
			throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		fail("An error occured when acessing the King constructor!");

		fail("Error occured when constructing a new King Card!");

		Constructor<?> cardConstructor = Class.forName(saverPath)
				.getConstructor(String.class, String.class,
						Class.forName(boardManagerPath),
						Class.forName(gameManagerPath));
		Object card = cardConstructor.newInstance("Saver", "description",
				board, game);

		return card;
	}

	private Object createBoard(Object game) {

		Field boardField=null;
		Object board= null;
		try {
			boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);


			board= boardField.get(game);


		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			fail("error occured while accessing the Game's Board, check for Typos");
		}

		return board;
	}

	private Object createTenCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(tenPath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the King constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("Ten", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Ten Card!");
		}

		return card;
	}

	private Object createFiveCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(fivePath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Five constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath),
					"DIAMOND");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("Five", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Five Card!");
		}

		return card;
	}

	private Object createFourCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(fourPath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the King constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath),
					"DIAMOND");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("Four", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Four Card!");
		}

		return card;
	}

	private Object createSevenCard(Object game, Object board) {

		Constructor<?> cardConstructor = null;
		try {
			cardConstructor = Class.forName(sevenPath).getConstructor(
					String.class, String.class, Class.forName(suitPath),
					Class.forName(boardManagerPath),
					Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Seven constructor!");
		}
		Object suit = null;
		try {
			suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "CLUB");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			fail("Class Suit is not found!");
		}
		Object card = null;
		try {
			card = cardConstructor.newInstance("Seven", "description", suit,
					board, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Seven Card!");
		}

		return card;
	}

	private Object constructGame() {

		Object game = null;
		Constructor<?> gameConstructor = null;
		try {
			gameConstructor = Class.forName(gamePath).getConstructor(
					String.class);
			game = gameConstructor.newInstance("PlayerName");

		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | InstantiationException
				| InvocationTargetException | IllegalAccessException
				| IllegalArgumentException e) {
			fail(e.getClass() + " occurred while creating object of type Game ");
		}

		return game;
	}

	private Object constructBoard(Object game) {

		Field boardField=null;
		Object board= null;
		try {
			boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);


			board= boardField.get(game);


		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			fail("error occured while accessing the Game's Board, check for Typos");
		}

		return board;
	}



	private boolean checkTwoAttributesEqualValue(String className,
			Object object1, Object object2, String fieldName)
					throws IllegalArgumentException, IllegalAccessException,
					ClassNotFoundException {
		Class curr1 = Class.forName(className);

		Field f = null;

		try {
			f = curr1.getDeclaredField(fieldName);
			f.setAccessible(true);

			Object mh1 = f.get(object1);
			Object mh2 = f.get(object2);
			return mh1.equals(mh2);

		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();
			fail("Attributes name error");
			return false;
		}

	}

	private boolean checkAttributesEqualValue(String className, Object object1,
			Object value, String fieldName) throws IllegalArgumentException,
	IllegalAccessException, ClassNotFoundException {
		Class curr1 = Class.forName(className);

		Field f = null;

		try {
			f = curr1.getDeclaredField(fieldName);
			f.setAccessible(true);

			Object mh1 = f.get(object1);

			return mh1.equals(value);

		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();
			fail("Attributes name error");
			return false;
		}

	}

	private Object returnEnumValue(String enumClassPath, String enumValue) {
		try {
			return Enum.valueOf((Class<Enum>) Class.forName(enumClassPath),
					enumValue);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void testMethodExistence(Class aClass, String methodName,
			Class returnType, Class[] parameters) {
		Method[] methods = aClass.getDeclaredMethods();
		assertTrue(aClass.getSimpleName() + " class should have " + methodName
				+ "method", containsMethodName(methods, methodName));

		Method m = null;
		boolean thrown = false;
		try {
			m = aClass.getDeclaredMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		assertTrue(
				"Method " + methodName
				+ " should have the following set of parameters : "
				+ Arrays.toString(parameters), !thrown);
		assertTrue("wrong return type", m.getReturnType().equals(returnType));
	}

	private void testMethodAbsence(Class aClass, String methodName) {
		Method[] methods = aClass.getDeclaredMethods();
		assertFalse(aClass.getSimpleName() + " class should not override "
				+ methodName + " method",
				containsMethodName(methods, methodName));
	}

	private void testInterfaceMethodIsDefault(Class iClass, String methodName,
			Class[] parameters) {
		Method m = null;
		boolean thrown = false;
		try {
			m = iClass.getDeclaredMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertTrue("Method " + methodName + " with the following parameters :"
				+ Arrays.toString(parameters) + " should exist in interface"
				+ iClass.getName(), !thrown);
		assertTrue("Method " + methodName + " should be a default method",
				m.isDefault());
	}

	private void testInterfaceMethod(Class iClass, String methodName,
			Class returnType, Class[] parameters) {
		Method[] methods = iClass.getDeclaredMethods();
		assertTrue(iClass.getSimpleName() + " interface should have "
				+ methodName + "method",
				containsMethodName(methods, methodName));

		Method m = null;
		boolean thrown = false;
		try {
			m = iClass.getDeclaredMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		assertTrue(
				"Method " + methodName
				+ " should have the following set of parameters : "
				+ Arrays.toString(parameters), !thrown);
		assertTrue("wrong return type", m.getReturnType().equals(returnType));

	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}

	// ////////////////////////////////////////////////////////////////////////

	private Object createStandardCard(Object game, Object board)
			throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Constructor<?> cardConstructor = Class.forName(standardPath)
				.getConstructor(String.class, String.class, int.class,
						Class.forName(suitPath),
						Class.forName(boardManagerPath),
						Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath),
				"SPADE");
		Object card = cardConstructor.newInstance("card", "description", 1,
				suit, board, game);

		return card;
	}

	private Object generateRandomColour() throws ClassNotFoundException {
		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath),
				"RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath),
				"YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath),
				"BLUE");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath),
				"GREEN");
		int randomNum = (int) (Math.random() * 4) + 1;
		Object colour = null;

		switch (randomNum) {
		case 1:
			colour = colour1;
		case 2:
			colour = colour2;
		case 3:
			colour = colour3;
		case 4:
			colour = colour4;
		}

		return colour;
	}
	private Object createCellType(String celltype)
			throws ClassNotFoundException {
		Object cell_type = Enum.valueOf(
				(Class<Enum>) Class.forName(cellTypePath), celltype);
		return cell_type;
	}

	private Object createCell(Object cell_type) throws InstantiationException,
	IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException,
	SecurityException, ClassNotFoundException {
		Constructor<?> cell_constructor = Class.forName(cellPath)
				.getConstructor(Class.forName(cellTypePath));
		Object cell = cell_constructor.newInstance(cell_type);
		return cell;
	}

	private Object createColor(String Color) throws NoSuchFieldException,
	SecurityException, ClassNotFoundException,
	IllegalArgumentException, IllegalAccessException,
	InvocationTargetException, NoSuchMethodException,
	InstantiationException {
		Object colour_Object = Enum.valueOf(
				(Class<Enum>) Class.forName(colourPath), Color);
		return colour_Object;
	}

	private ArrayList<Object> getSafeZone(Object board_object)
			throws NoSuchFieldException, SecurityException,
			ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException {
		Field safeZoneField = Class.forName(boardPath).getDeclaredField(
				"safeZones");
		safeZoneField.setAccessible(true);
		ArrayList<Object> safeZones = (ArrayList<Object>) safeZoneField
				.get(board_object);
		return safeZones;
	}

	private int getBasePosition(Object board_object, Object color_object)
			throws NoSuchFieldException, SecurityException,
			ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		ArrayList<Object> safeZones = getSafeZone(board_object);
		Method getColorMethod = Class.forName(safeZonePath).getDeclaredMethod(
				"getColour");
		int index = -1;
		for (int i = 0; i < safeZones.size(); i++) {
			Object color = getColorMethod.invoke(safeZones.get(i));
			if (color == color_object) {
				index = i;
				break;
			}
		}

		int basePosition = index * 25;
		return basePosition;

	}

	private Object createMarble(Object Color) throws NoSuchFieldException,
	SecurityException, ClassNotFoundException,
	IllegalArgumentException, IllegalAccessException,
	InvocationTargetException, NoSuchMethodException,
	InstantiationException {
		Constructor<?> marble_constructor = Class.forName(marblePath)
				.getConstructor(Class.forName(colourPath));
		Object marble = marble_constructor.newInstance(Color);
		return marble;
	}


	private Object createPlayer(Object colour) throws ClassNotFoundException,
	NoSuchMethodException, SecurityException, InstantiationException,
	IllegalAccessException, IllegalArgumentException,
	InvocationTargetException {

		int randomInt = (int) (Math.random() * 100);
		String playerName = "Player " + randomInt;

		Class playerClass = Class.forName(playerPath);
		Constructor playerConstructor = playerClass.getConstructor(
				String.class, Class.forName(colourPath));
		Object player = playerConstructor.newInstance(playerName, colour);

		return player;
	}


	private Object createMarbleForActivePlayer(Object game) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException, ClassNotFoundException, NoSuchMethodException {
		Object activePlayerColour =  getActiveColor(game);
		Constructor<?> marble_constructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object marble = marble_constructor.newInstance(activePlayerColour);

		return marble;

	}

	private Object getActiveColor(Object game) throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// Get Active Player
		Field players = Class.forName(gamePath).getDeclaredField("players"); 
		players.setAccessible(true);
		ArrayList<Object> players_array = (ArrayList<Object>) players.get(game);

		Field currentPlayerIndex = Class.forName(gamePath).getDeclaredField("currentPlayerIndex"); 
		currentPlayerIndex.setAccessible(true);
		int currentPlayerIndex_value = (int) currentPlayerIndex.get(game);

		Method getColorMethod= Class.forName(playerPath).getDeclaredMethod("getColour");
		Object activePlayerColour = getColorMethod.invoke(players_array.get(currentPlayerIndex_value));

		return activePlayerColour;

	}





	private ArrayList<Object> getTrack(Object board_object) throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		Class<?> board_class = Class.forName(boardPath); 
		Field trackField = board_class.getDeclaredField("track"); 
		trackField.setAccessible(true);
		ArrayList<Object> track = (ArrayList<Object>) trackField.get(board_object);

		return track;
	}

	private Object getActivePlayer(Object game) throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		// Get Active Player
		Field players = Class.forName(gamePath).getDeclaredField("players"); 
		players.setAccessible(true);
		ArrayList<Object> players_array = (ArrayList<Object>) players.get(game);

		Field currentPlayerIndex = Class.forName(gamePath).getDeclaredField("currentPlayerIndex"); 
		currentPlayerIndex.setAccessible(true);
		int currentPlayerIndex_value = (int) currentPlayerIndex.get(game);

		Object player = players_array.get(currentPlayerIndex_value);

		return player;

	}



	private Object createOpponentMarble(Object game) throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		Object activePlayerColour = getActiveColor(game);

		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour_Object2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		Object colour_Object3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour_Object4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object marble2 = null;
		Constructor<?> marble_constructor = Class.forName(marblePath).getConstructor( Class.forName(colourPath));

		if(activePlayerColour == colour_Object)
			marble2 = marble_constructor.newInstance(colour_Object2);
		else 
			marble2 = marble_constructor.newInstance(colour_Object);

		return marble2;
	}

	private ArrayList<Object> fourCardsSet(Object board_object, Object game) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException{
		// Create 4 cards
		Constructor<?> queen_constructor = Class.forName(queenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object queen_card = queen_constructor.newInstance("queen card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

		Constructor<?> five_constructor = Class.forName(fivePath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object five_card = five_constructor.newInstance("five card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

		Constructor<?> ten_constructor = Class.forName(tenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object ten_card = ten_constructor.newInstance("ten card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

		Constructor<?> four_constructor = Class.forName(fourPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object four_card = four_constructor.newInstance("four card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


		// Create ArrayList of cards
		ArrayList<Object> hand = new ArrayList<>();
		hand.add(queen_card);hand.add(five_card);hand.add(ten_card);hand.add(four_card);

		return hand;

	}

	private ArrayList<Object> fourCardsSet2(Object board_object, Object game) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException{
		// Create 4 cards
		Constructor<?> ace_constructor = Class.forName(acePath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object ace_card = ace_constructor.newInstance("ace card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

		Constructor<?> king_constructor = Class.forName(kingPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object king_card = king_constructor.newInstance("king card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

		Constructor<?> jack_constructor = Class.forName(jackPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object jack_card = jack_constructor.newInstance("jack card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

		Constructor<?> seven_constructor = Class.forName(sevenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
		Object seven_card = seven_constructor.newInstance("seven card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


		// Create ArrayList of cards
		ArrayList<Object> hand = new ArrayList<>();
		hand.add(ace_card);hand.add(king_card);hand.add(jack_card);hand.add(seven_card);

		return hand;

	}




}
