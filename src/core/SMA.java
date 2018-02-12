package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hunter.Avatar;
import hunter.Hunter;
import hunter.Wall;
import particules.Particule;
import water.Fish;
import water.Shark;

public class SMA {
	
	private Environnement environnement;
	public static List<Agent> agents = new ArrayList<Agent>();
	private Random random = new Random();
	private int ticks;
	private int gridsizeX, gridsizeY;
	private String strategie;
	private String game;
	
	public SMA(Environnement environnement, String game) {
		this.environnement = environnement;
		this.game = game;
		initTableau();
	}

	/**
	 * Initialiser les position initales des agents dans l'environnement
	 * @param nbAgent nombre d'agents
	 * @param x nbr lignes
	 * @param y nbr colonnes
	 * @return 
	 * @return liste des positions initiales des agents
	 */
	public void initTableau() {
		
		gridsizeX = (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX")));
		gridsizeY = (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY")));
		int nbParticles = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbParticles")));
		int nbFish = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbFish")));
		int nbShark = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbShark")));
		ticks = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbTicks")));
		strategie = PropertiesReader.getInstance().getProperties("scheduling");
		int nbWalls = (int)((gridsizeX*gridsizeY)*Integer.parseInt(PropertiesReader.getInstance().getProperties("wallsPercent"))/100f);
		int nbHunter = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbHunter")));
		
		if("particules".equals(this.game)) {
			if (gridsizeX * gridsizeY < nbParticles) {
				throw new IllegalArgumentException("Le nombre de particule dépasse la capacité de la grille !");
			}
			for (int i = 0; i < nbParticles; i++) {
				Position position = this.getRandomPosition();
				agents.add(new Particule(i, position, new Pas(0, 0),this.environnement));
			}
		} else if("water".equals(this.game)) {
			for (int i = 0; i < nbFish; i++) {
				Position position = this.getRandomPosition();
				agents.add(new Fish(position, new Pas(0, 0),this.environnement));
			}
			for (int i = 0; i < nbShark; i++) {
				Position position = this.getRandomPosition();
				agents.add(new Shark(position, new Pas(0, 0),this.environnement));
			}
		} else if ("hunter".equals(game)) {
			for(int i = 0; i < nbWalls; i++) {
				Position position = this.getRandomPosition();
				agents.add(new Wall(position, null,this.environnement));
			}
			for(int i = 0; i < nbHunter; i++) {
				Position position = this.getRandomPosition();
				agents.add(new Hunter(position, null,this.environnement));
			}
			Position position = this.getRandomPosition();
			agents.add(new Avatar(position, new Pas(0, 0), environnement));
		}
		
	}
	
	public void runAleatoire() {		
		agents.get(random.nextInt(agents.size())).decide();
	}
	
	public void runEquitable() {
		for(int i = 0; i < ticks; i++) {
			for(int agent = 0; agent< agents.size(); agent++) {
				agents.get(agent).decide();
			}
			if("SEQUENTIEL".equals(strategie)) {
				Collections.shuffle(agents);
			}
		}
	}
	
	public Position getRandomPosition() {
			int posXRandom , posYRandom = 0;
			do {
				posXRandom = random.nextInt(gridsizeX);
				posYRandom = random.nextInt(gridsizeY);	

			} while (!this.environnement.caseLibre(posXRandom, posYRandom));
			
			return new Position(posXRandom, posYRandom);
	}
	
	


}
