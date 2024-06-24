package com.proyecto.encuentranos.controladores;

import com.proyecto.encuentranos.enumeracion.TipoArchivo;
import com.proyecto.encuentranos.servicios.BucketServicio;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("api/bucket")
public class BucketControlador {

    private BucketServicio service;
    private static final String ENCUENTRANOS = "encuentranos";

    @Autowired
    public BucketControlador(BucketServicio service){
        this.service = service;
    }

    @GetMapping("/encuentranos")
    public ResponseEntity<?> listFiles() {
        val body = service.listFiles(ENCUENTRANOS);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/encuentranos/subir")
    @SneakyThrows(IOException.class)
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        long fileSize = file.getSize();
        InputStream inputStream = file.getInputStream();

        service.uploadFile(ENCUENTRANOS, fileName, fileSize, contentType, inputStream);

        return ResponseEntity.ok().body("File uploaded successfully");
    }

    @SneakyThrows
    @GetMapping("/encuentranos/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("fileName") String fileName
    ) {
        val body = service.downloadFile(ENCUENTRANOS, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(TipoArchivo.fromFilename(fileName))
                .body(body.toByteArray());
    }

    @DeleteMapping("/encuentranos/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable("fileName") String fileName
    ) {
        service.deleteFile(ENCUENTRANOS, fileName);
        return ResponseEntity.ok().build();
    }
}
