package uj.pwj2020.introduction;

public class Reverser {

    public String reverse(String input) {
        if (input == null) {
            return input;
        }

        String output = input.trim();
        output = new StringBuilder(output).reverse().toString();

        return output;
    }

    public String reverseWords(String input) {
        if (input == null) {
            return input;
        }

        String output = input.trim();
        
        String[] words = output.split("\\s+");

        output = "";

        for (int i = words.length - 1; i >= 0; i--) {
            output += words[i];
            output += " ";
        }

        output = output.trim();

        return output;
    }
}