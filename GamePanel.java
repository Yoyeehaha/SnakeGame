import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 750;
	static final int SCREEN_HEIGHT = 750;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int BodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
	  if(running) {	
		
		  g.setColor(Color.red);
		  g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		  for(int i=0;i<BodyParts;i++) {
			  if(i%2==0) {
				  g.setColor(Color.green);
				  g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
			  }else if(i%2==1){
				  g.setColor(Color.blue);
				  g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
			  }
		  }
		    g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
	  }else {
		  gameOver(g);
	  }
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		for(int i=0; i<BodyParts; i++){
		if(x[i]==appleX && y[i]==appleY){
		    newApple();
		   }
		}
	}
	public void move() {
		for(int i=BodyParts;i>0;i--) { 		
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	public void checkApple() {
		if((x[0]==appleX)&&(y[0]==appleY)) {
			BodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = BodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}	
		//check if head touches left border
		if(x[0]<0) {
			running = false;
		}
		//check if head touches right border
		if(x[0]>SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(y[0]<0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0]>SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Your Final Score is: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Your Final Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 95));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GameOver" ,(SCREEN_WIDTH - metrics2.stringWidth("GameOver"))/2, SCREEN_HEIGHT/2);
		if(applesEaten<=10) {
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("Pathetic!!" ,(SCREEN_WIDTH - metrics3.stringWidth("Pathetic!!"))/2, SCREEN_HEIGHT/3);
		}else if(applesEaten<=20) {
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("Good!!" ,(SCREEN_WIDTH - metrics3.stringWidth("Good!!"))/2, SCREEN_HEIGHT/3);
		}else if(applesEaten<=40) {
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("Nice Job!!" ,(SCREEN_WIDTH - metrics3.stringWidth("Nice Job!!"))/2, SCREEN_HEIGHT/3);
		}else {
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("UNBELIVABLE!!" ,(SCREEN_WIDTH - metrics3.stringWidth("UNBELIVABLE!!"))/2, SCREEN_HEIGHT/3);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!= 'R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!= 'L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!= 'D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!= 'U') {
					direction='D';
				}
				break;	
			}
		
		}
	}
	

}
