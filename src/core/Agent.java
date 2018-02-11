package core;

public abstract class Agent {
	
	protected Environnement environnement;
	protected int agent;
	protected Position position;
	protected Pas pas;
	protected int xMatrice;
	protected int yMatrice;
	protected boolean collision = false;
	
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

	
	public abstract void decide();
	
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
