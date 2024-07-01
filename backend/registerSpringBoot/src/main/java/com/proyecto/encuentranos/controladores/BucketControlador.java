package com.proyecto.encuentranos.controladores;

import com.amazonaws.AmazonClientException;
import com.proyecto.encuentranos.enumeracion.TipoArchivo;
import com.proyecto.encuentranos.servicios.BucketServicio;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("api/bucket")
public class BucketControlador {

    private final BucketServicio servicio;
    private static final String NOMBRE_BUCKET = "encuentranos";
    private static final String CARPETA_USUARIOS = "usuarios/";

    @Autowired
    public BucketControlador(BucketServicio servicio){
        this.servicio = servicio;
    }

    @PostMapping("/usuarios/subir")
    public ResponseEntity<?> subirArchivo(
            @RequestParam("file") MultipartFile archivo
    ) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }

        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());
        String contentType = archivo.getContentType();
        long tamañoArchivo = archivo.getSize();
        InputStream inputStream;
        try {
            inputStream = archivo.getInputStream();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el archivo");
        }

        String rutaArchivo = CARPETA_USUARIOS + nombreArchivo;

        try {
            servicio.subirArchivo(NOMBRE_BUCKET, rutaArchivo, tamañoArchivo, contentType, inputStream);
            return ResponseEntity.ok().body("Archivo subido exitosamente");
        } catch (AmazonClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el archivo a S3");
        }
    }

    @GetMapping("/usuarios/descargar/{nombreArchivo:.+}")
    public ResponseEntity<?> descargarArchivo(
            @PathVariable("nombreArchivo") String nombreArchivo
    ) {
        String rutaArchivo = CARPETA_USUARIOS + nombreArchivo;

        try {
            ByteArrayOutputStream contenido = servicio.descargarArchivo(NOMBRE_BUCKET, rutaArchivo);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(new ByteArrayInputStream(contenido.toByteArray())));
        } catch (IOException | AmazonClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al descargar el archivo de S3");
        }
    }
}
