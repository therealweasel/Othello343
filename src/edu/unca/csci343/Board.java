package edu.unca.csci343;

import java.util.*;

public class Board {


	/**
	 * Basis for Othello - 
	 */

	//-2 out of bounds / -1 adversary piece / 0 open space / 1 friendly piece
	static int [][] board;
	static char [] characters= {'a','b','c','d','e','f','g','h'};

	// Constructor for initial board, 10 x 10 int array with -2 marking out of bounds in rows and columns 0 and 9. 
	public Board () {
		board = new int [10] [10];
		int length = board.length;
		boolean correctInput = false;
		// Set initial board 
		for (int i = 0; i<board.length; i++) {
			for (int j = 0; j<board[i].length; j++) {
				if (i == 0 || i == 9 || j == 0 || j == 9) {
					board[i][j] = -2;
				}
			} 
		}

		//player black
		board[4][5] = 1;
		board[5][4] = 1;

		// initialize, opponent = white 
		board[4][4] = -1;
		board [5][5] = -1;	
	}


	/**
	 * Outputs board accounting for proper formatting for visualization -- rows labels rows 1-8 and columns a-h
	 */
	public static void printBoard () { 
		System.out.print("      ");
		for (char chars : characters) {
			System.out.print(chars + "  ");	
		}
		System.out.println();
		for (int i = 0 ; i <board.length; i++) {
			if (i > 0 && i < 9) {
				System.out.print(i + " ");
			} else {
				System.out.print("  ");
			}
			for (int j =0; j<board[i].length; j++) {
				if (board[i][j] == 0 || board[i][j] == 1) {
					System.out.print(" ");
				}
				System.out.print(board[i][j]+ " ");
			}
			System.out.println();
		}
	}

	/**
	 * Iterates through board and counts pieces of a given value 
	 * @param currentPlayer 
	 * @return num of tiles matching currentPlayer value throughout board. 
	 */
	public int getScore (int currentPlayer) {
		int playerScore = 0;
		for (int i = 0; i<board.length; i++) {
			for (int j = 0; j<board[i].length; j++) {
				if (board[i][j] == currentPlayer) {
					playerScore++;
				}
			}
		}
		return playerScore;
	}

