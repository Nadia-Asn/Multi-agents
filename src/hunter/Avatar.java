package hunter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import core.Agent;
import core.Dijsktra;
import core.Environnement;
import core.Pas;
import core.Position;
import core.PropertiesReader;
import core.SMA;


public class Avatar extends Agent implements KeyListener {
    public static int [][] tabDij;

    private int dirX = 0;
    private int dirY = 0;
    private int nbDefender = 0;
    public static int speedAvatar;

    public Avatar(Position position, Pas pas, Environnement environnement){
        super(position, pas, environnement);
        tabDij = new int [Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))]
                              [Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))];
        resetTab();
        speedAvatar = Integer.parseInt(PropertiesReader.getInstance().getProperties("speedAvatar"));
    }

	public void decide(){
        if(environnement.getTicks() % speedAvatar== 0) {
            resetTab();
            Position position = new Position(this.getPosition().getPositionX() + dirX, this.getPosition().getPositionY() + dirY);
           
            wallBounds(position);
            if (deplacementPossible(position)) {
                this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;

                this.setPosition(position);
                if(this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] instanceof Defender) {
                    SMA.agents.remove(this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()]);
                    nbDefender++;
                    if(nbDefender==4) {
                        Agent agent = null;
                        Random r = new Random();
                        int x = -1, y = -1;
                        while (!environnement.caseDispo(x, y)) {
                            x = r.nextInt(this.environnement.getGridSizeX());
                            y = r.nextInt(this.environnement.getGridSizeY());
                        }
                        agent = new Winner(new Position(x, y), null, this.environnement);
                        SMA.agents.add(agent);
                        this.environnement.getEnvironnement()[agent.getPosition().getPositionX()][agent.getPosition().getPositionY()] = agent;
                    }
                }
                if(this.environnement.getEnvironnement()[getPosition().getPositionX()][getPosition().getPositionY()] instanceof Winner) {
                   System.out.println("Gagné");
                }
                this.environnement.getEnvironnement()[getPosition().getPositionX()][getPosition().getPositionY()] = this;
            }
            algoDijkstra();
            sendDijstraToHunter();
            this.environnement.notifyChanges();
        }
    }
	
    public boolean deplacementPossible(Position position) {
    	int posX = this.getPosition().getPositionX();
    	int posY = this.getPosition().getPositionY();
        if(PropertiesReader.getInstance().getProperties("torique").equals("false")) {
            if(posX == 0 && dirX == -1) return false;
            if(posX == this.environnement.getGridSizeX()-1 && dirX == 1) return false;
            if(posY == 0 && dirY == -1) return false;
            if(posY == this.environnement.getGridSizeY()-1 && dirY == 1) return false;
        } else if(this.environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()] instanceof Hunter)
        	return false;
        return this.environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()] == null || this.environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()] instanceof Defender || this.environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()] instanceof Winner;
    }
    /**
     * Move the avatar with arrows
     * @param e
     */
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                dirY = 0;
                dirX = -1;
                break;
            case KeyEvent.VK_RIGHT:
                dirY = 0;
                dirX = 1;
                break;
            case KeyEvent.VK_UP:
                dirX = 0;
                dirY = -1;
                break;
            case KeyEvent.VK_DOWN:
                dirX = 0;
                dirY = 1;
                break;
            case KeyEvent.VK_O:
                if(speedAvatar != 1) {
                    if(speedAvatar - 10 > 0 ) {
                        speedAvatar -= 10;
                    } else {
                        speedAvatar = 1;
                    }
                }  else {
                    speedAvatar = 1;
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e){

    }

    public void keyTyped(KeyEvent e){
    }

    public void algoDijkstra(){
        Dijsktra element = new Dijsktra(this.getPosition().getPositionX(), this.getPosition().getPositionY());
        int distance = 1;
        tabDij[element.getX()][element.getY()] = 0;
        List<Dijsktra> l = new ArrayList<Dijsktra>();
        l.add(element);

        while(!getNeighbour(l).isEmpty()){
            l = getNeighbour(l);
            for (Dijsktra e: l) {
                tabDij[e.getX()][e.getY()] = distance;
            }
            distance++;
        }
    }

    public List<Dijsktra> getNeighbour(List<Dijsktra> listelement){
        List<Dijsktra> listNeighbour = new ArrayList<Dijsktra>();

        for (Dijsktra element: listelement) {
        	for(Pas pas : Pas.getAllPas()) {
                Dijsktra newElement = new Dijsktra(pas.getPasX(), pas.getPasY());
                int newX = element.getX() + newElement.getX();
                int newY = element.getY() + newElement.getY();

                if(PropertiesReader.getInstance().getProperties("torique").equals("true")) {
                	if(newX == -1){
                		newX = environnement.getGridSizeX() - 1;
                	} 
                	if(newY == -1){
                		newY = environnement.getGridSizeY() - 1;
                	} 
                	if(newY == environnement.getGridSizeY()){
                		newY = 0;
                	}
                	if(newX == environnement.getGridSizeX()){
                		newX = 0;
                	}
                }
                
                if(newX > -1 && newX <  environnement.getGridSizeX() && newY > -1 && newY < environnement.getGridSizeY()) {
	                    if (tabDij[newX][newY] == -1) {
	                        newElement.setX(newX);
	                        newElement.setY(newY);
	                        listNeighbour.add(newElement);
	                    }
                }
            }
        }

        return listNeighbour;
    }

    public void resetTab(){
        for (int i = 0; i<tabDij.length; i++){
            for(int j = 0; j<tabDij[i].length; j++)
            	if((environnement.getEnvironnement()[i][j] instanceof Wall || environnement.getEnvironnement()[i][j] instanceof Defender)){
            		tabDij[i][j] = Integer.MAX_VALUE;
            	} else {
            		tabDij[i][j] = -1;
            	}
        }
    }

    public void sendDijstraToHunter(){
        environnement.sendDijktra(tabDij);
    }
}
