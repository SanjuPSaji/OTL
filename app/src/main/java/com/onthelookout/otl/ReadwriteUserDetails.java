package com.onthelookout.otl;

public class ReadwriteUserDetails {
    public String name, email, dob, gender, mobile, profilepicture;

    public ReadwriteUserDetails(String textFullName, String textEmail, String  textDob, String  textGender, String  textNum, String profilepic){
        this.name= textFullName;
        this.email = textEmail;
        this.dob = textDob;
        this.gender = textGender;
        this.mobile = textNum;
        this.profilepicture = profilepic;
    }

}
