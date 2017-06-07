package com.example.ankit.quickquiz.HelperClasses;

/**
 * Created by Kumar on 4/15/2017.
 */

public class Utility {
    public static class Utils{

        public static boolean validateEmail(String emailId){
            if(emailId.matches("[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")){
                return true;
            }
            return false;
        }

    }
}
