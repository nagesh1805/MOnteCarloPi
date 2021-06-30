import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GLabel;
import acm.graphics.GMath;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class MonteCarloPiAnimation extends GraphicsProgram{
	
	public void init() {
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		//pause(3000);
	//	System.out.println("HI");
		start_simulation=false;
		stop_simulation=false;
		speed=100;
		sPointCount=0;
		cPointCount=0;
		pi_est=0;
		title=new GLabel("***Monte Carlo Simulation for "+"\u03C0"+" value***");
		title.setFont("Times New Roman-BOLD-25");
		title.setColor(Color.WHITE);
		
		start=new JButton("start");
		stop=new JButton("stop");
		
		speedbarlbl=new GLabel("Speed");
		speedbarlbl.setColor(Color.LIGHT_GRAY);
		speedbarlbl.setFont("Times New Roman-BOLD-18");
	
		speedbar=new JSlider(1,20,1);
		speedbar.setMajorTickSpacing(5);
		speedbar.setMinorTickSpacing(1);
		speedbar.setSnapToTicks(true);
		speedbar.setPaintTicks(true);
		//speedbar.setValue(speed);
		speedbar.setSize(200,30);
		speedbar.setBackground(Color.BLACK);
		slider_listener=new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider slider=(JSlider)e.getSource();
				speed= speed_list[slider.getValue()];
			}
		};
		speedbar.addChangeListener(slider_listener);
		
		
		cPointlbl=new GLabel("# Points within Circle: ");
		cPointlbl.setFont("Serif-BOLD-15");
		cPointClbl=new GLabel(""+cPointCount);
		cPointClbl.setFont("Serif-BOLD-15");
		sPointlbl=new GLabel("# Points within Square: ");
		sPointlbl.setFont("Serif-BOLD-15");
		sPointClbl=new GLabel(""+sPointCount);
		sPointClbl.setFont("Serif-BOLD-15");
		pi_estlbl=new GLabel("estimated "+"\u03C0"+" value : ");
		pi_estlbl.setFont("Serif-BOLD-20");
		pi_estClbl=new GLabel(""+pi_est);
		pi_estClbl.setFont("Serif-BOLD-20");
		cPointlbl.setColor(Color.YELLOW);
		cPointClbl.setColor(Color.WHITE);
		
		sPointlbl.setColor(Color.pink);
		sPointClbl.setColor(Color.WHITE);
		
		pi_estlbl.setColor(Color.CYAN);
		pi_estClbl.setColor(Color.WHITE);
		
		background =new GRect(1200,700);
		background.setFilled(true);
		background.setColor(Color.BLACK);
		
		circle = new GOval(2*RADIUS,2*RADIUS);
		circle.setColor(Color.WHITE);
		square = new GRect(2*RADIUS,2*RADIUS);
		square.setColor(Color.WHITE);
		
		
		
		add(background,0,0);
		add(square,X_OFFSET,Y_OFFSET);
		add(circle,X_OFFSET,Y_OFFSET);
		add(speedbarlbl,320,615);
		add(speedbar,380,600);
		add(title,220,30);
		add(sPointlbl,250,80);
		add(sPointClbl,405,80);
		add(cPointlbl,480,80);
		add(cPointClbl,630,80);
		add(pi_estlbl,330,530);
		add(pi_estClbl,495,530);
		add(start,240,600);
		add(stop,595,600);
	    addActionListeners();


	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if (cmd.equals("start")) {
			start_simulation=true;
		}
		if (cmd.equals("stop")) {
			stop_simulation=true;
		}
	}
	
	
	
	public void run() {
		//double speed_limit=1.2;
		//soundClip = getAudioClip(getDocumentBase(),"LathikasTheme.wav");
		
		clipURL= getClass().getResource("LathikasTheme.wav");
		soundClip=Applet.newAudioClip(clipURL);
		soundClip.loop();
		while(!start_simulation) {
			pause(10);
		}
		
		while(!stop_simulation) {
			double x,y;
			int ctrial_count=0;
	
			pause(speed);
			
			x=gen.nextDouble(X_OFFSET,X_OFFSET+2*RADIUS);
			y=gen.nextDouble(Y_OFFSET,Y_OFFSET+2*RADIUS);
			
			GOval dot =new GOval(0.1,0.1);
			dot.setFilled(true);
			dot.setLocation(x,y);
			if(GMath.distance(X_OFFSET+RADIUS, Y_OFFSET+RADIUS, x,y) < RADIUS) {
			    dot.setColor(Color.GREEN);
			    cPointCount++;
			    cPointClbl.setLabel(""+cPointCount);
			    ctrial_count=1;
			  }
			  else {
			    	dot.setColor(Color.RED);
		       }
			   sPointCount++;
			   sPointClbl.setLabel(""+sPointCount);
			
			   add(dot,x,y);
			
		        pi_est=4*((cPointCount*(1.0)/sPointCount));	
		    	pi_estClbl.setLabel(""+pi_est);
			
	    }
		soundClip.stop();
	}
	private AudioClip soundClip;
	private URL clipURL;
	private JSlider speedbar;
	private static boolean start_simulation,stop_simulation;
	private JButton start,stop;
	private ChangeListener slider_listener;
	private GLabel speedbarlbl;
	private GLabel cPointlbl,cPointClbl,sPointlbl,sPointClbl,pi_estlbl,pi_estClbl,title;
	private GRect background,square;
	private GOval circle;
	private int sPointCount, cPointCount;
	private double pi_est;
	private double speed;
	private static double[] speed_list= {1000,500,200,100,50,20,10,5,2,1,0.1,0.01,0.001,0.0001,0.00001,0.000001,0000001,0.00000001,0.000000001,0.0000000001,0.00000000001};
	private static final double RADIUS=200;
	private static final int APPLICATION_WIDTH=900;
	private static final int APPLICATION_HEIGHT=700;
	private static final int X_OFFSET=250;
	private static final int Y_OFFSET=100;
	private RandomGenerator gen=RandomGenerator.getInstance();

}
