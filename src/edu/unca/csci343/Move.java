package edu.unca.csci343;

import java.util.ArrayList;

/**
 * 
 * @author wesleybryant
 * move class has overloaded constructors to create a move dependent on information available. 
 * Each move contains a row (x) col (y) an ArrayList containing all opponent disks that move affected, and numFlips (total number of disks flipped 
 * used in mostDisks(). 
 */
public class Move {
	private int row;
	private int col;
	private ArrayList<Move> disksTurned = null;
	private int numFlips;

	public Move() {
		//this is the constructor for a pass move
		row = -1;
		col = -1;
	}
	
	public ArrayList<Move> getDisksTurned() {
		return disksTurned;
	}

	public void setDisksTurned(ArrayList<Move> disksTurned) {
		this.disksTurned = disksTurned;
	}

	public int getNumFlips() {
		return numFlips;
	}

	public void setNumFlips(int numFlips) {
		this.numFlips = numFlips;
	}

	/**
	 * 
	 * @param moveStr input to be parsed 
	 * parses input based upon white space delimitter - assigns row and column values accordingly. 
	 */
	public Move(String moveStr) {
		//makes a move from user input
		String[] moveString = moveStr.split(" ");
		
		//check length of moveString
		if(moveString.length <=1) {
			//it's a pass move
			row = -1;
			col = -1;
		} else {
			//assign the corresponding array values to their variables
			String colorStart = moveString[0];
			String colString = moveString[1];
			row = Integer.parseInt(moveString[2]);
			col = (colString.toLowerCase().charAt(0) - 'a')+1;
		}
	}
	
	public Move(int r, int c) {
		row = r;
		col = c;
	}
	
	public Move(int r, int c, ArrayList<Move> opponentDisksFlipped) {
		row = r;
		col = c;
		this.disksTurned = opponentDisksFlipped;
	}

	public boolean isPassMove() {
		//returns true if it is a pass move, false otherwise
		return row == -1;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	/*Outputs string representation of a move, translating column value to represent appropriate column label. 
	 * Utilized in updating referee with a selected move. 
	 * 
	 */
	
	@Override 
	public String toString() {
		String stringCol = null;
		switch (col) {
		  case 1:
		    stringCol = "a";
		    break;
		  case 2:
		    stringCol = "b";
		    break;
		  case 3:
		    stringCol = "c";
		    break;
		  case 4:
		    stringCol = "d";
		    break;
		  case 5:
		    stringCol = "e";
		    break;
		  case 6:
		    stringCol = "f";
		    break;
		  case 7:
		    stringCol = "g";
		    break;
		  case 8:
			  stringCol = "h";
		}
		if(row == -1) {
			return "";
		} else {
			return stringCol + " " + row; 
		}
	}
}
