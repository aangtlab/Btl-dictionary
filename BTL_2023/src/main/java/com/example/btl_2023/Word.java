package com.example.btl_2023;


/*
Bùi Tiến Thành X Quang Anh - Từ Điển Tiếng Anh - A+ hehehe
*/

public class Word {
    private String wordTarget; // từ vựng tiếng Anh
    private String wordExplain; // giải nghĩa tiếng Việt

    // Constructor
    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    // Getters and Setters
    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
