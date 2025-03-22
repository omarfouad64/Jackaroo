package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import java.util.Random;

import org.junit.Test;

public class Milestone1PrivateTests {

	private String playerPath="model.player.Player";
	private String CPUPath="model.player.CPU";
	private String marblePath="model.player.Marble";

	private String burnerPath="model.card.wild.Burner";
	private String saverPath="model.card.wild.Saver";
	private String wildPath="model.card.wild.Wild";

	private String acePath="model.card.standard.Ace";
	private String fivePath="model.card.standard.Five";
	private String fourPath="model.card.standard.Four";
	private String jackPath="model.card.standard.Jack";
	private String kingPath="model.card.standard.King";
	private String queenPath="model.card.standard.Queen";
	private String sevenPath="model.card.standard.Seven";
	private String standardPath="model.card.standard.Standard";
	private String suitPath="model.card.standard.Suit";
	private String tenPath="model.card.standard.Ten";

	private String cardPath="model.card.Card";
	private String deckPath="model.card.Deck";

	private String colourPath="model.Colour";

	private String ActionExceptionExceptionPath="exception.ActionException";
	private String CannotDiscardExceptionExceptionPath="exception.CannotDiscardException";
	private String CannotFieldExceptionExceptionPath="exception.CannotFieldException";
	private String GameExceptionExceptionPath="exception.GameException";
	private String IllegalDestroyExceptionExceptionPath="exception.IllegalDestroyException";
	private String IllegalMovementExceptionExceptionPath="exception.IllegalMovementException";
	private String IllegalSwapExceptionExceptionPath="exception.IllegalSwapException";
	private String InvalidCardExceptionExceptionPath="exception.InvalidCardException";
	private String InvalidMarbleExceptionExceptionPath="exception.InvalidMarbleException";
	private String InvalidSelectionExceptionExceptionPath="exception.InvalidSelectionException";
	private String SplitOutOfRangeExceptionExceptionPath="exception.SplitOutOfRangeException";


	private String boardPath="engine.board.Board";
	private String boardManagerPath="engine.board.BoardManager";
	private String cellPath="engine.board.Cell";
	private String safeZonePath="engine.board.SafeZone";
	private String cellTypePath="engine.board.CellType";

	private String gamePath="engine.Game";
	private String gameManagerPath="engine.GameManager";

	private static ArrayList<String> cards_csv;



	@Test(timeout = 1000)
	public void testColourIsEnum() throws ClassNotFoundException {
		testIsEnum(Class.forName(colourPath));
	}



	@Test(timeout = 1000)
	public void testBoardManagerIsAnInterface() throws ClassNotFoundException {
		testIsInterface(Class.forName(boardManagerPath));
	}
	@Test(timeout = 1000)
	public void testSuitEnumValues() {
		testEnumValues(suitPath, "suit", new String[] { "HEART", "DIAMOND", "CLUB", "SPADE"});
	}


