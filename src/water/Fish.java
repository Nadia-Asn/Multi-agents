package water;


import core.Agent;
import core.Environnement;
import core.Pas;
import core.Position;
import core.PropertiesReader;
import core.SMA;

public class Fish extends Agent{

	int fishBreedTime;
	boolean isBaby;
	int babyTime;
	
	public Fish(Position position, Pas pas, Environnement environnement ) {
		super(position, pas, environnement);
		isBaby = true;
		babyTime = 2;
		this.fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
	}
	
	public boolean isBaby() {
		return isBaby;
	}

	public void setBaby(boolean isBaby) {
		this.isBaby = isBaby;
	}

	public int getBabyTime() {
		return babyTime;
	}

	public void setBabyTime(int babyTime) {
		this.babyTime = babyTime;
	}

	@Override
	public void decide() {
		fishBreedTime--;
		if(isBaby) {
			babyTime--;
		}
		if(babyTime == 0) {
			isBaby = false;
		}
		
		int oldX = this.getPosition().getPositionX();
		int oldY = this.getPosition().getPositionY();
		
		this.pas.genererPasAleatoire(this);
		this.environnement.deplacerAgent(this);

		if(fishBreedTime == 0){
			if(environnement.caseDispo(oldX, oldY)) {
				Agent fish = new Fish(new Position(oldX, oldY), new Pas(0, 0), this.getEnvironnement());
				fish.getPas().alea();
				environnement.addAgent(fish);
				SMA.agents.add(fish);
			}
			fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
		} else if(fishBreedTime <0) {
			fishBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("fishBreedTime"));
		}
		this.environnement.notifyChanges();
	}

}
