package gui;

import java.io.IOException;
import javax.swing.JTextArea;
import java.io.OutputStream;


public class GUI
{
    
    public static void main(String[] args)
    {
        intro(); 
    }
    
    public static void intro() 
    {
        Intro form = new Intro(); // run the intro gui part
        form.setVisible(true); // make the frame visible
    }
    
    // mainPage method is run after a button clicks in intro gui
    // refer intro gui before looking at this method
    public void mainPage() 
    {
         Main window = new Main();
         window.setVisible(true);
         window.app.startGame();
    }
}


// overwrite the method to redirect standard output into the gui
// in other words: make System.out to output into the gui but not to the terminal
// this process is not done yet, continue in Main constructor
class CustomOutputStream extends OutputStream 
{
    private JTextArea textArea;
    
    public CustomOutputStream(JTextArea textArea) 
    {
        this.textArea = textArea;
    }
    
    @Override
    public void write(int b) throws IOException 
    {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
