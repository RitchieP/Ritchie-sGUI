
package backend;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Scanner;

public class HangmanApp extends Hangman
{
    public HangmanApp() 
    {
        super(); // call the parent class constructor
    }
    
    public static void main(String[] args) throws IOException, AWTException, InterruptedException
    {
        Scanner sc = new Scanner (System.in);
        System.out.println ("Welcome to Hangman by Ritchie!");
        boolean doYouWantToPlay = true;

        while (doYouWantToPlay)
        {
            System.out.println ("Let's Begin!");
            Hangman game = new Hangman();

            do
            {
                //Setup the board
                System.out.println ();
                System.out.println (game.drawPicture());
                System.out.println ();
                System.out.println (game.getFormalCurrentGuess());
                System.out.println (game.mysteryWord);

                //Get the guess
                System.out.println ("Enter a character.");
                char guess = (sc.next().toLowerCase()).charAt(0);
                System.out.println ();

                //check if the character have been guessed
                while (game.isGuessed(guess))
                {
                    System.out.println ("Try again. Character already guessed");
                    guess = (sc.next().toLowerCase()).charAt(0);
                }

                game.clearConsole();
                
                if (game.playGuess(guess))
                {
                    System.out.println ("Good guess!");
                }
                else
                {
                    System.out.println ("Character not in the word.");
                }
            }
            while (!game.gameOver());

            System.out.println ();
            System.out.println ("Enter Y to keep playing"
                + " or anything else to exit.");
            Character response = (sc.next().toUpperCase()).charAt(0);
            doYouWantToPlay = (response == 'Y');
        }
        sc.close();
    }
    
    // initiate the game
    public void startGame() 
    {
        System.out.println ("Welcome to Hangman by Ritchie!");
        System.out.println ("Let's Begin!");
        System.out.println ();
        this.question();
    }
    
    // print the question out
    // this will repeat several times so better to make it into a method
    public void question() 
    {
        System.out.println (this.drawPicture());
        System.out.println ();
        System.out.println (this.getFormalCurrentGuess());
        System.out.println (this.mysteryWord);
    }
    
    // decide which action to take after the confirm button in the Main gui is clicked
    public boolean buttonDecider(char input, javax.swing.JTextArea area) 
    {
        area.setText(""); // clear the text area
        
        if (this.isGuessed(input))
        {
            System.out.println ("Try again. Character already guessed");
        }
        else 
        {
            if (this.playGuess(input))
            {
                System.out.println ("Good guess!");
            }
            else
            {
                System.out.println ("Character not in the word.");
            }
        }
        System.out.println(); // leave a line space
        if (this.gameOver()) 
        {
            System.out.println ();
            System.out.println ("Press the quit button to exit.");
            System.out.println("Press the reset button to replay.");
            return false;
        }
        else 
        {
            this.question(); // print the question
        }
        
        return true;
    }
}