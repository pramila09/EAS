package com.example.pramila.eas;

public class profiledetail {
    private String tvemail;
    private String tvdept;
    private String tvaddress;
    private String tvreg;
    private String tvname;

    public profiledetail(String tvemail, String tvdept, String tvaddress, String tvreg, String tvname){
        this.tvemail = tvemail;
        this.tvdept = tvdept;
        this.tvaddress = tvaddress;
        this.tvreg = tvreg;
        this.tvname = tvname;
    }

    public String getTvemail(){
        return tvemail;
    }
    public String getTvdept(){
        return tvdept;
    }
    public String getTvaddress(){
        return tvaddress;
    }
    public String getTvreg(){
        return tvreg;
    }
    public String getTvname(){
        return tvname;
    }

}
