package com.homoloa.shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homoloa.domain.PaerseEntity;
import com.homoloa.dto.JsonWrapperDto;
import com.homoloa.service.ParseService;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@ShellComponent
@Slf4j
public class ShellCommands {

    @Autowired
    private ParseService parseService;


    @ShellMethod(value = "Parse csv file to Jsone.", group = "Parsing csv")
    public String parse(String pathCsvFile, String pathOutputJsonFile) {

        File fileForParse = new File(pathCsvFile);
        if (fileForParse.exists()) {
            try (CSVReader csvReader = new CSVReader(new FileReader(pathCsvFile))) {

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(new JsonWrapperDto(parseService.parseCsvFile(csvReader)));

                if (json != null) {
                    try (FileWriter fw = new FileWriter(pathOutputJsonFile);
                         BufferedWriter bw = new BufferedWriter(fw)) {

                        bw.write(json);
                        return "Json file has been successfully saved";
                    }
                }
            } catch (IOException ex) {
                log.error("File for parse by path: " + pathCsvFile + " not found!");
            }
        }
        return "Json file hasn't been saved";
    }

    @ShellMethod(value = "Parse zip csv file to Jsone.", group = "Parsing zip csv")
    public String parseZip(String pathCsvFile, String pathOutputJsonFile) {

        try (FileInputStream fis = new FileInputStream(pathCsvFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipEntry ze;

            try (CSVReader csvReader = new CSVReader(new FileReader(pathCsvFile))) {
                List<PaerseEntity> paerseEntities = new ArrayList<>();
                while ((ze = zis.getNextEntry()) != null) {
                    paerseEntities.addAll(parseService.parseCsvFile(csvReader));
                }

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(new JsonWrapperDto(paerseEntities));


                if (json != null) {
                    try (FileWriter fw = new FileWriter(pathOutputJsonFile);
                         BufferedWriter bw = new BufferedWriter(fw)) {

                        bw.write(json);
                        return "Json file has been successfully saved";
                    }
                }
            } catch (IOException ex) {
                log.error("File for parse by path: " + pathCsvFile + " not found!");
            }
        }catch (IOException ex) {
            log.error(" Zip File for parse by path: " + pathCsvFile + " hasn't been handled");
        }
        return "Json file hasn't been saved";
    }

}
