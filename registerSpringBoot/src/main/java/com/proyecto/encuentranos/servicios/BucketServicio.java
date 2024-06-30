package com.proyecto.encuentranos.servicios;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BucketServicio {

    private final AmazonS3 s3Client;

    @Autowired
    public BucketServicio(AmazonS3 s3Client){
        this.s3Client = s3Client;
    }

    public void subirArchivo(
            final String nombreBucket,
            final String rutaArchivo,
            final Long tamañoArchivo,
            final String tipoContenido,
            final InputStream inputStream
    ) throws AmazonClientException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(tamañoArchivo);
        metadata.setContentType(tipoContenido);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);

        PutObjectRequest putObjectRequest = new PutObjectRequest(nombreBucket, rutaArchivo, inputStream, metadata)
                .withAccessControlList(acl);

        s3Client.putObject(putObjectRequest);
        log.info("Archivo subido al bucket({}): {}", nombreBucket, rutaArchivo);
    }

    public ByteArrayOutputStream descargarArchivo(
            final String nombreBucket,
            final String rutaArchivo
    ) throws IOException, AmazonClientException {
        S3Object s3Object = s3Client.getObject(nombreBucket, rutaArchivo);
        InputStream inputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        log.info("Archivo descargado del bucket({}): {}", nombreBucket, rutaArchivo);
        return outputStream;
    }
}
