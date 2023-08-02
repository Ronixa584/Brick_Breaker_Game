package demogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener , KeyListener
{
	private boolean play=false;
	private int score=0;
	private int totalBrick=30;
	private Timer timer;
	private int delay=20;
	private int ballposX=345;
	private int ballposY=525;
	private int ballXdir=-1;
	private int ballYdir=-2;
	private int playerX=300;
	private MapGeneration map;
	
	public GamePlay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer=new Timer(delay,this);
		timer.start();
		
		map=new MapGeneration(5,6);
	}
	public void paint(Graphics g)
	{
		g.setColor(Color.darkGray);
		g.fillRect(3, 3, 692, 592);
		
		//Paddle
		g.setColor(Color.black);
		g.fillRect(playerX, 550, 100, 8);
		
		//bricks
		map.draw((Graphics2D)g);;
		
		//ball
		g.setColor(Color.RED);
		g.fillOval(ballposX, ballposY, 40, 40);
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif",Font.BOLD,30));
		g.drawString("Score :"+score, 520, 30);
		
		//game over
		if(ballposY>=600) {
			play=false;
			ballXdir=ballYdir=0;
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("You Lost The Ball...", 220, 300);
			
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Game Over", 220, 325);
			
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Score : "+score, 220, 350);
			
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Press "+"Enter"+" to Restart..", 220, 375);
			
		}
		   
		if(totalBrick<=0) {
			ballposY=500;
			ballposX=500;
			play=false;
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won...", 220, 325);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Score : "+score, 220, 355);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press "+"Enter"+" to Restart..", 220, 385);
			
		}
	}
	
	private void moveLeft() {
		play=true;
		playerX-=20;
	}
	
	private void moveRight() {
		play=true;
		playerX+=20;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<=0)
				playerX=0;
			else
				moveLeft();
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=500)
				playerX=600;
			else
				moveRight();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				score=0;
				totalBrick=30;
				ballposX=250;
				ballposY=525;
				ballXdir=-1;
				ballYdir=-2;
				playerX=225;
				
				map=new MapGeneration(5,6);
				
			}
		}
		
		repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(play) {
			
			if(ballposX<=0) {
				ballXdir=-ballXdir;
			}
			if(ballposX>=670) {
				ballXdir=-ballXdir;
			}
			if(ballposY<=0) {
				ballYdir=-ballYdir;
			}
			
			Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
			Rectangle paddleRect=new Rectangle(playerX,550,100,8);
			
			if(ballRect.intersects(paddleRect)) {
				ballYdir=-ballYdir;
			}
			
			
			A:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
						int width=map.brickwidth;
						int height=map.brickHeight;
						int brickXpos=80+j*width;
						int brickYpos=50+i*height;
						
						Rectangle brickRect=new Rectangle(brickXpos,brickYpos,width,height);
						if(ballRect.intersects(brickRect))
						{
							map.setBrick(0, i, j);
							totalBrick--;
							score+=5;
							
							if(ballposX+19<=brickXpos || ballposX+1>=brickXpos+width) {
								ballXdir=-ballXdir;
							}
							else {
								ballYdir=-ballYdir;
							}
							
							break A;
						}
						
					}
				}
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			
			}
			
		}
		repaint();
	}

}
