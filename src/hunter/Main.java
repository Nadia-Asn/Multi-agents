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
        
        // Launch
        java.util.List<Agent> agents = new ArrayList<Agent>();
        SMA sma = new SMA(agents, view, "hunter");

        f.addKeyListener((KeyListener) sma.listAgent.get(0));
        f.addKeyListener((KeyListener) changer );
        sma.run();
    }

}
