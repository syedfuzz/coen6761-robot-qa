package com.coen6761.HelperFunctions;

public class HelperFunctions {
    public static boolean IntegerExist(String[] commands){
        if(commands.length == 1){
            return false;
        } 
        return true;
    }

    public static int isValidInteger(String number){
        try {
            int integerVal = Integer.parseInt(number);
            return integerVal;
        } catch (Exception ex){
            return -1;
        }
    }

    public static boolean isIntegergreaterThanZero(int number){
        if(number>0) return true;
        else return false;
    }
}
