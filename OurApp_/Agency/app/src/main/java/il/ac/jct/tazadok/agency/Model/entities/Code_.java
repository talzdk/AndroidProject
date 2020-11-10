package il.ac.jct.tazadok.agency.Model.entities;

/**
 * Created by Talush122 on 15/03/2017.
 */

public class Code_ {

   protected int Ucode;
    protected int Bcode;
    protected int Acode;

    public Code_(int u, int b, int a){Ucode=u; Bcode=b; Acode=a;}
    public Code_(){}

    public int getAcode() {
        return Acode;
    }

    public int getBcode() {
        return Bcode;
    }

    public int getUcode() {
        return Ucode;
    }

    public void setAcode(int acode) {
        Acode = acode;
    }

    public void setUcode(int ucode) {
        Ucode = ucode;
    }

    public void setBcode(int bcode) {
        Bcode = bcode;
    }
}


