package core;


import java.util.Observer;


public class ViewAgent extends View implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ViewAgent(EnvironnementAgent env) {
		super(env);
	}
}
