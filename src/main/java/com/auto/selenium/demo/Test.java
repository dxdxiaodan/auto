package com.auto.selenium.demo;

import java.util.*;
class Test{
    public static void main(String[] args){
        /*Scanner input = new Scanner(System.in);
        int month = 0;
        if(input.hasNext()){
         month = input.nextInt();
        }*/
        //System.out.println(calculate(month));
        String s = "a";
        System.out.println(s.length());
    }
    public static int calculate(int month){
        if(month < 5){return 1;}
        // 上回能生
        int first = 0;
        // 上回总数
        int preNum = 1;
        int allNum =0;
        int diff = month -5;
        for(int i =0; i<= diff/5;i++){
            allNum = first*5 + preNum*2;
            first = preNum*2;
            preNum = allNum;
        }
        allNum += diff % 5;
        return allNum;
    }
}