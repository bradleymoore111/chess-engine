package main.board.piece;

import java.util.ArrayList;
public class Knight{
	public static ArrayList<XY> getMoves(XY pos){
		ArrayList<XY> moves = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;

		XY[] locs = {new XY(-2,1),new XY(-1,2),new XY(1,2),new XY(2,1),new XY(2,-1),new XY(1,-2),new XY(-1,-2),new XY(-2,-1)};

		for(int i=0;i<locs.length;i++){
			XY p = locs[i];
			if((x+p.x)>=0&&(x+p.x<8)&&(y+p.y>=0)&&(y+p.y<8))
				moves.add(new XY(x+p.x,y+p.y));
		}

		return moves;
	}
}