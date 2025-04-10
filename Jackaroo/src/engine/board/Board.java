package engine.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import engine.GameManager;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
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
	        validCells.remove(randomIndex); // Remove to prevent re-selection
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
		// Take care of -1 case
		if (fullPath.size() - 1 == 13) {
			destroy = true;
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
		if (fullPath.size() - 1 == 13) {
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
