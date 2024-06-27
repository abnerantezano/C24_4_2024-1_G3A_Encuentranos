from django.db import models
from gestion_usuarios.models import Cliente, Proveedor
from gestion_servicios.models import Servicio


class Calficacion(models.Model):
    id_calificacion = models.AutoField(primary_key=True, db_column="id_calificacion")
    id_cliente = models.ForeignKey(
        Cliente, on_delete=models.CASCADE, db_column="id_cliente"
    )
    calificacion = models.IntegerField()
    comentario = models.TextField()
    fh_creacion = models.DateTimeField(auto_now_add=True)


class DetalleCalificacion(models.Model):
    id_calificacion = models.ForeignKey(
        Calficacion, on_delete=models.CASCADE, db_column="id_calificacion"
    )
    id_proveedor = models.ForeignKey(
        Proveedor, on_delete=models.CASCADE, db_column="id_proveedor"
    )
    id_servicio = models.ForeignKey(
        Servicio, on_delete=models.CASCADE, db_column="id_servicio"
    )
