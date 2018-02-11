package core;

public class Agent {
	
	private Environnement environnement;
	private int agent;
	private Position position;
	private Pas pas;
	private int xMatrice;
	private int yMatrice;
	boolean collision = false;
	
	public Position getPosition() {
		return position;
	}

	public Agent(Position position, Pas pas, Environnement environnement) {
		this.position = position;
		this.pas = pas;
		this.environnement=environnement;
		environnement.addAgent(this);
		collision = false;

	}

	public Agent(int agent, Position position, Pas pas, Environnement environnement) {
		this.position = position;
		this.pas = pas;
		this.agent = agent;
		this.environnement=environnement;
		environnement.addAgent(this);
		collision = false;

	}

	@Override
	public String toString() {
		return "Agent [position=" + position + ", pas=" + pas + ", xMatrice=" + xMatrice + ", yMatrice=" + yMatrice+
				"]";
	}

	/**
	 * Getter de l'attribut {@link Agent#agent}
	 * @return agent
	 */
	public int getAgent() {
		return agent;
	}

	/**
	 * Setter de l'attribut {@link Agent#agent}
	 * @param agent l'attribut {@link Agent#agent} à setter
	 */
	public void setAgent(int agent) {
		this.agent = agent;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Pas getPas() {
		return pas;
	}

	public void setPas(Pas pas) {
		this.pas = pas;
	}

	public int getxMatrice() {
		return xMatrice;
	}

	public void setxMatrice(int xMatrice) {
		this.xMatrice = xMatrice;
	}

	public int getyMatrice() {
		return yMatrice;
	}

	public void setyMatrice(int yMatrice) {
		this.yMatrice = yMatrice;
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
	
	public void verif() {
		if (positionInterditeZeroX()) {
			this.pas.setPasX(-this.pas.getPasX());
		}
		if (positionInterditeZeroY()) {
			this.pas.setPasY(-this.pas.getPasY());
		}
		if (positionInterditeX()) {
			this.pas.setPasX(-this.pas.getPasX());
		}
		if (positionInterditeY()) {
			this.pas.setPasY(-this.pas.getPasY());
		}
	}
	

	//Methode au cas ou Probleme de frontiere 
	private boolean positionInterditeZeroX() {
		if (this.position.getPositionX()+this.pas.getPasX()< 0) {
			return true;
		}
		return false;
	}

	private boolean positionInterditeZeroY() {
		if (this.position.getPositionY()+this.pas.getPasY()< 0) {
			return true;
		}
		return false;
	}
	private boolean positionInterditeX(){
		if (this.position.getPositionX()+this.pas.getPasX()>=xMatrice) {
			return true;
		}
		return false;
	}
	
	private boolean positionInterditeY() {
		if (this.position.getPositionY()+this.pas.getPasY()>=yMatrice) {
			return true;
		}
		return false;
	}
	
	

	/**
	 * mettre à jour la position de l'agent
	 */
	public void update() {

	//verif();

		this.position.setPositionX(this.position.getPositionX() + this.pas.pasX);
		this.position.setPositionY(this.position.getPositionY() + this.pas.pasY);
	
		if (environnement.isTorus()){
			if(position.getPositionX() >= environnement.getGridSizeX()) {
				this.position.setPositionX(0);
			}else if(position.getPositionX() < 0) {
				this.position.setPositionX(environnement.getGridSizeX() - 1);
			}
			if(position.getPositionY() >= environnement.getGridSizeY()) {
				this.position.setPositionY(0);
			}else if(position.getPositionY() < 0) {
				this.position.setPositionY(environnement.getGridSizeY() - 1);
			}
		}else{
			if(position.getPositionX() >= environnement.getGridSizeX()) {
				this.pas.setPasX(-this.pas.getPasX());
				this.position.setPositionX(this.position.getPositionX() + this.pas.getPasX());
			}else if(position.getPositionX() < 0) {
				this.pas.setPasX(-this.pas.getPasX());
				this.position.setPositionX(this.position.getPositionX() + this.pas.getPasX());
			}
			if(position.getPositionY() >= environnement.getGridSizeY()) {
				this.pas.setPasY(-this.pas.getPasY());
				this.position.setPositionY(this.position.getPositionY() + this.pas.getPasY());

			}else if(position.getPositionY() < 0) {
				this.pas.setPasY(-this.pas.getPasY());
				this.position.setPositionY(this.position.getPositionY() + this.pas.getPasY());

		}
		
		}

	}

	public boolean getCollision() {
		return collision;
	}

	/**
	 * Getter de l'attribut {@link Agent#environnement}
	 * @return environnement
	 */
	public Environnement getEnvironnement() {
		return environnement;
	}

	/**
	 * Setter de l'attribut {@link Agent#environnement}
	 * @param environnement l'attribut {@link Agent#environnement} à setter
	 */
	public void setEnvironnement(Environnement environnement) {
		this.environnement = environnement;
	}

}
