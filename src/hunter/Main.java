package hunter;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Environment env = new Environment();
        GameChanger changer = new GameChanger();
        View view = new View(env);
        
        // core.View
        JFrame f = new JFrame();
        f.setSize(Integer.parseInt(PropertiesReader.getInstance().getProperties("canvasSizeX")), Integer.parseInt(PropertiesReader.getInstance().getProperties("canvasSizeY")));
        f.setLayout(new BorderLayout());
        f.add(view, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.setVisible(true);
        

        // Launch
        java.util.List<Agent> agents = new ArrayList<Agent>();
        SMA sma = new SMA(agents, view, "hunter");

        f.addKeyListener((KeyListener) sma.listAgent.get(0));
        f.addKeyListener((KeyListener) changer );
        sma.run();
    }

}
