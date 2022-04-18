package uj.pwj2020.introduction;

import java.util.Arrays;

public class Banner {

    private void addEmptySpace(String[] lettersRows) {
        lettersRows[0] += " ";
        lettersRows[1] += " ";
        lettersRows[2] += " ";
        lettersRows[3] += " ";
        lettersRows[4] += " ";
        lettersRows[5] += " ";
        lettersRows[6] += " ";
    }

    private void convertLetter (String[] lettersRows, char letter) {
        //input validation
        char properLetter = Character.toLowerCase(letter);

        switch (properLetter) {
            case 'a':
                lettersRows[0] += "   #   ";
                lettersRows[1] += "  # #  ";
                lettersRows[2] += " #   # ";
                lettersRows[3] += "#     #";
                lettersRows[4] += "#######";
                lettersRows[5] += "#     #";
                lettersRows[6] += "#     #";
                break;
            case 'b':
                lettersRows[0] += "###### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "###### ";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += "###### ";
                break;
            case 'c':
                lettersRows[0] += " ##### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#      ";
                lettersRows[3] += "#      ";
                lettersRows[4] += "#      ";
                lettersRows[5] += "#     #";
                lettersRows[6] += " ##### ";
                break;
            case 'd':
                lettersRows[0] += "###### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "#     #";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += "###### ";
                break;
            case 'e':
                lettersRows[0] += "#######";
                lettersRows[1] += "#      ";
                lettersRows[2] += "#      ";
                lettersRows[3] += "#####  ";
                lettersRows[4] += "#      ";
                lettersRows[5] += "#      ";
                lettersRows[6] += "#######";
                break;
            case 'f':
                lettersRows[0] += "#######";
                lettersRows[1] += "#      ";
                lettersRows[2] += "#      ";
                lettersRows[3] += "#####  ";
                lettersRows[4] += "#      ";
                lettersRows[5] += "#      ";
                lettersRows[6] += "#      ";
                break;
            case 'g':
                lettersRows[0] += " ##### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#      ";
                lettersRows[3] += "#  ####";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += " ##### ";
                break;
            case 'h':
                lettersRows[0] += "#     #";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "#######";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += "#     #";
                break;
            case 'i':
                lettersRows[0] += "###";
                lettersRows[1] += " # ";
                lettersRows[2] += " # ";
                lettersRows[3] += " # ";
                lettersRows[4] += " # ";
                lettersRows[5] += " # ";
                lettersRows[6] += "###";
                break;
            case 'j':
                lettersRows[0] += "      #";
                lettersRows[1] += "      #";
                lettersRows[2] += "      #";
                lettersRows[3] += "      #";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += " ##### ";
                break;
            case 'k':
                lettersRows[0] += "#    #";
                lettersRows[1] += "#   # ";
                lettersRows[2] += "#  #  ";
                lettersRows[3] += "###   ";
                lettersRows[4] += "#  #  ";
                lettersRows[5] += "#   # ";
                lettersRows[6] += "#    #";
                break;
            case 'l':
                lettersRows[0] += "#      ";
                lettersRows[1] += "#      ";
                lettersRows[2] += "#      ";
                lettersRows[3] += "#      ";
                lettersRows[4] += "#      ";
                lettersRows[5] += "#      ";
                lettersRows[6] += "#######";
                break;
            case 'm':
                lettersRows[0] += "#     #";
                lettersRows[1] += "##   ##";
                lettersRows[2] += "# # # #";
                lettersRows[3] += "#  #  #";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += "#     #";
                break;
            case 'n':
                lettersRows[0] += "#     #";
                lettersRows[1] += "##    #";
                lettersRows[2] += "# #   #";
                lettersRows[3] += "#  #  #";
                lettersRows[4] += "#   # #";
                lettersRows[5] += "#    ##";
                lettersRows[6] += "#     #";
                break;
            case 'o':
                lettersRows[0] += "#######";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "#     #";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += "#######";
                break;
            case 'p':
                lettersRows[0] += "###### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "###### ";
                lettersRows[4] += "#      ";
                lettersRows[5] += "#      ";
                lettersRows[6] += "#      ";
                break;
            case 'q':
                lettersRows[0] += " ##### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "#     #";
                lettersRows[4] += "#   # #";
                lettersRows[5] += "#    # ";
                lettersRows[6] += " #### #";
                break;
            case 'r':
                lettersRows[0] += "###### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "###### ";
                lettersRows[4] += "#   #  ";
                lettersRows[5] += "#    # ";
                lettersRows[6] += "#     #";
                break;
            case 's':
                lettersRows[0] += " ##### ";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#      ";
                lettersRows[3] += " ##### ";
                lettersRows[4] += "      #";
                lettersRows[5] += "#     #";
                lettersRows[6] += " ##### ";
                break;
            case 't':
                lettersRows[0] += "#######";
                lettersRows[1] += "   #   ";
                lettersRows[2] += "   #   ";
                lettersRows[3] += "   #   ";
                lettersRows[4] += "   #   ";
                lettersRows[5] += "   #   ";
                lettersRows[6] += "   #   ";
                break;
            case 'u':
                lettersRows[0] += "#     #";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "#     #";
                lettersRows[4] += "#     #";
                lettersRows[5] += "#     #";
                lettersRows[6] += " ##### ";
                break;
            case 'w':
                lettersRows[0] += "#     #";
                lettersRows[1] += "#  #  #";
                lettersRows[2] += "#  #  #";
                lettersRows[3] += "#  #  #";
                lettersRows[4] += "#  #  #";
                lettersRows[5] += "#  #  #";
                lettersRows[6] += " ## ## ";
                break;
            case 'v':
                lettersRows[0] += "#     #";
                lettersRows[1] += "#     #";
                lettersRows[2] += "#     #";
                lettersRows[3] += "#     #";
                lettersRows[4] += " #   # ";
                lettersRows[5] += "  # #  ";
                lettersRows[6] += "   #   ";
                break;
            case 'x':
                lettersRows[0] += "#     #";
                lettersRows[1] += " #   # ";
                lettersRows[2] += "  # #  ";
                lettersRows[3] += "   #   ";
                lettersRows[4] += "  # #  ";
                lettersRows[5] += " #   # ";
                lettersRows[6] += "#     #";
                break;
            case 'y':
                lettersRows[0] += "#     #";
                lettersRows[1] += " #   # ";
                lettersRows[2] += "  # #  ";
                lettersRows[3] += "   #   ";
                lettersRows[4] += "   #   ";
                lettersRows[5] += "   #   ";
                lettersRows[6] += "   #   ";
                break;
            case 'z':
                lettersRows[0] += "#######";
                lettersRows[1] += "     # ";
                lettersRows[2] += "    #  ";
                lettersRows[3] += "   #   ";
                lettersRows[4] += "  #    ";
                lettersRows[5] += " #     ";
                lettersRows[6] += "#######";
                break;
            case ' ':
                lettersRows[0] += "   ";
                lettersRows[1] += "   ";
                lettersRows[2] += "   ";
                lettersRows[3] += "   ";
                lettersRows[4] += "   ";
                lettersRows[5] += "   ";
                lettersRows[6] += "   ";
                break;

        }
    }

    public String[] toBanner(String input) {
        if (input == null) {
            return new String[] {};
        }

        String[] lettersRows = new String[7];
        Arrays.fill(lettersRows, "");

        for (char x : input.toCharArray()) {
            convertLetter(lettersRows, x);
            if (x != ' ') {
                addEmptySpace(lettersRows);
            }
        }

        for (String x : lettersRows) {
            x.substring(0, x.length() - 1);
            //System.out.println(x);
        }
        return lettersRows;
    }
}