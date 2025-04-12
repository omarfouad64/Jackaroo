package engine.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import engine.GameManager;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;
import model.Colour;
import model.player.Marble;

public class Board implements BoardManager{
	 private final GameManager gameManager;
	 private final ArrayList<Cell> track;
	 private final ArrayList<SafeZone> safeZones;
	 private int splitDistance;
	public Board(ArrayList<Colour> colourOrder, GameManager gameManager) {
		super();
		this.gameManager = gameManager;
		this.track = new ArrayList<>();
		this.safeZones = new ArrayList<>();
		this.splitDistance = 3;
		initializeTrack();
	    assignTrapCell();
	    initializeSafeZones(colourOrder);
	}
	
	private void initializeTrack() {
	    int totalCells = 100;
	    
	    for (int i = 0; i < totalCells; i++) {
	        track.add(new Cell(CellType.NORMAL));
	    }
	    for (int i = 1; i <= 4; i++) {
	        int basePosition = 25 * i;
	        if (basePosition == 100) {
		        track.set(0, new Cell(CellType.BASE));
	        }
	        else {
		        track.set(basePosition, new Cell(CellType.BASE));
	        }
	        int entryPosition = basePosition - 2;
	        track.set(entryPosition, new Cell(CellType.ENTRY));
	    }
	}

	private void assignTrapCell() {
	    Random rand = new Random();
	    int trapsAssigned = 0;
	    List<Cell> validCells = track.stream()
	        .filter(cell -> cell.getCellType() == CellType.NORMAL && !cell.isTrap())
	        .collect(Collectors.toList());

	    while (trapsAssigned < 8 && !validCells.isEmpty()) {
	        int randomIndex = rand.nextInt(validCells.size());
	        Cell selectedCell = validCells.get(randomIndex);
	        selectedCell.setTrap(true);
	        validCells.remove(randomIndex);
	        trapsAssigned++;
	    }
	}
	
    private void initializeSafeZones(ArrayList<Colour> colourOrder) {
        for (int i = 0; i < 4; i++) {
            SafeZone safeZone = new SafeZone(colourOrder.get(i));
            safeZones.add(safeZone);
        }
    }
    
    private ArrayList<Cell> getSafeZone(Colour colour) {
        for (int i = 0; i < 4; i++) {
        	if (safeZones.get(i).getColour() == colour) {
        		return safeZones.get(i).getCells();
        	}
        }
        return new ArrayList<Cell>(4);
    }
    
