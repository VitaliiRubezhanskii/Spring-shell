package com.homoloa.shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homoloa.domain.PaerseEntity;
import com.homoloa.dto.JsonWrapperDto;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ShellComponent
@Slf4j
public class ShellCommands {

    @ShellMethod(value = "Parse csv file to Jsone.",group = "Parsing csv")
    public String parse(String pathCsvFile, String pathOutputJsonFile){

        File fileForParse = new File(pathCsvFile);
        if(fileForParse.exists()){
            try (CSVReader csvReader = new CSVReader(new FileReader(pathCsvFile))){

                Map<String, String> mapping = getStringStringMap();

                HeaderColumnNameTranslateMappingStrategy<PaerseEntity> strategy =
                        new HeaderColumnNameTranslateMappingStrategy<PaerseEntity>();
                strategy.setType(PaerseEntity.class);
                strategy.setColumnMapping(mapping);

                CsvToBean csvToBean = new CsvToBean();

                // call the parse method of CsvToBean
                // pass strategy, csvReader to parse method
                List<PaerseEntity> paersedEntities = csvToBean.parse(strategy, csvReader);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(new JsonWrapperDto(paersedEntities));

                if(json != null){
                    try(FileWriter fw = new FileWriter(pathOutputJsonFile);
                        BufferedWriter bw = new BufferedWriter(fw)) {

                        bw.write(json);
                        return "Json file has been successfully saved";
                    }
                }
            }catch (IOException ex){
                log.error("File for parse by path: "+pathCsvFile+" not found!");
            }
        }
        return "Json file hasn't been saved";
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
