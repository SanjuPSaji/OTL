package com.onthelookout.otl;

public class ReadwriteUserDetails {
    public String fullname, email, dob, gender, mobile;

    public ReadwriteUserDetails(String textFullName,String textEmail,String  textDob,String  textGender,String  textNum){
        this.fullname = textFullName;
        this.email = textEmail;
        this.dob = textDob;
        this.gender = textGender;
        this.mobile = textNum;
    }

}
