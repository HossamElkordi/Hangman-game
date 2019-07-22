package theGame;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Hangman {

	private JFrame frmHangman;
	String chosen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hangman window = new Hangman();
					window.frmHangman.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public Hangman() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHangman = new JFrame();
		frmHangman.setIconImage(Toolkit.getDefaultToolkit().getImage(Hangman.class.getResource("/theGame/hangman.jpg")));
		frmHangman.setTitle("Hangman");
		frmHangman.setBounds(400, 250, 425, 77);
		frmHangman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHangman.getContentPane().setLayout(null);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chosen != null) {
					frmHangman.dispose();
					GameInterface o = new GameInterface(chosen);
					o.setVisible(true);
				}
			}
		});
		btnEnter.setBounds(298, 9, 89, 23);
		frmHangman.getContentPane().add(btnEnter);
		
		JLabel lblChoosing = new JLabel("Choose a dictionary:");
		lblChoosing.setBounds(10, 11, 119, 19);
		frmHangman.getContentPane().add(lblChoosing);
		
		String[] options = comboOptions();
		
		JComboBox comboBoxDics = new JComboBox(options);
		comboBoxDics.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int index = comboBoxDics.getSelectedIndex();
					if (index != 0 && index != -1) {
						chosen = options[index];
					}
				}
			}
		});
		comboBoxDics.setBounds(135, 10, 153, 20);
		frmHangman.getContentPane().add(comboBoxDics);
	}
	/*
	 * Get dictionary files from folder
	 * */
	private String[] dictionaryContent() {
		return new File("Dictionary\\").list();
	}
	/*
	 * Set comboBox options
	 * */
	private String[] comboOptions() {
		String[] content = dictionaryContent();
		String[] dics = new String[content.length + 1];
		for (int i = 0; i < dics.length; i++) {
			if (i == 0) {
				dics[i] = "Choose a dictionary";
			}else {
				dics[i] = content[i - 1].substring(0, content[i - 1].indexOf('.'));
			}
		}
		return dics;
	}
}
