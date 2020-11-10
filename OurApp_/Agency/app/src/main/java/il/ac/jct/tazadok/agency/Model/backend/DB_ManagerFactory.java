package il.ac.jct.tazadok.agency.Model.backend;

import il.ac.jct.tazadok.agency.Model.datasource.Lists;
import il.ac.jct.tazadok.agency.Model.datasource.VlabManager;

/**
 * Created by Talush122 on 11/12/2016.
 */

public class DB_ManagerFactory
{

    static DB_Manager manager = null;
    //singelton
    public static DB_Manager getManager() {
        if (manager == null)
            manager =  new VlabManager();
            //manager=new Lists();
        return manager;
    }

}
