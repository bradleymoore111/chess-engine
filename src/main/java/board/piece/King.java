package board.piece;

import java.util.ArrayList;
public class King{
	public static ArrayList<XY> getMoves(XY pos){
		ArrayList<XY> moves = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;

		XY[] locs = {new XY(1,1),new XY(0,1),new XY(-1,1),new XY(-1,0),new XY(-1,-1),new XY(0,-1),new XY(1,-1),new XY(1,0)};

		for(int i=0;i<locs.length;i++){
			XY p = locs[i];
			if(x+p.x>=0&&x+p.x<8&&y+p.y>=0&&y+p.y<8)
				moves.add(new XY(x+p.x,y+p.y));
		}

		return moves;
	}
}