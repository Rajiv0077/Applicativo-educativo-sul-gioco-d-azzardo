package roulette;

public class InputBet {
	private Integer betAmount;
	private Integer buttonId;
	
	public InputBet(Integer betAmount, Integer buttonId) {
		this.betAmount = betAmount;
		this.buttonId = buttonId;
	}
	
	 public int getButtonId() {
	        return buttonId;
	    }

	    public int getBetAmount() {
	        return betAmount;
	    }
	
}
