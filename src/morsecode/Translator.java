package morsecode;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Translator {

	private HashMap<Character, String> map = new HashMap<Character, String>();
	private HashMap<String, Character> reverseMap = new HashMap<String, Character>();

	public Translator() {
		map.put('A', ".-");
		map.put('B', "-...");
		map.put('C', "-.-.");
		map.put('D', "-..");
		map.put('E', ".");
		map.put('F', "..-.");
		map.put('G', "--.");
		map.put('H', "....");
		map.put('I', "..");
		map.put('J', ".---");
		map.put('K', "-.-");
		map.put('L', ".-..");
		map.put('M', "--");
		map.put('N', "-.");
		map.put('O', "---");
		map.put('P', ".--.");
		map.put('Q', "--.-");
		map.put('R', ".-.");
		map.put('S', "...");
		map.put('T', "-");
		map.put('U', "..-");
		map.put('V', "...-");
		map.put('W', ".--");
		map.put('X', "-..-");
		map.put('Y', "-.--");
		map.put('Z', "--..");
		map.put('a', ".-");
		map.put('b', "-...");
		map.put('c', "-.-.");
		map.put('d', "-..");
		map.put('e', ".");
		map.put('f', "..-.");
		map.put('g', "--.");
		map.put('h', "....");
		map.put('i', "..");
		map.put('j', ".---");
		map.put('k', "-.-");
		map.put('l', ".-..");
		map.put('m', "--");
		map.put('n', "-.");
		map.put('o', "---");
		map.put('p', ".--.");
		map.put('q', "--.-");
		map.put('r', ".-.");
		map.put('s', "...");
		map.put('t', "-");
		map.put('u', "..-");
		map.put('v', "...-");
		map.put('w', ".--");
		map.put('x', "-..-");
		map.put('y', "-.--");
		map.put('z', "--..");
		map.put('1', ".----");
		map.put('2', "..---");
		map.put('3', "...--");
		map.put('4', "....-");
		map.put('5', ".....");
		map.put('6', "-....");
		map.put('7', "--...");
		map.put('8', "---..");
		map.put('9', "----.");
		map.put('0', "-----");
		map.put('.', ".-.-.-");
		map.put(',', "--..--");
		map.put('?', "..--..");
		map.put('!', "-.-.--");

		for (HashMap.Entry<Character, String> entry : map.entrySet()) {
			reverseMap.put(entry.getValue(), entry.getKey());
		}
	}

	public String translateToMorse(String text) {
		StringBuilder morse = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ' ') {
				morse.append(" / ");
			} else if (map.containsKey(c)) {
				morse.append(map.get(c)).append(" ");
			}
		}

		return morse.toString().trim();
	}

	public String translateFromMorse(String morseCode) {
		StringBuilder english = new StringBuilder();
		String[] words = morseCode.split(" / ");

		for (String word : words) {
			String[] letters = word.split("\\s+");
			for (String letter : letters) {
				if (reverseMap.containsKey(letter)) {
					english.append(reverseMap.get(letter));
				}
			}
			english.append(" ");
		}

		return english.toString().trim();
	}

	public void translateToAudio(String morseCode) throws LineUnavailableException, InterruptedException {
		AudioFormat audio = new AudioFormat(44100, 16, 1, true, false);
		int dot = 200;
		int dash = (int) (1.5 * dot);
		int slash = 2 * dash;
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audio);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sourceDataLine.open(audio);
		sourceDataLine.start();

		for (int i = 0; i < morseCode.length(); i++) {
			if (morseCode.charAt(i) == '.') {
				playBeep(sourceDataLine, dot);
				Thread.sleep(dot);

			} else if (morseCode.charAt(i) == '-') {
				playBeep(sourceDataLine, dash);
				Thread.sleep(dash);

			} else if (morseCode.charAt(i) == '/') {
				Thread.sleep(slash);
			}
		}

		sourceDataLine.drain();
		sourceDataLine.stop();
		sourceDataLine.close();

	}

	private void playBeep(SourceDataLine line, int duration) {
		byte[] data = new byte[duration * 44100 / 1000];

		for (int i = 0; i < data.length; i++) {
			double angle = i / (44100.0 / 440) * 2.0 * Math.PI;
			data[i] = (byte) (Math.sin(angle) * 127.0);
		}

		line.write(data, 0, data.length);
	}

}
