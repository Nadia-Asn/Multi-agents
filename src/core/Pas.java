package core;

import java.util.Random;

public class Pas {
	public int pasX;
	public int pasY;
	static Random r =new Random();
	public int getPasX() {
		return pasX;
	}
	public Pas(int pasX, int pasY) {
		super();
		this.pasX = pasX;
		this.pasY = pasY;
	}
	public void setPasX(int pasX) {
		this.pasX = pasX;
	}
	public int getPasY() {
		return pasY;
	}
	public void setPasY(int pasY) {
		this.pasY = pasY;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pas other = (Pas) obj;
		if (pasX != other.pasX)
			return false;
		if (pasY != other.pasY)
			return false;
		return true;
	}
	
	/**
	 * Génerer un déplacement aléatoire 
	 */
	public void alea() {
		while(pasX == 0 && pasY == 0) {
			pasX=generatRandomPositiveNegitiveValue(2,0);
			pasY=generatRandomPositiveNegitiveValue(2,0);
		}
	}
	
	public static int generatRandomPositiveNegitiveValue(int max , int min) {
		int xRandom = r.nextInt(max-min) + min;
		if (r.nextBoolean()) {
			xRandom=-xRandom;
		};
	    return xRandom;
	}
}
