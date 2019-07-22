package theGame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class GameInterface extends JFrame {

	private JPanel contentPane;
	public String chosenFileName;
	private JTextField textField;
	private JLabel lblCovered;
	private JLabel lblErrors;
	
	String[] words;
	int numOfWords;
	int wordPlayed = 0;
	int errors = 9;
	String gameWord;
	StringBuilder covered;
	String entry;
	String wrongEntry = "";
	boolean win = false;
	int wordWon = 0;
	private JLabel lblDoYouWant;
	private JButton btnYes;
	private JButton btnNo;
	private JLabel lblWrongEntries;
	private Drawing d;
	private JLabel lblWordPlayed;


	/**
	 * Create the frame.
	 */
	public GameInterface(String name) {
		setFont(new Font("Dialog", Font.PLAIN, 13));
		setTitle("Hangman");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameInterface.class.getResource("/theGame/hangman.jpg")));
		chosenFileName = name; 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 444, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterCharacter = new JLabel("Enter a character:");
		lblEnterCharacter.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterCharacter.setBounds(10, 30, 117, 14);
		contentPane.add(lblEnterCharacter);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(124, 28, 163, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entry = textField.getText();
				onEntry();
			}
		});
		btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEnter.setBounds(311, 27, 100, 23);
		contentPane.add(btnEnter);
		
		lblErrors = new JLabel("Text");
		lblErrors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblErrors.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrors.setText("You have " + errors + " chances remaining");
		lblErrors.setBounds(100, 115, 220, 45);
		contentPane.add(lblErrors);
		
		lblCovered = new JLabel("word");
		lblCovered.setHorizontalAlignment(SwingConstants.CENTER);
		lblCovered.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCovered.setBounds(100, 59, 220, 45);
		contentPane.add(lblCovered);
		
		lblDoYouWant = new JLabel("Do you want to continue?");
		lblDoYouWant.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDoYouWant.setBounds(21, 186, 157, 20);
		contentPane.add(lblDoYouWant);
		lblDoYouWant.setVisible(false);
		
		btnYes = new JButton("YES");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game(words);
				btnYes.setVisible(false);
				btnNo.setVisible(false);
				lblDoYouWant.setVisible(false);
			}
		});
		btnYes.setBounds(175, 186, 56, 23);
		contentPane.add(btnYes);
		btnYes.setVisible(false);
		
		btnNo = new JButton("NO");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNo.setBounds(242, 186, 62, 23);
		contentPane.add(btnNo);
		btnNo.setVisible(false);
		
		lblWrongEntries = new JLabel("Wrong entries: " + wrongEntry);
		lblWrongEntries.setHorizontalAlignment(SwingConstants.CENTER);
		lblWrongEntries.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWrongEntries.setBounds(84, 155, 248, 20);
		contentPane.add(lblWrongEntries);
		
		d = new Drawing();
		d.setNum(errors);
		getContentPane().add(d);
		
		lblWordPlayed = new JLabel("Word played: ");
		lblWordPlayed.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWordPlayed.setBounds(314, 186, 114, 20);
		contentPane.add(lblWordPlayed);
		
		words = words(new File("Dictionary\\" + chosenFileName + ".txt"));
		numOfWords = words.length;
		game(words);
	}
	
	private String[] words(File file) {
		int size = countWords(file);
		String[] content = new String[size];
		int i = 0;
		try(Scanner scan = new Scanner(file)) {
			while(scan.hasNext()) {
				content[i++] = scan.nextLine();
			}
		} catch(Exception e) {
			throw new FileSystemNotFoundException();
		}
		
		return content;
	}
	
	private int countWords(File file) {
		int count = 0;
		
		try(Scanner scan = new Scanner(file)) {
			while(scan.hasNext()) {
				scan.nextLine();
				count++;
			}
		} catch(Exception e) {
			throw new FileSystemNotFoundException();
		}
				
		return count;
	}
	
	private int indexGenerator (int max) {
		Random rand = new Random();
		return rand.nextInt(max);
	}
	
	private StringBuilder coverWord(String word) {
		StringBuilder covered = new StringBuilder();
		
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != ' ') {
				covered.append('-');
			}else {
				covered.append(' ');
			}
		}
		return covered;
	}
	
	private void onEntry() {
		
		if (errors > 0) {
			
			if (entry.length() == 0 || entry.length() > 1 || entry.charAt(0) == ' ') {
				JOptionPane.showMessageDialog(null, "Enter a valid character");
				textField.setText("");
				return;
			}
			char uch = Character.toUpperCase(entry.charAt(0));
			char lch = Character.toLowerCase(entry.charAt(0));
			if (gameWord.indexOf(uch) != -1 || gameWord.indexOf(lch) != -1) {
				for (int i = 0; i < gameWord.length(); i++) {
					if (gameWord.charAt(i) == uch || gameWord.charAt(i) == lch) {
						if (covered.charAt(i) != '-') {
							JOptionPane.showMessageDialog(null, "You have entered this character before");
							textField.setText("");
							break;
						}else {
							covered.setCharAt(i, gameWord.charAt(i));
							lblCovered.setText(covered.toString());
							textField.setText("");
						}
					}
				}
			}else {
				if (wrongEntry.indexOf(entry) == -1) {
					errors--;
					d.setNum(errors);
					wrongEntry += (entry + " ");
					lblWrongEntries.setText("Wrong Entries: " + wrongEntry);
					lblErrors.setText("You have " + errors + " chances remaining");
				}else {
					JOptionPane.showMessageDialog(null, "You have entered this character before");
				}
				
				textField.setText("");
				if (errors <= 0) {
					JOptionPane.showMessageDialog(null, "You have lost! The word was " + gameWord);
					btnYes.setVisible(true);
					btnNo.setVisible(true);
					lblDoYouWant.setVisible(true);
				}
			}
			for (int i = 0; i < gameWord.length(); i++) {
				if (covered.charAt(i) == '-') {
					break;
				}
				if (i == gameWord.length()-1) {
					win = true;
				}
			}
			if(win) {
				JOptionPane.showMessageDialog(null, "You WIN!!");
				wordWon++;
				btnYes.setVisible(true);
				btnNo.setVisible(true);
				lblDoYouWant.setVisible(true);
			}
		}
	}
	
	private void game (String[] words) {
		if (numOfWords <= 0) {
			JOptionPane.showMessageDialog(null, "You completed this level, you got " + wordWon + " out of " + words.length);
			dispose();
			return;
		}
		win = false;
		errors = 9;
		d.setNum(errors);
		wrongEntry = "";
		lblWrongEntries.setText("Wrong Entries: " + wrongEntry);
		lblErrors.setText("You have " + errors + " chances remaining");
		wordPlayed++;
		lblWordPlayed.setText("Word played: " + wordPlayed);
		if(numOfWords > 0) {
			int index = indexGenerator (words.length);
			while (words[index] == "") {
				index = indexGenerator (words.length);
				continue;
			}
			gameWord = words[index];
			words[index] = "";
			covered = coverWord(gameWord);
			lblCovered.setText(covered.toString());
			numOfWords--;
		}			
	}

}
