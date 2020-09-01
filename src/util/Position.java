package util;

public class Position {
	private int x;
	private int y;
	
	public Position(int x ,int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int new_x) {
		x = new_x;
	}
	public void setY(int new_y) {
		y = new_y;
	}
}
