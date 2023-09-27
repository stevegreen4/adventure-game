import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    // Scanner for all user inputs
    private final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        Application application = new Application();
        application.setUp();
    }

    // todo create a wrapping method for the command line


    public void setUp() {
        System.out.println("Throughout your adventure you will be tasked with many options. " +
                "Each will be numbered accordingly.\n Please enter the numeric value for the option" +
                " you wish to take.");
        System.out.println("");
        System.out.println("Are you ready to save the magical kingdom? (Y/N)");
        String beginAdventure = userInput.nextLine();
        if (beginAdventure.toLowerCase().contains("N")) {
            System.out.println("Thank you for joining us. Maybe you will embark on this journey another time.");
        } else {
            run();
        }
    }

    // Method to read your selected story.
    public static List<String> readStory(String filename) {
        File dataInput = new File(filename);
        List<String> contentsOfStory = new ArrayList<>();
        try (Scanner inputData = new Scanner(dataInput)){
            while (inputData.hasNextLine()) {
                String lineOfStory = inputData.nextLine();
                contentsOfStory.add(lineOfStory);
            }
        } catch (FileNotFoundException e) {
            System.out.println("We are having trouble finding this story");
        }
        return contentsOfStory;
    }

    private String[] chooseYourStory() {

        List<String> fullSections = new ArrayList<>();
        List<String> listOfLines = readStory("/Users/Student/workspace/Portfolio/choose-your-own-adventure/save-the-kingdom.txt");
        String story = "";
        for (String line : listOfLines) {
            if (line.contains("//")) {
                story = story.concat(" \n" + line);
                fullSections.add(story);
                story = "";
            } else {
                story = story.concat(" \n" + line);
            }
        }

        String[] storyArray = fullSections.toArray(new String[0]);
        return storyArray;
    }

    public void run() {
        String[] story = chooseYourStory();

        int currentChoice = 0;

        while (currentChoice < story.length) {
            System.out.println(story[currentChoice]);
            if (story[currentChoice].contains("The End.") || story[currentChoice].contains("*** To Be Continued ***")) {
                break;
            }

            for (String line : story) {
                if (line.startsWith(Integer.toString(currentChoice))) {
                    System.out.println(line);
                    continue;
                }
            }

            if (userChoice() >= 0) {
                currentChoice = userChoice();
            }
        }
    }

    private int userChoice() {
        int choice = 0;
        String userChoice = userInput.nextLine();
        if (userChoice.isEmpty() || userChoice.length() > 2) {
            System.out.println("Please enter a numerical value");
        } else {
            choice = Integer.parseInt(userChoice);
        }
        return choice;
    }
}
