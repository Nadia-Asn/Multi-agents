package environnement;

import java.util.ArrayList;
import java.util.Random;

import model.Agent;
import model.Pas;
import model.Position;
import model.Strategie;
import sma.SMA;

public class EnvironnementSequentiel {
	
	private SMA sma;
	private int tours;
	private Agent[][] agents;

	public EnvironnementSequentiel(int nbAgents,int x, int y, int nbtours) {
		tours = nbtours;
		agents = new Agent[x][y];
		ArrayList<Agent>listAgent=initTableau(nbAgents, x, y);
		sma = new SMA(listAgent, Strategie.SEQUENTIELLE,agents);
	}
	
	public void start() {
		for (int i=0;i<tours;i++) {
			sma.run();
		}
	}
	
	public ArrayList<Agent> initTableau(int nbAgent, int x, int y) {
		ArrayList<Agent> agents = new ArrayList<Agent>();
		for (int i = 0; i < nbAgent; i++) {
			Random random = new Random();
			int xRandom = random.nextInt(x);
			int yRandom = random.nextInt(y);
			Agent agent = new Agent(new Position(xRandom, yRandom), new Pas(0, 0), x, y, 0);
			this.agents[xRandom][yRandom] = agent;
			agents.add(agent);
		}
		return agents;
	}
}
