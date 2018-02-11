package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import particules.Particule;

public class SMA {
	
	private Environnement environnement;
	protected final List<Agent> agents = new ArrayList<Agent>();
	private Random random = new Random();
	private int ticks;
	private String strategie;

	/**
	 * Initialiser les position initales des agents dans l'environnement
	 * @param nbAgent nombre d'agents
	 * @param x nbr lignes
	 * @param y nbr colonnes
	 * @return 
	 * @return liste des positions initiales des agents
	 */
	public void initTableau(Environnement environnement) {
		
		int gridsizeX = (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX")));
		int gridsizeY = (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY")));
		int nbParticles = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbParticles")));
		ticks = (Integer.parseInt(PropertiesReader.getInstance().getProperties("nbTicks")));
		strategie = PropertiesReader.getInstance().getProperties("scheduling");
		
		if (gridsizeX * gridsizeY < nbParticles) {
			throw new IllegalArgumentException("Le nombre de particule dépasse la capacité de la grille !");
		}
		
		for (int i = 0; i < nbParticles; i++) {
			int posXRandom , posYRandom = 0;
			int pasXRandom = 0;
			int pasYRandom = 0;
			do {
				posXRandom = random.nextInt(gridsizeX);
				posYRandom = random.nextInt(gridsizeY);	

			} while (!environnement.caseLibre(posXRandom, posYRandom));

			agents.add(new Particule(i, new Position(posXRandom, posYRandom), new Pas(pasXRandom, pasYRandom),environnement));

			System.out.println("position initial agent n° :" + i + " => ("+posXRandom + "," + posYRandom + ")");
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
