
public class GameLogic {
	
	CategoryHandler catHandler;
	Boolean win = false;
	int gameStage = 0;
	int cat1Guesses = 0;
	int c1Tries = 0;
	int cat2Guesses = 0;
	int c2Tries = 0;
	int cat3Guesses = 0;
	int c3Tries = 0;
	
	//default constructor
	public GameLogic() {
		catHandler = new CategoryHandler();
		win = false;
		gameStage = 0;
		cat1Guesses = 0;
		cat2Guesses = 0;
		cat3Guesses = 0;
	}
	
	//we use game stage as a means to check if the player had won the game
	public boolean checkGameWin() {
		if (gameStage == 3) {
			win = true;
			return true;
		}
			win = false;
			return false;
	}
	
	public String EndGame() {
		if (win == true) {
			return "YOU WIN. :)";
		}
		return "YOU LOSE. :(";
	}
	//starts the round for the specific gameStage
	//takes in the choice made from the player pushing a button and applies it throughout
	public String startRound(int choice) {
		
		if (c1Tries == 3 || c2Tries == 3 || c3Tries == 3) {
			win = false;
			return EndGame();
		}
		
		if (gameStage < 3) {
			catHandler.selectCategory(choice);
			catHandler.chooseWord();
			return "";
		} else {
			checkGameWin();
			return EndGame();
		}
	}
	
	//takes in a char and checks if its inside the word
	//if it is there it replaces it with "" -emptiness
	public void takeGuess(char b) {
		String checker = "" + b;
		if (catHandler.adjustedWord.indexOf(checker) > -1) {
			catHandler.adjustedWord = (catHandler.adjustedWord).replaceAll(checker, "");
		} else {
			if (catHandler.currentCategory == "CAT 1") {
				cat1Guesses++;
			} else if (catHandler.currentCategory == "CAT 2") {
				cat2Guesses++;
			} else if (catHandler.currentCategory == "CAT 3") {
				cat3Guesses++;
			}
		}
	}
	
	//checks all parameters to see if the round is over per category
	//false if round is over
	//true if the player can keep playing
	public boolean passive_CheckRound() {
		if (catHandler.currentCategory == "CAT 1" && cat1Guesses == 6) {
			c1Tries++;
			return false;
		} else if (catHandler.currentCategory == "CAT 2" && cat2Guesses == 6) {
			c2Tries++;
			return false;
		} else if (catHandler.currentCategory == "CAT 3" && cat3Guesses == 6) {
			c3Tries++;
			return false;
		} else if (catHandler.adjustedWord == "") {
			gameStage++;
			return false;
		}
		return true;
	}
}
