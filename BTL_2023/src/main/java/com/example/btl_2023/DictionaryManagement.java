package com.example.btl_2023;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Path;

/*
Bùi Tiến Thành X Quang Anh - Từ Điển Tiếng Anh - A+ hehehe
*/


public class DictionaryManagement {
    private final Dictionary dictionary;
    private final Scanner scanner;
    private TextField wordInputField;
    private TextArea gameTextArea;

    // Constructor
    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.scanner = new Scanner(System.in);
    }

    // Hàm thêm từ từ command line
    public void insertFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số lượng từ vựng: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            scanner.nextLine(); // consume the newline character
            System.out.print("Nhập từ Tiếng Anh: ");
            String wordTarget = scanner.nextLine();
            System.out.print("Nhập giải thích Tiếng Việt: ");
            String wordExplain = scanner.nextLine();

            Word word = new Word(wordTarget, wordExplain);
            dictionary.addWord(word);
        }
    }

    // Hàm thêm từ từ tệp dictionaries.txt
    public void insertFromFile() {
        try {
            // Lấy đường dẫn tuyệt đối của thư mục làm việc và kết hợp với tên tệp
            Path currentWorkingDirectory = Path.of("").toAbsolutePath();
            Path filePath = currentWorkingDirectory.resolve("src/main/java/com/example/btl_2023/dictionaries.txt");

            // Đọc dữ liệu từ tệp và thêm vào từ điển
            List<String> vocabDataList = Files.readAllLines(filePath);
            for (String vocabData : vocabDataList) {
                String[] data = vocabData.split("  ");  // Sửa lại dấu cách để phù hợp với định dạng từ điển
                if (data.length == 2) {
                    String wordTarget = data[0];
                    String wordExplain = data[1];
                    Word word = new Word(wordTarget, wordExplain);
                    dictionary.addWord(word);
                }
            }

            System.out.println("Imported from file successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> dictionaryLookupFromUI(String search) {
        List<String> translations = new ArrayList<>();

        for (Word word : dictionary.getWordList()) {
            if (word.getWordTarget().toLowerCase().startsWith(search.toLowerCase()) ||
                    word.getWordExplain().toLowerCase().startsWith(search.toLowerCase())) {
                translations.add(word.getWordTarget() + " - " + word.getWordExplain());
            }
        }

        return translations;
    }

    // Trong class DictionaryManagement
    public List<String> dictionaryLookup(String search) {
        List<String> translations = new ArrayList<>();

        for (Word word : dictionary.getWordList()) {
            if (word.getWordTarget().toLowerCase().startsWith(search.toLowerCase()) ||
                    word.getWordExplain().toLowerCase().startsWith(search.toLowerCase())) {
                translations.add(word.getWordTarget() + " - " + word.getWordExplain());
            }
        }

        return translations;
    }





    public void addWord() {
        System.out.print("Nhập từ Tiếng Anh mới: ");
        String wordTarget = scanner.nextLine();

        // Kiểm tra xem từ tiếng Anh đã tồn tại trong từ điển chưa
        if (dictionary.containsWord(wordTarget)) {
            System.out.println("Từ '" + wordTarget + "' đa tồn tại trong từ điển.");
            return;
        }

        System.out.print("Nhập giải thích Tiếng Việt cho từ '" + wordTarget + "': ");
        String wordExplain = scanner.nextLine();

        // Tạo đối tượng Word mới và thêm vào từ điển
        Word newWord = new Word(wordTarget, wordExplain);
        dictionary.addWord(newWord);

        System.out.println("Đã thêm từ '" + wordTarget + "' vào từ điển.");
        dictionaryExportToFile();// xuất dữ liệu ra file txt sau khi thêm từ
    }

    public void addWord(String word, String meaning) {
        dictionary.addWord(word, meaning);
    }



    // Hàm xóa từ khỏi từ điển
    public void removeWord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập từ Tiếng Anh cần xóa: ");
        String wordTarget = scanner.nextLine();

        Iterator<Word> iterator = dictionary.getWordList().iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                iterator.remove();
                System.out.println("Đã xóa từ '" + wordTarget + "' khỏi từ điển.");
                dictionaryExportToFile(); // xuất dữ liệu ra file txt sau khi xóa từ
                return;
            }
        }

        System.out.println("Không tìm thấy từ '" + wordTarget + "' trong từ điển.");

    }

    public boolean removeWord(String wordTarget) {
        Iterator<Word> iterator = dictionary.getWordList().iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }



    // Hàm sửa từ trong từ điển
    public void editWord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập từ Tiếng Anh cần sửa: ");
        String wordTarget = scanner.nextLine();

        if (dictionary.containsWordIgnoreCase(wordTarget)) {
            for (Word word : dictionary.getWordList()) {
                if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                    System.out.println("Từ hiện tại: " + word.getWordTarget() + " - " + word.getWordExplain());

                    System.out.print("Nhập giải thích Tiếng Việt mới: ");
                    String newWordExplain = scanner.nextLine();

                    word.setWordExplain(newWordExplain);
                    System.out.println("Đã sửa từ '" + wordTarget + "' trong từ điển.");
                    dictionaryExportToFile(); // xuất dữ liệu ra file txt sau khi sửa từ
                    return;
                }
            }
        } else {
            System.out.println("Không tìm thấy từ '" + wordTarget + "' trong từ điển.");
        }
    }

    public boolean editWord(String wordTarget, String newExplain) {
        for (Word word : dictionary.getWordList()) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                word.setWordExplain(newExplain);
                return true; // Trả về true nếu sửa thành công
            }
        }
        return false; // Trả về false nếu không tìm thấy từ để sửa
    }




    // Hàm tìm kiếm các từ bắt đầu bằng một chuỗi
    public void dictionarySearcher() {
        try {
            System.out.print("Nhập chuỗi cần tìm kiếm: ");
            String searchString = scanner.nextLine().toLowerCase();

            boolean found = false;

            for (Word word : dictionary.getWordList()) {
                if (word.getWordTarget().toLowerCase().startsWith(searchString)) {
                    System.out.format("%-15s | %-10s \n", "English", "Vietnamese");
                    System.out.format("%-15s | %-10s \n", word.getWordTarget(), word.getWordExplain());
                    System.out.println("_____________________________");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy từ nào bắt đầu bằng chuỗi '" + searchString + "'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void dictionaryExportToFile() {
        FileWriter fileWriter = null;
        try {
            // Lấy đường dẫn tuyệt đối của thư mục làm việc và kết hợp với tên tệp
            Path currentWorkingDirectory = Path.of("").toAbsolutePath();
            Path filePath = currentWorkingDirectory.resolve("src/main/java/com/example/btl_2023/dictionaries.txt");

            // Tạo đối tượng FileWriter để ghi dữ liệu vào tệp
            fileWriter = new FileWriter(filePath.toString());

            // Lấy danh sách từ điển
            List<Word> wordList = dictionary.getWordList();

            // Ghi từng từ vào tệp
            for (Word word : wordList) {
                fileWriter.write(word.getWordTarget() + "  " + word.getWordExplain() + "\n");
            }

            System.out.println("Xuất dữ liệu từ điển thành công.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng FileWriter ngay cả khi có lỗi
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Phương thức trả về đối tượng Dictionary
    public Dictionary getDictionary() {
        return dictionary;
    }

    // Hàm phát âm từ Tiếng Anh
    // Hàm phát âm từ Tiếng Anh
    public void pronounceWord(String wordToPronounce) {
        if (dictionary.containsWord(wordToPronounce)) {
            Speech.speak(wordToPronounce);
        } else {
            System.out.println("Từ '" + wordToPronounce + "' không tồn tại trong từ điển.");
        }
    }

    // Hàm phát âm từ Tiếng Anh từ UI
    public void dictionaryPronounceFromUI() {
        try {
            System.out.print("Nhập từ cần phát âm: ");
            String wordToPronounce = scanner.nextLine().trim();
            pronounceWord(wordToPronounce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm sử dụng API Google Translate để dịch từ
    public void translateWithGoogleAPI() {
        System.out.print("Nhập từ cần dịch (Tiếng Anh): ");
        String wordToTranslate = scanner.nextLine().trim();

        try {
            String translatedText = TranslatorAPIwithScripts.translate("en", "vi", wordToTranslate);
            System.out.println("Nghĩa Tiếng Việt: " + translatedText);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi dịch từ.");
        }
    }

    // Thêm phương thức này để có thể lấy kết quả từ bên ngoài
    public String getLookupResult() {
        String lookupResult = null;
        return lookupResult;
    }

    // Thêm phương thức này để có thể đặt kết quả từ bên trong
    private void setLookupResult(String result) {
        String lookupResult = result;
    }

    // Inside DictionaryManagement class
    public void playWordChainGame(TextField wordInputField, TextArea gameTextArea) {
        // Logic chơi trò chơi Word Chain với giao diện người dùng
        // Sử dụng giá trị từ các tham số wordInputField và gameTextArea
        // Thay vì sử dụng các trường thành viên

        String[] currentWord = {getRandomWordFromDictionary()};  // Wrap in an array to make it mutable

        gameTextArea.appendText("Chào mừng đến với trò chơi Word Chain!\n");
        gameTextArea.appendText("Mỗi người chơi nối một từ mới vào từ trước đó bằng cách sử dụng chữ cái cuối cùng của từ trước đó.\n");
        gameTextArea.appendText("Nhập 'exit' để kết thúc trò chơi.\n");
        gameTextArea.appendText("Nhập từ đầu tiên: " + currentWord[0] + "\n");

        wordInputField.setOnAction(e -> {
            String userInput = wordInputField.getText().toLowerCase();

            if (userInput.equals("exit")) {
                gameTextArea.appendText("Kết thúc trò chơi. Cảm ơn mọi người đã chơi!\n");
            } else if (isValidWord(currentWord[0], userInput)) {
                currentWord[0] = userInput;
                gameTextArea.appendText("Người chơi tiếp theo, nhập từ mới: " + userInput + "\n");
                wordInputField.clear();  // Xóa nội dung của TextField
            } else {
                gameTextArea.appendText("Từ bạn nhập không hợp lệ. Hãy thử lại!\n");
                wordInputField.clear();  // Xóa nội dung của TextField
            }
        });
    }

// Rest of your class...

    private boolean isValidWord(String currentWord, String newWord) {
        return currentWord.charAt(currentWord.length() - 1) == newWord.charAt(0);
    }

    private String getRandomWordFromDictionary() {
        List<Word> wordList = getDictionary().getWordList();
        if (!wordList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(wordList.size());
            return wordList.get(randomIndex).getWordTarget();
        } else {
            // Return a default value or handle the case where the dictionary is empty
            return "defaultWord";
        }
    }

    // Phương thức để lấy danh sách tất cả các từ
    public List<String> getAllWords() {
        List<String> allWords = new ArrayList<>();
        for (Word word : dictionary.getWordList()) {
            allWords.add(word.getWordTarget());
        }
        return allWords;
    }

}