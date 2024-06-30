package com.proyecto.encuentranos.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.amazonaws.AmazonClientException;
import com.proyecto.encuentranos.auth.config.PasswordConfig;
import com.proyecto.encuentranos.enumeracion.TipoArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.proyecto.encuentranos.modelos.UsuarioModelo;
import com.proyecto.encuentranos.repositorios.IClienteRepositorio;
import com.proyecto.encuentranos.repositorios.IProveedorRepositorio;
import com.proyecto.encuentranos.repositorios.IUsuarioRepositorio;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {

    private final PasswordConfig passwordEncoder;

	private final IUsuarioRepositorio usuarioRepositorio;
	
    private final IClienteRepositorio clienteRepositorio;

    private final IProveedorRepositorio proveedorRepositorio;

    private final BucketServicio bucketServicio;

    @Autowired
    public UsuarioServicio(PasswordConfig passwordEncoder,
                           IUsuarioRepositorio usuarioRepositorio,
                           IClienteRepositorio clienteRepositorio,
                           IProveedorRepositorio proveedorRepositorio,
                           BucketServicio bucketServicio) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepositorio = usuarioRepositorio;
        this.clienteRepositorio = clienteRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
        this.bucketServicio = bucketServicio;
    }

    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio) {
        this.passwordEncoder = null;
        this.usuarioRepositorio = usuarioRepositorio;
        this.clienteRepositorio = null;
        this.proveedorRepositorio = null;
        this.bucketServicio = null;
    }
    //CRUD

    //CREATE
    public UsuarioModelo guardarUsuario(UsuarioModelo usuario, MultipartFile archivo) {
        if (archivo != null && !archivo.isEmpty()) {
            String urlImagen = subirArchivoAS3(archivo);
            usuario.setImagenUrl(urlImagen);
        }

        usuario.setContrasena(passwordEncoder.passwordEncoder().encode(usuario.getContrasena()));
        return usuarioRepositorio.save(usuario);
    }
    //READ
    public List<UsuarioModelo> obtenerUsuarios(){
    	return usuarioRepositorio.findAll();
    }
    
    //UPDATE
    public UsuarioModelo actualizarUsuario(Integer id, UsuarioModelo usuarioActualizado, MultipartFile archivo) {
        UsuarioModelo usuarioExistente = usuarioRepositorio.findById(id).orElse(null);
        if (usuarioExistente != null) {
            if (usuarioActualizado.getCorreo() != null) {
                usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            }
            if (archivo != null && !archivo.isEmpty()) {
                String urlImagen = subirArchivoAS3(archivo);
                usuarioExistente.setImagenUrl(urlImagen);
            }
            usuarioExistente = usuarioRepositorio.save(usuarioExistente);
        }
        return usuarioExistente;
    }

    //----------------------------------------

    private String subirArchivoAS3(MultipartFile archivo) {
        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());
        String contentType = archivo.getContentType();
        long tamañoArchivo = archivo.getSize();
        InputStream inputStream;
        try {
            inputStream = archivo.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error al obtener el InputStream del archivo", e);
        }

        // Utilizando TipoArchivo para obtener el tipo MIME correcto
        MediaType mediaType = TipoArchivo.fromFilename(nombreArchivo);

        String rutaArchivo = "usuarios/" + nombreArchivo; // Ruta en S3
        try {
            assert bucketServicio != null;
            bucketServicio.subirArchivo("encuentranos", rutaArchivo, tamañoArchivo, mediaType.toString(), inputStream);
        } catch (AmazonClientException e) {
            throw new RuntimeException("Error al subir el archivo a S3", e);
        }

        return "https://encuentranos.s3.amazonaws.com/" + rutaArchivo; // URL pública de S3
    }

    //BUSCAR UN USUARIO POR SU CORREO
    public Optional<UsuarioModelo> buscarUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }
    
    //BUSCAR USUARIO POR SU ID
    public Optional<UsuarioModelo> buscarUsuarioPorId(int id){
    	return usuarioRepositorio.findById(id);
    }
    
    //VERIFICAR SI EL CORREO EXISTE
    public boolean existsByEmail(String correo) {
        return usuarioRepositorio.existsByCorreo(correo);
    }

    //VERIFICAR SI EXISTE UN CLIENTE O PROVEEDOR CON EL CORREO EXISTENTE
    public boolean existsInClienteOrProveedor(String correo) {
        return clienteRepositorio.existsByIdUsuarioCorreo(correo) || proveedorRepositorio.existsByIdUsuarioCorreo(correo);
    }

    // ACTUALIZAR CONTRASEÑA DEL USUARIO
    public UsuarioModelo actualizarContrasena(Integer id, String contrasenaActual, String nuevaContrasena) {
        Optional<UsuarioModelo> optionalUsuario = usuarioRepositorio.findById(id);
        if (optionalUsuario.isPresent()) {
            UsuarioModelo usuarioExistente = optionalUsuario.get();

            if (passwordEncoder.passwordEncoder().matches(contrasenaActual, usuarioExistente.getContrasena())) {
                usuarioExistente.setContrasena(passwordEncoder.passwordEncoder().encode(nuevaContrasena));

                usuarioExistente = usuarioRepositorio.save(usuarioExistente);

                return usuarioExistente;
            } else {
                throw new IllegalArgumentException("Contraseña actual incorrecta");
            }
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

}
