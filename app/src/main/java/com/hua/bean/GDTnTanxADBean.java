package com.hua.bean;



import java.io.Serializable;
import java.util.ArrayList;

/**
 */
public class GDTnTanxADBean implements Serializable{

    private String adtitle;
    private String addescription;
    private String adicon;
    private String adimg;
    private int isWhatAD; // 1:Tanx, 2:GDT
    private ArrayList<String> adimgs;
    private ArrayList<String> adprices;
//    private List<MMPromoter> promoters;

    public String getAdtitle() {
        return adtitle;
    }

    public void setAdtitle(String adtitle) {
        this.adtitle = adtitle;
    }

    public String getAddescription() {
        return addescription;
    }

    public void setAddescription(String addescription) {
        this.addescription = addescription;
    }

    public String getAdicon() {
        return adicon;
    }

    public void setAdicon(String adicon) {
        this.adicon = adicon;
    }

    public String getAdimg() {
        return adimg;
    }

    public void setAdimg(String adimg) {
        this.adimg = adimg;
    }

    public int getIsWhatAD() {
        return isWhatAD;
    }

    public void setIsWhatAD(int isWhatAD) {
        this.isWhatAD = isWhatAD;
    }

    public ArrayList<String> getAdimgs() {
        return adimgs;
    }

    public void setAdimgs(ArrayList<String> adimgs) {
        this.adimgs = adimgs;
    }

    public ArrayList<String> getAdprices() {
        return adprices;
    }

    public void setAdprices(ArrayList<String> adprices) {
        this.adprices = adprices;
    }

//    public List<MMPromoter> getPromoters() {
//        return promoters;
//    }
//
//    public void setPromoters(List<MMPromoter> promoters) {
//        this.promoters = promoters;
//    }
}

