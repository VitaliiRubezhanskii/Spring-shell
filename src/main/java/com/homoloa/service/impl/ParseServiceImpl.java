package com.homoloa.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homoloa.domain.ParseEntity;
import com.homoloa.dto.JsonWrapperDto;
import com.homoloa.exception.FileParseException;
import com.homoloa.service.ParseService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
public class ParseServiceImpl implements ParseService {
    private static Logger log = LoggerFactory.getLogger(ParseServiceImpl.class);
    @Override
    public InputStreamResource parseFile(MultipartFile file) throws FileParseException {

        List<ParseEntity> parsedEntities = new ArrayList<>();
        String json;
        try {
            if (file.getOriginalFilename().endsWith(".zip")) {
                readDataFromZipFile(file.getInputStream(), parsedEntities);
            } else if (file.getOriginalFilename().endsWith(".csv")) {
                readDataFromCsvFile(file.getInputStream(), parsedEntities);
            } else {
                throw new FileParseException("Sorry, couldn't parse that file");
            }
            json = transformToJson(parsedEntities);
        } catch (Exception ex) {
            log.error("File with name: " + file.getName() + " hasn't been parsed!");
            throw new FileParseException("Sorry, couldn't parse that file");
        }

        if(json != null) {
            ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
            return new InputStreamResource(in);
        }else{
            throw new FileParseException("Sorry, couldn't parse that file");
        }
    }

    private void readDataFromCsvFile(InputStream fin, List<ParseEntity> entities) throws IOException {
        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(fin, StandardCharsets.UTF_8))) {
            entities.addAll(parseCsvFile(csvReader));
        }
    }

    @Override
    public void readDataFromZipFile(InputStream fin, List<ParseEntity> entities) throws IOException {
        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");

        try (FileOutputStream fos = new FileOutputStream(zip)) {
            IOUtils.copy(fin, fos);
        }

        try (FileInputStream fis = new FileInputStream(zip);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipFile zipFile = new ZipFile(zip);
            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {
                readDataFromCsvFile(zipFile.getInputStream(ze), entities);
            }
            zip.delete();
        }
    }

    @Override
    public List<ParseEntity> parseCsvFile(BufferedReader csvReader) {
        Map<String, String> mapping = getStringStringMap();

        HeaderColumnNameTranslateMappingStrategy<ParseEntity> strategy =
                new HeaderColumnNameTranslateMappingStrategy<ParseEntity>();
        strategy.setType(ParseEntity.class);
        strategy.setColumnMapping(mapping);

        CsvToBean csvToBean = new CsvToBeanBuilder<ParseEntity>(csvReader)
                .withType(ParseEntity.class)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    @Override
    public String transformToJson(List<ParseEntity> paerseEntities) throws IOException {
        String json = null;
        if (CollectionUtils.isNotEmpty(paerseEntities)) {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(new JsonWrapperDto(paerseEntities));
        }
        return json;
    }

    @Override
    public boolean saveJson(String pathOutputJsonFile, String json) throws IOException {
        if (json != null) {
            try (FileWriter fw = new FileWriter(pathOutputJsonFile);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(json);
                return true;
            }
        }
        return false;
    }

//    private HttpHeaders getHttpHeaders(String fileType, String fileName) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Encoding", "UTF-8");
//        headers.add("Content-Type", "application/json");
//        headers.add("name", fileName + "." + fileType);
//        headers.add("Content-Disposition", "attachment; filename= " + fileName + "." + fileType);
//        return headers;
//    }

    private Map<String, String> getStringStringMap() {
        Map<String, String> mapping = new
                HashMap<String, String>();
        mapping.put("ACTI_CODE", "activeCode");
        mapping.put("Active combinaison", "activeMixName");
        mapping.put("ACTI_NAME", "activeName");
        mapping.put("ACTI_UNIT", "activeUnit");
        mapping.put("CAS_NO", "casNumber");
        mapping.put("COMP_CODE", "companyCode");
        mapping.put("COMP_NAME", "companyName");
        mapping.put("CONCENTRAT", "concentration");
        mapping.put("CTRY_CODE", "countryAbbreviation");
        mapping.put("CTRY_NAME", "countryName");
        mapping.put("Formulation", "formulation");
        mapping.put("Phrase abbrev", "phraseAbbreviation");
        mapping.put("PHRA_CODE", "phraseCode");
        mapping.put("PHRG_CODE", "phraseGroupCode");
        mapping.put("PHRA_NAME", "phraseName");
        mapping.put("PHRG_NAME", "phraseGroupName");
        mapping.put("Picto", "pictogramCode");
        mapping.put("PROD_CODE", "productCode");
        mapping.put("PROD_NAME_EN", "productNameEnglish");
        mapping.put("Reg number", "registrationNumber");
        return mapping;
    }

    @Override
    public String parseForLambda(String testZipFilePath){

        ParseService parseService = new ParseServiceImpl();
        File file = new File(this.getClass().getClassLoader().getResource(testZipFilePath).getFile());

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipFile zipFile = new ZipFile(file);
            ZipEntry ze;


            List<ParseEntity> parseEntities = new ArrayList<>();
            while ((ze = zis.getNextEntry()) != null) {
                try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), StandardCharsets.UTF_8))) {
                    parseEntities.addAll(parseService.parseCsvFile(csvReader));
                } catch (Exception ex) {
                    log.error("File with path: " + testZipFilePath + " hasn't been parsed!");
                }
            }
            return parseService.transformToJson(parseEntities);

        }catch (IOException ex) {
            log.error(" Zip File for parse by path: " + testZipFilePath + " hasn't been handled");
        }
        return " Zip File for parse by path: " + testZipFilePath + " hasn't been handled";
    }
}
