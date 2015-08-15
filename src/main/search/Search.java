package main.search;

import java.util.ArrayList;
import main.board.Board;
import main.board.piece.*;
import main.evaluation.Evaluation;
public class Search{

	public XY search(Board board, int n){ // n = depth
		// call function for each possible move, after making move, evaluate that board using min maxing.
		// move with highest min maxed is the XY to be returned

		// make list of all possible moves for this color

		// make additional list for storing negamax value of each move

		// fill list 

		// find highest value within that list along with its location, return that move

		return new XY(0,0);
	}

	// This function will evaluate a position, including future developments, through basic min-maxing
	// Might change from int to double later
	public int recursiveNegaMaxSearch(Board board, int lowerBound, int upperBound, int n){ // n is depth which is telling current depth to search. Will search until depth = 0.
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
					return score;
				}
				int d = n-1;
				if(d>0){
					// do move
					int v = -recursiveNegaMaxSearch(board.clone(), -upperBound, ((lowerBound>score)?-lowerBound:-score),d);
					// undo move
					if(v>score){
						score = v;
					}
				}
			}
		}

		return score;
		/* (think box)
		can we add the score value of a specific position to the end, just stored in the .x value, with the .y value being negative or something?
		here we prune the list down by deleting all lists that are too high of a score (we're looking for the lowest possible moves)
			Now wait just one god darn minute
			How about you do another turn, except with the opposite colors turn, before pruning?
			Oh yea, and you need to limit yourself to only doing one side's moves at a time. Right now you're doing every single one. 
		we also append the lists value onto it, as a final XY with the y value being negative one.
			That's actually not so doable. I may have to keep calling eval on each board position -_- it's inefficient, I know. For now, tough shit.
		*/ 
	}
	public ArrayList<XY> listAllPieces(int[][] position){
		// todo
		// Make sure it only returns pieces of the color of whos turn it is

		return new ArrayList<XY>();
	}
}