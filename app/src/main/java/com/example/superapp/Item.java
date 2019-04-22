package com.example.superapp;


public class Item {

    public Integer Id;
    public Integer Foto;
    public String Name;
    public String Description;
    public Integer Count;
    public Integer LeftCount;

    public Item(Integer Id,Integer Foto, String Name, String Description, Integer Count,Integer LeftCount){

        this.Id=Id;
        this.Foto=Foto;
        this.Name=Name;
        this.Description= Description;
        this.Count=Count;
        this.LeftCount=LeftCount;
    }
}
