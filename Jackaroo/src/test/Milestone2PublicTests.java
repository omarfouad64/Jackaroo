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


public class Milestone2PublicTests {
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


	
	@Test(timeout = 1000)
	public void testActMethodInClassCardIsAbstract()
			throws NoSuchMethodException {
		try {
			Method actMethod = Class.forName(cardPath).getDeclaredMethod("act",
					ArrayList.class);

			assertTrue(" act method in class Card should be abstract",
					Modifier.isAbstract(actMethod.getModifiers()));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalArgumentException e) {

			fail(e.getClass()
					+ " occurred while accessing method validatePath method in class Board.");
		}
	}

	// case 1 : where there is cell in the path has cell type = safe
	@Test(timeout = 1000)
	public void testValidatePathCase1InBoardClass() {
		Class<?> IllegalMovementException = null;
		try {
			IllegalMovementException = Class
					.forName(IllegalMovementExceptionExceptionPath);

			Object game = createGame();
			Object board_object = createBoard(game);

			// Create a marble
			Object marble = createMarbleForActivePlayer(game);
			Object marble_1 = createMarbleForActivePlayer(game);

			// Create a cell
			Object cell_type = createCellType("SAFE");
			Object cell_type_2 = createCellType("NORMAL");
			Object cell = createCell(cell_type);

			Object cell_0 = createCell(cell_type_2);
			Object cell_3 = createCell(cell_type_2);

			Method setMarble = Class.forName(cellPath).getDeclaredMethod(
					"setMarble", Class.forName(marblePath));

			setMarble.invoke(cell, marble_1);

			ArrayList<Object> path = new ArrayList<>();
			path.add(cell_0);
			path.add(cell);
			path.add(cell_3);

			Method validatePath = Class.forName(boardPath).getDeclaredMethod(
					"validatePath", Class.forName(marblePath), ArrayList.class,
					boolean.class);
			validatePath.setAccessible(true);
			validatePath.invoke(board_object, marble, path, true);

			fail("The given path is invalid because it contains a safe zone cell that holds a marble; therefore,an IllegalMovementException is expected to be thrown, but it is not.");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown",
					IllegalMovementException.isInstance(thrownException));

			// Assert that the exception message is as expected.
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
	
	// case 2.1: where there is a base cell in the path and has RED marble and
		// this base cell belong to RED
		@Test(timeout = 1000)
		public void testValidatePathCase2_1InBoardClass() {
			Class<?> IllegalMovementException = null;
			try {

				IllegalMovementException = Class
						.forName(IllegalMovementExceptionExceptionPath);

				Object game = createGame();

				Object board_object = createBoard(game);
				Object marble_1 = createMarbleForActivePlayer(game);

				// create RED marble

				Object colour_Object = createColor("RED");
				Object marble = createMarble(colour_Object);

				// get the track field
				ArrayList<Object> track = getTrack(board_object);

				// add normal cell to the path, so you make the path more realistic
				Object cell_type = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type);

				// get base cell of RED marble from the track
				int basePosition = getBasePosition(board_object, colour_Object);
				Object baseCell = track.get(basePosition);

				// set the base cell of the RED to RED marble
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
				// Assert that the exception message is as expected.
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
		
		@Test(timeout = 1000)
		public void testValidatePathCase3InBoardClass() throws NoSuchFieldException {
			Class<?> IllegalMovementException = null;
			try {
				IllegalMovementException = Class
						.forName(IllegalMovementExceptionExceptionPath);

				Object game = createGame();
				Object board_object = createBoard(game);

				// create marble for active player
				Object marble = createMarbleForActivePlayer(game);
				Object marble_1 = createMarbleForActivePlayer(game);

				// add normal cell to the path, so you make the path more realistic
				Object cell_type_0 = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type_0);

				// Create a cell
				Object cell_type = createCellType("NORMAL");
				Object cell = createCell(cell_type);

				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(cell, marble);

				ArrayList<Object> path = new ArrayList<>();
				path.add(cell_0);
				path.add(cell);

				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, marble_1, path, false);
				fail("The given path is invalid because it contains a cell with a marble belonging to the active player; therefore,an IllegalMovementException is expected to be thrown, but it is not.");

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

		// case 4: where there destroy == false and the path contains cell entry and
		// the next cell is safe
		@Test(timeout = 1000)
		public void testValidatePathCase4InBoardClass() {
			Class<?> IllegalMovementException = null;

			try {
				IllegalMovementException = Class
						.forName(IllegalMovementExceptionExceptionPath);

				Object game = createGame();
				Object board_object = createBoard(game);

				Object cell_type_1 = createCellType("SAFE");
				Object cell_type_2 = createCellType("ENTRY");

				Object marble_1 = createMarbleForActivePlayer(game);
				// create opponent marble

				Object marble = createOpponentMarble(game);

				// add normal cell to the path, so you make the path more realistic
				Object cell_type_0 = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type_0);

				// Create a cell
				Object safe_cell = createCell(cell_type_1);
				Object entry_cell = createCell(cell_type_2);
				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(entry_cell, marble);

				ArrayList<Object> path = new ArrayList<>();
				path.add(cell_0);
				path.add(entry_cell);
				path.add(safe_cell);
				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, marble_1, path, false);
				fail("The given path is invalid because it contains a cell with a marble in the safe zone entry; therefore,an IllegalMovementException is expected to be thrown, but it is not.");

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

		// case 5: where there destroy == false and marbleCount > 1
		@Test(timeout = 1000)
		public void testValidatePathCase5InBoardClass() {
			Class<?> IllegalMovementException = null;
			try {

				IllegalMovementException = Class
						.forName(IllegalMovementExceptionExceptionPath);

				Object game = createGame();
				Object board_object = createBoard(game);

				Object active_player_marble = createMarbleForActivePlayer(game);
				Object marble_0 = createOpponentMarble(game);
				Object marble_1 = createOpponentMarble(game);
				Object marble_2 = createOpponentMarble(game);
				Object marble_3 = createOpponentMarble(game);
				Object cell_type = createCellType("NORMAL");
				Object normal_cell_0 = createCell(cell_type);
				Object normal_cell_1 = createCell(cell_type);
				Object normal_cell_2 = createCell(cell_type);
				Object normal_cell_3 = createCell(cell_type);

				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(normal_cell_0, marble_0);
				setMarble.invoke(normal_cell_1, marble_1);
				setMarble.invoke(normal_cell_2, marble_2);
				setMarble.invoke(normal_cell_3, marble_3);
				ArrayList<Object> path = new ArrayList<>();
				path.add(normal_cell_0);
				path.add(normal_cell_1);
				path.add(normal_cell_2);
				path.add(normal_cell_3);

				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, active_player_marble, path, false);
				fail("The given path is invalid because it contains more than one occupied cells; therefore,an IllegalMovementException is expected to be thrown, but it is not.");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue("Expected IllegalMovementException was not thrown",
						IllegalMovementException.isInstance(thrownException));
				try{
					assertTrue("You should use the exception constructor that assigns a message to the exception.", (thrownException.getMessage()).length() > 0);
				}catch(NullPointerException e_1){
					fail("You should use the exception constructor that assigns a message to the exception.");
				}
			} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}
		}
		
