package water;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Agent;
import core.AgentColor;
import core.Direction;
import core.Environment;
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
			List<Fish> l_fish = isAFishAround();
			if (!l_fish.isEmpty()) {
				// eat the fish
				Random r = new Random();
				Fish toRemove = l_fish.get(r.nextInt(l_fish.size()));
				SMA.listAgent.remove(toRemove);
				Environment.getTab()[toRemove.getPosX()][toRemove.getPosY()] = null;


				// move the shark
				Environment.getTab()[getPosX()][getPosY()] = null;

				oldX = this.getPosX();
				oldY = this.getPosY();

				setPosX(toRemove.getPosX());
				setPosY(toRemove.getPosY());

				Environment.getTab()[getPosX()][getPosY()] = this;

                sharkStarveTime =  Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkStarveTime"));
			} else {

				oldX = this.getPosX();
				oldY = this.getPosY();

				findNewPosition();
				makeAction();
			}

			if (sharkBreedTime == 0 && madeAMove(oldX, oldY)) {
				Agent shark = new Shark(oldX, oldY, AgentColor.Rose, Direction.getRandomDirection());
				Environment.getTab()[oldX][oldY] = shark;
				SMA.listAgent.add(shark);
				sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));

			} else if(sharkBreedTime == 0){
				sharkBreedTime = Integer.parseInt(PropertiesReader.getInstance().getProperties("sharkBreedTime"));
			}
		} else {
			SMA.listAgent.remove(this);
			Environment.getTab()[getPosX()][getPosY()] = null;
		}
		setDirection(Direction.getRandomDirection());

	}
	
	/*
	 * 
	 * @return
	 */
	public List<Fish> isAFishAround(){
		int x = 0, y = 0;
		List<Fish> l_fish = new ArrayList<Fish>();
		for(int i=0;i<Direction.dir.length; i++) {

			switch (Direction.dir[i]) {
				case "N":
					x = getPosX() - 1;
					y = getPosY();
					break;
				case "S":
					x = getPosX() + 1;
					y = getPosY();
					break;
				case "W":
					x = getPosX();
					y = getPosY() - 1;
					break;
				case "E":
					x = getPosX();
					y = getPosY() + 1;
					break;
				case "NW":
					x = getPosX() - 1;
					y = getPosY() - 1;
					break;
				case "NE":
					x = getPosX() - 1;
					y = getPosY() + 1;
					break;
				case "SW":
					x = getPosX() + 1;
					y = getPosY() - 1;
					break;
				case "SE":
					x = getPosX() + 1;
					y = getPosY() + 1;
					break;
			}
			// Outside the map
			if(x >= Environment.getTailleX()){
				x = 0;
				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
					x = Environment.getTailleX() - 2;
				}
			}
			if(x == -1){
				x = Environment.getTailleX() - 1;
				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
					x = 1;
				}
			}
			if(x >= Environment.getTailleY()){
				y = 0;
				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
					y = Environment.getTailleY() - 2;

                }
			}
			if(y == -1){
				y = Environment.getTailleY() - 1;
				if((PropertiesReader.getInstance().getProperties("torique").equals("false"))) {
					y = 1;

                }
			}
			if(x > -1 && x < Environment.getTailleX() && y > -1 && y < Environment.getTailleY())
				if(Environment.getTab()[x][y] != null && Environment.getTab()[x][y].getClass().equals(Fish.class))
					l_fish.add((Fish)Environment.getTab()[x][y]);
		}

		return l_fish;
	}

}
