package edu.unca.csci343;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 * Communicates with referee / other agent using scanner (input) and sysout (output) to enact changes on a board object.
 * Runs while game still has valid moves possible in board - all moves are processed through referee and the state of the board is updated accordingly 
 * each move to ensure game won't crash / invalid moves not enacted.
 */
public class Game {

	public static void main(String[] args) {
		// TODO Auto-generat ed method stub	
		
		// possibly rewrite apply move method  
		// find out why
		
		
		System.out.println("C Awaiting referee input");
		Scanner scan = new Scanner(System.in);
		String refInput = scan.nextLine();
		String opponentColor; 
		String currentPlayerColor;
		String myColor = null;
		Board board = null;
		Move passMove = new Move();
		int currentPlayer = 1;
		int myTurn;
		


		if (refInput.equals("I W")) {
			board = new Board();
			System.out.println("R W");
			myColor = "W";

		}

		if (refInput.equals("I B")) {
			board = new Board();
			System.out.println("R B");
			myColor = "B";
		}

		board.printBoard();

		// initialize board and establish who goes first based on referee input 

		if (myColor.toLowerCase().equals("b")) {
			myTurn = 1;
			currentPlayerColor = "B";
		} else {
			myTurn = - 1;
			currentPlayerColor = "W";
		}



		// Begin continuous gameplay // 

		while (!board.isOver()) {


			ArrayList<Move> testMoves = board.availableMoves(currentPlayer);
			//System.out.println(testMoves);
			Move currentMove = new Move();

			if (currentPlayer == myTurn) {

				// pass move // 
				if (testMoves.size() == 0) {
					System.out.println("C NO MOVES - Switching Players ");
					//board.applyMove(passMove,currentPlayer);
					currentPlayer*=-1;
					System.out.println(currentPlayerColor);
					continue;
				}

				currentMove = board.mostDisks(currentPlayer, testMoves);
				//List<Move> uniqueMoves = testMoves.stream().distinct().collect(Collectors.toList());

				//System.out.println("All available moves : "+uniqueMoves);
				//System.out.println("Most disks from moves: "+most + " --> # of flips = " + most.getNumFlips());
				//System.out.println(most.getDisksTurned());
				//System.out.println("Applying move " +most +" as player : "+currentPlayer);

				//Output my move //  
				board.applyMove(currentMove, currentPlayer);
				System.out.println(currentPlayerColor +" "+ currentMove);
				
			} else {

				refInput = scan.nextLine();
				while (refInput.charAt(0) == 'C') {
					refInput = scan.nextLine();
				}



				if (refInput.length() < 5) {
					if (refInput.charAt(0) == 'n') {
						if (board.isOver()) {
							break;
						}
					} 
				} else {
					// is a move 
					
					Move tempMove = new Move (refInput);
					//System.out.println(tempMove);
					System.out.println("C " + testMoves);
					for (Move move : testMoves ) {
						if (tempMove.getCol() == move.getCol() && tempMove.getRow() == move.getRow()) {
							currentMove = move;
							//System.out.println(currentMove + ", "+move);
							board.applyMove(currentMove, currentPlayer);
						}
						
					}
					
					//board.applyMove(currentMove, currentPlayer);
				}

			}


			board.printBoard();

			//Swap players // 
			currentPlayer *= -1;
			//System.out.println("Player:"+currentPlayer +" score: "+ board.getScore(currentPlayer));



		}
				
		// Game over - board full or no moves remaining // 
		System.out.println("n");
		System.out.println("C Player:"+currentPlayer +" score: "+ board.getScore(currentPlayer));
		System.out.println("C Player:"+(currentPlayer *-1) +" score: "+ board.getScore(currentPlayer *-1));

	}

}

	

