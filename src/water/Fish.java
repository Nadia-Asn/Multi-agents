package water;


import core.Agent;
import core.Environnement;
import core.Pas;
import core.Position;
import core.PropertiesReader;
import core.SMA;

public class Fish extends Agent{

	int fishBreedTime;
	
	public Fish(Position position, Pas pas, Environnement environnement ) {
		super(position, pas, environnement);
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
