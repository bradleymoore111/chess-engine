package board.piece;

import java.util.ArrayList;
public class Piece{
	public static ArrayList<XY> getMoves(int type,XY pos){
		if(type<0)
			type = (int) (-1*type);
		switch(type){
			case 0:
				return new ArrayList<XY>();
			case 1:
				return King.getMoves(pos);
			case 2:
				return Knight.getMoves(pos);
			// case 3:
			// 	return Bishop.getMoves(pos);
			// 	break;
			// case 4:
			// 	return Rook.getMoves(pos);
			// 	break;
			// default:
			// 	return Queen.getMoves(pos);
			// 	break;
		}
		return new ArrayList<XY>();
	}
}