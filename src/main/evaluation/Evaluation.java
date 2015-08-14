package main.evaluation;

public class Evaluation {
	public boolean idk;
	public Evaluation(){
		idk=true;
	}

	public static int evaluateBoard(int[][] board, boolean sideToMove){ // t=white, f=black

		int score=0; // might change to a double eventually, that or just bump the size of everything WAY up.

		/*
		Pawn: 1
		Knight: 3
		Bishop: 3
		Rook: 5
		Queen: 9

		Friendly King 200
		Enemy King 200
		 */

		// Count up material value
		int multiplier = sideToMove?1:-1;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				score+= multiplier * valcueOfPiece(board[i][j]);
			}
		}

		return score;
	}

	public static int valueOfPiece(int type){
		/*
		Pawn: 1
		Knight: 3
		Bishop: 3
		Rook: 5
		Queen: 9

		Friendly King 200
		Enemy King 200
		 */
		switch(type){
			case 0:
				return 0;
			case -1:
				return 200;
			case 1:
				return 400;
			case -2:
			case 2:
				return 3;
			case -3:
			case 3:
				return 3;
			case -4:
			case 4:
				return 5;
			case -5:
			case 5:
				return 9;
			case -6:
			case 6:
				return 1;
		}

		return 0;
	}
}