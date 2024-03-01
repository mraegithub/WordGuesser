import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

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
