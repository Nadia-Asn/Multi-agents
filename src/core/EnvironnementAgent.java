package core;


import javax.swing.JScrollPane;


public class EnvironnementAgent extends Environnement {

	public EnvironnementAgent( int sizeX, int sizeY, boolean torus) {
		super(sizeX, sizeY,torus);
		
		View vue = new ViewAgent(this);
		JScrollPane scroller = new JScrollPane();
		scroller.setViewportView(vue);
		vue.setVisible(true);
		this.createWindow("Particules").add(vue);

	}

}