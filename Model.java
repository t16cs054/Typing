import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Collections;
//import static java.util.Comparator.*;
public class Model {
	private ConsoleView view;
	public Player player;
		
	private final static int WIDTH = 80;//画面の幅
	private final static int HEIGHT = 48;//画面の高さ
	public LinkedList<Bullet> bullets;//入力した文字を保存
	private LinkedList<Library> library;//問題文を格納(原文)
	private ConsoleController controller;
	private String scene;//どの画面を描画するのか
	LinkedList<String> words;//ランダムに問題文が出力されるよう問題文をコピーしたものを格納
	LinkedList<Integer> rankingdata = new LinkedList<>();//スコアをランキングとして生成、表示するときに使用するリンクリスト
	
	private final static Random random = new Random();
	private char[] c;//タイプした文字を格納
	private int score;//スコア
	//double speed_counter = 0.0;

	private int diff;//問題文を縦で流すための差分をつくる
	private boolean flag;//問題文とタイプした文字の判定に使用
	private boolean start_flag = false;//ランキングのテキストを読み込むのはゲーム起動時のみ
	
	private int correct_check;//弾丸として出力された文字が問題文に衝突した回数
	private int word_length;//問題文の長さ
	private int miss_typing;//タイプスミスした回数
	private int bullet_counter;//1つの問題文の中でタイプ成功した文字数
	private int bullet_counter_prev;//1つ前の問題でタイプ成功した文字数
	private int bullet_all_counter;//ゲーム中にタイプ成功した文字数の総和
	
	public Model(){
		ResetModel();
	}
	
    public void ResetModel(){//変数をすべて初期状態に戻す  
    	view = new ConsoleView(this,WIDTH, HEIGHT);
		controller = new ConsoleController(this);
		bullets  = new LinkedList<Bullet>();
		player = new Player(WIDTH/2,HEIGHT-5,500);
		library = new LinkedList<Library>();
		words = new LinkedList<String>();
		scene = "start";
		score = 0;
		diff = 0;
		flag = false;
		
		correct_check = 0;
		word_length = 0;
		bullet_counter = 0;
		bullet_counter_prev = 0;
		bullet_all_counter = 0;
		miss_typing = 0;

		readQuestionfile();//問題文を読み込む
		 
 
	//ランキングをテキストから読み込むのは始めの一度のみ→リスタートした時に二重、三重に書かれるのを防ぐ
		 if(start_flag==false){
			 readFile();
			 start_flag = true;
		 }
		// view.readFile();
	}
    
//getter////////////////////////////////////////////////////////////////////////////////////////////////////	
    public int getX(){return WIDTH;}
    public int getY(){return HEIGHT;}
    
    //ランキング表が読み込めているか
    public boolean getStartReadRankingText(){return start_flag;}
    
   //どの画面なのかnow 
   public String getScene() {return scene;}
    
  //スコアを手に入れる(	画面上に表示する用)
   public int getScore(){return score;}
  	
  //スコアカウンタを手に入れる
  	public int getCorrectCheck(){return correct_check;}
  //問題文の長さを手に入れる
  	public int getlength(){return word_length;}
  	
  //タイプ成功によって加算される点数を加算する回数を測る
  	public int getBulletCounter(){return bullet_counter;}
  	public int getBulletCounterPrev(){return bullet_counter_prev;}
  //タイプ成功数合計
  	public int getBulletAllCounter(){return bullet_all_counter;}
  	
  //タイプミス数を測る
  	public int getMissTyping(){return miss_typing;}

  //自機の残機を獲得
    public int getPlayerInfo(){return player.getPlayerHP();}
  //最終スコアを獲得
   	//public int getTotalScore(){return score;}
    	
