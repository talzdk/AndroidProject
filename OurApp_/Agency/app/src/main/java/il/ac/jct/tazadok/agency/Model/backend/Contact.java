package il.ac.jct.tazadok.agency.Model.backend;
import android.net.Uri;
/**
 * Created by Talush122 on 14/01/2017.
 */

public class Contact {


    public static final String AUTHORITY = "il.ac.jct.tazadok.agency";

    /**
     * A content:// style uri to the authority for the contacts provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class Activity_ {
        public static final String DESCRIPTION = "Description";
        public static final String ACTIVITY_CODE = "Id";
        public static final String COUNTRY = "Country";
        public static final String START = "Start";
        public static final String END = "End";
        public static final String PRICE = "Price";
        public static final String SHORT_DES = "ShortDescription";
        public static final String BUSINESS_ID = "BusinessID";
        /**
         * The content:// style URI for this table
         */
        public static final Uri ACTIVITY_URI = Uri.withAppendedPath(AUTHORITY_URI, "activities");

    }

    public static class Business {
        public static final String ID = "Id";
        public static final String NAME = "Name";
        public static final String ADDRESS = "Address";
        public static final String PHONE = "Phone";
        public static final String EMAIL = "Email";
        public static final String WEBSITE = "Website";

        /**
         * The content:// style URI for this table
         */
        public static final Uri BUSINESS_URI = Uri.withAppendedPath(AUTHORITY_URI, "businesses");
    }

    public static class User {

        public static final String USER_NAME = "UserName";
        public static final String USER_CODE = "Code";
        public static final String PASSWORD = "Password";
        /**
         * The content:// style URI for this table
         */
        public static final Uri USER_URI = Uri.withAppendedPath(AUTHORITY_URI, "users");
    }

    public static class Code {

        public static final String CODEU = "Ucode";
        public static final String CODEB = "Bcode";
        public static final String CODEA = "Acode";
        /**
         * The content:// style URI for this table
         */
        public static final Uri CODE_URI = Uri.withAppendedPath(AUTHORITY_URI, "codes");
    }
}