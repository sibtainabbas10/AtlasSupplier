/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgss.i2.atlas.supplier.controller;

import com.tgss.i2.atlas.supplier.model.Atlas;
import com.tgss.i2.atlas.supplier.service.AtlasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author hassam.aslam
 */
@Component
public class AtlasController {
    
    @Autowired
    AtlasService atlasService;
    
     //@RequestMapping(method = RequestMethod.POST, value = "/atlas/consume")
     public ResponseEntity<?> SupplierDataToAtlas( Atlas atlasModel){
         return atlasService.consume(atlasModel);
    
        
     }
}
