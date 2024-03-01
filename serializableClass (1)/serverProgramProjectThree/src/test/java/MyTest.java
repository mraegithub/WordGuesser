import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
//-----------------------------------------------------------------------------------
//CATEGORY HANDLER CLASS
  @Test
  //tests if constructor initializes the vectors
  void test_CatFULL() {
    CategoryHandler tester = new CategoryHandler();
    assertEquals(false, tester.cat1List.isEmpty(), "It should be full full");
    assertEquals(false, tester.cat2List.isEmpty(), "It should be full full");
    assertEquals(false, tester.cat3List.isEmpty(), "It should be full full");
  }

  @Test
  //checks to see if categories get reset properly
  void test_IFCatReset() {
    CategoryHandler tester = new CategoryHandler();
    tester.cat1List.clear();
    tester.cat2List.clear();
    tester.cat3List.clear();
    tester.fillC1();
    tester.fillC2();
    tester.fillC3();
    assertEquals(false, tester.cat1List.isEmpty(), "It should be full full");
    assertEquals(false, tester.cat2List.isEmpty(), "It should be full full");
    assertEquals(false, tester.cat3List.isEmpty(), "It should be full full");
  }

  @Test
  //checks to see if selecting a category actually selects
  void test_selectCategory() {
    CategoryHandler tester = new CategoryHandler();
    tester.selectCategory(1);
    assertEquals("CAT 1", tester.currentCategory, "Should be Category 1");
    tester.selectCategory(2);
    assertEquals("CAT 2", tester.currentCategory, "Should be Category 2");
    tester.selectCategory(3);
    assertEquals("CAT 3", tester.currentCategory, "Should be Category 3");
  }

  @Test
  //checks to see if choosing word works and outputs word
  void test_chooseWord() {
    CategoryHandler tester = new CategoryHandler();
    int c1Size = tester.cat1List.size();
    int c2Size = tester.cat2List.size();
    int c3Size = tester.cat3List.size();
    String test = "";

    tester.selectCategory(1);
    test = tester.chooseWord();
    assertNotEquals("", test, "It should not still be empty");
    assertEquals(c1Size - 1, tester.cat1List.size(), "Function should have decreased size");

    tester.selectCategory(2);
    test = tester.chooseWord();
    assertEquals(c2Size - 1, tester.cat2List.size(), "Function should have decreased in size");
    assertNotEquals("", test, "It should not still be empty");

    tester.selectCategory(3);
    test = tester.chooseWord();
    assertEquals(c3Size - 1, tester.cat3List.size(), "Function should have decreased in size.");
    assertNotEquals("", test, "It should not still be empty");
  }
