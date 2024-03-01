import java.io.Serializable;

public class SerializedClass implements Serializable{ // serializedclass constructor

  char guess;
  int categoryChosen;
  Boolean haveTheyLost;
  int gameStatus;
  String message;
  Boolean catOneFinish;
  Boolean catTwoFinish;
  Boolean catThreeFinish;
  public SerializedClass()  {
    this.guess = '0';
    this.categoryChosen = 0;
    this.haveTheyLost = false;
    this.message ="";
    this.catOneFinish = false;
    this.catTwoFinish = false;
    this.catThreeFinish = false;
  }


  void setMessage(String message) { //edits message
    this.message = message;
  }

  int getCategory() { //gets category
    return categoryChosen;
  }

  char getCharacter() { // gets character
    return guess;
  }
}
