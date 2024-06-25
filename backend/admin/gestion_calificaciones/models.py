from django.db import models
from gestion_servicios.models import Servicio
from gestion_usuarios.models import Cliente, Proveedor

class Calificacion(models.Model):
    id_cliente = models.ForeignKey(Cliente, on_delete=models.CASCADE)
    calificacion = models.IntegerField()
    comentario = models.TextField(blank=True, null=True)
    fh_creacion = models.DateTimeField(auto_now_add=True)

class DetalleCalificacion(models.Model):
    id_calificacion = models.ForeignKey(Calificacion, on_delete=models.CASCADE)
    id_servicio = models.ForeignKey(Servicio, on_delete=models.CASCADE)
    id_proveedor = models.ForeignKey(Proveedor, on_delete=models.CASCADE)

    class Meta:
        unique_together = (('id_calificacion', 'id_servicio', 'id_proveedor'),)


