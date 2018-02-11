package particules;

import core.Agent;
import core.Environnement;
import core.Pas;
import core.Position;

public class Particule extends Agent {
	
	public Particule(int agent, Position position, Pas pas, Environnement environnement) {
		super(agent, position, pas, environnement);
	}

	public void decide() {
		this.pas.alea();
		
		int newPosX = this.position.getPositionX() + this.pas.getPasX();
		int newPosY = this.position.getPositionY() + this.pas.getPasY();
		
		if(environnement.collisionFrontiere(newPosX, newPosY)){
			System.out.println("collision avec une frontière de l'agent : " + this.agent);
			this.pas.setPasX(this.pas.getPasX() * -1);
			this.pas.setPasY(this.pas.getPasY() * -1);
			collision = true;

		}else if (!environnement.caseLibre(newPosX , newPosY)){
			System.out.println("collision de "+ this.agent);
			this.pas.setPasX(this.pas.getPasX() * -1);
			this.pas.setPasY(this.pas.getPasY() * -1);
			collision = true;

			environnement.notifyChanges();
		}else{

			environnement.deplacerAgent(this);
		}
	}

}
