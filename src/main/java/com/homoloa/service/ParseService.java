package com.homoloa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homoloa.domain.ParseEntity;
import com.homoloa.exception.FileParseException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ParseService {

    InputStreamResource parseFile(MultipartFile file) throws FileParseException;

    String parseForLambda(String testZipFilePath);

    void readDataFromZipFile(InputStream fin, List<ParseEntity> entities) throws IOException;

    List<ParseEntity> parseCsvFile(BufferedReader csvReader) throws JsonProcessingException;

    String transformToJson(List<ParseEntity> paerseEntities) throws IOException;

    boolean saveJson(String pathOutputJsonFile, String json) throws IOException;
}
