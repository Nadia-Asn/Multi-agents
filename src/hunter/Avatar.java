package hunter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import org.omg.CORBA.Environment;

import core.Agent;
import core.AgentColor;
import core.Dijsktra;
import core.Direction;
import core.Environnement;
import core.Pas;
import core.Position;
import core.PropertiesReader;
import core.SMA;


public class Avatar extends Agent implements KeyListener {
    public static int [][] tabDij;

    private int dirX = 0, dirY = 0, nbDefender =0;
    public static int speedAvatar;

    public Avatar(Position position, Pas pas, Environnement environnement){
        super(position, pas, environnement);
        this.tabDij = new int [Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))]
                              [Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))];
        resetTab();
        speedAvatar = Integer.parseInt(PropertiesReader.getInstance().getProperties("speedAvatar"));
    }

	public void decide(){
        if(environnement.getTicks() % speedAvatar== 0) {
            resetTab();
            int posXtmp = this.getPosition().getPositionX() + dirX;
            int posYtmp = this.getPosition().getPositionY() + dirY;

            wallBounds();
            if (interditDeplacement()) {
                Environment.getTab()[getPosX()][getPosY()] = null;

                setPosX(getPosXTmp());
                setPosY(getPosYTmp());
                if(Environment.getTab()[getPosX()][getPosY()] instanceof Defender) {
                    SMA.listAgent.remove(Environment.getTab()[getPosX()][getPosY()]);
                    nbDefender++;
                    if(nbDefender==4) {
                        AgentColor color = null;
                        Agent agent = null;
                        Random r = new Random();
                        int x = -1, y = -1;
                        while (!Environment.isAGoodPosition(x, y)) {
                            x = r.nextInt(Environment.getTailleX());
                            y = r.nextInt(Environment.getTailleY());
                        }
                        color = AgentColor.Vert;

                        agent = new Winner(x, y, color, null);
                        SMA.listAgent.add(agent);
                        Environment.getTab()[agent.getPosX()][agent.getPosY()] = agent;
                        SMA.listAgent.add(new Winner(x,y,color,null));
                    }
                }
                if(Environment.getTab()[getPosX()][getPosY()] instanceof Winner) {
                    JOptionPane.showMessageDialog(null, "GAGNE");
                    while (true) {
                        try {
                            Thread.sleep(Long.MAX_VALUE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Environment.getTab()[getPosX()][getPosY()] = this;
            }
            doDijkstra();
            sendDijstraToHunter();
        }


    }
    public boolean interditDeplacement() {
        if(PropertiesReader.getInstance().getProperties("torique").equals("false")) {
            if(getPosX() == 0 && dirX == -1) return false;
            if(getPosX() == Environment.getTailleX()-1 && dirX == 1) return false;
            if(getPosY() == 0 && dirY == -1) return false;
            if(getPosY() == Environment.getTailleY()-1 && dirY == 1) return false;
        } else if(Environment.getTab()[getPosXTmp()][getPosYTmp()] instanceof Hunter)
        	return false;
        return Environment.getTab()[getPosXTmp()][getPosYTmp()] == null || Environment.getTab()[getPosXTmp()][getPosYTmp()] instanceof Defender || Environment.getTab()[getPosXTmp()][getPosYTmp()] instanceof Winner;
    }
    /**
     * Move the avatar with arrows
     * @param e
     */
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                dirY = -1;
                dirX = 0;
                break;
            case KeyEvent.VK_RIGHT:
                dirY = 1;
                dirX = 0;
                break;
            case KeyEvent.VK_UP:
                dirX = -1;
                dirY = 0;
                break;
            case KeyEvent.VK_DOWN:
                dirX = 1;
                dirY = 0;
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
            case KeyEvent.VK_P:
                speedAvatar += 10;
                break;
        }
    }

    public void keyReleased(KeyEvent e){

    }

    public void keyTyped(KeyEvent e){
    }

    public void doDijkstra(){
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
            	if((Environment.getTab()[i][j] instanceof Wall || Environment.getTab()[i][j] instanceof Defender)){
            		tabDij[i][j] = Integer.MAX_VALUE;
            	} else {
            		tabDij[i][j] = -1;
            	}
        }
    }

    public void sendDijstraToHunter(){
        Environment.sendDijktra(tabDij);
    }
    
    public void printDij(){
    	System.out.println("-----------------");
    	for (int i = 0; i < tabDij.length; i++) {
			for (int j = 0; j < tabDij[i].length; j++) {
				System.out.print(tabDij[i][j]);
			}
			System.out.println();
		}
    	System.out.println("-----------------");
    }

}
