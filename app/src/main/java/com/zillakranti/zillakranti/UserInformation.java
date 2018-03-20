package com.zillakranti.zillakranti;

/**
 * Created by root on 21/03/18.
 */

public class UserInformation {

    public String FullName;
    public String PostalAddress;
    public String Landmark;
    public String sex;


    public UserInformation()
    {

    }


    public UserInformation(String fullName, String postalAddress, String landmark, String sex) {
        this.FullName = fullName;
        this.PostalAddress = postalAddress;
        this.Landmark =landmark;
        this.sex =sex;


    }


}
