package hunter;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
    	

		Environnement env = new EnvironnementAgent((Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))),(Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))),Boolean.valueOf(PropertiesReader.getInstance().getProperties("torique")));
		String strategie = PropertiesReader.getInstance().getProperties("scheduling");
		SMA sma = new SMA(env, "hunter");
		Thread.sleep(env.getDelay());
		while (true) {
			if("ALEATOIRE".equals(strategie)) {
				for (int j = 0; j < env.getNbParticle(); j++) {
					sma.runAleatoire();
					Thread.sleep(env.getDelay());
				}
			} else if("EQUITABLE".equals(strategie) || "SEQUENTIEL".equals(strategie)) {
				sma.runEquitable();
			}
			Thread.sleep(env.getDelay());
		}
        GameChanger changer = new GameChanger();
        // Launch
        java.util.List<Agent> agents = new ArrayList<Agent>();
        SMA sma = new SMA(agents, view, "hunter");

        f.addKeyListener((KeyListener) sma.listAgent.get(0));
        f.addKeyListener((KeyListener) changer );
        sma.run();
    }

}
