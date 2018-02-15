package water;

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
        SMA sma = new SMA(env, "water");
        String strategie = PropertiesReader.getInstance().getProperties("scheduling");
        View vue = new ViewAgent((EnvironnementAgent)env);
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(vue);
		vue.setVisible(true);
		JFrame frame = env.createWindow("");
		frame.add(vue);
		while (true) {
			if("ALEATOIRE".equals(strategie)) {
				for (int j = 0; j < env.getNbParticle(); j++) {
					Thread.sleep(env.getDelay());
					sma.runAleatoire();
				}
			} else if("EQUITABLE".equals(strategie) || "SEQUENTIEL".equals(strategie)) {
				Thread.sleep(env.getDelay());
				sma.runEquitable();
			}
			
		}

    }
}
