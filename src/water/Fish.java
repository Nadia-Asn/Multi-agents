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
		fishBreedTime--;
		
		int oldX = this.getPosition().getPositionX();
		int oldY = this.getPosition().getPositionY();
		
		this.pas.alea();
		this.environnement.deplacerAgent(this);

		if(fishBreedTime == 0){
			Agent fish = new Fish(new Position(oldX, oldY), new Pas(0, 0), this.getEnvironnement());
			fish.getPas().alea();
			environnement.addAgent(fish);
			SMA.agents.add(fish);

			fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
		}
		
	}

}
