package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import backend.HangmanApp;


public class GUI {
    
    private static JButton button;
    private static JLabel code;
    private static JLabel label;
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;
    private static StringBuilder strBuilder = new StringBuilder();
    static String display;
    
    public static void mainPage() throws IOException
    {
        JFrame codeFrame = new JFrame("Hangman Game");
        JPanel codePanel = new JPanel();

        codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.PAGE_AXIS));

        label.setText("Hangman Game code by Ritchie");
        codePanel.add(label);

        code = new JLabel();
        code.setText(getCode());
        code.setBounds(500, 400, 10, 10);
        codePanel.add(code);

        codeFrame.add(codePanel);
        codeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        codeFrame.setSize(600, 500);

        codeFrame.setVisible(true);
    }

    public static String getCode() throws IOException
    {
        try
        {
            File inFile = new File("Code.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader (fileReader);
            String temp;

            while (true)
            {
                temp = bufferedFileReader.readLine();
                strBuilder.append("<html>");
                //I know this is a problem, because it breaks when it reads an empty line (line spacings)
                //but the main problem is why after it breaks the code still can read other lines
                if (bufferedFileReader.readLine() == null)
                {
                    break;
                }
                strBuilder.append("<br/>").append(temp.replaceAll("\t", "    "));
            }
            strBuilder.append("<html/>");
            display = strBuilder.toString();
            bufferedFileReader.close();
            fileReader.close();
        }
        catch (IOException e)
        {
            display = "Cannot find file.";
        }
        return display;
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Ritchie Game Code");
        JPanel panel = new JPanel();
        
        label = new JLabel();
        label.setText("Press the button to continue");
        label.setBounds(10, 20, 200, 30);
        panel.add(label);

        button = new JButton("Hangman Game");
        button.setBounds (10, 50, 200, 30);
        button.addActionListener(new ActionForButton());
        panel.add(button);

        panel.setLayout(null);

        frame.setSize(500, 400);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


class ActionForButton implements ActionListener {

    //This is the button function
    @Override
    public void actionPerformed (ActionEvent e)
    {
        try
        {
            GUI.mainPage();
        }
        catch (IOException e1)
        {
            System.out.println ("File cannot be found");
        }
    }

    
}
