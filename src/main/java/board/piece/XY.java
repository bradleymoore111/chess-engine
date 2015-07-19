package board.piece;

public class XY{
	public int x;
	public int y;
	public XY(int x,int y){
		this.x=x;
		this.y=y;
	}
	public XY(){
		this(0,0);
	}
	public String toString(){
		return "("+x+", "+y+")";
	}
}