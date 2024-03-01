
public class GameLogic {

  CategoryHandler catHandler;
  Boolean win = false;
  Boolean cat1win = false;
  Boolean cat2win = false;
  Boolean cat3win = false;
  Boolean c1winAlready  = false;
  Boolean c2winAlready = false;
  Boolean c3winAlready = false;
  String starVersion;
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
      starVersion = "";
      for (int i = 0; i < catHandler.currentWord.length(); i++) {
        starVersion = starVersion + "*";
      }
      return starVersion;
    } else {
      checkGameWin();
      return EndGame();
    }
  }

  //takes in a char and checks if its inside the word
  //if it is there it replaces it with "*" 
  public boolean takeGuess(char b) {
    String checker = "" + b;
    checker.toLowerCase();
    if (catHandler.currentWord.indexOf(checker) > -1) {
     int index = catHandler.adjustedWord.indexOf(checker);
     while (catHandler.adjustedWord.indexOf(checker) > -1) {
      catHandler.adjustedWord = catHandler.adjustedWord.substring(0, index) + "!" + catHandler.adjustedWord.substring(index + 1);
      starVersion = starVersion.substring(0, index) + b + starVersion.substring(index + 1);
      }
      return true;
    }
    if (catHandler.currentWord.indexOf(checker) == -1) {
        if (catHandler.currentCategory == "CAT 1") {
          cat1Guesses++;
        } else if (catHandler.currentCategory == "CAT 2") {
          cat2Guesses++;
        } else if (catHandler.currentCategory == "CAT 3") {
          cat3Guesses++;
        }
    }
    return false;
  }

  //checks all parameters to see if the round is over per category
  //false if round is over
  //true if the player can keep playing
  public boolean passive_CheckRound() {
    if (catHandler.currentCategory == "CAT 1" && cat1Guesses == 6) {
      c1Tries++;
      cat1Guesses = 0;
      return false;
    } else if (catHandler.currentCategory == "CAT 2" && cat2Guesses == 6) {
      c2Tries++;
      cat2Guesses = 0;
      return false;
    } else if (catHandler.currentCategory == "CAT 3" && cat3Guesses == 6) {
      c3Tries++;
      cat3Guesses = 0;
      return false;
    } else if (starVersion.equals(catHandler.currentWord)) {
      if (catHandler.currentCategory == "CAT 1") {
        cat1win = true;
      }else if (catHandler.currentCategory == "CAT 2") {
        cat2win = true;
      }else if (catHandler.currentCategory == "CAT 3") {
        cat3win = true;
      }
      gameStage++;
      return false;
    }
    return true;
  }
}
