import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.Timer;

public class ConsoleController implements ActionListener {
	private static final int DELAY = 120;
	private Timer timer;
	private Model model;
	private int time1;
	private int time2;

	public ConsoleController(Model model) {
		this.model = model;
		this.timer = new Timer(DELAY,this);
		time1 = 0;
		time2 = 0;
	}

	public void run() throws IOException {
	timer.start();
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String line = null;
	while ((line = reader.readLine()) != null)
		model.process(line);
	}
	
public void actionPerformed(ActionEvent e) {
		time1++;
		time2++;
		if(time1 == 60){
			model.process("MAKE");
			time1 = 0;
		}
		if(time2 == 4){
			model.process("MOVE");
			time2 = 0;
		}
		
			model.process("TIME_ELAPSED");
		}

}
