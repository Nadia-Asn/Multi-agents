package core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import hunter.Avatar;
import hunter.Hunter;
import hunter.Wall;
import particules.Particule;
import water.Fish;
import water.Shark;


public class View extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	protected Environnement env;
	protected float zoom = 1;
	private int frameX;
	private int frameY;


	public View(Environnement env) {
		super();
		this.frameX = env.getGridSizeX() * env.getBoxSize() + env.getGridSizeX();
		this.frameY = env.getGridSizeY() * env.getBoxSize() + env.getGridSizeY();
		this.env = env;
		this.env.addObserver(this);
		this.setBackground(Color.WHITE);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setPreferredSize(new Dimension(frameX,frameY));

	}
	

	public int scaled(int coor) {
		return (int) (coor * env.getBoxSize() * zoom);
	}


	@Override
	public void update(Observable o, Object arg) {
		this.invalidate();
		this.repaint();
		Toolkit.getDefaultToolkit().sync();
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		if (env.showGrille()) {
			for (int i = env.getBoxSize() + 1; i < frameX; i += env.getBoxSize() + 1) {
				g.drawLine(i, 0, i, frameY);
			}
			for (int i = env.getBoxSize() + 1; i < frameY; i += env.getBoxSize() + 1) {
				g.drawLine(0, i, frameX, i);
			}
		}
		for (int i = 0; i<env.getEnvironnement().length; i++) {
			

			for (int j = 0; j < env.getEnvironnement()[i].length; j++) {
				Agent agent = env.getEnvironnement()[i][j];

				if (agent != null) {
					draw(g, i, j, agent);
				}
			}
		}
	}
	private void draw(Graphics g, int x, int y, Agent agent) {
		if(agent.getClass().equals(Particule.class)) {
			drawBallParticules(g, x, y, agent);
		} else if(agent.getClass().equals(Shark.class) || agent.getClass().equals(Fish.class)) {
			drawFishWater(g, x, y, agent);
		} else {
			drawHunterGame(g, x, y, agent);
		}
	}


	private void drawBallParticules(Graphics g, int x, int y, Agent agent) {
		int posX = (env.getBoxSize() + 1) * x;
		int posY = (env.getBoxSize() + 1) * y;
		if(agent.getCollision()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillOval(posX, posY, env.getBoxSize() + 1, env.getBoxSize() + 1);
	}
	
	private void drawFishWater(Graphics g, int x, int y, Agent agent) {
		int posX = (env.getBoxSize() + 1) * x;
		int posY = (env.getBoxSize() + 1) * y;
		if(agent.getClass().equals(Shark.class)) {
			Shark shark = (Shark) agent;
			if(shark.isBaby()) {
				g.setColor(Color.PINK);
			} else {
				g.setColor(Color.RED);
			}
		}
		if(agent.getClass().equals(Fish.class)) {
			Fish fish = (Fish) agent;
			if(fish.isBaby()) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.BLUE);
			}
		}
		g.fillRect(posX, posY, env.getBoxSize()+1, env.getBoxSize()+1);
	}
	
	private void drawHunterGame(Graphics g, int x, int y, Agent agent) {
		int posX = (env.getBoxSize() + 1) * x;
		int posY = (env.getBoxSize() + 1) * y;
		if(agent.getClass().equals(Wall.class)) {
			g.setColor(Color.RED);
			g.fillRect(posX, posY, env.getBoxSize()+1, env.getBoxSize()+1);
		}
		if(agent.getClass().equals(Hunter.class)) {
			g.setColor(Color.YELLOW);
			g.fillOval(posX, posY, env.getBoxSize()+1, env.getBoxSize()+1);
		}
		if(agent.getClass().equals(Avatar.class)) {
			g.setColor(Color.BLACK);
			g.fillOval(posX, posY, env.getBoxSize()+1, env.getBoxSize()+1);
		}
	}
}
