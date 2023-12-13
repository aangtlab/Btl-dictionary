package com.example.btl_2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Dictionary {
    private final List<Word> wordList;

    // Constructor
    public Dictionary() {
        this.wordList = new ArrayList<>();
    }

    // Thêm từ vào từ điển
    public void addWord(Word word) {
        wordList.add(word);
    }

    // Kiểm tra xem từ tiếng Anh đã tồn tại trong từ điển chưa
    public boolean containsWord(String wordTarget) {
        for (Word word : wordList) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                return true;
            }
        }
        return false;
    }

    // Getter cho danh sách từ
    public List<Word> getWordList() {
        return wordList;
    }

    // Xóa tất cả các từ trong từ điển
    public void clear() {
        wordList.clear();
    }

    // Thêm từ vào từ điển (với cách sửa)
    public void addWord(String wordTarget, String wordExplain) {
        Word newWord = new Word(wordTarget, wordExplain);
        addWord(newWord);
    }

    // Kiểm tra xem từ tiếng Anh đã tồn tại trong từ điển chưa (không phân biệt chữ hoa chữ thường)
    public boolean containsWordIgnoreCase(String wordTarget) {
        for (Word word : wordList) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                return true;
            }
        }
        return false;
    }


    // Xóa một từ theo từ khóa (target) mà không phân biệt chữ hoa chữ thường
    public void removeWordByTargetIgnoreCase(String wordTarget) {
        Iterator<Word> iterator = wordList.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                iterator.remove();
                return;
            }
        }
    }
}
