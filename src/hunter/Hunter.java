package hunter;

import core.*;

import javax.swing.*;


public class Hunter extends Agent{

    int [][] dij;
    public Hunter(Position position, Pas pas, Environnement environnement){
        super(position, pas, environnement);
    }

    @Override
    public void decide() {
        if (SMA.nbTicks % GameChanger.speedHunter == 0) {
            if (dij != null) {
                int currentValue = dij[getPosX()][getPosY()];
                for (int i = 0; i < Direction.dir.length; i++) {
                    DijsktraElement element = Direction.getDirection(Direction.dir[i]);
                    Hunter tmp = this;
                    tmp.setPosXTmp(getPosX() + element.getX());
                    tmp.setPosYTmp(getPosY() + element.getY());

                    tmp.wallBounds();
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
