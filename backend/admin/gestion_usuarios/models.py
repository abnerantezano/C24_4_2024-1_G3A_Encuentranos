from django.db import models


class TipoUsuario(models.Model):
    id_tipo = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=45)

    class Meta:
        db_table = "tipo_usuario"


class Usuario(models.Model):
    id_usuario = models.AutoField(primary_key=True)
    id_tipo = models.ForeignKey(
        TipoUsuario, on_delete=models.CASCADE, db_column="id_tipo"
    )
    correo = models.EmailField(max_length=150, unique=True)
    contrasena = models.CharField(max_length=255)
    imagen_url = models.CharField(max_length=255, blank=True, null=True)
    estado = models.CharField(max_length=45)
    fh_creacion = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = "usuario"


class Distrito(models.Model):
    id_distrito = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=100)

    class Meta:
        db_table = "distrito"


class Proveedor(models.Model):
    id_proveedor = models.AutoField(primary_key=True)
    id_usuario = models.OneToOneField(
        Usuario, on_delete=models.CASCADE, db_column="id_usuario"
    )
    id_distrito = models.OneToOneField(
        Distrito, on_delete=models.CASCADE, db_column="id_distrito"
    )
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

    class Meta:
        db_table = "proveedor"


class Cliente(models.Model):
    id_cliente = models.AutoField(primary_key=True)
    id_usuario = models.OneToOneField(
        Usuario, on_delete=models.CASCADE, db_column="id_usuario"
    )
    id_distrito = models.OneToOneField(
        Distrito, on_delete=models.CASCADE, db_column="id_distrito"
    )
    nombre = models.CharField(max_length=45)
    apellido_paterno = models.CharField(max_length=45)
    apellido_materno = models.CharField(max_length=45)
    sexo = models.CharField(max_length=1)
    dni = models.CharField(max_length=8, unique=True)
    celular = models.CharField(max_length=9, unique=True)
    fecha_nacimiento = models.DateField()
    descripcion = models.TextField(blank=True, null=True)
    fh_creacion = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = "cliente"
