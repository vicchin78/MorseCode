package morsecode;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class GUI implements ActionListener {

	private JTextArea text, morse;
	private JLabel enterText;
	private JButton translateToMorse, translateToEnglish, playAudio;
	private static String word, morseWord;
	private Translator translator = new Translator();

	public static void main(String[] args) {
		new GUI();
	}

	public GUI() {
		JFrame frame = new JFrame("Morse Code Solver");
		frame.setVisible(true);
		frame.setBounds(0, 0, 1920, 1080);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container container = frame.getContentPane();
		container.setLayout(null);

		text = new JTextArea("ENTER ENGLISH TEXT HERE...", 50, 50);
		text.setBounds(50, frame.getY() / 2, frame.getWidth() - 100, 100);
		text.setFont(new Font(("Serif"), Font.BOLD, 30));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		container.add(text);

		enterText = new JLabel("Enter Phrase to be Translated to Morse Code:");
		enterText.setFont(new Font(("Serif"), Font.BOLD, 50));
		enterText.setBounds(50, text.getY() - 70, frame.getWidth(), 50);
		container.add(enterText);

		translateToMorse = new JButton("Translate to Morse Code");
		translateToMorse.setBounds(50, text.getY() + 125, 200, 50);
		translateToMorse.addActionListener(this);
		container.add(translateToMorse);

		morseWord = translator.translateToMorse(text.getText());
		morse = new JTextArea(morseWord, 50, 50);
		morse.setBounds(50, text.getY() + 300, frame.getWidth() - 100, 100);
		morse.setFont(new Font(("Serif"), Font.BOLD, 30));
		morse.setLineWrap(true);
		morse.setWrapStyleWord(true);
		container.add(morse);

		translateToEnglish = new JButton("Translate to English Alphabet");
		translateToEnglish.setBounds(50, morse.getY() + 125, 200, 50);
		translateToEnglish.addActionListener(this);
		container.add(translateToEnglish);

		playAudio = new JButton("Listen as Audio");
		playAudio.setBounds(frame.getX() - morse.getX(), morse.getY() + 125, 150, 50);
		playAudio.addActionListener(this);
		container.add(playAudio);

		container.setBackground(Color.cyan);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == translateToMorse) {
			word = text.getText();
			morseWord = translator.translateToMorse(word);
			morse.setText(morseWord);
		}
		if (e.getSource() == translateToEnglish) {
			morseWord = morse.getText();
			word = translator.translateFromMorse(morseWord);
			text.setText(word);
		}
		if (e.getSource() == playAudio) {
			try {
				translator.translateToAudio(morseWord);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