  //public String getDate(){return view.getDate();}
    public boolean typeCheck(int check){
    	  if(check == 1){return true;}
    	  else{ return false;}
      }
      

      
  //スコアのランキングを出すためにテキストを読み込んだリンクリストを返す
      public LinkedList<Integer> getRankList(){return rankingdata;}
      
      
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
      //テキストファイルから問題文を読み込んでいる
  public void readQuestionfile(){
	   try{
		      File file = new File("library.txt");
		      

		      if(!file.exists()){
		    	  System.out.println("NOT EXIST");
		    	  return;
		      }
		      
		      FileReader filereader = new FileReader(file);
		      BufferedReader bufferdReader = new BufferedReader(filereader);
		      
		      String str = null;
		      //i = 0;
		      while((str = bufferdReader.readLine()) != null){
		        //System.out.println(str);
		        //words[index] = str;
		        //index++;
		    	  words.add(str);
		      }

		      filereader.close();
		    }catch(IOException e){
		      System.out.println(e);
		    }
   }
    	
      
    	
   	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 public void outputFile(int score){//ランキング用のテキストファイルに書き込む
 	    	
 	    	try{
 	    		BufferedWriter rw = new BufferedWriter(new FileWriter("RankingData.txt"));
 	    		rankingdata.add(new Integer(score));
 	    		Collections.sort(rankingdata,(a,b)->b.intValue()-a.intValue());
 	    		//rankingdata.removeLast();
 	    		for(Integer integer : rankingdata){  
 	    			//rankingdata.sort(reverseOrder());//////////////////////////////////////////////////////
 	    			rw.write(integer.toString());
 	    			rw.newLine();
 	    		}
 	    		rw.close();
 	    	}catch(IOException e){
 	    		System.out.println(e);
 	    	}
 	    	
 }
 
 public void readFile(){//ランキング用のテキストファイルを読み込む
	 try{
	  BufferedReader br = new BufferedReader(new FileReader("RankingData.txt"));
	  String line = null;
	 
      while ((line = br.readLine()) != null){
    	  
    	  rankingdata.add(Integer.parseInt(line));
      }
   
  	Collections.sort(rankingdata,(a,b)->b.intValue()-a.intValue());
      br.close();
	 }catch(FileNotFoundException e){
	    	System.out.println(e);
	    }catch (IOException e) {
	      System.out.println(e);
	 }
	 
 }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    
 
 	
	//場面の変更
	public synchronized void process(String event){
		switch(scene){
		case "start":
			startScene(event);
			break;
		/*case "help":
			helpScene(event);
			break;
		*/case "recode":
			recodeScene(event);
			break;
		case "game":
			gameScene(event);
			break;
		case "result":
			resultScene(event);
			break;
		}
	}

	 // スタート画面を表示
   public synchronized void startScene(String event){
    	if(!(event.equals(""))){
        if (!(event.equals("TIME_ELAPSED"))){    
         char[] type = event.toCharArray();
            switch(type[0]){
                case 's':
                    scene = "game";
                    break;
                case 'r':
                		scene = "recode";
                		break;
                case 'x':
                	System.exit(0);
                    //break;
        }
      }	  	  
            view.update_StartScene();
    }     
 }
	//レコード画面
    public synchronized void recodeScene(String event){
    	if(!(event.equals(""))){
        if (!(event.equals("TIME_ELAPSED"))){    
         char[] type = event.toCharArray();
            switch(type[0]){
                case 'b':
                    scene = "start";
                    break;
                case 'x':
                	System.exit(0);
                    //break;
        }
      }	  	 
            view.update_RecodeScene();
    }
 }
  
    // リザルト画面を表示
    public synchronized void resultScene(String event){
    	if(!(event.equals(""))){
        if (event.equals("TIME_ELAPSED")){
        }else{
         char[] type = event.toCharArray();
            switch(type[0]){
                case 's':
                    ResetModel();
                    scene = "game";
                    break;
                case 't':
                		ResetModel();
                		scene = "start";
                		break;
                case 'x':
                    System.exit(0);
                   // break;
            }
        }
        view.update_ResultScene();
    }        
 }
   	//ゲーム画面を表示
public synchronized void gameScene(String event){
	if(!(event.equals(""))){
		if(event.equals("TIME_ELAPSED")){	
			LinkedList<Bullet> newbs = new LinkedList<Bullet>();
			for(Bullet b : bullets){
				b.update();
				if(b.insideOfScreen(WIDTH,HEIGHT))
					newbs.add(b);
			}
			bullets = newbs;
		}else if(event.equals("MOVE")){
			LinkedList<Library> newls = new LinkedList<Library>();
			for(Library l : library){
				l.update();
				if(l.insideOfScreen(WIDTH,HEIGHT))
					newls.add(l);
			}
			library = newls;
			
			
		}else if(event.equals("MAKE")){
			makeLibrary();
		}else{
			c = event.toCharArray();
			if(!(event.equals("UP")) && !(event.equals("DOWN")) 
						&& !(event.equals("LEFT")) && !(event.equals("RIGHT"))){
				bullets.add(makeBullet());
			}else{
				player.action(event);
			
			}
		
			}
				collision_check();//衝突判定
				end_judgement(player.getPlayerHP());//player_ver

				//end_judgment();//ゲームの終了判定
				view.update_GameScene();//ゲーム画面をアップデート
		}
	}
	// 終了
	public void exitGame(){
	    System.exit(0);
	}

