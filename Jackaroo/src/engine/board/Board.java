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
		for (int i = 0 ; i < 8 ; i++) {
		    assignTrapCell();
		}
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
	    List<Cell> validCells = track.stream()
	        .filter(cell -> cell.getCellType() == CellType.NORMAL && !cell.isTrap())
	        .collect(Collectors.toList());

	    if (validCells.isEmpty()) return; // No available cells to assign

	    int index = new Random().nextInt(validCells.size());
	    validCells.get(index).setTrap(true);
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
        return null;
    }
    
    private int getPositionInPath(ArrayList<Cell> path, Marble marble) {
    	for (int i = 0 ; i < path.size() ; i++) {
    		if (marble.equals(path.get(i).getMarble())) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    private int getBasePosition(Colour colour) {
       	if (colour == null) {
    		return -1;
    	}
       	for (int i = 0 ; i < safeZones.size(); i++) {
       		if (safeZones.get(i).getColour().equals(colour)) {
       			return i * 25;
       		}
    	}
       	return -1;
    }
    
    private int getEntryPosition(Colour colour) {
       	if (colour == null) {
    		return -1;
    	}
       	for (int i = 0 ; i < safeZones.size(); i++) {
       		if (safeZones.get(i).getColour().equals(colour)) {
       			int baseCellIndex = i * 25;
       			int entryCellIndex = (baseCellIndex - 2 + track.size()) % track.size();
       			return entryCellIndex;
       		}
    	}
       	return -1;
    }  
    
    private ArrayList<Cell> validateSteps(Marble marble, int steps) throws IllegalMovementException {
    	
    	
    	ArrayList<Cell> fullPath = new ArrayList<>();
        Colour marbleColour = marble.getColour();
        
        // 1. Get current positions
        int trackPos = getPositionInPath(track, marble);
        int safePos = getPositionInPath(getSafeZone(marbleColour), marble);
        
        // 2. Validate marble is on board
        if (trackPos == -1 && safePos == -1) {
            throw new IllegalMovementException("Marble is not on board");
        }

        // 3. Handle track movement
        if (trackPos != -1) {
            int entryPos = getEntryPosition(marbleColour);
            boolean isOwnMarble = marbleColour.equals(gameManager.getActivePlayerColour());
            
            // Special case: Four card (backward movement)
            if (steps < 0) {
                for (int i = 0; i <= Math.abs(steps); i++) {
                    int pos = (trackPos - i + track.size()) % track.size();
                    fullPath.add(track.get(pos));
                }
                return fullPath;
            }
            
            // Normal forward movement
            int remainingToEntry = (entryPos - trackPos + track.size()) % track.size();
            
            // Case 1: Movement stays on track
            if (steps <= remainingToEntry || !isOwnMarble) {
                for (int i = 0; i <= steps; i++) {
                    fullPath.add(track.get((trackPos + i) % track.size()));
                }
            } 
            // Case 2: Movement enters safe zone (own marbles only)
            else {
                // Track portion
                for (int i = 0; i <= remainingToEntry; i++) {
                    fullPath.add(track.get((trackPos + i) % track.size()));
                }
                
                // Safe zone portion
                int safeSteps = steps - remainingToEntry;
                if (safeSteps > getSafeZone(marbleColour).size()) {
                    throw new IllegalMovementException("Steps exceed safe zone capacity");
                }
                for (int i = 0; i < safeSteps; i++) {
                    fullPath.add(getSafeZone(marbleColour).get(i));
                }
            }
        }
        // 4. Handle safe zone movement
        else if (safePos != -1) {
            if (steps < 0) {
                throw new IllegalMovementException("Cannot move backwards in safe zone");
            }
            if (safePos + steps >= getSafeZone(marbleColour).size()) {
                throw new IllegalMovementException("Steps exceed safe zone bounds");
            }
            for (int i = 0; i <= steps; i++) {
                fullPath.add(getSafeZone(marbleColour).get(safePos + i));
            }
        }
        
        return fullPath;
    }

    private void validatePath(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalMovementException {
		if (destroy) {
			for (int i = 1 ; i < fullPath.size() ; i++) {
				if (fullPath.get(i).getMarble() != null && fullPath.get(i).getCellType() == CellType.SAFE) {
					throw new IllegalMovementException("Cannot destroy or bypass a marble in the Safe Zone.");
				}
			}
		}
		else {
			for (int i = 1 ; i < fullPath.size() ; i++) {
				if (fullPath.get(i).getMarble() != null) {
					if (gameManager.getActivePlayerColour() == fullPath.get(i).getMarble().getColour()) {
						throw new IllegalMovementException("Cannot bypass or land on your own marble.");
					}
				}
				if (fullPath.get(i).getCellType() == CellType.ENTRY && fullPath.get(i).getMarble() != null) {
					if (i + 1 < fullPath.size() && fullPath.get(i + 1).getCellType() == CellType.SAFE) {
						throw new IllegalMovementException("Cannot land on or bypass a marble in the Safe Zone entry.");
					}
				}
			}
			int count = 0;
			for (int i = 1 ; i < fullPath.size() - 1 ; i++) {
				if (fullPath.get(i).getMarble() != null) {
					if (marble.getColour() != fullPath.get(i).getMarble().getColour()) {
						count++;
					}
				}
				if (count > 1) {
					throw new IllegalMovementException("Path is blocked by more than one marble.");
				}
			}
			if (marble.getColour().equals(gameManager.getActivePlayerColour()) &&
			        track.get(getEntryPosition(marble.getColour())).getMarble() != null) {
			    throw new IllegalMovementException("Safe Zone entry is blocked.");
			}
		}
		for (int i = 1 ; i < fullPath.size() ; i++) {
			if (fullPath.get(i).getCellType() == CellType.BASE) {
				if (fullPath.get(i).getMarble() != null) {
					if (getPositionInPath(track, fullPath.get(i).getMarble()) == getBasePosition(fullPath.get(i).getMarble().getColour())) {
						throw new IllegalMovementException("Cannot move through or land on an opponent's marble in its Base Cell.");
					}
				}
			}
		}
	}

	private void move(Marble marble, ArrayList<Cell> fullPath , boolean destroy) throws IllegalDestroyException {
		if (destroy) {
			for (int i = 1 ; i < fullPath.size() ; i++) {
				if (fullPath.get(i).getMarble() != null) {
					destroyMarble(fullPath.get(i).getMarble());
				}
			}
		}
		if (fullPath.get(fullPath.size() - 1).isTrap()) {
			destroyMarble(marble);
			if (fullPath.get(fullPath.size() - 1).getMarble() != null) {
				destroyMarble(fullPath.get(fullPath.size() - 1).getMarble());
			}
			fullPath.get(fullPath.size() - 1).setTrap(false);
			assignTrapCell();
			return;
		}
		else {
			if (fullPath.get(fullPath.size() - 1).getMarble() != null) {
				destroyMarble(fullPath.get(fullPath.size() - 1).getMarble());
			}
			fullPath.get(0).setMarble(null);
			fullPath.get(fullPath.size() - 1).setMarble(marble);
		}
	}
	
	private void validateSwap(Marble marble_1, Marble marble_2) throws IllegalSwapException {
		int countSoFar = 0;
		for (int i = 0 ; i < track.size() ; i++) {
			if (track.get(i).getMarble() == marble_1 || track.get(i).getMarble() == marble_2) {
				countSoFar++;
			}
			if (track.get(i).getCellType() == CellType.BASE) {
				if (track.get(i).getMarble() != null) {
					if (getPositionInPath(track, marble_1) == getBasePosition(marble_1.getColour()) && !(marble_1.getColour() == gameManager.getActivePlayerColour())
					 || getPositionInPath(track, marble_2) == getBasePosition(marble_2.getColour()) && !(marble_2.getColour() == gameManager.getActivePlayerColour())) {
						throw new IllegalSwapException("Cannot swap with opponent's marble in their Base Cell.");
					}
				}
			}
		}
		if (countSoFar < 2) {
			throw new IllegalSwapException("Both marbles must be on the track to swap.");
		}
		if ((marble_1.getColour() == gameManager.getActivePlayerColour()) && (marble_2.getColour() == gameManager.getActivePlayerColour())) {
			throw new IllegalSwapException("Marbles owned by the same player cannot be swapped");
		}
	}

	private void validateDestroy(int positionInPath) throws IllegalDestroyException {
		if (positionInPath < 0 || positionInPath >= track.size()) {
			throw new IllegalDestroyException("Invalid position: Out of track bounds.");
		}
		if (track.get(positionInPath).getMarble() != null) {
			if (getPositionInPath(track, track.get(positionInPath).getMarble()) == getBasePosition(track.get(positionInPath).getMarble().getColour())) {
				throw new IllegalDestroyException("Destroying a marble that is safe in its Base Cell is not allowed.");
			}
		}
	}

	private void validateFielding(Cell occupiedBaseCell) throws CannotFieldException {
	    if (occupiedBaseCell == null) {
	        throw new CannotFieldException("Base Cell does not exist.");
	    }
		Marble marbleInBaseCell = occupiedBaseCell.getMarble();
		if (marbleInBaseCell != null) {
			if (marbleInBaseCell.getColour().equals(gameManager.getActivePlayerColour())) {
	                throw new CannotFieldException("A marble of the same color is already in the Base Cell.");
	        }
	    }
	}

	private void validateSaving(int positionInSafeZone, int positionOnTrack) throws InvalidMarbleException {
		if (positionInSafeZone >= 0 && positionInSafeZone < safeZones.size()) {
			throw new InvalidMarbleException("Invalid action: Marble is already in the Safe Zone.");
		}
		if (positionOnTrack < 0 || positionOnTrack >= track.size()) {
		    throw new InvalidMarbleException("Invalid action: Marble is not on the track.");
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
		int positionInSafeZone = getPositionInPath(getSafeZone(marble.getColour()), marble);
		if (positionInSafeZone != -1) {
			throw new IllegalDestroyException("Cannot destroy a marble in the Safe Zone.");
		}
		if (positionInPath == -1 && positionInSafeZone == -1) {
			throw new IllegalDestroyException("Cannot destroy a marble in the home Zone.");
		}
	    if (!marble.getColour().equals(gameManager.getActivePlayerColour())) {
	        validateDestroy(positionInPath);
	    }
		gameManager.sendHome(marble);
		track.get(positionInPath).setMarble(null);
	}

	public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
		// 2 questions
		// The test acts as if it doesn't read the setMarble() line
		// the test acts as if it doesn't read the validateFielding() method
		int baseCellPosition = getBasePosition(marble.getColour());
		if (track.get(baseCellPosition).getMarble() == null) {
			track.get(baseCellPosition).setMarble(marble);
		}
		else {
			validateFielding(track.get(baseCellPosition));
			destroyMarble(track.get(baseCellPosition).getMarble());
			track.get(baseCellPosition).setMarble(marble);
		}
	}

	public void sendToSafe(Marble marble) throws InvalidMarbleException {
	    int positionOnTrack = getPositionInPath(track, marble);
	    int positionOnSafeZone = getPositionInPath(getSafeZone(marble.getColour()), marble);
	    validateSaving(positionOnSafeZone, positionOnTrack);
	    List<Cell> cells = getSafeZone(marble.getColour());
		Random r = new Random();
		int randomIndex = r.nextInt(getSafeZone(marble.getColour()).size());
	    while (randomIndex < cells.size() && cells.get(randomIndex).getMarble() != null) {
	    	randomIndex = r.nextInt(getSafeZone(marble.getColour()).size());
	    }
	    track.get(positionOnTrack).setMarble(null);
	    cells.get(randomIndex).setMarble(marble);
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
		for (int i = 0 ; i < getSafeZone(gameManager.getActivePlayerColour()).size() ; i++) {
			if (getSafeZone(gameManager.getActivePlayerColour()).get(i).getMarble() != null) {
				actionableMarbles.add(getSafeZone(gameManager.getActivePlayerColour()).get(i).getMarble());
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
