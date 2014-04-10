public abstract class Round {
	
	public String playerSolution;
	
	public Round(){
		playerSolution = "";
	}
	
	public void submitSolution(String solution){
		playerSolution = solution;
	}
	
	public abstract void playGame();
	
	public abstract boolean checkSolution();
	
	public abstract int scoreSolution();
	
	public abstract void revealSolution();
	
}