		// checking if the student check on marble color instead of active player
		// color
		@Test(timeout = 1000)
		public void testValidatePathCheckingOnMarbleColorInsteadActivePlayerColorInBoardClass() {
			Class<?> IllegalMovementException = null;
			try {

				IllegalMovementException = Class
						.forName(IllegalMovementExceptionExceptionPath);

				Object game = createGame();
				Object board_object = createBoard(game);

				Object input_marble = createOpponentMarble(game);
				Object marble_0 = createOpponentMarble(game);
				Object marble_1 = createOpponentMarble(game);

				Object cell_type = createCellType("NORMAL");
				Object normal_cell_0 = createCell(cell_type);
				Object normal_cell_1 = createCell(cell_type);

				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(normal_cell_0, marble_0);
				setMarble.invoke(normal_cell_1, marble_1);
				ArrayList<Object> path = new ArrayList<>();
				path.add(normal_cell_0);
				path.add(normal_cell_1);

				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, input_marble, path, false);
			} catch (InvocationTargetException e) {
				fail("The path given shouldn't throw any exception but got:  "
						+ e.getCause());
			} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}
		}
		// checking if the student missed any condition in case 2
		@Test(timeout = 1000)
		public void testValidatePathRemovingConditionFromCase2InBoardClass() {
			try {
				Object game = createGame();

				Object board_object = createBoard(game);
				Object marble_1 = createMarbleForActivePlayer(game);

				// create BLUE marble
				Object base_cell_color = createColor("RED");

				Object colour_Object = createColor("BLUE");
				Object marble = createMarble(colour_Object);

				// get the track field
				ArrayList<Object> track = getTrack(board_object);

				// add normal cell to the path, so you make the path more realistic
				Object cell_type = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type);

				// get base cell of RED marble from the track
				int basePosition = getBasePosition(board_object, base_cell_color);
				Object baseCell = track.get(basePosition);

				// set the base cell of the RED to Blue marble
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
			} catch (InvocationTargetException e) {
				fail("The path given shouldn't throw any exception but got:  "
						+ e.getCause());
			} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}
		}

		// checking if the student include the condition of !destroy or not but I
		// check this with only I condition should I repeat it
		@Test(timeout = 1000)
		public void testValidatePathCheckingTheConditionOfDestoryIsFalseInBoardClass() {
			try {

				Object game = createGame();
				Object board_object = createBoard(game);

				// create marble for active player

				Object marble = createMarbleForActivePlayer(game);
				Object marble_2 = createMarbleForActivePlayer(game);

				// Create a cell
				Object cell_type = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type);
				Object cell = createCell(cell_type);

				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(cell, marble_2);

				ArrayList<Object> path = new ArrayList<>();
				path.add(cell_0);
				path.add(cell);

				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, marble, path, true);
			} catch (InvocationTargetException e) {
				fail("The path given shouldn't throw any exception but got:  "
						+ e.getCause());
			} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}
		}

		// checking if the student start the loop with the 2nd cell in the path or
		// not
		@Test(timeout = 1000)
		public void testValidatePathCheckingTheLoopStartInBoardClass() {
			try {
				Object game = createGame();
				Object board_object = createBoard(game);

				// Create a marble
				Object colour_Object = getActiveColor(game);
				Object marble = createMarble(colour_Object);

				Object marble_2 = createMarbleForActivePlayer(game);

				// Create a cell
				Object cell_type = createCellType("SAFE");
				Object cell = createCell(cell_type);

				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(cell, marble);

				ArrayList<Object> path = new ArrayList<>();
				path.add(cell);

				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, marble_2, path, true);
			} catch (InvocationTargetException e) {
				fail("The path given shouldn't throw any exception but got:  "
						+ e.getCause());
			} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}
		}
		
		// checking if the student exclude the target cell in counting the marble
		@Test(timeout = 1000)
		public void testValidatePathExcludeTargetInCountingInBoardClass() {

			try {

				Object game = createGame();
				Object board_object = createBoard(game);

				Object marble = createMarbleForActivePlayer(game);

				Object marble_0 = createOpponentMarble(game);
				Object marble_1 = createOpponentMarble(game);
				Object marble_2 = createOpponentMarble(game);
				Object cell_type = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type);
				Object cell_1 = createCell(cell_type);
				Object cell_2 = createCell(cell_type);

				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(cell_0, marble_0);
				setMarble.invoke(cell_0, marble_1);
				setMarble.invoke(cell_0, marble_2);

				ArrayList<Object> path = new ArrayList<>();
				path.add(cell_0);
				path.add(cell_1);
				path.add(cell_2);

				Method validatePath = Class.forName(boardPath).getDeclaredMethod(
						"validatePath", Class.forName(marblePath), ArrayList.class,
						boolean.class);
				validatePath.setAccessible(true);
				validatePath.invoke(board_object, marble, path, false);
			} catch (InvocationTargetException e) {
				fail("The path given shouldn't throw any exception but got:  "
						+ e.getCause());
			} catch (Exception e) {
				fail(e.getClass()
						+ " occurred while accessing method validatePath method in class Board.");
			}

		}
		
	

		// checking if the student check only if the path containing entry cell
		// without checking if the next if safe (giving here the path contain entry
		// cell then normal cell)
		@Test(timeout = 1000)
		public void testValidatePathRemovingConditionSafeCellFromCase4InBoardClass() {
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
				Object marble_for_normal_cell = createOpponentMarble(game);

				// add normal cell to the path, so you make the path more realistic
				Object cell_type_0 = createCellType("NORMAL");
				Object cell_0 = createCell(cell_type_0);

				// Create a cell
				Object entry_cell = createCell(cell_type_1);
				Object normal_cell = createCell(cell_type_2);
				Method setMarble = Class.forName(cellPath).getDeclaredMethod(
						"setMarble", Class.forName(marblePath));
				setMarble.invoke(entry_cell, marble_for_entry_cell);
				setMarble.invoke(normal_cell, marble_for_normal_cell);

				ArrayList<Object> path = new ArrayList<>();
				path.add(cell_0);
				path.add(entry_cell);
				path.add(normal_cell);
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
		public void testValidatePathIsCalledInMethodMoveByInBoardClass() {

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

				// Get the SafeZone of the active player

				ArrayList<Object> safeZones_value = getSafeZone(board_object);

				Method getColorMethod_SZ = Class.forName(safeZonePath)
						.getDeclaredMethod("getColour");
				Method getCellsMehod = Class.forName(safeZonePath)
						.getDeclaredMethod("getCells");
				ArrayList<?> safeZone = null;
				for (int i = 0; i < 4; i++) {

					Object sf_color = getColorMethod_SZ.invoke(safeZones_value
							.get(i));
					if (sf_color == activePlayerColour) {
						safeZone = (ArrayList<?>) getCellsMehod
								.invoke(safeZones_value.get(i));
						break;
					}
				}

				// Add the Marble to the SafeZone
				Object safeZoneCell = safeZone.get(0);
				setMarbleMethod.invoke(safeZoneCell, marble_2);

				int basePosition = getBasePosition(board_object, activePlayerColour);
				int entryPosition = (basePosition - 2 + 100) % 100;
				Object entryCell = track.get(entryPosition);
				setMarbleMethod.invoke(entryCell, marble);

				// Invoke MoveByMethod
				Method moveByMethod = Class.forName(boardPath).getDeclaredMethod(
						"moveBy", Class.forName(marblePath), int.class,
						boolean.class);
				moveByMethod.invoke(board_object, marble, 2, false);
				fail("moveBy method in class Board must call method ValidatePath");
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


	
	
	

	@Test(timeout=1000)
	public void testregainMarblePlayer(){
		Object player= createPlayer("RED");

		Object marble= createMarble("RED");


		try {
			Field marbelsField= Class.forName(playerPath).getDeclaredField("marbles");
			marbelsField.setAccessible(true);

			Field selectedMarblesField= Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			ArrayList<Object> marbles= (ArrayList<Object>) marbelsField.get(player);
			ArrayList<Object> selectedMarbles= (ArrayList<Object>) selectedMarblesField.get(player);
			int sizeMarbles=marbles.size();
			int sizeSelectedMarbles=selectedMarbles.size();

			Method regainMarbleMethod= Class.forName(playerPath).
					getDeclaredMethod("regainMarble", Class.forName(marblePath));
			regainMarbleMethod.invoke(player, marble);
			ArrayList<Object> marblesAfter= (ArrayList<Object>) marbelsField.get(player);
			ArrayList<Object> selectedMarblesAfter= (ArrayList<Object>) selectedMarblesField.get(player);

			assertTrue("Regain Marble should add the given marble to the player's collection of marbles",
					marblesAfter.contains(marble));
			assertTrue("Regain Marble should add the given marble to the player's collection of marbles,"
					+ " without changing the existing marbles in the collection",
					marblesAfter.size()==sizeMarbles+1);
			assertTrue("Regain Marble should NOT add the given marble to the player's collection of "
					+ "SELECTED marbles", !selectedMarblesAfter.contains(marble));
			assertTrue("Regain Marble should NOT add the given marble to the player's collection of "
					+ "SELECTED marbles", sizeSelectedMarbles==selectedMarblesAfter.size());




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when creating calling regainMarble in Player");
		}


	}
	@Test(timeout=1000)
	public void testSendHomeGameMarbleAddedToPlayer(){

		Object game=createGame();

		Object marble=createMarble("RED");

		try {
			Field marbelsField= Class.forName(playerPath).getDeclaredField("marbles");
			marbelsField.setAccessible(true);
			Object player=null;
			try {
				Field players= Class.forName(gamePath).getDeclaredField("players");
				players.setAccessible(true);
				Field colourFiled= Class.forName(playerPath).getDeclaredField("colour");
				colourFiled.setAccessible(true);
				ArrayList<Object> playersList= (ArrayList<Object>) players.get(game);
				for (Object playerObj: playersList) {
					if(colourFiled.get(playerObj).toString().equals("RED"))
					{
						player=playerObj;
					}
				}
				//				player= players
			} catch (IllegalAccessException
					| IllegalArgumentException | SecurityException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				fail(e.getCause()+" occured when getting the player from game");
			}
			Field selectedMarblesField= Class.forName(playerPath).getDeclaredField("selectedMarbles");
			selectedMarblesField.setAccessible(true);
			ArrayList<Object> marbles= (ArrayList<Object>) marbelsField.get(player);
			ArrayList<Object> selectedMarbles= (ArrayList<Object>) selectedMarblesField.get(player);
			int sizeMarbles=marbles.size();
			int sizeSelectedMarbles=selectedMarbles.size();

			Method sendHomeMethod= Class.forName(gamePath).
					getDeclaredMethod("sendHome", Class.forName(marblePath));
			sendHomeMethod.invoke(game, marble);
			ArrayList<Object> marblesAfter= (ArrayList<Object>) marbelsField.get(player);
			ArrayList<Object> selectedMarblesAfter= (ArrayList<Object>) selectedMarblesField.get(player);

			assertTrue("sendHome should regain the given marble to the player's collection of marbles",
					marblesAfter.contains(marble));
			assertTrue("sendHome should regain the given marble to the player's collection of marbles,"
					+ " without changing the existing marbles in the collection",
					marblesAfter.size()==sizeMarbles+1);
			assertTrue("sendHome should NOT add the given marble to the player's collection of "
					+ "SELECTED marbles", !selectedMarblesAfter.contains(marble));
			assertTrue("sendHome should NOT add the given marble to the player's collection of "
					+ "SELECTED marbles", sizeSelectedMarbles==selectedMarblesAfter.size());




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when creating calling regainMarble in Player");
		}
	}



	@Test(timeout=1000)
	public void testKingActSendToBase(){
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


			Method actMethod= Class.forName(kingPath).getDeclaredMethod("act",ArrayList.class);
			actMethod.setAccessible(true);
			try{
				actMethod.invoke(kingCard, new ArrayList<>());
				trackFiled.setAccessible(true);
				Object cellUpdate=((ArrayList)trackFiled.get(board)).get(position);
				assertTrue("Check the game description for how the king card acts; fieldMarble Should set the base cell's marble and not create a new Cell", cell.equals(cellUpdate));

				marbleField= Class.forName(cellPath).getDeclaredField("marble");
				marbleField.setAccessible(true);
				Object marbleUpdated= marbleField.get(cellUpdate);
				assertTrue("Check the game description for how the king card acts; when the king act is called it should set the base cell's marble of the current player to the correct marble", marbleObject.equals(marbleUpdated));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when calling act on a king card with empty marbles input");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | NoSuchFieldException 
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}
	}
	@Test(timeout=1000)
	public void testAceActEmptyExceptionThrown(){

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
			Object AceCard= createAceCard(game, board);

			Method actMethod;
			try {
				actMethod = Class.forName(acePath).getDeclaredMethod("act", ArrayList.class);
				actMethod.setAccessible(true);

				try {

					actMethod.invoke(AceCard, new ArrayList<>());
					fail("Ace's act should throw a CannotFieldException if the player's marble collection and selected marbles are empty.");
				}catch (Exception e) {

					if(!e.getCause().toString().contains("CannotFieldException"))
						fail("Ace's act should throw only a CannotFieldException if the player's marble collection and selected marbles are empty.");

				}

			} catch (NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				fail("Cannot access and call method Act on a Ace's Card");
			}





		} catch (NoSuchFieldException| ClassNotFoundException |IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing attributes, make sure the namings are correct and no typos");
		}



	}



	@Test(timeout=1000)
	public void testFieldMarbleRemoveMarble(){
		try {
			Object game = createGame();
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
			ArrayList<Object> saveMarbles= new ArrayList<>(((ArrayList<Object>)marblesField.get(currentPlayer)));
			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colourPath), playerColour);


			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board= boardField.get(game);


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


			Method fieldMarbleMethod= Class.forName(gamePath).getDeclaredMethod("fieldMarble",null);
			fieldMarbleMethod.setAccessible(true);
			try{
				fieldMarbleMethod.invoke(game, null);
				marblesList= (ArrayList<Object>)marblesField.get(currentPlayer);
				assertFalse("fieldMarble should remove the first marble from that player’s Home Zone after sending it to base", marblesList.contains(marbleObject));


				for (int i =1 ; i < saveMarbles.size(); i++) {
					assertTrue("Only the first marble should be removed. No other marbles should be removed.", marblesList.contains(saveMarbles.get(i)));


				}
			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when calling fieldMarble in Game");

			}




		}  catch (NoSuchFieldException| NoSuchMethodException|
				ClassNotFoundException | IllegalAccessException| IllegalArgumentException
				e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing an object attribute/Method, make sure there arent any typos with the attribute names");
		}
	}

	@Test(timeout=1000)
	public void testFieldMarbleSendToBase(){
		try {
			Object game = createGame();
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


			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board= boardField.get(game);

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


			Method fieldMarbleMethod= Class.forName(gamePath).getDeclaredMethod("fieldMarble",null);
			fieldMarbleMethod.setAccessible(true);
			try{
				fieldMarbleMethod.invoke(game, null);
				trackFiled.setAccessible(true);
				Object cellUpdate=((ArrayList)trackFiled.get(board)).get(position);
				assertTrue("fieldMarble Should set the base cell's marble and not create a new Cell", cell.equals(cellUpdate));

				marbleField= Class.forName(cellPath).getDeclaredField("marble");
				marbleField.setAccessible(true);
				Object marbleUpdated= marbleField.get(cellUpdate);
				assertTrue("fieldMarble Should set the base cell's marble to the given marble", marbleObject.equals(marbleUpdated));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when calling fieldMarble in Game");

			}




		}  catch (NoSuchFieldException| ClassNotFoundException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}
		catch(NoSuchMethodException e) {
			fail(e.getCause()+" occurred when accessing and calling fieldMarble in Game");
		}
	}

	@Test(timeout=1000)
	public void testFieldMarbleExceptionThrown(){

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



			Method fieldMarbleMethod= Class.forName(gamePath).getDeclaredMethod("fieldMarble", null);
			fieldMarbleMethod.setAccessible(true);

			try {

				fieldMarbleMethod.invoke(game, null);
				fail("fieldMarble should throw a CannotFieldException if the player's marble collection is empty");
			}catch (Exception e) {

				if(!e.getCause().toString().contains("CannotFieldException"))
					fail("fieldMarble should throw only a CannotFieldException if the player's marble collection is empty.");

			}






		} catch (NoSuchFieldException | ClassNotFoundException |IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" occured when accessing attributes, make sure the namings are correct and no typos");
		}
		catch(NoSuchMethodException e) {
			fail(e.getCause()+" occured when calling fieldMarble for a player of empty marbles collection");

		}



	}

	@Test(timeout=1000)
	public void testGetOneMarblePlayer2(){
		Object player= createPlayer("GREEN");
		try {
			Field marbleField = null;
			try {
				marbleField = Class.forName(playerPath).getDeclaredField("marbles");
			} catch (SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				fail("An error occurred when accessing the marbles attribute in class Player");				
			}
			marbleField.setAccessible(true);
			Field selectedMarblesField=null;
			try {
				selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			} catch (SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				fail("An error occurred when accessing the selected marbles attribute in class Player");				
			}
			selectedMarblesField.setAccessible(true);
			ArrayList<Object> marbleList = null;
			try {
				marbleList = (ArrayList) marbleField.get(player);
				((ArrayList) selectedMarblesField.get(player)).add(createMarble("GREEN"));
				((ArrayList) selectedMarblesField.get(player)).add(createMarble("GREEN"));
				ArrayList<Object> selectedMarbleListBefore= (ArrayList) selectedMarblesField.get(player);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				fail("An error occurred when retreiving the marbles collection from the Player");				

			}



			Object firstMarble= marbleList.get(0);

			Method getOneMarbleMethod = null;
			try {
				getOneMarbleMethod = Class.forName(playerPath).getDeclaredMethod("getOneMarble", null);
				getOneMarbleMethod.setAccessible(true);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object returnedMarble;
			try {
				returnedMarble = getOneMarbleMethod.invoke(player, null);
				marbleList= (ArrayList) marbleField.get(player);
				ArrayList<Object> selectedMarbleList= (ArrayList) selectedMarblesField.get(player);

				assertTrue("getOneMarble in class Player should not remove the first marble from the player's collection",marbleList.contains(returnedMarble));
				assertTrue("getOneMarble in class Player should not affect the player's selected marbles collection",selectedMarbleList.size()==2);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				fail(e.getCause()+" "+e.getMessage()+" occurred when calling getOneMarble in Player class ");
			}




		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing player attributes a new Player");
		}




	}

	@Test(timeout=1000)
	public void testGetOneMarblePlayerEmpty(){
		Object player= createPlayer("GREEN");
		try {
			Field marbleField = null;
			try {
				marbleField = Class.forName(playerPath).getDeclaredField("marbles");
			} catch (SecurityException | ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing the marbles attribute in class Player");
			}
			marbleField.setAccessible(true);
			Field selectedMarblesField = null;
			try {
				selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
			} catch (SecurityException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing the selectedMarbles attribute in class Player");				}
			selectedMarblesField.setAccessible(true);
			marbleField.set(player, new ArrayList<>());

			((ArrayList) selectedMarblesField.get(player)).add(createMarble("GREEN"));
			((ArrayList) selectedMarblesField.get(player)).add(createMarble("GREEN"));
			ArrayList<Object> selectedMarbleListBefore= (ArrayList) selectedMarblesField.get(player);



			Method getOneMarbleMethod = null;
			try {
				getOneMarbleMethod = Class.forName(playerPath).getDeclaredMethod("getOneMarble", null);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				fail(e.getCause()+" occured when accessing getOneMarble for a player of empty marbles collection");
			}
			getOneMarbleMethod.setAccessible(true);

			Object returnedMarble= getOneMarbleMethod.invoke(player, null);
			ArrayList<Object> selectedMarbleList= (ArrayList) selectedMarblesField.get(player);

			assertTrue("getOneMarble in class Player should either return the first marble in the collection or null if the collection is empty",returnedMarble==null);




		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing player attributes a new Player");
		}
		catch(InvocationTargetException e) {
			fail(e.getCause()+" occured when calling getOneMarble for a player of empty marbles collection");

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when calling getOneMarble for a player of empty marbles collection");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when calling getOneMarble for a player of empty marbles collection");
		}



	}




	@Test(timeout=1000)
	public void testSendToBaseBoardSetMarble(){
		try {
			Object game = createGame();
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			currentPlayerIndexField.set(game, 2);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);
			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();


			Object marbleObject2= createMarble(playerColour);

			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);

			Object board=boardField.get(game);


			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell, null);

			Field trackFiled= Class.forName(boardPath).getDeclaredField("track");
			trackFiled.setAccessible(true);
			((ArrayList)trackFiled.get(board)).set(50, cell);


			Method sendToBaseMethod= Class.forName(boardPath).getDeclaredMethod("sendToBase", Class.forName(marblePath));
			sendToBaseMethod.setAccessible(true);
			try{
				sendToBaseMethod.invoke(board, marbleObject2);
				trackFiled.setAccessible(true);
				Object cellUpdate=((ArrayList)trackFiled.get(board)).get(50);
				assertTrue("SendToBase Should set the base cell's marble and not create a new Cell", cell.equals(cellUpdate));

				marbleField= Class.forName(cellPath).getDeclaredField("marble");
				marbleField.setAccessible(true);
				Object marbleUpdated= marbleField.get(cellUpdate);
				assertTrue("SendToBase Should set the base cell's marble to the given marble", marbleObject2.equals(marbleUpdated));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException 
				| IllegalAccessException | IllegalArgumentException
				e) {
			fail(e.getCause()+" occured when creating a new Game");

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}

	}

	@Test(timeout=1000)
	public void testSendToBaseBoardOccupiedCellSameColour(){
		try {
			Object game = createGame();
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			currentPlayerIndexField.set(game, 1);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);
			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();
			Object marbleObject= createMarble(playerColour);

			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);

			Object board=boardField.get(game);

			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell, marbleObject);

			Field trackFiled= Class.forName(boardPath).getDeclaredField("track");
			trackFiled.setAccessible(true);
			((ArrayList)trackFiled.get(board)).set(25, cell);


			Method sendToBaseMethod= Class.forName(boardPath).getDeclaredMethod("sendToBase", Class.forName(marblePath));
			sendToBaseMethod.setAccessible(true);
			try{
				sendToBaseMethod.invoke(board, marbleObject);
				fail ("CannotFieldException Should be thrown when the marble of the same colour as the player is already in the Base Cell");

			}
			catch(Exception e){
				if(!e.getCause().toString().contains("CannotFieldException"))
					fail ("ONLY CannotFieldException Should be thrown when the marble of the same colour as the player is already in the Base Cell");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException 
				| IllegalAccessException | IllegalArgumentException
				e) {
			fail(e.getCause()+" occured when creating a new Game");

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}
	}
	@Test(timeout=1000)
	public void testSendToBaseBoardOccupiedCell(){
		try {
			Object game = createGame();
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);
			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String playerColour= colourField.get(currentPlayer).toString();



			ArrayList<String> colourOrder=new ArrayList<String>();
			colourOrder.add("GREEN");
			colourOrder.add("RED");
			colourOrder.add("YELLOW");
			colourOrder.add("BLUE");

			colourOrder.remove(playerColour);
			colourOrder.add(0, playerColour);

			ArrayList<Object> colours = new ArrayList<Object>();
			for (int i = 0; i < 4; i++) {

				Object c = Enum.valueOf((Class<Enum>) Class.forName(colourPath),colourOrder.get(i) );
				colours.add(c);
			}

			Object marbleObject2= createMarble(playerColour);
			Object marbleObject= createMarble(colourOrder.get(2));

			Constructor<?> boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object board = boardConstructor.newInstance(colours,game);

			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(cell, marbleObject);

			Field trackFiled= Class.forName(boardPath).getDeclaredField("track");
			trackFiled.setAccessible(true);
			((ArrayList)trackFiled.get(board)).set(0, cell);


			Method sendToBaseMethod= Class.forName(boardPath).getDeclaredMethod("sendToBase", Class.forName(marblePath));
			sendToBaseMethod.setAccessible(true);
			try{
				sendToBaseMethod.invoke(board, marbleObject2);
				trackFiled.setAccessible(true);
				Object cellUpdate=((ArrayList)trackFiled.get(board)).get(0);
				assertTrue("SendToBase Should set the base cell's marble and not create a new Cell", cell.equals(cellUpdate));

				marbleField= Class.forName(cellPath).getDeclaredField("marble");
				marbleField.setAccessible(true);
				Object marbleUpdated= marbleField.get(cellUpdate);
				assertTrue("SendToBase Should set the base cell's marble to the given marble", marbleObject2.equals(marbleUpdated));

			}
			catch(Exception e){
				if(e.getCause().toString().contains("CannotFieldException"))
					fail ("CannotFieldException Should NOT be thrown when the marble in the Base Cell has a different colour than the current active player");
				else
					fail(e.getCause()+" occured when calling sendToBase in Board");


			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail(e.getCause()+" occured when creating a new Game");

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}
	}


	@Test(timeout=1000)
	public void testvalidateFieldingBoardOccupiedCell(){
		try {
			Object game = createGame();
			Field playersField= Class.forName(gamePath).getDeclaredField("players");
			playersField.setAccessible(true);
			ArrayList<Object> playersList= (ArrayList<Object>) playersField.get(game);
			Field currentPlayerIndexField= Class.forName(gamePath).getDeclaredField("currentPlayerIndex");
			currentPlayerIndexField.setAccessible(true);
			int currentPlayerIndex= (int) currentPlayerIndexField.get(game);

			Object currentPlayer= playersList.get(currentPlayerIndex);
			Field colourField= Class.forName(playerPath).getDeclaredField("colour");
			colourField.setAccessible(true);
			String colour= colourField.get(currentPlayer).toString();


			Object cell= CreateCell("BASE");
			Field marbleField= Class.forName(cellPath).getDeclaredField("marble");
			Object marbleObject= createMarble(colour);
			marbleField.setAccessible(true);
			marbleField.set(cell, marbleObject);

			Object board= createBoard(game);
			Method validateFieldingMethod= Class.forName(boardPath).getDeclaredMethod("validateFielding", Class.forName(cellPath));
			validateFieldingMethod.setAccessible(true);
			try{
				validateFieldingMethod.invoke(board, cell);
				fail ("CannotFieldException Should be thrown when the marble of the same colour as the player is already in the Base Cell");

			}
			catch(Exception e){
				if(!e.getCause().toString().contains("CannotFieldException"))
					fail ("ONLY CannotFieldException Should be thrown when the marble of the same colour as the player is already in the Base Cell");

			}




		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException | 
				IllegalArgumentException e) {
			fail(e.getCause()+" occured when Testing validateFielding in Board");

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			fail(e.getCause()+" occured when accessing an object attribute, make sure there arent any typos with the attribute names");
		}

	}


	@Test(timeout=1000)
	public void testDiscardCardColourInGame() {
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
			ArrayList<Object> handBefore= (ArrayList<Object>) handField.get(playersList.get(randIndex));
			int sizeBefore= handBefore.size();

			try {

				Method discardCardMethod= Class.forName(gamePath).getDeclaredMethod("discardCard",Class.forName(colourPath));
				discardCardMethod.setAccessible(true);
				discardCardMethod.invoke(game, colourOrder.get(randIndex));
				playersList=(ArrayList<Object>) playersField.get(game);
				ArrayList<Object> handAfter= (ArrayList<Object>) handField.get(playersList.get(randIndex));
				assertTrue("DiscardCard should remove one random card from the hand of the player of the given colour.", handAfter.size()==(sizeBefore-1));

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing discardCard method in Game. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				fail(e.getCause()+" occurred when calling discardCard method on a Game object, and it should NOT");
			}


		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}

	@Test(timeout=2000)
	public void testDiscardCardColourInGameRandom() {
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
			ArrayList<Object> handBefore= new ArrayList<>();
			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);

			for (int i = 0; i < 6; i++) {
				handBefore.add(createAceCard(game, boardField.get(game)));
			}

			HashSet<ArrayList<Object>> randomSet= new HashSet<>();

			try {
				for (int i = 0; i < 30; i++) {
					handField.set(playersList.get(randIndex), handBefore.clone());
					Method discardCardMethod= Class.forName(gamePath).getDeclaredMethod("discardCard",Class.forName(colourPath));
					discardCardMethod.setAccessible(true);
					discardCardMethod.invoke(game, colourOrder.get(randIndex));
					playersList=(ArrayList<Object>) playersField.get(game);
					ArrayList<Object> handAfter= (ArrayList<Object>) handField.get(playersList.get(randIndex));
					randomSet.add(handAfter);
				}

				assertTrue("DiscardCard should remove one random card from the hand of the player of the given colour. Hint: make sure your code is dynamic to the hand size.", randomSet.size()>=5);

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing discardCard method in Game. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				fail(e.getCause()+" occurred when calling discardCard method on a Game object, and it should NOT");
			}


		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}


	@Test(timeout=1000)
	public void testActTenEmptyMarblesEmptyHandException() {
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

			int nextPlayerIndex= (currentIndex+1>=4)? 0:currentIndex+1;

			Field handField= Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			handField.set(playersList.get(nextPlayerIndex),new ArrayList<>() );
			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);

			Object tenCard=createTenCard(game, boardField.get(game));


			try {

				Method actTenMethod= Class.forName(tenPath).getDeclaredMethod("act",ArrayList.class);
				actTenMethod.setAccessible(true);
				actTenMethod.invoke(tenCard, new ArrayList<>());
				fail("CannotDiscardException should be thrown when ten card is acting and the next player has an empty hand!");


			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing act on a Ten card with empty marbles. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				if(!e.getCause().toString().contains("CannotDiscardException"))
					fail("CannotDiscardException should be thrown when ten card is acting and the next player has an empty hand!"+e.getCause()+" occurred when calling act on a Ten card with empty marbles, and it should NOT");

			}




		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}
	@Test(timeout=1000)
	public void testActonTenWithEmptyMarbles() {
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
			int nextPlayerIndex= (currentIndex+1>=4)? 0:currentIndex+1;

			Field handField= Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			ArrayList<Object> handBefore= (ArrayList<Object>) handField.get(playersList.get(nextPlayerIndex));
			int sizeBefore= handBefore.size();
			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);

			Object tenCard=createTenCard(game, boardField.get(game));

			try {

				Method actTenMethod= Class.forName(tenPath).getDeclaredMethod("act",ArrayList.class);
				actTenMethod.setAccessible(true);
				actTenMethod.invoke(tenCard, new ArrayList<>());
				playersList=(ArrayList<Object>) playersField.get(game);
				ArrayList<Object> handAfter= (ArrayList<Object>) handField.get(playersList.get(nextPlayerIndex));
				assertTrue("Act on a Ten card with empty marbles should remove one random card from the hand of the player of the given colour.", handAfter.size()==(sizeBefore-1));


			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing act on a Ten card with empty marbles. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				fail(e.getCause()+" occurred when calling act on a Ten card with empty marbles, and it should NOT");
			}



		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}

	@Test(timeout=2000)
	public void testActTenEmptyRandomTest() {
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
			int nextPlayerIndex= (currentIndex+1>=4)? 0:currentIndex+1;

			Field handField= Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			ArrayList<Object> handBefore= new ArrayList<>();
			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);

			for (int i = 0; i < 6; i++) {
				handBefore.add(createAceCard(game, boardField.get(game)));
			}

			HashSet<ArrayList<Object>> randomSet= new HashSet<>();

			Object tenCard=createTenCard(game, boardField.get(game));
			try {
				for (int i = 0; i < 30; i++) {
					handField.set(playersList.get(nextPlayerIndex), handBefore.clone());
					Method tenActMethod= Class.forName(tenPath).getDeclaredMethod("act",ArrayList.class);
					tenActMethod.setAccessible(true);
					tenActMethod.invoke(tenCard, new ArrayList<>());
					playersList=(ArrayList<Object>) playersField.get(game);
					ArrayList<Object> handAfter= (ArrayList<Object>) handField.get(playersList.get(nextPlayerIndex));
					randomSet.add(handAfter);
				}

				assertTrue("Act on a Ten card with empty marbles should remove one random card from the hand of the next player. Hint: make sure your code is dynamic to the hand size.", randomSet.size()>=5);

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing act on a Ten card with empty marbles. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				fail(e.getCause()+" occurred when calling act on a Ten card with empty marbles, and it should NOT");
			}


		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}
	@Test(timeout=1000)
	public void testDiscardCardInGameException() {
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

			Field handField= Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			for (int i = 0; i < playersList.size(); i++) {
				if(i!=currentIndex)
					handField.set(playersList.get(i),new ArrayList<>() );
			}


			try {

				Method discardCardMethod= Class.forName(gamePath).getDeclaredMethod("discardCard",null);
				discardCardMethod.setAccessible(true);
				discardCardMethod.invoke(game, null);
				fail("CannotDiscardException should be thrown when a player with an empty hand is selected randomly to discard a card");


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
	@Test(timeout=2000)
	public void testDiscardCardInGameRandom() {
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

			Field handField= Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			ArrayList<ArrayList<Object>> handsBefore= new ArrayList<>();
			Field boardField= Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			for (int i = 0; i < 4; i++) {
				handsBefore.add(new ArrayList<>());
				for (int j = 0; j < 4; j++) {
					handsBefore.get(i).add(createKingCard(game, boardField.get(game)));
				}


			}

			HashSet<ArrayList<Object>> randomSet1= new HashSet<>();
			HashSet<ArrayList<Object>> randomSet2= new HashSet<>();
			HashSet<ArrayList<Object>> randomSet3= new HashSet<>();
			HashSet<ArrayList<Object>> randomSet4= new HashSet<>();

			try {
				for (int i = 0; i < 16; i++) {
					playersList=(ArrayList<Object>) playersField.get(game);
					for (int j = 0; j < 4; j++) {
						handField.set(playersList.get(j), handsBefore.get(j).clone());
					}

					Method discardCardMethod= Class.forName(gamePath).getDeclaredMethod("discardCard",null);
					discardCardMethod.setAccessible(true);
					discardCardMethod.invoke(game, null);
					playersList=(ArrayList<Object>) playersField.get(game);
					ArrayList<Object> handAfter= (ArrayList<Object>) handField.get(playersList.get(1));
					randomSet1.add((ArrayList<Object>) handField.get(playersList.get(0)));
					randomSet2.add((ArrayList<Object>) handField.get(playersList.get(1)));
					randomSet3.add((ArrayList<Object>) handField.get(playersList.get(2)));
					randomSet4.add((ArrayList<Object>) handField.get(playersList.get(3)));
				}
				int randomizingThreshold;
				switch (currentIndex) {
				case 0:
					assertTrue("The current player's hand should not be affected when calling discardCard in Game.", randomSet1.size()==1);
					randomizingThreshold=randomSet2.size()+randomSet3.size()+randomSet4.size();
					assertTrue("DiscardCard should remove one random card from the hand of the player of the given colour. Hint: make sure your code is dynamic to the hand size.",
							randomizingThreshold>=4);

					break;
				case 1:
					assertTrue("The current player's hand should not be affected when calling discardCard in Game.", randomSet2.size()==1);
					randomizingThreshold=randomSet1.size()+randomSet3.size()+randomSet4.size();
					assertTrue("DiscardCard should remove one random card from the hand of the player of the given colour. Hint: make sure your code is dynamic to the hand size.",
							randomizingThreshold>=4);

					break;
				case 2:
					assertTrue("The current player's hand should not be affected when calling discardCard in Game.", randomSet3.size()==1);
					randomizingThreshold=randomSet1.size()+randomSet2.size()+randomSet4.size();
					assertTrue("DiscardCard should remove one random card from the hand of the player of the given colour. Hint: make sure your code is dynamic to the hand size.",
							randomizingThreshold>=4);
					break;
				case 3:

					assertTrue("The current player's hand should not be affected when calling discardCard in Game.", randomSet4.size()==1);
					randomizingThreshold=randomSet1.size()+randomSet3.size()+randomSet2.size();
					assertTrue("DiscardCard should remove one random card from the hand of the player of the given colour. Hint: make sure your code is dynamic to the hand size.",
							randomizingThreshold>=4);
					break;
				}

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				fail("An error occured when accessing discardCard method in Game. Make sure it has the correct declaration, and no typos.");

			} catch (InvocationTargetException e) {
				fail(e.getCause()+" occurred when calling discardCard method on a Game object, and it should NOT");
			}


		} catch (NoSuchFieldException | SecurityException |
				ClassNotFoundException | IllegalArgumentException |
				IllegalAccessException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage()+" Error occurred when acessing a Game's attribute, make sure there are no typos.");
		}

	}


	@Test(timeout=1000)
	public void testGetNextPlayerColourInGame2() {
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
			currentPlayerIndexField.set(game, 3);


			try {

				Method getNextPlayerColourMethod= Class.forName(gamePath).getDeclaredMethod("getNextPlayerColour",null);
				getNextPlayerColourMethod.setAccessible(true);
				Object returnedColour=getNextPlayerColourMethod.invoke(game, null);
				assertEquals("getNextPlayerColour should retrun the colour of the next player in the list, according to the correct turn.", colourOrder.get(0), returnedColour);
				assertEquals("The currentPlayerIndex should not be updated after calling getNextPlayerColour", 3,(int) (currentPlayerIndexField.get(game)));

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
	public void testSendHomeInGameDeclaration() {

		Method m;
		try {
			m = Class.forName(gamePath).getDeclaredMethod("sendHome",Class.forName(marblePath));
			assertTrue("sendHome expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("Send Home should be void. ",void.class, returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing sendHome method declaration, make sure there are no typos");
		}
	}

	@Test(timeout=1000)
	public void testGetNextPlayerColourInGameDeclaration() {

		Method m;
		try {
			m = Class.forName(gamePath).getDeclaredMethod("getNextPlayerColour",null);
			assertTrue("getNextPlayerColour expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("getNextPlayerColour should return Colour. ",Class.forName(colourPath), returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing getNextPlayerColour method declaration, make sure there are no typos");
		}
	}
	@Test(timeout=1000)
	public void testDiscardCardColourInGameDeclaration() {

		Method m;
		try {
			m = Class.forName(gamePath).getDeclaredMethod("discardCard",Class.forName(colourPath));
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
	public void testFieldMarbleInGameDeclaration() {

		Method m;
		try {
			m = Class.forName(gamePath).getDeclaredMethod("fieldMarble",null);
			assertTrue("fieldMarble expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("fieldMarble should be void. ",void.class, returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing fieldMarble method declaration, make sure there are no typos");
		}
	}

	@Test(timeout=1000)
	public void testGetOneMarbleInPlayerDeclaration() {

		Method m;
		try {
			m = Class.forName(playerPath).getDeclaredMethod("getOneMarble",null);
			assertTrue("getOneMarble expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("getOneMarble should return a Marble. ",Class.forName(marblePath), returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing getOneMarble method declaration, make sure there are no typos");
		}
	}

	@Test(timeout=1000)
	public void testsendToBaseInBoardDeclaration() {

		Method m;
		try {
			m = Class.forName(boardPath).getDeclaredMethod("sendToBase",Class.forName(marblePath));
			assertTrue("sendToBase expected to be public",Modifier.isPublic(m.getModifiers()));
			// Check the return type
			Class<?> returnType = m.getReturnType();

			assertEquals("sendToBase should be void. ",void.class, returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail("error occured while testing sendToBase method declaration, make sure there are no typos");
		}
	}

	@Test(timeout=1000)
	public void testValidateFieldingInBoardDeclaration() {

		Method m;
		try {
			m = Class.forName(boardPath).getDeclaredMethod("validateFielding",Class.forName(cellPath));
			assertTrue("validateFielding expected to be private",Modifier.isPrivate(m.getModifiers()));
			// Check the return type
			m.setAccessible(true);
			Class<?> returnType = m.getReturnType();

			assertEquals("validateFielding should be void. ",void.class, returnType);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block

			fail(e.getMessage()+" error occured while testing validateFielding method declaration, make sure there are no typos");
		}
	}


	@Test(timeout = 1000)
	public void testGetSafeZoneIsPrivateInClassBoard()  {
		Class<?> board_class = null;
		Class<?> colour_class = null;

		try {
			board_class = Class.forName(boardPath);
		}

		catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}


		try {
			colour_class = Class.forName(colourPath);
		}

		catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}


		Method m = null;
		try {
			m = board_class.getDeclaredMethod("getSafeZone",new Class[] { colour_class});
		} catch (NoSuchMethodException | SecurityException e) {

			fail(e.getClass() + " occurred when searching  getSafeZone method, Error Message: " + 
					e.getMessage());
		}

		int modifiers = m.getModifiers();
		assertTrue("getSafeZone method should be private", Modifier.isPrivate(modifiers));

	}


	@Test(timeout = 1000)
	public void testGetSafeZoneMethodRightColourInBoardClass() {


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
	public void testGetSafeZoneMethodZoneNotFoundInBoardClass() {


		// Set test environment
		Object game = constructGame();
		Object board = constructBoard(game); // Create board instance


		try {


			Object colour = null;

			// Get the private getSafeZone method
			Method getSafeZoneMethod = board.getClass().getDeclaredMethod("getSafeZone", Class.forName(colourPath));
			getSafeZoneMethod.setAccessible(true); // Required for private methods

			// Invoke the method
			Object result = getSafeZoneMethod.invoke(board, colour);

			// Ensure it has exactly 4 elements
			assertTrue("getSafeZone should return null if safezone not found", result== null);



		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException  e) {
			e.printStackTrace();
			fail(e.getCause() + " occurred while testing getSafeZone method");
		}
	}


	@Test(timeout=1000)
	public void testGetPositionInPathMethodExistenceInBoardClass() {
		Class<?> board_class = null;

		try {
			board_class = Class.forName(boardPath);
		}

		catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}


		try {
			testMethodExistence(board_class, "getPositionInPath", int.class,
					new Class[] { ArrayList.class,Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {

			fail("Class not found: " + e.getMessage());
		}
	}


	@Test(timeout = 1000)
	public void testGetPositionInPathMethodLogicInBoardClass()  {


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


		// Assign created marble to a random cell marble value in the track  
		int rand = new Random().nextInt(100);
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

	@Test(timeout = 1000)
	public void testGetPositionInPathUpperBoundryBoardClass()  {


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


		// Assign created marble to 99  
		int rand = 99;
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


	@Test(timeout = 1000)
	public void testGetPositionInPathMarbleNotOnPathBoardClass()  {


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




		try {
			//Get Method getPositionInPath
			Method getPositionInPathMethod = board.getClass().getDeclaredMethod("getPositionInPath", new Class[]  { ArrayList.class,marbleClass} );
			getPositionInPathMethod.setAccessible(true); // Required for private methods

			// Invoke the method 
			int result = (int)getPositionInPathMethod.invoke(board, track,marble);



			// Final assertion
			assertTrue("Method should return -1 when marble is not on path, But was: " + result,
					result == -1);


		} catch (  IllegalAccessException | NoSuchMethodException |
				InvocationTargetException  e) {
			fail( e.getCause() + "Error occurred while testing getPositionInPath method.");
		}
	}


	@Test(timeout = 1000)
	public void testGetBasePositionMethodLogicInBoardClass() {


		// Set Test Environment
		Object game = constructGame(); // Create game instance
		ArrayList<Object> colours = new ArrayList<Object>();


		Object board = null;
		Constructor<?> boardConstructor = null;
		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
			board = boardConstructor.newInstance(colours,game);
		} catch (InstantiationException |NoSuchMethodException | SecurityException |  ClassNotFoundException |
				IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail(e.getClass() + " occurred when creating object of type Board, Error message: " +
					e.getMessage());

		}


		try {

			// Select a random Colour enum value
			int rand = new Random().nextInt(colours.size());
			Object colour = colours.get(rand);

			// Get the private getBasePosition  method
			Method getBasePositionMethod = board.getClass().getDeclaredMethod("getBasePosition", Class.forName(colourPath));
			getBasePositionMethod.setAccessible(true); // Required for private methods

			// Invoke the method
			int result = (int)getBasePositionMethod.invoke(board, colour);

			// Ensure the result is the corect index
			assertTrue("getBasePosition should return the correct base index "
					+ "Expected: " + rand*25 +  " but was: " + result + " for colour " + colour
					, result  == rand*25);



		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException  e) {
			fail( e.getCause() + "Error occurred while testing getBasePosition method.");

		}
	}

	@Test(timeout = 1000)
	public void testGetBasePositionMethodNullColourInBoardClass() {


		// Set Test Environment
		Object game = constructGame(); // Create game instance
		ArrayList<Object> colours = new ArrayList<Object>();


		Object board = null;
		Constructor<?> boardConstructor = null;
		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			Object colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			Object colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");

			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
			board = boardConstructor.newInstance(colours,game);
		} catch (InstantiationException |NoSuchMethodException | SecurityException |  ClassNotFoundException |
				IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail(e.getClass() + " occurred when creating object of type Board, Error message: " +
					e.getMessage());

		}


		try {


			// Get the private getBasePosition  method
			Method getBasePositionMethod = board.getClass().getDeclaredMethod("getBasePosition", Class.forName(colourPath));
			getBasePositionMethod.setAccessible(true); // Required for private methods

			// Invoke the method
			Object colour = null;
			int result = (int)getBasePositionMethod.invoke(board, colour);

			// Ensure the result is the corect index
			assertTrue("getBasePosition should return the correct base index "
					+ "Expected: -1 but was: " + result + " for invalid colour " 
					, result  == -1);



		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException  e) {
			fail( e.getCause() + "Error occurred while testing getBasePosition method.");

		}
	}




	// Method Private

	@Test(timeout = 1000)
	public void testGetEntryPositionIsPrivateInClassBoard() {
		Class<?> board_class = null;
		Method m = null;
		try {
			board_class = Class.forName(boardPath);
		} catch ( SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred when creating object of type Board ");

		}
		try {
			m = board_class.getDeclaredMethod("getEntryPosition",new Class[] { Class.forName(colourPath)});
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred when searching  getEntryPosition method, Error Message: " + 
					e.getMessage());
		}

		int modifiers = m.getModifiers();
		assertTrue("getEntryPosition method should be private",Modifier.isPrivate(modifiers));
	}

	@Test(timeout = 1000)

	public void testGetEntryPositionMethodLogicInBoardClass() {


		// Set test environment
		Object game = constructGame(); // Create game instance
		Object colour1;
		Object colour2;
		Object colour3;
		Object colour4;

		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred when creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred when creating object of type Board " + " " + "Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred when creating object of type Board " + " " + "Error Message: " + 
					e.getMessage());
		}






		// Get the private getEntryPosition method
		Method getEntryPositionMethod = null;
		try {
			getEntryPositionMethod = board.getClass().getDeclaredMethod("getEntryPosition", Class.forName(colourPath));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred while searching method getEntryPosition  " + " " + "Error Message: " + 
					e.getMessage());
		}

		getEntryPositionMethod.setAccessible(true); // Required for private methods

		// Invoke the method
		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		int result4 = 0;
		int i1 = 0;
		int i2 = 1;
		int i3 = 2;
		int i4 = 3;
		try {
			result1 = (int)getEntryPositionMethod.invoke(board, colours.get(i1));
			result2 = (int)getEntryPositionMethod.invoke(board,  colours.get(i2));
			result3 = (int)getEntryPositionMethod.invoke(board,  colours.get(i3));
			result4 = (int)getEntryPositionMethod.invoke(board,  colours.get(i4));



		} catch (IllegalAccessException | InvocationTargetException e) {
			fail(e.getClass() + " occurred when while searching method getEntryPosition,  " + " " + "Error Message: " + 
					e.getMessage());
		}

		// Ensure the result is the corect index
		assertTrue("getEntryPosition should return the correct base index "
				+ "Expected: " + (i1*25 -2 + 100)%100 +  " but was: " + result1 + " for colour " + colours.get(i1)
				, result1  == (i1*25 -2 + 100)%100);


		// Ensure the result is the corect index
		assertTrue("getEntryPosition should return the correct base index "
				+ "Expected: " + (i2*25 -2 + 100)%100 +  " but was: " + result2 + " for colour " + colours.get(i2)
				, result2  == (i2*25 -2 + 100)%100);


		// Ensure the result is the corect index
		assertTrue("getEntryPosition should return the correct base index "
				+ "Expected: " + (i3*25 -2 + 100)%100 +  " but was: " + result3 + " for colour " + colours.get(i3)
				, result3  == (i3*25 -2 + 100)%100);


		// Ensure the result is the corect index
		assertTrue("getEntryPosition should return the correct base index "
				+ "Expected: " + (i4*25 -2 + 100)%100 +  " but was: " + result4 + " for colour " + colours.get(i4)
				, result4  == (i4*25 -2 + 100)%100);






	}


	@Test(timeout=1000)

	public void testGetEntryPositionMethodNullColourInBoardClass() {


		// Set test environment
		Object game = constructGame(); // Create game instance
		Object colour1;
		Object colour2;
		Object colour3;
		Object colour4;

		ArrayList<Object> colours = new ArrayList<Object>();
		try {
			colour1 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED");
			colour2 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE");
			colour3 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW");
			colour4 = Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour1);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred when creating object of type Colour");

		}

		Object board = null ;
		Constructor<?> boardConstructor;

		try {
			boardConstructor = Class.forName(boardPath).getConstructor(ArrayList.class,Class.forName(gameManagerPath));
			board = boardConstructor.newInstance(colours,game);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException  e) {
			fail(e.getClass() + " occurred when creating object of type Board " + " " + "Error Message: " + 
					e.getMessage());
		}	
		catch (InvocationTargetException e) {
			fail(e.getCause() + " occurred when creating object of type Board " + " " + "Error Message: " + 
					e.getMessage());
		}






		// Get the private getEntryPosition method
		Method getEntryPositionMethod = null;
		try {
			getEntryPositionMethod = board.getClass().getDeclaredMethod("getEntryPosition", Class.forName(colourPath));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred while searching method getEntryPosition  " + " " + "Error Message: " + 
					e.getMessage());
		}

		getEntryPositionMethod.setAccessible(true); // Required for private methods

		// Invoke the method
		int result1 = 0;
		Object colour = null;
		try {
			result1 = (int)getEntryPositionMethod.invoke(board, colour);
			// Ensure the result is the corect index
			assertTrue("getEntryPosition should return the correct base index "
					+ " -1, but was: " + result1 + " for invalid colour " 
					, result1  == -1); 




		} catch (IllegalAccessException | InvocationTargetException e) {
			fail(e.getClass() + " occurred when while searching method getEntryPosition,  " + " " + "Error Message: " + 
					e.getMessage());
		}



	}


	@Test(timeout=1000)
	public void testValidateStepsMethodExistenceInBoardClass() {
		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		} catch ( SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred when creating object of type Board ");

		}
		try {
			testMethodExistence(Class.forName(boardPath), "validateSteps", ArrayList.class,
					new Class[] {  Class.forName(marblePath),int.class });
		} 
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred when creating object of type Board ");		}
	}

	// Method Private

	@Test(timeout = 1000)
	public void testValidateStepsIsPrivateInClassBoard() {
		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		} catch ( SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred when creating object of type Board ");

		}

		try {
			Method m = board_class.getDeclaredMethod("validateSteps",new Class[] {  Class.forName(marblePath),int.class});
			int modifiers = m.getModifiers();
			assertTrue("validateSteps method should be private",Modifier.isPrivate(modifiers));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail(e.getClass() + " occurred when while searching method validateSteps, Error Message: " + 
					e.getMessage());
		}
	}






	// marble on entry - 2 and steps 6


	@Test(timeout = 1000)

	public void testValidateStepsMethoReturnsOnTrackInBoardClass() {
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
					marbleField.set(track.get(entryPosition - 2), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 
			// Invoke the method
			int steps = 6;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			// Ensure the result is the correct cells size
			assertTrue("validateSteps should return an arraylist of size  "
					+ "Expected:  "+ (steps+1) + " but was: " + result.size()+ " , when distance to entry is 2 and steps are 6" ,result.size()== steps+1);

		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}





		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method, distance to entry is 2 and steps are 6,  Error Message: " + 
					e.getMessage());

		}
		}


	}




	// case marble on 1 and moving backwards to 97
	@Test(timeout = 1000)

	public void testValidateStepsMethoBackwardsOnTracknTrackInBoardClass() {
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


		Object cell= null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board);


			//Set marble
			Field marbleField = track.get(2).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(1), marble);

			cell = track.get(97);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 
			// Invoke the method
			int steps = -4;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			// Ensure the result is the correct cells size
			assertTrue("marble should wraps around the end of track when moving backwards near start of track"
					,cell== result.get(result.size()-1));

		} catch (NoSuchMethodException | SecurityException | InvocationTargetException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}
	}


	// Case Marble is on track entering Safe zone. Position is its entry position - 2 and steps are 6 (Boundry Case)

	@Test(timeout = 1000)
	public void testValidateStepsMethodOnTrackEnterSafeZoneInBoardClass() {

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
					marbleField.set(track.get(entryPosition - 2), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



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


			// Get result cellType 
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int firstIndex = 3;
			Object cellType =  cellTypeField.get(result.get(firstIndex));




			assertTrue("Marble should steps into safezone from track when it is behind 2 cells from entry and apply 6 steps, track cells should be added first before safe zone cells "   , cellType.equals(returnEnumValue(cellTypePath,"SAFE")));




		} catch (NoSuchMethodException | NoSuchFieldException| SecurityException
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

	// player marble exceeds entry 








	// Case Marble is on track entering Safe zone. Position is its entry position  and steps are 1

	@Test(timeout = 1000)
	public void testValidateStepsMethodOnTrackOneStepIntoSafeZoneInBoardClass() {

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
					marbleField.set(track.get(entryPosition), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 
			// Invoke the method
			int steps = 1;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			// Ensure the result is the correct cells size
			assertTrue("validateSteps should return an arraylist of size  "
					+ "Expected:  "+ (steps+1) + " but was: " + result.size() ,result.size()== steps+1);


			// Get result cellType 
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int firstIndex = 1;
			Object cellType =  cellTypeField.get(result.get(firstIndex));




			assertTrue("Marble should steps into safezone from track when it is on entry and apply 1 steps, track cells should be added first before safe zone cells "   , cellType.equals(returnEnumValue(cellTypePath,"SAFE")));




		} catch (NoSuchMethodException | NoSuchFieldException| SecurityException
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


	// Case Marble is inside Safe zone. Position is 0 and steps are 3 (Boundry Case) 0 1 2 3 

	// Case Marble is inside Safe zone. Position is 0 and steps are 3 (Boundry Case) 0 1 2 3 



	// Case Marble is inside Safe zone. Position is 0 and steps are 2  ( Case) 0 1 2 



	// Case Marble is inside Safe zone. Position is 0 and steps are 1  ( Case) 0 1 

	@Test(timeout = 1000)
	public void testValidateStepsMethodReturn2SafeZonesCellsInBoardClass() {

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



		int steps = 1;


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
			fail(e.getCause() + " occurred  while testing validateSteps method moving "+ steps + " from safezone cell index 0, should return an arraylist of size "+ (steps+1) +  " ,Error Message: " + 
					e.getMessage());

		}
		}





	}

	// Case Marble is inside Safe zone. Position is 0 and steps are 1  ( Case) 0 1 




	// Case moving other player marble. marble is near other player safezone  

	@Test(timeout = 1000)
	public void testValidateStepsMethodOtherPlayerMarbleBoardClass() {
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


		// Create marble of current other colour 
		Class<?> marbleClass;
		Object marble = null; 
		Object otherColour = null;
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));

			for (Object col: colours){
				if (!col.equals(colour)) {
					otherColour = col;	
					break;}
			}
			marble = marbleConstructor.newInstance(otherColour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}


		int entryPosition = 0;
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


			for(int i=0; i<safeZones.size();i++)
				if (otherColour == colourField.get(safeZones.get(i)) )
					entryPosition = (i*25 - 2 + 100) % 100; ;
					//Set marble
					Field marbleField = track.get(entryPosition - 2).getClass().getDeclaredField("marble");
					marbleField.setAccessible(true);
					marbleField.set(track.get(entryPosition - 2), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



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

			// Get Safe Zone
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int lastIndex = result.size() - 1;
			Object cellType =  cellTypeField.get(result.get(lastIndex));




			assertTrue("Player marble should not enter safezone when moved by another player "   , !cellType.equals(returnEnumValue(cellTypePath,"SAFE")));





		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());

		}
		} catch (NoSuchFieldException e) {
			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}




	}

	// Case moving other player marble. marble is near current  player safezone  

	@Test(timeout = 1000)
	public void testValidateStepsMethodMovingOtherPlayerMarbleBoardClass() {
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


		// Create marble of current other colour 
		Class<?> marbleClass;
		Object marble = null; 
		Object otherColour = null;
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));

			for (Object col: colours){
				if (!col.equals(colour)) {
					otherColour = col;	
					break;}
			}
			marble = marbleConstructor.newInstance(otherColour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}


		int entryPosition = 0;
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


			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					entryPosition = (i*25 - 2 + 100) % 100; ;
					//Set marble
					Field marbleField = track.get(entryPosition - 2).getClass().getDeclaredField("marble");
					marbleField.setAccessible(true);
					marbleField.set(track.get(entryPosition - 2), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



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

			// Get Safe Zone
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int lastIndex = result.size() - 1;
			Object cellType =  cellTypeField.get(result.get(lastIndex));




			assertTrue("Player marble should not enter safezone when moved by another player "   , !cellType.equals(returnEnumValue(cellTypePath,"SAFE")));








		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());

		}
		} catch (NoSuchFieldException e) {
			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}




	}


	// Case moving other player marble. marble is near current  player safezone  

	@Test(timeout = 1000)
	public void testValidateStepsMethodOtherPlayerMarbleReturnBoardClass() {
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


		// Create marble of current other colour 
		Class<?> marbleClass;
		Object marble = null; 
		Object otherColour = null;
		try {
			marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));

			for (Object col: colours){
				if (!col.equals(colour)) {
					otherColour = col;	
					break;}
			}
			marble = marbleConstructor.newInstance(otherColour);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail(e.getClass() + " occurred  while creating object of type Marble, Error Message: " + 
					e.getMessage());
		}


		int entryPosition = 0;
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


			for(int i=0; i<safeZones.size();i++)
				if (colour == colourField.get(safeZones.get(i)) )
					entryPosition = (i*25 - 2 + 100) % 100; ;
					//Set marble
					Field marbleField = track.get(entryPosition - 2).getClass().getDeclaredField("marble");
					marbleField.setAccessible(true);
					marbleField.set(track.get(entryPosition - 2), marble);

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

			// Get Safe Zone
			Field cellTypeField= Class.forName(cellPath).getDeclaredField("cellType");
			cellTypeField.setAccessible(true);
			int lastIndex = result.size() - 1;
			Object cellType =  cellTypeField.get(result.get(lastIndex));




			assertTrue("Player marble should not enter safezone when moved by another player "   , !cellType.equals(returnEnumValue(cellTypePath,"SAFE")));





		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch(InvocationTargetException e){{
			fail(e.getCause() + " occurred  while testing validateSteps method when moving other player Marble, Error Message: " + 
					e.getMessage());

		}
		} catch (NoSuchFieldException e) {
			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}




	}


	@Test(timeout = 1000)

	public void testValidateStepsMethodTooHighRankInSafeZoneInBoardClass() {
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
			int steps = 4;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);
			fail("Expected IllegalMovementException was not thrown");

		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}





		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown " + e.getCause() +  "occurred when validateSteps was invoked",
					IllegalMovementException.isInstance(thrownException));
			assertTrue("Expected IllegalMovementException should have a message",
					thrownException.getMessage()!= null);
		}


	}



	// Test too high Rank on track
	@Test(timeout = 1000)

	public void testValidateStepsMethodTooHighRankOnTrackInBoardClass() {
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
					marbleField.set(track.get(entryPosition - 2), marble);

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

			fail("Expected IllegalMovementException was not thrown when using too high rank card on track");


		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}







		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown",
					IllegalMovementException.isInstance(thrownException));
			assertTrue("Expected IllegalMovementException should have a message",
					thrownException.getMessage()!= null);
		}


	}



	// Test too high Rank on track marble is on entry and steps are 5
	@Test(timeout = 1000)

	public void testValidateStepsMethodTooHighRankOnEntryInBoardClass() {
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
					marbleField.set(track.get(entryPosition), marble);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		try {
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 
			// Invoke the method
			int steps = 5;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			fail("Expected IllegalMovementException was not thrown when using too high rank card on track, marble is on entry and steps are 5");


		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}





		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown when steps are 5 and distance to entry is 0",
					IllegalMovementException.isInstance(thrownException));
		}


	}



	// Negative steps inside safeZone




	// Test marble is neither on track nor safezone
	// Test marble is neither on track nor safezone
	@Test(timeout = 1000)

	public void testValidateStepsMethodMarbleNotInSafeZoneNotOnTrackExceptionBoardClass() {
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

		int index = 1000; // just dymmy
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
			// Get the private validateSteps Method 
			Method validateStepsMethod = board.getClass().getDeclaredMethod("validateSteps",  new Class[] {  Class.forName(marblePath),int.class});
			validateStepsMethod.setAccessible(true); 


			// Invoke the method

			int steps = -2;
			ArrayList<Object> result = (ArrayList)validateStepsMethod.invoke(board,marble, steps);

			fail("Expected IllegalMovementException was not thrown when  marble neither on track nor inside safeZone");


		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException | IllegalAccessException e) {

			fail(e.getClass() + " occurred  while testing validateSteps method, Error Message: " + 
					e.getMessage());
		}

		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalMovementException was not thrown when  marble neither on track nor inside safeZone" ,
					IllegalMovementException.isInstance(thrownException));
			assertTrue("Expected IllegalMovementException should have a message",
					thrownException.getMessage()!= null);
		}

	}





	@Test(timeout=1000)
	public void testValidateSwapExistenceInBoardClass() {
		Class<?> board_class = null;


		try {
			board_class = Class.forName(boardPath);
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}

		try {
			testMethodExistence(Class.forName(boardPath), "validateSwap", void.class,
					new Class[] { Class.forName(marblePath), Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {

			fail(e.getClass() + " occurred while creating object of type Marble");
		}
	}



	// Method Private




	// Test Both Marbles are not on track
	@Test(timeout = 1000)

	public void testValidateSwapMethodBothMarblesNotOnTrackInBoardClass() {
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


		try {
			// Get the private  method
			Method validateSwapMethod = board.getClass().getDeclaredMethod("validateSwap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			validateSwapMethod.setAccessible(true); 


			// Invoke the method
			validateSwapMethod.invoke(board,marbleActive, marbleOther);

			fail("Expected IllegalSwapException was not thrown, marbles are not on track");


		}



		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalSwapException was not thrown, but was " + e.getCause() ,
					IllegalSwapExceptionException.isInstance(thrownException));
			assertTrue("Expected IllegalSwapException should have a message ",
					thrownException.getMessage()!= null);
		}

		catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException e) {
			fail(e.getClass() + " occurred while testing validateSwap method, Error Message: " + 
					e.getMessage());			}

	}



	// Test active  Marble 1 is not on track



	// Test Marble2 is not on track

	@Test(timeout = 1000)
	public void testValidateSwapMethodrMarble2NotOnTrackInBoardClass() {
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


		int pose1 = 77;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			//Set marbles
			marbleField = track.get(pose1).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose1), marbleActive);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}


		try {
			// Get the private  method
			Method validateSwapMethod = board.getClass().getDeclaredMethod("validateSwap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			validateSwapMethod.setAccessible(true); 


			// Invoke the method
			validateSwapMethod.invoke(board,marbleActive, marbleOther);

			fail("Expected IllegalSwapException was not thrown, marble2 is not on track");


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

	// Test other player  Marble is in base
	@Test(timeout = 1000)
	public void testValidateSwapMethodOtherMarbleInBaseInBoardClass() {
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

		int pose1 = 12;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			// Set active player marble anywhere not base
			marbleField = track.get(pose1).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose1), marbleActive);

			//Set othr player marble in base marble
			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			int basePosition = 0;
			for(int i=0; i<safeZones.size();i++)
				if (otherColour == colourField.get(safeZones.get(i)) )
					basePosition = i*25  ;
			marbleField.set(track.get(basePosition), marbleOther);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}


		try {
			// Get the private  method
			Method validateSwapMethod = board.getClass().getDeclaredMethod("validateSwap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			validateSwapMethod.setAccessible(true); 


			// Invoke the method
			validateSwapMethod.invoke(board,marbleActive, marbleOther);

			fail("Expected IllegalSwapException was not thrown when (other marble) marble2 is  on its base");


		}




		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalSwapException was not thrown, but was " + e.getCause() ,
					IllegalSwapExceptionException.isInstance(thrownException));
			assertTrue("Expected IllegalSwapException should have a message ",
					thrownException.getMessage()!= null);
		}

		catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException  e) {
			fail(e.getClass() + " occurred while testing validateSwap method, Error Message: " + 
					e.getMessage());	
		}
	}


	// Test other player  Marble is in base
	@Test(timeout = 1000)
	public void testValidateSwapMethodOtherMarble1InBaseInBoardClass() {
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

		int pose1 = 12;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			// Set active player marble anywhere not base
			marbleField = track.get(pose1).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose1), marbleActive);

			//Set othr player marble in base marble
			// get safeZones
			Field safeZonesField = board.getClass().getDeclaredField("safeZones");
			safeZonesField.setAccessible(true);
			ArrayList <Object> safeZones = (ArrayList<Object>) safeZonesField.get(board);

			// get colour
			Field colourField = safeZones.get(0).getClass().getDeclaredField("colour");
			colourField.setAccessible(true);
			int basePosition = 0;
			for(int i=0; i<safeZones.size();i++)
				if (otherColour == colourField.get(safeZones.get(i)) )
					basePosition = i*25  ;
			marbleField.set(track.get(basePosition), marbleOther);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}


		try {
			// Get the private  method
			Method validateSwapMethod = board.getClass().getDeclaredMethod("validateSwap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			validateSwapMethod.setAccessible(true); 


			// Invoke the method
			validateSwapMethod.invoke(board,marbleOther, marbleActive);

			fail("Expected IllegalSwapException was not thrown when (other player marble ) marble1 is  on its base");


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


	// Test active player marble 1 in base should not throw exception




	// Test active player  marble 2 in   base should not throw exception 

	@Test(timeout = 1000)
	public void testValidateSwapMethodActivePlayerMarble2InBaseInBoardClass() {
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
			validateSwapMethod.invoke(board,marbleOther, marbleActive);

		}

		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertTrue("IllegalSwapExceptionException should not be thrown when active player marble 2 is in base and other player in normal cell",
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


	// Test both marbles are on normal cell should not throw exception

	@Test(timeout = 1000)
	public void testValidateSwapMethodMarblesNoneBasePositonsBoardClass() {
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

		int pose1 = 12;
		int pose2 = 55;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			// Set active player marble anywhere not base
			marbleField = track.get(pose1).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose1), marbleActive);

			//Set other player marble  anywhere not base
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

		}

		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertTrue("IllegalSwapExceptionException should not be thrown when both marbles are on normal none base cells",
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




	@Test(timeout=1000)
	public void testSwapExistenceInBoardClass() {
		Class<?> board_class = null;
		try {
			board_class = Class.forName(boardPath);
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Board");
		}
		try {
			testMethodExistence(board_class, "swap", void.class,
					new Class[] { Class.forName(marblePath), Class.forName(marblePath) });
		}
		catch (ClassNotFoundException e) {
			fail(e.getClass() + " occurred while creating object of type Marble");

		}

	}

	// Method Private



	// Test swap logic

	@Test(timeout = 1000)
	public void testSwapInBoardClass() {

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

		int pose1 = 24;
		int pose2 = 77;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			//Set marbles
			marbleField = track.get(pose1).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose1), marbleActive);
			marbleField.set(track.get(pose2), marbleOther);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, colour, marble attribute, Error Message: " + 
					e.getMessage());}



		try {
			// Get the private validateSteps Method 
			Method SwapMethod = board.getClass().getDeclaredMethod("swap",  new Class[] {  Class.forName(marblePath),Class.forName(marblePath)});
			SwapMethod.setAccessible(true); 


			// Invoke the method
			SwapMethod.invoke(board,marbleActive, marbleOther);

			Object newMarblePose1 = marbleField.get(track.get(pose1));
			Object newMarblePose2 = marbleField.get(track.get(pose2));

			assertTrue("Marbles should be swaped",marbleOther == newMarblePose1&& marbleActive == newMarblePose2  );



		}


		catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException e) {
			fail(e.getClass() + " occurred  while testing swap method, Error Message: " + 
					e.getMessage());

		}
		catch(InvocationTargetException e){
			fail(e.getCause() + " occurred  while testing swap method, when both marble are on normal track cell Error Message: " + 
					e.getMessage());
		}

	}


	// Test swap logic calls Validate Swap

	@Test(timeout = 1000)
	public void testSwapCallsValidateSwapInBoardClass() {
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


		try {

			// Get the private  method
			Method SwapMethod = board.getClass().getDeclaredMethod("swap",  new Class[] {  Class.forName(marblePath), Class.forName(marblePath)});
			SwapMethod.setAccessible(true); 

			// Invoke the method
			SwapMethod.invoke(board,marbleActive, marbleOther);
			fail("Expected IllegalSwapException was not thrown, validateSwap Method was not called");


		}
		catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalSwapException was not thrown, validateSwap Method was not called",
					IllegalSwapExceptionException.isInstance(thrownException));
		}

		catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException e) {
			fail(e.getClass() + " occurred  while testing swap method, Error Message: " + 
					e.getMessage());
		}

	}



	@Test(timeout=1000)
	public void testDiscardCardWithoutArgsExistenceInGameManagerClass() {
		try {
			testInterfaceMethod(Class.forName(gameManagerPath), "discardCard", void.class,
					new Class[] {});
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	@Test(timeout=1000)
	public void testGetActivePlayerColourExistenceInGameManagerClass() {
		try {
			testInterfaceMethod(Class.forName(gameManagerPath), "getActivePlayerColour", Class.forName(colourPath),
					new Class[] {});
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	@Test(timeout=1000)
	public void testGetNextPlayerColourExistenceInGameManagerClass() {
		try {
			testInterfaceMethod(Class.forName(gameManagerPath), "getNextPlayerColour", Class.forName(colourPath),
					new Class[] {});
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}


	@Test(timeout=1000)
	public void testMoveByExistenceInBoardManagerClass() {
		try {
			testInterfaceMethod(Class.forName(boardManagerPath), "moveBy", void.class,
					new Class[] { Class.forName(marblePath), int.class, boolean.class });
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	@Test(timeout=1000)
	public void testSwapExistenceInBoardManagerClass() {
		try {
			testInterfaceMethod(Class.forName(boardManagerPath), "swap", void.class,
					new Class[] { Class.forName(marblePath), Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}

	@Test(timeout=1000)
	public void testDestroyMarbleExistenceInBoardManagerClass() {
		try {
			testInterfaceMethod(Class.forName(boardManagerPath), "destroyMarble", void.class,
					new Class[] { Class.forName(marblePath) });
		} catch (ClassNotFoundException e) {
			fail("Class not found: " + e.getMessage());
		}
	}



	@Test(timeout=1000)
	public void testIsFullExistenceInSafeZoneClass() {
		try {
			testMethodExistence(Class.forName(safeZonePath), "isFull", boolean.class,
					new Class[] { });
		} catch (ClassNotFoundException e) {

			fail("Class not found: " + e.getMessage());
		}
	}


	// Method public
	@Test(timeout = 1000)
	public void testIsFullIsPublicInSafeZoneClass() {
		Class<?> safeZone_class = null;
		try {
			safeZone_class = Class.forName(safeZonePath);
		} catch (ClassNotFoundException e) {
			fail("class not found:  " + e.getMessage());

		}
		Method m = null;
		try {
			m = safeZone_class.getDeclaredMethod("isFull",new Class[] { });
		} catch (NoSuchMethodException | SecurityException e) {
			fail(e.getClass() + " occurred when searching  isFull method, Error Message: " + 
					e.getMessage());
		}
		int modifiers = m.getModifiers();
		assertTrue("isFull method should be public",Modifier.isPublic(modifiers));
	}


	// Method Logic case full
	@Test(timeout = 1000)
	public void testIsFullMethodCaseFullInSafeZoneClass() {
		try {

			// Set test environment
			Object game = constructGame(); // Create game instance
			Object board = constructBoard(game); // Create board instance


			// Get SsafeZone attribute
			Field SafeZonesF = board.getClass().getDeclaredField("safeZones");
			SafeZonesF.setAccessible(true);
			ArrayList <Object> SafeZones = (ArrayList<Object>) SafeZonesF.get(board);


			int rand = new Random().nextInt(4);
			Object safeZone = SafeZones.get(rand);


			Field cellsF = safeZone.getClass().getDeclaredField("cells");
			cellsF.setAccessible(true);
			ArrayList <Object> cells = (ArrayList<Object>) cellsF.get(safeZone);


			Field colourF = safeZone.getClass().getDeclaredField("colour");
			colourF.setAccessible(true);
			Object colour = colourF.get(safeZone);



			// Create marble of saafeZone colour
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(colour);

			boolean isFull = true; 
			for(Object cell : cells) {
				Field marbleF= cell.getClass().getDeclaredField("marble");
				marbleF.setAccessible(true);
				marbleF.set(cell, marble);

			}

			Method isFullMethod = safeZone.getClass().getDeclaredMethod("isFull",new Class[] { });
			boolean result = (boolean)isFullMethod.invoke(safeZone);

			assertTrue("isFull expected return " + isFull + " but was " + result, result == isFull);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred  while testing isFull method, Error Message: " + 
					e.getMessage());
		}

	}

	// Method Logic not full
	@Test(timeout = 1000)
	public void testIsFullMethodCaseNotFullInBoardClass() {
		try {

			// Set test environment
			Object game = constructGame(); // Create game instance
			Object board = constructBoard(game); // Create board instance


			// Get SsafeZone attribute
			Field SafeZonesF = board.getClass().getDeclaredField("safeZones");
			SafeZonesF.setAccessible(true);
			ArrayList <Object> SafeZones = (ArrayList<Object>) SafeZonesF.get(board);




			int rand = new Random().nextInt(4);
			Object safeZone = SafeZones.get(rand);


			Field cellsF = safeZone.getClass().getDeclaredField("cells");
			cellsF.setAccessible(true);
			ArrayList <Object> cells = (ArrayList<Object>) cellsF.get(safeZone);


			Field colourF = safeZone.getClass().getDeclaredField("colour");
			colourF.setAccessible(true);
			Object colour = colourF.get(safeZone);



			// Create marble of saafeZone colour
			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
			Object marble = marbleConstructor.newInstance(colour);

			boolean isFull = false; 
			for(Object cell : cells) {
				Field marbleF= cell.getClass().getDeclaredField("marble");
				marbleF.setAccessible(true);
				marbleF.set(cell, marble);
				break;

			}

			Method isFullMethod = safeZone.getClass().getDeclaredMethod("isFull",new Class[] { });
			boolean result = (boolean)isFullMethod.invoke(safeZone);

			assertTrue("isFull expected return " + isFull + " but was " + result, result == isFull);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred  while testing isFull method, Error Message: " + 
					e.getMessage());
		}

	}





	// Method public
	@Test(timeout = 1000)
	public void testGetPoolSizeIsPublicInSafeDeckClass() {
		Class<?> deck_class = null;
		try {
			deck_class = Class.forName(deckPath);
		} catch (ClassNotFoundException e) {
			fail("class not found:  " + e.getMessage());

		}
		Method m = null;
		try {
			m = deck_class.getDeclaredMethod("getPoolSize",new Class[] { });
		} catch (NoSuchMethodException | SecurityException e) {
			fail(e.getClass() + " occurred when searching  getPoolSize method, Error Message: " + 
					e.getMessage());
		}
		int modifiers = m.getModifiers();
		assertTrue("getPoolSize method should be public",Modifier.isPublic(modifiers));
	}


	// Method Logic





	@Test(timeout=1000)
	public void testRefillPoolExistenceInDeckClass() {
		try {
			testMethodExistence(Class.forName(deckPath), "refillPool", void.class,
					new Class[] {ArrayList.class });
		} catch (ClassNotFoundException e) {

			fail("Class not found: " + e.getMessage());
		}
	}


	// Method public



	//Method Logic
	@Test(timeout = 1000)
	public void testRefillPoolLogicInSafeDeckClass() {
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
			cardsPool_field.set(null,  new ArrayList());

			Method refillPoolMethod =  Class.forName(deckPath).getDeclaredMethod("refillPool",new Class[] {ArrayList.class });
			refillPoolMethod.invoke(null,cards);


			ArrayList<Object> result = (ArrayList<Object>) cardsPool_field.get(null);


			assertTrue("cardsPool should have cards of size expected " + cards.size() 
			+ " but was: " + result.size(), result.size() == cards.size());

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred  while testing refillPool method, Error Message: " + 
					e.getMessage());
		}

	}





	@Test(timeout=1000)
	public void testActExistenceInJackClass() {
		try {
			testMethodExistence(Class.forName(jackPath), "act", void.class,
					new Class[] {ArrayList.class });
		} catch (ClassNotFoundException e) {

			fail("Class not found: " + e.getMessage());
		}
	}


	// Method public
	@Test(timeout = 1000)
	public void testActIsPublicInSafeJackClass() {
		Class<?> jack_class = null;
		try {
			jack_class = Class.forName(jackPath);
		} catch (ClassNotFoundException e) {
			fail("class not found:  " + e.getMessage());

		}
		Method m = null;
		try {
			m = jack_class.getDeclaredMethod("act",new Class[] {ArrayList.class });
		} catch (NoSuchMethodException | SecurityException e) {
			fail(e.getClass() + " occurred when searching  act method, Error Message: " + 
					e.getMessage());
		}
		int modifiers = m.getModifiers();
		assertTrue("act method should be public",Modifier.isPublic(modifiers));
	}


	//Method Logic
	@Test(timeout = 1000)
	public void testActLogicInJackClass() {

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

		int pose1 = 23;
		int pose2 = 77;
		ArrayList<Object> track = null;
		Field marbleField = null;
		try {
			// GetTrack
			Field trackField = board.getClass().getDeclaredField("track");
			trackField.setAccessible(true);
			track = (ArrayList<Object>) trackField.get(board);

			//Set marbles
			marbleField = track.get(pose1).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(pose1), marbleActive);
			marbleField.set(track.get(pose2), marbleOther);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail(e.getClass() + " occurred  while searching track, marble attribute, Error Message: " + 
					e.getMessage());}


		try {

			Class<?> card_class = Class.forName(cardPath);
			Class<?> game_manager_class = Class.forName(gameManagerPath);
			Class<?> board_manager_class = Class.forName(boardManagerPath);

			Object suit_Object = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "HEART");
			String input_name = new Random().nextInt(10) +"card";
			Constructor<?> jack_constructor = Class.forName(jackPath).getConstructor(String.class,String.class,Class.forName(suitPath),board_manager_class,game_manager_class);
			int randSize = new Random().nextInt(15);

			Object jack_instance =  jack_constructor.newInstance(input_name,input_name,suit_Object,board,game);;




			ArrayList<Object> marbles = new ArrayList ();
			marbles.add(marbleActive);
			marbles.add(marbleOther);

			Method actMethod =  Class.forName(jackPath).getDeclaredMethod("act", new Class[] {ArrayList.class });
			actMethod.invoke(jack_instance,marbles);

			Object newMarblePose1 = marbleField.get(track.get(pose1));
			Object newMarblePose2 = marbleField.get(track.get(pose2));

			assertTrue("Marbles should be swaped",marbleOther == newMarblePose1&& marbleActive == newMarblePose2  );


		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException   e) {
			fail(e.getClass() + " occurred  while testing act method, Error Message: " + 
					e.getMessage());
		}

	}





	@Test(timeout = 1000)
	public void testValidateStepsIsCalledInMethodMoveByInBoardClass(){
		try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);

			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);

			Method moveByMethod= Class.forName(boardPath).getDeclaredMethod("moveBy", Class.forName(marblePath) , int.class, boolean.class);
			moveByMethod.invoke(board_object, marble ,2000, false);
		}
		catch (InvocationTargetException e) {
			Throwable cause = e.getCause();

			if (cause.getClass().getName().equals(IllegalMovementExceptionExceptionPath)) {

			} else {
				fail("moveBy method in class Board must call method ValidateSteps");
			}

		}
		catch (Exception e) {
			fail(e.getClass()+" occurred while accessing method moveBy method in class Board.");
		}
	}


	@Test(timeout=1000)
	public void testdestroyMarbleBoardMarblePosition(){

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


			Object redColour = Enum.valueOf((Class<Enum>) colourClass, "RED");

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(redColour);


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			Object cell_object = null;
			Object new_cell_object = null;

			int positionInPath = 1;
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			Method destroyMarblemethod=Class.forName(boardPath).getDeclaredMethod("destroyMarble",  marbleClass);
			destroyMarblemethod.setAccessible(true);
			destroyMarblemethod.invoke(board,marbleInstance);

			Object m = marbleField.get(track.get(positionInPath));

			assertEquals("destroyMarble method in class Board should set the position of this marble in the track to null.",null,m);




		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method destroyMarble method in class Board.");
		}

	}



	@Test(timeout=1000)
	public void testdestroyMarbleBoardSendHome(){

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

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> playerClass = Class.forName(playerPath);
			Field marblesField = playerClass.getDeclaredField("marbles");
			marblesField.setAccessible(true);
			marblesField.set(player1, new ArrayList<Object>());



			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);


			Class<?> colourClass = Class.forName(colourPath);


			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			Object cell_object = null;
			Object new_cell_object = null;


			int positionInPath = (int) (Math.random() * 100);
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			ArrayList<Object> expectedMarbles = new ArrayList<>();
			ArrayList<Object> setMarbles = new ArrayList<>();
			for (int i = 0; i < 2; i++) {
				Object marble = marbleConstructor.newInstance(colours.get(0));
				expectedMarbles.add(marble);
				setMarbles.add(marble);

			}

			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game, players);

			Object playersInst = players_field.get(game);

			ArrayList<Object> p = (ArrayList<Object>)playersInst;


			marblesField.set(p.get(0), setMarbles);

			Method destroyMarblemethod=Class.forName(boardPath).getDeclaredMethod("destroyMarble",  marbleClass);
			destroyMarblemethod.setAccessible(true);
			destroyMarblemethod.invoke(board,marbleInstance);


			expectedMarbles.add(marbleInstance);

			ArrayList<?> returnMarbles = (ArrayList<?>) marblesField.get(player1);




			if (expectedMarbles.size() != returnMarbles.size()) {
				fail("The size of the current player's marbles list should differ after destoying a marble: expected " + expectedMarbles.size() 
				+ " but was " + returnMarbles.size());
			}
			for (int i = 0; i < expectedMarbles.size(); i++) {
				Object expectedMarble = expectedMarbles.get(i);
				Object actualMarble = returnMarbles.get(i);
				if (!expectedMarble.equals(actualMarble)) {
					fail("Mismatch at index " + i
							+ ": expected " + expectedMarble + " but was " + actualMarble);
				}
			}



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method destroyMarble in class Board.");
		}

	}



	@Test(timeout = 1000)
	public void testValidateDestroyFailCasePositionInPath() {

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

			Object player1 = players.get(0);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}

			IllegalDestroyException = Class.forName(IllegalDestroyExceptionExceptionPath);

			Method validateDestroymethod=Class.forName(boardPath).getDeclaredMethod("validateDestroy",  int.class);
			validateDestroymethod.setAccessible(true);
			validateDestroymethod.invoke(board,-1);

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
			fail(e.getClass()+" occurred while accessing method validateDestroy in class Board.");
		}
	}	




	@Test(timeout = 1000)
	public void testValidateDestroyNotFailCaseSafeMarble() {

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

			int positionInPath = 1;
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



		} catch(InvocationTargetException |NullPointerException |ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException  e){
			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertTrue("IllegalDestroyException should not be thrown in case that a marble is not safe in their Base Cell.",
					!IllegalDestroyException.isInstance(thrownException));
			fail(e.getClass()+" occurred while accessing method validateDestroy in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method validateDestroy in class Board.");

		}
		catch (Exception e) {

			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertTrue("IllegalDestroyException should not be thrown in case that a marble is not safe in their Base Cell.",
					!IllegalDestroyException.isInstance(thrownException));
		}

	}	

	@Test(timeout = 1000)
	public void testValidateDestroyNotFailCaseSafeMarble2() {

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
			marbleField.set(track.get(positionInPath),null);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			IllegalDestroyException = Class.forName(IllegalDestroyExceptionExceptionPath);

			Method validateDestroymethod=Class.forName(boardPath).getDeclaredMethod("validateDestroy",  int.class);
			validateDestroymethod.setAccessible(true);
			validateDestroymethod.invoke(board,positionInPath);



		} catch(InvocationTargetException|ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| NullPointerException e){
			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertTrue("IllegalDestroyException should not be thrown in case that a marble is not safe in their Base Cell.",
					!IllegalDestroyException.isInstance(thrownException));
			fail(e.getClass()+" occurred while accessing method validateDestroy in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method validateDestroy in class Board.");

		}
		catch (Exception e) {
			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertTrue("IllegalDestroyException should not be thrown in case that a marble is not safe in their Base Cell.",
					!IllegalDestroyException.isInstance(thrownException));
		}

	}	



	@Test(timeout = 1000)
	public void testDestroyMarbleFailCase() {

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

			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field players_field = gameClass.getDeclaredField("players");
			players_field.setAccessible(true);
			players_field.set(game,players);

			Class<?> colour_class = Class.forName(colourPath);
			Class<?> playerClass = Class.forName(playerPath);
			Field marblesField = playerClass.getDeclaredField("marbles");
			marblesField.setAccessible(true);
			marblesField.set(player, new ArrayList<Object>());



			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colour_class);


			Class<?> colourClass = Class.forName(colourPath);


			Object marbleInstance = marbleConstructor.newInstance(colours.get(1));


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);



			Object cell_object = null;
			Object new_cell_object = null;

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			int positionInPath = 25;
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);


			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			IllegalDestroyException = Class.forName(IllegalDestroyExceptionExceptionPath);


			Method destroyMarblemethod=Class.forName(boardPath).getDeclaredMethod("destroyMarble",  marbleClass);
			destroyMarblemethod.setAccessible(true);
			destroyMarblemethod.invoke(board,marbleInstance);

			fail("Expected IllegalDestroyException was not thrown");

		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalDestroyException was not thrown",
					IllegalDestroyException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method destroyMarble in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method destroyMarble in class Board.");

		}
	}	






	@Test(timeout = 1000)
	public void testValidateSavingFailCasePositionOnTrackMarble() {

		Class<?> InvalidMarbleException = null;
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
			int positionInSafeZone = 0;
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);


			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method validateSavingmethod=Class.forName(boardPath).getDeclaredMethod("validateSaving", int.class,int.class);
			validateSavingmethod.setAccessible(true);
			validateSavingmethod.invoke(board,positionInSafeZone,positionInPath);

			fail("Expected InvalidMarbleException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					InvalidMarbleException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method validateSaving in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method validateSaving in class Board.");

		}
	}	




	@Test(timeout = 1000)
	public void testSendToSafeFailCaseSafeZoneMarble() {

		Class<?> InvalidMarbleException = null;
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

			int positionInPath = -1;
			int positionInSafeZone = -1;

			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method sendToSafemethod=Class.forName(boardPath).getDeclaredMethod("sendToSafe",marbleClass);
			sendToSafemethod.setAccessible(true);
			sendToSafemethod.invoke(board,marbleInstance);

			fail("Expected InvalidMarbleException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					InvalidMarbleException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method sendToSafe in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method sendToSafe in class Board.");

		}
	}


	@Test(timeout = 1000)
	public void testSendToSafeNotFailCaseSafeZoneMarble() {

		Class<?> InvalidMarbleException = null;
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
			int positionInSafeZone = -1;

			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);


			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);



			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method sendToSafemethod=Class.forName(boardPath).getDeclaredMethod("sendToSafe",marbleClass);
			sendToSafemethod.setAccessible(true);
			sendToSafemethod.invoke(board,marbleInstance);



		} catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method sendToSafe in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method sendToSafe in class Board.");

		}
		catch (Exception e) {
			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertFalse("InvalidMarbleException should be thrown only if the selected marble was already in the Safe Zone or if it wasn’t on the track.",
					InvalidMarbleException.isInstance(thrownException));
		}

	}	


	@Test(timeout = 1000)
	public void testValidateSavingFailCaseSafeZoneMarble() {

		Class<?> InvalidMarbleException = null;
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

			int positionInPath = -1;
			int positionInSafeZone = -1;

			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method validateSavingmethod=Class.forName(boardPath).getDeclaredMethod("validateSaving", int.class,int.class);
			validateSavingmethod.setAccessible(true);
			validateSavingmethod.invoke(board,positionInSafeZone,positionInPath);

			fail("Expected InvalidMarbleException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					InvalidMarbleException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method validateSaving in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method validateSaving in class Board.");

		}
	}


	@Test(timeout = 1000)
	public void testValidateSavingNotFailCaseSafeZoneMarble() {

		Class<?> InvalidMarbleException = null;
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
			int positionInSafeZone = -1;

			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);


			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);



			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method validateSavingmethod=Class.forName(boardPath).getDeclaredMethod("validateSaving", int.class,int.class);
			validateSavingmethod.setAccessible(true);
			validateSavingmethod.invoke(board,positionInSafeZone,positionInPath);



		} catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method validateSaving in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method validateSaving in class Board.");

		}
		catch (Exception e) {
			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertFalse("InvalidMarbleException should be thrown only if the selected marble was already in the Safe Zone or if it wasn’t on the track.",
					InvalidMarbleException.isInstance(thrownException));
		}

	}	


	@Test(timeout=1000)
	public void testSendToSafeBoardOldLocation(){

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

			Object marbleInstance1=null;
			Constructor<?> safe_zone_constructor = Class.forName(safeZonePath).getConstructor(Class.forName(colourPath));
			Object safe_zone_instance = safe_zone_constructor.newInstance(colours.get(0));;

			for (int i = 0; i < 4; i++)
			{
				safe_zone_instance = safe_zone_constructor.newInstance(colours.get(i));

				Class safeZoneClass = Class.forName(safeZonePath);
				Field f1 = safeZoneClass.getDeclaredField("cells");
				f1.setAccessible(true);
				ArrayList<Object> l = new ArrayList();
				for(int j=0;j<4;j++){
					Object cellType = Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "SAFE");
					Constructor<?> cellConstructor = Class.forName(cellPath).getConstructor(Class.forName(cellTypePath));
					Object cell = cellConstructor.newInstance(cellType);
					Class cellClass = Class.forName(cellPath);

					Field marbleField1 = Class.forName(cellPath).getDeclaredField("marble");
					marbleField1.setAccessible(true);
					marbleField1.set(cell, marbleInstance1);
					l.add(cell);
				}

				f1.set(safe_zone_instance, l);



				safeZones.add(safe_zone_instance);
			}

			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			safeZones_field.set(board, safeZones);


			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));

			ArrayList<Object> expectedMarbles = new ArrayList<>();
			ArrayList<Object> setMarbles = new ArrayList<>();

			int positionInPath =0;
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


			Method sendToSafemethod=Class.forName(boardPath).getDeclaredMethod("sendToSafe",marbleClass);
			sendToSafemethod.setAccessible(true);
			sendToSafemethod.invoke(board,marbleInstance);


			Object m = marbleField.get(track.get(positionInPath));

			assertEquals("sendToSafe method in class board should set the old position of this marble in the track to null.",null,m);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method sendToSafe in class Board.");
		}

	}

	@Test(timeout=1000)
	public void testMoveBoardOldLocation(){

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

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			for (int i = 0; i < 100; i++)
			{
				cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL"));
				if(i==0)
					marbleField.set(cell_object, marbleInstance);
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


			marbleField.set(track.get(0),marbleInstance);


			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);




			boolean destroy = false;

			ArrayList<Object> path = new ArrayList<Object>();
			Object currentCell = track.get(0);
			Object cell = track.get(1);
			Object targetCell = track.get(2);
			path.add(currentCell);
			path.add(cell);
			path.add(targetCell);


			Method moveMethod= Class.forName(boardPath).getDeclaredMethod("move", Class.forName(marblePath) , ArrayList.class, boolean.class);
			moveMethod.setAccessible(true);
			moveMethod.invoke(board, marbleInstance ,path, false);

			trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			Object returnMarble = marbleField.get(trackreturnValue.get(0));



			assertEquals("move method in class board should set the old position of this marble in the track to null.",null,returnMarble);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method move in class Board.");
		}
	}







	@Test(timeout=1000)
	public void testMoveBoardDestroyTarget(){
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

			Object player1 = players.get(0);
			Object player2 = players.get(1);
			Object player3 = players.get(2);


			Field playerColourField = Class.forName(playerPath).getDeclaredField("colour");
			playerColourField.setAccessible(true);

			Object player1Colour = playerColourField.get(player1);
			Object player2Colour = playerColourField.get(player2);
			Object player3Colour = playerColourField.get(player3);

			Class<?> colourClass = Class.forName(colourPath);

			Class<?> marbleClass = Class.forName(marblePath);
			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance1 = marbleConstructor.newInstance(player1Colour);
			Object marbleInstance2 = marbleConstructor.newInstance(player2Colour);
			Object marbleInstance3 = marbleConstructor.newInstance(player3Colour);

			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);


			Object cell_object = null;
			Object new_cell_object = null;

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);

			boolean destroy = false;
			marbleField.set(track.get(1), marbleInstance1);
			marbleField.set(track.get(2), marbleInstance2);
			ArrayList<Object> path = new ArrayList<Object>();
			Object currentCell = track.get(1);
			Object cell = track.get(2);
			Object targetCell = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL"));
			marbleField.set(targetCell, marbleInstance3);
			path.add(currentCell);
			path.add(cell);
			path.add(targetCell);

			track_field.set(board, track);


			Field playerMarblesField = Class.forName(playerPath).getDeclaredField("marbles");
			playerMarblesField.setAccessible(true);

			IllegalDestroyException = Class.forName(IllegalDestroyExceptionExceptionPath);

			Method moveMethod= Class.forName(boardPath).getDeclaredMethod("move", Class.forName(marblePath) , ArrayList.class, boolean.class);
			moveMethod.setAccessible(true);
			moveMethod.invoke(board, marbleInstance1 ,path, destroy);			
			fail("Expected IllegalDestroyException was not thrown");

		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected IllegalDestroyException was not thrown",
					IllegalDestroyException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method move in class Board.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method move in class Board.");

		}


	}



	@Test(timeout=1000)
	public void testMoveBoardDestroyTargetTrap(){

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


			boolean destroy = false;

			ArrayList<Object> path = new ArrayList<Object>();
			Object currentCell = track.get(0);
			Object cell = track.get(1);
			Object targetCell = track.get(2);
			path.add(currentCell);
			path.add(cell);
			path.add(targetCell);
			marbleField.set(track.get(1), marbleInstance2);
			marbleField.set(track.get(2),marbleInstance3);


			Field trap_field = Class.forName(cellPath).getDeclaredField("trap");
			trap_field.setAccessible(true);
			trap_field.set(targetCell, true);




			Method moveMethod= Class.forName(boardPath).getDeclaredMethod("move", Class.forName(marblePath) , ArrayList.class, boolean.class);
			moveMethod.setAccessible(true);
			moveMethod.invoke(board, marbleInstance ,path, destroy);

			track =  (ArrayList<Object>) track_field.get(board);

			Object returnMarble = marbleField.get(track.get(2));


			assertEquals("move method in class board should destroy the marble in the target cell if it was a trap",null,returnMarble);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method move in class Board.");
		}
	}


	@Test(timeout=1000)
	public void testMoveBoardAssignNewTrapCell(){

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

			ArrayList<Object> track = new ArrayList();
			Object cell_object = null;
			Object new_cell_object = null;

			Field marbleField = Class.forName(cellPath).getDeclaredField("marble");
			marbleField.setAccessible(true);


			for (int i = 0; i < 100; i++)
			{
				cell_object = cell_constructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(cellTypePath), "NORMAL"));
				if(i==0)
					marbleField.set(cell_object, marbleInstance);
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


			marbleField.set(track.get(0),marbleInstance);

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);
			track_field.set(board, track);



			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			boolean destroy = false;

			ArrayList<Object> path = new ArrayList<Object>();
			Object currentCell = track.get(0);
			Object cell = track.get(1);
			Object targetCell = track.get(2);
			path.add(currentCell);
			path.add(cell);
			path.add(targetCell);
			marbleField.set(track.get(1), marbleInstance2);
			marbleField.set(track.get(2),marbleInstance3);


			Field trap_field = Class.forName(cellPath).getDeclaredField("trap");
			trap_field.setAccessible(true);
			trap_field.set(targetCell, true);
			trap_field.set(cell, true);



			Method moveMethod= Class.forName(boardPath).getDeclaredMethod("move", Class.forName(marblePath) , ArrayList.class, boolean.class);
			moveMethod.setAccessible(true);
			moveMethod.invoke(board, marbleInstance ,path, destroy);

			trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			Object returnMarble = marbleField.get(trackreturnValue.get(2));

			int trap_cells=0;

			for(int i=0; i<trackreturnValue.size();i++){

				if((boolean) trap_field.get(trackreturnValue.get(i)))
					trap_cells++;

			}


			assertEquals("move method in class board should call the assign trap cell method if the target cell was a trap",2,trap_cells);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method move in class Board.");
		}
	}



	@Test(timeout=1000)
	public void testActBurnerDestroyMarble(){

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


			Object redColour = Enum.valueOf((Class<Enum>) colourClass, "RED");

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(redColour);


			Class cell_type_class = Class.forName(cellTypePath);
			Constructor<?> cell_constructor = Class.forName(cellPath).getConstructor(cell_type_class);

			Field track_field = Class.forName(boardPath).getDeclaredField("track");
			track_field.setAccessible(true);

			ArrayList<Object> track = (ArrayList<Object>) track_field.get(board);
			Object cell_object = null;
			Object new_cell_object = null;

			int positionInPath = 3;
			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);



			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);

			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marbleInstance);

			Method actMethod=Class.forName(burnerPath).getDeclaredMethod("act",  ArrayList.class);
			actMethod.setAccessible(true);
			actMethod.invoke(card,marbles);

			Object m = marbleField.get(track.get(positionInPath));

			assertEquals("Act method should call destroyMarble in class board.",null,m);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			fail(e.getClass()+" occurred while accessing method act in class Card.");
		}

	}

	@Test(timeout=1000)
	public void testPlayPlayerTurnGameBurner(){

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


			Method playMethod=Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
			playMethod.setAccessible(true);
			playMethod.invoke(game);

			Object m = marbleField.get(track.get(positionInPath));

			assertEquals("playPlayerTurn method in class Game should allow allows the current player to play their turn.",null,m);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method playPlayerTurn in class Game.");
		}

	}



	@Test(timeout=10000)
	public void testPlayPlayerActBurnerSelectedMarbles(){

		try {	
			Class<?> boardClass = Class.forName(boardPath);
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
			Object marbleInstance2 = marbleConstructor.newInstance(colours.get(1));

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

			int positionInPath2 = 1;
			marbleField.set(track.get(positionInPath2),marbleInstance2);


			track_field.setAccessible(true);
			track_field.set(board, track);


			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);


			Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Burner","description",board,game);



			Class<?> player_class = Class.forName(playerPath);
			Field card_field = player_class.getDeclaredField("selectedCard");
			card_field.setAccessible(true);
			card_field.set(player, card);

			Field selectedMarbles_field = player_class.getDeclaredField("selectedMarbles");
			selectedMarbles_field.setAccessible(true);
			ArrayList<Object> marbles = (ArrayList<Object>) selectedMarbles_field.get(player);
			marbles.add(marbleInstance);
			selectedMarbles_field.set(player, marbles);

			Field marbles_field = player_class.getDeclaredField("marbles");
			marbles_field.setAccessible(true);
			ArrayList<Object> playerMarbles = (ArrayList<Object>) marbles_field.get(player);
			playerMarbles.add(marbleInstance2);
			selectedMarbles_field.set(player, marbles);


			Method playMethod=Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.setAccessible(true);
			playMethod.invoke(player);

			Object m = marbleField.get(track.get(positionInPath2));

			assertEquals("Play method in class Player should allow the selected card to act with the selected marbles not marbles arraylist.",m,marbleInstance2);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method play in class Player.");
		}

	}


	@Test(timeout=1000)
	public void testPlayPlayerFailInvalidateMarbleColours(){
		Class<?> InvalidMarbleException = null;
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


			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method playMethod=Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.setAccessible(true);
			playMethod.invoke(player);

			fail("Expected InvalidMarbleException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					InvalidMarbleException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+" occurred while accessing method play in class Player.");
		} catch (NoSuchFieldException e) {

		}		
	}


	@Test(timeout=1000)
	public void testPlayPlayerFailInvalidateMarblesSize(){
		Class<?> InvalidMarbleException = null;
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

			int positionInPath = (int) (Math.random() * 100);
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
			ArrayList<Object> marbles = new ArrayList<Object>();	
			selectedMarbles.set(player, marbles);


			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Method playMethod=Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.setAccessible(true);
			playMethod.invoke(player);

			fail("Expected InvalidMarbleException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InvalidMarbleException was not thrown",
					InvalidMarbleException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+"  occurred while accessing method play in class Player.");
		} catch (NoSuchFieldException e) {
			fail(e.getClass()+"  occurred while accessing method play in class Player.");

		}		
	}


	@Test(timeout = 1000)
	public void testActSaverNotFailCaseSafeZone() {

		Class<?> InvalidMarbleException = null;
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
			int positionInSafeZone = -1;

			Field marbleField = track.get(positionInPath).getClass().getDeclaredField("marble");
			marbleField.setAccessible(true);
			marbleField.set(track.get(positionInPath),marbleInstance);

			track_field.setAccessible(true);
			track_field.set(board, track);


			Class<?> boardClass = Class.forName(boardPath);
			ArrayList<Object> trackreturnValue =  (ArrayList<Object>) track_field.get(board);

			InvalidMarbleException = Class.forName(InvalidMarbleExceptionExceptionPath);

			Constructor<?> cardConstructor = Class.forName(saverPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));
			Object card = cardConstructor.newInstance("Saver","description",board,game);

			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marbleInstance);

			Method actMethod=Class.forName(saverPath).getDeclaredMethod("act",  ArrayList.class);
			actMethod.setAccessible(true);
			actMethod.invoke(card,marbles);

			Object m = marbleField.get(track.get(positionInPath));

			assertEquals("Act method in Saver class should call sendToSafe in class board.",null,m);



		} catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){

			fail(e.getClass()+"  occurred while accessing method act in class Card.");
		} catch (NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method act in class Card.");

		}
		catch (Exception e) {
			Throwable thrownException = ((InvocationTargetException) e).getTargetException();
			assertFalse("InvalidMarbleException should be thrown only if the selected marble was already in the Safe Zone or if it wasn’t on the track.",
					InvalidMarbleException.isInstance(thrownException));
		}

	}	

	@Test(timeout=1000)
	public void testPlayPlayerActSaverSelectedMarbles(){

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

			ArrayList<Object> colours = new ArrayList<Object>();
			for(int i=0;i<players.size();i++){
				colours.add( playerColourField.get(players.get(i)));
			}


			int currentPlayerIndex = 0;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Class<?> marbleClass = Class.forName(marblePath);
			Class<?> colourClass = Class.forName(colourPath);

			Constructor<?> marbleConstructor = marbleClass.getConstructor(colourClass);
			Object marbleInstance = marbleConstructor.newInstance(colours.get(0));
			Object marbleInstance2 = marbleConstructor.newInstance(colours.get(1));

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

			int positionInPath2 = 2;
			marbleField.set(track.get(positionInPath2),marbleInstance2);

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
			card_field.set(player1, card);

			Field selectedMarbles_field = player_class.getDeclaredField("selectedMarbles");
			selectedMarbles_field.setAccessible(true);
			ArrayList<Object> marbles = (ArrayList<Object>) selectedMarbles_field.get(player1);
			marbles.add(marbleInstance);
			selectedMarbles_field.set(player1, marbles);

			Field marbles_field = player_class.getDeclaredField("marbles");
			marbles_field.setAccessible(true);
			ArrayList<Object> playerMarbles = (ArrayList<Object>) marbles_field.get(player1);
			playerMarbles.add(marbleInstance2);
			selectedMarbles_field.set(player1, marbles);


			Method playMethod=Class.forName(playerPath).getDeclaredMethod("play");
			playMethod.setAccessible(true);
			playMethod.invoke(player1);

			Object m = marbleField.get(track.get(positionInPath));


			assertEquals("Play method in class Player should allow the selected card to act with the selected marbles not marbles arraylist.",null,m);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method play in class Player.");
		}

	}

	@Test(timeout=1000)
	public void testPlayPlayerActSaver(){

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
	public void testCanPlayTurnGameFalse(){

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

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));

			Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));


			Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
			Object card2 = saverConstructor.newInstance("Burner","description",board,game);

			ArrayList<Object> playerHand = new ArrayList<Object>();
			playerHand.add(card1);
			playerHand.add(card2);

			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");
			hand_field.setAccessible(true);
			hand_field.set(player,playerHand);


			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 3);


			Method playMethod=Class.forName(gamePath).getDeclaredMethod("canPlayTurn");
			playMethod.setAccessible(true);
			boolean returnedValue= (boolean) playMethod.invoke(game);


			assertEquals("canPlayTurn method in class Game should check whether the player’s turn should be skipped by comparing their hand card count against the turn.",false,returnedValue);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method canPlayTurn method in class Game.");
		}

	}

	@Test(timeout=1000)
	public void testCanPlayTurnGameTrue(){

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

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));

			Constructor<?> saverConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));


			Object card1 = burnerConstructor.newInstance("Burner","description",board,game);
			Object card2 = saverConstructor.newInstance("Burner","description",board,game);

			ArrayList<Object> playerHand = new ArrayList<Object>();
			playerHand.add(card1);
			playerHand.add(card2);

			Class<?> playerClass = Class.forName(playerPath);
			Field hand_field = playerClass.getDeclaredField("hand");
			hand_field.setAccessible(true);
			hand_field.set(player,playerHand);


			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 2);


			Method playMethod=Class.forName(gamePath).getDeclaredMethod("canPlayTurn");
			playMethod.setAccessible(true);
			boolean returnedValue= (boolean) playMethod.invoke(game);


			assertEquals("canPlayTurn method in class Game should check whether the player’s turn should be skipped by comparing their hand card count against the turn.",true,returnedValue);


		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method canPlayTurn method in class Game.");
		}

	}


	@Test(timeout=1000)
	public void testCheckWinGame(){

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

			int randomPlayer = (int)(Math.random() * 4);
			Object player1 = players.get(randomPlayer);



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
			for(int i=0;i<4;i++){
				marbles.add( marbleConstructor.newInstance(player1Colour));
			}


			Field safeZones_field = Class.forName(boardPath).getDeclaredField("safeZones");
			safeZones_field.setAccessible(true);
			ArrayList<Object> safeZonesList = (ArrayList<Object>) safeZones_field.get(board);

			Object firstSafeZone = safeZonesList.get(randomPlayer);

			ArrayList<Object> firstSafeZoneCells = (ArrayList<Object>) cellsField.get(firstSafeZone);

			for(int i=0;i<4;i++){
				marbleField.set(firstSafeZoneCells.get(i), marbles.get(i));
			}

			cellsField.set(firstSafeZone,firstSafeZoneCells);

			Method checkWinMethod =Class.forName(gamePath).getDeclaredMethod("checkWin");
			checkWinMethod.setAccessible(true);
			Object returnedValue= checkWinMethod.invoke(game);


			assertEquals("checkWin method should return the color of the winning player if a player has won.",player1Colour,returnedValue);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method checkWin in class Game.");
		}

	}

	@Test(timeout=1000)
	public void testCheckWinNoWinnerGame(){

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

			Method checkWinMethod =Class.forName(gamePath).getDeclaredMethod("checkWin");
			checkWinMethod.setAccessible(true);
			Object returnedValue= checkWinMethod.invoke(game);


			assertEquals("checkWin method should return null if no player has won.",null,returnedValue);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method checkWin in class Game.");
		}

	}


	@Test(timeout=1000)
	public void testEndPlayerTurnGameNewTurn(){

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



			int currentPlayerIndex = 3;
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



			assertEquals("endPlayerTurn method in class game should start a new turn once all players have played a card and the play order is back to the the first player.",3,newTurn);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}



	@Test(timeout=1000)
	public void testEndPlayerTurnGameNewRound(){

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



			int currentPlayerIndex = 3;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 3);


			Method endPlayerTurnMethod =Class.forName(gamePath).getDeclaredMethod("endPlayerTurn");
			endPlayerTurnMethod.setAccessible(true);
			endPlayerTurnMethod.invoke(game);


			int newTurn = (int) turn_field.get(game);



			assertEquals("endPlayerTurn method in class game should start a new round once 4 turns have passed by resetting the turn counter.",0,newTurn);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}







	@Test(timeout=1000)
	public void testEndPlayerTurnGameAddingSelectedCardToFirepit(){

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


			Field firepit_field = Class.forName(gamePath).getDeclaredField("firePit");
			firepit_field.setAccessible(true);
			ArrayList<Object> firePit = (ArrayList<Object>) firepit_field.get(game);




			boolean found=false;
			for(int i=0;i<firePit.size();i++){
				if(firePit.get(i)==card)
					found=true;
			}



			assertEquals("endPlayerTurn method in class game should add the current player’s selected card to the firePit.",true,found);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}

	@Test(timeout=1000)
	public void testEndPlayerTurnGameDeselecting(){

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


			Object selectedCard = card_field.get(player1);

			assertEquals("endPlayerTurn method in class game should deselect everything the current player has selected.",null,selectedCard);



		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}




	@Test(timeout=1000)
	public void testEndPlayerTurnGameRefillingHands(){

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
			boolean Refilled = true;
			for(int i=0; i<players.size();i++){
				playerHand = (ArrayList<Object>) hand_field.get(players.get(i));
				if(playerHand.size()!=4)
					Refilled = false;

			}
			assertEquals("endPlayerTurn method in class game should refil all players’ hands from the deck when starting a new round.",true,Refilled);






		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}
	}



	@Test(timeout=1000)
	public void testEndPlayerTurnGameNotRefillingHands(){

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


			int currentPlayerIndex = 1;
			Field current_field = gameClass.getDeclaredField("currentPlayerIndex");
			current_field.setAccessible(true);
			current_field.set(game,currentPlayerIndex );

			Field turn_field = gameClass.getDeclaredField("turn");
			turn_field.setAccessible(true);
			turn_field.set(game, 3);

			Constructor<?> burnerConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,
					Class.forName(boardManagerPath),Class.forName(gameManagerPath));




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
			boolean Refilled = true;
			for(int i=0; i<players.size();i++){
				playerHand = (ArrayList<Object>) hand_field.get(players.get(i));
				if(playerHand.size()!=4)
					Refilled = false;

			}
			assertEquals("endPlayerTurn method in class game should not refil all players’ hands from the deck except when starting a new round.",false,Refilled);






		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {

			fail(e.getClass()+" occurred while accessing method endPlayerTurn in class Game.");
		}

	}





	@Test(timeout=1000)

	public void testEndPlayerTurnGameRefillingDeck(){

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

			int currentPlayerIndex = 3;
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


			assertTrue("endPlayerTurn method in class game should refil the Deck’s card pool with the cards in the firepit. Expected size is:" + 5
					+ " but was: " + result.size(), result.size() == 5);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred while accessing method endPlayerTurn in class Game." + 
					e.getMessage());
		}
	}






	@Test(timeout=1000)
	public void testEndPlayerTurnGameClearingFirePit(){

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

			int currentPlayerIndex = 3;
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


			ArrayList<Object> firePit = (ArrayList<Object>) firepit_field.get(game);


			assertTrue("endPlayerTurn method in class game should clear the firepit if the cards pool has fewer than 4 cards to draw. Expected size is:" + 0
					+ " but was: " + firePit.size(), firePit.size() == 0);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred while accessing method endPlayerTurn in class Game." + 
					e.getMessage());
		}
	}


	@Test(timeout=1000)
	public void testEndPlayerTurnGameNotClearingFirePit(){

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


			ArrayList<Object> firePit = (ArrayList<Object>) firepit_field.get(game);


			assertTrue("endPlayerTurn method in class game should not clear the firepit except when the cards pool has fewer than 4 cards to draw. Expected size is: 21"
					+ " but was: " + firePit.size(), firePit.size() != 0);

		} catch (SecurityException | ClassNotFoundException | 
				IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
				InvocationTargetException | InstantiationException | NoSuchFieldException  e) {
			fail(e.getClass() + " occurred while accessing method endPlayerTurn in class Game." + 
					e.getMessage());
		}
	}


