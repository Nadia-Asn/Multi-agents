package model;

public class Agent {
	private Position position;
	private Pas pas;
	private int xMatrice;
	private int yMatrice;
	//0 si grise ,1 si rouge
	private int couleur;

	public Position getPosition() {
		return position;
	}

	public Agent(Position position, Pas pas, int xMatrice, int yMatrice, int couleur) {
		this.position = position;
		this.pas = pas;
		this.xMatrice = xMatrice;
		this.yMatrice = yMatrice;
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return "Agent [position=" + position + ", pas=" + pas + ", xMatrice=" + xMatrice + ", yMatrice=" + yMatrice
				+ ", couleur=" + couleur + "]";
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

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	/**
	 * mettre à jour le pas de l'agent
	 */
	public void update() {

		position.setPositionX(position.getPositionX() + pas.pasX);
		position.setPositionY(position.getPositionY() + pas.pasY);

	}

	public void decide() {
		this.pas.alea();

		// inverser le pas de l'agent en cas de collision avec la frontière
		if (positionInterditeZeroX()) {
			System.out.println("collision frontière");
			this.pas.setPasX(-this.pas.getPasX());
		}
		if (positionInterditeZeroY()) {
			System.out.println("collision frontière");
			this.pas.setPasY(-this.pas.getPasY());
		}
		if (positionInterditeX()) {
			System.out.println("collision frontière");
			this.pas.setPasX(-this.pas.getPasX());
		}
		if (positionInterditeY()) {
			System.out.println("collision frontière");
			this.pas.setPasY(-this.pas.getPasY());
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
	
	
	/**
	 * Vérifier si y a colision avec la frontière
	 * @return false si collision true sinon
	 */
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
	

}
