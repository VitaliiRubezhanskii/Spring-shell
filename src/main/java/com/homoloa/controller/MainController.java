package com.homoloa.controller;

import com.homoloa.domain.ParseEntity;
import com.homoloa.service.ParseService;
import com.homoloa.service.impl.ParseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@RestController
public class MainController {

    private final String pathTestCsvFile = "test.zip";

    @Autowired
    private ParseService parseService;

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public ResponseEntity<InputStreamResource> parseUploadedFile(@RequestParam("file") MultipartFile file){
//
////       if(file != null){
////           return parseService.parseFile(file);
////       }
//       return null;
//    }

    @GetMapping("/**")
    @ResponseBody
    public String processing(){

            return parseService.parseForLambda(pathTestCsvFile);
//        return "Hello World";
    }

//    ParseService parseService = new ParseServiceImpl();
//
//            try (
//    FileInputStream fis = new FileInputStream(testZipFilePath);
//    BufferedInputStream bis = new BufferedInputStream(fis);
//    ZipInputStream zis = new ZipInputStream(bis)) {
//
//        ZipFile zipFile = new ZipFile(new File(testZipFilePath));
//        ZipEntry ze;
//
//
//        List<ParseEntity> parseEntities = new ArrayList<>();
//        while ((ze = zis.getNextEntry()) != null) {
//            try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), StandardCharsets.UTF_8))) {
//                parseEntities.addAll(parseService.parseCsvFile(csvReader));
//            } catch (Exception ex) {
//                logger.error("File with path: " + testZipFilePath + " hasn't been parsed!");
//            }
//        }
//        String json = parseService.transformToJson(parseEntities);
//        if(StringUtils.isNotBlank(json)){
//            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
//        }
//    }catch (IOException ex) {
//        logger.error(" Zip File for parse by path: " + testZipFilePath + " hasn't been handled");
//    }
}
