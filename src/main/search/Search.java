package main.search;

import java.util.ArrayList;
import main.board.Board;
import main.board.piece.*;
import main.evaluation.Evaluation;
public class Search{
	public ArrayList<XY> search(Board board, int depth, int pruning){ // Depth is telling current depth to search. Will search until depth = 0. Pruning is how many branches to create
		ArrayList<XY> allPieces = listAllPieces(board.board);
		ArrayList<ArrayList<XY>> allMoves  = new ArrayList<ArrayList<XY>>();// get every move, for each piece
		ArrayList<int> values = new ArrayList<int>();

		for(int i=0;i<allPieces.size();i++){
			allPieces.add(board.getMoves(allPieces.get(i))); // Generate every single possible game
		}

		/* (think box)
		can we add the score value of a specific position to the end, just stored in the .x value, with the .y value being negative or something?
		here we prune the list down by deleting all lists that are too high of a score (we're looking for the lowest possible moves)
			Now wait just one god darn minute
			How about you do another turn, except with the opposite colors turn, before pruning?
			Oh yea, and you need to limit yourself to only doing one side's moves at a time. Right now you're doing every single one. 
		we also append the lists value onto it, as a final XY with the y value being negative one.
			That's actually not so doable. I may have to keep calling eval on each board position -_- it's inefficient, I know. For now, tough shit.
		*/ 
		int[] mins = new int[pruning];
		for(int i=0;i<pruning;i++){
			mins[i] = MAX_VALUE-i;
		}
		int max = pruning-1;

		Evaluation evalor = new Evaluation();

		for(int i=0;i<allMoves.size();i++){
			int score = evalor.evaluate(board.tempBoardAfterMove(allMoves.get(i)));
			if(score<mins[max]){ // evaluating a list of the worst moves... why again? I mean it looks like good code, and it works, but should I do that
				mins[max] = score;
				int tempMax = score;
				for(int i=0;i<pruning;i++){
					if(mins[i]>tempMax){
						max=i;
						tempMax=mins[i];
					}
				}
			}
			// Append score to move. 
		}

		if(depth==0){
			return mins
		}else if(depth>0){
			// generate mins, using command, with depth-- as the command
		}
	}
	public ArrayList<XY> listAllPieces(int[][] position){
		// todo
	}
}