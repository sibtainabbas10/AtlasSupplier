package com.tgss.i2.atlas.supplier;

import com.tgss.i2.atlas.supplier.controller.AtlasController;
import com.tgss.i2.atlas.supplier.model.Atlas;
import com.tgss.i2.atlas.supplier.model.Atlas.AtlasModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtlasApplication implements CommandLineRunner {

    
    @Autowired
    AtlasController atlasController;
    
    
    Atlas atlas=new Atlas();
	public static void main(String[] args) {
		SpringApplication.run(AtlasApplication.class, args);
	}
        
    @Override
    public void run(String... args) throws Exception {
      
        AtlasModel atlasModel=new AtlasModel();
       atlasModel.setVendor_Name("hassam");
       atlasModel.setSegment1("khan");
       atlas.getAtlasModel().add(atlasModel); 
       atlasModel=new AtlasModel();
       atlasModel.setVendor_Name("kazim");
       atlasModel.setSegment1("ali");
        atlas.getAtlasModel().add(atlasModel);
        
       
   
        System.out.println(atlasController.SupplierDataToAtlas(atlas).getStatusCode());
    }
}
