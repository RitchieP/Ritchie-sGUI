package hangmanapp;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Hangman
{
    String mysteryWord;
    StringBuilder currentGuess;
    ArrayList<Character> previousGuess = new ArrayList<>(); //Guessed word will be held in this array list
    ArrayList<String> dictionary = new ArrayList<>();       //Word bank

    int maxTries = 6, currentTry = 0;

    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;

    public Hangman() throws IOException                     //To except if the file is not found
    {
        initializeStreams();
        mysteryWord = pickWord();
        currentGuess = initializeCurrentGuess();
    }

    public void initializeStreams() throws IOException
    {
        try
        {
            File inFile = new File("WordList.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader(fileReader);
            String currentLine = bufferedFileReader.readLine();
            while (currentLine != null)
            {
                dictionary.add(currentLine);
                currentLine = bufferedFileReader.readLine();
            }
            bufferedFileReader.close();
            fileReader.close();
        }
        catch (IOException e)
        {
            System.out.println ("Could not init stream");
        }
    }

    public String pickWord()
    {
        Random rand = new Random();
        int wordIndex = Math.abs(rand.nextInt() % dictionary.size());
        return dictionary.get(wordIndex);
    }

    public StringBuilder initializeCurrentGuess()
    {
        StringBuilder current = new StringBuilder();
        for (int i=0; i < mysteryWord.length() * 2; i++)
        {
            if (i % 2 == 0)
            {
                current.append("_");
            }
            else
            {
                current.append(" ");
            }
        }
        return current;
    }

    public String getFormalCurrentGuess()
    {
        return "Current Guess: " + currentGuess.toString();
    }

    public boolean gameOver()
    {
        if (didWeWin())
        {
            System.out.println ("Congratulations! You won.");
            return true;
        }
        else if (didWeLose())
        {
            System.out.println ("Sorry you lost.");
            System.out.println ("The mystery word is " + mysteryWord);
            return true;
        }
        return false;
    }

    public boolean didWeLose()
    {
        return currentTry >= maxTries;
    }

    public boolean didWeWin()
    {
        String guess = getCondensedCurrentGuess();
        return guess.equals(mysteryWord);
    }

    public String getCondensedCurrentGuess()
    {
        String guess = currentGuess.toString();
        return guess.replace(" ", "");
    }

    public boolean isGuessed(char guess)
    {
        return previousGuess.contains(guess);
    }

    public boolean playGuess(char guess)
    {
        boolean isGoodGuess = false;
        previousGuess.add(guess);
        for (int i=0; i<mysteryWord.length(); i++)
        {
            if (mysteryWord.charAt(i) == guess)
            {
                currentGuess.setCharAt(i * 2, guess);
                isGoodGuess = true;
            }
        }

        if (!isGoodGuess)
        {
            currentTry++;
        }
        return isGoodGuess;
    }

    public String drawPicture()
    {
        switch (currentTry)
        {
            case 0: return noPersonDraw();
            case 1: return addHeadDraw();
            case 2: return addBodyDraw();
            case 3: return addOneArmDraw();
            case 4: return addSecondArmDraw();
            case 5: return addfirstLegDraw();
            default: return fullPersonDraw();
        }
    }

    public void clearConsole() throws AWTException, InterruptedException
    {
        try
        {
            Robot pressbot = new Robot();
            pressbot.keyPress(17); // Holds CTRL key.
            pressbot.keyPress(76); // Holds L key.
            pressbot.keyRelease(17); // Releases CTRL key.
            pressbot.keyRelease(76); // Releases L key.
            TimeUnit.MILLISECONDS.sleep(5); //This delay is to avoid bug
        }
        catch (AWTException ex)
        {
            System.out.println ("Cannot clear console.");
        }
    }

    private String noPersonDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|\n" +
               "|\n"+
               "|\n" +
               "|\n" +
               "|\n" +
               "|\n";
    }

    private String addHeadDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|         O\n" +
               "|\n"+
               "|\n" +
               "|\n" +
               "|\n" +
               "|\n";
    }

    private String addBodyDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|         O\n" +
               "|         |\n"+
               "|         |\n" +
               "|\n" +
               "|\n" +
               "|\n";
    }

    private String addOneArmDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|         O\n" +
               "|        /|\n"+
               "|         |\n" +
               "|\n" +
               "|\n" +
               "|\n";
    }

    private String addSecondArmDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|         O\n" +
               "|        /|\\ \n"+
               "|         |\n" +
               "|\n" +
               "|\n" +
               "|\n";
    }

    private String addfirstLegDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|         O\n" +
               "|        /|\\ \n"+
               "|         |\n" +
               "|        / \n" +
               "|\n" +
               "|\n";
    }

    private String fullPersonDraw()
    {
        return " - - - - -\n"+
               "|         |\n"+
               "|         O\n" +
               "|        /|\\ \n"+
               "|         |\n" +
               "|        / \\ \n" +
               "|\n" +
               "|\n";
    }
}
