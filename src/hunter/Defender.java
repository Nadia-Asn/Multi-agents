package hunter;

import core.*;

public class Defender extends Agent {
    public int nbCycleAlive;
    public Defender(Position position, Pas pas, Environnement environnement) {
        super(position, pas, environnement);
        nbCycleAlive = 0;
    }
    @Override
    public void decide() {
        if(nbCycleAlive == Integer.parseInt(PropertiesReader.getInstance().getProperties("defenderLife"))) {
            SMA.agents.remove(this);
            this.getEnvironnement().getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;
        }
        nbCycleAlive ++;
    }
}
