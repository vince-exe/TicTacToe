package main;

public class Point {
	private int x;
	private int y;
	
	public Point() {};
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int first() {
		return this.x;
	}
	
	public int second() {
		return this.y;
	}
}
