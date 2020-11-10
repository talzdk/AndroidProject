package il.ac.jct.tazadok.agency.Model.entities;

import java.io.Serializable;

/**
 * Created by Talush122 on 05/12/2016.
 */



public class User implements Serializable {


    protected String user_name;
    protected long user_num;
    protected String password;

    public User(String s, String s1) {
        user_name=s;
        password=s1;
        user_num=0;

    }
    public User(){};
    public long getUser_code() {
        return user_num;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_code(long user_num) {
        this.user_num = user_num+1;
    }

}