	//プレイヤーのステータスとスコアによる終了判定
public void end_judgement(int hp){
	//if(outscreen == true){player.subtractionPlayerHP();}
	if(hp <= 0 || score == 45500){
		outputFile(score);
		//view.outputFile();////////////////////////////////////////////////////////////////////////////////////////////
		scene = "result";//player(自機)のLIFEがゼロになればリザルト画面に移行
	}	
}

	//衝突チェック
	public void collision_check(){
		for(Library l : getLibrary()){
			word_length = l.getString().toCharArray().length;//出力される文字の長さを獲得
			for(Bullet bb : getBullets()){
				char[] target = l.getString().toCharArray();
				correct_check = 0;
				
				for(int i=0; i<target.length; i++){	
					
					if(bb.getChara() == target[i] && 
							(bb.getY() == l.getY() || bb.getY() == l.getY()+1 || bb.getY() == l.getY() -1) && 
							bb.getX() == l.getX()){
						char[] str1 = l.str.toCharArray();//問題文(String)をchar型の配列に
						//char[] str2 = l.str.substring(i+1,target.length).toCharArray();//問題文(String)をchar型の配列に
	
						bb.setChara();//弾を消してる
						l.str = String.valueOf(str1).substring(i+1);//先頭要素を取り除いた問題文を問題文に書き込み
						flag = true;
						correct_check++;//文字が一致したら+
						bullet_counter++;//正解できた文字数をカウントしている
						bullet_all_counter++;
					}														
					if(bb.getChara() != ' ' && 
							(bb.getY() == l.getY() || bb.getY() == l.getY()+1 || bb.getY() == l.getY() -1) && 
							bb.getX() == l.getX())//タイプミスだと弾が消滅
						{bb.setChara();//弾を消してる
						miss_typing++;//タイプミスした回数をカウント
						player.subtractionPlayerHP_miss(40);
						}
					view.put(target[i], l.getX(), l.getY()-diff);//問題文を上書き
					//view.put( bb.getChara(), bb.getX(), bb.getY());//弾を上書き
							
					if(target.length == correct_check/*1*/&& typeCheck(target.length)){
						bullet_counter_prev = bullet_counter;
						score += 100*bullet_counter;
						player.plusPlayerHP(bullet_counter*30);
						bullet_counter = 0;//問題文の配列が空になったら(/0)スコアを加算
					}			
					diff+=1;
					if(flag == true )break;					
				}
				diff = 0;
				flag = false;
			}				
			player.subtractionPlayerHP(1);
		}
	}

	//実行
	public void run() throws IOException{controller.run();}
	
	//タイプした文字を弾丸として生成
	private Bullet makeBullet(){
		return new Bullet(c[0], player.getX(), player.getY()-1, 0, 1);
		}
	
	//弾丸のリンクリスト
	public LinkedList<Bullet> getBullets(){return bullets;}
	
	public LinkedList<Library> getLibrary() {return library;}
	
	//問題文を読み込む？
	public void makeLibrary(){
		if(words.size() != 0){
			int word_number = random.nextInt(words.size());
			library.add(new Library(words.get(word_number), 0, 0, 1));
		
			words.remove(word_number);
		}		
	}
	
	//実行
	public static void main(String[] args) throws InterruptedException, IOException{
		Model m = new Model();
		m.run();
	}

}
	
	

	