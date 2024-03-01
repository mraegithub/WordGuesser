import java.util.Random;
import java.util.Vector;

public class CategoryHandler {
  String currentCategory = "N/A";
  String currentWord  = "None Chosen";
  String adjustedWord = "";
  //the size of all Lists will more or less be the same unless i change this later
  Vector <String> cat1List = new Vector<String>();
  String cat1Name = "Items In Your House";
  Vector <String> cat2List = new Vector<String>();
  String cat2Name = "1 Word Movie Titles";
  Vector <String> cat3List = new Vector<String>();
  String cat3Name = "Animals";

  public CategoryHandler() {
    fillC1();
    fillC2();
    fillC3();
  }

  //mainly a random word will be chosen
  public String chooseWord() {
    String curr = "None Chosen";

    Random rand = new Random();

    //selects based off of what the category is
    //makes sure it also removes the word
    if (currentCategory == "CAT 1") {
      int selection = rand.nextInt(cat1List.size());
      curr = cat1List.get(selection);
      cat1List.remove(selection);
    } else if (currentCategory == "CAT 2") {
      int selection = rand.nextInt(cat2List.size());
      curr = cat2List.get(selection);
      cat2List.remove(selection);
    } else if (currentCategory == "CAT 3") {
      int selection = rand.nextInt(cat3List.size());
      curr = cat3List.get(selection);
      cat3List.remove(selection);
    }
    currentWord = curr;
    adjustedWord = curr;
    return curr;
  }

  //selects category based off user input
  void selectCategory(int choice) {
    if (choice == 1)  {
      currentCategory = "CAT 1";
    } else if (choice == 2) {
      currentCategory = "CAT 2";
    } else if (choice == 3) {
      currentCategory = "CAT 3";
    }
  }

  public void fillC1 () {
    Vector <String> temp = new Vector<String>();
    temp.add("pencil");
    temp.add("bread");
    temp.add("fridge");
    temp.add("fork");
    temp.add("knife");
    temp.add("bed");
    temp.add("clothes");
    cat1List = temp;
  }

  public void fillC2() {
    Vector <String> temp = new Vector<String>();
    temp.add("tangled");
    temp.add("aliens");
    temp.add("jaws");
    temp.add("scream");
    temp.add("saw");
    temp.add("dumbo");
    cat2List = temp;
  }

  public void fillC3() {
    Vector <String> temp = new Vector<String>();
    temp.add("snake");
    temp.add("dog");
    temp.add("donkey");
    temp.add("cat");
    temp.add("bat");
    temp.add("bird");
    temp.add("flamingo");
    temp.add("monkey");
    temp.add("gecko");
    temp.add("bear");
    cat3List = temp;
  }
}
