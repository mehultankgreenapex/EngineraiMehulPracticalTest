package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StoryText {

    @SerializedName("matchLevel")
    private String matchLevel;

    @SerializedName("value")
    private String value;

    @SerializedName("matchedWords")
    private List<Object> matchedWords;

    public String getMatchLevel() {
        return matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Object> getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(List<Object> matchedWords) {
        this.matchedWords = matchedWords;
    }

    @Override
    public String toString() {
        return
                "StoryText{" +
                        "matchLevel = '" + matchLevel + '\'' +
                        ",value = '" + value + '\'' +
                        ",matchedWords = '" + matchedWords + '\'' +
                        "}";
    }
}