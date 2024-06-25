from django.db import models
from gestion_usuarios.models import Proveedor

class Servicio(models.Model):
    id_servicio = models.AutoField(primary_key=True)
    nombre = models.CharField(max_length=45)
    descripcion = models.TextField()
    imagen_url = models.CharField(max_length=255, blank=True, null=True)
    fh_creacion = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.nombre
    
    class Meta:
        db_table = 'servicio'

class ServicioProveedor(models.Model):
    id_servicio = models.ForeignKey(Servicio, on_delete=models.CASCADE)
    id_proveedor = models.ForeignKey(Proveedor, on_delete=models.CASCADE)
    precio = models.FloatField()
    negociable = models.BooleanField()

    class Meta:
        unique_together = (('id_servicio', 'id_proveedor'),)
        db_table = 'servicio_proveedor'