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
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;


import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class Milestone1PublicTests {

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





	////////////////////////////////////////// 2. Enums /////////////////////////////////////////


	@Test(timeout = 1000)
	public void testColourEnumValues() {
		testEnumValues(colourPath, "colour", new String[] {"GREEN", "RED", "YELLOW", "BLUE"});
	}

	@Test(timeout = 1000)
	public void testCellTypeIsEnum() throws ClassNotFoundException {
		testIsEnum(Class.forName(cellTypePath));
	}

	@Test(timeout = 1000)
	public void testCellTypeEnumValues() {
		testEnumValues(cellTypePath, "cellType", new String[] { "NORMAL", "SAFE", "ENTRY", "BASE"});
	}


	@Test(timeout = 1000)
	public void testSuitIsEnum() throws ClassNotFoundException {
		testIsEnum(Class.forName(suitPath));
	}



	//////////////////////////////// 3. Interfaces ////////////////////////////////////////

	@Test(timeout = 1000)
	public void testGameManagerIsAnInterface() throws ClassNotFoundException {
		testIsInterface(Class.forName(gameManagerPath));
	}


	@Test(timeout = 1000)
	public void testgetSplitDistanceInBoardManagerInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(boardManagerPath), "getSplitDistance", int.class, null);
	}


	//////////////////////////////// 4.1 Marble Class //////////////////////////////////////




	@Test(timeout = 1000)
	public void testMarbleInstanceVariableColourIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(marblePath), "colour");
	}

	@Test(timeout = 1000)
	public void testMarbleInstanceVariableColourIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(marblePath), "colour");
	}

	@Test(timeout = 1000)
	public void testColourInMarbleType() throws Exception {
		testInstanceVariableOfType(Class.forName(marblePath), "colour", Class.forName(colourPath));

	}


	@Test(timeout = 1000)
	public void testMarbleInstanceVariableColourGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(marblePath), "getColour", Class.forName(colourPath),true);
	}


	//Method changed to be randomized
	@Test(timeout = 1000)
	public void testMarbleInstanceVariableColourGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		int randomNum = (int) (Math.random() * 4) + 1;
		Constructor<?> marbleConstructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object marble = marbleConstructor.newInstance(colour1);
		if(randomNum==1){
			testGetterMethodLogic(marble, "colour", colour1);
		}
		if(randomNum==2){
			marble = marbleConstructor.newInstance(colour2);
			testGetterMethodLogic(marble, "colour", colour2);
		}
		if(randomNum==3){
			marble = marbleConstructor.newInstance(colour3);
			testGetterMethodLogic(marble, "colour", colour3);
		}
		if(randomNum==4){
			marble = marbleConstructor.newInstance(colour4);
			testGetterMethodLogic(marble, "colour", colour4);
		}
	}



	@Test(timeout = 1000)
	public void testMarbleInstanceVariableColourNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(marblePath), "setColour",Class.forName(colourPath) , false);
	}



	//Method changed to be randomized
	@Test(timeout = 1000)
	public void testMarbleConstructorInitialization() throws Exception{

		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		int randomNum = (int) (Math.random() * 4) + 1;
		Constructor<?> marbleConstructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object marble = marbleConstructor.newInstance(colour1);
		String[] names = {"colour"};

		if(randomNum==1){
			Object[] values = {colour1};
			testConstructorInitialization(marble, names, values);
		}
		if(randomNum==2){
			marble = marbleConstructor.newInstance(colour2);
			Object[] values2 = {colour2};
			testConstructorInitialization(marble, names, values2);
		}
		if(randomNum==3){
			marble = marbleConstructor.newInstance(colour3);
			Object[] values3 = {colour3};
			testConstructorInitialization(marble, names, values3);
		}
		if(randomNum==4){
			marble = marbleConstructor.newInstance(colour4);
			Object[] values4 = {colour4};
			testConstructorInitialization(marble, names, values4);
		}

	}


	//////////////////////////////// 4.2 Cell Class //////////////////////////////////////



	@Test(timeout = 1000)
	public void testCellInstanceVariableTrapPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(cellPath), "trap");
	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableMarbleIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(cellPath), "marble");
	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableCellTypeIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(cellPath), "cellType");
	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableTrapIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(cellPath), "trap");
	}

	@Test(timeout = 1000)
	public void testTypeOfMarbleInCell() throws Exception {
		testInstanceVariableOfType(Class.forName(cellPath), "marble", Class.forName(marblePath));

	}

	@Test(timeout = 1000)
	public void testTypeOfCellTypeInCell() throws Exception {
		testInstanceVariableOfType(Class.forName(cellPath), "cellType", Class.forName(cellTypePath));

	}


	@Test(timeout = 1000)
	public void testCellInstanceVariableMarbleGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(cellPath), "getMarble", Class.forName(marblePath),true);
	}



	@Test(timeout = 1000)
	public void testCellInstanceVariableMarbleGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Constructor<?> marbleConstructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object marble = marbleConstructor.newInstance(colour);
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		boolean trap = true;
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		Class cellClass = Class.forName(cellPath);

		Field f1 = cellClass.getDeclaredField("marble");
		f1.setAccessible(true);
		f1.set(cell, marble);


		testGetterMethodLogic(cell, "marble", marble);
	}

	@Test(timeout = 1000)
	public void testCellInstanceVariableCellTypeGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Constructor<?> marbleConstructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object marble = marbleConstructor.newInstance(colour);
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		boolean trap = true;
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);

		testGetterMethodLogic(cell, "cellType", cellType);
	}




	@Test(timeout = 1000)
	public void testTrapInCellGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(cellPath), "isTrap", boolean.class,true);
	}



	@Test(timeout = 1000)
	public void testMarbleInCellSetterExists() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(cellPath), "setMarble", Class.forName(marblePath),true);
	}

	@Test(timeout = 1000)
	public void testCellTypeInCellSetterExists() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(cellPath), "setCellType", Class.forName(cellTypePath),true);
	}



	@Test(timeout = 1000)
	public void testMarbleInCellSetterLogic() throws Exception{
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		Constructor<?> marbleConstructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object marble = marbleConstructor.newInstance(colour);
		testSetterMethodLogic(cell, "marble", marble, Class.forName(marblePath));
	}



	@Test(timeout = 1000)
	public void testTrapInCellSetterLogic() throws Exception{
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		testSetterMethodLogic(cell, "trap", true, boolean.class);
	}


	@Test(timeout = 1000)
	public void testTrapInCellSetterLogic2() throws Exception{
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		testSetterMethodLogic(cell, "trap", false, boolean.class);
	}


	@Test(timeout = 1000)
	public void testCellConstructorExists() throws ClassNotFoundException {
		Class[] parameters = {Class.forName(cellTypePath)};
		testConstructorExists(Class.forName(cellPath), parameters);
	}

	@Test(timeout = 1000)
	public void testCellConstructorInitialization() throws Exception{
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Constructor<?> marbleConstructor = Class.forName(marblePath).getConstructor(Class.forName(colourPath));
		Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY");
		String[] names = {"marble", "cellType","trap"};
		Object[] values = {null,cellType,false};
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Object cell = cellConstructor.newInstance(cellType);
		testConstructorInitialization(cell, names, values);
	}


	//////////////////////////////// 4.3 Safe Zone Class //////////////////////////////////////

	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableColourPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(safeZonePath), "colour");
	}



	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableColourIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(safeZonePath), "colour");
	}

	@Test(timeout = 1000)
	public void testColourInSafeZoneType() throws Exception {
		testInstanceVariableOfType(Class.forName(safeZonePath), "colour", Class.forName(colourPath));

	}

	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableCellsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(safeZonePath), "cells");
	}

	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableCellsIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(safeZonePath), "cells");
	}

	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableCellsIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(safeZonePath), "cells");
	}



	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableColourGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(safeZonePath), "getColour", Class.forName(colourPath),true);
	}


	//Method changed to be randomized
	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableColourGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		int randomNum = (int) (Math.random() * 4) + 1;
		Constructor<?> safeZoneConstructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
		Object safeZone = safeZoneConstructor.newInstance(colour1);
		if(randomNum==1){
			testGetterMethodLogic(safeZone, "colour", colour1);
		}
		if(randomNum==2){
			safeZone = safeZoneConstructor.newInstance(colour2);
			testGetterMethodLogic(safeZone, "colour", colour2);
		}
		if(randomNum==3){
			safeZone = safeZoneConstructor.newInstance(colour3);
			testGetterMethodLogic(safeZone, "colour", colour3);
		}
		if(randomNum==4){
			safeZone = safeZoneConstructor.newInstance(colour4);
			testGetterMethodLogic(safeZone, "colour", colour4);
		}



	}

	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableCellsGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(safeZonePath), "getColour", Class.forName(colourPath),true);
	}




	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableColourNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(safeZonePath), "setColour",Class.forName(colourPath) , false);
	}

	@Test(timeout = 1000)
	public void testSafeZoneInstanceVariableCellsNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(safeZonePath), "setCells",ArrayList.class, false);
	}


	//Method changed to be randomized
	@Test(timeout = 10000)
	public void testSafeZoneConstructorInitializationColour() throws Exception{

		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		int randomNum = (int) (Math.random() * 4) + 1;
		Constructor<?> safeZoneConstructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
		Object safeZone = safeZoneConstructor.newInstance(colour1);
		String[] names = {"colour"};

		if(randomNum==1){
			Object[] values = {colour1};
			testConstructorInitialization(safeZone, names, values);
		}
		if(randomNum==2){
			safeZone = safeZoneConstructor.newInstance(colour2);
			Object[] values2 = {colour2};
			testConstructorInitialization(safeZone, names, values2);
		}
		if(randomNum==3){
			safeZone = safeZoneConstructor.newInstance(colour3);
			Object[] values3 = {colour3};
			testConstructorInitialization(safeZone, names, values3);
		}
		if(randomNum==4){
			safeZone = safeZoneConstructor.newInstance(colour4);
			Object[] values4 = {colour4};
			testConstructorInitialization(safeZone, names, values4);
		}

	}

	@Test(timeout = 10000)
	public void testSafeZoneConstructorInitializationCells() throws Exception{
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Constructor<?> safeZoneConstructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
		Object safeZone = safeZoneConstructor.newInstance(colour);
		Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
		Class cellClass = Class.forName(cellPath);
		ArrayList<Object> cells = new ArrayList();
		for(int i=0;i<4;i++)
		{
			//create 4 safe cells
			Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");
			Object cell = cellConstructor.newInstance(cellType);
			cells.add(cell);

		}

		String[] names = {"cells"};
		Object[] values = {cells};
		testConstructorInitializationSafeZone(safeZone, names, values);
	}




	//////////////////////////////////////////// 4.21 Game Class //////////////////////////////////////////////////////////////

	@Test(timeout = 10000)
	public void testGameClassImplementsGameManagerInterface() {
		try {
			testClassImplementsInterface(Class.forName(gamePath), Class.forName(gameManagerPath));
		}
		catch(ClassNotFoundException e) {
			assertTrue(e.getClass().getName() + " occurred: " + e.getMessage(), false);
		}
	}





	@Test(timeout = 1000)
	public void testGameInstanceVariableBoardIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(gamePath), "board");
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableBoardIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(gamePath), "board");
	}

	@Test(timeout = 1000)
	public void testBoardInGameType() throws Exception {
		testInstanceVariableOfType(Class.forName(gamePath), "board", Class.forName(boardPath));

	}

	@Test(timeout = 1000)
	public void testGameInstanceVariablePlayersPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(gamePath), "players");
	}



	@Test(timeout = 1000)
	public void testGameInstanceVariablePlayersIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(gamePath), "players");
	}



	@Test(timeout = 1000)
	public void testGameInstanceVariableFirePitPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(gamePath), "firePit");
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableFirePitIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(gamePath), "firePit");
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableFirePitIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(gamePath), "firePit");
	}

	@Test(timeout = 1000)
	public void testFirePitInGameType() throws Exception {
		testInstanceVariableOfType(Class.forName(gamePath), "firePit", ArrayList.class);

	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableCurrentPlayerIndexPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(gamePath), "currentPlayerIndex");
	}



	@Test(timeout = 1000)
	public void testCurrentPlayerIndexInGameType() throws Exception {
		testInstanceVariableOfType(Class.forName(gamePath), "currentPlayerIndex", int.class);

	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableTurnPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(gamePath), "turn");
	}

	@Test(timeout = 1000)
	public void testGameInstanceVariableTurnIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(gamePath), "turn");
	}




	@Test(timeout = 1000)
	public void testGameInstanceVariableBoardGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(gamePath), "getBoard", Class.forName(boardPath),true);
	}






	@Test(timeout = 1000)
	public void testGameInstanceVariableBoardNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(gamePath), "setBoard",Class.forName(boardPath) , false);
	}


	@Test(timeout = 1000)
	public void testGameInstanceVariablePlayersGetterExists() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(gamePath), "getPlayers", ArrayList.class,true);
	}



	@Test(timeout = 1000)
	public void testGameInstanceVariablePlayersGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		ArrayList<Object> players = new ArrayList<Object>();

		Constructor<?> playerConstructor = Class.forName(playerPath).getConstructor(String.class,Class.forName(colourPath));
		Object player = playerConstructor.newInstance("playerName",colour);

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");

		players.add(player);

		Class gameClass = Class.forName(gamePath);
		Field f1 = gameClass.getDeclaredField("players");
		f1.setAccessible(true);



		f1.set(game, players);

		testGetterMethodLogic(game, "players", players);
	}



	@Test(timeout = 1000)
	public void testGameInstanceVariablePlayersNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(gamePath), "setPlayers",ArrayList.class , false);
	}





	@Test(timeout = 1000)
	public void testGameInstanceVariableFirePitGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		ArrayList<Object> firePits = new ArrayList<Object>();

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");

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
		Object board = boardConstructor.newInstance(colours,game);

		Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");

		Constructor<?> cardStandardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",1,suit,board,game);

		firePits.add(card);


		Class gameClass = Class.forName(gamePath);
		Field f1 = gameClass.getDeclaredField("firePit");
		f1.setAccessible(true);



		f1.set(game, firePits);

		testGetterMethodLogic(game, "firePit", firePits);
	}



	@Test(timeout = 1000)
	public void testGameInstanceVariableFirePitNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(gamePath), "setFirePit",ArrayList.class , false);
	}


	@Test(timeout = 1000)
	public void testGameInstanceVariableCurrentPlayerIndexNoSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(gamePath), "setCurrentPlayerIndex",int.class , false);
	}


	@Test(timeout = 1000)
	public void testGameInstanceVariableCurrentPlayerIndexNOGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(gamePath), "getCurrentPlayerIndex", int.class,false);
	}



	@Test(timeout = 1000)
	public void testGameInstanceVariableTurnNOGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(gamePath), "getTurn", int.class,false);
	}



	@Test(timeout = 10000)
	public void testGameConstructorInitializationTurn() throws Exception{

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");
		String[] names = {"turn"};
		Object[] values = {0};
		testConstructorInitialization(game, names, values);
	}



	@Test(timeout = 10000)
	public void testGameConstructorInitializationFirePit() throws Exception{

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");
		ArrayList<Object> firePit = new ArrayList<Object>();
		String[] names = {"firePit"};
		Object[] values = {firePit};
		testGameConstructorInitialization(game, names, values);
	}

	@Test(timeout = 10000)
	public void testGameConstructorInitializationPlayersNames() throws Exception{

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");


		Constructor<?> CPUConstructor = Class.forName(CPUPath).getConstructor(String.class,Class.forName(colourPath),Class.forName(boardManagerPath));

		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		ArrayList<Object> colours = new ArrayList<Object>();
		colours.add(colour);
		colours.add(colour1);
		colours.add(colour2);
		colours.add(colour3);

		Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		Object board = boardConstructor.newInstance(colours,game);


		Constructor<?> playerConstructor = Class.forName(playerPath).getConstructor(String.class,Class.forName(colourPath));
		Object player = playerConstructor.newInstance("PlayerName",colour);


		Object CPU1 = CPUConstructor.newInstance("CPU 1",colour1,board);
		Object CPU2 = CPUConstructor.newInstance("CPU 2",colour2,board);
		Object CPU3 = CPUConstructor.newInstance("CPU 3",colour3,board);

		ArrayList<Object> players = new ArrayList<Object>();
		players.add(player);
		players.add(CPU1);
		players.add(CPU2);
		players.add(CPU3);

		Class curr = game.getClass();

		Field f = null;

		try {
			f = curr.getDeclaredField("players");
			f.setAccessible(true);
		} catch (NoSuchFieldException e) {
			curr = curr.getSuperclass();
		}

		ArrayList<Object> myPlayers = (ArrayList<Object>) f.get(game);

		assertTrue("Class Game Constructor should add first a human player in the player arrayList", myPlayers.get(0).getClass().equals(Class.forName(playerPath)));
		assertTrue("Class Game Constructor should add  CPU players named CPU 1 CPU 2 CPU 3 in the player arrayList", myPlayers.get(1).getClass().equals(Class.forName(CPUPath)));
		assertTrue("Class Game Constructor should add  CPU players named CPU 1 CPU 2 CPU 3 in the player arrayList", myPlayers.get(2).getClass().equals(Class.forName(CPUPath)));
		assertTrue("Class Game Constructor should add  CPU players named CPU 1 CPU 2 CPU 3 in the player arrayList", myPlayers.get(3).getClass().equals(Class.forName(CPUPath)));

		if (myPlayers.get(0).getClass().equals(Class.forName(playerPath))) {
			Field name = Class.forName(playerPath).getDeclaredField("name");
			name.setAccessible(true);
			player =  myPlayers.get(0);
			String n = (String) name.get(player);
			assertTrue("The Player Name should be initialized correctly.",n.equals("PlayerName"));
		}

		if(myPlayers.get(1).getClass().equals(Class.forName(CPUPath))){

			Field name = Class.forName(playerPath).getDeclaredField("name");
			name.setAccessible(true);
			player =  myPlayers.get(1);
			String n = (String) name.get(player);
			assertTrue("The Player Name should be initialized correctly.",n.equals("CPU 1"));

		}

		if(myPlayers.get(2).getClass().equals(Class.forName(CPUPath))){

			Field name = Class.forName(playerPath).getDeclaredField("name");
			name.setAccessible(true);
			player =  myPlayers.get(2);
			String n = (String) name.get(player);
			assertTrue("The Player Name should be initialized correctly.",n.equals("CPU 2"));

		}

		if(myPlayers.get(3).getClass().equals(Class.forName(CPUPath))){

			Field name = Class.forName(playerPath).getDeclaredField("name");
			name.setAccessible(true);
			player =  myPlayers.get(3);
			String n = (String) name.get(player);
			assertTrue("The Player Name should be initialized correctly.",n.equals("CPU 3"));

		}


	}


	@Test(timeout = 1000)
	public void testGameInstanceVariableCurrentPlayerIndexIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsNotFinal(Class.forName(gamePath), "currentPlayerIndex");
	}







	///////////////////////////	Testing 4.14 Wild Card Class
	//	Present
	@Test(timeout = 1000)
	public void testWildCardExists() {
		try {
			Class.forName(wildPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Wild");
		}
	}
	//	Abstract
	@Test(timeout = 1000)
	public void testWildCardIsAnAbstractClass() throws Exception {
		testClassIsAbstract(Class.forName(wildPath));
	}



	//	Constructor present
	@Test(timeout = 1000)
	public void testConstructorWild() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(boardManagerPath),Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(wildPath), inputs);
	}



	///////////////////////////	Testing 4.15 Burner Card Class

	//	Subclass of Card
	@Test(timeout = 1000)
	public void testBurnerIsSubClassOfWild() throws ClassNotFoundException {
		testClassIsSubClass(Class.forName(wildPath), Class.forName(burnerPath));
	}



	//	Constructor present
	@Test(timeout = 1000)
	public void testConstructorBurner() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(boardManagerPath),Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(burnerPath), inputs);
	}


	@Test(timeout = 10000)
	public void testBurnerCardConstructorInitialization() {

		try {
			
			Object game= createGame();
			Object board= createBoard(game);
			
			int randomInt=(int) (Math.random()*5);
			String name="Burner Card"+randomInt;
			String description="Here's a description with random number"+randomInt;
			//Card constructor 
			Constructor<?> BurnerCardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
			Object card = BurnerCardConstructor.newInstance(name,description,board,game);
			
			//the expected field names and corresponding expected values.
			String[] fieldNames = {"name", "description", "boardManager", "gameManager"};
			Object[] expectedValues = {name,description,board,game};
			
			// Use a helper method to verify that the card's fields are properly initialized.
			testConstructorInitialization(card, fieldNames, expectedValues);
		} catch (Exception e) {
			// TODO: handle exception
			fail("Exception "+e.getMessage()+ " occured while creating a burner card");
		}
	}


	///////////////////////////	Testing 4.16 Saver Card Class
	//	Present
	@Test(timeout = 1000)
	public void testSaverCardExists() {
		try {
			Class.forName(saverPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Saver");
		}
	}

	//	Subclass of Card
	@Test(timeout = 1000)
	public void testSaverIsSubClassOfWild() throws ClassNotFoundException {
		testClassIsSubClass(Class.forName(wildPath), Class.forName(saverPath));
	}



	//	Constructor present
	@Test(timeout = 1000)
	public void testConstructorSaver() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(boardManagerPath),Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(saverPath), inputs);
	}




	///////////////////////////	Testing 4.17 Deck Class

	@Test(timeout = 1000)
	public void testCardsFileInDeckExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("CARDS_FILE",deckPath);
	}



	//	String CARDS Static
	@Test(timeout=1000)
	public void testCardsFileInDeckIsStatic() throws ClassNotFoundException {
		testInstanceVariableIsStatic(Class.forName(deckPath), "CARDS_FILE");
	}

	//	String CARDS FILE Final
	@Test(timeout=1000)
	public void testCardsFileInDeckIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(deckPath), "CARDS_FILE");
	}


	//	String CARDS FILE initialised correctly

	@Test(timeout = 1000)
	public void testCardsFileInDeckValue() {

		try {

			Field cardsFileField = Class.forName(deckPath).getDeclaredField("CARDS_FILE");
			cardsFileField.setAccessible(true);
			assertEquals("Cards.csv",cardsFileField.get(null));
		} catch ( NoSuchFieldException| SecurityException|
				ClassNotFoundException| IllegalArgumentException| IllegalAccessException  e) {
			e.printStackTrace();
			fail("An error "+e.getMessage()+" occured while testing the CARDS_FILE attribute");
		}
	}

	@Test(timeout = 1000)
	public void testCardFileInDeckOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(deckPath), "CARDS_FILE", String.class);
	}
	//	No Getter CARDS FILE 
	@Test(timeout = 1000)
	public void testCardFileInDeckGetterIsAbsent() throws ClassNotFoundException {
		testGetterMethodIsAbsentInClass(Class.forName(deckPath), "getCARDS_FILE");
	}







	//	ArrayList<Card> cardsPool private
	@Test(timeout=1000)
	public void testCardsPoolInDeckIsPrivate() throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		testInstanceVariableIsPrivate(Class.forName(deckPath), "cardsPool");
	}
	//	ArrayList<Card> cardsPool static
	@Test(timeout=1000)
	public void testCardsPoolInDeckIsStatic() throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		testInstanceVariableIsStatic(Class.forName(deckPath), "cardsPool");
	}


	//	cardsPool Type check
	@Test(timeout = 1000)
	public void testCardsPoolInDeckOfType() throws ClassNotFoundException {
		testInstanceVariableOfTypeArrayList(Class.forName(deckPath), "cardsPool", Class.forName(cardPath));
	}


	//	No Getter CARDS FILE 
	@Test(timeout = 1000)
	public void testCardsPoolInDeckGetterIsAbsent() throws ClassNotFoundException {
		testGetterMethodIsAbsentInClass(Class.forName(deckPath), "getCardsPool");
	}
	// 	No Setter CARDS FILE 
	@Test(timeout = 1000)
	public void testCardsPoolInDeckSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(deckPath), "setCardsPool");
	}






	//	loadCardPool method static
	@Test(timeout = 1000)
	public void testLoadCardPoolInDeckisStatic() {

		Method m;
		try {
			m = Class.forName(deckPath).getMethod("loadCardPool",Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			assertTrue("loadCardPool expected to be Static",Modifier.isStatic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

	}


	//	loadCardPool method throws IOException

	@Test(timeout = 1000)
	public void testLoadCardPoolInDeckThrowsIOException() {

		Method m;
		try {
			m = Class.forName(deckPath).getMethod("loadCardPool",Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Class<?>[] exceptionsThrown= m.getExceptionTypes();
			boolean IOExceptionFound=false;
			for (Class<?> excep : exceptionsThrown) {
				if(excep.equals(IOException.class))
					IOExceptionFound=true;
			}

			assertTrue("loadCardPool expected to throw IOException",IOExceptionFound);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			fail("an error occured while testing the loadCardPool, error cause is "+e.getMessage()+" please check the console");
		}

	}





	//	loadCardPool logic reading csv and loading the cardsPool

	@Test(timeout = 1000)
	public void testNameCardsLoadCardPoolInDeckReadingCSVLoadingWildCards() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);
			cardsPool_Object= new ArrayList<>();
			ArrayList<String> cardsList= writeWildCardsCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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
					Field nameField= Class.forName(cardPath).getDeclaredField("name");
					nameField.setAccessible(true);
					arraylistCounter++;
					assertEquals("Wild card name loaded incorrectly ", name, (String)nameField.get(card));
				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}


	@Test(timeout = 1000)
	public void testCodeCardsLoadCardPoolInDeckReadingCSVLoadingWildCards() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);
			cardsPool_Object= new ArrayList<>();
			ArrayList<String> cardsList= writeWildCardsCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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

					arraylistCounter++;

					if(code==14)
						assertEquals("Wild card code loaded incorrectly, expected a Burner card but was Saver", Class.forName(burnerPath),card.getClass());
					else
						assertEquals("Wild card code loaded incorrectly, expected a Saver card but was Burner ", Class.forName(saverPath),card.getClass());
				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}

	@Test(timeout = 1000)
	public void testFrequencyCardsLoadCardPoolInDeckReadingCSVLoadingWildCards() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);
			cardsPool_Object= new ArrayList<>();
			ArrayList<String> cardsList= writeWildCardsCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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


				int frequencyCounter=0;
				for (int j = 0; j < cardsPool_Object.size(); j++) {
					Field nameField= Class.forName(cardPath).getDeclaredField("name");
					Object card =cardsPool_Object.get(arraylistCounter);
					nameField.setAccessible(true);
					card =cardsPool_Object.get(j);
					if(name.equals((String)nameField.get(card)))
					{
						frequencyCounter++;
					}


				}
				assertEquals("Card Frequency is incorrect, card of name:"+name+" should be added with frequency ", freq,frequencyCounter);

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}



	@Test(timeout = 1000)
	public void testLoadCardPoolInDeckReadingCSVLoadingMixCards() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			ArrayList<String> cardsList= writeCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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
					Field nameField= Class.forName(cardPath).getDeclaredField("name");
					attributeField.setAccessible(true);
					nameField.setAccessible(true);
					arraylistCounter++;
					//					System.out.println( (String)attributeField.get(card));
					assertEquals("Card description loaded incorrectly ", description, (String)attributeField.get(card));
					assertEquals("Card name loaded incorrectly ", name, (String)nameField.get(card));

				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException |
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}
	@Test(timeout = 2000)
	public void testLoadCardPoolInDeckReadingCSVLoadingMixCards2() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			ArrayList<String> cardsList= writeCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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

					arraylistCounter++;
					//					System.out.println( (String)attributeField.get(card));
					switch (code) {
					case 0: assertTrue("wrong card created, make sure you create the correct card based on the code", 
							card.getClass().equals(Class.forName(standardPath))	);

					break;

					case 1:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(acePath),card.getClass()	);
					break;
					case 4: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(fourPath),card.getClass()	);
					break;
					case 5: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(fivePath),card.getClass()	);
					break;
					case 7:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(sevenPath),card.getClass()	);
					break;
					case 10: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(tenPath),card.getClass()	);
					break;
					case 11:assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(jackPath),card.getClass()	);
					break;
					case 12: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(queenPath),card.getClass()	);
					break;
					case 13: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(kingPath),card.getClass()	);
					break;
					case 14: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(burnerPath),card.getClass()	);
					break;
					case 15:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(saverPath),card.getClass()	);
					break;



					default:
						break;
					}

				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException |
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}


	@Test(timeout = 2000)
	public void testLoadCardPoolInDeckReadingCSVLoadingStandardCards1() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			ArrayList<String> cardsList= writeStandartCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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
					Field rankField= Class.forName(standardPath).getDeclaredField("rank");
					Field suitField= Class.forName(standardPath).getDeclaredField("suit");

					suitField.setAccessible(true);
					rankField.setAccessible(true);

					arraylistCounter++;
					assertEquals("Wrong Suit loaded for Standard card", suit,suitField.get(card).toString() );
					switch (code) {
					case 0: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(standardPath),card.getClass()	);
					break;

					case 1:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(acePath),card.getClass()	);
					break;
					case 4: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(fourPath),card.getClass()	);
					break;
					case 5: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(fivePath),card.getClass()	);
					break;
					case 7:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(sevenPath),card.getClass()	);
					break;
					case 10: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(tenPath),card.getClass()	);
					break;
					case 11:assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(jackPath),card.getClass()	);
					break;
					case 12: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(queenPath),card.getClass()	);
					break;
					case 13: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(kingPath),card.getClass()	);
					break;

					default:
						break;
					}


				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException |
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}

	@Test(timeout = 2000)
	public void testLoadCardPoolInDeckReadingCSVLoadingStandardCards1Rank() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			ArrayList<String> cardsList= writeStandartCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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
					Field rankField= Class.forName(standardPath).getDeclaredField("rank");
					Field suitField= Class.forName(standardPath).getDeclaredField("suit");

					suitField.setAccessible(true);
					rankField.setAccessible(true);

					arraylistCounter++;
					assertEquals("Wrong rank loaded for Standard card", rank,rankField.get(card) );
					switch (code) {
					case 0: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(standardPath),card.getClass()	);
					break;

					case 1:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(acePath),card.getClass()	);
					break;
					case 4: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(fourPath),card.getClass()	);
					break;
					case 5: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(fivePath),card.getClass()	);
					break;
					case 7:  assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(sevenPath),card.getClass()	);
					break;
					case 10: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(tenPath),card.getClass()	);
					break;
					case 11:assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(jackPath),card.getClass()	);
					break;
					case 12: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(queenPath),card.getClass()	);
					break;
					case 13: assertEquals("wrong card created, make sure you create the correct card based on the code", 
							Class.forName(kingPath),card.getClass()	);
					break;

					default:
						break;
					}


				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException |
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}


	@Test(timeout = 2000)
	public void testLoadCardPoolInDeckReadingCSVLoadingStandardCards2() throws IOException {
		savingCardsCSV();


		try {
			Field cardsPool_field= Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);


			ArrayList<String> cardsList= writeStandartCSVForLoadCards();

			Object game = createGame();
			Object board= createBoard(game);

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
					assertEquals("Wrong name loaded for Standard card", name,nameField.get(card) );


				}

			}




		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException |
				IllegalArgumentException | IllegalAccessException |
				NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing loadCardPool method check the console "+e.getMessage());
		}

		finally {

			reWriteCardsCSVForLoadCards();
		}
	}



	//	drawCards() Method public
	//	drawCards method static
	@Test(timeout = 1000)
	public void testDrawCardsInDeckisStatic() {

		Method m;
		try {
			m = Class.forName(deckPath).getMethod("drawCards",null);
			assertTrue("drawCards expected to be Static",Modifier.isStatic(m.getModifiers()));
			assertTrue("drawCards expected to be Static",Modifier.isPublic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing drawCards method check the console "+e.getMessage());
		}

	}


	//	drawCards() Method returns shuffles

	//	drawCards() Method returns the first four cards
	@Test(timeout=1000) 
	public void testDrawCardsInDeckReturnsFourCards() {
		Field cardsPool_field;
		try {
			cardsPool_field = Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= new ArrayList<>();



			Object game = createGame();
			Object board= createBoard(game);
			Object card1=createStandardCard(game,board);
			Object card2=createAceCard(game,board);
			Object card3=createKingCard(game,board);
			Object card4=createQueenCard(game,board);
			Object card5=createBurnerCard(game,board);
			Object card6=createSaverCard(game,board);

			cardsPool_Object.add(card1);
			cardsPool_Object.add(card2);
			cardsPool_Object.add(card3);
			cardsPool_Object.add(card4);
			cardsPool_Object.add(card5);
			cardsPool_Object.add(card6);
			cardsPool_field.set(null,cardsPool_Object);

			Method drawCards= Class.forName(deckPath).getMethod("drawCards",null);
			ArrayList<Object> returnedList=(ArrayList<Object>) drawCards.invoke(null, null);
			assertEquals("Returned list should contain exactly 4 cards",4, returnedList.size());

		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException e) {

			e.printStackTrace();
			fail("error occured while testing drawCards method check the console "+e.getMessage());


		}




	}
	@Test(timeout=1000) 
	public void testDrawCardsInDeckRemovedCardsCards() {
		Field cardsPool_field;
		try {
			cardsPool_field = Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			cardsPool_field.set(null,new ArrayList<>());
			ArrayList<Object> cardsPool_Object= (ArrayList<Object>) cardsPool_field.get(null);



			Object game = createGame();
			Object board= createBoard(game);

			Object card1=createStandardCard(game,board);
			Object card2=createAceCard(game,board);
			Object card3=createKingCard(game,board);
			Object card4=createQueenCard(game,board);
			Object card5=createBurnerCard(game,board);
			Object card6=createSaverCard(game,board);
			Object card7=createSevenCard(game,board);
			Object card8=createTenCard(game,board);
			Object card9=createFiveCard(game,board);
			Object card10=createFourCard(game,board);

			cardsPool_Object.add(card1);
			cardsPool_Object.add(card2);
			cardsPool_Object.add(card3);
			cardsPool_Object.add(card4);
			cardsPool_Object.add(card5);
			cardsPool_Object.add(card6);
			cardsPool_Object.add(card7);
			cardsPool_Object.add(card8);
			cardsPool_Object.add(card9);
			cardsPool_Object.add(card10);

			Method drawCards= Class.forName(deckPath).getMethod("drawCards",null);
			cardsPool_field.set(null,cardsPool_Object);
			ArrayList<Object> returnedList=(ArrayList<Object>) drawCards.invoke(null, null);

			assertEquals("Returned list should contain exactly 4 cards",4, returnedList.size());

			for (int i = 0; i < returnedList.size(); i++) {
				assertFalse("Returned cards should be removed from the cards pool", cardsPool_Object.contains(returnedList.get(i)));
			}
			assertEquals("Cards pool list should only be missing 4 cards, size is incorrect", 6,cardsPool_Object.size() );

		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing drawCards method check the console "+e.getMessage());

		}




	}


	@Test(timeout=1000) 
	public void testDrawCardsInDeckShufflesCards() {
		Field cardsPool_field;
		try {
			cardsPool_field = Class.forName(deckPath).getDeclaredField("cardsPool");
			cardsPool_field.setAccessible(true);
			Object game = createGame();
			Object board= createBoard(game);

			ArrayList<Object> cardsPool_Object= new ArrayList<>();
			ArrayList<Object> cardsPool_Object2= new ArrayList<>();
			Set<ArrayList<Object>> results = new HashSet<>();

			int shuffleCount=0;
			Object card1=createStandardCard(game,board);
			Object card2=createAceCard(game,board);
			Object card3=createKingCard(game,board);
			Object card4=createQueenCard(game,board);
			Object card5=createBurnerCard(game,board);
			Object card6=createSaverCard(game,board);
			Object card7=createSevenCard(game,board);
			Object card8=createTenCard(game,board);
			Object card9=createFiveCard(game,board);
			Object card10=createFourCard(game,board);
			for (int i = 0; i < 10; i++) {

				cardsPool_field.set(null,new ArrayList<>());

				cardsPool_Object= new ArrayList<>();
				cardsPool_Object.add(card1);
				cardsPool_Object.add(card2);
				cardsPool_Object.add(card3);
				cardsPool_Object.add(card4);
				cardsPool_Object.add(card5);
				cardsPool_Object.add(card6);
				cardsPool_Object.add(card7);
				cardsPool_Object.add(card8);
				cardsPool_Object.add(card9);
				cardsPool_Object.add(card10);
				cardsPool_field.set(null,cardsPool_Object);

				Method drawCards= Class.forName(deckPath).getMethod("drawCards",null);
				ArrayList<Object> returnedList=(ArrayList<Object>) drawCards.invoke(null, null);
				results.add(returnedList);

			}
			assertTrue("Draw Cards should shuffle the array",results.size()> 5);

		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error occured while testing drawCards method check the console "+e.getMessage());

		}

	}


	@Test(timeout = 10000)
	public void testGameConstructorInitializationPlayersColours() throws Exception{

		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");	

		Constructor<?> CPUConstructor = Class.forName(CPUPath).getConstructor(String.class,Class.forName(colourPath),Class.forName(boardManagerPath));

		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
		Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
		Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
		ArrayList<Object> colours = new ArrayList<Object>();
		colours.add(colour);
		colours.add(colour1);
		colours.add(colour2);
		colours.add(colour3);

		Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		Object board = boardConstructor.newInstance(colours,game);



		Class curr = game.getClass();

		Field playersField = null;

		try {
			playersField = curr.getDeclaredField("players");
			playersField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			curr = curr.getSuperclass();
			fail("error occured while testing game constructor check the console "+e.getMessage());

		}

		ArrayList<Object> myPlayers = (ArrayList<Object>) playersField.get(game);

		Field colourField=Class.forName(playerPath).getDeclaredField("colour");
		colourField.setAccessible(true);

		Set<Object> colourSet= new HashSet<>();
		for (int i = 0; i < myPlayers.size(); i++) {
			Object colourPlayers=colourField.get(myPlayers.get(i));
			colourSet.add(colourPlayers);

		}

		assertTrue("Each player in the players list should be assigned a unique colour",colourSet.size()==4);


	}


	@Test(timeout = 10000)
	public void testGameConstructorInitializationHandPlayers() throws Exception{


		Constructor<?> gameConstructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = gameConstructor.newInstance("PlayerName");	


		Class curr = game.getClass();

		Field playersField = null;

		try {
			playersField = curr.getDeclaredField("players");
			playersField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			curr = curr.getSuperclass();
			fail("error occured while testing game constructor check the console "+e.getMessage());

		}

		ArrayList<Object> myPlayers = (ArrayList<Object>) playersField.get(game);

		Field handField=Class.forName(playerPath).getDeclaredField("hand");
		handField.setAccessible(true);

		Set<ArrayList<Object>> handSet= new HashSet<>();
		for (int i = 0; i < myPlayers.size(); i++) {
			ArrayList<Object> handPlayers=(ArrayList<Object>) handField.get(myPlayers.get(i));
			assertEquals("Player's hand should countain exactly 4 cards", 4, handPlayers.size());
			handSet.add(handPlayers);

		}

		assertTrue("Each player in the players list should be assigned a unique hand. make sure that loadCardPool create"
				+ " unique cards and not copy the same cards according to the frequency"
				,handSet.size()==4);

	}



	@Test(timeout = 10000)
	public void testGameConstructorInitializationGameMangers() throws Exception{


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




		ArrayList<Object> myPlayers = (ArrayList<Object>) playersField.get(game);

		Field handField=Class.forName(playerPath).getDeclaredField("hand");
		Field game_manager_card_Field=Class.forName(cardPath).getDeclaredField("gameManager");
		Field board_manager_card_Field=Class.forName(cardPath).getDeclaredField("boardManager");
		handField.setAccessible(true);
		game_manager_card_Field.setAccessible(true);
		board_manager_card_Field.setAccessible(true);

		for (int i = 0; i < myPlayers.size(); i++) {
			ArrayList<Object> handPlayers=(ArrayList<Object>) handField.get(myPlayers.get(i));
			for (int j = 0; j < handPlayers.size(); j++) {
				Object card= handPlayers.get(j);
				assertEquals("Error passing the reference of this game as GameManager"
						,game , game_manager_card_Field.get(handPlayers.get(j)));
			}

		}



	}


	@Test(timeout = 10000)
	public void testGameConstructorInitializationBoardMangers() throws Exception{


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




		ArrayList<Object> myPlayers = (ArrayList<Object>) playersField.get(game);

		Field handField=Class.forName(playerPath).getDeclaredField("hand");
		Field game_manager_card_Field=Class.forName(cardPath).getDeclaredField("gameManager");
		Field board_manager_card_Field=Class.forName(cardPath).getDeclaredField("boardManager");
		handField.setAccessible(true);
		game_manager_card_Field.setAccessible(true);
		board_manager_card_Field.setAccessible(true);

		for (int i = 0; i < myPlayers.size(); i++) {
			ArrayList<Object> handPlayers=(ArrayList<Object>) handField.get(myPlayers.get(i));
			for (int j = 0; j < handPlayers.size(); j++) {
				Object card= handPlayers.get(j);

				assertEquals("Error passing the reference of the created board as a BoardManager"
						, board_game, board_manager_card_Field.get(handPlayers.get(j)));
			}

		}



	}

	@Test(timeout=100)
	public void testColourValues() {
		testEnumValues(colourPath, "Colour", new String[] {"GREEN","RED", "YELLOW", "BLUE"});

	}


	////////////////////////////////////////// 

	/*------------------- testing the existence of classes-----------------*/
	@Test(timeout = 100)
	public void testClassCardExists() {
		try {
			Class.forName(cardPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Card");
		}
	}



	@Test(timeout = 100)
	public void testClassAceExists() {
		try {
			Class.forName(acePath);
		} catch (ClassNotFoundException e) {
			fail("missing class Ace");
		}
	}

	@Test(timeout = 100)
	public void testClassKingExists() {
		try {
			Class.forName(kingPath);
		} catch (ClassNotFoundException e) {
			fail("missing class King");
		}
	}


	@Test(timeout = 100)
	public void testClassJackExists() {
		try {
			Class.forName(jackPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Jack");
		}
	}

	@Test(timeout = 100)
	public void testClassTenExists() {
		try {
			Class.forName(tenPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Ten");
		}
	}

	@Test(timeout = 100)
	public void testClassFiveExists() {
		try {
			Class.forName(fivePath);
		} catch (ClassNotFoundException e) {
			fail("missing class Five");
		}
	}
	@Test(timeout = 100)
	public void testClassBurnerExists() {
		try {
			Class.forName(burnerPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Burner");
		}
	}



	/*-----------------------------   testing if the class is abstract ---------------------------*/

	@Test(timeout = 100)
	public void testIsCardAnAbstractClass() throws Exception {
		testClassIsAbstract(Class.forName(cardPath));
	}

	/*----------------------------- testing if the constructor exists ---------------------------*/


	@Test(timeout = 100)
	public void testConstructorStandardExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, int.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(standardPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorAceExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(acePath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorKingExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(kingPath), inputs);
	}



	@Test(timeout = 100)
	public void testConstructorJackExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(jackPath), inputs);
	}


	@Test(timeout = 100)
	public void testConstructorSevenExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(sevenPath), inputs);
	}



	@Test(timeout = 100)
	public void testConstructorfourExists() throws ClassNotFoundException {
		Class[] inputs = { String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath)};
		testConstructorExists(Class.forName(fourPath), inputs);
	}

	/*-----------------------------test constructor initialization------------------------------------------------*/

	@Test(timeout = 10000)
	public void testStandardCardConstructorInitialization() throws Exception {

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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",1,suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","description",1,suit, board, game};

		// Use a helper method to verify that the card's fields are properly initialized.
		testConstructorInitialization(card, fieldNames, expectedValues);
	}


	@Test(timeout = 10000)
	public void testKingCardConstructorInitialization() throws Exception {

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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(kingPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","description",13,suit, board, game};

		// Use a helper method to verify that the card's fields are properly initialized.
		testConstructorInitialization(card, fieldNames, expectedValues);
	}

	@Test(timeout = 10000)
	public void testQueenCardConstructorInitialization() throws Exception {

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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(queenPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","description",12,suit, board, game};

		// Use a helper method to verify that the card's fields are properly initialized.
		testConstructorInitialization(card, fieldNames, expectedValues);
	}

	@Test(timeout = 10000)
	public void testTenCardConstructorInitialization() throws Exception {

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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(tenPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","description",10,suit, board, game};

		// Use a helper method to verify that the card's fields are properly initialized.
		testConstructorInitialization(card, fieldNames, expectedValues);
	}

	@Test(timeout = 10000)
	public void testSevenCardConstructorInitialization() throws Exception {

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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(sevenPath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","description",7,suit, board, game};

		// Use a helper method to verify that the card's fields are properly initialized.
		testConstructorInitialization(card, fieldNames, expectedValues);
	}

	@Test(timeout = 10000)
	public void testFiveCardConstructorInitialization() throws Exception {

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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(fivePath).getConstructor(String.class,String.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","description",5,suit, board, game};

		// Use a helper method to verify that the card's fields are properly initialized.
		testConstructorInitialization(card, fieldNames, expectedValues);
	}




	/*-----------------------------testing the subclasses------------------------------------------------*/
	@Test(timeout = 100)
	public void testTestingStandardIsSubClassOfCard() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(standardPath), Class.forName(cardPath));
	}

	@Test(timeout = 100)
	public void testTestingQueenIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(queenPath), Class.forName(standardPath));
	}
	@Test(timeout = 100)
	public void testTestingJackIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(jackPath), Class.forName(standardPath));
	}

	@Test(timeout = 100)
	public void testTestingfiveIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(fivePath), Class.forName(standardPath));
	}
	@Test(timeout = 100)
	public void testTestingsevenIsSubClassOfStandrad() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(sevenPath), Class.forName(standardPath));
	}


	/*------------------------------test existence of attributes -----------------------------------------------*/
	// Test Names Changed 
	@Test(timeout = 100)
	public void testCardInstanceVariableNameIsPresent() throws NoSuchFieldException, ClassNotFoundException {

		testInstanceVariableIsPresent(Class.forName(cardPath), "name");
	}

	@Test(timeout = 100)
	public void testCardInstanceVariablBoardManagerIsPresent() throws NoSuchFieldException, ClassNotFoundException {
		testInstanceVariableIsPresent( Class.forName(cardPath), "boardManager");
	}

	@Test(timeout = 100)
	public void testCardInstanceVariablRankIsPresent() throws NoSuchFieldException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(standardPath), "rank");
	}

	/*--------------------------testing if attributes are final-----------------------------------*/


	@Test(timeout = 100)
	public void testCardInstanceVariableNameisFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field nameField = Class.forName(cardPath).getDeclaredField("name");
		assertTrue("The name instance variable in class Card should be final", java.lang.reflect.Modifier.isFinal(nameField.getModifiers()));
	}

	@Test(timeout = 100)
	public void testCardInstanceVariableDescriptionisFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field descriptionField = Class.forName(cardPath).getDeclaredField("description");
		assertTrue("The description instance variable in class Card should be final", java.lang.reflect.Modifier.isFinal(descriptionField.getModifiers()));
	}



	@Test(timeout = 100)
	public void testCardInstanceVariableSuitiFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field suitField = Class.forName(standardPath).getDeclaredField("suit");
		assertTrue("The suit instance variable in class Card should be final", java.lang.reflect.Modifier.isFinal(suitField.getModifiers()));
	}


	/*--------------------------testing getters existence -----------------------------------*/


	@Test(timeout = 100)
	public void testDescriptionGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(cardPath),"getDescription",String.class,true);
	}

	@Test(timeout = 100)
	public void testRankGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(standardPath),"getRank",int.class,true);
	}

	@Test(timeout = 100)
	public void testSuitGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(standardPath),"getSuit",Class.forName(suitPath),true);
	}

	/*--------------------------testing getters logic -----------------------------------*/

	@Test(timeout = 100)
	public void testGetterLogicForNameInClassStandard() throws Exception {
		int random = (int) (Math.random() * 100);
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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","description",1,suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card"+random,"description",1,suit, board, game};


		testGetterLogic(card, "name", "card"+random);
	}

	@Test(timeout = 100)
	public void testGetterLogicForDescriptionInClassStandard() throws Exception {
		int random = (int) (Math.random() * 100);
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


		//Card constructor 
		Constructor<?> cardStandardConstructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class, Class.forName(suitPath),Class.forName(boardManagerPath), Class.forName(gameManagerPath) );
		Object card = cardStandardConstructor.newInstance("card","descriptiontest"+random,1,suit,board,game);

		//the expected field names and corresponding expected values.
		String[] fieldNames = {"name", "description", "rank", "suit", "boardManager", "gameManager"};
		Object[] expectedValues = {"card","descriptiontest"+random ,1,suit, board, game};


		testGetterLogic(card, "description", "descriptiontest"+random);
	}



	@Test(timeout = 100)
	public void testGetterLogicForSuiteInClassStandard() throws Exception {

		// Get all possible values of the Suit Enum 
		Class<?> suitClass = Class.forName(suitPath);
		Object[] suitValues = suitClass.getEnumConstants();

		// Randomly select one suit
		Random random = new Random();
		Object suit = suitValues[random.nextInt(suitValues.length)]; 

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

		Object[] expectedValues = {"card","descriptiontest",rank ,suit ,board ,game};


		testGetterLogic(card, "suit", suit);
	}

	/*--------------------------testing setters missing -----------------------------------*/

	@Test(timeout = 1000)
	public void testSetterForInstanceVariableNameDoesNotExistInAnyClass() throws Exception {
		String[] classs = { cardPath , standardPath, acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testSetterAbsent("name", classs);
	}

	@Test(timeout = 1000)
	public void testSetterForInstanceVariableRankDoesNotExistInAnyClass() throws Exception {
		String[] classs = { cardPath , standardPath, acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testSetterAbsent("rank", classs);
	}




	/*--------------------------testing getters missing in subclasses ------------------*/

	@Test(timeout = 1000)
	public void testGetterForInstanceVariableNameDoesNotExistInWeaponSubClasses() throws Exception {
		String[] subClasses = { acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testGetterAbsentInSubclasses("name", subClasses);
	}



	@Test(timeout = 1000)
	public void testGetterForInstanceVariableRankDoesNotExistInWeaponSubClasses() throws Exception {
		String[] subClasses = { acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath};
		testGetterAbsentInSubclasses("rank", subClasses);
	}

	@Test(timeout = 1000)
	public void testGetterForInstanceVariableSuitDoesNotExistInWeaponSubClasses() throws Exception {
		String[] subClasses = { acePath, kingPath, queenPath, jackPath, tenPath, sevenPath, fivePath, fourPath };
		testGetterAbsentInSubclasses("suit", subClasses);
	}



	/*--------------------------testing attribute modifiers ------------------*/

	@Test(timeout = 10000)
	public void testBoardManagerModifier() throws Exception {

		testInstanceVariableIsProtected(Class.forName(cardPath), "boardManager");

	}


	@Test(timeout = 10000)
	public void testCardDescriptionModifiers4() throws Exception {

		testInstanceVariableIsPrivate(Class.forName(cardPath), "description");

	}

	@Test(timeout = 10000)
	public void testStandardCardRankModifiers1() throws Exception {

		testInstanceVariableIsPrivate(Class.forName(standardPath), "rank");

	}




	/*------------------------------ Player Class --------------------------------------*/
	/*------------------------------ attributes --------------------------------*/
	/*------------------------------ Name attribute --------------------------------*/


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(playerPath), "name");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(playerPath), "name");
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(playerPath), "setName");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableNameGetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String input_name = generateRandomString(10);
		Class<?> input_class_to_constructor = Class.forName(colourPath);
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Constructor<?> constructor = Class.forName(playerPath).getConstructor(String.class,input_class_to_constructor);
		Object instance = constructor.newInstance(input_name,colour);
		testGetterMethodLogic(instance, "name", input_name);	
	}
	/*------------------------------ Colour attribute --------------------------------*/
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(playerPath), "colour");
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(playerPath), "colour");
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(playerPath), "colour");
	}

	// case for each initializing to each different color 
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableColourGetterLogicCase1() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String input_name = generateRandomString(10);
		Class<?> input_class_to_constructor = Class.forName(colourPath);
		Random random = new Random();
		int randomNumber = random.nextInt(4) + 1;
		Enum colour_Object = null;
		switch(randomNumber){
		case 1:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			break;
		}
		case 2:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			break;
		}
		case 3:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			break;
		}
		case 4:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			break;
		}

		}
		Constructor<?> constructor = Class.forName(playerPath).getConstructor(String.class,input_class_to_constructor);
		Object instance = constructor.newInstance(input_name,colour_Object);
		testGetterMethodLogic(instance, "colour", colour_Object);	
	}


	/*------------------------------ selectedMarbles attribute --------------------------------*/


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(playerPath), "selectedMarbles");
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesOfTypeArrayList() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(playerPath), "selectedMarbles", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(playerPath), "selectedMarbles");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedMarblesGetterIsAbsent() throws ClassNotFoundException {
		testGetterMethodIsAbsentInClass(Class.forName(playerPath), "getSelectedMarbles");
	}
	/*------------------------------ selectedCard attribute --------------------------------*/


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedCardOfTypeCard() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> cardClass = Class.forName(cardPath); 
		testInstanceVariableOfType(Class.forName(playerPath), "selectedCard", cardClass);
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedCardSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(playerPath), "setSelectedCard");
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableSelectedCardGetterLogic() throws Exception {
		Class<?> player_class = Class.forName(playerPath);
		Class<?> card_class = Class.forName(cardPath);
		Class<?> colour_class = Class.forName(colourPath);

		Class<?> board_manager_class = Class.forName(boardManagerPath);
		Class<?> game_manager_class = Class.forName(gameManagerPath);

		String player_name = generateRandomString(5);

		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);


		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);
		Object board = board_constructor.newInstance(colourOrder,game);

		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_instance = player_constructor.newInstance(input_name,colour);
		Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),board_manager_class,game_manager_class);

		Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");

		Object card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);

		Field card_field = player_class.getDeclaredField("selectedCard");
		card_field.setAccessible(true);
		card_field.set(player_instance, card_instance);

		testGetterLogic(player_instance,"selectedCard",card_instance);

	}

	/*------------------------------ marbles attribute --------------------------------*/


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(playerPath), "marbles");
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(playerPath), "marbles");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesOfTypeArrayListOfMarbles() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> marbleClass = Class.forName(marblePath); 
		Field field = playerClass.getDeclaredField("marbles");
		Type genericType = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		assertEquals("marbles must be an ArrayList of Marble", marbleClass, typeArguments[0]);
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(playerPath), "getMarbles", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableMarblesGetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> colour_class = Class.forName(colourPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_instance = player_constructor.newInstance(input_name,colour);
		ArrayList<Object> expected_marbles = new ArrayList();

		Class<?> marbleClass = Class.forName(marblePath);
		Class<?> colourClass = Class.forName(colourPath);
		Object redColour = Enum.valueOf((Class<Enum>) colourClass, "RED");
		Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
		Object marbleInstance = marbleConstructor.newInstance(redColour);
		expected_marbles.add(marbleInstance);
		expected_marbles.add(marbleInstance);
		expected_marbles.add(marbleInstance);
		Field f1 = playerClass.getDeclaredField("marbles");
		f1.setAccessible(true);
		f1.set(player_instance, expected_marbles);

		testGetterMethodLogic(player_instance, "marbles", expected_marbles);	
	}


	/*------------------------------ hand attribute --------------------------------*/

	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(playerPath), "hand");
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(playerPath), "hand");
	}

	@Test(timeout = 1000)
	public void testPlayerInstanceVariablehHandOfTypeArrayListOfCard() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> cardClass = Class.forName(cardPath); 
		Field field = playerClass.getDeclaredField("hand");
		Type genericType = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		assertEquals("hand must be an ArrayList of Card", cardClass, typeArguments[0]);
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(playerPath), "getHand", ArrayList.class);
	}


	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandGetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> colour_class = Class.forName(colourPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_instance = player_constructor.newInstance(input_name,colour);
		ArrayList<Object> expected_hand = new ArrayList();
		Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
		String player_name = generateRandomString(5);

		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);

		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		Object board = board_constructor.newInstance(colourOrder,game);

		Object card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);
		expected_hand.add(card_instance);
		expected_hand.add(card_instance);
		expected_hand.add(card_instance);
		expected_hand.add(card_instance);

		Field f1 = playerClass.getDeclaredField("hand");
		f1.setAccessible(true);
		f1.set(player_instance, expected_hand);

		testGetterMethodLogic(player_instance, "hand", expected_hand);	
	}
	@Test(timeout = 1000)
	public void testPlayerInstanceVariableHandSetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> colour_class = Class.forName(colourPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_instance = player_constructor.newInstance(input_name,colour);
		Method m = playerClass.getDeclaredMethod("setHand", ArrayList.class);
		ArrayList<Object> expected_hand = new ArrayList();

		Constructor<?> card_constructor = Class.forName(standardPath).getConstructor(String.class,String.class,int.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
		Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
		String player_name = generateRandomString(5);

		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);

		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		Object board = board_constructor.newInstance(colourOrder,game);

		Object card_instance = card_constructor.newInstance(input_name,input_name,0,suit_Object,board,game);
		expected_hand.add(card_instance);
		expected_hand.add(card_instance);
		expected_hand.add(card_instance);
		expected_hand.add(card_instance);
		m.invoke(player_instance, expected_hand);
		testSetterMethodLogic(player_instance, "hand", expected_hand, ArrayList.class);

	}


	@Test(timeout = 1000)
	public void testPlayerConstructorInitialization() throws Exception{
		Class<?> colour_class = Class.forName(colourPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_Object = player_constructor.newInstance(input_name, colour_Object);
		String[] names = {"name", "colour"};
		Object[] values = {input_name,colour_Object};
		testConstructorInitialization(player_Object, names, values);
	}

	@Test(timeout = 1000)
	public void testPlayerConstructorInitializationHandCorrect() throws Exception{
		try{Class<?> colour_class = Class.forName(colourPath);
		String input_name = generateRandomString(10);
		Class<?> playerClass = Class.forName(playerPath);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_Object = player_constructor.newInstance(input_name, colour_Object);
		Field f1 = playerClass.getDeclaredField("hand");
		f1.setAccessible(true);
		ArrayList<Object> expectedHand = (ArrayList<Object>) f1.get(player_Object);

		assertTrue("The hand initialization isn't correct",expectedHand.size() == 0);}
		catch(NullPointerException e){
			fail("Attributes should be initialized correctly.");
		}
	}


	@Test(timeout = 1000)
	public void testPlayerConstructorInitializationSelectedMarblesCorrect() throws Exception{
		Class<?> colour_class = Class.forName(colourPath);
		Class<?> playerClass = Class.forName(playerPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		Object player_Object = player_constructor.newInstance(input_name, colour_Object);
		Field f1 = playerClass.getDeclaredField("selectedMarbles");
		f1.setAccessible(true);
		ArrayList<?> expectedSelectedMarbles = (ArrayList<?>) f1.get(player_Object);

		assertTrue("The SelectedMarbles initialization isn't correct",expectedSelectedMarbles.size() == 0);
	}

	@Test(timeout = 1000)
	public void testPlayerConstructorInitializationMarblesCorrect() throws Exception{
		Class<?> colour_class = Class.forName(colourPath);
		Class<?> playerClass = Class.forName(playerPath);
		String input_name = generateRandomString(10);
		Constructor<?> player_constructor = Class.forName(playerPath).getConstructor(String.class,colour_class);
		Random random = new Random();
		int randomNumber = random.nextInt(4) + 1;
		Enum colour_Object = null;
		switch(randomNumber){
		case 1:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			break;
		}
		case 2:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			break;
		}
		case 3:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			break;
		}
		case 4:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			break;
		}

		}
		Object player_Object = player_constructor.newInstance(input_name, colour_Object);
		Field f1 = playerClass.getDeclaredField("marbles");
		f1.setAccessible(true);
		ArrayList<?> returnMarbles = (ArrayList<?>) f1.get(player_Object);

		ArrayList<Object> expectedMarbles = new ArrayList<>();;
		Class<?> marbleClass = Class.forName(marblePath);
		Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);
		for (int i = 0; i < 4; i++) {
			Object marbleInstance = marbleConstructor.newInstance(colour_Object);
			expectedMarbles.add(marbleInstance);
		}
		if (expectedMarbles.size() != returnMarbles.size()) {
			fail("The sizes of the marble lists initialized in the constructor differ: expected " + expectedMarbles.size() 
			+ " but got " + returnMarbles.size());
		}
		for (int i = 0; i < expectedMarbles.size(); i++) {
			Object expectedMarble = expectedMarbles.get(i);
			Object actualMarble = returnMarbles.get(i);
			Field[] fields = expectedMarble.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object expectedValue = field.get(expectedMarble);
				Object actualValue = field.get(actualMarble);
				if (expectedValue == null) {
					if (actualValue != null) {
						fail("Mismatch at index " + i + " in field " + field.getName() 
						+ ": expected null but got " + actualValue);
					}
				} else if (!expectedValue.equals(actualValue)) {
					fail("Mismatch at index " + i + " in field " + field.getName() 
					+ ": expected " + expectedValue + " but got " + actualValue);
				}
			}
		}

	}




	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*------------------------------ CPU Class --------------------------------------*/
	/*------------------------------ Is SubClass --------------------------------------*/
	@Test(timeout = 1000)
	public void testCPUSubToPlayer() throws ClassNotFoundException {
		Class<?> playerClass = Class.forName(playerPath); 
		Class<?> cpuClass = Class.forName(CPUPath); 
		testClassIsSubClass(playerClass,cpuClass);
	}

	/*------------------------------ Attributes --------------------------------------*/
	/*------------------------------ boardManager --------------------------------------*/
	@Test(timeout = 1000)
	public void testCPUInstanceVariableBoardManagerIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(CPUPath), "boardManager");
	}
	@Test(timeout = 1000)
	public void testCPUInstanceVariableBoardManagerIsFinal() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(CPUPath), "boardManager");
	}

	@Test(timeout = 1000)
	public void testCPUInstanceVariableBoardManagerSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(CPUPath), "boardManager");
	}


	/*------------------------------ Constructor --------------------------------------*/

	@Test(timeout = 1000)
	public void testCPUConstructorExists() throws ClassNotFoundException {
		Class<?> colour_class = Class.forName(colourPath);
		Class<?> board_manager_class = Class.forName(boardManagerPath);
		Class[] parameters = {String.class,colour_class,board_manager_class};
		testConstructorExists(Class.forName(CPUPath), parameters);
	}
	@Test(timeout = 1000)
	public void testCPUConstructorInitialization() throws Exception{
		Class<?> colour_class = Class.forName(colourPath);
		Class<?> board_manager_class = Class.forName(boardManagerPath);
		String input_name = generateRandomString(10);
		Constructor<?> cpu_constructor = Class.forName(CPUPath).getConstructor(String.class,colour_class,board_manager_class);
		Object colour_Object = null;
		Random random = new Random();
		int randomNumber = random.nextInt(4) + 1;
		switch(randomNumber){
		case 1:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			break;
		}
		case 2:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			break;
		}
		case 3:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			break;
		}
		case 4:
		{
			colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			break;
		}

		}

		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);

		ArrayList<Object> colourOrder = new ArrayList();
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);

		Object board_object = board_constructor.newInstance(colourOrder,game);




		Object cpu_object = cpu_constructor.newInstance(input_name,colour_Object,board_object);

		String[] names = {"name", "colour","boardManager"};
		Object[] values = {input_name,colour_Object,board_object};
		testConstructorInitialization(cpu_object, names, values);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/*------------------------------ Board Class --------------------------------------*/
	/*------------------------------ Class Existence --------------------------------*/

	@Test(timeout = 1000)
	public void testBoardClassExists() {
		try {
			Class.forName(boardPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Board");
		}
	}
	@Test(timeout = 1000)
	public void testBoardImplementsBoardManager() throws Exception {
		Class<?> boardClass = Class.forName(boardPath);
		Class<?> boardManagerInterface = Class.forName(boardManagerPath);
		assertTrue("Board should implement BoardManager",
				boardManagerInterface.isAssignableFrom(boardClass));
	}

	/*------------------------------ attributes --------------------------------*/
	/*------------------------------ GameManager attribute --------------------------------*/



	@Test(timeout = 1000)
	public void testBoardInstanceVariableGameManagerOfTypeGameManager() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> game_manager_class = Class.forName(gameManagerPath);
		testInstanceVariableOfType(Class.forName(boardPath), "gameManager", game_manager_class);
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableGameManagerIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(boardPath), "gameManager");
	}


	@Test(timeout = 1000)
	public void testBoardInstanceVariableGameManagerGetterIsAbsent() throws ClassNotFoundException {
		testGetterMethodIsAbsentInClass(Class.forName(boardPath), "gameManager");
	}

	/*------------------------------ track attribute --------------------------------*/

	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(boardPath), "track");
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(boardPath), "track");
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackIsFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(boardPath), "track");
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariablehTrackOfTypeArrayListOfCell() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> board_class = Class.forName(boardPath); 
		Class<?> cell_class = Class.forName(cellPath); 
		Field field = board_class.getDeclaredField("track");
		Type genericType = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		assertEquals("track must be an ArrayList of Cell", cell_class, typeArguments[0]);
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(boardPath), "setTrack");
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariableTrackGetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		Class<?> board_class = Class.forName(boardPath); 
		Class<?> game_manager_class = Class.forName(gameManagerPath); 
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);

		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);
		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);

		Object board_object = board_constructor.newInstance(colourOrder,game);



		Class cell_type_class = Class.forName(cellTypePath);
		Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


		ArrayList<Object> track = new ArrayList();
		Object cell_object = null;
		Object new_cell_object = null;
		for (int i = 0; i < 100; i++)
		{
			cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL"));
			track.add(cell_object);
			if (i % 25 == 0) {
				new_cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "BASE"));
				track.set(i, new_cell_object);
			}
			else if ((i+2) % 25 == 0) {
				new_cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY"));
				track.set(i, new_cell_object);
			}
		}

		Field track_field = Class.forName(boardPath).getDeclaredField("track");
		track_field.setAccessible(true);
		track_field.set(board_object, track);

		testGetterMethodLogic(board_object, "track", track);	
	}
	/*------------------------------ safeZones attribute --------------------------------*/

	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(boardPath), "safeZones");
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(boardPath), "safeZones");
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariablehSafeZonesOfTypeArrayListOfSafeZone() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Class<?> board_class = Class.forName(boardPath); 
		Class<?> safe_zone_class = Class.forName(safeZonePath); 
		Field field = board_class.getDeclaredField("safeZones");
		Type genericType = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		assertEquals("SafeZones must be an ArrayList of SafeZone", safe_zone_class, typeArguments[0]);
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(boardPath), "getSafeZones", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariableSafeZonesSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(boardPath), "setSafeZones");
	}



	/*------------------------------ splitDistance attribute --------------------------------*/


	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceOfTypeInt() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(boardPath), "splitDistance", int.class);
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(boardPath), "getSplitDistance", int.class);
	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceGetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<?> board_class = Class.forName(boardPath); 
		Class<?> game_manager_class = Class.forName(gameManagerPath); 
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);
		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);

		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);

		Random random = new Random();
		int randomNumber = random.nextInt(100) + 1;
		Object board_object = board_constructor.newInstance(colourOrder,game);
		Field f = Class.forName(boardPath).getDeclaredField("splitDistance");
		f.setAccessible(true);
		f.setInt(board_object, randomNumber);

		testGetterMethodLogic(board_object, "splitDistance", randomNumber);

	}
	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceSetterExists() throws ClassNotFoundException {
		testSetterMethodExistInClass(Class.forName(boardPath), "setSplitDistance", int.class);
	}

	@Test(timeout = 1000)
	public void testBoardInstanceVariableSplitDistanceSetterLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<?> board_class = Class.forName(boardPath); 
		Class<?> game_manager_class = Class.forName(gameManagerPath); 
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);

		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);
		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);

		Random random = new Random();
		int randomNumber = random.nextInt(100) + 1;
		Object board_object = board_constructor.newInstance(colourOrder,game);
		testSetterMethodLogic(board_object, "splitDistance", randomNumber, int.class);		
	}





	/*------------------------------ Constructor Existence --------------------------------*/	


	@Test(timeout = 1000)
	public void testBoardConstructorInitializationSplittingDisctanceCorrect() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, InvocationTargetException {

		Class<?> board_class = Class.forName(boardPath); 
		Class<?> game_manager_class = Class.forName(gameManagerPath); 
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);


		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);

		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);

		Object board_object = board_constructor.newInstance(colourOrder,game);
		Field f = Class.forName(boardPath).getDeclaredField("splitDistance");
		f.setAccessible(true);
		int returnValue = (int) f.get(board_object);
		assertEquals("The splitDistance initialization isn't correct",3,returnValue);

	}

	@Test(timeout = 1000)
	public void testBoardConstructorInitializationGameManagerCorrect() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, InvocationTargetException {

		Class<?> board_class = Class.forName(boardPath); 
		Class<?> game_manager_class = Class.forName(gameManagerPath); 
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);

		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);
		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);

		Object board_object = board_constructor.newInstance(colourOrder,game);

		String[] names = { "gameManager"};
		Object[] values = {game};
		testConstructorInitialization(board_object, names, values);

	}

	@Test(timeout = 1000)
	public void testBoardConstructorInitializationTrackCorrect() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, InvocationTargetException {

		Class<?> board_class = Class.forName(boardPath); 
		Class<?> game_manager_class = Class.forName(gameManagerPath); 
		Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);

		ArrayList<Object> colourOrder = new ArrayList();
		Object colour_Object = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);
		colourOrder.add(colour_Object);


		String player_name = generateRandomString(5);
		Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
		Object game = game_constructor.newInstance(player_name);
		Object board_object = board_constructor.newInstance(colourOrder,game);

		Class cell_type_class = Class.forName(cellTypePath);
		Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


		ArrayList<Object> track = new ArrayList();
		Object cell_object = null;
		Object new_cell_object = null;
		for (int i = 0; i < 100; i++)
		{
			cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL"));
			track.add(cell_object);
			if (i % 25 == 0) {
				new_cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "BASE"));
				track.set(i, new_cell_object);
			}
			else if ((i+2) % 25 == 0) {
				new_cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "ENTRY"));
				track.set(i, new_cell_object);
			}
		}

		Field f = Class.forName(boardPath).getDeclaredField("track");
		f.setAccessible(true);
		ArrayList<Object> returnValue =  (ArrayList<Object>) f.get(board_object);

		assertEquals("Lists should have the same size", track.size(), returnValue.size());

		if (!track.isEmpty()) {
			Field cellTypeField = track.get(0).getClass().getDeclaredField("cellType");
			cellTypeField.setAccessible(true);

			for (int i = 0; i < track.size(); i++) {
				Object expectedCell = track.get(i);
				Object actualCell = returnValue.get(i);

				Object expectedCellType = cellTypeField.get(expectedCell);
				Object actualCellType = cellTypeField.get(actualCell);
				assertEquals("Mismatch in cellType at index " + i, expectedCellType, actualCellType);
			}
		}

	}

	@Test(timeout = 1000)
	public void testBoardConstructorInitializationSafeZonesCorrect() throws InstantiationException, IllegalAccessException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException, InvocationTargetException {



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
		Field f = Class.forName(boardPath).getDeclaredField("safeZones");
		f.setAccessible(true);
		ArrayList<Object> returnValue =  (ArrayList<Object>) f.get(board_object);

		assertEquals("Lists should have the same size", safeZones.size(), returnValue.size());
		Field colourField = Class.forName(safeZonePath).getDeclaredField("colour");
		colourField.setAccessible(true);

		for (int i = 0; i < safeZones.size(); i++) {
			Object expectedSafeZone = safeZones.get(i);
			Object actualSafeZone = returnValue.get(i);

			Object expectedColour = colourField.get(expectedSafeZone);
			Object actualColour = colourField.get(actualSafeZone);
			assertEquals("Mismatch in safe zone colour at index " + i,
					expectedColour, actualColour);
		}

	}






	/*------------------------------ assignTrapCell Method --------------------------------*/	
	@Test(timeout = 1000)
	public void testAssignTrapExistanceInClassBoard() throws ClassNotFoundException,SecurityException {
		Class <?> board_class = Class.forName(boardPath);
		try {
			Method m = board_class.getDeclaredMethod("assignTrapCell", null);
		} catch (NoSuchMethodException e) {
			fail("No Such Method assignTrapCell in class board");
		}
	}
	@Test(timeout = 1000)
	public void testAssignTrapIsPrivateInClassBoard() throws ClassNotFoundException,SecurityException, NoSuchMethodException {
		Class <?> board_class = Class.forName(boardPath);
		Method m = board_class.getDeclaredMethod("assignTrapCell", null);
		int modifiers = m.getModifiers();
		assertTrue("AssignTrapCell method should be private",Modifier.isPrivate(modifiers));
	}

	////Test Exceptions are abstract///////////////////////////////////////////////////
	@Test(timeout = 100)
	public void testClassIsAbstractGameException() throws ClassNotFoundException {
		testClassIsAbstract(Class.forName(GameExceptionExceptionPath));
	}

	@Test(timeout = 100)
	public void testClassIsAbstractInvalidSelectionException() throws ClassNotFoundException  {
		testClassIsAbstract(Class.forName(InvalidSelectionExceptionExceptionPath));
	}




	////Test Exception Subclasses//////////////////////////////////////////////////////
	@Test(timeout = 1000)
	public void testClassIsSubclassGameException() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(GameExceptionExceptionPath), Exception.class);
	}


	@Test(timeout = 1000)
	public void testClassIsSubclassActionException () throws ClassNotFoundException{
		testClassIsSubclass(Class.forName(ActionExceptionExceptionPath), Class.forName(GameExceptionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassInvalidCardException () throws ClassNotFoundException  {
		testClassIsSubclass(Class.forName(InvalidCardExceptionExceptionPath), Class.forName(InvalidSelectionExceptionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassIllegalMovementException () throws ClassNotFoundException{
		testClassIsSubclass(Class.forName(IllegalMovementExceptionExceptionPath), Class.forName(ActionExceptionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testClassIsSubclassIllegalSwapException () throws ClassNotFoundException  {
		testClassIsSubclass(Class.forName(IllegalSwapExceptionExceptionPath), Class.forName(ActionExceptionExceptionPath));
	}


	@Test(timeout = 1000)
	public void testClassIsSubclassCannotFieldException () throws ClassNotFoundException  {
		testClassIsSubclass(Class.forName(CannotFieldExceptionExceptionPath), Class.forName(ActionExceptionExceptionPath));
	}



	////Test Exception Empty Constructors///////////////////////////////////////////////////////////////////

	@Test(timeout = 100)
	public void testEmptyConstructorGameException() throws ClassNotFoundException  {

		Class[] inputs = {};
		testConstructorExists(Class.forName(GameExceptionExceptionPath), inputs);
	}


	@Test(timeout = 100)
	public void testEmptyConstructorActionException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(ActionExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testEmptyConstructorInvalidCardException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(InvalidCardExceptionExceptionPath), inputs);
	}


	@Test(timeout = 100)
	public void testEmptyConstructorSplitOutOfRangeException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(SplitOutOfRangeExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testEmptyConstructorIllegalMovementException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(IllegalMovementExceptionExceptionPath), inputs);
	}


	@Test(timeout = 100)
	public void testEmptyConstructorIllegalDestroyException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(IllegalDestroyExceptionExceptionPath), inputs);
	}


	@Test(timeout = 100)
	public void testEmptyConstructorCannotDiscardException() throws ClassNotFoundException {

		Class[] inputs = {};
		testConstructorExists(Class.forName(CannotDiscardExceptionExceptionPath), inputs);
	}

	////Test Exception Constructors with message/////////////////////////////////////////////

	@Test(timeout = 100)
	public void testConstructorGameException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(GameExceptionExceptionPath), inputs);
	}


	@Test(timeout = 100)
	public void testConstructorActionException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(ActionExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorInvalidCardException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(InvalidCardExceptionExceptionPath), inputs);
	}


	@Test(timeout = 100)
	public void testConstructorSplitOutOfRangeException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(SplitOutOfRangeExceptionExceptionPath), inputs);
	}



	@Test(timeout = 100)
	public void testConstructorIllegalSwapException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(IllegalSwapExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorIllegalDestroyException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(IllegalDestroyExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorCannotFieldException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(CannotFieldExceptionExceptionPath), inputs);
	}

	@Test(timeout = 100)
	public void testConstructorCannotDiscardException() throws ClassNotFoundException {

		Class[] inputs = { String.class };
		testConstructorExists(Class.forName(CannotDiscardExceptionExceptionPath), inputs);
	}

	/////////////Test Constructor Initialization///////////////////////////////////////////////

	@Test(timeout = 10000)
	public void testInvalidCardExceptionConstructorInitialization() throws Exception{

		Constructor<?> invalidCardExceptionConstructor = Class.forName(InvalidCardExceptionExceptionPath).getConstructor(String.class);
		Object invalidCardException = invalidCardExceptionConstructor.newInstance("Invalid card has been selected");
		String[] names = {"detailMessage"};
		Object[] values = {"Invalid card has been selected"};
		testExceptionConstructorInitialization(invalidCardException, names, values);
	}


	@Test(timeout = 10000)
	public void testInvalidMarbleExceptionConstructorInitialization() throws Exception{

		Constructor<?> invalidMarbleExceptionConstructor = Class.forName(InvalidMarbleExceptionExceptionPath).getConstructor(String.class);
		Object invalidMarbleException = invalidMarbleExceptionConstructor.newInstance("Invalid marble has been selected");
		String[] names = {"detailMessage"};
		Object[] values = {"Invalid marble has been selected"};
		testExceptionConstructorInitialization(invalidMarbleException, names, values);
	}

	@Test(timeout = 10000)
	public void testInvalidMarbleExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> invalidMarbleExceptionConstructor = Class.forName(InvalidMarbleExceptionExceptionPath).getConstructor(String.class);
		Object invalidMarbleException = invalidMarbleExceptionConstructor.newInstance("Invalid marble");
		String[] names = {"detailMessage"};
		Object[] values = {"Invalid marble"};
		testExceptionConstructorInitialization(invalidMarbleException, names, values);
	}


	@Test(timeout = 10000)
	public void testSplitOutOfRangeExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> splitOutOfRangeExceptionConstructor = Class.forName(SplitOutOfRangeExceptionExceptionPath).getConstructor(String.class);
		Object splitOutOfRangeException = splitOutOfRangeExceptionConstructor.newInstance("Split distance is invalid");
		String[] names = {"detailMessage"};
		Object[] values = {"Split distance is invalid"};
		testExceptionConstructorInitialization(splitOutOfRangeException, names, values);
	}

	@Test(timeout = 10000)
	public void testIllegalMovementExceptionConstructorInitialization() throws Exception{

		Constructor<?> illegalMovementExceptionConstructor = Class.forName(IllegalMovementExceptionExceptionPath).getConstructor(String.class);
		Object illegalMovementException = illegalMovementExceptionConstructor.newInstance("Illegal movement for this marble");
		String[] names = {"detailMessage"};
		Object[] values = {"Illegal movement for this marble"};
		testExceptionConstructorInitialization(illegalMovementException, names, values);
	}



	@Test(timeout = 10000)
	public void testIllegalSwapExceptionConstructorInitialization() throws Exception{

		Constructor<?> illegalSwapExceptionConstructor = Class.forName(IllegalSwapExceptionExceptionPath).getConstructor(String.class);
		Object illegalSwapException = illegalSwapExceptionConstructor.newInstance("Cannot swap these two marbles");
		String[] names = {"detailMessage"};
		Object[] values = {"Cannot swap these two marbles"};
		testExceptionConstructorInitialization(illegalSwapException, names, values);
	}

	@Test(timeout = 10000)
	public void testIllegalSwapExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> illegalSwapExceptionConstructor = Class.forName(IllegalSwapExceptionExceptionPath).getConstructor(String.class);
		Object illegalSwapException = illegalSwapExceptionConstructor.newInstance("Illegal swap");
		String[] names = {"detailMessage"};
		Object[] values = {"Illegal swap"};
		testExceptionConstructorInitialization(illegalSwapException, names, values);
	}



	@Test(timeout = 10000)
	public void testIllegalDestroyExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> illegalDestroyExceptionConstructor = Class.forName(IllegalDestroyExceptionExceptionPath).getConstructor(String.class);
		Object illegalDestroyException = illegalDestroyExceptionConstructor.newInstance("Illegal Destroy");
		String[] names = {"detailMessage"};
		Object[] values = {"Illegal Destroy"};
		testExceptionConstructorInitialization(illegalDestroyException, names, values);
	}

	@Test(timeout = 10000)
	public void testCannotFieldExceptionConstructorInitialization() throws Exception{

		Constructor<?> cannotFieldExceptionConstructor = Class.forName(CannotFieldExceptionExceptionPath).getConstructor(String.class);
		Object cannotFieldException = cannotFieldExceptionConstructor.newInstance("Cannot field this marble on board");
		String[] names = {"detailMessage"};
		Object[] values = {"Cannot field this marble on board"};
		testExceptionConstructorInitialization(cannotFieldException, names, values);
	}


	@Test(timeout = 10000)
	public void testCannotDiscardExceptionConstructorInitialization() throws Exception{

		Constructor<?> cannotDiscardExceptionConstructor = Class.forName(CannotDiscardExceptionExceptionPath).getConstructor(String.class);
		Object cannotDiscardException = cannotDiscardExceptionConstructor.newInstance("Cannot discard this card");
		String[] names = {"detailMessage"};
		Object[] values = {"Cannot discard this card"};
		testExceptionConstructorInitialization(cannotDiscardException, names, values);
	}

	@Test(timeout = 10000)
	public void testCannotDiscardExceptionConstructorInitializationCheck() throws Exception{

		Constructor<?> cannotDiscardExceptionConstructor = Class.forName(CannotDiscardExceptionExceptionPath).getConstructor(String.class);
		Object cannotDiscardException = cannotDiscardExceptionConstructor.newInstance("Discard of this card is not allowed");
		String[] names = {"detailMessage"};
		Object[] values = {"Discard of this card is not allowed"};
		testExceptionConstructorInitialization(cannotDiscardException, names, values);
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
		Object game = gameConstructor.newInstance("PlayerName");


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
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
	SecurityException, ClassNotFoundException {


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
		Object board = boardConstructor.newInstance(colours,game);


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
