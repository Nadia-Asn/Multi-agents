package water;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Environnement env = new EnvironnementAgent((Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeX"))),(Integer.parseInt(PropertiesReader.getInstance().getProperties("gridSizeY"))),Boolean.valueOf(PropertiesReader.getInstance().getProperties("torique")));

        java.util.List<Agent> agents = new ArrayList<Agent>();
        SMA sma = new SMA(agents, "water");
        sma.run();
    }
}
