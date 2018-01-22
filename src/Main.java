

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import environnement.EnvironnementAlea;
import model.Pas;

public class Main {

	public static void main(String[] args) {
			/*JFrame t = new JFrame();
			JPanel pan = new JPanel (new GridLayout (3,3));
			Border blackline = BorderFactory.createLineBorder(Color.black,1); 
			for(int i = 0; i<25;i++){
			   JPanel ptest = new JPanel();
			   ptest.setBorder(blackline);
			   pan.add(ptest);
			}
			pan.setBorder(blackline);
			t.add(pan);
			t.setVisible(true);*/
			new EnvironnementAlea(10, 3, 4, 2).start();
	}

}