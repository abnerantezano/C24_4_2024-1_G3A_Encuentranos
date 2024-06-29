from django.db import models
from gestion_usuarios.models import Proveedor


class Servicio(models.Model):
    id_servicio = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=45)
    descripcion = models.TextField()
    imagen_url = models.CharField(max_length=255, blank=True, null=True)
    fh_creacion = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "servicio"


class ServicioProveedor(models.Model):
    id_servicio = models.OneToOneField(
        Servicio, models.DO_NOTHING, db_column="id_servicio", primary_key=True
    )
    id_proveedor = models.ForeignKey(
        Proveedor, models.DO_NOTHING, db_column="id_proveedor"
    )
    precio = models.FloatField()
    negociable = models.IntegerField()

    class Meta:
        managed = False
        db_table = "servicio_proveedor"
        unique_together = (("id_servicio", "id_proveedor"),)
