package gui;

import java.io.IOException;
import javax.swing.JTextArea;
import java.io.OutputStream;


public class GUI{
    
    public static void main(String[] args)
    {
        intro(); 
    }
    
    public static void intro() {
        Intro form = new Intro();
        form.setVisible(true);
    }
    
   public void mainPage() {
        Main window = new Main();
        window.setVisible(true);
        window.app.startGame();
   }
}


class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
    
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
    
    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
