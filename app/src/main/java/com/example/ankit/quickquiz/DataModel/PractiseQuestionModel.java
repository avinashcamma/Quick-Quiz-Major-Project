package com.example.ankit.quickquiz.DataModel;

import java.util.Date;

/**
 * Created by Ankit on 4/26/2017.
 */

public class PractiseQuestionModel {

    private int questionId;
    private String question;
    private String imageOneUrl;
    private String imageTwoUrl;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private byte answer;
    private String explanation;
    private Date dateAdded;
    private QuestionCategory category;
    private int difficultyLevel;
    private int correctHits;
    private int wrongHits;
    private float efficiency;
    private int totalAttempts;
}
