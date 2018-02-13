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
                    if(!(Environment.getTab()[tmp.getPosXTmp()][tmp.getPosYTmp()] instanceof Hunter)){
	                    if (dij[tmp.getPosXTmp()][tmp.getPosYTmp()] < currentValue) {         	
	                    	Environment.getTab()[getPosX()][getPosY()] = null;
	
	                    	setPosX(tmp.getPosXTmp());
	                    	setPosY(tmp.getPosYTmp());
	
	                		Environment.getTab()[getPosX()][getPosY()] = this;
	                		
	                		if(dij[getPosX()][getPosY()] == 0) {
                                JOptionPane.showMessageDialog(null, "GAME OVER");
                                while (true) {
                                    try {
                                        Thread.sleep(Long.MAX_VALUE);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
	                		i = Direction.dir.length+1;
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
