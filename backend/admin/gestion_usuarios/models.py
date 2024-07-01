from django.db import models
from django.utils import timezone
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager


class TipoUsuario(models.Model):
    id_tipo = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=45)

    class Meta:
        managed = False
        db_table = "tipo_usuario"


class UsuarioManager(BaseUserManager):
    def create_user(self, correo, contrasena=None):
        if not correo:
            raise ValueError("El correo electrónico es obligatorio")
        user = self.model(correo=self.normalize_email(correo))
        if contrasena:
            user.set_password(contrasena)
        user.save(using=self._db)
        return user

    def create_superuser(self, correo, contrasena):
        user = self.create_user(correo, contrasena=contrasena)
        user.is_admin = True
        user.save(using=self._db)
        return user


class Usuario(AbstractBaseUser):
    id_usuario = models.AutoField(primary_key=True)
    id_tipo = models.ForeignKey(
        TipoUsuario, models.DO_NOTHING, db_column="id_tipo", blank=True, null=True
    )
    correo = models.CharField(unique=True, max_length=150)
    password = models.CharField(max_length=255, db_column="contrasena")
    imagen_url = models.CharField(max_length=255, blank=True, null=True)
    estado = models.CharField(max_length=45)
    fh_creacion = models.DateTimeField(blank=True, null=True)
    last_login = models.DateTimeField(default=timezone.now)

    is_active = models.BooleanField(default=True)
    is_admin = models.BooleanField(default=False)

    objects = UsuarioManager()

    USERNAME_FIELD = "correo"

    def check_password(self, raw_password):
        return raw_password == self.password

    class Meta:
        managed = False
        db_table = "usuario"


class Distrito(models.Model):
    id_distrito = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = "distrito"


class Proveedor(models.Model):
    id_proveedor = models.AutoField(primary_key=True)
    id_usuario = models.ForeignKey(
        Usuario, models.DO_NOTHING, db_column="id_usuario", blank=True, null=True
    )
    id_distrito = models.ForeignKey(
        Distrito, models.DO_NOTHING, db_column="id_distrito", blank=True, null=True
    )
    nombre = models.CharField(max_length=45)
    apellido_paterno = models.CharField(max_length=45)
    apellido_materno = models.CharField(max_length=45)
    sexo = models.CharField(max_length=1)
    dni = models.CharField(unique=True, max_length=8)
    celular = models.CharField(unique=True, max_length=9)
    fecha_nacimiento = models.DateField()
    descripcion = models.TextField(blank=True, null=True)
    calificacion_promedio = models.IntegerField(blank=True, null=True)
    curriculo_url = models.CharField(max_length=255, blank=True, null=True)
    fh_creacion = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "proveedor"


class Cliente(models.Model):
    id_cliente = models.AutoField(primary_key=True)
    id_usuario = models.ForeignKey(
        Usuario, models.DO_NOTHING, db_column="id_usuario", blank=True, null=True
    )
    id_distrito = models.ForeignKey(
        Distrito, models.DO_NOTHING, db_column="id_distrito", blank=True, null=True
    )
    nombre = models.CharField(max_length=45)
    apellido_paterno = models.CharField(max_length=45)
    apellido_materno = models.CharField(max_length=45)
    sexo = models.CharField(max_length=1)
    dni = models.CharField(unique=True, max_length=8)
    celular = models.CharField(unique=True, max_length=9)
    fecha_nacimiento = models.DateField()
    descripcion = models.TextField(blank=True, null=True)
    fh_creacion = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "cliente"
