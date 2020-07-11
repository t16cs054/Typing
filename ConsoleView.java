import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
/*
import java.util.Random; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;
import static java.util.Comparator.*;
*/
public class ConsoleView{
	private static Model model;
	private Bullet bb;

		private char[][] screen;
		private int width;
		private int height;
		private final static int WIDTH = 80;
		private final static int HEIGHT = 48;
		private boolean write_flag = true;
		private int diff = 0;
		//LinkedHashSet<String>hashSet = new LinkedHashSet<String>(); 
		//ArrayList<String> list = new ArrayList<String>();
		 
		public ConsoleView(Model m){
		  this(m,WIDTH,HEIGHT);    
	    }

		public ConsoleView(Model m, int w, int h){
			model = m;
			width = w;
			height = h;
			screen = new char[height][width];
			clear();
		    }
		
		  // スタート画面
	    public void update_StartScene(){
	        clear();
	        drawDate();
	        drawTitle();
	        drawExplanation();
	        drawSelect();
	        drawInfo();
	        paint();
	    }

	    //レコード画面
	    public void update_RecodeScene(){
	    	clear();
	    	//drawFile();///////////////////////////////////////////////////////////
	    	drawRankingMenu();
	    	drawRankingdata();
	    	drawInfo();
	    	paint();
	    }
	    
	    // リザルト画面
	    public void update_ResultScene(){
	        clear();
	        write_flag = true;
	        drawTotalScoreInResult();
	       // drawFile();/////////////////////////////////////////////////////
	        drawRankingdata();
	        drawSelectInResult();
	        drawInfo();
	        paint();
	    }
	    
	    // 画面上にタイトルを描画
	    public void drawTitle(){
	    	drawString("Fighter&Attacker AXZ", WIDTH/2-10, 10);
	    }
	    //説明文を描画
	    public void drawExplanation(){
	    	drawString("This game is Shooting style typing game.", WIDTH/2-18, 15);
	     	drawString("Problem sentence flows from the top of the screen.", WIDTH/2-24, 16);
	     	drawString("Typed characters are displayed as bullets.", WIDTH/2-20, 17);
	     	drawString("You can move left with the left button,same as right.", WIDTH/2-26, 18);
	     	drawString("Mission is to type matching letters and earn score.", WIDTH/2-25, 19);	
	    	drawString("Your life  will decrease with the passage of time, ", WIDTH/2-24, 20);	
	    	drawString("but life will increase with typing success.", WIDTH/2-21, 21);
	    	drawString("When correct answer of 45 questions or if the life is lost, the game ends.", WIDTH/2-37, 22);
	    	drawString("I wish for GOOD LUCK.", WIDTH/2-10, 23);
	    }
	    // 画面上にセレクトボタンを描画
	    public void drawSelect(){
	    	drawString("GAMESTART : s", WIDTH/2-4, HEIGHT-HEIGHT%10-4);
	      	drawString("RECODE : r", WIDTH/2-4, HEIGHT-HEIGHT%10);
	    	drawString("EXIT : x", WIDTH/2-4, HEIGHT-HEIGHT%11);
	    }
	    
	    //ランキング画面
	    public void drawRankingMenu(){
	    	drawString("BACK : b", WIDTH/2-4, HEIGHT-HEIGHT%10);
	    	drawString("EXIT : x", WIDTH/2-4, HEIGHT-HEIGHT%11);
	    }
	    
	 // 画面上にセレクトボタンを描画
	    public void drawSelectInResult(){
	    	drawString("GAME RE START : s", WIDTH/2-8, HEIGHT-HEIGHT%10);
	    	drawString("TITLE : t", width/2-6, HEIGHT-HEIGHT%10+2);
	    	drawString("EXIT : x", WIDTH/2-6, HEIGHT-HEIGHT%11);
	    }

	    // 画面上にトータルスコアを描画
	    public void drawTotalScoreInResult() {
	    	if(model.getScore() == 45500){drawString("Your Complete!", 10, HEIGHT/2-6);}
	        drawString("Your Score : " + model.getScore() + " / Date : " + getDate(), 10, HEIGHT/2-5);
	        LinkedList<Integer> rankList = model.getRankList();
	        for(int i=0; i<rankList.size(); i++){
	        	if(model.getScore() == rankList.get(i))
	        		drawString("Your Score is No." + (i+1), 10, HEIGHT/2-4);
	        }
	    }
	 	    
	    //ランキングを表示
	    public void drawRankingdata(){//////////////////////////////////////////////////////////////////////////////
	    	drawString("HEIGHT SCORE RANKING",WIDTH/2-10,HEIGHT/2-2);
	    	for(int i=0; i< 5/*model.rankingdata.size()*/; i++){
	    		drawString("No." + (i+1) + " Score:" + model.rankingdata.get(i), WIDTH/2-8,HEIGHT/2+i);
	    	}
	    }
	   ////////////////////////////////////////////////////////////////////////////////////////// 
	    

	    
	    public void drawDate(){//日付等を取得
	    	LocalDateTime d = LocalDateTime.now();
	    	
	    	DateTimeFormatter df = 
					DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分ss秒E曜日");
			String s = df.format(d); //format(d)のdは、LocalDateTime dのd
			drawString(/*s*/String.valueOf(model.getScore()) + "," + s, WIDTH-40, HEIGHT-1);
			
	    }
	    
