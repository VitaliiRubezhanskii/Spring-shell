package com.homoloa.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.homoloa.service.ParseService;
import com.homoloa.service.impl.ParseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Component
public class S3EventHandler implements RequestHandler<S3Event, String> {

    private final ParseService parseService;
    private static Logger log = LoggerFactory.getLogger(ParseServiceImpl.class);

    @Autowired
    public S3EventHandler(ParseService parseService) {
        this.parseService = parseService;
    }

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        log.info("Invoked " + S3EventHandler.class.getSimpleName() + " with "
                + (s3Event.getRecords() == null ? 0 : s3Event.getRecords().size()) + " records.");
        List<S3EventNotification.S3EventNotificationRecord> records = s3Event.getRecords();
        if (records != null) {
            for (S3EventNotification.S3EventNotificationRecord record : records) {
                String eventName = record.getEventName();
                if ("ObjectCreated:Put".equals(eventName)) {
                    S3EventNotification.S3Entity s3 = record.getS3();
                    if (s3 != null) {
                        String bucketName = s3.getBucket().getName();
                        String key = s3.getObject().getKey();
                        if (key.endsWith(".csv") || key.endsWith(".zip")) {
                            AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
                            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
                            S3Object s3Object = client.getObject(getObjectRequest);
                            S3ObjectInputStream inputStream = s3Object.getObjectContent();
                            try {
                                byte[] bytes = IOUtils.toByteArray(inputStream);
                                String json = parseService.parseForLambda(bytes);

                                uploadJsonToS3(bucketName, key, client, json);
                                log.info("Successfully created json file.");
                            } catch (IOException e) {
                                log.error("Failed to get content of S3 object (bucket=" + bucketName
                                        + ", key=" + key + "): " + e.getMessage(), e);
                            }
                        } else {
                            log.debug("Key does not end with .jpg or .jpeg.");
                        }
                    } else {
                        log.debug("No S3 object in Record.");
                    }
                } else {
                    log.debug("Ignoring record (not a put request).");
                }
            }
        }
        return "OK";
    }

    private void uploadJsonToS3(String bucketName, String key, AmazonS3 client, String json) {
        int lastIndexOfDot = key.lastIndexOf('.');
        String newKey = key.substring(0, lastIndexOfDot) + "_thumb"
                + key.substring(lastIndexOfDot+1);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(json.getBytes().length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey,
                new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), metadata);
        client.putObject(putObjectRequest);
    }
}
