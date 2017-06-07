package com.example.ankit.quickquiz.DataModel;

/**
 * Created by Ankit on 4/26/2017.
 */

public class QuestionCategory {

    private int categoryId;
    private String category;
    private int time;

    public QuestionCategory() {
    }

    public QuestionCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

