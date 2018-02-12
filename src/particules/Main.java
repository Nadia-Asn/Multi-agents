package particules;
import core.Environnement;
import core.EnvironnementAgent;
import core.PropertiesReader;
import core.SMA;


public class Main {
	
	public static int nbCases() {
		return (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX")) / Integer.parseInt(PropertiesReader.getInstance().getProperties("boxSize"))) * (Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX")) / Integer.parseInt(PropertiesReader.getInstance().getProperties("boxSize")));
	}

	public static void main(String[] args) throws InterruptedException {

			Environnement env = new EnvironnementAgent((Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))),(Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))),Boolean.valueOf(PropertiesReader.getInstance().getProperties("torique")));
			String strategie = PropertiesReader.getInstance().getProperties("scheduling");
			SMA sma = new SMA(env, "particules");
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

	
	}
}