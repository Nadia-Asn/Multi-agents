package water;

import core.Agent;
import core.AgentColor;
import core.Direction;
import core.Environment;
import core.PropertiesReader;
import core.SMA;

public class Fish extends Baby{

	int fishBreedTime;
	
	public Fish(int posX, int posY, AgentColor color, String direction) {
		super(posX, posY, color, direction);
		this.fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
	}
	
	@Override
	public void decide() {
		super.decide();
		fishBreedTime--;

		int oldX = this.getPosX();
		int oldY = this.getPosY();

		findNewPosition();
		makeAction();
		
		if(fishBreedTime == 0 && madeAMove(oldX, oldY)){
			Agent fish = new Fish(oldX, oldY, AgentColor.Vert, Direction.getRandomDirection());
			Environment.getTab()[oldX][oldY] = fish;
			SMA.listAgent.add(fish);

			fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
		} else if(fishBreedTime < 0 ) {
			fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
		}
		setDirection(Direction.getRandomDirection());
	}

}