/////// Select Card Method Player Class Tests/////////////
	@Test(timeout=1000)
	public void testSelectCardMethodPresentInPlayerClass(){
			try {
				testMethodExistence(Class.forName(playerPath),"selectCard",void.class,new Class [] {Class.forName(cardPath)});
			}
			catch(ClassNotFoundException e){
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
	@Test(timeout=1000)
	public void testSelectCardMethodInPlayerClassLogic() {
	
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

			//get player's hand
			Field handField = Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			ArrayList<Object> hand = (ArrayList<Object>) handField.get(player);
			
			//get a card from the player hand
			int random = (int) Math.random()*4;
			Object card = hand.get(random);

			
			//try the selectCard method
			Method m = Class.forName(playerPath).getDeclaredMethod("selectCard", Class.forName(cardPath));
			m.invoke(player, card);
			
			//get the selectedCard 
			Field selectedCardField = Class.forName(playerPath).getDeclaredField("selectedCard");
			selectedCardField.setAccessible(true);
			assertEquals("The card is not selected",card,selectedCardField.get(player));
			

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException  | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getMessage()+" occurred");
		}
		
	}
	
	
	@Test(timeout=1000)
	public void testSelectCardMethodInPlayerClassThrowsException() {
		Class invalidCardException = null;
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
		
			//create new board instance
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);
			
			//create new card object
			Object card = createStandardCard(game,board);		
		
			invalidCardException = Class.forName(InvalidCardExceptionExceptionPath);
	
			//try the selectCard method
			Method m = Class.forName(playerPath).getDeclaredMethod("selectCard", Class.forName(cardPath));
			m.invoke(player, card);
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}

	}
	/////// Select Card Method Player Class Tests/////////////
	
	
	
	/////// Select Card Method Game Class Tests/////////////
	@Test(timeout=1000)
	public void testSelectCardMethodPresentInGameClass(){
			try {
				testMethodExistence(Class.forName(gamePath),"selectCard",void.class,new Class [] {Class.forName(cardPath)});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
	@Test(timeout=1000)
	public void testSelectCardMethodInGameClassLogic() {
	
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

			//get player's hand
			Field handField = Class.forName(playerPath).getDeclaredField("hand");
			handField.setAccessible(true);
			ArrayList<Object> hand = (ArrayList<Object>) handField.get(player);
			
			//get a card from the player hand
			int random = (int) Math.random()*4;
			Object card = hand.get(random);
			
			//try the selectCard method
			Method m = Class.forName(gamePath).getDeclaredMethod("selectCard", Class.forName(cardPath));
			m.invoke(game, card);
			
			//get the selectedCard 
			Field selectedCard = Class.forName(playerPath).getDeclaredField("selectedCard");
			selectedCard.setAccessible(true);
			assertEquals("The card is not selected",card,selectedCard.get(player));
			

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException  | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");
		}
	}	
	/////// Select Card Method Game Class Tests/////////////
	
	
	/////// Select Marble Method Player Class Tests/////////////
	@Test(timeout=1000)
	public void testSelectMarlbeMethodPresentInPlayerClass(){
			try {
				testMethodExistence(Class.forName(playerPath),"selectMarble",void.class,new Class [] {Class.forName(marblePath)});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
	@Test(timeout=1000)
	public void testSelectMarbleMethodInPlayerClassLogicWithEmptyList() {
		
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
			Method m = Class.forName(playerPath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(player, marble4);
			
			assertEquals("The marble should be added to your selection ",selectedMarbles,playerSelectedMarbles.get(player));

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");
		}
	}
		
	@Test(timeout=1000)
	public void testSelectMarbleMethodInPlayerClassLogicMarbleAlreadyExists() {
		
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
			Method m = Class.forName(playerPath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(player, marble4);
			
			assertEquals("Selecting an already selected marble should not modify the selection",selectedMarbles2,playerSelectedMarbles.get(player));

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	/////// Select Marble Method Player Class Tests/////////////
	
	
	/////// Select Marble Method Game Class Tests/////////////
	@Test(timeout=1000)
	public void testSelectMarbleMethodPresentInGameClass(){
			try {
				testMethodExistence(Class.forName(gamePath),"selectMarble",void.class,new Class [] {Class.forName(marblePath)});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}	
	
	@Test(timeout=1000)
	public void testSelectMarbleMethodInGameClassLogicWithListofOneMarble() {
		
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
			Object marble5 = marbleConstructor.newInstance(playerColour);
			
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
			Method m = Class.forName(gamePath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(game, marble5);
			
			
			assertEquals("The marble should be added to your selection ",selectedMarbles2,playerSelectedMarbles.get(player));

		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");
		}
	}
		
	@Test(timeout=1000)
	public void testSelectMarbleMethodInGameClassThrowsException () {
		
		Class invalidMarbleException = null;
		
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
			Method m = Class.forName(gamePath).getDeclaredMethod("selectMarble", Class.forName(marblePath));
			m.invoke(game, marble2);
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
			
			fail("an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	
	/////// Select Marble Method Game Class Tests/////////////
	
	
	/////// deselectAll Method Player Class Tests/////////////
	
	@Test(timeout=1000)
	public void testDeselectAllMethodPresentInPlayerClass(){
			try {
				testMethodExistence(Class.forName(playerPath),"deselectAll",void.class,new Class [] {});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
	@Test(timeout=1000)
	public void testDeselectAllMethodInPlayerClassLogic(){
		
		
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
			
			Method m = Class.forName(playerPath).getDeclaredMethod("deselectAll");
			m.invoke(player);
			
			ArrayList<Object> selectedMarblesClear = new ArrayList<Object>();
			
			assertEquals("The card should be updated correctly",null,selectedCardField.get(player));
			assertEquals("No marbles should be selected",selectedMarblesClear,selectedMarblesField.get(player));
			
			
			
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
		
	}
	
	/////// deselectAll Method Player Class Tests/////////////
	
	
	/////// deselectAll Method Game Class Tests/////////////
	
	@Test(timeout=1000)
	public void testDeselectAllMethodPresentInGameClass(){
			try {
				testMethodExistence(Class.forName(gamePath),"deselectAll",void.class,new Class [] {});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}	
	/////// deselectAll Method Game Class Tests/////////////
	
	
	////// editSplitDistance Method Game Class Tests////////////
	
	@Test(timeout=1000)
	public void testEditSplitDistanceMethodPresentInGameClass(){
			try {
				testMethodExistence(Class.forName(gamePath),"editSplitDistance",void.class,new Class [] {int.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
	@Test(timeout=1000)
	public void testEditSplitDistanceMethodInGameClassLogic() {
		
		try {
			//Create game object
			Object game = createGame();	
			
			for(int splitDistance = 1; splitDistance < 7; splitDistance++){
				//Invoke the method in game
				Method m = Class.forName(gamePath).getDeclaredMethod("editSplitDistance", int.class);
				m.invoke(game, splitDistance);
				
				//Get the splitDistance from board
				Field splitDistanceField = Class.forName(boardPath).getDeclaredField("splitDistance");
				splitDistanceField.setAccessible(true);
				
				Field boardField = Class.forName(gamePath).getDeclaredField("board");
				boardField.setAccessible(true);
				Object board = boardField.get(game);
				
				
				assertEquals("Wrong split distance",splitDistance,splitDistanceField.get(board));
			}
			
			
		}
		catch(ClassNotFoundException | NoSuchMethodException | SecurityException  | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
	}
	
	@Test(timeout=1000)
	public void testEditSplitDistanceMethodInGameClassThrowsException() {
		
		Class splitOutOfRangeException = null;
		
		try {
			//Create game object
			Object game = createGame();
			
			//Generate a random number to be the split distance
			int randomDistance = new Random().nextInt(8) - 7;
	
			splitOutOfRangeException = Class.forName(SplitOutOfRangeExceptionExceptionPath);
			
			//Invoke the method in game
			Method m = Class.forName(gamePath).getDeclaredMethod("editSplitDistance", int.class);
			m.invoke(game, randomDistance);
			fail("Expected SplitOutOfRangeException was not thrown");
		}
		catch(InvocationTargetException e) {
			// Check if the thrown exception is SplitOutOfRangeException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected SplitOutOfRangeException was not thrown",
					splitOutOfRangeException.isInstance(thrownException));
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
		
		
	}
	
	@Test(timeout=1000)
	public void testEditSplitDistanceMethodInGameClassThrowsExceptionSecond() {
		
		Class splitOutOfRangeException = null;
		
		try {
			//Create game object
			Object game = createGame();
			
			//Generate a random number to be the split distance
			int randomDistance = new Random().nextInt(7) + 7;
			
			splitOutOfRangeException = Class.forName(SplitOutOfRangeExceptionExceptionPath);
			
			//Invoke the method in game
			Method m = Class.forName(gamePath).getDeclaredMethod("editSplitDistance", int.class);
			m.invoke(game, randomDistance);
			fail("Expected SplitOutOfRangeException was not thrown");
		}
		catch(InvocationTargetException e) {
			// Check if the thrown exception is SplitOutOfRangeException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected SplitOutOfRangeException was not thrown",
					splitOutOfRangeException.isInstance(thrownException));
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
		
		
	}
	
	//////editSplitDistance Method Game Class Tests////////////
	
	
	
	/////validateMarbleSize Method Card and All Subclasses Tests//////
	
	///Card Class///
	
	@Test(timeout=1000)
	public void testValidateMarbleSizePresentInCardClass() {
		try {
			testMethodExistence(Class.forName(cardPath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
		}
		catch(ClassNotFoundException e){
			
			fail("an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout=1000)
	public void testValidateMarbleSizeInCardClassLogic() {
		try {
			//Create game
			Object game = createGame();
			
			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);
			
			//Create StandardCard
			Object card = createStandardCard(game,board);
			
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
			Method m = Class.forName(cardPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);
			
			assertTrue("The size of marbles should be 1",returnValue);
			
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
	}
	
	@Test(timeout=1000)
	public void testValidateMarbleSizeInCardClassLogicFailCase() {
		try {
			//Create game
			Object game = createGame();
			
			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);
			
			//Create StandardCard
			Object card = createStandardCard(game,board);
			
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
			Method m = Class.forName(cardPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);
			
			assertFalse("The size of marbles should be 1",returnValue);
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
	}
	
	@Test(timeout=1000)
	public void testValidateMarbleSizeInCardClassLogicFailCaseWithEmptyList() {
		try {
			//Create game
			Object game = createGame();
			
			//get board from game
			Field boardField = Class.forName(gamePath).getDeclaredField("board");
			boardField.setAccessible(true);
			Object board = boardField.get(game);
			
			//Create StandardCard
			Object card = createStandardCard(game,board);	
			
			//Create a list with marble(s)
			ArrayList<Object> marbles = new ArrayList<Object>();
						
			//Invoke the validateMarbleSize method
			Method m = Class.forName(cardPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
			boolean returnValue = (boolean) m.invoke(card, marbles);
			
			assertFalse("The size of marbles should be 1",returnValue);
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			
			fail("an error from this catch statement."+e.getClass()+" occurred");

		}
	}

		
		///Ace Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizePresentInAceClass() {
			try {
				testMethodExistence(Class.forName(acePath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInAceClassLogicWithOneMarble() {
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
				Object marble = marbleConstructor.newInstance(colour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(acePath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInAceClassLogicWithNoMarble() {
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
				
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(acePath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 0",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		///King Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInKingClassLogicWithOneMarble() {
			try {
				//Create game
				Object game = createGame();
				
				//get board from game
				Field boardField = Class.forName(gamePath).getDeclaredField("board");
				boardField.setAccessible(true);
				Object board = boardField.get(game);
				
				//Create StandardCard
				Constructor<?> cardConstructor = Class.forName(kingPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("King","description13",suit,board,game);
				
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
				Method m = Class.forName(kingPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInKingClassLogicWithNoMarble() {
			try {
				//Create game
				Object game = createGame();
				
				//get board from game
				Field boardField = Class.forName(gamePath).getDeclaredField("board");
				boardField.setAccessible(true);
				Object board = boardField.get(game);
				
				//Create StandardCard
				Constructor<?> cardConstructor = Class.forName(kingPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("King","description13",suit,board,game);
				
				//Create Random Colour
				Object colour = generateRandomColour();
				
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(kingPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 0",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInKingClassLogicFailCase() {
			try {
				//Create game
				Object game = createGame();
				
				//get board from game
				Field boardField = Class.forName(gamePath).getDeclaredField("board");
				boardField.setAccessible(true);
				Object board = boardField.get(game);
				
				//Create StandardCard
				Constructor<?> cardConstructor = Class.forName(kingPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("King","description13",suit,board,game);
				
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
				Method m = Class.forName(kingPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertFalse("The size of marbles should be 0 or 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		///Queen Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizePresentInQueenClass() {
			try {
				testMethodExistence(Class.forName(queenPath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInQueenClassLogicWithOneMarble() {
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
				
				//Create Marble
				Class marbleClass = Class.forName(marblePath);
				Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
				Object marble = marbleConstructor.newInstance(colour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(queenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInQueenClassLogicFailCase() {
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
				Method m = Class.forName(queenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertFalse("The size of marbles should be 0 or 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		///Ten Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizePresentInTenClass() {
			try {
				testMethodExistence(Class.forName(tenPath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
				
		@Test(timeout=1000)
		public void testValidateMarbleSizeInTenClassLogicWithNoMarble() {
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
				
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(tenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 0",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInTenClassLogicFailCase() {
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
				
				
				//Create a list with marble(s)
				int random = new Random().nextInt(11)+2;
				ArrayList<Object> marbles = new ArrayList<Object>();
				Object marble = null;
				for(int i=0; i<random; i++) {
					marble = marbleConstructor.newInstance(colour);
					marbles.add(marble);
				}

				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(tenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertFalse("The size of marbles should be 0 or 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		///Jack Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInJackClassLogicWithTwoMarbles() {
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
				Object marble1 = marbleConstructor.newInstance(colour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
				marbles.add(marble1);
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 2",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInJackClassLogicWithNoMarble() {
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
								
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertFalse("The size of marbles should be 1 or 2",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInJackClassFailCase() {
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
				
				//Create a list with marble(s)
				int random = new Random().nextInt(11)+3;
				ArrayList<Object> marbles = new ArrayList<Object>();
				Object marble = null;
				for(int i=0; i<random; i++) {
					marble = marbleConstructor.newInstance(colour);
					marbles.add(marble);
				}
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertFalse("The size of marbles should be 1 or 2",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		///Seven Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizePresentInSevenClass() {
			try {
				testMethodExistence(Class.forName(sevenPath),"validateMarbleSize",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInSevenClassLogicWithOneMarble() {
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
				Object marble = marbleConstructor.newInstance(colour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(sevenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 1",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeInSevenClassLogicWithTwoMarbles() {
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
				Object marble = marbleConstructor.newInstance(colour);
				Object marble1 = marbleConstructor.newInstance(colour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
				marbles.add(marble1);
				
				//Invoke the validateMarbleSize method
				Method m = Class.forName(sevenPath).getDeclaredMethod("validateMarbleSize", ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card, marbles);
				
				assertTrue("The size of marbles should be 2",returnValue);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		///Standard Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeAbsentInStandardClass() {
			try {
				testMethodAbsence(Class.forName(standardPath),"validateMarbleSize");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		///Five Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeAbsentInFiveClass() {
			try {
				testMethodAbsence(Class.forName(fivePath),"validateMarbleSize");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		///Wild Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeAbsentInWildClass() {
			try {
				testMethodAbsence(Class.forName(wildPath),"validateMarbleSize");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		///Saver Class///
		
		@Test(timeout=1000)
		public void testValidateMarbleSizeAbsentInSaverClass() {
			try {
				testMethodAbsence(Class.forName(saverPath),"validateMarbleSize");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		
	/////validateMarbleSize Method Card and All Subclasses Tests//////
		
		///Play Method Exceptions Check///
		@Test(timeout=1000)
		public void testPlayMethodPresentInPlayerClass() {
			try {
				testMethodExistence(Class.forName(playerPath),"play",void.class, new Class[] {});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardNullCaseMarbleSize() {
			
			Class invalidCardException = null;
			try {
				//create player
				Object colour = generateRandomColour();
				Object player = createPlayer(colour);
				
				//get the selected null field
				Field f = Class.forName(playerPath).getDeclaredField("selectedCard");
				f.setAccessible(true);
				f.set(player, null);
				
				
				invalidCardException = Class.forName(InvalidCardExceptionExceptionPath);
				
				//get play method
				Method m = Class.forName(playerPath).getDeclaredMethod("play");
				m.invoke(player);
				fail("Expected InvalidCardException was not thrown");
			}
			catch(InvocationTargetException e) {
				// Check if the thrown exception is InvalidCardException
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue("Expected InvalidCardException was not thrown",
						invalidCardException.isInstance(thrownException));
			
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardAceCaseMarbleSize() {
			
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
				Constructor<?> cardConstructor = Class.forName(acePath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("Ace","description1",suit,board,game);
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardKingCaseMarbleSize() {
			
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
				Constructor<?> cardConstructor = Class.forName(kingPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("King","description13",suit,board,game);
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardTenCaseMarbleSize() {
			
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
				Constructor<?> cardConstructor = Class.forName(tenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("Ten","description10",suit,board,game);
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}	
		}
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardJackCaseEmptyListMarbleSize() {
			
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardSevenCaseEmptyListMarbleSize() {
			
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
				Constructor<?> cardConstructor = Class.forName(sevenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("Seven","description7",suit,board,game);
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardSevenCaseMarbleSize() {
			
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
				Constructor<?> cardConstructor = Class.forName(sevenPath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("Seven","description7",suit,board,game);
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardStandardCaseMarbleSize() {
			
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
				
				
				//Create a list with marble(s)
				int random = new Random().nextInt(11)+2;
				ArrayList<Object> marbles = new ArrayList<Object>();
				Object marble1 = null;
				for(int i=0; i<random; i++) {
					marble1 = marbleConstructor.newInstance(playerColour);
					marbles.add(marble1);
				}
				
				//marbles list fixed value
				ArrayList<Object> marblesFixed = new ArrayList<Object>();
				marblesFixed.add(marble);
				Field palyerMarblesField = Class.forName(playerPath).getDeclaredField("marbles");
				palyerMarblesField.setAccessible(true);
				palyerMarblesField.set(player, marblesFixed);
				
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardStandardCaseMarbleColour() {
			
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
				Object marbleFixed = marbleConstructor.newInstance(playerColour);

				//marbles list fixed value
				ArrayList<Object> marblesFixed = new ArrayList<Object>();
				marblesFixed.add(marbleFixed);
				Field marblesField = Class.forName(playerPath).getDeclaredField("marbles");
				marblesField.setAccessible(true);
				marblesField.set(player, marblesFixed);
				
				Object marble=null;
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
				
				//get selected marbles list of player
				Field selectedMarblesField = Class.forName(playerPath).getDeclaredField("selectedMarbles");
				selectedMarblesField.setAccessible(true);
				selectedMarblesField.set(player, marbles);
				
				//get the selected card field
				Field selectedCard = Class.forName(playerPath).getDeclaredField("selectedCard");
				selectedCard.setAccessible(true);
				selectedCard.set(player, card);
				
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testPlayMethodSelectedCardJackCaseMarbleColour() {
			
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
				
				Object marbleFixed=null;
				if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN")))
					marbleFixed = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
				else{
					if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED")))
						marbleFixed = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
					else{
						if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW")))
							marbleFixed = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
						else
							marbleFixed = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED"));
					}
				}
				
				//marbles list fixed value
				ArrayList<Object> marblesFixed = new ArrayList<Object>();
				marblesFixed.add(marbleFixed);
				marblesFixed.add(marble);
				Field marblesField = Class.forName(playerPath).getDeclaredField("marbles");
				marblesField.setAccessible(true);
				marblesField.set(player, marblesFixed);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				Object marble1 = marbleConstructor.newInstance(playerColour);
				Object marble2 = marbleConstructor.newInstance(playerColour);
				marbles.add(marble1);
				marbles.add(marble2);
				
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
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		/////Validate Marble Colour Method Tests////////
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursPresentInCardClass() {
			try {
				testMethodExistence(Class.forName(cardPath),"validateMarbleColours",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInStandardClassLogic() {
			
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
				Object marble = marbleConstructor.newInstance(playerColour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
								
				//get validateMarbleColours method
				Method m = Class.forName(cardPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertTrue("The colour of marble should belong to the current player",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursPresentInJackClass() {
			try {
				testMethodExistence(Class.forName(jackPath),"validateMarbleColours",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInJackClassLogicWithTwoMarbles() {
			
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
				Object marble1 = marbleConstructor.newInstance(playerColour);
				Object marble2 = null;
				if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "GREEN")))
					marble2 = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
				else{
					if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED")))
						marble2 = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
					else{
						if(playerColour.equals(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "YELLOW")))
							marble2 = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "BLUE"));
						else
							marble2 = marbleConstructor.newInstance(Enum.valueOf((Class<Enum>) Class.forName(colourPath), "RED"));
					}
				}
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble1);
				marbles.add(marble2);
								
				//get validateMarbleColours method
				Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertTrue("The colour of marbles should be different",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInJackClassLogicWithTwoMarblesFail() {
			
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
				Object marble1 = marbleConstructor.newInstance(playerColour);
				Object marble2 = marbleConstructor.newInstance(playerColour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble1);
				marbles.add(marble2);
				
				//get validateMarbleColours method
				Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertFalse("The colour of marbles should be different",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
				
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInJackClassWithOneMarbleFail() {
			
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
				Method m = Class.forName(jackPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertFalse("The colour of marble is invalid",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursPresentInFiveClass() {
			try {
				testMethodExistence(Class.forName(fivePath),"validateMarbleColours",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInFiveClassLogic() {
			
			try {
				Object game = createGame();
				
				//get board from game
				Field boardField = Class.forName(gamePath).getDeclaredField("board");
				boardField.setAccessible(true);
				Object board = boardField.get(game);				
				
				//create colour
				Object colour = generateRandomColour();
				
				//Create StandardCard
				Constructor<?> cardConstructor = Class.forName(fivePath).getConstructor(String.class,String.class,Class.forName(suitPath),Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("Five","description5",suit,board,game);
				
				//Create Marble
				Class marbleClass = Class.forName(marblePath);
				Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
				Object marble = marbleConstructor.newInstance(colour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
								
				//get validateMarbleColours method
				Method m = Class.forName(fivePath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertTrue("The colour of marble is valid",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursPresentInBurnerClass() {
			try {
				testMethodExistence(Class.forName(burnerPath),"validateMarbleColours",boolean.class,new Class [] {ArrayList.class});
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInBurnerClassFail() {
			
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
				Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object card = cardConstructor.newInstance("Burner","description14",board,game);
				
				//Create Marble
				Class marbleClass = Class.forName(marblePath);
				Constructor marbleConstructor = marbleClass.getConstructor(Class.forName(colourPath));
				Object marble = marbleConstructor.newInstance(playerColour);
				
				//Create a list with marble(s)
				ArrayList<Object> marbles = new ArrayList<Object>();
				marbles.add(marble);
								
				//get validateMarbleColours method
				Method m = Class.forName(burnerPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertFalse("The colour of marble is invalid",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColourMethodInBurnerClassLogic() {
			
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
				Constructor<?> cardConstructor = Class.forName(burnerPath).getConstructor(String.class,String.class,Class.forName(boardManagerPath),Class.forName(gameManagerPath));
				Object suit = Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE");
				Object card = cardConstructor.newInstance("Burner","description14",board,game);
				
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
				Method m = Class.forName(burnerPath).getDeclaredMethod("validateMarbleColours",ArrayList.class);
				boolean returnValue = (boolean) m.invoke(card,marbles);
				
				assertTrue("The colour of marble is valid",returnValue);
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				
				fail("an error from this catch statement."+e.getClass()+" occurred");

			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursAbsentInStandardClass() {
			try {
				testMethodAbsence(Class.forName(standardPath),"validateMarbleColours");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursAbsentInAceClass() {
			try {
				testMethodAbsence(Class.forName(acePath),"validateMarbleColours");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursAbsentInKingClass() {
			try {
				testMethodAbsence(Class.forName(kingPath),"validateMarbleColours");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursAbsentInQueenClass() {
			try {
				testMethodAbsence(Class.forName(queenPath),"validateMarbleColours");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursAbsentInFourClass() {
			try {
				testMethodAbsence(Class.forName(fourPath),"validateMarbleColours");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout=1000)
		public void testValidateMarbleColoursAbsentInWildClass() {
			try {
				testMethodAbsence(Class.forName(wildPath),"validateMarbleColours");
			}
			catch(ClassNotFoundException e){
				
				fail("an error from this catch statement."+e.getClass()+" occurred");
			}
		}
		
		@Test(timeout = 1000)
		public void testActMethodInClassAceMovesMarble1Step()  {
			try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			
			Constructor<?> five_constructor = Class.forName(acePath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object ace_card = five_constructor.newInstance("Ace card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


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
			
			
			//Cell1 that the marble will move to in case of ace card
		    Object Cell1 = track.get(1);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell1);
		    
			// Invoke Ace.act 
			Method actMethod= Class.forName(acePath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(ace_card, marbles);
			
		    assertEquals("Method act in class Ace must call method moveBy", null, getMarbleMethod.invoke(Cell0) );

			
			// check new position now contains the marble if it wasn't a trap
		    if( !isTrap) 
		    assertEquals("Method act in class Ace must call method moveBy with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell1));
			} 
			catch(Exception e){
				fail(e.getMessage() + " occured while accessing method act in class Ace");
			}	
		}

		@Test(timeout = 1000)
		public void testActMethodInClassKingDestroysMarblesInPath()  {
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

			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marbles to cell 0 & 5 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble2);
			
		    
			Constructor<?> king_constructor = Class.forName(kingPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object king_card = king_constructor.newInstance("king card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


			// Invoke King.act 
			Method actMethod= Class.forName(kingPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(king_card, marbles);
			
			
			// Check position 5 is null
		    assertEquals("Method act in class King must destroy all marbles in its path", null, getMarbleMethod.invoke(Cell5) );

			} 
			catch(Exception e){
				
				fail(e.getCause()+ " occured while accessing method moveBy in class board");
			}	
		}

		@Test(timeout = 1000)
		public void testActMethodInClassKingDoesntDestroysMarblesNOTInPath()  {
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
					

			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);
			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marble to cell 0 & 15 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			Object Cell15 = track.get(15);
			setMarbleMethod.invoke(Cell15, marble2);
			
		    
			// Invoke King.act 
			Method actMethod= Class.forName(kingPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(king_card, marbles);
			
			
			// Check position 15 is not affected
		    assertEquals("Method act in class King must not destroy marbles not in its path", marble2, getMarbleMethod.invoke(Cell15) );

			} 
			catch(Exception e){

				fail(e.getCause()+ " occured while accessing method act in class King");
			}	
		}

		@Test(timeout = 1000)
		public void testActMethodInClassJacknMovesMarble11Steps()  {
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
			
			Constructor<?> jack_constructor = Class.forName(jackPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object jack_card = jack_constructor.newInstance("Jack card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			
			//Cell10 that the marble will move to in case of a jack card
		    Object Cell11 = track.get(11);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell11);
		    
			// Invoke jack.act 
			Method actMethod= Class.forName(jackPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(jack_card, marbles);
			
			
			// Check position 0 is null
		    assertEquals("Method act in class Jack must call method moveBy in case given a list of 1 marble", null, getMarbleMethod.invoke(Cell0));

			
			// check position 11 now contains the marble if it wasn't a trap
		    if( !isTrap) 
		    assertEquals("Method act in class Jack must call method moveBy with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell11));
			} 
			catch(Exception e){
				fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Jack");
			}	
		}
			
		@Test(timeout = 1000)
		public void testActMethodInClassJackDoesntDestroysMarblesInPath()  {
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
			
			Constructor<?> jack_constructor = Class.forName(jackPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object jack_card = jack_constructor.newInstance("Jack card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marble to cell 0 & 5 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble2);
			
		    
			// Invoke jack.act 
			Method actMethod= Class.forName(jackPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(jack_card, marbles);
			
			// Check position 5 didn't change
		    assertEquals("Method act in class jack must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell5) );

			} 
			catch(Exception e){
				fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Jack");
			}	
		}

		@Test(timeout = 1000)
		public void testActMethodInClassTenMovesMarble10Steps()  {
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
			
			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			//Cell10 that the marble will move to in case of ten card
		    Object Cell10 = track.get(10);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell10);
		    
			// Invoke Ten.act 
			Method actMethod= Class.forName(tenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(ten_card, marbles);
			
			
			// Check position 0 is null
		    assertEquals("Method act in class Ten must call method moveBy in case given a list of non empty marbles", null, getMarbleMethod.invoke(Cell0) );

			
			// check position 10 now contains the marble if it wasn't a trap
		    if( !isTrap) 
		    assertEquals("Method act in class Ten must call method moveBy with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell10) );
			} 
			catch(Exception e){
				fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Ten");
			}	
		}

		@Test(timeout = 1000)
		public void testActMethodInClassSevenMovesOneMarble7Steps()  {
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

			Constructor<?> seven_constructor = Class.forName(sevenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object seven_card = seven_constructor.newInstance("seven card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			
			//Cell7 that the marble will move to in case of seven card
		    Object Cell7 = track.get(7);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell7);
		    
		
			Method actMethod= Class.forName(sevenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(seven_card, marbles);
			
			
			// Check position 0 is null
		    assertEquals("Method act in class Seven must call method moveBy in case given a list of 1 marble", null, getMarbleMethod.invoke(Cell0) );

			
			// check position 7 now contains the marble if it wasn't a trap
		    if( !isTrap) 
		    assertEquals("Method act in class Seven must call method MoveBy with the correct marble and the correct number of steps", marble, getMarbleMethod.invoke(Cell7) );
			} 
			catch(Exception e){
				fail(e.getMessage() +  " occured while accessing method act in class Seven");
			}	
		}
		@Test(timeout = 1000)
		public void testActMethodInClassSevenMovesTwoMarbles7Steps()  {
			try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
				
			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			Object marble3 = createMarbleForActivePlayer(game);
			marbles.add(marble);marbles.add(marble3);
		
			// Create opponent Marble
			Object marble2 = createOpponentMarble(game);

			
			Constructor<?> seven_constructor = Class.forName(sevenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object seven_card = seven_constructor.newInstance("seven card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
		    
			// Add the marble to cell 0 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			Object Cell6 = track.get(6);
			setMarbleMethod.invoke(Cell6, marble3);
			
			// Get Split Distance
			Method getSplitDistanceMethod= Class.forName(boardPath).getDeclaredMethod("getSplitDistance");
			int split = (int) getSplitDistanceMethod.invoke(board_object);
		    

		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    
			// check the marble to cell 0 on track
			Object Cell3 = track.get(0+split);
		    boolean isCell3Trap = (boolean) isTrapMethod.invoke(Cell3);

			Object Cell10 = track.get(6+(7-split));
		    boolean isCell10Trap = (boolean) isTrapMethod.invoke(Cell10);

					
		    
			// Invoke Seven.act 
			Method actMethod= Class.forName(sevenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(seven_card, marbles);
			
		
			// Check position 0 is null
		    assertEquals("Method act in class Seven must call method moveBy on the first marble", null, getMarbleMethod.invoke(Cell0) );
		    assertEquals("Method act in class Seven must call method moveBy on the second marble with the correct remining steps", null, getMarbleMethod.invoke(Cell6) );

			
			// check position 3 and 10 now contains the marbles if they werent trap cells 
		    if( !isCell3Trap) 
		    	assertEquals("Method act in class Seven must call method moveBy on the first marble with the correct number of splitted steps", marble, getMarbleMethod.invoke(Cell3));
			
		    if( !isCell10Trap) 
		    	assertEquals("Method act in class Seven must call method moveBy on the first marble with the correct number of splitted steps", marble3, getMarbleMethod.invoke(Cell10));
			}
			catch(Exception e){
				fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Seven");
			}	
		}
		
		@Test(timeout = 1000)
		public void testActMethodInClassSevenDoesntDestroysMarblesInPathWhenMoving1Marble()  {
			try {
			Class<?> board_class = Class.forName(boardPath); 
			Class<?> game_manager_class = Class.forName(gameManagerPath); 
			Class<?> color_class = Class.forName(colourPath); 
			Constructor<?> board_constructor = Class.forName(boardPath).getConstructor(ArrayList.class,game_manager_class);
			Constructor<?> seven_constructor = Class.forName(sevenPath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));

			
			Constructor<?> game_constructor = Class.forName(gamePath).getConstructor(String.class);
			Object game = game_constructor.newInstance("test");
			ArrayList<Object> colourOrder = new ArrayList();
			Object colour_Object = Enum.valueOf((Class<Enum>) color_class, "RED");
			colourOrder.add(colour_Object);
			Object colour_Object2 = Enum.valueOf((Class<Enum>) color_class, "GREEN");
			colourOrder.add(colour_Object2);
			Object colour_Object3 = Enum.valueOf((Class<Enum>) color_class, "BLUE");
			colourOrder.add(colour_Object3);
			Object colour_Object4 = Enum.valueOf((Class<Enum>) color_class, "YELLOW");
			colourOrder.add(colour_Object4);
		
			Object board_object = board_constructor.newInstance(colourOrder,game);
			
			//Create the queen object
			Object seven_card = seven_constructor.newInstance("seven card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);

			//Get the track of the board
			Field trackField = board_class.getDeclaredField("track"); 
			trackField.setAccessible(true);
			ArrayList<Object> track = (ArrayList<Object>) trackField.get(board_object);
	      
			// Create a marble
			// get active player color 
			Field players = Class.forName(gamePath).getDeclaredField("players"); 
			players.setAccessible(true);
			ArrayList<Object> players_array = (ArrayList<Object>) players.get(game);
						
			Field currentPlayerIndex = Class.forName(gamePath).getDeclaredField("currentPlayerIndex"); 
			currentPlayerIndex.setAccessible(true);
			int currentPlayerIndex_value = (int) currentPlayerIndex.get(game);
						
			Method getColorMethod= Class.forName(playerPath).getDeclaredMethod("getColour");
			Object activePlayerColour = getColorMethod.invoke(players_array.get(currentPlayerIndex_value));
			
			ArrayList<Object> marbles = new ArrayList<Object>();
			Constructor<?> marble_constructor = Class.forName(marblePath).getConstructor(color_class);
			
			//Add 1 oponent marble
			Object marble2 = null;
			
			if(activePlayerColour == colour_Object)
				marble2 = marble_constructor.newInstance(colour_Object2);
			else if(activePlayerColour == colour_Object2)
				marble2 = marble_constructor.newInstance(colour_Object);
			else if(activePlayerColour == colour_Object3)
				marble2 = marble_constructor.newInstance(colour_Object);
			else if(activePlayerColour == colour_Object4)
				marble2 = marble_constructor.newInstance(colour_Object);
			
			// Add 1 marble for the active player
			Object marble = marble_constructor.newInstance(activePlayerColour);
			marbles.add(marble);
			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marbles to cell 0 & 5 on track
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble2);
			

			// Invoke Seven.act 
			Method actMethod= Class.forName(sevenPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(seven_card, marbles);
			
			// Check position 5 didn't change
		    assertEquals("Method act in class Seven must not destroy any marbles in its path", marble2, getMarbleMethod.invoke(Cell5) );

			} 
			catch(Exception e){
				fail(e.getMessage() + e.getCause()+ " occured while accessing method act in class Seven");
			}
		}
		@Test(timeout = 1000)
		public void testActMethodInClassFiveMovesMarble5Steps()  {
			try {
			Object game = createGame();
			Object board_object = getBoardObjectFromGame(game);
			ArrayList<Object> track = getTrack(board_object);
			
			Constructor<?> five_constructor = Class.forName(fivePath).getConstructor(String.class, String.class, Class.forName(suitPath), Class.forName(boardManagerPath) , Class.forName(gameManagerPath));
			Object five_card = five_constructor.newInstance("five card","description",Enum.valueOf((Class<Enum>) Class.forName(suitPath), "SPADE"), board_object, game);


			// Create a marble
			ArrayList<Object> marbles = new ArrayList<Object>();
			Object marble = createMarbleForActivePlayer(game);
			marbles.add(marble);
			
			// Methods to get and set marbles in a cell
		    Method setMarbleMethod= Class.forName(cellPath).getDeclaredMethod("setMarble", Class.forName(marblePath));
		    Method getMarbleMethod= Class.forName(cellPath).getDeclaredMethod("getMarble");
			
			// Add the marble to cell 5 on track
			Object Cell5 = track.get(5);
			setMarbleMethod.invoke(Cell5, marble);
			
			
			//Cell10 that the marble will move to in case of five card
		    Object Cell10 = track.get(10);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell10);
		    
			// Invoke Five.act 
			Method actMethod= Class.forName(standardPath).getDeclaredMethod("act", ArrayList.class);
			actMethod.invoke(five_card, marbles);
			
			
			// Check position 5 is null
		    assertEquals("Method act in class five must call method moveBy, as inherritted from its superclass.", null, getMarbleMethod.invoke(Cell5));

			
			// check position 1 now contains the marble if it wasn't a trap
		    if( !isTrap) 
		    assertEquals("Method act in class five must call method act with the correct marble and the correct number of steps", marble,  getMarbleMethod.invoke(Cell10) );
			} 
			catch(Exception e){
				fail(e.getMessage() + " occured while accessing method act in class Five");
			}	
		}
		
		@Test(timeout = 1000)
		public void testPlayMethodInClassPlayerWithCardJack()  {
			
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
			
			
			//Cell 11 that the marble will move to 
		    Object Cell11 = track.get(11);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell11);
		    
		    
			// Select a card
			Field card = Class.forName(playerPath).getDeclaredField("selectedCard");
			card.setAccessible(true);
			card.set(player, hand.get(2));
			
			
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
		    assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble,  getMarbleMethod.invoke(Cell11) );
					
			} catch (Exception e) {
				fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

			}
		}

		@Test(timeout = 1000)
		public void testPlayMethodInClassPlayerWithCardTen()  {
			
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
			
			
			//Cell 10 that the marble will move to in case of ten card played
		    Object Cell10 = track.get(10);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell10);
		    
		    
			// Select a card
			Field card = Class.forName(playerPath).getDeclaredField("selectedCard");
			card.setAccessible(true);
			card.set(player, hand.get(2));
			
			
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
		    assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble, getMarbleMethod.invoke(Cell10) );
					
			} catch (Exception e) {
				fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

			}
		}

		@Test(timeout = 1000)
		public void testPlayMethodInClassPlayerWithCardFive()  {
			
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
			
			// Create opponent marble
			Object marble = createOpponentMarble(game);
			ArrayList<Object> marbles = new ArrayList<Object>();
			marbles.add(marble);
			
			Object Cell0 = track.get(0);
			setMarbleMethod.invoke(Cell0, marble);
			
			//Cell 5 that the marble will move to in case of five card played
		    Object Cell5 = track.get(5);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell5);
		    
		    
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
		    assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble, getMarbleMethod.invoke(Cell5) );
					
			} catch (Exception e) {
				fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

			}
		}

		@Test(timeout = 1000)
		public void testPlayMethodInClassPlayerWithCardSeven()  {
			
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
			
			
			//Cell 7
		    Object Cell7 = track.get(7);
		    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
		    boolean isTrap = (boolean) isTrapMethod.invoke(Cell7);
		    
		    
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
		    assertEquals("Method play in class Player must call method act", null,  getMarbleMethod.invoke(Cell0) );

			
			// check new position now contains the marble if it wasn't a trap
		    if( !isTrap) 
		    assertEquals("Method play in class Play must call method act on the selected marbles with the selected card and the correct number of steps", marble, getMarbleMethod.invoke(Cell7) );
					
			} catch (Exception e) {
				fail(e.getMessage() + e.getCause()+ " occured while accessing method play in class Player");

			}
		}

		@Test(timeout = 1000)
		public void testPlayPlayerTurnMethodInClassGameWithCardAce(){
			try {
				Object game = createGame();
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
				
				//Cell 1 that the marble will move to
			    Object Cell1 = track.get(1);
			    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			    boolean isTrap = (boolean) isTrapMethod.invoke(Cell1);
			    
				Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
				playPlayerTurn.invoke(game);
				
				// Check position 0 is null
			    assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );
			
				// check new position now contains the marble if it wasn't a trap
			    if(!isTrap) 
			    	assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell1));
			}
			catch(Exception e){
				fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
			}
		}

		@Test(timeout = 1000)
		public void testPlayPlayerTurnMethodInClassGameWithCardJack(){
			try {
				Object game = createGame();
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
				selected_card.set(current_player, hand.get(2));
				
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
				
				//Cell 11 that the marble will move to
			    Object Cell11 = track.get(11);
			    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			    boolean isTrap = (boolean) isTrapMethod.invoke(Cell11);
			    
				Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
				playPlayerTurn.invoke(game);
				
				// Check position 0 is null
			    assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );
			
				// check new position now contains the marble if it wasn't a trap
			    if(!isTrap) 
			    	assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell11) );
			}
			catch(Exception e){
				fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
			}
		}

		@Test(timeout = 1000)
		public void testPlayPlayerTurnMethodInClassGameWithCardSeven(){
			try {
				Object game = createGame();
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
			
				// Add marbles to the track to cell 0
				Object Cell0 = track.get(0);
				setMarbleMethod.invoke(Cell0, marble);
				
				//Cell 7 that the marble will move to
			    Object Cell7 = track.get(7);
			    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			    boolean isTrap = (boolean) isTrapMethod.invoke(Cell7);
			    
				Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
				playPlayerTurn.invoke(game);
				
				// Check position 0 is null
			    assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );
			
				// check new position now contains the marble if it wasn't a trap
			    if(!isTrap) 
			    	assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell7) );
			}
			catch(Exception e){
				fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
			}
		}

		@Test(timeout = 1000)
		public void testPlayPlayerTurnMethodInClassGameWithCardFive(){
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
				
				//Cell 5 that the marble will move to
			    Object Cell5 = track.get(5);
			    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			    boolean isTrap = (boolean) isTrapMethod.invoke(Cell5);
			    
				Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
				playPlayerTurn.invoke(game);
				
				// Check position 0 is null
			    assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );
			
				// check new position now contains the marble if it wasn't a trap
			    if(!isTrap) 
			    	assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell5) );
			}
			catch(Exception e){
				fail(e.getMessage() + " occured while accessing method playPlayerTurn in class Game");
			}
		}

		@Test(timeout = 1000)
		public void testPlayPlayerTurnMethodInClassGameWithCardTen(){
			try {
				Object game = createGame();
//				Object board_object = createBoard(game);
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
				selected_card.set(current_player, hand.get(2));
				
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
				
				//Cell 10 that the marble will move to
			    Object Cell10 = track.get(10);
			    Method isTrapMethod = Class.forName(cellPath).getDeclaredMethod("isTrap");
			    boolean isTrap = (boolean) isTrapMethod.invoke(Cell10);
			    
				Method playPlayerTurn= Class.forName(gamePath).getDeclaredMethod("playPlayerTurn");
				playPlayerTurn.invoke(game);
				
				// Check position 0 is null
			    assertEquals("Method playPlayerTurn in class Game must call method play", null, getMarbleMethod.invoke(Cell0) );
			
				// check new position now contains the marble if it wasn't a trap
			    if(!isTrap) 
			    	assertEquals("Method playPlayerTurn in class Game must call method method play", marble, getMarbleMethod.invoke(Cell10) );
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
	
	private Object createColor(String Color) throws NoSuchFieldException,
	SecurityException, ClassNotFoundException,
	IllegalArgumentException, IllegalAccessException,
	InvocationTargetException, NoSuchMethodException,
	InstantiationException {
Object colour_Object = Enum.valueOf(
		(Class<Enum>) Class.forName(colourPath), Color);
return colour_Object;
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

		ArrayList<Object> colours = new ArrayList<Object>();
		Object colour = null;
		try {
			colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath),
					"RED");
			Object colour2 = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			fail("Class Colour is not found!");
		}

		Constructor<?> boardConstructor = null;
		try {
			boardConstructor = Class.forName(boardPath).getConstructor(
					ArrayList.class, Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			fail("An error occured when acessing the Board constructor!");
		}
		Object board = null;
		try {
			board = boardConstructor.newInstance(colours, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			fail("Error occured when constructing a new Board!");
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

		Object colour;
		ArrayList<Object> colours = new ArrayList<Object>();

		try {
			colour = Enum.valueOf((Class<Enum>) Class.forName(colourPath),
					"RED");
			Object colour2 = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), "BLUE");
			Object colour3 = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), "YELLOW");
			Object colour4 = Enum.valueOf(
					(Class<Enum>) Class.forName(colourPath), "GREEN");
			colours.add(colour);
			colours.add(colour2);
			colours.add(colour3);
			colours.add(colour4);
		} catch (ClassNotFoundException e) {
			fail(e.getClass()
					+ " occurred while creating object of type Colour, Error message: "
					+ e.getMessage());
		}

		Constructor<?> boardConstructor = null;
		try {
			boardConstructor = Class.forName(boardPath).getConstructor(
					ArrayList.class, Class.forName(gameManagerPath));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			fail(e.getClass()
					+ " occurred while creating object of type Board, Error message: "
					+ e.getMessage());
		}

		Object board = null;
		try {
			board = boardConstructor.newInstance(colours, game);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			fail(e.getCause() + " occurred when creating object of type Board ");
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




}
