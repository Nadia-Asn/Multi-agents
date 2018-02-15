package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;

import hunter.Hunter;

public class Environnement extends Observable{
	
	private static Agent[][] environnement;
		
	public Environnement(int x, int y, boolean torus) {
		environnement = new Agent[x][y];
	}


	/**
	 * Ajouter un agent à l'environnement
	 * @param agent à ajouter
	 */
	public void addAgent(Agent agent) {
		environnement[agent.getPosition().getPositionX()][agent.getPosition().getPositionY()] = agent;		
	}
	
	/**
	 * Vérifier si c'est une colision avec une fontière
	 * @param posX 
	 * @param posY
	 * @return true si c'est une collision avec une frontière , false sinon
	 */
	public boolean collisionFrontiere(int posX, int posY) {
		
		if ( !isTorus() && horsGrille(posX, posY)){
			return true;
		}else{
			return false;
		}
	}
	
	public List<Agent> getVoisins(Position position) {
		List<Agent> agents = new ArrayList<Agent>();
		int posX = position.getPositionX();
		int posY = position.getPositionY();
		if(posX -1 >= 0 && posY-1 >= 0) {
			if(environnement[posX-1][posY-1] != null) {
				agents.add(environnement[posX-1][posY-1]);
			}
		} 
		if(posY -1 >= 0) {	
			if(environnement[posX][posY-1] != null) {
				agents.add(environnement[posX][posY-1]);
			}
		} 
		if(posX+1 <= this.getGridSizeX() -1 && posY -1 >=0) {
			if(environnement[posX+1][posY-1] != null) {
				agents.add(environnement[posX+1][posY-1]);
			}
		} 
		if(posX-1 >= 0) {
			if(environnement[posX-1][posY] != null) {
				agents.add(environnement[posX-1][posY]);
			}
		} 
		if(posX+1 <= this.getGridSizeX()-1) {
			if(environnement[posX+1][posY] != null) {
				agents.add(environnement[posX+1][posY]);
			}
		} 
		if(posX-1 >= 0 && posY +1 <= this.getGridSizeY()-1) {
			if(environnement[posX-1][posY+1] != null) {
				agents.add(environnement[posX-1][posY+1]);
			}
		} 
		if(posY+1 <= this.getGridSizeY()-1) {
			if(environnement[posX][posY+1] != null) {
				agents.add(environnement[posX][posY+1]);
			}
		} 
		if(posX+1 <= this.getGridSizeX()-1 && posY+1 <= this.getGridSizeY()-1) {
				if(environnement[posX+1][posY+1] != null) {
					agents.add(environnement[posX+1][posY+1]);
				}
		}
		return agents;
	}


	/**
	 * Vérifier si la case où l'agent veut se déplacer est vide
	 * @param newPosX
	 * @param newPosY
	 * @return true si la case est libre , false sinon
	 */
	public boolean caseLibre(int posX, int posY) {	
			 if(posX < 0) {
				posX = getGridSizeX() - 1;
			}
			if(posY < 0) {
				posY = getGridSizeY() - 1;
			}
			if(posX >= getGridSizeX()) {
				posX = 0;
			}
			if(posY >= getGridSizeY()) {
				posY = 0;
			}
		return null == environnement[posX][posY];
	}
	
	public boolean caseDispo(int posX, int posY){
		if(posX < 0 || posY < 0 || posX > this.getGridSizeX() || posY > this.getGridSizeY())
			return false;
		else if (environnement[posX][posY] != null )
			return false;
		
		return true;
	}
	
	/**
	 * Depassement de la limite de la grille
	 * @param posX
	 * @param posY
	 * @return
	 */
	public boolean horsGrille(int posX, int posY){
		if (posX < 0  || posY < 0 || posX > getGridSizeX() || posY > getGridSizeY() ){
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * Mettre à jour la position de l'agent
	 * @param agent à déplacer
	 */
	public void deplacerAgent(Agent agent) {
		
		System.out.println("deplacement de l'agent : " + agent.getAgent() + " de ["+agent.getPosition().getPositionX() + "," + agent.getPosition().getPositionY()+"] "
				+ "à  [" + agent.getPosition().getPositionX() + agent.getPas().getPasX()+ ","+ agent.getPosition().getPositionY()+agent.getPas().getPasY() +" ]");

		environnement[agent.getPosition().getPositionX()][agent.getPosition().getPositionY()] = null;
		
		do {
			agent.update();
		} while(!caseLibre(agent.getPosition().getPositionX(), agent.getPosition().getPositionY()));

		environnement[agent.getPosition().getPositionX()][agent.getPosition().getPositionY()] = agent;

		notifyChanges();
	}
	
	/**
	 * Send dijkstra to hunter
	 * @param dij
	 */
	public void sendDijktra(int [][] dij){
		for (int i = 0; i< environnement.length; i++) {
			for (int j = 0; j < environnement[i].length; j++)
				if (environnement[i][j] instanceof Hunter) {
					Hunter h = (Hunter) environnement[i][j];
					h.setDij(dij);
				}
		}
	}




	/**
	 * Getter de l'attribut {@link Environnement#environnement}
	 * @return environnement
	 */
	public Agent[][] getEnvironnement() {
		return environnement;
	}


	/**
	 * Setter de l'attribut {@link Environnement#environnement}
	 * @param environnement l'attribut {@link Environnement#environnement} à setter
	 */
	public void setEnvironnement(Agent[][] penvironnement) {
		environnement = penvironnement;
	}
	
	
	public JFrame createWindow(String name) {
		JFrame fenetre = new JFrame();
		fenetre.setTitle(name);
		fenetre.setSize((getBoxSize()*getGridSizeX()+2 * getBoxSize()), getBoxSize()*getGridSizeY()+getBoxSize()+2 * getBoxSize());
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setBackground(Color.WHITE);
		fenetre.setVisible(true);

		return fenetre;
	}


	/** 
	 * Notifier les changement à la vue 
	 * 
	 */
	public void notifyChanges() {
		if(getTicks() % getRefresh() ==0){
			setChanged();
			notifyObservers();
			
		}

		
	}
	
	
	public int getGridSizeX(){
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"));	
	}
	
	public int getGridSizeY(){

		return Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"));	
	}
	
	public boolean isTorus(){

		return Boolean.valueOf(PropertiesReader.getInstance().getProperties("torique"));
	}
	
	
	public boolean showGrille(){
		return Boolean.valueOf(PropertiesReader.getInstance().getProperties("grid"));	
	}
	
	public int getBoxSize(){

		return Integer.parseInt(PropertiesReader.getInstance().getProperties("boxSize"));	
	}
	
	public int getCanvasSizeX(){
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("canvasSizeX"));	
	}
	
	public int getCanvasSizeY(){
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("canvasSizeY"));	
	}
	
	public int getDelay(){
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("delay"));	
	}
	
	public int getNbParticle(){
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("nbParticles"));	
	}	
	
	public int getRefresh() {
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("refresh"));
	}
	
	public int getTicks() {
		return Integer.parseInt(PropertiesReader.getInstance().getProperties("nbTicks"));
	}
}
