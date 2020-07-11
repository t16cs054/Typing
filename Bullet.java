public class Bullet {
	private char c;
	private int x;
	private int y;
	private int dx;
	private int dy;
	public Library library;

	public Bullet(char c, int x, int y, int dx, int dy) {
		this.c = c;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		x += dx;
		y -= dy;
	}

	public int getX() {return x;}
	public int getY() {return y;}
	public char getChara(){return c;}
	
	public boolean insideOfScreen(int w, int h){
		return 0 <= x && x < w && 0 <= y && y < h;
	}

	public void setChara() {//弾丸を初期化(空白)に書き換える
		c = ' '; 	
	}

}