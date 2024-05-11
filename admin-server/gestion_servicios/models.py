from django.db import models


class Servicio(models.Model):
    id_servicio = models.AutoField(primary_key=True, db_column='id_servicio')
    nombre = models.CharField(max_length=60, unique=True, db_column='nombre')
    descripcion = models.TextField(db_column='descripcion')

    class Meta:
        db_table = 'servicio'

        indexes = [models.Index(fields=["nombre"])]
