from django.db import models


class TipoUsuario(models.Model):
    id_tipo = models.AutoField(primary_key=True, db_column='id_tipo')
    nombre = models.CharField(max_length=60, unique=True, db_column='nombre')
    permisos = models.CharField(
        max_length=255, blank=True, db_column='permisos')

    class Meta:
        db_table = 'tipo_usuario'


class Usuario(models.Model):
    id_usuario = models.AutoField(primary_key=True, db_column='id_usuario')
    id_tipo = models.ForeignKey(
        TipoUsuario, on_delete=models.CASCADE, db_column='id_tipo')
    correo = models.CharField(max_length=120, unique=True, db_column='correo')
    contrasena = models.CharField(
        max_length=200, db_column='contrasena')
    imagen_url = models.CharField(max_length=255, db_column='imagen_url')
    eliminada = models.BooleanField(db_column='eliminada')

    class Meta:
        db_table = 'usuario'


class Proveedor(models.Model):
    id_proveedor = models.AutoField(primary_key=True, db_column='id_proveedor')
    id_usuario = models.ForeignKey(
        Usuario, on_delete=models.CASCADE, db_column='id_usuario')
    nombre = models.CharField(max_length=50, db_column='nombre')
    apellido_paterno = models.CharField(
        max_length=60, db_column='apellido_paterno')
    apellido_materno = models.CharField(
        max_length=60, null=True, db_column='apellido_materno')
    sexo = models.CharField(max_length=50, db_column='sexo')
    dni = models.CharField(max_length=8, unique=True, db_column='dni')
    celular = models.CharField(max_length=9, unique=True, db_column='celular')
    fecha_nacimiento = models.DateField(db_column='fecha_nacimiento')
    distrito = models.CharField(max_length=60, db_column='distrito')
    disponible = models.BooleanField(db_column='disponible')
    calificacion_promedio = models.FloatField(
        db_column='calificacion_promedio', null=True)

    class Meta:
        db_table = 'proveedor'


class Cliente(models.Model):
    id_cliente = models.AutoField(primary_key=True, db_column='id_cliente')
    id_usuario = models.ForeignKey(
        Usuario, on_delete=models.CASCADE, db_column='id_usuario')
    nombre = models.CharField(max_length=50, db_column='nombre')
    apellido_paterno = models.CharField(
        max_length=60, db_column='apellido_paterno')
    apellido_materno = models.CharField(
        max_length=60, null=True, db_column='apellido_materno')
    sexo = models.CharField(max_length=50, db_column='sexo')
    dni = models.CharField(max_length=8, unique=True, db_column='dni')
    celular = models.CharField(max_length=9, unique=True, db_column='celular')
    fecha_nacimiento = models.DateField(db_column='fecha_nacimiento')
    distrito = models.CharField(max_length=60, db_column='distrito')

    class Meta:
        db_table = 'cliente'


class CalificacionProveedor(models.Model):
    id_proveedor = models.ForeignKey(
        Proveedor, on_delete=models.CASCADE, db_column='id_proveedor')
    id_cliente = models.ForeignKey(
        Cliente, on_delete=models.CASCADE, db_column='id_cliente')
    calificacion = models.IntegerField(db_column='calificacion')
    comentario = models.TextField(db_column='comentario', null=True)
    fecha_calificacion = models.DateField(db_column='fecha_calificacion')

    class Meta:
        db_table = 'calificacion_proveedor'
