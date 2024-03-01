import java.util.Random;
import java.util.Vector;

public class CategoryHandler {
	String currentCategory = "N/A";
	String currentWord  = "None Chosen";
	String adjustedWord = "None Chosen";
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
		if (choice == 0)  {
			currentCategory = "CAT 1";
		} else if (choice == 1) {
			currentCategory = "CAT 2";
		} else if (choice == 2) {
			currentCategory = "CAT 3";
		}
	}
	
	//15 at full
	public void fillC1 () {
		Vector <String> temp = new Vector<String>();
		temp.add("Pencil");
		temp.add("Paper");
		temp.add("Bread");
		temp.add("Fridge");
		temp.add("Backpack");
		temp.add("Fork");
		temp.add("Spoon");
		temp.add("Knife");
		temp.add("Bed");
		temp.add("Floor");
		temp.add("Ceiling");
		temp.add("Clothes");
		cat1List = temp;
	}
	
	public void fillC2() {
		Vector <String> temp = new Vector<String>();
		temp.add("Titanic");
		temp.add("Zootopia");
		temp.add("Bambi");
		temp.add("Aladdin");
		temp.add("Tangled");
		temp.add("Aliens");
		temp.add("Jaws");
		temp.add("Scream");
		temp.add("Ratatouille");
		temp.add("Saw");
		temp.add("Dumbo");
		cat2List = temp;
	}
	
	public void fillC3() {
		Vector <String> temp = new Vector<String>();
		temp.add("Snake");
		temp.add("Dog");
		temp.add("Elephant");
		temp.add("Donkey");
		temp.add("Cat");
		temp.add("Bat");
		temp.add("Bird");
		temp.add("Flamingo");
		temp.add("Armadillo");
		temp.add("Rattlesnake");
		temp.add("Monkey");
		temp.add("Gorilla");
		temp.add("Giraffe");
		temp.add("Gecko");
		temp.add("Penguin");
		temp.add("Bear");
		cat3List = temp;
	}
}
