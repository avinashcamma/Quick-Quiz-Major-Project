package com.example.ankit.quickquiz.DataModel;

/**
 * Created by Ankit on 4/4/2017.
 */

public class QuestionsModel {


    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    int answerOption;

    /**
     * AI ALGORITH PARAMETERS POSTED BACK AFTER THE GAME ENDS
     */
    int correctHits;
    int difficultyLevel;
    int effeciency;
    int wrongHits;
    String category;
    int categoryId = 0;
    int questionId = 0;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public QuestionsModel() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(int answerOption) {
        this.answerOption = answerOption;
    }

    public int getCorrectHits() {
        return correctHits;
    }

    public void setCorrectHits(int correctHits) {
        this.correctHits = correctHits;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getEffeciency() {
        return effeciency;
    }

    public void setEffeciency(int effeciency) {
        this.effeciency = effeciency;
    }

    public int getWrongHits() {
        return wrongHits;
    }

    public void setWrongHits(int wrongHits) {
        this.wrongHits = wrongHits;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public QuestionsModel(String question, String option1, String option2,
                          String option3, String option4, int answerOption,
                          int correctHits, int difficultyLevel, int effeciency,
                          int wrongHits, String category) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerOption = answerOption;
        this.correctHits = correctHits;
        this.difficultyLevel = difficultyLevel;
        this.effeciency = effeciency;
        this.wrongHits = wrongHits;
        this.category = category;
    }
}
