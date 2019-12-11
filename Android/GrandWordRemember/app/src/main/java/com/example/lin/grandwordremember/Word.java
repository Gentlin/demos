package com.example.lin.grandwordremember;

public class Word {
    private String word;
    private String explanation;
    private int level;
    public Word(String w, String e, String level) {
        this.word = w;
        this.explanation = e;
        this.level = Integer.parseInt(level);
    }
    public String getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }

    public int getLevel() {
        return level;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
