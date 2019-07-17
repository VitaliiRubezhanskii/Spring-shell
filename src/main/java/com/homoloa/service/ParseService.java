package com.homoloa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.homoloa.domain.PaerseEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ParseService {

    ResponseEntity<InputStreamResource> parseFile(MultipartFile file);

    void readDataFromZipFile(InputStream fin, List<PaerseEntity> entities) throws IOException;

    List<PaerseEntity> parseCsvFile(BufferedReader csvReader) throws JsonProcessingException;

    String transformToJson(List<PaerseEntity> paerseEntities) throws IOException;

    boolean saveJson(String pathOutputJsonFile, String json) throws IOException;
}
