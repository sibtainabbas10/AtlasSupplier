/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgss.i2.atlas.supplier.service;

import com.tgss.i2.atlas.supplier.model.Atlas;
import com.tgss.i2.core.model.Constants;
import com.tgss.i2.core.model.Response;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author hassam.aslam
 */
@Service
public class AtlasService {

    public ResponseEntity<?> consume(Atlas atlasModel) {
        Response response = new Response();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            response.setStatus(Constants.Status.ERROR);
            response.setMessage(Constants.Status.ERROR.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;
        ResultSet rs = null;

        try {
            String xml = ObjectToXml(atlasModel);
          //  Document document;

            try {
             //   document = loadXML(xml);
            } catch (Exception ex) {
                Logger.getLogger(AtlasService.class.getName()).log(Level.SEVERE, null, ex);
            }

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=tlntifz02.gitest.telenor.com)(PORT=1683))(ADDRESS=(PROTOCOL=TCP)(HOST=tlntatl12-vip.gitest.telenor.com)(PORT=1531)))(SOURCE_ROUTE=YES)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ATL10)))",
                    "APPS", "weloswal10");

            if (connection != null) {
                System.out.println("Connection successful with ATLAS");

                connection.setAutoCommit(false);

                // CallableStatement cstmt = connection.prepareCall("{CALL APPS.xxtln_i2_coupa.generate_charge_account(?,?,?)}");
                CallableStatement cstmt = connection.prepareCall("{CALL APPS.xxtln_i2_coupa.create_sup(?,?,?,?)}");
                cstmt.setString(1, xml);
                //cstmt.setString(2, atlas.getLookupName());
                //cstmt.setString(3, atlas.getLastRunAt());

                cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
                cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
                cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
                cstmt.executeUpdate();
                System.out.println("Status: " + cstmt.getString(2));
                System.out.println("Error: " + cstmt.getString(3));
                System.out.println("Vendor ID: " + cstmt.getString(4));
                
                if (cstmt.getString(2) == "S") {
                    response.setStatus(Constants.Status.SUCCESS);
                } else if (cstmt.getString(2) == "E") {
                    response.setStatus(Constants.Status.ERROR);
                }
                
                
                response.setMessage(cstmt.getString(2));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                System.out.println("Failed to make connection!");
                response.setStatus(Constants.Status.ERROR);
                response.setMessage(Constants.Status.ERROR.toString());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            response.setStatus(Constants.Status.ERROR);
            response.setMessage(Constants.Status.ERROR.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
     
    }

    private String ObjectToXml(Atlas atlasModel) {
        String xmlString = "";
        try {

            JAXBContext context = JAXBContext.newInstance(Atlas.class);
            Marshaller m = context.createMarshaller();

            // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
            StringWriter sw = new StringWriter();
            m.marshal(atlasModel, sw);
            xmlString = sw.toString();

            System.out.println(xmlString);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlString;
    }

    public Document loadXML(String xml) throws Exception {
        DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
        DocumentBuilder bldr = fctr.newDocumentBuilder();
        InputSource insrc = new InputSource(new StringReader(xml));
        return bldr.parse(insrc);
    }

}
