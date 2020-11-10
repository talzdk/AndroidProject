package il.ac.jct.tazadok.agency.Model.entities;

import android.provider.ContactsContract;

import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;

/**
 * Created by Talush122 on 05/12/2016.
 */
class Address
{
    private String country;
    private String city;
    private String street;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}

public class Business  {


    protected long business_id;
    protected String business_name;
    protected String business_address;
    protected String business_phone;
    protected String business_email;
    protected String business_website;
    public Business(String s, String s1, String s2, String s3, String s4)
    {
        business_id=0;
        business_name=s;
        business_address=s1;
        business_phone=s2;
        business_email=s3;
        business_website=s4;


    }

    public Business(){business_id=1;}

    public long getId() {
        return business_id;
    }

    public void setId(long business_id) {
        this.business_id = business_id+1;
    }

    public String getName() {
        return business_name;
    }

    public String getPhone() {
        return business_phone;
    }

    public String getAddress() {
        return business_address;
    }

    public String getEmail() {
        return business_email;
    }

    public void setName(String business_name) {
        this.business_name = business_name;
    }

    public void setAddress(String business_address) {
        this.business_address = business_address;
    }

    public void setEmail(String business_email) {
        this.business_email = business_email;
    }

    public void setPhone(String business_phone) {
        this.business_phone = business_phone;
    }

    public void setWebSite(String business_website) {
        this.business_website = business_website;
    }

    public String getWebSite() {
        return business_website;
    }

}
