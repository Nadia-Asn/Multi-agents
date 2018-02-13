package hunter;

import core.*;

import javax.swing.*;


public class Hunter extends Agent{

    int [][] dij;
    int speedHunter;
    public Hunter(Position position, Pas pas, Environnement environnement){
        super(position, pas, environnement);
        speedHunter = Integer.parseInt(PropertiesReader.getInstance().getProperties("speedHunter"));
    }

    @Override
    public void decide() {
        if (this.environnement.getTicks() % speedHunter == 0) {
            if (dij != null) {
                int currentValue = dij[this.getPosition().getPositionX()][this.getPosition().getPositionY()];
                for(Pas pas : Pas.getAllPas()) {
                    Dijsktra element = new Dijsktra(pas.getPasX(), pas.getPasY());
                    Position position = new Position(this.getPosition().getPositionX() + element.getX(), this.getPosition().getPositionY() + element.getY());
                    wallBounds(position);
                    if(!(environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()] instanceof Hunter)){
	                    if (dij[position.getPositionX()][position.getPositionY()] < currentValue) {         	
	                    	this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;
	
	                    	this.setPosition(position);
	
	                		this.environnement.getEnvironnement()[this.position.getPositionX()][this.position.getPositionY()] = this;
	                		
	                		if(dij[this.getPosition().getPositionX()][this.getPosition().getPositionY()] == 0) {
                                System.out.println("Perdu");
                            }
	                    }
                    }
                }
            }
        }
    }

    public void setDij(int[][] dij) {
        this.dij = dij;
    }
}
