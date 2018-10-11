package com.mic.quiz;


public class Question_details
{
    int ID;
    String question;
    String user_answer;

    public Question_details(int ID,String question)
    {
        this.ID=ID;
        this.question=question;
    }


    public int getID() {
        return ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

}
