package com.example.myfarm.modals;

public class Post {


    private String description;

    private String imageurl;


    private String postid;

  private String date;
  private  String time;
    private String publisher;

    public Post(){

    }

    public  Post(String description,String imageurl,String postid,String publisher,String date,String time){

        this.description=description;
        this.imageurl=imageurl;
        this.postid=postid;
        this.publisher=publisher;
        this.date=date;
        this.time=time;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}


