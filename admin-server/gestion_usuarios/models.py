from django.db import models


class TipoUsuario(models.Model):
    id_tipo = models.AutoField(primary_key=True, db_column='id_tipo')
    nombre = models.CharField(max_length=60, db_column='nombre')

    class Meta:
        db_table = 'tipo_usuario'


class Usuario(models.Model):
    id_usuario = models.AutoField(primary_key=True, db_column='id_usuario')
    id_tipo = models.ForeignKey(
        TipoUsuario, on_delete=models.CASCADE, db_column='id_tipo')
    correo = models.CharField(max_length=150, unique=True, db_column='correo')
    contrasena = models.CharField(max_length=255, db_column='contrasena')
    imagen_url = models.CharField(max_length=255, db_column='imagen_url')

    class Meta:
        db_table = 'usuario'
        indexes = [models.Index(fields=["id_tipo", "correo"])]


class Cliente(models.Model):
    id_cliente = models.AutoField(primary_key=True, db_column='id_cliente')
    id_usuario = models.ForeignKey(
        Usuario, on_delete=models.CASCADE, db_column='id_usuario')
    nombre = models.CharField(max_length=50, db_column='nombre')
    apellido_paterno = models.CharField(
        max_length=50, db_column='apellido_paterno')
    apellido_materno = models.CharField(
        max_length=50, null=True, db_column='apellido_materno')
    genero = models.CharField(max_length=20, db_column='genero')
    dni = models.CharField(max_length=8, unique=True, db_column='dni')
    fecha_nacimiento = models.DateField(db_column='fecha_nacimiento')
    celular = models.CharField(max_length=9, unique=True, db_column='celular')
    distrito = models.CharField(max_length=50, db_column='distrito')

    class Meta:
        db_table = 'cliente'
        indexes = [models.Index(fields=["id_usuario", "dni"])]


class Proveedor(models.Model):
    id_proveedor = models.AutoField(primary_key=True, db_column='id_proveedor')
    id_usuario = models.ForeignKey(
        Usuario, on_delete=models.CASCADE, db_column='id_usuario')
    nombre = models.CharField(max_length=50, db_column='nombre')
    apellido_paterno = models.CharField(
        max_length=50, db_column='apellido_paterno')
    apellido_materno = models.CharField(
        max_length=50, null=True, db_column='apellido_materno')
    genero = models.CharField(max_length=20, db_column='genero')
    dni = models.CharField(max_length=8, unique=True, db_column='dni')
    fecha_nacimiento = models.DateField(db_column='fecha_nacimiento')
    celular = models.CharField(max_length=9, unique=True, db_column='celular')
    distrito = models.CharField(max_length=50, db_column='distrito')
    disponible = models.BooleanField(db_column='disponible')

    class Meta:
        db_table = 'proveedor'
        indexes = [models.Index(fields=["id_usuario", "dni"])]
