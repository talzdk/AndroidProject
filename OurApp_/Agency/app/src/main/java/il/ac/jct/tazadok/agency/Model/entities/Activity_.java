package il.ac.jct.tazadok.agency.Model.entities;

        import android.util.Log;

        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;

        import il.ac.jct.tazadok.agency.Model.entities.Description;

/**
 * Created by Talush122 on 05/12/2016.
 */

public class Activity_ {



    protected Description description;
    protected  String country_name;
    private String start;
    private String end;
    protected double cost;
    protected String activity_desc;
    protected long buisness_id;
    protected long active_id;


    public Activity_()
    {
        active_id=1;
    }

    public Activity_(String e_active, String cname, String sdate, String fdate, double _cost, String _activity_desc, long _buisness_id) {
        description = Description.valueOf(e_active);
        country_name = cname;
        cost=_cost;
        activity_desc=_activity_desc;
        buisness_id=_buisness_id;
        active_id=0;
        setStart(sdate);
        setEnd(fdate);
    }




    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public double getPrice() {
        return cost;
    }

    public Description getDes() {
        return description;
    }

    public String getCountry() {
        return country_name;
    }

    public long getBusiness_id() {
        return buisness_id;
    }

    public void setDes(Description description) {
        this.description = description;
    }

    public void setCountry(String country_name) {
        this.country_name = country_name;
    }

    public String getShort_des() {
        return activity_desc;
    }

    public void setShort_des(String activity_desc) {
        this.activity_desc = activity_desc;
    }

    public void setBusiness_id(long buisness_id) {
        this.buisness_id = buisness_id;
    }

    public void setPrice(double cost) {
        this.cost = cost;
    }


    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;

    }

    public void setCode(long active_id) {
        this.active_id = active_id+1;
    }

    public long getCode() {
        return active_id;
    }

}
