package environnement;

import java.util.ArrayList;
import java.util.Random;

import model.Agent;
import model.Pas;
import model.Position;
import model.Strategie;
import sma.SMA;

public class EnvironnementAlea implements Environnement {

	private SMA sma;
	private int tours;
	private Agent[][] agents;
	Random random = new Random();
	boolean[][] cellules = new boolean[3][4] ;


	public EnvironnementAlea(int nbAgents,int x, int y, int nbtours) {
		tours = nbtours;
		agents = new Agent[x][y];
		ArrayList<Agent>listAgent=initTableau(nbAgents, x, y);
		sma = new SMA(listAgent, Strategie.ALEA,agents);
		
	}

	@Override
	public void start() {
		for (int i = 0; i < tours; i++) {
			sma.run();
			System.out.println(agents);
		}

	}

	/**
	 * Initialiser les position initales des agents dans l'environnement
	 * @param nbAgent nombre d'agents
	 * @param x nbr lignes
	 * @param y nbr colonnes
	 * @return liste des positions initiales des agents
	 */
	public ArrayList<Agent> initTableau(int nbAgent, int x, int y) {
		ArrayList<Agent> agents = new ArrayList<Agent>();

		for (int i = 0; i < nbAgent; i++) {
			
			int xRandom = 0;
			int yRandom = 0;
			do {
				xRandom = random.nextInt(x);
				yRandom = random.nextInt(y);	
			} while (cellules[xRandom][yRandom] == true);

			Agent agent = new Agent(new Position(xRandom, yRandom), new Pas(0, 0), x, y, 0);
			//this.agents[xRandom][yRandom] = agent;
			cellules[xRandom][yRandom] = true;
			agents.add(agent);
			System.out.println("position initial agent n° :" + i + " => ("+xRandom + "," + yRandom + ")");
		}
		
		return agents;
	}
}
