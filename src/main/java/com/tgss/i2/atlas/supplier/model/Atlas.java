/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgss.i2.atlas.supplier.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.stereotype.Component;



/**
 *
 * @author hassam.aslam
 */
@Component


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "atlasModel"
})
@XmlRootElement(name ="SupHeader")
public class Atlas {

    @XmlElement(name = "row",  required = true)
    protected List<AtlasModel> atlasModel;

    public List<AtlasModel> getAtlasModel() {
        if(atlasModel==null){
        
        atlasModel=new ArrayList<>();
        }
        return atlasModel;
    }

    public void setAtlasModel(List<AtlasModel> atlasModel)  {
        this.atlasModel = atlasModel;
    }
    
    
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Vendor_Name",
    "Segment1"
})
//@XmlRootElement(name ="row")
    public static class AtlasModel implements java.io.Serializable{
    @XmlElement(name = "VENDOR_NAME", required = true)
    private String Vendor_Name;
    @XmlElement(name = "SEGMENT1", required = true)
    private String Segment1;
    
    
      public String getVendor_Name() {
        return Vendor_Name;
    }

    public void setVendor_Name(String Vendor_Name) {
        this.Vendor_Name = Vendor_Name;
    }

    public String getSegment1() {
        return Segment1;
    }

    public void setSegment1(String Segment1) {
        this.Segment1 = Segment1;
    }
    }
     
     
}
