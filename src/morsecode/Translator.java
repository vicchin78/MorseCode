package morsecode;

import java.util.HashMap;

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
		text = text.toUpperCase();

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
}
