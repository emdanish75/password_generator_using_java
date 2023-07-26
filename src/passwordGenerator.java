import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class passwordGenerator extends JFrame implements ActionListener {
    // Declare GUI components as static class members
    static JLabel lettersLabel, numbersLabel, symbolsLabel, suggestedPasswordLabel, invalidInputLabel;
    static JTextField letters_textfield, numbers_textfield, symbols_textfield, password_textfield;
    static JButton generate, copy;
    passwordGenerator() {
        // Set up the JFrame properties
        setBounds(300,50,800,650);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Password Generator");

        // Add JLabels for user instructions
        lettersLabel = new JLabel("How many letters do you want in your password?");
        lettersLabel.setFont(new Font("Candara", Font.BOLD, 20));
        lettersLabel.setBounds(50,50,440,30);
        add(lettersLabel);

        numbersLabel = new JLabel("How many numbers do you want in your password?");
        numbersLabel.setFont(new Font("Candara", Font.BOLD, 20));
        numbersLabel.setBounds(50,130,440,30);
        add(numbersLabel);

        symbolsLabel = new JLabel("How many symbols do you want in your password?");
        symbolsLabel.setFont(new Font("Candara", Font.BOLD, 20));
        symbolsLabel.setBounds(50,210,440,30);
        add(symbolsLabel);

        // Add JTextFields for user input
        letters_textfield = new JTextField();
        letters_textfield.setFont(new Font("Candara", Font.BOLD, 20));
        letters_textfield.setBounds(560,45,80,30);
        add(letters_textfield);

        numbers_textfield = new JTextField();
        numbers_textfield.setFont(new Font("Candara", Font.BOLD, 20));
        numbers_textfield.setBounds(560,125,80,30);
        add(numbers_textfield);

        symbols_textfield = new JTextField();
        symbols_textfield.setFont(new Font("Candara", Font.BOLD, 20));
        symbols_textfield.setBounds(560,205,80,30);
        add(symbols_textfield);

        // Add the "Generate Password" JButton
        generate = new JButton("Generate Password");
        generate.setFont(new Font("Candara", Font.BOLD, 20));
        generate.setBounds(285,300,200,50);
        generate.addActionListener(this);
        add(generate);

        // Add the "Suggested Password" JLabel
        suggestedPasswordLabel = new JLabel("Suggested Password:");
        suggestedPasswordLabel.setFont(new Font("Candara", Font.BOLD, 30));
        suggestedPasswordLabel.setBounds(245,390,300,50);
        add(suggestedPasswordLabel);

        // Add the TextField that contains the generated password
        password_textfield = new JTextField();
        password_textfield.setFont(new Font("Candara", Font.BOLD, 20));
        password_textfield.setBounds(140,440,500,40);
        password_textfield.setHorizontalAlignment(JTextField.CENTER);
        password_textfield.setEditable(false);
        add(password_textfield);

        // Add a JLabel that shows the validity or invalidity of user input
        invalidInputLabel = new JLabel();
        invalidInputLabel.setFont(new Font("Candara", Font.BOLD, 20));
        invalidInputLabel.setBounds(210,560,400,50);
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setVisible(false);
        add(invalidInputLabel);

        // Add the "Copy password" button
        copy = new JButton("Copy Password!");
        copy.setFont(new Font("Candara", Font.BOLD, 20));
        copy.setBounds(285, 510, 200, 40);
        copy.addActionListener(this);
        add(copy);

        // Initially, disable the "Copy" button
        copy.setEnabled(false);

        // Show the frame
        setVisible(true);
    }

    // Enable or disable the "Copy" button based on whether the password text field has text
    private void updateCopyButtonState() {
        String password = password_textfield.getText().trim();
        copy.setEnabled(!password.isEmpty());
    }

    // Method to copy text to the clipboard
    private void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

        // Update the "Password copied" message and show it
        invalidInputLabel.setText("Password copied to clipboard!");
        invalidInputLabel.setBounds(260,560,400,50);
        invalidInputLabel.setForeground(Color.decode("#27ae60"));
        invalidInputLabel.setVisible(true);
    }

    public void actionPerformed (ActionEvent e) {

        if (e.getSource() == generate) {

            // Clear the password text field and hide the "Invalid Input" message
            password_textfield.setText("");
            invalidInputLabel.setVisible(false);

            // Collect user input for letters, numbers, and symbols
            String letters = letters_textfield.getText().trim();
            String numbers = numbers_textfield.getText().trim();
            String symbols = symbols_textfield.getText().trim();

            if (letters.isEmpty() || numbers.isEmpty() || symbols.isEmpty()) {

                // If any input field is empty, show the "Invalid Input" message
                invalidInputLabel.setText("Error: Please make sure all fields are filled.");
                invalidInputLabel.setBounds(210,560,400,50);
                invalidInputLabel.setForeground(Color.RED);
                invalidInputLabel.setVisible(true);

            } else {

                try {
                    // Parse the input strings to integers
                    int lettersFinal = Integer.parseInt(letters);
                    int numbersFinal = Integer.parseInt(numbers);
                    int symbolsFinal = Integer.parseInt(symbols);
                    int totalCharacters = lettersFinal + numbersFinal + symbolsFinal;

                    // Arrays to store characters for password generation
                    char[] lettersArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
                    char[] symbolsArray = {'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>',
                            '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
                    int[] numbersArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                    char[] passwordChars = new char[totalCharacters];

                    Random rnd = new Random();

                    // Collect letters
                    for (int i = 0; i < lettersFinal; i++) {
                        int index = rnd.nextInt(lettersArray.length);
                        passwordChars[i] = lettersArray[index];
                    }

                    // Collect numbers
                    for (int i = 0; i < numbersFinal; i++) {
                        int index = rnd.nextInt(numbersArray.length);
                        passwordChars[lettersFinal + i] = (char) (numbersArray[index] + '0');
                    }

                    // Collect symbols
                    for (int i = 0; i < symbolsFinal; i++) {
                        int index = rnd.nextInt(symbolsArray.length);
                        passwordChars[lettersFinal + numbersFinal + i] = symbolsArray[index];
                    }

                    // Shuffle the collected characters
                    for (int i = totalCharacters - 1; i > 0; i--) {
                        int index = rnd.nextInt(i + 1);
                        char temp = passwordChars[index];
                        passwordChars[index] = passwordChars[i];
                        passwordChars[i] = temp;
                    }

                    // Convert the character array into a String
                    String password = new String(passwordChars);
                    password_textfield.setText(password);

                } catch (NumberFormatException a) {

                    // Show an error message if invalid input is detected
                    invalidInputLabel.setText("Error: Please make sure to enter valid numbers.");
                    invalidInputLabel.setBounds(200, 560, 410, 50);
                    invalidInputLabel.setForeground(Color.RED);
                    invalidInputLabel.setVisible(true);

                }
            }
        } else if (e.getSource() == copy || e.getSource() == password_textfield) {
            // Handle the copy operation when the "copy" button is clicked
            String password = password_textfield.getText();
            copyToClipboard(password);
        }

        // Update the state of the "Copy" button
        updateCopyButtonState();
    }

    public static void main(String[] args) {
        new passwordGenerator();
    }
}
