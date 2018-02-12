package water;

import core.Environnement;
import core.EnvironnementAgent;
import core.PropertiesReader;
import core.SMA;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Environnement env = new EnvironnementAgent((Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))),(Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))),Boolean.valueOf(PropertiesReader.getInstance().getProperties("torique")));
        SMA sma = new SMA(env, "water");
        String strategie = PropertiesReader.getInstance().getProperties("scheduling");
        Thread.sleep(env.getDelay());
		while (true) {
			if("ALEATOIRE".equals(strategie)) {
				for (int j = 0; j < env.getNbParticle(); j++) {
					sma.runAleatoire();
				}
			} else if("EQUITABLE".equals(strategie) || "SEQUENTIEL".equals(strategie)) {
				sma.runEquitable();
			}
			Thread.sleep(env.getDelay());
		}

    }
}
