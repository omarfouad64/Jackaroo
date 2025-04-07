package engine.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import engine.GameManager;
import model.Colour;

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
