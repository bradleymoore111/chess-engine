package main.evaluation;

public class Evaluation {
	public static int evaluateBoard(int[][] board, boolean sideToMove){ // t=white, f=black

		int score=0;

		/*
		Pawn: 100
		Knight: 290
		Bishop: 310
		Rook: 500
		Queen: 900

		Friendly King 20,000
		Enemy King 10,000
		 */

		// Count up material value
		int multiplier = sideToMove?1:-1;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				score+= multiplier * board[i][j];
			}
		}

		return score;
	}

	public static int valueOfPiece(int type){
		/*
		Pawn: 100
		Knight: 290
		Bishop: 310
		Rook: 500
		Queen: 900

		Friendly King 20,000
		Enemy King 10,000
		 */
		switch(type){
			case 0:
				return 0;
			case -1:
				return 10000;
			case 1:
				return 20000;
			case -2:
			case 2:
				return 290;
			case -3:
			case 3:
				return 310;
			case -4:
			case 4:
				return 500;
			case -5:
			case 5:
				return 900;
			case -6:
			case 6:
				return 100;
		}

		return 0;
	}
}