package hunter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import core.Environnement;
import core.EnvironnementAgent;
import core.PropertiesReader;
import core.SMA;
import core.View;
import core.ViewAgent;


public class Main {

    public static void main(String[] args) throws InterruptedException {
    	

		Environnement env = new EnvironnementAgent((Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))),(Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))),Boolean.valueOf(PropertiesReader.getInstance().getProperties("torique")));
		String strategie = PropertiesReader.getInstance().getProperties("scheduling");
		SMA sma = new SMA(env, "hunter");
		View vue = new ViewAgent((EnvironnementAgent)env);
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(vue);
		vue.setVisible(true);
		JFrame frame = env.createWindow("");
		frame.add(vue);
		frame.addKeyListener((Avatar)SMA.agents.get(0));
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
