package core;

import java.util.ArrayList;
import java.util.List;
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
	
	public void genererPasAleatoire(Agent agent) {
		Random r = new Random();
		List<Pas> pas = getAllPas();
		agent.setPas(pas.get(r.nextInt(pas.size()-1)));
	}
	
	public static int generatRandomPositiveNegitiveValue(int max , int min) {
		int xRandom = r.nextInt(max-min) + min;
		if (r.nextBoolean()) {
			xRandom=-xRandom;
		};
	    return xRandom;
	}
	
	public static List<Pas> getAllPas() {
		List<Pas> listPas = new ArrayList<Pas>();
		listPas.add(new Pas(0, -1));
		listPas.add(new Pas(0, 1));
		listPas.add(new Pas(-1, 0));
		listPas.add(new Pas(1, 0));
		listPas.add(new Pas(-1, -1));
		listPas.add(new Pas(1, -1));
		listPas.add(new Pas(-1, 1));
		listPas.add(new Pas(1, 1));
		
		return listPas;
	}
	
	public static Pas getPasOppose(Pas pas, Pas mur) {
		boolean versLeNord = pas.getPasX() == 0 && pas.getPasY() == -1;
		boolean versLeSud = pas.getPasX() == 0 && pas.getPasY() == 1;
		boolean versLOuest = pas.getPasX() == -1 && pas.getPasY() == 0;
		boolean verslEst = pas.getPasX() == 1 && pas.getPasY() == 0;
		boolean versNordOuest = pas.getPasX() == -1 && pas.getPasY() == -1;
		boolean versSudEst = pas.getPasX() == 1 && pas.getPasY() == 1;
		boolean versNordEst = pas.getPasX() == 1 && pas.getPasY() == -1;
		boolean versSudOuest = pas.getPasX() == -1 && pas.getPasY() == 1;
		
		boolean murAuNord = mur.getPasX() == 0 && mur.getPasY() == -1;
		boolean murAuSud = mur.getPasX() == 0 && mur.getPasY() == 1;
		
		if(versLeNord) {
			return new Pas(0, -1);
		} else if(versLeSud) {
			return new Pas(0, -1);
		} else if(versLOuest) {
			return new Pas(1, 0);
		} else if(verslEst) {
			return new Pas(-1, 0);
		} else if(versNordOuest) {
			if(murAuNord) {
				return new Pas(-1, 1);
			}else {
				return new Pas (1, -1);
			}
		} else if(versSudEst) {
			if(murAuSud) {
				return new Pas(1,-1);
			}else {
				return new Pas(-1, 1);
			}
		} else if(versNordEst) {
			if(murAuNord) {
				return new Pas(1, 1);
			} else {
				return new Pas(-1, -1);
			}
		} else if(versSudOuest) {
			if(murAuSud) {
				return new Pas(-1, -1);
			} else {
				return new Pas(1, 1);
			}
		}
		return null;
	}
}
