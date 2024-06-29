from django.db import models
from gestion_usuarios.models import Cliente, Proveedor
from gestion_servicios.models import Servicio


class Calificacion(models.Model):
    id_calificacion = models.AutoField(primary_key=True)
    id_cliente = models.ForeignKey(
        Cliente, models.DO_NOTHING, db_column="id_cliente", blank=True, null=True
    )
    calificacion = models.IntegerField()
    comentario = models.TextField(blank=True, null=True)
    fh_creacion = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "calificacion"


class DetalleCalificacion(models.Model):
    id_calificacion = models.OneToOneField(
        Calificacion, models.DO_NOTHING, db_column="id_calificacion", primary_key=True
    )
    id_servicio = models.ForeignKey(
        Servicio, models.DO_NOTHING, db_column="id_servicio"
    )
    id_proveedor = models.ForeignKey(
        Proveedor, models.DO_NOTHING, db_column="id_proveedor"
    )

    class Meta:
        managed = False
        db_table = "detalle_calificacion"
        unique_together = (("id_calificacion", "id_servicio", "id_proveedor"),)
