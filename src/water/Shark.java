package water;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



import core.Agent;
import core.Environnement;
import core.Pas;
import core.Position;
import core.PropertiesReader;
import core.SMA;

public class Shark extends Agent{

	int sharkBreedTime, sharkStarveTime;
	boolean isBaby;
	int babyTime;
	
    public Shark(Position position, Pas pas, Environnement environnement){
        super(position, pas, environnement);
        isBaby = true;
        babyTime = 2;
        this.sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
        this.sharkStarveTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkStarveTime"));
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

	public void decide() {
		sharkBreedTime--;
		sharkStarveTime--;
	
		if(isBaby) {
			babyTime--;
		}
		if(babyTime == 0) {
			isBaby = false;
		}
		if(sharkStarveTime > 0) {
			int oldX, oldY;
			oldX = this.getPosition().getPositionX();
			oldY = this.getPosition().getPositionY();
			List<Fish> listfish = isFishAround();
			if (!listfish.isEmpty()) {
				seNourir(listfish);
			} else {
				deplacement();
			}
			reproduction(oldX, oldY);
		} else {
			mourir();
		}
		this.pas.genererPasAleatoire(this);
		environnement.notifyChanges();

	}

	private void mourir() {
		SMA.agents.remove(this);
		this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;
	}

	private void seNourir(List<Fish> listfish) {
		Random r = new Random();
		Fish poissonMange = listfish.get(r.nextInt(listfish.size()));
		Position pos = poissonMange.getPosition();
		SMA.agents.remove(poissonMange);
		this.environnement.getEnvironnement()[pos.getPositionX()][pos.getPositionY()] = null;
		this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;
		this.setPosition(new Position(pos.getPositionX(),  pos.getPositionY()));
		this.environnement.getEnvironnement()[pos.getPositionX()][pos.getPositionY()] = this;
		sharkStarveTime =  Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkStarveTime"));
	}

	private void deplacement() {
		this.pas.genererPasAleatoire(this);
		this.environnement.deplacerAgent(this);
	}

	private void reproduction(int oldX, int oldY) {
		if (sharkBreedTime == 0) {
			if(environnement.caseDispo(oldX, oldY)) {
				Agent shark = new Shark(new Position(oldX, oldY), new Pas(0, 0), this.environnement);
				shark.getPas().genererPasAleatoire(shark);
				environnement.addAgent(shark);
				SMA.agents.add(shark);
				sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
			}
		} else if(sharkBreedTime < 0) {
			sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
		}
	}
	
	public List<Fish> isFishAround(){
		List<Fish> listfish = new ArrayList<Fish>();
		List<Agent> listAgent = environnement.getVoisins(this.getPosition());
		
		for(Agent agent : listAgent) {
			if(agent.getClass().equals(Fish.class)) {
				listfish.add((Fish)agent);
			}
		}
		return listfish;
	}

}
