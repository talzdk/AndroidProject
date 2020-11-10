package il.ac.jct.tazadok.agency.Model.datasource;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.entities.Business;

/**
 * Created by Talush122 on 28/03/2017.
 */

public class Toolss {

    public static List<Business> list;

public static ArrayList<String> getId()
{
    ArrayList<String > list2 = new ArrayList<String>() ;

    for (Business b:list
         ) {
        list2.add(b.getId()+"");
    }

    return list2;

}



}