	@Test(timeout = 1000)
	public void testMarbleInstanceVariableColourPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(marblePath), "colour");
	}


	@Test(timeout = 1000)
	public void testMarbleConstructorExists() throws ClassNotFoundException {
		Class[] parameters = {Class.forName(colourPath)};
		testConstructorExists(Class.forName(marblePath), parameters);
	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableMarblePresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(cellPath), "marble");
	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableCellTypePresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(cellPath), "cellType");
	}


	@Test(timeout = 1000)
	public void testTypeOfTrapInCell() throws Exception {
		testInstanceVariableOfType(Class.forName(cellPath), "trap", boolean.class);

	}

	@Test(timeout = 1000)
	public void testCellTypeInCellGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(cellPath), "getCellType", Class.forName(cellTypePath),true);
	}


	@Test(timeout = 1000)
	public void testTrapInCellSetterExists() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(cellPath), "setTrap", boolean.class,true);
	}


	@Test(timeout = 1000)
	public void testCellTypeInCellSetterLogic() throws Exception{
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		testSetterMethodLogic(cell, "cellType", cellType, Class.forName(cellTypePath));
	}


	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableColourIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(safeZonePath), "colour");
	}

	@Test(timeout = 1000)
	public void testCellsInSafeZoneType() throws Exception {
		testInstanceVariableOfType(Class.forName(safeZonePath), "cells",ArrayList.class);

	}



	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableCellsGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Constructor<?> safeZoneConstructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
		Object safeZone = safeZoneConstructor.newInstance(colour);
		Class safeZoneClass = Class.forName(safeZonePath);
		Field f1 = safeZoneClass.getDeclaredField("cells");
		f1.setAccessible(true);
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		boolean trap = true;
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		Class cellClass = Class.forName(cellPath);
		ArrayList<Object> l = new ArrayList();
		l.add(cell);
		f1.set(safeZone, l);
		testGetterMethodLogic(safeZone, "cells", l);
	}



	@Test(timeout = 1000)
	public void testSafeZoneConstructorExists() throws ClassNotFoundException {
		Class[] parameters = {Class.forName(colourPath)};
		testConstructorExists(Class.forName(safeZonePath), parameters);
	}


	@Test(timeout = 1000)
	public void testGameInstanceVariableBoardPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(gamePath), "board");
	}


	@Test(timeout = 1000)
	public void testGameInstanceVariablePlayersIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(gamePath), "players");
	}

	@Test(timeout = 1000)
	public void testPlayersInGameType() throws Exception {
		testInstanceVariableOfType(Class.forName(gamePath), "players", ArrayList.class);

	}
	@Test(timeout = 1000)
	public void testTurnInGameType() throws Exception {
		testInstanceVariableOfType(Class.forName(gamePath), "turn", int.class);

	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableCurrentPlayerIndexIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(gamePath), "currentPlayerIndex");
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableBoardGetterLogic() {
		try{

			Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			ArrayList<Object> colours = new ArrayList<Object>();
			colours.add(colour);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance("PlayerName");

			Class gameClass = Class.forName(gamePath);
			Field f1 = gameClass.getDeclaredField("board");
			f1.setAccessible(true);

			Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object board = boardConstructor.newInstance(colours,game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());

			f1.set(game, board);

			testGetterMethodLogic(game, "board", board);
		}
		catch(Exception e){
			fail(e.getCause()+" occurred when testing GameInstanceVariableBoardGetterLogic");
		}
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableFirePitGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(gamePath), "getFirePit", ArrayList.class,true);
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableTurnNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(gamePath), "setTurn",int.class , false);
	}

	@Test(timeout = 1000)
	public void testGameConstructorExists() throws ClassNotFoundException {
		Class[] parameters = {String.class};
		testConstructorExists(Class.forName(gamePath), parameters);
	}
	@Test(timeout = 10000)
	public void testGameConstructorInitializationCurrentPlayerIndex() {
		try{

			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance("PlayerName");
			String[] names = {"currentPlayerIndex"};
			Object[] values = {0};
			testConstructorInitialization(game, names, values);
		}
		catch(Exception e){
			fail(e.getCause()+" occurred when testing GameConstructorInitializationCurrentPlayerIndex");
		}
	}


	@Test(timeout = 1000)
	public void testGameInstanceVariableTurnIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsNotFinal(Class.forName(gamePath), "turn");
	}



	//	Subclass of Card
	@Test(timeout = 1000)
	public void testWildIsSubClassOfCard() throws ClassNotFoundException {
		testClassIsSubClass(Class.forName(cardPath), Class.forName(wildPath));
	}



	//	Present
	@Test(timeout = 1000)
	public void testBurnerCardExists() {
		try {
			Class.forName(burnerPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Burner");
		}
	}



	@Test(timeout = 10000)
	public void testSaverCardConstructorInitialization() {
		try{
			Object game= createGame();
			Object board= createBoard(game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());
			int randomInt=(int) (Math.random()*5);
			String name="Saver Card"+randomInt;
			String description="Here's a description with random number"+randomInt;
			//Card constructor 
			Constructor<?> saverConstructor = Class.forName(saverPath).getConstructor(String.class,String.class,Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
			Object card = saverConstructor.newInstance(name,description,board,game);

			//the expected field names and corresponding expected values.
			String[] fieldNames = {"name", "description", "boardManager", "gameManager"};
			Object[] expectedValues = {name,description,board,game};

			// Use a helper method to verify that the card's fields are properly initialized.
			testConstructorInitialization(card, fieldNames, expectedValues);
		}
		catch(Exception e){
			fail(e.getCause()+" error occured while testing SaverCardConstructorInitialization.");

		}
	}

	//	String CARDS private
	@Test(timeout=1000)
	public void testCardsFileInDeckIsPrivate() throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		testInstanceVariableIsPrivate(Class.forName(deckPath), "CARDS_FILE");
	}

	// 	No Setter CARDS FILE 
	@Test(timeout = 1000)
	public void testCardFileInDeckSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(deckPath), "setCARDS_FILE");
	}

	//	ArrayList<Card> cardsPool  present
	@Test(timeout = 1000)
	public void testCardsPoolInDeckExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("cardsPool",deckPath);
	}


	//	loadCardPool(BoardManager boardManager, GameManager gameManager) method public
	@Test(timeout = 1000)
	public void testLoadCardPoolInDeckisPublic() {

		Method m;
		try {
			m = Class.forName(deckPath).getMethod("loadCardPool",Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			assertTrue("loadCardPool expected to be public",Modifier.isPublic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("loadCardPool in Deck class should be public");
		}

	}

	//	loadCardPool method void
	@Test(timeout = 1000)
	public void testLoadCardPoolInDeckisVoid() {

		Method m;
		try {
			m = Class.forName(deckPath).getMethod("loadCardPool",Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			assertTrue("loadCardPool expected to be void",m.getReturnType().equals(void.class));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

	}


	@Test(timeout = 1000)
	public void testSizeLoadCardPoolInDeckReadingCSVLoadingWildCards() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);
			cardsPool_Object= new ArrayList<>();
			writeWildCardsCSVForLoadCards();
			Object game = createGame();
			Object board= createBoard(game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());

			Method loadcardPool= Class.forName(deckPath).getMethod("loadCardPool", Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			loadcardPool.invoke(null, board,game);
			cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);

			assertEquals("Load Card Pool in Deck did not load all the cards correcly to the cardsPool list, size", 
					25,cardsPool_Object.size() );





		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured while testing loadCardPool method check the console ");
		}
		finally {

			reWriteCardsCSVForLoadCards();
		}

	}

	@Test(timeout = 1000)
	public void testDescriptionCardsLoadCardPoolInDeckReadingCSVLoadingWildCards() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);
			cardsPool_Object= new ArrayList<>();
			ArrayList<String> cardsList= writeWildCardsCSVForLoadCards();


			Object game = createGame();
			Object board= createBoard(game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());

			Method loadcardPool= Class.forName(deckPath).getMethod("loadCardPool", Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			loadcardPool.invoke(null, board,game);
			cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			int arraylistCounter=0;
			for (int i = 0; i < cardsList.size(); i++) {
				String[] cardInfo= cardsList.get(i).split(",");
				int code = Integer.parseInt(cardInfo[0]);
				int freq = Integer.parseInt(cardInfo[1]);
				String name= cardInfo[2];
				String description = cardInfo[3];
				for (int j = 0; j < freq; j++) {
					Object card =cardsPool_Object.get(arraylistCounter);
					Field attributeField= Class.forName(cardPath).getDeclaredField("description");
					attributeField.setAccessible(true);
					arraylistCounter++;
					assertEquals("Wild card description loaded incorrectly ", description, (String)attributeField.get(card));
				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" error occured while testing loadCardPool method.");
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}

	@Test(timeout = 2000)
	public void testLoadCardPoolInDeckReadingCSVLoadingStandardCards3() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			ArrayList<String> cardsList= writeStandartCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());
			Method loadcardPool= Class.forName(deckPath).getMethod("loadCardPool", Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			loadcardPool.invoke(null, board,game);
			cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			int arraylistCounter=0;
			for (int i = 0; i < cardsList.size(); i++) {
				String[] cardInfo= cardsList.get(i).split(",");
				int code = Integer.parseInt(cardInfo[0]);
				int freq = Integer.parseInt(cardInfo[1]);
				String name= cardInfo[2];
				String description = cardInfo[3];
				int rank= Integer.parseInt(cardInfo[4]);
				String suit=cardInfo[5];

				for (int j = 0; j < freq; j++) {
					Object card =cardsPool_Object.get(arraylistCounter);
					Field nameField= Class.forName(cardPath).getDeclaredField("name");
					Field descriptionField= Class.forName(cardPath).getDeclaredField("description");

					nameField.setAccessible(true);
					descriptionField.setAccessible(true);

					arraylistCounter++;
					assertEquals("Wrong description loaded for Standard card", description,descriptionField.get(card));


				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException |
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}


	@Test(timeout = 1000)
	public void testDrawCardsInDeckReturn() {

		Method m;
		try {
			m = Class.forName(deckPath).getMethod("drawCards",null);
			assertTrue("drawCards expected to return an Arraylist of Cards",m.getReturnType().equals(ArrayList.class));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("error occured while testing drawCards method check the console "+e.getMessage());
		}

	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableTrapGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		boolean trap = (int)(Math.random()*2)==0? false:true;
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);

		Field trapField = Class.forName(cellPath).getDeclaredField("trap");
		trapField.setAccessible(true);
		trapField.set(cell, trap);
		testIsGetterMethodLogic(cell, "trap", trap);


	}

	@Test(timeout = 10000)
	public void testGameConstructorInitializationMangerForBoard() {

		try{
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance("PlayerName");	





			Class curr = game.getClass();

			Field boardField = null;

			try {
				boardField = curr.getDeclaredField("board");
				boardField.setAccessible(true);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
				fail("error occured while testing game constructor check the console "+e.getMessage());

			}
			Field playersField = null;

			try {
				playersField = curr.getDeclaredField("players");
				playersField.setAccessible(true);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
				fail("error occured while testing game constructor check the console "+e.getMessage());

			}


			Object board_game = boardField.get(game);

			Field gameManagerField=Class.forName(boardPath).getDeclaredField("gameManager");
			gameManagerField.setAccessible(true);


			assertEquals("The board initialization is incorrect, it should reference the game", game,gameManagerField.get(board_game));
		}
		catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured while testing loadCardPool method check the console ");
		}



	}

	@Test(timeout = 100)
	public void testClassStandardExists() {
		try {
			Class.forName(standardPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Standard");
		}
	}


	@Test(timeout = 100)
	public void testClassQueenExists() {
		try {
			Class.forName(queenPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Queen");
		}
	}


	@Test(timeout = 100)
	public void testClassFourExists() {
		try {
			Class.forName(fourPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Four");
		}
	}

	@Test(timeout = 100)
	public void testClassWildExists() {
		try {
			Class.forName(wildPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Wild");
		}
	}


	@Test(timeout = 100)
	public void testClassSaverExists() {
		try {
			Class.forName(saverPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Saver");
		}
	}
	@Test(timeout = 100)
	public void testConstructorCardExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(cardPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorQueenExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(queenPath), inputs);
	}


	@Test(timeout = 100)
	public void testConstructorTenExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(tenPath), inputs);
	}


	@Test(timeout = 100)
	public void testConstructorfiveExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(fivePath), inputs);
	}

	@Test(timeout = 10000)
	public void testAceCardConstructorInitialization()  {
		try{
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");

			Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			ArrayList<Object> colours = new ArrayList<Object>();
			colours.add(colour);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);

			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance("PlayerName");

			Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object board = boardConstructor.newInstance(colours,game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());

			Class boardManagerClass = Class.forName(boardManagerPath);
			Class gameManagerClass = Class.forName(gameManagerPath);


			//Card constructor 
			Constructor<?> cardStandardConstructor = Class.forName(acePath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
			Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

			//the expected field names and corresponding expected values.
			String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
			Object[] expectedValues = {"card","description",1,suit, board, game};

			// Use a helper method to verify that the card's fields are properly initialized.
			testConstructorInitialization(card, fieldNames, expectedValues);
		}
		catch(Exception e ){
			fail(e.getCause()+" occured when testing AceCardConstructorInitialization");
		}
	}


	@Test(timeout = 10000)
	public void testJackCardConstructorInitialization()  {

		try{
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");

			Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			ArrayList<Object> colours = new ArrayList<Object>();
			colours.add(colour);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);

			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance("PlayerName");

			Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object board = boardConstructor.newInstance(colours,game);
			Field trackField= Class.forName(boardPath).getDeclaredField("track");

			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());

			Class boardManagerClass = Class.forName(boardManagerPath);
			Class gameManagerClass = Class.forName(gameManagerPath);


			//Card constructor 
			Constructor<?> cardStandardConstructor = Class.forName(jackPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
			Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

			//the expected field names and corresponding expected values.
			String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
			Object[] expectedValues = {"card","description",11,suit, board, game};

			// Use a helper method to verify that the card's fields are properly initialized.
			testConstructorInitialization(card, fieldNames, expectedValues);
		}
		catch(Exception e ){
			fail(e.getCause()+" occured when testing JackCardConstructorInitialization");
		}
	}


	@Test(timeout = 10000)
	public void testFourCardConstructorInitialization()  {
		try{
			Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");

			Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			ArrayList<Object> colours = new ArrayList<Object>();
			colours.add(colour);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);

			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance("PlayerName");

			Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object board = boardConstructor.newInstance(colours,game);
			Field trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(board)).size());


			Class boardManagerClass = Class.forName(boardManagerPath);
			Class gameManagerClass = Class.forName(gameManagerPath);


			//Card constructor 
			Constructor<?> cardStandardConstructor = Class.forName(fourPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
			Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

			//the expected field names and corresponding expected values.
			String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
			Object[] expectedValues = {"card","description",4,suit, board, game};

			// Use a helper method to verify that the card's fields are properly initialized.
			testConstructorInitialization(card, fieldNames, expectedValues);
		}
		catch(Exception e ){
			fail(e.getCause()+" occured when testing FourCardConstructorInitialization");
		}
	}


	@Test(timeout = 100)
	public void testTestingAceIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(acePath), Class.forName(standardPath));
	}

	@Test(timeout = 100)
	public void testTestingKingIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(kingPath), Class.forName(standardPath));
	}



	@Test(timeout = 100)
	public void testTestingtenIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(tenPath), Class.forName(standardPath));
	}
	@Test(timeout = 100)
	public void testTestingFourIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(fourPath), Class.forName(standardPath));
	}

	@Test(timeout = 100)
	public void testCardInstanceVariablGameManagerIsPresent() throws NoSuchFieldException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(cardPath), "gameManager");
	}


	@Test(timeout = 100)
	public void testCardInstanceVariablDescriptionIsPresent() throws NoSuchFieldException, ClassNotFoundException {
		testInstanceVariableIsPresent( Class.forName(cardPath), "description");
	}


	@Test(timeout = 100)
	public void testCardInstanceVariableRankisFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field rankField = Class.forName(standardPath).getDeclaredField("rank");
		assertTrue("The rank instance variable in class Card should be final", java.lang.reflect.Modifier.isFinal(rankField.getModifiers()));
	}


	@Test(timeout = 100)
	public void testCardInstanceVariablSuitIsPresent() throws NoSuchFieldException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(standardPath), "suit");
	}

	@Test(timeout = 100)
	public void testNameGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(cardPath),"getName",String.class,true);
	}


	@Test(timeout = 100)
	public void testGetterLogicForRankInClassStandard() throws Exception {
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");

		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		ArrayList<Object> colours = new ArrayList<Object>();
		colours.add(colour);
		colours.add(colour2);
		colours.add(colour3);
		colours.add(colour4);

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");

		Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		Object board = boardConstructor.newInstance(colours,game);


		Class boardManagerClass = Class.forName(boardManagerPath);
		Class gameManagerClass = Class.forName(gameManagerPath);

		int rank= (int) (Math.random() * 100);
		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","descriptiontest",rank ,suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};

		Object[] expectedValues = {"card","descriptiontest",rank,suit, board, game};


		testGetterLogic(card, "rank", rank);
	}

	@Test(timeout = 1000)
	public void testSetterForInstanceVariableDescriptionDoesNotExistInAnyClass() throws Exception {
		String[] classs = { cardPath , standardPath, acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testSetterAbsent("description", classs);
	}

	@Test(timeout = 1000)
	public void testSetterForInstanceVariableSuitDoesNotExistInAnyClass() throws Exception {
		String[] classs = { cardPath , standardPath, acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testSetterAbsent("suit", classs);
	}

	@Test(timeout = 1000)
	public void testGetterForInstanceVariableDescriptionDoesNotExistInWeaponSubClasses() throws Exception {
		String[] subClasses = { acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testGetterAbsentInSubclasses("description", subClasses);
	}


	@Test(timeout = 10000)
	public void testGameManagerModifier2() throws Exception {

		testInstanceVariableIsProtected(Class.forName(cardPath), "gameManager");

	}

	@Test(timeout = 10000)
	public void testNameModifier3() throws Exception {

		testInstanceVariableIsPrivate(Class.forName(cardPath), "name");

	}


	@Test(timeout = 10000)
	public void testStandardCardSuitModifiers2() throws Exception {

		testInstanceVariableIsPrivate(Class.forName(standardPath), "suit");
	}



	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(playerPath), "name");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameOfTypeString() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(playerPath), "name", String.class);
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(playerPath), "getName",String.class);
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(playerPath), "colour");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(playerPath), "getColour", Class.forName(colourPath));
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourOfTypeColour() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> class_name = Class.forName(colourPath);
		testInstanceVariableOfType(Class.forName(playerPath), "colour", class_name);
	}



	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(playerPath), "selectedMarbles");
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesOfTypeArrayListOfMarbles() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> marbleClass = Class.forName(marblePath); 
		Field field = playerClass.getDeclaredField("selectedMarbles");
		Type genericType = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		assertEquals("selectedMarbles must be an ArrayList of Marble", marbleClass, typeArguments[0]);
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(playerPath), "setSelectedMarbles");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedCardIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(playerPath), "selectedCard");
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedCardIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(playerPath), "selectedCard");
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedCardGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(playerPath), "getSelectedCard", Class.forName(cardPath));
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesOfTypeArrayList() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(playerPath), "marbles", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(playerPath), "setMarbles");
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(playerPath), "marbles");
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandOfTypeArrayList() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(playerPath), "hand", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandSetterExists() throws ClassNotFoundException {
		testSetterMethodExistInClass(Class.forName(playerPath), "setHand", ArrayList.class);
	}



	@Test(timeout = 1000)
	public void testPlayerConstructorExists() throws ClassNotFoundException {
		Class<?> colour_class = Class.forName(colourPath);
		Class[] parameters = {String.class,colour_class};
		testConstructorExists(Class.forName(playerPath), parameters);
	}




	@Test(timeout = 1000)
	public void testPlayerConstructorInitializationSelectedCardCorrect() throws Exception{
		Class<?> colour_class = Class.forName(colourPath);
		Class<?> playerClass = Class.forName(playerPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_Object = player_constructor.newInstance(input_name, colour_Object);
		Field f1 = playerClass.getDeclaredField("selectedCard");
		f1.setAccessible(true);
		Object expectedSelectedCard = f1.get(player_Object);


		assertTrue("The selectedCard initialization isn't correct",expectedSelectedCard == null);
	}






	@Test(timeout = 1000)
	public void testCPUClassExists() {
		try {
			Class.forName(CPUPath);
		} catch (ClassNotFoundException e) {
			fail("missing class CPU");
		}
	}


	@Test(timeout = 1000)
	public void testCPUInstanceVariableBoardManagerIsPrivate() throws SecurityException, ClassNotFoundException, NoSuchFieldException {
		testInstanceVariableIsPrivate(Class.forName(CPUPath), "boardManager");
	}


	@Test(timeout = 1000)
	public void testCPUInstanceVariableBoardManagerGetterIsAbsent() throws ClassNotFoundException {
		testGetterMethodIsAbsentInClass(Class.forName(CPUPath), "boardManager");
	}



	@Test(timeout = 1000)
	public void testBoardInstanceVariableGameManagerIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(boardPath), "gameManager");
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableGameManagerIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(boardPath), "gameManager");
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariableGameManagerSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(boardPath), "gameManager");
	}


	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackOfTypeArrayList() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(boardPath), "track", ArrayList.class);
	}


	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(boardPath), "getTrack", ArrayList.class);
	}


	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesOfTypeArrayList() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(boardPath), "safeZones", ArrayList.class);
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesIsFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(boardPath), "safeZones");
	}



	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesGetterLogic() {

		try{
			Class<?> board_class = Class.forName(boardPath); 
			Class<?> game_manager_class = Class.forName(gameManagerPath); 
			Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);


			String player_name = generateRandomString(5);
			Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = game_constructor.newInstance(player_name);

			ArrayList<Object> colourOrder = new ArrayList();
			Object colour_Object = null;
			while (colourOrder.size()<4) {
				Random random = new Random();
				int randomNumber = random.nextInt(4) + 1;
				switch(randomNumber) {
				case 1:{
					colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
					break;
				}
				case 2:{
					colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
					break;
				}
				case 3:{
					colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
					break;
				}
				case 4:{
					colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
					break;
				}
				}
				if (!colourOrder.contains(colour_Object)) {
					colourOrder.add(colour_Object);
				}
			}
			Object board_object = board_constructor.newInstance(colourOrder,game);
			ArrayList <Object> safeZones = new ArrayList<>();


			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
			Object safe_zone_instance = null;
			for (int i = 0; i < 4; i++)
			{
				safe_zone_instance = safe_zone_constructor.newInstance(colourOrder.get(i));
				safeZones.add(safe_zone_instance);
			}
			Field safe_zones_filed = Class.forName(boardPath).getDeclaredField("safeZones");
			safe_zones_filed.setAccessible(true);
			safe_zones_filed.set(board_object, safeZones);

			testGetterMethodLogic(board_object, "safeZones", safeZones);
		}

		catch(Exception e){
			fail(e.getCause()+" error occured while testing BoardInstanceVariableSafeZonesGetterLogic.");


		}
	}



	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(boardPath), "splitDistance");
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(boardPath), "splitDistance");
	}

	@Test(timeout = 1000)
	public void testBoardConstructorExists() throws ClassNotFoundException {
		Class<?> game_manager_class = Class.forName(gameManagerPath);
		Class[] parameters = {ArrayList.class,game_manager_class};
		testConstructorExists(Class.forName(boardPath), parameters);
	}



	@Test(timeout = 2000)
	public void testBoardConstructorInitializationTrapCellsAreCorrect() {
		Object boardObject =null;
		try{

			for (int iteration = 0; iteration < 100; iteration++) {
				Class<?> boardClass = Class.forName(boardPath); 
				Class<?> gameManagerClass = Class.forName(gameManagerPath); 
				Constructor<?> boardConstructor = boardClass.getConstructor(ArrayList.class, gameManagerClass);

				String playerName = generateRandomString(5);
				Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
				Object game = gameConstructor.newInstance(playerName);


				ArrayList<Object> colourOrder = new ArrayList<>();
				Object colour_Object = null;
				Random random = new Random(); 
				while (colourOrder.size() < 4) {
					int randomNumber = random.nextInt(4) + 1; 
					switch(randomNumber) {
					case 1:
						colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
						break;
					case 2:
						colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
						break;
					case 3:
						colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
						break;
					case 4:
						colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
						break;
					}
					if (!colourOrder.contains(colour_Object)) {
						colourOrder.add(colour_Object);
					}
				}
				boardObject = boardConstructor.newInstance(colourOrder, game);

				Field trackField = boardClass.getDeclaredField("track");
				trackField.setAccessible(true);
				ArrayList<Object> track = (ArrayList<Object>) trackField.get(boardObject);

				Class<?> cellClass = Class.forName(cellPath);
				Method isTrapMethod = cellClass.getMethod("isTrap");

				int trapCount = 0;
				for (Object cell : track) {
					if ((Boolean) isTrapMethod.invoke(cell)) {
						trapCount++;
					}
				}
				assertEquals("The number of trapped cells after constructor initialization isn't correct", 
						8, trapCount);
			}
		}
		catch(Exception e){
			fail(e.getCause()+" error occured while testing Board Constructor.");

		}
		Field trackField;
		try {
			trackField = Class.forName(boardPath).getDeclaredField("track");
			trackField.setAccessible(true);
			assertEquals("The attribute track in Board should be intialized with 100 cells", 100, ((ArrayList<Object>)trackField.get(boardObject)).size());
		} catch (NoSuchFieldException | SecurityException
				| ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" error occured while testing Board Constructor.");

		}



	}
	
	@Test(timeout = 2000)
	public void testAssignTrapCellAlwaysAddsNewTrap() {
	    try {
	        Class<?> boardClass = Class.forName(boardPath);
	        Class<?> gameManagerClass = Class.forName(gameManagerPath);
	        Constructor<?> boardConstructor = boardClass.getConstructor(ArrayList.class, gameManagerClass);

	        Class<?> cellTypeClass = Class.forName(cellTypePath);
	        Class<?> cellClass = Class.forName(cellPath);
	        Constructor<?> cellConstructor = cellClass.getConstructor(cellTypeClass);
	        Method setTrapMethod = cellClass.getMethod("setTrap", boolean.class);
	        Method isTrapMethod = cellClass.getMethod("isTrap");
	        Method getCellTypeMethod = cellClass.getMethod("getCellType");

	        // Create a game object (needed for the board constructor).
	        String playerName = "testPlayer";
	        Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
	        Object game = gameConstructor.newInstance(playerName);

	        ArrayList<Object> colourOrder = new ArrayList<>();
	        Object colourObject = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
	        for (int i = 0; i < 4; i++) {
	            colourOrder.add(colourObject);
	        }

	        Object boardObject = boardConstructor.newInstance(colourOrder, game);

	        // We'll run the scenario many times to catch the faulty implementation.
	        for (int iteration = 0; iteration < 100; iteration++) {
	            ArrayList<Object> track = new ArrayList<>();
	            // Create the candidate cell at index 0: NORMAL and untrapped.
	            Object candidateCell = cellConstructor.newInstance(
	                Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL")
	            );
	            // (Do not mark candidateCell as trapped so that it is valid.)
	            track.add(candidateCell);
	            
	            // For indices 1 to 99, create NORMAL cells that are already trapped.
	            Object normalEnum = Enum.valueOf((Class<Enum>) cellTypeClass, "NORMAL");
	            for (int i = 1; i < 100; i++) {
	                Object trappedCell = cellConstructor.newInstance(normalEnum);
	                // Mark this cell as trapped.
	                setTrapMethod.invoke(trappedCell, true);
	                track.add(trappedCell);
	            }

	            // Set the board's "track" field to our track.
	            Field trackField = boardClass.getDeclaredField("track");
	            trackField.setAccessible(true);
	            trackField.set(boardObject, track);

	            // Count initial traps among NORMAL cells. We expect 99 (all but the candidate).
	            int initialTrapCount = 0;
	            for (Object cell : track) {
	                Object cellType = getCellTypeMethod.invoke(cell);
	                if (cellType.equals(normalEnum)) {
	                    if ((Boolean) isTrapMethod.invoke(cell)) {
	                        initialTrapCount++;
	                    }
	                }
	            }
	            assertEquals("Initial trap count among NORMAL cells should be 99", 
	                         99, initialTrapCount);

	            Method assignTrapCellMethod = boardClass.getDeclaredMethod("assignTrapCell", (Class<?>[]) null);
	            assignTrapCellMethod.setAccessible(true);
	            assignTrapCellMethod.invoke(boardObject, (Object[]) null);

	            int finalTrapCount = 0;
	            for (Object cell : track) {
	                Object cellType = getCellTypeMethod.invoke(cell);
	                if (cellType.equals(normalEnum)) {
	                    if ((Boolean) isTrapMethod.invoke(cell)) {
	                        finalTrapCount++;
	                    }
	                }
	            }
	            if (finalTrapCount != 100) {
	                fail("Expected trap count to increase by 1 (to 100), but it is " + finalTrapCount);
	            }
	        }
	    } catch(Exception e) {
	        fail(e.getCause() + " occurred when testing assignTrapCellAlwaysAddsNewTrap");
	    }
	}

	@Test(timeout = 2000)
	public void testAssignTrapCellAlwaysAddsNewTrapSecond() {
		try{
			Class<?> boardClass = Class.forName(boardPath);
			Class<?> gameManagerClass = Class.forName(gameManagerPath);
			Constructor<?> boardConstructor = boardClass.getConstructor(ArrayList.class, gameManagerClass);

			Class<?> cellTypeClass = Class.forName(cellTypePath);
			Class<?> cellClass = Class.forName(cellPath);
			Constructor<?> cellConstructor = cellClass.getConstructor(cellTypeClass);
			Method setTrapMethod = cellClass.getMethod("setTrap", boolean.class);
			Method isTrapMethod = cellClass.getMethod("isTrap");
			Method getCellTypeMethod = cellClass.getMethod("getCellType");

			String playerName = "testPlayer";
			Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = gameConstructor.newInstance(playerName);

			ArrayList<Object> colourOrder = new ArrayList<>();
			Object colourObject = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			for (int i = 0; i < 4; i++) {
				colourOrder.add(colourObject);
			}

			Object boardObject = boardConstructor.newInstance(colourOrder, game);


			for (int iteration = 0; iteration < 100; iteration++) {

				ArrayList<Object> track = new ArrayList<>();

				Object normalCellUntrapped = cellConstructor.newInstance(
						Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL")
						);
				track.add(normalCellUntrapped);

				Object normalCellTrapped = cellConstructor.newInstance(
						Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL")
						);
				//setTrapMethod.invoke(normalCellTrapped, true); 
				track.add(normalCellTrapped);

				for (int i = 2; i < 100; i++) {
					Object baseCell = cellConstructor.newInstance(
							Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "BASE")
							);
					track.add(baseCell);
				}

				Field trackField = boardClass.getDeclaredField("track");
				trackField.setAccessible(true);
				trackField.set(boardObject, track);

				int initialTrapCount = 0;
				for (Object cell : track) {
					if ((Boolean) isTrapMethod.invoke(cell)) {
						initialTrapCount++;
					}
				}
				Object normalEnum = Enum.valueOf((Class<Enum>) cellTypeClass, "NORMAL");

				Method assignTrapCellMethod = boardClass.getDeclaredMethod("assignTrapCell", (Class<?>[]) null);
				assignTrapCellMethod.setAccessible(true);
				assignTrapCellMethod.invoke(boardObject, (Object[]) null);
				Field f_2 = Class.forName(boardPath).getDeclaredField("track");
				f_2.setAccessible(true);
				ArrayList <?> track_value = (ArrayList<?>) f_2.get(boardObject);

				int finalTrapCount = 0;
				for (Object cell : track_value) {
					if ((Boolean) isTrapMethod.invoke(cell)) {
						finalTrapCount++;
						Object cellType = getCellTypeMethod.invoke(cell);
						assertEquals("Trapped cell should be NORMAL", normalEnum, cellType);
					}
				}

				//assertEquals(" Expected exactly 2 trapped cells", initialTrapCount + 1, finalTrapCount);
			}
		}
		catch(Exception e ){
			fail(e.getCause()+" occured when testing AssignTrapCellAlwaysAddsNewTrap");
		}

	}

	@Test(timeout = 100)
	public void testClassIsAbstractActionException() throws ClassNotFoundException  {
		testClassIsAbstract(Class.forName(ActionExceptionExceptionPath));
	}


	@Test(timeout = 1000)
	public void testClassIsSubclassInvalidSelectionException () throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(InvalidSelectionExceptionExceptionPath), Class.forName(GameExceptionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassInvalidMarbleException () throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(InvalidMarbleExceptionExceptionPath), Class.forName(InvalidSelectionExceptionExceptionPath));
	}



	@Test(timeout = 1000)
	public void testClassIsSubclassSplitOutOfRangeException () throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(SplitOutOfRangeExceptionExceptionPath), Class.forName(InvalidSelectionExceptionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassIllegalDestroyException () throws ClassNotFoundException  {
		testClassIsSubclass(Class.forName(IllegalDestroyExceptionExceptionPath), Class.forName(ActionExceptionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassCannotDiscardException () throws ClassNotFoundException  {
		testClassIsSubclass(Class.forName(CannotDiscardExceptionExceptionPath), Class.forName(ActionExceptionExceptionPath));
	}

	@Test(timeout = 100)
	public void testEmptyConstructorInvalidSelectionException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(InvalidSelectionExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testEmptyConstructorInvalidMarbleException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(InvalidMarbleExceptionExceptionPath), inputs);
	}



	@Test(timeout = 100)
	public void testEmptyConstructorIllegalSwapException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(IllegalSwapExceptionExceptionPath), inputs);
	}




	@Test(timeout = 100)
	public void testEmptyConstructorCannotFieldException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(CannotFieldExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorInvalidSelectionException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(InvalidSelectionExceptionExceptionPath), inputs);
	}




	@Test(timeout = 100)
	public void testConstructorInvalidMarbleException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(InvalidMarbleExceptionExceptionPath), inputs);
	}



	@Test(timeout = 100)
	public void testConstructorIllegalMovementException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(IllegalMovementExceptionExceptionPath), inputs);
	}



	@Test(timeout = 10000)
	public void testInvalidCardExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> invalidCardExceptionConstructor = Class.forName(InvalidCardExceptionExceptionPath).getConstructor(String.class);
		Object invalidCardException = invalidCardExceptionConstructor.newInstance("Invalid card");
		String[] names = {"detailMessage"};
		Object[] values = {"Invalid card"};
		testExceptionConstructorInitialization(invalidCardException, names, values);
	}




	@Test(timeout = 10000)
	public void testSplitOutOfRangeExceptionConstructorInitialization() throws Exception{

		Constructor<?> splitOutOfRangeExceptionConstructor = Class.forName(SplitOutOfRangeExceptionExceptionPath).getConstructor(String.class);
		Object splitOutOfRangeException = splitOutOfRangeExceptionConstructor.newInstance("This split distance is invalid");
		String[] names = {"detailMessage"};
		Object[] values = {"This split distance is invalid"};
		testExceptionConstructorInitialization(splitOutOfRangeException, names, values);
	}

	@Test(timeout = 10000)
	public void testIllegalMovementExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> illegalMovementExceptionConstructor = Class.forName(IllegalMovementExceptionExceptionPath).getConstructor(String.class);
		Object illegalMovementException = illegalMovementExceptionConstructor.newInstance("Illegal movement");
		String[] names = {"detailMessage"};
		Object[] values = {"Illegal movement"};
		testExceptionConstructorInitialization(illegalMovementException, names, values);
	}

	@Test(timeout = 10000)
	public void testIllegalDestroyExceptionConstructorInitialization() throws Exception{

		Constructor<?> illegalDestroyExceptionConstructor = Class.forName(IllegalDestroyExceptionExceptionPath).getConstructor(String.class);
		Object illegalDestroyException = illegalDestroyExceptionConstructor.newInstance("This action will return the marble to it's home zone");
		String[] names = {"detailMessage"};
		Object[] values = {"This action will return the marble to it's home zone"};
		testExceptionConstructorInitialization(illegalDestroyException, names, values);
	}

	@Test(timeout = 10000)
	public void testCannotFieldExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> cannotFieldExceptionConstructor = Class.forName(CannotFieldExceptionExceptionPath).getConstructor(String.class);
		Object cannotFieldException = cannotFieldExceptionConstructor.newInstance("Illegal field of marble");
		String[] names = {"detailMessage"};
		Object[] values = {"Illegal field of marble"};
		testExceptionConstructorInitialization(cannotFieldException, names, values);
	}













	/////////////////The new helper method/////
	private void testExceptionConstructorInitialization(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class<?> curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}

			//f.setAccessible(true);
			Method messageGetter=curr.getDeclaredMethod("getMessage");
			messageGetter.setAccessible(true);
			Object message= messageGetter.invoke(createdObject);


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + "message" + "\" correctly.",
					currValue, message);

		}
	}



	////////////////////////////////////////////	Helper Methods//////////////////////////////////////////////////////////////

	private void testIsEnum(Class eClass) {
		assertTrue(eClass.getSimpleName() + " should be an Enum", eClass.isEnum());
	}

	private void testEnumValues(String path, String name, String[] values) {
		try {
			Class eClass = Class.forName(path);
			for(int i=0;i<values.length;i++) {
				try {
					Enum.valueOf((Class<Enum>)eClass, values[i]);
				}
				catch(IllegalArgumentException e) {
					fail(eClass.getSimpleName() + " enum can be " + values[i]);
				}
			}
		}
		catch(ClassNotFoundException e) {
			fail("There should be an enum called " + name + "in package " + path);
		}

	}




	private void testGetterLogicWallBase(Object createdObject, String name,String name2, Object value) throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);


		Character c = name.charAt(0);

		String methodName = "get" + name2;

		if (value.getClass().equals(Boolean.class))
			methodName = "is" + Character.toUpperCase(c) + name.substring(1, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
				+ " should return the correct value of variable \"" + name + "\".",
				value, m.invoke(createdObject));

	}

	// NEW


	private void testGameConstructorInitialization(Object createdObject, String[] names, Object[] values) throws IllegalArgumentException, IllegalAccessException {
		for(int i=0;i<names.length;i++) {
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			Field f = null;
			while(f == null) {
				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);
			if(currName.equals("firepit")) {
				ArrayList<Object> w = (ArrayList<Object>) f.get(createdObject);
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName
						+ "\" correctly. the size of weapons should be equals to the 0 initially.", 
						currValue, w.size());
			}	
			else	
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName + "\" correctly.", 
						currValue, f.get(createdObject));

		}
	}





	private Object createCard(Object game,Object board) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(fourPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);
		return card;
	}



	private Object createGame() throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = null;
		try{
			game = gameConstructor.newInstance("PlayerName");
		}
		catch(IndexOutOfBoundsException e){
			fail("Index out of bound occured when creating a new Game, please make sure that the arrays in both classes Game and Board are intialized with the correct size according to the game discription");
		}
		catch(NullPointerException e){
			fail("NullPointerException occured when creating a new Game, please make sure that the arrays in both classes Game and Board are intialized with the correct size according to the game discription");
		}

		return game;
	}
	private Object createStandardCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class, int.class,Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
		Object card = cardConstructor.newInstance("Two","description",2,suit,board,game);


		return card;
	}
	private Object createQueenCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(queenPath).getConstructor(String.class,String.class, Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
		Object card = cardConstructor.newInstance("Queen","description",suit,board,game);


		return card;
	}
	private Object createKingCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(kingPath).getConstructor(String.class,String.class, Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
		Object card = cardConstructor.newInstance("King","description",suit,board,game);


		return card;
	}
	private Object createAceCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(acePath).getConstructor(String.class,String.class, Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
		Object card = cardConstructor.newInstance("ACE","description",suit,board,game);


		return card;
	}
	private Object createBurnerCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object card = cardConstructor.newInstance("Burner","description",board,game);


		return card;
	}
	private Object createSaverCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(saverPath).getConstructor(String.class,String.class,
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object card = cardConstructor.newInstance("Saver","description",board,game);


		return card;
	}

	private Object createBoard(Object game) throws InstantiationException, IllegalAccessException, 
	IllegalArgumentException, NoSuchMethodException,
	SecurityException, ClassNotFoundException, NoSuchFieldException {

		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		ArrayList<Object> colours = new ArrayList<Object>();
		colours.add(colour);
		colours.add(colour2);
		colours.add(colour3);
		colours.add(colour4);


		Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		Object board =null;
		try{
			board = boardConstructor.newInstance(colours,game);
		}
		catch(IndexOutOfBoundsException e){
			fail("Index out of bound occured when creating a new board, please make sure that the arrays are intialized with the correct size according to the game discription");
		}
		catch(NullPointerException e){
			fail("NullPointerException occured when creating a new board, please make sure that the arrays are intialized with the correct size according to the game discription");
		}
		catch(InvocationTargetException e){
			fail("Index out of bound occured when creating a new board, please make sure that the arrays are intialized with the correct size according to the game discription");

		}

		return board;
	}
	private Object createTenCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(tenPath).getConstructor(String.class,String.class,Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
		Object card = cardConstructor.newInstance("Ten","description",suit,board,game);


		return card;
	}
	private Object createFiveCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(fivePath).getConstructor(String.class,String.class,Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "DIAMOND");
		Object card = cardConstructor.newInstance("Five","description",suit,board,game);


		return card;
	}
	private Object createFourCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(fourPath).getConstructor(String.class,String.class,Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "DIAMOND");
		Object card = cardConstructor.newInstance("Four","description",suit,board,game);


		return card;
	}
	private Object createSevenCard(Object game, Object board) throws NoSuchMethodException, SecurityException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {


		Constructor<?> cardConstructor = Class.forName(sevenPath).getConstructor(String.class,String.class,Class.forName(suitPath),
				Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "CLUB");
		Object card = cardConstructor.newInstance("Seven","description",suit,board,game);


		return card;
	}





	private static void savingCardsCSV() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Cards.csv"));
		cards_csv= new ArrayList<>();
		while (br.ready()) {
			String nextLine = br.readLine();
			cards_csv.add(nextLine);
		}
	}


	private static void reWriteCardsCSVForLoadCards() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("Cards.csv");

		//	code, frequency, name, description, rank, suit

		for (int i = 0; i < cards_csv.size(); i++) {
			csvWriter.println(cards_csv.get(i));
		}
		csvWriter.flush();
		csvWriter.close();

	}

	private void testIsGetterMethodLogic(Object createdObject, String varName, Object expectedValue) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException, InvocationTargetException {
		Field f = null;
		Class curr = createdObject.getClass();

		while(f == null) {
			if(curr == Object.class)
				fail("Class should have " + varName + " as an instance variable in class " + curr.getSimpleName()
						+" or one of its superclasses");
			try {
				f = curr.getDeclaredField(varName);
			}
			catch(NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}

		f.setAccessible(true);
		f.set(createdObject, expectedValue);

		String methodName = "";
		methodName = "is" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);

		Method m = createdObject.getClass().getDeclaredMethod(methodName);
		m.invoke(createdObject);
		assertTrue("The method \"" + methodName
				+ "\" in class "+ curr.getSimpleName()+" should return the correct value of variable \"" + varName + "\"."
				, m.invoke(createdObject).equals(expectedValue));
	}

	private ArrayList<String> writeStandartCSVForLoadCards() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("Cards.csv");

		//	code, frequency, name, description, rank, suit

		ArrayList<String> list_= new ArrayList<>();
		csvWriter.println("7,3,Seven,Moves 7 steps on one marble or a total of 7 steps on two marbles,7,HEART");
		list_.add("7,3,Seven,Moves 7 steps on one marble or a total of 7 steps on two marbles,7,HEART");


		csvWriter.println("0,2,Eight,Moves 8 steps.,8,DIAMOND");
		list_.add("0,2,Eight,Moves 8 steps.,8,DIAMOND");

		csvWriter.println("13,5,King,Places a marble in the base hole or moves 13 steps killing all marbles in their path.,13,DIAMOND");
		list_.add("13,5,King,Places a marble in the base hole or moves 13 steps killing all marbles in their path.,13,DIAMOND");

		csvWriter.println("0,5,Three,Moves 3 steps.,3,HEART");
		list_.add("0,5,Three,Moves 3 steps.,3,HEART");

		csvWriter.println("0,4,Six,Moves 6 steps.,6,CLUB");
		list_.add("0,4,Six,Moves 6 steps.,6,CLUB");

		csvWriter.println("1,4,Ace,Places a marble in the base hole or moves 1 step.,1,HEART");
		list_.add("1,4,Ace,Places a marble in the base hole or moves 1 step.,1,HEART");

		csvWriter.println("12,3,Queen,Selects a random card from next player to discard or moves 12 steps.,12,DIAMOND");
		list_.add("12,3,Queen,Selects a random card from next player to discard or moves 12 steps.,12,DIAMOND");

		csvWriter.println("0,2,Two,Moves 3 steps.,2,HEART");
		list_.add("0,2,Two,Moves 3 steps.,2,HEART");

		csvWriter.println("11,5,Jack,Swaps your own marble with another or moves 11 steps.,11,HEART");
		list_.add("11,5,Jack,Swaps your own marble with another or moves 11 steps.,11,HEART");

		csvWriter.println("0,5,Nine,Move9 steps.,9,HEART");
		list_.add("0,5,Nine,Move9 steps.,9,HEART");

		csvWriter.println("4,5,Four,Move4 steps.,4,HEART");
		list_.add("4,5,Four,Move4 steps.,4,HEART");

		csvWriter.println("5,1,Five,Move5 steps.,5,CLUB");
		list_.add("5,1,Five,Move5 steps.,5,CLUB");

		csvWriter.println("10,5,Ten,Move4 steps.,10,HEART");
		list_.add("10,5,Ten,Move4 steps.,10,HEART");

		csvWriter.flush();
		csvWriter.close();
		return list_;

	}
	private ArrayList<String> returnCSVOfCards() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("Cards.csv");

		//	code, frequency, name, description, rank, suit

		ArrayList<String> list_= new ArrayList<>();
		csvWriter.println("7,3,Seven,Moves 7 steps on one marble or a total of 7 steps on two marbles,7,HEART");
		list_.add("7,3,Seven,Moves 7 steps on one marble or a total of 7 steps on two marbles,7,HEART");


		csvWriter.println("0,2,Eight,Moves 8 steps.,8,DIAMOND");
		list_.add("0,2,Eight,Moves 8 steps.,8,DIAMOND");

		csvWriter.println("13,5,King,Places a marble in the base hole or moves 13 steps killing all marbles in their path.,13,DIAMOND");
		list_.add("13,5,King,Places a marble in the base hole or moves 13 steps killing all marbles in their path.,13,DIAMOND");

		csvWriter.println("0,5,Three,Moves 3 steps.,3,HEART");
		list_.add("0,5,Three,Moves 3 steps.,3,HEART");

		csvWriter.println("0,4,Six,Moves 6 steps.,6,CLUB");
		list_.add("0,4,Six,Moves 6 steps.,6,CLUB");

		csvWriter.println("1,4,Ace,Places a marble in the base hole or moves 1 step.,1,HEART");
		list_.add("1,4,Ace,Places a marble in the base hole or moves 1 step.,1,HEART");

		csvWriter.println("12,3,Queen,Selects a random card from next player to discard or moves 12 steps.,12,DIAMOND");
		list_.add("12,3,Queen,Selects a random card from next player to discard or moves 12 steps.,12,DIAMOND");

		csvWriter.println("0,2,Two,Moves 3 steps.,2,HEART");
		list_.add("0,2,Two,Moves 3 steps.,2,HEART");

		csvWriter.println("11,5,Jack,Swaps your own marble with another or moves 11 steps.,11,HEART");
		list_.add("11,5,Jack,Swaps your own marble with another or moves 11 steps.,11,HEART");

		csvWriter.println("0,5,Nine,Move9 steps.,9,HEART");
		list_.add("0,5,Nine,Move9 steps.,9,HEART");

		csvWriter.flush();
		csvWriter.close();
		return list_;

	}

	private ArrayList<String> writeCSVForLoadCards() throws FileNotFoundException {

		PrintWriter csvWriter = new PrintWriter("Cards.csv");
		ArrayList<String> cardsList=new ArrayList<>();
		int randomCount=(int) (Math.random()*(10)+1)+5;

		int[] standardCodes= {0,1,13,12,11,4,5,7,10};
		String[] standardNames= {"Standard","ACE","KING","QUEEN","JACK","FOUR","FIVE","SEVEN","TEN"};
		String[] suits= {"CLUB","HEART","DIAMOND","SPADE"};
		int[] ranks= {2,1,13,12,11,4,5,7,10};
		for (int i = 0; i < randomCount; i++) {

			int randomFreq=(int) (Math.random()*(10)+1)+5;
			int randomValue= (int) (Math.random()*2);
			//		0 means standard card 1 means wild card

			int randomWild= (int) (Math.random()*2);
			//		0 means burner Code 14, 1 means saver code 15

			if(randomValue==1) {
				if(randomWild==0) {

					String write_line= "14,"+randomFreq+",randomBurner,randomDescription inserting Random count"+
							randomCount+",,";
					csvWriter.println(write_line);
					cardsList.add(write_line);
				}
				else {

					String write_line= "15,"+randomFreq+",randomSaver,randomDescription inserting Random count"+
							randomCount+",,";
					csvWriter.println(write_line);
					cardsList.add(write_line);
				}
			}
			else {

				int randomSuitIndex=(int) (Math.random()*(suits.length));

				int randomIndex=(int) (Math.random()*(standardNames.length));


				String description= "genrating random description from suit "+suits[randomSuitIndex]+
						" and random standard card "+standardNames[randomIndex]+",";


				String write_line= standardCodes[randomIndex]+","+randomFreq+","+standardNames[randomIndex]+","+
						description+ranks[randomIndex]+","+suits[randomSuitIndex];

				csvWriter.println(write_line);
				cardsList.add(write_line);


			}
		}





		//	code, frequency, name, description, rank, suit


		csvWriter.flush();
		csvWriter.close();
		return cardsList;

	}

	private ArrayList<String> writeWildCardsCSVForLoadCards() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("Cards.csv");

		ArrayList<String> cardList= new ArrayList<>();
		//	code, frequency, name, description

		csvWriter.println("14,20,MarbleBurner,Selects an opponent marble, that is on track, to send home.,,");
		cardList.add("14,20,MarbleBurner,Selects an opponent marble, that is on track, to send home.,,");
		csvWriter.println("15,5,MarbleSaver,Selects one of your marbles, that is on track base, to send to a random empty safe zone cell.,,");
		cardList.add("15,5,MarbleSaver,Selects one of your marbles, that is on track base, to send to a random empty safe zone cell.,,\"");

		csvWriter.flush();
		csvWriter.close();

		return cardList;

	}





	// Helper method to generate a random string of specified length
	private String generateRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}
		return sb.toString();
	}


	private void testConstructorInitializationSafeZone(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			ArrayList<Object> h = (ArrayList<Object>) currValue;
			int len = h.size() ;


			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);
			ArrayList<Object> h2 = (ArrayList<Object>) f.get(createdObject);
			int len2 = h2.size() ;


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" with the correct size as required in the milestone.",
					len, len2);
		}

	}



	private void testLoadMethodExistsInClass(Class aClass, String methodName, Class inputType) {
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName,inputType);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		String varName = "";

		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one "
				+ inputType.getSimpleName() + " parameter.", found);

		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
				m.getReturnType().equals(Void.TYPE));

	}


	private void testSetterAbsentInSubclasses(String varName,
			String[] subclasses) throws SecurityException,
			ClassNotFoundException {
		String methodName = "set" + varName.substring(0, 1).toUpperCase()
				+ varName.substring(1);
		boolean methodIsInSubclasses = false;
		for (String subclass : subclasses) {
			Method[] methods = Class.forName(subclass).getDeclaredMethods();
			methodIsInSubclasses = methodIsInSubclasses
					|| containsMethodName(methods, methodName);

		}
		assertFalse("The " + methodName
				+ " method should not be implemented in a subclasses.",
				methodIsInSubclasses);
	}
	private void testClassIsSubclass(Class subClass, Class superClass) {
		assertEquals(subClass.getSimpleName() + " class should be a subclass from " + superClass.getSimpleName() + ".",
				superClass, subClass.getSuperclass());
	}



	private void testClassIsSubClass(Class superClass, Class subClass) {
		assertEquals(subClass.getSimpleName() + " should be a subClass of Class : "+ superClass.getSimpleName(), 
				superClass, subClass.getSuperclass());
	}

	private static void testInstanceVariableOfTypeArrayList(Class<?> aClass, String varName, Class<?> expectedType) {
		try {
			Field field = aClass.getDeclaredField(varName);

			// Check if the field is of type ArrayList
			if (!ArrayList.class.isAssignableFrom(field.getType())) {
				fail("The attribute '" + varName + "' should be of type ArrayList<" + expectedType.getSimpleName() + ">");
			}

			// Check the generic type
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {
				ParameterizedType paramType = (ParameterizedType) genericType;
				Type[] actualTypeArguments = paramType.getActualTypeArguments();

				if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof Class<?>) {
					Class<?> actualType = (Class<?>) actualTypeArguments[0];

					assertEquals("The attribute '" + varName + "' should be of type ArrayList<" + expectedType.getSimpleName() + ">",
							expectedType, actualType);
					return; // Success
				}
			}

			fail("The attribute '" + varName + "' should be of type ArrayList<" + expectedType.getSimpleName() + ">");
		} catch (NoSuchFieldException e) {
			fail("Expected field '" + varName + "' was not found in class " + aClass.getSimpleName());
		}
	}


	private void testInstanceVariableIsProtected(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isProtected = Modifier.isProtected(f.getModifiers());
			assertTrue(varName + " should be protected", isProtected);
		}
		else
			assertTrue("you should have" + varName + " as a protected variable", false);
	}


	private void testInterfaceMethod(Class iClass, String methodName, Class returnType, Class[] parameters) {
		Method[] methods = iClass.getDeclaredMethods();
		assertTrue(iClass.getSimpleName()+" interface should have " + methodName + "method", 
				containsMethodName(methods, methodName));

		Method m = null;
		boolean thrown = false;
		try {
			m = iClass.getDeclaredMethod(methodName,parameters);
		}
		catch(NoSuchMethodException e) {
			thrown = true;
		}

		assertTrue("Method " + methodName + " should have the following set of parameters : " + Arrays.toString(parameters),
				!thrown);
		assertTrue("wrong return type ",m.getReturnType().equals(returnType));

	}

	private void testIsInterface(Class iClass) {
		assertTrue(iClass.getSimpleName() + " should be interface",iClass.isInterface());
	}

	private void testClassImplementsInterface(Class aClass, Class iClass) {
		assertTrue(aClass.getSimpleName() +" should implement " + iClass.getSimpleName(), 
				iClass.isAssignableFrom(aClass));
	}



	private void testClassIsAbstract(Class aClass) {
		assertTrue(aClass.getSimpleName() + " should be an abtract class.", 
				Modifier.isAbstract(aClass.getModifiers()));
	}

	private void testSetterMethodLogic(Object createdObject, String varName, Object setValue, Class setType) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		Field f = null;
		Class curr = createdObject.getClass();

		while(f == null) {
			if(curr == Object.class)
				fail("There should be " + varName + " as an instance variable in class " + curr.getSimpleName()
						+" or one of its superclasses");
			try {
				f = curr.getDeclaredField(varName);
			}
			catch(NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}

		f.setAccessible(true);
		String MethodName = "set" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
		Method m = null;
		try {
			m = curr.getDeclaredMethod(MethodName, setType);
		}
		catch(NoSuchMethodException e) {
			assertTrue("No such method",false);
		}
		m.invoke(createdObject, setValue);
		if(f.getType().equals(int.class) && (int)setValue < 0 && varName.equals("currentHealth")) 
			assertEquals("current health should not be less than 0", 0, f.get(createdObject));
		else

			assertEquals("The method \"" + MethodName + "\" in class " + createdObject.getClass().getSimpleName()
					+ " should set the correct value of variable \"", setValue, f.get(createdObject));
	}

	private void testGetterMethodLogic(Object createdObject, String varName, Object expectedValue) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException, InvocationTargetException {
		Field f = null;
		Class curr = createdObject.getClass();

		while(f == null) {
			if(curr == Object.class)
				fail("There should be " + varName + " as an instance variable in class " + curr.getSimpleName()
						+" or one of its superclasses");
			try {
				f = curr.getDeclaredField(varName);
			}
			catch(NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}

		f.setAccessible(true);
		f.set(createdObject, expectedValue);

		String methodName = "";
		if(expectedValue.getClass().equals(boolean.class))
			methodName = "is" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
		else
			methodName = "get" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);

		Method m = createdObject.getClass().getDeclaredMethod(methodName);
		m.invoke(createdObject);
		assertTrue("The method \"" + methodName
				+ "\" in class Character should return the correct value of variable \"" + varName + "\"."
				, m.invoke(createdObject).equals(expectedValue));
	}

	private void testSetterMethodIsAbsentInClass(Class aClass, String methodName) {
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		assertTrue(varName + "should not have a setter", !containsMethodName(methods, methodName));
	}
	private void testGetterMethodIsAbsentInClass(Class aClass, String methodName) {
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		assertTrue(varName + "should not have a getter", !containsMethodName(methods, methodName));
	}

	private void testSetterMethodExistInClass(Class aClass, String methodName, Class setType) {
		//first check if the method exists
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		assertTrue(varName + "should have a setter", containsMethodName(methods, methodName));

		//second check if takes a parameter or not
		Method m = null;
		boolean thrown = false;
		try {
			m = aClass.getDeclaredMethod(methodName,setType);
		}
		catch(NoSuchMethodException e) {
			thrown = true;
		}
		assertTrue(methodName + " method should take a parameter of type : " + setType, !thrown);

		//finally check if it is void
		assertTrue(methodName +" method should be void",m.getReturnType().equals(void.class));

	}


	private void testGetterMethodExistInClass(Class aClass, String methodName, Class returnType) {
		Method m = null;
		boolean thrown = false;
		try {
			m = aClass.getDeclaredMethod(methodName);
		}catch(NoSuchMethodException e) {
			thrown = true;
		}
		String varName = "";
		if(m.getReturnType().equals(boolean.class))
			varName = methodName.substring(2,3).toLowerCase() + methodName.substring(3);
		else
			varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		if(!thrown) {
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.",
					m.getReturnType().equals(returnType));
		}
		else
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a READ variable.", false);
	}



	private void testInstanceVariableIsStatic(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isStatic = Modifier.isStatic(f.getModifiers());
			assertTrue(varName + " should be static", isStatic);
		}
		else
			assertTrue("There should be " + varName + " as a static variable", false);
	}
	private void testInstanceVariableIsNotStatic(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isStatic = Modifier.isStatic(f.getModifiers());
			assertFalse(varName + " should not be static", isStatic);
		}
		else
			assertFalse("There should not be " + varName + " as a static variable", false);
	}



	private void testInstanceVariableIsPresent(Class aClass, String varName) throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse("There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",thrown);
	}

	private void testInstanceVariableIsNotPresent(Class aClass, String varName) throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		}catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertTrue("There should not be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".", thrown);
	}

	private void testInstanceVariableOfTypeDoubleArray(Class aClass, String varName, Class expectedType) {
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			return;
		}
		Class varType = f.getType();
		assertEquals(
				"the attribute: " + varType.getSimpleName() + " should be of the type: " + expectedType.getSimpleName(),
				expectedType.getTypeName() + "[][]", varType.getTypeName());
	}

	private void testInstanceVariableIsPresent(Class aClass, String varName, boolean implementedVar)
			throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		if (implementedVar) {
			assertFalse(
					"There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",
					thrown);
		} else {
			assertTrue("The instance variable \"" + varName + "\" should not be declared in class "
					+ aClass.getSimpleName() + ".", thrown);
		}
	}

	private void testInstanceVariableOfType(Class aClass, String varName, Class expectedType) {
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			return;
		}
		Class varType = f.getType();
		assertEquals(
				"The attribute " + varType.getSimpleName() + " should be of the type " + expectedType.getSimpleName(),
				expectedType, varType);
	}

	private void testInstanceVariableIsPrivate(Class aClass, String varName) throws NoSuchFieldException, SecurityException {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isPrivate = (Modifier.isPrivate(f.getModifiers()));
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " should not be accessed outside that class.", isPrivate);

		}
		else {
			assertFalse("There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",thrown);
		}

	}


	private void testInstanceVariableIsFinal(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isFinal = Modifier.isFinal(f.getModifiers());
			assertTrue(varName + " should be final", isFinal);
		}
		else
			assertTrue("There should have" + varName + " as a final variable", false);
	}
	private void testInstanceVariableIsNotFinal(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isFinal = Modifier.isFinal(f.getModifiers());
			assertFalse(varName + " should not be final", isFinal);
		}
		else
			assertFalse("There should have" + varName + " as a not final variable", false);
	}


	private void testGetterMethodExistsInClass(Class aClass, String methodName, Class returnedType,
			boolean readvariable) {
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		String varName = "";
		if (returnedType == boolean.class)
			varName = methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
		else
			varName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
		if (readvariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is a READ variable.", found);
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.",
					m.getReturnType().isAssignableFrom(returnedType));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a READ variable.", found);
		}

	}

	private void testGetterLogic(Object createdObject, String name, Object value) throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);
		f.set(createdObject, value);

		Character c = name.charAt(0);

		String methodName = "get" + Character.toUpperCase(c) + name.substring(1, name.length());

		if (value.getClass().equals(Boolean.class))
			methodName = "is" + Character.toUpperCase(c) + name.substring(1, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
				+ " should return the correct value of variable \"" + name + "\".",
				value, m.invoke(createdObject));

	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}


	private void testSetterMethodExistsInClass(Class aClass, String methodName, Class inputType,
			boolean writeVariable) {

		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
		if (writeVariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is a WRITE variable.", containsMethodName(methods, methodName));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a WRITE variable.", containsMethodName(methods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName, inputType);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one "
				+ inputType.getSimpleName() + " parameter.", found);

		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
				m.getReturnType().equals(Void.TYPE));

	}

	private void testSetterLogic(Object createdObject, String name, Object setValue, Object expectedValue, Class type)
			throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);

		Character c = name.charAt(0);
		String methodName = "set" + Character.toUpperCase(c) + name.substring(1, name.length());
		Method m = createdObject.getClass().getMethod(methodName, type);
		m.invoke(createdObject, setValue);
		if (name == "currentActionPoints" || name == "currentHP") {
			if ((int) setValue > (int) expectedValue) {
				assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name
						+ "\". It should not exceed the maximum value.", expectedValue, f.get(createdObject));
			} else if ((int) setValue == -1 && (int) expectedValue == 0) {
				assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name
						+ "\". It should not be less than zero.", expectedValue, f.get(createdObject));
			} else {
				assertEquals(
						"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name + "\".",
						expectedValue, f.get(createdObject));
			}
		} else {
			assertEquals(
					"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
					+ " should set the correct value of variable \"" + name + "\".",
					expectedValue, f.get(createdObject));
		}
	}


	private void testConstructorExists(Class aClass, Class[] inputs) {
		boolean thrown = false;
		try {
			aClass.getConstructor(inputs);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		if (inputs.length > 0) {
			String msg = "";
			int i = 0;
			do {
				msg += inputs[i].getSimpleName() + " and ";
				i++;
			} while (i < inputs.length);

			msg = msg.substring(0, msg.length() - 4);

			assertFalse(
					"Missing constructor with " + msg + " parameter" + (inputs.length > 1 ? "s" : "") + " in "
							+ aClass.getSimpleName() + " class.",

							thrown);
		} else
			assertFalse("Missing constructor with zero parameters in " + aClass.getSimpleName() + " class.",

					thrown);

	}

	private void testConstructorInitialization(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);

			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly.",
					currValue, f.get(createdObject));

		}

	}


	private void testGetterLogic(Object createdObject, String name, Object value,String currentMethodName)
			throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName()
						+ " should have the instance variable \"" + name
						+ "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);
		f.set(createdObject, value);

		Character c = name.charAt(0);

		String methodName = currentMethodName;

		if (value.getClass().equals(Boolean.class)
				&& !name.substring(0, 2).equals("is"))
			methodName = "is" + Character.toUpperCase(c)
			+ name.substring(1, name.length());
		else if (value.getClass().equals(Boolean.class)
				&& name.substring(0, 2).equals("is"))
			methodName = "is" + Character.toUpperCase(name.charAt(2))
			+ name.substring(3, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals("The method \"" + methodName + "\" in class "
				+ createdObject.getClass().getSimpleName()
				+ " should return the correct value of variable \"" + name
				+ "\".", value, m.invoke(createdObject));

	}

	private void testSetterAbsent(String varName,
			String[] subclasses) throws SecurityException,
			ClassNotFoundException {
		String methodName = "set" + varName.substring(0, 1).toUpperCase()
				+ varName.substring(1);
		boolean methodIsInSubclasses = false;
		for (String subclass : subclasses) {
			Method[] methods = Class.forName(subclass).getDeclaredMethods();
			methodIsInSubclasses = methodIsInSubclasses
					|| containsMethodName(methods, methodName);

		}
		assertFalse("The " + methodName
				+ " method should not be implemented.",
				methodIsInSubclasses);
	}


	private void testGetterAbsentInSubclasses(String varName,
			String[] subclasses) throws SecurityException,
			ClassNotFoundException {
		String methodName = "get" + varName.substring(0, 1).toUpperCase()
				+ varName.substring(1);
		boolean methodIsInSubclasses = false;
		for (String subclass : subclasses) {
			Method[] methods = Class.forName(subclass).getDeclaredMethods();
			methodIsInSubclasses = methodIsInSubclasses
					|| containsMethodName(methods, methodName);

		}
		assertFalse("The " + methodName
				+ " method should not be implemented in a subclasses.",
				methodIsInSubclasses);
	}


	private void testAttributeExistence(String givenAttributeName,String classPath) throws ClassNotFoundException {
		Class givenClass = Class.forName(classPath);
		String attributeName = givenAttributeName;
		try {
			Field field = givenClass.getDeclaredField(givenAttributeName);

			assertTrue("Attribute " + attributeName + " should exist in class " + givenClass.getSimpleName(),
					field != null);
		} catch (Exception e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}



}