//------------------------------------------------------------------------------------------------
//GAME LOGIC CLASS
  @Test
  //MAKES SURE CONSTRUCTOR INITIALIZED PROPERLY
  void test_GAMELOGIC_CLASS () {
    GameLogic game = new GameLogic();
    assertEquals(false, game.win, "Should be false win.");
    assertEquals("N/A", game.catHandler.currentCategory, "Should be N/A");
    assertEquals(0, game.gameStage, "Should be 0");
    assertEquals(0, game.cat1Guesses, "Should be 0");
    assertEquals(0, game.cat2Guesses, "Should be 0");
    assertEquals(0, game.cat3Guesses, "Should be 0");
  }

  @Test
  //tests game winning conditions
  void test_gameWin() {
    GameLogic game = new GameLogic();
    assertEquals(false, game.checkGameWin(), "Should be false at the start");
    game.gameStage = 3;
    assertEquals(true, game.checkGameWin(), "Should be true when adjusted via the value");
  }

  @Test
  //tests the starting a new round conditions
  void test_startRound() {
    GameLogic game = new GameLogic();

    String theVersion ="";
    String gameString = game.startRound(1);
    for (int i = 0; i <game.catHandler.currentWord.length(); i++) {
      theVersion = theVersion+"*";
    }

    assertEquals(theVersion, gameString, "Starts the round forsure");
    assertNotEquals("None Chosen", game.catHandler.currentWord, "Shouldn't be empty");

    String gameStringTwo = game.startRound(2);
    String theVersionTwo = "";
    for (int i = 0; i <game.catHandler.currentWord.length(); i++) {
      theVersionTwo = theVersionTwo+"*";
    }

    assertEquals(theVersionTwo, gameStringTwo, "Starts the round forsure");

    String gameStringThree = game.startRound(3);
    String theVersionThree = "";
    for (int i = 0; i <game.catHandler.currentWord.length(); i++) {
      theVersionThree = theVersionThree+"*";
    }
    assertEquals(theVersionThree, gameStringThree, "Starts the round forsure");

    game.gameStage = 3;
    assertEquals("YOU WIN. :)", game.startRound(3), "makes sure you win.");
    game.c1Tries = 3;
    assertEquals("YOU LOSE. :(", game.startRound(2), "makes sure you lost.");
    game.c1Tries = 0;
    game.c2Tries = 3;
    assertEquals("YOU LOSE. :(", game.startRound(2), "makes sure you lost.");
    game.c2Tries = 0;
    game.c3Tries = 3;
    assertEquals("YOU LOSE. :(", game.startRound(2), "makes sure you lost.");
  }

  @Test
  //tests takeGuess Method
  void test_takeGuess() {
    GameLogic game = new GameLogic();
    game.startRound(1);
    game.takeGuess('z');
    assertEquals(1, game.cat1Guesses, "should be 1 as there is no z");
    game.startRound(2);
    game.takeGuess('q');
    assertEquals(1, game.cat2Guesses, "should be 1 as there is no z");
    game.startRound(3);
    game.takeGuess('w');
    assertEquals(1, game.cat3Guesses, "should be 1 as there is no w");
  }

  @Test
  //tests the chipping aspect of takeGuess Method
  void test_takeGuessCHipper() {
    GameLogic game = new GameLogic();
    boolean testValue = false;
    game.startRound(1);
    int wordSize = game.catHandler.currentWord.length();
    game.takeGuess(game.catHandler.currentWord.charAt(0));
    assertEquals(0, game.cat1Guesses, "should be no change");
    if (wordSize == game.starVersion.length()) { testValue = true;}
    assertEquals(true,  testValue, "should be true");

    game.startRound(2);
    wordSize = game.catHandler.currentWord.length();
    game.takeGuess(game.catHandler.currentWord.charAt(0));
    assertEquals(0, game.cat2Guesses, "should be no change");
    if (wordSize > game.catHandler.adjustedWord.length()) { testValue = true;}
    assertEquals(true,  testValue, "should be true");

    game.startRound(3);
    wordSize = game.catHandler.currentWord.length();
    game.takeGuess(game.catHandler.currentWord.charAt(0));
    assertEquals(0, game.cat3Guesses, "should be no change");
    if (wordSize > game.catHandler.adjustedWord.length()) { testValue = true;}
    assertEquals(true,  testValue, "should be true");
  }

  @Test
  //tests the passive_CheckRound method
  void test_passive_CheckRound() {
    GameLogic game = new GameLogic();
    game.startRound(1);
    assertEquals(true, game.passive_CheckRound(), "should be true");

    game.cat1Guesses = 6;
    assertEquals(false, game.passive_CheckRound(), "Should be false");
    assertEquals(1, game.c1Tries, "Should have 1 added");

    game.cat1Guesses = 0;
    game.startRound(2);
    game.cat2Guesses = 6;
    assertEquals(false, game.passive_CheckRound(), "SHould be false");
    assertEquals(1, game.c2Tries, "Should have 1 added");

    game.cat2Guesses = 0;
    game.startRound(3);
    game.cat3Guesses = 6;
    assertEquals(false, game.passive_CheckRound(), "SHould be false");
    assertEquals(1, game.c3Tries, "Should have 1 added");

    game.cat3Guesses = 0;
    game.startRound(3);
    game.starVersion = game.catHandler.currentWord;
    assertEquals(false, game.passive_CheckRound(), "Should be false");
    assertEquals(1, game.c3Tries, "Should not have 1 added");
    assertEquals(1, game.c2Tries, "Should not have 1 added");
    assertEquals(1, game.c1Tries, "Should not have 1 added");
    assertEquals(1, game.gameStage, "Should have 1 added");
  }
  @Test
  //Tests the serialized class
  void testSerializedClass() {
    SerializedClass a = new SerializedClass();

    assertEquals(0, a.categoryChosen, "could not make serialized class"); 

    SerializedClass messageMaker = new SerializedClass();
    messageMaker.setMessage("hi!");
    assertEquals("hi!", messageMaker.message, "could not set message");
    String theMessage = messageMaker.message;
    assertEquals("hi!", theMessage, "message not accessed");

    messageMaker.categoryChosen = 2;

    assertEquals(2, messageMaker.getCategory(), "could not get category");

    messageMaker.guess = 'f';

    assertEquals('f', messageMaker.getCharacter(), "could not get character");




  }
}
