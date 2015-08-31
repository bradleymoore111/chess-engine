package main.search;

import java.util.ArrayList;
import main.board.Board;
import main.board.piece.*;
import main.evaluation.Evaluation;
public class Search{

	// call function for each possible move, after making move, evaluate that board using min maxing.
	// move with highest min maxed is the XY to be returned, as a 2 entry list
	public static ArrayList<XY> search(Board board, int n){ // n = depth

		// make list of all possible moves for this color
		ArrayList<XY> allPieces = listAllPieces(board.board);
		ArrayList<ArrayList<XY>> allMoves = new ArrayList<ArrayList<XY>>();

		for(int i=0;i<allPieces.size();i++){
			allMoves.add(board.getMoves(allPieces.get(i))); // Generate every single possible move
		}

		// make additional list for storing negamax value of each move
		ArrayList<ArrayList<Integer>> scores = new ArrayList<ArrayList<Integer>>();

		// fill list 
		for(int i=0;i<allMoves.size();i++){
			scores.add(new ArrayList<Integer>());
			for(int j=0;j<allMoves.get(i).size();j++){
				board.move(allPieces.get(i),allMoves.get(i).get(j));
				scores.get(i).add(recursiveNegaMaxSearch(board.clone(),-8000,8000,n));
				board.undoMove();
			}
		}

		// find highest value within that list along with its location, return that move
		int max = -8000;
		int locMaxI = 0;
		int locMaxJ = 0;

		for(int i=0;i<scores.size();i++){
			for(int j=0;j<scores.get(i).size();j++){
				if(scores.get(i).get(j)>max){
					locMaxI = i;
					locMaxJ = j;
					max = scores.get(i).get(j);
				}
			}
		}

		ArrayList<XY> idk = new ArrayList<XY>();
		idk.add(allPieces.get(locMaxI));
		idk.add(allMoves.get(locMaxI).get(locMaxJ));
		return idk;
	}

	// This function will evaluate a position, including future developments, through basic min-maxing
	// Might change from int to double later
	public static int recursiveNegaMaxSearch(Board board, int lowerBound, int upperBound, int n){ // n is depth which is telling current depth to search. Will search until depth = 0.
		Evaluation evalor = new Evaluation();

		ArrayList<XY> allPieces = listAllPieces(board.board); // returns every piece on the board of the color whos turn it is
		ArrayList<ArrayList<XY>> allMoves  = new ArrayList<ArrayList<XY>>();// get every move, for each piece
		
		for(int i=0;i<allPieces.size();i++){
			allMoves.add(board.getMoves(allPieces.get(i))); // Generate every single possible move
		}

		int score = n>1 ? -8000 : evalor.evaluateBoard(board.board); // score so far

		for(int i=0;i<allMoves.size();i++){
			for(int j=0;j<allMoves.get(i).size();j++){
				if(score>=upperBound){
					return score-1;
				}
				int d = n-1;
				if(d>0){
					board.move(allPieces.get(i),allMoves.get(i).get(j));
					int v = -recursiveNegaMaxSearch(board.clone(), -upperBound, ((lowerBound>score)?-lowerBound:-score),d);
					board.undoMove();
					if(v>score){
						score = v;
					}
				}
			}
		}

		return score-1;		 
	}
	public static ArrayList<XY> listAllPieces(int[][] board){
		// Make sure it only returns pieces of the color of whos turn it is
		boolean turn = ((board[8][0]==1)?true:false);

		ArrayList<XY> pieces = new ArrayList<XY>();

		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(board[i][j] == 0){
					continue;
				}
				if(turn == (board[i][j]>0)){
					pieces.add(new XY(i,j));
				}
			}
		}

		return pieces;
	}

	/*

	public static int alphaBetaMax (int alpha,int beta,int depthleft){

		if (depthleft == 0): return evaluate()
		for (all moves){
			// do move
			int score = alphaBetaMin(alpha, beta, depthleft-1); // need to pass in board, unless history is kept here
			if( score >= beta):
				return beta //fail hard beta-cutoff
			if( score > alpha ):
				alpha = score //alpha acts like max in MiniMax
			// undo move?
		}
		return alpha
	}

	public static int alphaBetaMin(int alpha,int beta,int depthleft){
		if ( depthleft == 0): return -evaluate()
		for (all moves){
			int score = alphaBetaMax(alpha, beta, depthleft-1); // would it be better to keep move history and undo it through here?
			if( score <= alpha )
				return alpha; //fail hard alpha-cutoff
			if( score < beta )
				beta = score; //beta acts like min in MiniMax
			// undo move
			// Or would it be better for the board to handle undoing of a move. Which we already have
		}
		return beta;
	}

	score = alphaBetaMax(-800000, +800000, 5)
	print score

	 */
}