	/**
	 * 
	 * @param move to be checked
	 * @param currentPlayer active player 
	 * @return true if after checking surrounding cells player can find an opponent piece
	 */
	public boolean isAdjacentOpponent(Move move, int currentPlayer) {
		for (int i = move.getRow()-1; i<=move.getRow()+1; i++) {
			for (int j = move.getCol()-1; j<=move.getCol()+1; j++) {
				if (board[i][j] * -1 ==  currentPlayer) {
					return true;
				} 
			}
		}
		return false; 
	}
	/**
	 * 
	 * @param move to be submitted 
	 * @param currentPlayer player value 
	 * checks several conditions and then each possible direction (horizontal,verticals and diagonals) to find 'end cap' of currentPlayer to determine validity
	 * @return false if any condition would nullify move (out of bounds row or column, occupied cell, no adjacent opponent) 
	 * @return true if co conditions are violated and a possible move is verified to have an opponent piece in between it and its designated start position
	 */
	public boolean isValidMove(Move move, int currentPlayer)   {


		if (move.getRow() < 1 || move.getRow() > 8 || move.getCol() < 1 ||  move.getCol() > 8) {
			return false;
		} 
		if (board[move.getRow()][move.getCol()] != 0) {
			return false;
		}
		if (!isAdjacentOpponent(move, currentPlayer)) {
			return false;
		}

		for (int i = move.getRow()-1; i<=move.getRow()+1; i++) {
			for (int j = move.getCol()-1; j<=move.getCol()+1; j++) {

				if (i == -2 || j == -2) {
					//System.out.println("Border");
				}

				if (board[i][j] * -1 ==  currentPlayer) {

					if (i < move.getRow() && j<move.getCol() ) {
						//move upper left 
						int k = i - 1;
						int l = j - 1; 
						while (board[k][l] != 0 && board[k][l] != -2) { 
							if (board[k][l] == currentPlayer) {
								return true; 
							}
							k --;
							l --;
						}
					} 

					if (i <move.getRow() && j == move.getCol()  ) {
						//move up 
						int k = i - 1;

						while (board[k][j] != 0 && board[k][j] != -2) { 
							if (board[k][j] == currentPlayer) {
								return true; 
							}
							k --;
						}
					}

					if (i < move.getRow() && j>move.getCol() ) {
						//move upper right 
						int k = i - 1;
						int l = j + 1; 
						while (board[k][l] != 0 && board[k][l] != -2) { 
							if (board[k][l] == currentPlayer) {
								return true; 
							}
							k --;
							l ++;
						}
					} 

					if (i == move.getRow() && j>move.getCol() ) {
						//move right

						int l = j + 1; 
						while (board[i][l] != 0 && board[i][l] != -2) { 
							if (board[i][l] == currentPlayer) {
								return true; 
							}
							l ++;
						}
					}

					if (i > move.getRow() && j>move.getCol() ) {
						//lower right 
						int k = i + 1;
						int l = j + 1; 
						while (board[k][l] != 0 && board[k][l] != -2) { 
							if (board[k][l] == currentPlayer) {
								return true; 
							}
							k ++;
							l ++;
						}
					}

					if (i > move.getRow() && j == move.getCol() ) {
						//down  
						int k = i + 1;
						while (board[k][j] != 0 && board[k][j] != -2) { 
							if (board[k][j] == currentPlayer) {
								return true; 
							}
							k ++;
						}
					}
					if (i > move.getRow() && j < move.getCol() ) {
						//lower left   
						int k = i + 1;
						int l = j - 1;
						while (board[k][j] != 0 && board[k][j] != -2) { 
							if (board[k][j] == currentPlayer) {
								return true; 
							}
							k ++;
							l --; 
						}
					}

					if (i == move.getRow() && j<move.getCol() ) {
						//move left 
						int l = j - 1; 
						while (board[i][l] != 0 && board[i][l] != -2) { 
							if (board[i][l] == currentPlayer) {
								return true; 
							}
							l --;
						}
					}
				} 
			}
		}
		return false ; 

	}
	/**
	 * 
	 * @param currentPlayer current active player 
	 * @return list of moves that are valid for a given player - later used in mostDisks() method, 
	 * iterates through board, finds open cells - checks surrounding cells and builds list of a given move and sets opponent disks turned 
	 * by that @Move object to later be iterated through when applying changes to board.  
	 */
	public ArrayList<Move> availableMoves(int currentPlayer) {

		System.out.println("C Searching moves for: "+currentPlayer);
		ArrayList<Move> moves = new ArrayList <Move>();
		//ArrayList<ArrayList<Move>> allDisksFlipped = new ArrayList<ArrayList<Move>>(); 
		//System.out.println(moves);
		for (int i =0; i<board.length; i++) {
			for( int j =0; j<board[i].length; j++) { 
				if (board[i][j] == 0) {
					//System.out.println("OPEN POSITION AT I:"+i +" J: "+j);
					Move newMove = new Move(i,j);
					ArrayList<Move> opponentDisks = new ArrayList<Move>();
					//System.out.println("Found player disk at : "+i+" "+j);

					//Search surroundings for opponent disks -- continue that direction until open square is found 
					for (int k = newMove.getRow()-1; k<=newMove.getRow()+1; k++) {
						//System.out.println("k = "+k);
						for (int l = newMove.getCol()-1; l<=newMove.getCol()+1; l++) {
							//System.out.println( "l = "+l);

							if (board[k][l] ==  currentPlayer * -1) {
								//System.out.println("Found opponent disk at position:"+k+" "+l);

								Move opponentPiece = new Move(k,l);


								if (k < newMove.getRow() && l<newMove.getCol() ) {
									//newMove upper left 
									int dx = k - 1;
									int dy = l - 1; 
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[dx][dy] != 0 && board[dx][dy] != -2) { 

										//System.out.println("inside while NW");										
										if (board[dx][dy] == currentPlayer) {
											//System.out.println("Possible move found at "+dx +" "+dy);
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											break;
										}
										Move opponentTurned = new Move(dx,dy);
										tempOpponentDisks.add(opponentTurned);
										dx --;
										dy --;
									}

								} 

								if (k <newMove.getRow() && l == newMove.getCol()  ) {
									//move up 
									int dx = k - 1;
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[dx][l] != 0 && board[dx][l] != -2) { 
										//System.out.println("inside while N ");
										if (board[dx][l] == currentPlayer) {
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(dx,l);
										tempOpponentDisks.add(opponentTurned);
										dx--;
									}

								}

								if (k < newMove.getRow() && l>newMove.getCol() ) {
									//move upper right 
									int dx = k - 1;
									int dy = l + 1; 
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[dx][dy] != 0 && board[dx][dy] != -2) { 
										//System.out.println("inside while NE ");

										if (board[dx][dy] == currentPlayer) {
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(dx,dy);
										tempOpponentDisks.add(opponentTurned);

										dx --;
										dy ++;
									}

								}
								if (k == newMove.getRow() && l>newMove.getCol() ) {
									//move right

									int dy = l + 1; 
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[k][dy] != 0 && board[k][dy] != -2) { 
										//System.out.println("inside while E");		

										if (board[k][dy] == currentPlayer) {
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(k,dy);
										tempOpponentDisks.add(opponentTurned);
										dy ++;
									}

								}

								if (k > newMove.getRow() && l>newMove.getCol() ) {
									//lower right 
									int dx = k + 1;
									int dy = l + 1; 
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[dx][dy] != 0 && board[dx][dy] != -2) { 
										//System.out.println("inside while SE");

										// Increment opponentDisk arrayList? 
										if (board[dx][dy] == currentPlayer) {
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(dx,dy);
										tempOpponentDisks.add(opponentTurned);
										dx ++;
										dy ++;
									}

								}

								if (k > newMove.getRow() && l == newMove.getCol() ) {
									//down  
									int dx = k + 1;
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[dx][l] != 0 && board[dx][l] != -2) { 
										//System.out.println("inside while S");

										if (board[dx][l] == currentPlayer) {
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(dx,l);
										tempOpponentDisks.add(opponentTurned);
										dx ++;
									}


								}

								if (k > newMove.getRow() && l < newMove.getCol() ) {
									//lower left   
									int dx = k + 1;
									int dy = l - 1;
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[dx][dy] != 0 && board[dx][dy] != -2) { 
										//System.out.println("inside while SW");

										if (board[dx][dy] == currentPlayer) {	
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(dx,dy);
										tempOpponentDisks.add(opponentTurned);
										dx ++;
										dy --; 
									}


								}

								if (k == newMove.getRow() && l<newMove.getCol() ) {
									//move left 
									int dy = j - 1; 
									ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
									while (board[k][dy] != 0 && board[k][dy] != -2) { 
										//System.out.println("inside while W");

										if (board[k][dy] == currentPlayer) {
											opponentDisks.add(opponentPiece);
											opponentDisks.addAll(tempOpponentDisks);
											newMove.setDisksTurned(opponentDisks);
											moves.add(newMove);
											//System.out.println("Possible move found at "+dx +" "+l);
											break;
										}
										Move opponentTurned = new Move(k,dy);
										tempOpponentDisks.add(opponentTurned);
										dy -- ;
									}

								}
							}
						}
					}
				}

			}
		}
		return 	moves;
	}


	/**
	 * ****Evaluation function ****
	 * Iterates through a list of generated moves for a player tracking all flips of opponent pieces
	 *  triggered in any given direction - checks each direction of each move, tracking the overall number of bestFlips by comparing 
	 *  tempflips (pieces flipped generated from a single move) to the current highest flips from a given move and reassigns values if needed. 
	 * @param currentPlayer active player 
	 * @param validMoves moves that have been generated for a given player with validity checked in the process
	 * @return move with the most opponent disks affected 
	 */
	public Move mostDisks (int currentPlayer, ArrayList<Move> validMoves) {

		Move bestMove = null; 
		int bestFlips = 0 ;

		for (Move move : validMoves) { 
			int numFlips = 0;
			for (int i = move.getRow()-1; i<=move.getRow()+1; i++) {
				for (int j = move.getCol()-1; j<=move.getCol()+1; j++) {
					if (board[i][j] * -1 ==  currentPlayer) {

						if (i < move.getRow() && j<move.getCol() ) {
							//move upper left 
							int k = i - 1;
							int l = j - 1; 
							int tempFlips = 0;
							while (board[k][l] != 0 && board[k][l] != -2) { 
								tempFlips++;
								if (board[k][l] == currentPlayer) {
									numFlips += tempFlips;
									break;
								}
								k --;
								l --;
							}
						} 

						if (i <move.getRow() && j == move.getCol()  ) {
							//move up 
							int k = i - 1;
							int tempFlips = 0;
							while (board[k][j] != 0 && board[k][j] != -2) { 
								tempFlips++;
								if (board[k][j] == currentPlayer) {
									numFlips += tempFlips;
									break;
								}
								k --;
							}
						}

						if (i < move.getRow() && j>move.getCol() ) {
							//move upper right 
							int k = i - 1;
							int l = j + 1; 
							int tempFlips = 0;
							while (board[k][l] != 0 && board[k][l] != -2) { 
								tempFlips++;
								if (board[k][l] == currentPlayer) {
									numFlips += tempFlips;
									break;
								}
								k --;
								l ++;

							}
						} 

						if (i == move.getRow() && j>move.getCol() ) {
							//move right

							int l = j + 1; 
							int tempFlips = 0;
							while (board[i][l] != 0 && board[i][l] != -2) { 
								tempFlips++;
								if (board[i][l] == currentPlayer) {
									numFlips += tempFlips;
									break;
								}
								l ++;
							}
						}

						if (i > move.getRow() && j>move.getCol() ) {
							//lower right 
							int k = i + 1;
							int l = j + 1; 
							int tempFlips = 0;
							while (board[k][l] != 0 && board[k][l] != -2) { 
								tempFlips++;
								if (board[k][l] == currentPlayer) {
									numFlips += tempFlips;
									break;
								}
								k ++;
								l ++;
							}
						}

						if (i > move.getRow() && j == move.getCol() ) {
							//down  
							int k = i + 1;
							int tempFlips = 0;
							while (board[k][j] != 0 && board[k][j] != -2) { 
								tempFlips++;
								if (board[k][j] == currentPlayer) {
									numFlips += tempFlips;
									break;
								}
								k ++;
							}
						}
						if (i > move.getRow() && j < move.getCol() ) {
							//lower left   
							int k = i + 1;
							int l = j - 1;
							int tempFlips = 0;
							while (board[k][j] != 0 && board[k][j] != -2) { 
								tempFlips++;
								if (board[k][j] == currentPlayer) {
									numFlips += tempFlips;
									break; 
								}
								k ++;
								l --; 
							}
						}

						if (i == move.getRow() && j<move.getCol() ) {
							//move left ƒno 
							int l = j - 1; 
							int tempFlips = 0;
							while (board[i][l] != 0 && board[i][l] != -2) { 
								tempFlips++;
								if (board[i][l] == currentPlayer) {
									numFlips += tempFlips;
									break; 
								}
								l --;
							}
						}
					} 
				}
			} 
			if (numFlips > bestFlips) {
				bestFlips = numFlips;
				bestMove = move;
				bestMove.setNumFlips(bestFlips);
			}
		}
		//System.out.println(bestMove);
		return bestMove; 
	}

	/**
	 * 
	 * @param newMove move to be applied on board object 
	 * @param currentPlayer current active player 
	 * Function altered slightly from availableMoves to deal with input from 
	 * referee - applies a Move through checking similar conditions of availableMoves, builds a list of all opponentDisks affected 
	 * , setDisksTurned() attribute on move for every direction and finally applies changes to board by flipping
	 * Move piece and all pieces effected accordingly at end. 
	 */
	public void applyMove(Move newMove, int currentPlayer) { 

		ArrayList<Move> opponentDisks = new ArrayList<Move>();
		//System.out.println("Found player disk at : "+i+" "+j);
		//System.out.println("C move row: "+newMove.getRow());
		//System.out.println("C move col: " +newMove.getCol());
		//Search surroundings for opponent disks -- continue that direction until open square is found 
		for (int k = newMove.getRow()-1; k<=newMove.getRow()+1; k++) {
			//System.out.println("k = "+k);
			for (int l = newMove.getCol()-1; l<=newMove.getCol()+1; l++) {
				//System.out.println( "l = "+l)
				if (board[k][l] ==  currentPlayer * -1) {
					//System.out.println("C Found opponent disk at position:"+k+" "+l);

					Move opponentPiece = new Move(k,l);


					if (k < newMove.getRow() && l<newMove.getCol() ) {
						//newMove upper left 
						int dx = k - 1;
						int dy = l - 1; 
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[dx][dy] != 0 && board[dx][dy] != -2) { 

							//System.out.println("inside while NW");										
							if (board[dx][dy] == currentPlayer) {
								//System.out.println("Possible move found at "+dx +" "+dy);
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);
								break;
							}
							Move opponentTurned = new Move(dx,dy);
							tempOpponentDisks.add(opponentTurned);
							dx --;
							dy --;
						}

					} 

					if (k <newMove.getRow() && l == newMove.getCol()  ) {
						//move up 
						int dx = k - 1;
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[dx][l] != 0 && board[dx][l] != -2) { 
							//System.out.println("inside while N ");
							if (board[dx][l] == currentPlayer) {
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);

								//System.out.println("Possible move found at "+dx +" "+l);
								break;
							}
							Move opponentTurned = new Move(dx,l);
							tempOpponentDisks.add(opponentTurned);
							dx--;
						}

					}

					if (k < newMove.getRow() && l>newMove.getCol() ) {
						//System.out.println("C upper right");

						//current player move location 
						//System.out.println("C k = "+k + " l = "+l);

						int dx = k - 1;
						int dy = l + 1; 
						int disks = 1;
						//System.out.println("C dx :"+dx +" dy: "+dy);
						//System.out.println("C "+board[dx][dy]);
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[dx][dy] != 0 && board[dx][dy] != -2) { 
							//System.out.println("C inside while NE ");

							if (board[dx][dy] == currentPlayer) {
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);

								//	System.out.println("C player disk found at "+dx +" "+dy);
								//	System.out.println("C "+newMove.getDisksTurned());
								break;
							} 

							disks++;
							Move opponentTurned = new Move(dx,dy);
							tempOpponentDisks.add(opponentTurned);

							dx --;
							dy ++;
						}
						//System.out.println("C opponent disks encountered: "+ disks + " move over, 0 or -2 found");

					}
					if (k == newMove.getRow() && l>newMove.getCol() ) {
						//move right

						int dy = l + 1; 
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[k][dy] != 0 && board[k][dy] != -2) { 
							//System.out.println("inside while E");		

							if (board[k][dy] == currentPlayer) {
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);

								//System.out.println("Possible move found at "+dx +" "+l);
								break;
							}
							Move opponentTurned = new Move(k,dy);
							tempOpponentDisks.add(opponentTurned);
							dy ++;
						}

					}



					if (k > newMove.getRow() && l>newMove.getCol() ) {
						//lower right 
						int dx = k + 1;
						int dy = l + 1; 
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[dx][dy] != 0 && board[dx][dy] != -2) { 
							//System.out.println("inside while SE");

							// Increment opponentDisk arrayList? 
							if (board[dx][dy] == currentPlayer) {
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);


								//System.out.println("Possible move found at "+dx +" "+l);
								break;
							}
							Move opponentTurned = new Move(dx,dy);
							tempOpponentDisks.add(opponentTurned);
							dx ++;
							dy ++;
						}

					}

					if (k > newMove.getRow() && l == newMove.getCol() ) {
						//down  
						int dx = k + 1;
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[dx][l] != 0 && board[dx][l] != -2) { 
							//System.out.println("inside while S");

							if (board[dx][l] == currentPlayer) {
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);

								//System.out.println("Possible move found at "+dx +" "+l);
								break;
							}
							Move opponentTurned = new Move(dx,l);
							tempOpponentDisks.add(opponentTurned);
							dx ++;
						}


					}

					if (k > newMove.getRow() && l < newMove.getCol() ) {
						//lower left   
						//System.out.println("C lower left");

						//current player move location 
						//System.out.println("C k = "+k + " l = "+l);

						int dx = k + 1;
						int dy = l - 1;
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[dx][dy] != 0 && board[dx][dy] != -2) { 
							//System.out.println("C inside while SW");

							if (board[dx][dy] == currentPlayer) {	
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);

								//System.out.println("C move found ending at "+dx +" "+dy);
								break;
							}
							Move opponentTurned = new Move(dx,dy);
							tempOpponentDisks.add(opponentTurned);
							dx ++;
							dy --; 
						}


					}

					if (k == newMove.getRow() && l<newMove.getCol() ) {
						//move left 
						int dy = l - 1; 
						ArrayList<Move> tempOpponentDisks = new ArrayList<Move>();
						while (board[k][dy] != 0 && board[k][dy] != -2) { 
							//System.out.println("inside while W");

							if (board[k][dy] == currentPlayer) {
								opponentDisks.add(opponentPiece);
								opponentDisks.addAll(tempOpponentDisks);
								newMove.setDisksTurned(opponentDisks);

								//System.out.println("Possible move found at "+dx +" "+l);
								break;
							}
							Move opponentTurned = new Move(k,dy);
							tempOpponentDisks.add(opponentTurned);
							dy -- ;
						}

					}
				}
			}
		}
		//String moveOutput = move.toString().replaceAll("\\s", "");
		//System.out.println("Move output test : "+moveOutput);
		//System.out.println("test");
		board[newMove.getRow()][newMove.getCol()] = currentPlayer;
		//System.out.println(move.getDisksTurned());
		for (Move toFlip : newMove.getDisksTurned()) {
			if (board[toFlip.getRow()][toFlip.getCol()] == currentPlayer * -1) {
				board[toFlip.getRow()][toFlip.getCol()] = currentPlayer;
			} 
		}
	}
	/**
	 * Repeatedly run in Drive class (Game) while loop to determine if a move can still be made. 
	 * @return false if moves are available 
	 * @return true if space is full / no valid moves available 
	 */
	public boolean isOver()  {

		boolean isOver = false;
		for (int i =1; i<board.length-1; i++) {
			for (int j=1; j<board[i].length-1; j++) {
				Move test = new Move (i,j);
				if (isValidMove(test, 1) || isValidMove(test, -1) ) {
					// I have a move or opponent has a move 
					//System.out.println("Current player has valid move at position "+i+" "+j);
					return false;
				}
			}
		}
		return true;
	} 
}


