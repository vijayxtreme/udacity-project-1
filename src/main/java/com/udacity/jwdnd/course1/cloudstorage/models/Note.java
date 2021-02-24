package com.udacity.jwdnd.course1.cloudstorage.models;

//a Note POJO
public class Note {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid; //belongs to a User

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

//    @Override String toString(){
//        return "Note: id: " + noteid.toString() + "title: " + notetitle.toString() + " created.";
//    }
}
