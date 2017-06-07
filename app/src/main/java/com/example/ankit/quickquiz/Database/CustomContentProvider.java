package com.example.ankit.quickquiz.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by Ankit on 4/20/2017.
 */

public class CustomContentProvider {

    private Context mContext;

    private final String CATEGORY_ONE = "english_synonymous";
    private final String CATEGORY_TWO = "english_antonymous";
    private final String CATEGORY_THREE = "general_awareness";
    private final String CATEGORY_FOUR = "reasoning";

    private final String SP_ENGLISH_QUESTION_LIST = "englishQuestionList";
    private final String SP_GK_QUESTION_LIST = "gkQuestionList";
    private final String SP_REASONING_QUESTION_LIST = "reasoningQuestionList";
    private final String SP_NO_QUESTIONS = "noquestions";

    private String englishQuestions = "1 2 4 5 6 7 8";
    private String gkQuestions = "9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24";
    private String reasoningQuestions = "60 61 62 63 64 65 66 67 68 69 70 71 72";

    private String englishQuestionList = "";
    private boolean isEnglishQuestionAdded;

    private String gkQuestionList = "";
    private boolean isGkQuestionAdded;

    private String reasoningQuestionList = "";
    private boolean isReasoningQuestionAdded;

    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public CustomContentProvider(Context mContext){
        this.mContext = mContext;
        sharedPref = mContext.getSharedPreferences("com.example.ankit.quickquiz.quickquizsp", Context.MODE_PRIVATE);

        editor = sharedPref.edit();

        if(sharedPref.getString(englishQuestionList, SP_NO_QUESTIONS).equals(SP_NO_QUESTIONS)){
            editor.putString(SP_ENGLISH_QUESTION_LIST, englishQuestions);
            editor.commit();
        }

        if(sharedPref.getString(gkQuestionList, SP_NO_QUESTIONS).equals(SP_NO_QUESTIONS)){
            editor.putString(SP_GK_QUESTION_LIST, gkQuestions);
            editor.commit();
        }

        if(sharedPref.getString(reasoningQuestionList, SP_NO_QUESTIONS).equals(SP_NO_QUESTIONS)){
            editor.putString(SP_REASONING_QUESTION_LIST, reasoningQuestions);
            editor.commit();
        }
    }

    // add questionId to local variable, --> needs to be called
    public void addQuestion(int questionId, String category){

        switch(category){
            case CATEGORY_ONE:
                englishQuestionList+= " " + String.valueOf(questionId);
                isEnglishQuestionAdded = true;
                break;

            case CATEGORY_TWO:
                englishQuestionList+= " " + String.valueOf(questionId);
                isEnglishQuestionAdded = true;
                break;

            case CATEGORY_THREE:
                gkQuestionList+= " " + String.valueOf(questionId);
                isGkQuestionAdded = true;
                break;

            case CATEGORY_FOUR:
                reasoningQuestionList+= " " + String.valueOf(questionId);
                isReasoningQuestionAdded = true;
                break;
        }

    }

    // add questions to sharedPreference, --> needs to be called once 1 time
    public void addQuestionsToDB(){

        if(isEnglishQuestionAdded){
            String EQList = sharedPref.getString(SP_ENGLISH_QUESTION_LIST, SP_NO_QUESTIONS);
            EQList+= englishQuestionList;
            editor.putString(SP_ENGLISH_QUESTION_LIST, EQList);
            editor.commit();
            isEnglishQuestionAdded = false;
        }

        if(isGkQuestionAdded){
            String GKQList = sharedPref.getString(SP_GK_QUESTION_LIST, SP_NO_QUESTIONS);
            GKQList+=gkQuestionList;
            editor.putString(SP_GK_QUESTION_LIST, GKQList);
            editor.commit();
            isGkQuestionAdded = false;
        }

        if(isReasoningQuestionAdded){
            String RQList = sharedPref.getString(SP_REASONING_QUESTION_LIST, SP_NO_QUESTIONS);
            RQList+=reasoningQuestionList;
            editor.putString(SP_REASONING_QUESTION_LIST, RQList);
            editor.commit();
            isReasoningQuestionAdded = false;
        }

    }

    // get english questions, --> needs to be called
    public int[] getEnglishQuestions(){
        String questionList = sharedPref.getString(SP_ENGLISH_QUESTION_LIST, SP_NO_QUESTIONS);
        String  arr[] = questionList.split(" ");
        return parseArray(arr);
    }

    public int getEnglishQuestion(){
        String questionList = sharedPref.getString(SP_ENGLISH_QUESTION_LIST, SP_NO_QUESTIONS);
        String  arr[] = questionList.split(" ");
        return parseQuestionId(arr);
    }

    //get gk questions, --> needs to be called
    public int[] getGkQuestions(){
        String questionList = sharedPref.getString(SP_GK_QUESTION_LIST, SP_NO_QUESTIONS);
        String arr[] = questionList.split(" ");
        return parseArray(arr);
    }

    public int getGkQuestion(){
        String questionList = sharedPref.getString(SP_GK_QUESTION_LIST, SP_NO_QUESTIONS);
        String arr[] = questionList.split(" ");
        return parseQuestionId(arr);
    }

    // get reasoning questions, --> needs to be called
    public int[] getReasoningQuestions(){
        String questionList = sharedPref.getString(SP_REASONING_QUESTION_LIST, SP_NO_QUESTIONS);
        String arr[] = questionList.split(" ");
        return parseArray(arr);
    }

    public int getReasoningQuestion(){
        String questionList = sharedPref.getString(SP_REASONING_QUESTION_LIST, SP_NO_QUESTIONS);
        String arr[] = questionList.split(" ");
        return parseQuestionId(arr);
    }

    // converts string array to int array
    public int[] parseArray(String[] arr){
        int intarr[] = new int[arr.length];
        for(int i=0;i<arr.length;i++){
            intarr[i] = Integer.parseInt(arr[i]);
        }
        return intarr;
    }

    public int parseQuestionId(String[] arr){
        Random random = new Random();
        return Integer.parseInt(arr[random.nextInt(arr.length - 0)]);
    }

}
