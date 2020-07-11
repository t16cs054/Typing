public class Player {
	private int player_x;
	private int player_y;
	private  int player_hp;
	
	public Player(int posx, int posy, int hp) {

		this.player_x = posx;
		this.player_y = posy;
		this.player_hp = hp;
	}
	
	public void update(int x, int y){
		player_x += x;
		player_y -= y;
	}

	public int getX() {return player_x;}
	public int getY() {return player_y;}
	public int getPlayerHP() {return player_hp;}

	public boolean insideOfScreen(int w, int h){
		return 0 <= player_x && player_x < w && 0 <= player_y && player_y < h;
	}

	public void action(String event) {
	/*
		//自機の動きy座標
		if( 4 < player_y && event.equals("UP"))player_y -= 2;	
		if( player_y < 42 && event.equals("DOWN"))player_y += 2;
	*/
		//自機の動きx座標
		if( 20 < player_x && event.equals("LEFT"))player_x -= 10;
		if( player_x < 60 && event.equals("RIGHT"))player_x += 10;		
	}

	public void plusPlayerHP(int score){player_hp += score;}
	
	public void subtractionPlayerHP_miss(int substraction_miss){player_hp -= substraction_miss;}

	public void subtractionPlayerHP(int timecounter){player_hp -= timecounter;}
	
}