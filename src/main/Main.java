package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame(); //create the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //lets the window close when user clicks the ("x") button.
        window.setResizable(false);
        window.setTitle("Shrine Of The Forgotten");

        //note to self: gamePanel is a subclass of JPanel.. just that it has some additional functionality.
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //automatically sizes the window to fit the preferred sizes and layouts of its subcomponents (GamePanel).

        window.setLocationRelativeTo(null); //makes the window display at the center of the screen
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}