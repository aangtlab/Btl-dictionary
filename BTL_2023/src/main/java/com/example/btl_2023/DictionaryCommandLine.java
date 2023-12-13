package com.example.btl_2023;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class DictionaryCommandLine {
    private final DictionaryManagement dictionaryManagement;

    public DictionaryCommandLine(DictionaryManagement dictionaryManagement) {
        this.dictionaryManagement = dictionaryManagement;
    }

    public void showAllWords() {
        System.out.format("%-4s | %-13s | %-5s\n", "No", "English", "Vietnamese");

        int index = 1;
        for (Word word : dictionaryManagement.getDictionary().getWordList()) {
            System.out.format("%-4s | %-13s | %-5s\n", index, word.getWordTarget(), word.getWordExplain());
            index++;
        }
    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandLine();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to My Application!");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.println("[10] Pronounce Word");
            System.out.println("[11] Translate Word");
            System.out.print("Your action: ");
            choice = scanner.nextInt();

            scanner.nextLine(); // consume newline

            String wordToTranslate;
            switch (choice) {
                case 0:
                    System.out.println("Exiting the application.");
                    break;
                case 1:
                    dictionaryManagement.addWord();
                    break;
                case 2:
                    dictionaryManagement.removeWord();
                    break;
                case 3:
                    dictionaryManagement.editWord();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    System.out.print("Enter word to lookup: ");
                    wordToTranslate = scanner.nextLine();
                    dictionaryManagement.dictionaryLookup(wordToTranslate);
                    break;
                case 6:
                    dictionaryManagement.dictionarySearcher();
                    break;
                case 7:
                    //dictionaryManagement.playWordChainGame();
                    break;
                case 8:
                    dictionaryManagement.insertFromFile();
                    break;
                case 9:
                    dictionaryManagement.dictionaryExportToFile();
                    break;
                case 10:
                    System.out.print("Nhập từ cần phát âm: ");
                    String wordToPronounce = scanner.nextLine();
                    dictionaryManagement.pronounceWord(wordToPronounce);

                    break;
                case 11:
                    dictionaryManagement.translateWithGoogleAPI();
                    break;
                default:
                    System.out.println("Action not supported.");
            }
        } while (choice != 0);
    }

}
