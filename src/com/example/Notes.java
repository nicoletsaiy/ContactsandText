package com.example;

/**
 * Created with IntelliJ IDEA.
 * User: ashish
 * Date: 7/10/12
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */

public class Notes {

    int _id;
    String _note;

    public Notes(){

    }
    public Notes(int id, String note){
        this._id = id;
        this._note = note;
    }

    public Notes(String note){
        this._note = note;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getNote(){
        return this._note;
    }

    public void setNote(String note){
        this._note = note;
    }

}