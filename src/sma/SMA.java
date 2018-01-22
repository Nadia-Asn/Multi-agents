package sma;

import java.util.ArrayList;
import java.util.Collections;

import model.Agent;
import model.Pas;
import model.Position;
import model.Strategie;

public class SMA {

	private ArrayList<Agent> agents;
	private String strategie;
	private Agent[][] agentsTab;

	public SMA(ArrayList<Agent> agents, String strategie,Agent[][] agentsTab) {
		this.agents = agents;
		this.strategie = strategie;
		this.agentsTab=agentsTab;
	}
	public void run() {
		int size = agents.size();
		runStrategie();
		
		for (int i = 0; i < size; i++) {
			Agent agentA = agents.get(i);
			agentA.decide();
			System.out.println("============================================");

			System.out.println("Agent n° : "+ i + " / déplacement : " + agentA.getPas().pasY +" , " + agentA.getPas().pasY);

			//System.out.println("position temporaire de l'agent A n° " + i +  " => ("+agentA.getPosition().getPositionX() + agentA.getPas().pasX+ "," + agentA.getPosition().getPositionX() + agentA.getPas().pasY + ")");
			
			// On suppose avoir deja fait le deplacement
			Position temporaire = new Position(agentA.getPosition().getPositionX() + agentA.getPas().pasX,
					agentA.getPosition().getPositionX() + agentA.getPas().pasY);
			for (int j = 0; j < size; j++) {

				if (j != i) {
					Agent agentB = agents.get(j);
					
					// en cas de collision entre A et B
					if (temporaire.equals(agentB.getPosition())) {
						System.out.println("Collision entre l'agent :" + i + " et " + j);
						
						//On échange les Pas de A et les Pas de B
						 changePasAgentAndAgentB(agentA,agentB);
					
					}
				}
			}
			
			//On libere l'ancienne case
			agentsTab[agentA.getPosition().getPositionX()][agentA.getPosition().getPositionY()]=null;
			// On fait deplacer l'agent vers la nouvelle position
			agentA.update();
			agentA.verif();
			//On met a jour l'environnement du tableau
			agentsTab[agentA.getPosition().getPositionX()][agentA.getPosition().getPositionY()]=agentA;

		}
	}

	private void runStrategie() {
		if (Strategie.ALEA.equals(strategie)) {
			// on melange la liste
			Collections.shuffle(agents);
		}
		// Sinon on garde la liste dans le meme ordre
	}

	private void changePasAgentAndAgentB(Agent agentA, Agent agentB) {
		Pas pasA = agentA.getPas();
		System.out.println("pas de A : " + agentA.getPas().pasX +" - " + agentA.getPas().pasY);
		Pas pasB = agentB.getPas();
		System.out.println("pas de B : " + agentB.getPas().pasX +" - " + agentB.getPas().pasY);
		// on echange les pas
		agentA.setPas(pasB);
		agentB.setPas(pasA);
		//On passe la Couleur à Rouge 
		agentA.setCouleur(1);
		agentB.setCouleur(1);
	}

}
