import java.util.Random;
class Library{
	
	private int x;
	private int y;
	//private int dx;
	private int dy;
	private final static Random random = new Random();
	public String str;
	//double count = 0.0;
	
	public Library(String str,int x, int y,  int dy) {
		this.x =  20+10*random.nextInt(3);
		this.y = y;
		this.dy = dy;
		this.str = str;

	}

public void update() {
	//x -= dx;
	y += dy;
	/*
	if(count == 4){y += dy;count = 0;}
	else y = dy;
	count+= 0.5;
	*/
}


public boolean insideOfScreen(int w, int h){
	return 0 <= x && x < w && 0 <= y && y < h;
}

public int getX() {return x;}
public int getY() {return y;}
public int getd() {return dy;}

public String getString(){return str;}
/*
public LinkedList<String> getWord() {
	//for(int i=0; words != null;)
	//for(int i=0; i<index; i++)
		return words;
}
*/
}
	

