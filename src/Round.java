
public abstract class Round {
	private String playerSolution;
	
	public abstract void submitSolution();
	
	public abstract boolean checkSolution();
	
	public abstract int scoreSolution();
	
	public abstract void revealSolution();
	
}
