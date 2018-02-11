package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import particules.Particule;
import water.Fish;
import water.Shark;

public class SMA {
	
	private Environnement environnement;
	public static List<Agent> agents = new ArrayList<Agent>();
	private Random random = new Random();
	private int ticks;
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
		
		int gridsizeX = (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX")));
		int gridsizeY = (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY")));
		int nbParticles = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbParticles")));
		int nbFish = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbFish")));
		int nbShark = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbShark")));
		ticks = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbTicks")));
		strategie = PropertiesReader.getInstance().getProperties("scheduling");
		
		if (gridsizeX * gridsizeY < nbParticles) {
			throw new IllegalArgumentException("Le nombre de particule dépasse la capacité de la grille !");
		}
		if("particules".equals(this.game)) {
			for (int i = 0; i < nbParticles; i++) {
				int posXRandom , posYRandom = 0;
				int pasXRandom = 0;
				int pasYRandom = 0;
				do {
					posXRandom = random.nextInt(gridsizeX);
					posYRandom = random.nextInt(gridsizeY);	
	
				} while (!this.environnement.caseLibre(posXRandom, posYRandom));
				agents.add(new Particule(i, new Position(posXRandom, posYRandom), new Pas(pasXRandom, pasYRandom),this.environnement));
				System.out.println("position initial agent n° :" + i + " => ("+posXRandom + "," + posYRandom + ")");
			}
		} else if("water".equals(this.game)) {
			for (int i = 0; i < nbFish; i++) {
				int posXRandom , posYRandom = 0;
				int pasXRandom = 0;
				int pasYRandom = 0;
				do {
					posXRandom = random.nextInt(gridsizeX);
					posYRandom = random.nextInt(gridsizeY);	
	
				} while (!this.environnement.caseLibre(posXRandom, posYRandom));
				agents.add(new Fish(new Position(posXRandom, posYRandom), new Pas(pasXRandom, pasYRandom),this.environnement));
				System.out.println("position initial agent n° :" + i + " => ("+posXRandom + "," + posYRandom + ")");
			}
			for (int i = 0; i < nbShark; i++) {
				int posXRandom , posYRandom = 0;
				int pasXRandom = 0;
				int pasYRandom = 0;
				do {
					posXRandom = random.nextInt(gridsizeX);
					posYRandom = random.nextInt(gridsizeY);	
	
				} while (!this.environnement.caseLibre(posXRandom, posYRandom));
				agents.add(new Shark(new Position(posXRandom, posYRandom), new Pas(pasXRandom, pasYRandom),this.environnement));
				System.out.println("position initial agent n° :" + i + " => ("+posXRandom + "," + posYRandom + ")");
			}
		}
		
	}
	
	public void runAleatoire() {		
		agents.get(random.nextInt(agents.size())).decide();
	}
	
	public void runEquitable() {
		for(int i = 0; i < ticks; i++) {
			for(Agent agent : agents) {
				agent.decide();
			}
			if("SEQUENTIEL".equals(strategie)) {
				Collections.shuffle(agents);
			}
		}
	}
	
	


}
