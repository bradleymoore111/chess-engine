package main.board.piece;

import java.util.ArrayList;
public class Queen{
	public static ArrayList<XY> getMoves(XY pos,int[][] board){
		ArrayList<XY> positions = new ArrayList<XY>();

		positions.addAll(Rook.getMoves(pos,board));
		positions.addAll(Bishop.getMoves(pos,board));

		return positions;
	}
}