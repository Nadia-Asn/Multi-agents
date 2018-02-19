package hunter;

import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.Environnement;
import core.Pas;
import core.Position;
import core.SMA;

public class Labyrinthe {
	
	private Environnement environnement;
	private List<Position> cases;
	private int sizeX;
	private int sizeY;
	private int nbMurOuvert;
	CaseLabyrinthe enCours;
	List<CaseLabyrinthe> listCases;
	private CaseLabyrinthe[][] labyrinthe;
	
	public Labyrinthe(Environnement env, int gridSizeX, int gridSizeY) {
		this.environnement = env;
		this.cases = new ArrayList<Position>();
		this.listCases = new ArrayList<CaseLabyrinthe>();
		this.sizeX = gridSizeX/2;
		this.sizeY = gridSizeY/2;
		this.nbMurOuvert = 0;
	}
	
	public void initLabyrinthe(Environnement env,int gridSizeX, int gridSizeY) {
		for(int i = 0; i < gridSizeX; i++) {
			for(int j = 0; j < gridSizeY; j++) {
				if(i % 2 == 0 || j % 2 == 0) {
					SMA.agents.add(new Wall(new Position(i,j), new Pas(0,0), env));
				} else {
					cases.add(new Position(j, i));
				}
			}
		}
	}
	
	public void generationLabyrinthe() {
		affectactionValeurCase();
		while(nbMurOuvert < sizeX*sizeY-1) {
			for(int i = 0; i < listCases.size(); i++) {
				CaseLabyrinthe cas = listCases.get(i);
				Position pos = cas.getMurAleatoire();
				CaseLabyrinthe voisin = retournerVoisinSelonMur(cas, pos);
				if(voisin != null && pos != null) {
					if(!cas.estDansLeMemeChemin(voisin) && !cas.murEstOuvert(pos)) {
						casserMur(pos);
						cas.memeChemin.add(voisin);
						voisin.memeChemin.add(cas);
						voisin.identifiant = cas.identifiant;
						voisin.modifierIdentifiantChemin(cas.identifiant);
					}
				}
			}
		}
		
	}
	
	private void affectactionValeurCase() {
		int valeur = 0;
		labyrinthe = new CaseLabyrinthe[sizeX][sizeY];
		for(int i = 0; i < sizeX; i ++) {
			for(int j = 0; j < sizeY; j++) {
				CaseLabyrinthe cas = new CaseLabyrinthe(this.environnement, cases.get(valeur), new Position(j, i),valeur);
				labyrinthe[j][i] = cas;
				listCases.add(cas);
				valeur ++;
			}
		}
	}
	
	public List<Position> getCases() {
		return this.cases;
	}
	
	public void casserMur(Position position) {
		Agent agent = environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()];
		SMA.agents.remove(agent);
		environnement.getEnvironnement()[position.getPositionX()][position.getPositionY()] = null;
		nbMurOuvert ++;
	}
	
	public CaseLabyrinthe retournerVoisinSelonMur(CaseLabyrinthe cas, Position mur) {
		// Voisin Ouest
		if(cas.positionEnv.getPositionX() > mur.getPositionX()) {
			if(mur.getPositionX() > 0) {
				return labyrinthe[cas.positionLab.getPositionX()-1][cas.positionLab.getPositionY()];
			}
		// Voisin Est
		}if(cas.positionEnv.getPositionX() < mur.getPositionX()) {
			if(mur.getPositionX() < environnement.getGridSizeX()-1) {
				return labyrinthe[cas.positionLab.getPositionX()+1][cas.positionLab.getPositionY()];
			}
		// Voisin Sud
		}if(cas.positionEnv.getPositionY() < mur.getPositionY()) {
			if(mur.getPositionY() < environnement.getGridSizeY()-1) {
				return labyrinthe[cas.positionLab.getPositionX()][cas.positionLab.getPositionY()+1];
			}
		// Voisin nord
		}if(cas.positionEnv.getPositionY() > mur.getPositionY()) {
			if(mur.getPositionY() > 0) {
				return labyrinthe[cas.positionLab.getPositionX()][cas.positionLab.getPositionY()-1];
			}
		}
		return null;
	}
	
}
