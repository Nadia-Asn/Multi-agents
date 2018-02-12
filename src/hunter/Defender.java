package hunter;

import core.*;

public class Defender extends Agent {
    public int nbCycleAlive;
    public Defender(int posX, int posY, AgentColor color, String direction) {
        super(posX,posY,color,direction);
        nbCycleAlive = 0;
    }
    @Override
    public void decide() {
        if(nbCycleAlive == Integer.parseInt(PropertiesReader.getInstance().getProperties("defenderLife"))) {
            SMA.listAgent.remove(this);
            Environment.getTab()[getPosX()][getPosY()] = null;
        }
        nbCycleAlive ++;
    }
}
