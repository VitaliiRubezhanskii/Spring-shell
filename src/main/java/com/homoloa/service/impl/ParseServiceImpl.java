package com.homoloa.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homoloa.domain.PaerseEntity;
import com.homoloa.dto.JsonWrapperDto;
import com.homoloa.service.ParseService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ParseServiceImpl implements ParseService {


    @Override
    public ResponseEntity<InputStreamResource> parseFile( MultipartFile file) {

            try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

                List<PaerseEntity> parsedEntities = parseCsvFile(csvReader);
                String json = transformToJson(parsedEntities);

                HttpHeaders headers = this.getHttpHeaders("json", "parsed");
                ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
                return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

        }catch(Exception ex){
            log.error("File with name: " + file.getOriginalFilename() + " hasn't been parsed");
        }
        return  ResponseEntity.badRequest().header("message", "Sorry, couldn't parse that file").build();
    }

    @Override
    public List<PaerseEntity> parseCsvFile(BufferedReader csvReader) {
        Map<String, String> mapping = getStringStringMap();

        HeaderColumnNameTranslateMappingStrategy<PaerseEntity> strategy =
                new HeaderColumnNameTranslateMappingStrategy<PaerseEntity>();
        strategy.setType(PaerseEntity.class);
        strategy.setColumnMapping(mapping);

        CsvToBean csvToBean = new CsvToBeanBuilder<PaerseEntity>(csvReader)
                .withType(PaerseEntity.class)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    @Override
    public String transformToJson(List<PaerseEntity> paerseEntities) throws IOException {
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

    private HttpHeaders getHttpHeaders(String fileType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", "application/json");
        headers.add("name", fileName + "." + fileType);
        headers.add("Content-Disposition", "attachment; filename= " + fileName + "." + fileType);
        return headers;
    }

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
}