	    public String getDate(){//日付等を返す
	    	LocalDateTime d = LocalDateTime.now();
	    	
	    	DateTimeFormatter df = 
					DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分ss秒E曜日");
			String s = df.format(d); //format(d)のdは、LocalDateTime dのd
			return s;
	    }
	    
	    //弾丸作成
	    public void drawBullet(){
			for(Bullet b : model.getBullets()){
				put( b.getChara(), b.getX(), b.getY());
				bb = b;
			}
	    }
	    
	    //問題作成
	    public void drawLibrary(){
	    	for(Library l : model.getLibrary()){
				char[] target = l.getString().toCharArray();
				for(int i=0; i<target.length; i++){
					put(target[i], l.getX(), l.getY()-diff);
					diff+=1;
				}
				diff = 0;
			}
	    }   
	    
		//ゲーム画面更新
		public void update_GameScene(){
			
			clear();
			//スコアの表示
			drawTotalScore();	
			//弾丸作成
			drawBullet();
			//問題作成
			drawLibrary();
			//Player情報
			drawPlayerLIFE();
			playerdraw();	
			drawDate();//日付
			drawInfo();//デバッグ用、状態の遷移
			paint();
}
	
	    //自機を描画
	    public void playerdraw(){
	    	put('▲', model.player.getX(), model.player.getY());
			put('▲', model.player.getX()-1, model.player.getY()+1);
			put('▲', model.player.getX()+1, model.player.getY()+1);
		};	
 
		 // 画面上にプレイヤーの体力を描画
	    public void drawPlayerLIFE(){
	        int x = 1;
	        int y = 1;
	        drawString("LIFE : " + model.player.getPlayerHP(), x+2, y+1);
	    }	    

	 // 画面上にトータルスコアを描画
	    public void drawTotalScore(){
	    	int x = 1;
	    	int y = 4;
	    //  String score = String.valueOf(hq.getTotalScore());	  
	        drawString("SCORE : " + model.getScore(), x+2, y+1);
	    }
	//各種ゲームの状態を描画
		private void drawInfo() {
			drawString("model.scene:" + model.getScene(), WIDTH-20, 2);
			drawString("CorrectCheck:" + model.getCorrectCheck(), WIDTH-20, 3);
			drawString("WordLength:" + model.getlength(), WIDTH-20, 4);
			drawString("BulletCounter:" + model.getBulletCounter(), WIDTH-20, 5);
			drawString("BulletCounterPrev:" + model.getBulletCounterPrev(), WIDTH-20, 6);
			drawString("BulletAllCounter:" + model.getBulletAllCounter(), WIDTH-20, 7);
			drawString("MissTyping:" + model.getMissTyping(), WIDTH-20, 8);
			drawString("PlayerInfo:" + model.getPlayerInfo(), WIDTH-20, 9);
			drawString("PlayerInfo:" + model.getStartReadRankingText(), WIDTH-20, 10);
		
		}		
		
		//画面上に文字列を配置
		private void drawString(String s, int x, int y) {
			for(int i=0; i < s.length(); i++)
				put(s.charAt(i),x+i,y);
		}

		//画面上に文字を配置
		public void put(char c, int x, int y){
			if(0 <= x && x < width && 0 <= y && y < height)
				screen[y][x] = c;
		}
		
		//画面クリア
		public void clear(){
			for(int y=0; y < height; y++)
				for(int x=0; x < width; x++)
					screen[y][x] = ' ';
		}

		//画面描画
		public void paint(){
			for(int y=0; y < height; y++){
				System.out.println(screen[y]);
			}	
		}	
		
		 // 画面上に長方形を配置
	    public void drawRect(char c, int x, int y, int w, int h){
	        for (int i = 0; i < w; i++){
	            for (int j=0; j < h; j++){
	                if(i == 0 || i == w-1 || j == 0 || j == h-1){
	                    put(c, x+i, y+j);
	                }
	            }
	        }
	    }
		
		
}

/*    
public void drawFile(){
	readFile();

	//  list.sort(reverseOrder());

	
	
}

public void readFile(){
	drawString("HEIGHT SCORE RANKING",WIDTH/2-10,HEIGHT/2-2);
	int i= 0;
	try {
	      File f = new File("test.txt");
	      BufferedReader br = new BufferedReader(new FileReader(f));

	      String line = br.readLine();
		 
	     
	      while (line != null) {
	    	 list.add(line);  	    	
	    	 drawString("Score"+(i+1)+" : " + list.get(i), WIDTH/2-24, HEIGHT/2+i);
	    	 i++;
	    	 if(i>5)break;
	    	  line = br.readLine();
	      }
    
	      
	      br.close();
	    } catch (IOException e) {
	      System.out.println(e);
	    }
}

public String makeRankingData(){
    return String.valueOf(model.getScore() + " , " + getDate());
}

public void outputFile(){
	
	try{
		BufferedWriter rw = new BufferedWriter(new FileWriter("test.txt", true));

		  if(write_flag == true){	  
		
			  rw.write(makeRankingData());	
			  rw.newLine();
			  write_flag = false;
		  }

		  rw.close();
		}catch(IOException e){
		  System.out.println(e);
		}    	
	
}
*/
