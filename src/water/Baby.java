package water;

import core.Agent;
import core.AgentColor;

public abstract class Baby extends Agent {

    private boolean isBaby;
    private int timeOfLife;
    public Baby(int x, int y, AgentColor color, String direction){
        super(x, y, color, direction);
        this.isBaby = true;
        this.timeOfLife = 0;
    }

    public void decide(){
        if(isBaby && timeOfLife ==1) {
            if (this.getClass().equals(Shark.class)) {
                this.setColor(AgentColor.Rouge);
            } else {
                this.setColor(AgentColor.Bleu);
            }
            isBaby = !isBaby;
        }
        timeOfLife++;
    }

}
