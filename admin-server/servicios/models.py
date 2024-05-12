from django.db import models
from usuarios.models import Proveedor


class Servicio(models.Model):
    id_servicio = models.AutoField(primary_key=True, db_column='id_servicio'),
    nombre = models.CharField(max_length=60, unique=True, db_column='nombre')
    descripcion = models.TextField(db_column='descripcion')

    class Meta:
        db_table = 'servicio'


class ServicioProveedor(models.Model):
    id_proveedor = models.ForeignKey(
        Proveedor, on_delete=models.CASCADE, db_column='id_proveedor')
    id_servicio = models.ForeignKey(
        Servicio, on_delete=models.CASCADE, db_column='id_servicio')
    precio = models.FloatField(db_column='precio')

    class Meta:
        db_table = 'servicio_proveedor'
