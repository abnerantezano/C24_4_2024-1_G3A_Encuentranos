from django.db import models
from gestion_servicios.models import Servicio
from gestion_usuarios.models import Cliente, Proveedor

class Contrato(models.Model):
    id_cliente = models.ForeignKey(Cliente, on_delete=models.CASCADE)
    estado = models.CharField(max_length=45)
    precio_final = models.FloatField()
    fecha_inicio = models.DateField()
    fecha_fin = models.DateField()
    hi_servicio = models.TimeField()
    hf_servicio = models.TimeField()
    fh_creacion = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = 'contrato'

class DetalleContrato(models.Model):
    id_contrato = models.ForeignKey(Contrato, on_delete=models.CASCADE)
    id_servicio = models.ForeignKey(Servicio, on_delete=models.CASCADE)
    id_proveedor = models.ForeignKey(Proveedor, on_delete=models.CASCADE)
    precio_actual = models.FloatField(blank=True, null=True)

    class Meta:
        unique_together = (('id_contrato', 'id_servicio', 'id_proveedor'),)
        db_table = 'detalle_contrato'