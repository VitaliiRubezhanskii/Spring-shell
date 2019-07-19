package com.homoloa.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.homoloa.Application;
import com.homoloa.domain.ParseEntity;
import com.homoloa.service.ParseService;
import com.homoloa.service.impl.ParseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class StreamLambdaHandler implements RequestStreamHandler {
        private static Logger logger = LoggerFactory.getLogger(StreamLambdaHandler.class);

        private final String testZipFilePath = "src/test/resources/test-csv/test.zip";

        public static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

        static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            String errMsg = "Could not initialize Spring Boot application";
            logger.error(errMsg);
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

        @Override
        public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
        // just in case it wasn't closed
        outputStream.close();
    }
}
