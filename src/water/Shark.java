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
	
    public Shark(Position position, Pas pas, Environnement environnement){
        super(position, pas, environnement);
        this.sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
        this.sharkStarveTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkStarveTime"));
    }
    
	public void decide() {
		sharkBreedTime--;
		sharkStarveTime--;

		if(sharkStarveTime > 0) {
			int oldX, oldY;
			List<Fish> listfish = isFishAround();
			if (!listfish.isEmpty()) {
				Random r = new Random();
				Fish poissonMange = listfish.get(r.nextInt(listfish.size()));
				Position pos = poissonMange.getPosition();
				SMA.agents.remove(poissonMange);
				this.environnement.getEnvironnement()[pos.getPositionX()][pos.getPositionY()] = null;

				this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;

				oldX = this.getPosition().getPositionX();
				oldY = this.getPosition().getPositionY();

				this.setPosition(new Position(pos.getPositionX(),  pos.getPositionY()));
				
				this.environnement.getEnvironnement()[pos.getPositionX()][pos.getPositionY()] = this;

                sharkStarveTime =  Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkStarveTime"));
			} else {

				oldX = this.getPosition().getPositionX();
				oldY = this.getPosition().getPositionY();
				this.pas.alea();
				this.environnement.deplacerAgent(this);
			}

			if (sharkBreedTime == 0) {
				Agent shark = new Shark(new Position(oldX, oldY), new Pas(0, 0), this.environnement);
				shark.getPas().alea();
				environnement.addAgent(shark);
				SMA.agents.add(shark);
				sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
			} else if(sharkBreedTime < 0) {
				sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
			}
		} else {
			SMA.agents.remove(this);
			this.environnement.getEnvironnement()[this.getPosition().getPositionX()][this.getPosition().getPositionY()] = null;
		}
		this.pas.alea();
		environnement.notifyChanges();

	}
	
	public List<Fish> isFishAround(){
		int x = 0, y = 0;
		List<Fish> listfish = new ArrayList<Fish>();
		int posX = this.getPosition().getPositionX();
		int posY = this.getPosition().getPositionY();
		
		List<Agent> listAgent = environnement.getVoisins(this.getPosition());
		
		for(Agent agent : listAgent) {
			if(agent.getClass().equals(Fish.class)) {
				listfish.add((Fish)agent);
			}
		}
		
		
//		for(int i=0;i<Direction.dir.length; i++) {
//
//			
//			// Outside the map
//			if(x >= Environment.getTailleX()){
//				x = 0;
//				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
//					x = Environment.getTailleX() - 2;
//				}
//			}
//			if(x == -1){
//				x = Environment.getTailleX() - 1;
//				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
//					x = 1;
//				}
//			}
//			if(x >= Environment.getTailleY()){
//				y = 0;
//				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
//					y = Environment.getTailleY() - 2;
//
//                }
//			}
//			if(y == -1){
//				y = Environment.getTailleY() - 1;
//				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
//					y = 1;
//
//                }
//			}
//			if(x > -1 && x < Environment.getTailleX() && y > -1 && y < Environment.getTailleY())
//				if(Environment.getTab()[x][y] != null && Environment.getTab()[x][y].getClass().equals(Fish.class))
//					l_fish.add((Fish)Environment.getTab()[x][y]);
//		}

		return listfish;
	}

}
