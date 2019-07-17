package com.homoloa.service.impl;


import com.homoloa.domain.PaerseEntity;
import com.homoloa.service.ParseService;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParseServiceImplTest {

    private ParseService parseService;
    private final String pathTestCsvFile = "src/test/resources/test-csv/test.csv";
    private final String pathTestJson = "src/test/resources/test-csv/test.json";
    private PaerseEntity testEntity1;
    private PaerseEntity testEntity2;

    @Before
    public void setUp() {
        parseService = new ParseServiceImpl();
        testEntity1 = setEntity1();
        testEntity2 = setEntity2();
    }

    @Test
    public void parseCsvFile() throws IOException {

        try (BufferedReader csvReader = new BufferedReader(new FileReader(pathTestCsvFile))) {
            List<PaerseEntity> parseEntities = parseService.parseCsvFile(csvReader);

            assertNotNull(parseEntities);
            assertEquals(5, parseEntities.size());
            assertEquals(testEntity1.getCountryAbbreviation(), parseEntities.get(0).getCountryAbbreviation());
            assertEquals(testEntity1.getCompanyCode(), parseEntities.get(0).getCompanyCode());
            assertEquals(testEntity1.getCompanyName(), parseEntities.get(0).getCompanyName());
            assertEquals(testEntity1.getCountryName(), parseEntities.get(0).getCountryName());
            assertEquals(testEntity1.getProductCode(), parseEntities.get(0).getProductCode());
            assertEquals(testEntity1.getProductNameEnglish(), parseEntities.get(0).getProductNameEnglish());
            assertNull(parseEntities.get(0).getActiveCode());
        }
    }

    @Test
    public void transformToJson() throws IOException{

        List<PaerseEntity> entities = Arrays.asList(testEntity1, testEntity2);
        String json = parseService.transformToJson(entities);

        assertNotNull(json);
        assertTrue(json.contains(testEntity1.getCompanyName()));
        assertTrue(json.contains(testEntity1.getProductCode()));
        assertTrue(json.contains(testEntity1.getProductNameEnglish()));
        assertTrue(json.contains(testEntity2.getCompanyName()));
        assertTrue(json.contains(testEntity2.getProductCode()));
        assertTrue(json.contains(testEntity2.getProductNameEnglish()));
        assertFalse(json.contains("Wrong string"));
    }

    @Test
    public void saveJson() throws IOException{

        List<PaerseEntity> entities = Arrays.asList(testEntity1, testEntity2);
        String json = parseService.transformToJson(entities);
        boolean isSaved = parseService.saveJson(pathTestJson, json);

        assertTrue(isSaved);
    }

    private PaerseEntity setEntity1() {
        PaerseEntity entity = new PaerseEntity();
        entity.setCountryAbbreviation("601");
        entity.setCompanyCode("41339");
        entity.setCompanyName("ACETO");
        entity.setCountryName("USA");
        entity.setProductCode("60100274900485");
        entity.setProductNameEnglish("ACETO ATRAZINE 90");
        return entity;
    }

    private PaerseEntity setEntity2() {
        PaerseEntity entity = new PaerseEntity();
        entity.setCountryAbbreviation("601");
        entity.setCompanyCode("40171");
        entity.setCompanyName("PBI-GORDON");
        entity.setCountryName("USA");
        entity.setProductCode("60100221700668");
        entity.setProductNameEnglish("GORDON'S AMINE 600 2.4-D WEED KILLER");
        return entity;
    }
}
