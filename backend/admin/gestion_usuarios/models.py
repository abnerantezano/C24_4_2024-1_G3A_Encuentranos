from django.db import models
import bcrypt


class TipoUsuario(models.Model):
    id_tipo = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=45)

    class Meta:
        managed = False
        db_table = "tipo_usuario"


class Usuario(models.Model):
    id_usuario = models.AutoField(primary_key=True)
    id_tipo = models.ForeignKey(
        TipoUsuario, models.DO_NOTHING, db_column="id_tipo", blank=True, null=True
    )
    correo = models.CharField(unique=True, max_length=150)
    contrasena = models.CharField(max_length=255)
    imagen_url = models.CharField(max_length=255, blank=True, null=True)
    estado = models.CharField(max_length=45)
    fh_creacion = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "usuario"

    def check_password(self, raw_password):
        return bcrypt.checkpw(
            raw_password.encode("utf-8"), self.contrasena.encode("utf-8")
        )


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
