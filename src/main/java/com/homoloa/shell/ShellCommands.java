package com.homoloa.shell;

import com.homoloa.domain.ParseEntity;
import com.homoloa.service.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@ShellComponent
@Slf4j
public class ShellCommands {

    private final ParseService parseService;

    @Autowired
    public ShellCommands(ParseService parseService) {
        this.parseService = parseService;
    }

    @ShellMethod(value = "Parse csv file to Jsone.", group = "Parsing csv")
    public String parse(String pathCsvFile, String pathOutputJsonFile) {
        File fileForParse = new File(pathCsvFile);
        if (fileForParse.exists()) {
            try (BufferedReader csvReader = new BufferedReader(new FileReader(pathCsvFile))) {
                List<ParseEntity> parseEntities = parseService.parseCsvFile(csvReader);

                String json = parseService.transformToJson(parseEntities);
                if (parseService.saveJson(pathOutputJsonFile, json)) return "Json file has been successfully saved";
            } catch (IOException ex) {
                log.error("File for parse by path: " + pathCsvFile + " not found!" + ex.getMessage());
            }
        }
        return "Json file hasn't been saved";
    }

    @ShellMethod(value = "Parse zip csv file to Jsone.", group = "Parsing zip csv")
    public String parseZip(String pathCsvFile, String pathOutputJsonFile) {


        try (FileInputStream fis = new FileInputStream(pathCsvFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipFile zipFile = new ZipFile(new File(pathCsvFile));
            ZipEntry ze;


            List<ParseEntity> parseEntities = new ArrayList<>();
            while ((ze = zis.getNextEntry()) != null) {
                try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), StandardCharsets.UTF_8))) {
                    parseEntities.addAll(parseService.parseCsvFile(csvReader));
                } catch (Exception ex) {
                    log.error("File with path: " + pathCsvFile + " hasn't been parsed!");
                }
            }
            String json = parseService.transformToJson(parseEntities);
            if (parseService.saveJson(pathOutputJsonFile, json)) return "Json file has been successfully saved";
        }catch (IOException ex) {
            log.error(" Zip File for parse by path: " + pathCsvFile + " hasn't been handled");
        }
        return "Json file hasn't been saved";
    }


}
