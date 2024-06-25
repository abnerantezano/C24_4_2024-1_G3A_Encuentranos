from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager

class TipoUsuario(models.Model):
    nombre = models.CharField(max_length=45)

    def __str__(self):
        return self.nombre
    
    class Meta:
        db_table = 'tipo_usuario'

class UserManager(BaseUserManager):
    def create_user(self, correo, contrasena=None, **extra_fields):
        if not correo:
            raise ValueError("El correo es obligatorio")
        user = self.model(correo=self.normalize_email(correo), **extra_fields)
        user.set_password(contrasena)
        user.save(using=self._db)
        return user

    def create_superuser(self, correo, contrasena=None, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)
        return self.create_user(correo, contrasena, **extra_fields)

class Usuario(AbstractBaseUser):
    id_tipo = models.ForeignKey(TipoUsuario, on_delete=models.CASCADE)
    correo = models.EmailField(max_length=150, unique=True)
    contrasena = models.CharField(max_length=255)
    imagen_url = models.CharField(max_length=255, blank=True, null=True)
    estado = models.CharField(max_length=45)
    fh_creacion = models.DateTimeField(auto_now_add=True)

    USERNAME_FIELD = 'correo'
    REQUIRED_FIELDS = []

    objects = UserManager()

    def __str__(self):
        return self.correo

    class Meta:
        db_table = 'usuario'

class Distrito(models.Model):
    nombre = models.CharField(max_length=100)

    def __str__(self):
        return self.nombre
    
    class Meta:
        db_table = 'distrito'

class Proveedor(models.Model):
    id_usuario = models.ForeignKey(Usuario, on_delete=models.CASCADE)
    id_distrito = models.ForeignKey(Distrito, on_delete=models.CASCADE)
    nombre = models.CharField(max_length=45)
    apellido_paterno = models.CharField(max_length=45)
    apellido_materno = models.CharField(max_length=45)
    sexo = models.CharField(max_length=1)
    dni = models.CharField(max_length=8, unique=True)
    celular = models.CharField(max_length=9, unique=True)
    fecha_nacimiento = models.DateField()
    descripcion = models.TextField(blank=True, null=True)
    calificacion_promedio = models.FloatField(default=0)
    curriculo_url = models.CharField(max_length=255, blank=True, null=True)
    fh_creacion = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.nombre} {self.apellido_paterno} {self.apellido_materno}"
    
    class Meta:
        db_table = 'proveedor'

class Cliente(models.Model):
    id_usuario = models.ForeignKey(Usuario, on_delete=models.CASCADE)
    id_distrito = models.ForeignKey(Distrito, on_delete=models.CASCADE)
    nombre = models.CharField(max_length=45)
    apellido_paterno = models.CharField(max_length=45)
    apellido_materno = models.CharField(max_length=45)
    sexo = models.CharField(max_length=1)
    dni = models.CharField(max_length=8, unique=True)
    celular = models.CharField(max_length=9, unique=True)
    fecha_nacimiento = models.DateField()
    descripcion = models.TextField(blank=True, null=True)
    fh_creacion = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"{self.nombre} {self.apellido_paterno} {self.apellido_materno}"
    
    class Meta:
        db_table = 'cliente'