    private int getPositionInPath(ArrayList<Cell> path, Marble marble) {
    	// index + 1 -> Wait for tests
    	for (int i = 0 ; i < path.size() ; i++) {
    		if (marble == path.get(i).getMarble()) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    private int getBasePosition(Colour colour) {
    	// index + 1 -> Wait for tests
    	for (int i = 0 ; i < track.size() ; i++) {
    		if (colour == track.get(i).getMarble().getColour() && track.get(i).getCellType() == CellType.BASE) {
    			return i;
    		}
    	}
    	return -1;
    }
    private int getEntryPosition(Colour colour) {
    	// index + 1 -> Wait for tests
    	for (int i = 0 ; i < track.size() ; i++) {
    		if (colour == track.get(i).getMarble().getColour() && track.get(i).getCellType() == CellType.ENTRY) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    private ArrayList<Cell> validateSteps(Marble marble, int steps) throws IllegalMovementException {
    	int marblePositionTrack = getPositionInPath(track, marble);
    	int marblePositionsafeZones = getPositionInPath(getSafeZone(marble.getColour()), marble);
		ArrayList<Cell> fullPath = new ArrayList<>(steps + 1);
		fullPath.add(track.get(marblePositionTrack));
    	if (marblePositionTrack == -1 && marblePositionsafeZones == -1) {
    		throw new IllegalMovementException();
    	}
    	else if (marblePositionTrack != -1) {
			int currentMarblePositionTrack = marblePositionTrack;
    		if (getEntryPosition(marble.getColour()) + 4 - marblePositionTrack < steps) {
				throw new IllegalMovementException();
			}
			else if (steps > marblePositionTrack - getEntryPosition(marble.getColour())) {
				int stepsLeft = steps - (marblePositionTrack - getEntryPosition(marble.getColour()));
				getSafeZone(marble.getColour()).get(stepsLeft).setMarble(marble);
			}
			else {
				int currentMarblePositionrack = marblePositionTrack;

				for (int i = 0 ; i < steps ; i++) {
					if (marblePositionTrack + i > 99) {
						fullPath.add(track.get((marblePositionTrack + i) % 100));
					}
					else {
						fullPath.add(track.get(marblePositionTrack + i));
					}
				}
			}
    	}
		else {
			if (steps == 4) {
				throw new IllegalMovementException();
			}
			else if (getEntryPosition(marble.getColour()) + 4 - marblePositionTrack < steps) { 
				throw new IllegalMovementException();
			}
			else {
				for (int i = 0 ; i < steps ; i++) {
					fullPath.add(track.get(marblePositionTrack + i));
				}
			}
		}
		return fullPath;
    }

    private void validatePath(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalMovementException {
		// Take care of -1 case //validateDestroy
		if (destroy) {
			for (int i = 0 ; i < fullPath.size() ; i++) {
				if (fullPath.get(i).getMarble() != null && fullPath.get(i).getCellType() == CellType.SAFE) {
					throw new IllegalMovementException();
				}
			}
		}
		else {
			for (int i = 0 ; i < fullPath.size() ; i++) {
				if (marble.getColour() == fullPath.get(i).getMarble().getColour()) {
					throw new IllegalMovementException();
				}
			}
			for (int i = 0 ; i < fullPath.size() ; i++) {
				int count = 0;
				if (marble.getColour() != fullPath.get(i).getMarble().getColour()) {
					count++;
				}
				if (count > 1) {
					throw new IllegalMovementException();
				}
			}
			if (track.get(getEntryPosition(marble.getColour())).getMarble().getColour() != null) {
				throw new IllegalMovementException();
			}
		}
		for (int i = 0 ; i < fullPath.size() ; i++) {
			if (fullPath.get(i).getCellType() == CellType.BASE) {
				if (fullPath.get(i).getMarble() != null) {
					if (getPositionInPath(track, fullPath.get(i).getMarble()) == getBasePosition(fullPath.get(i).getMarble().getColour())) {
						throw new IllegalMovementException();
					}
				}
			}
		}
	}

	private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalDestroyException {
		fullPath.get(0).setMarble(null);
		if (destroy) {
			for (int i = 0 ; i < fullPath.size() ; i++) {
				if (fullPath.get(i).getMarble().getColour() != marble.getColour()) {
					fullPath.get(i).setMarble(null);
				}
			}
		}
		if (fullPath.get(fullPath.size() - 1).isTrap()) {
			fullPath.get(fullPath.size() - 1).setMarble(null);
			fullPath.get(fullPath.size() - 1).setTrap(false);
			assignTrapCell();
		}
		fullPath.get(fullPath.size() - 1).setMarble(marble);
	}

	private void validateSwap(Marble marble_1, Marble marble_2) throws IllegalSwapException {
		int countSoFar = 0;
		for (int i = 0 ; i < track.size() ; i++) {
			if (track.get(i).getMarble() == marble_1 || track.get(i).getMarble() == marble_2) {
				countSoFar++;
			}
			if (track.get(i).getCellType() == CellType.BASE) {
				if (track.get(i).getMarble() != null) {
					if (getPositionInPath(track, marble_1) == getBasePosition(marble_1.getColour()) || getPositionInPath(track, marble_2) == getBasePosition(marble_2.getColour())) {
						throw new IllegalSwapException();
					}
				}
			}
		}
		if (countSoFar == 0) {
			throw new IllegalSwapException();
		}
	}

	private void validateDestroy(int positionInPath) throws IllegalDestroyException {
		if (positionInPath < 0 || positionInPath >= track.size()) {
			throw new IllegalDestroyException("Invalid position: Out of track bounds.");
		}
		if (track.get(positionInPath).getMarble() == null) {
			throw new IllegalDestroyException();
		}
		if (track.get(positionInPath).getCellType() == CellType.BASE) {
			if (track.get(positionInPath).getMarble() != null) {
				if (getPositionInPath(track, track.get(positionInPath).getMarble()) == getBasePosition(track.get(positionInPath).getMarble().getColour())) {
					throw new IllegalDestroyException();
				}
			}
		}
	}

	 private void validateFielding(Cell occupiedBaseCell) throws CannotFieldException {
		if (occupiedBaseCell.getCellType() == CellType.BASE) {
			if (occupiedBaseCell.getMarble() != null) {
				if (getPositionInPath(track, occupiedBaseCell.getMarble()) == getBasePosition(occupiedBaseCell.getMarble().getColour())) {
					throw new CannotFieldException();
				}
			}
		}
	}

	 private void validateSaving(int positionInSafeZone, int positionOnTrack) throws InvalidMarbleException {
		if (positionInSafeZone >= 0 && positionInSafeZone < safeZones.size()) {
			throw new InvalidMarbleException("Invalid position: Out of safe zone bounds.");
		}
		if (positionOnTrack < 0 || positionOnTrack >= track.size()) {
			throw new InvalidMarbleException("Invalid position: Out of track bounds.");
		}
	}

	public void moveBy(Marble marble, int steps, boolean destroy) throws IllegalMovementException, IllegalDestroyException {
		validateSteps(marble, steps);
		validatePath(marble, validateSteps(marble, steps), destroy);
		move(marble, validateSteps(marble, steps), destroy);
	}

	public void swap(Marble marble_1, Marble marble_2) throws IllegalSwapException {
		validateSwap(marble_1, marble_2);
		int positionInPath_1 = getPositionInPath(track, marble_1);
		int positionInPath_2 = getPositionInPath(track, marble_2);
		track.get(positionInPath_1).setMarble(marble_2);
		track.get(positionInPath_2).setMarble(marble_1);
	}

	public void destroyMarble(Marble marble) throws IllegalDestroyException {
		int positionInPath = getPositionInPath(track, marble);
		validateDestroy(positionInPath);
		track.get(positionInPath).setMarble(null);
		gameManager.sendHome(marble);
	}

	public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
		int baseCellPosition = getBasePosition(marble.getColour());
		if (track.get(baseCellPosition).getMarble() == null) {
			track.get(baseCellPosition).setMarble(marble);
		}
		else {
			validateFielding(track.get(baseCellPosition));
			track.get(baseCellPosition).setMarble(null);
		}
	}

	public void sendToSafe(Marble marble) throws InvalidMarbleException {
		boolean flag = false;
		int positionOnTrack = getPositionInPath(track, marble);
		for (int i = 0 ; i < safeZones.size() ; i++) {
			if (safeZones.get(i).getColour() == marble.getColour()) {
				int j = 0;
				while (safeZones.get(i).getCells().get(j) != null) {
					j++;
				}
				validateSaving(j, positionOnTrack);
				track.get(positionOnTrack).setMarble(null);
				safeZones.get(i).getCells().get(j).setMarble(marble);
				flag = true;
			}
			if (flag) {
				break;
			}
		}
	}

	public ArrayList<Marble> getActionableMarbles() {
		ArrayList<Marble> actionableMarbles = new ArrayList<>();
		for (int i = 0 ; i < track.size() ; i++) {
			if (track.get(i).getMarble() != null) {
				if (track.get(i).getMarble().getColour() == gameManager.getActivePlayerColour()) {
					actionableMarbles.add(track.get(i).getMarble());
				}
			}
		}
		for (int i = 0 ; i < safeZones.size() ; i++) {
			if (safeZones.get(i).getColour() == gameManager.getActivePlayerColour()) {
				for (int j = 0 ; j < safeZones.get(i).getCells().size() ; j++) {
					if (safeZones.get(i).getCells().get(j).getMarble() != null) {
						actionableMarbles.add(safeZones.get(i).getCells().get(j).getMarble());
					}
				}
			}
		}
		return actionableMarbles;
	}

	public int getSplitDistance() {
		return splitDistance;
	}

	public void setSplitDistance(int splitDistance) {
		this.splitDistance = splitDistance;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public ArrayList<Cell> getTrack() {
		return track;
	}

	public ArrayList<SafeZone> getSafeZones() {
		return safeZones;
	}
    
}
