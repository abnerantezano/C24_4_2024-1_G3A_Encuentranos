from django.db import models
from gestion_usuarios.models import Proveedor, Cliente, Usuario


class Chat(models.Model):
    id_chat = models.AutoField(primary_key=True)
    id_proveedor = models.ForeignKey(
        Proveedor, models.DO_NOTHING, db_column="id_proveedor", blank=True, null=True
    )
    id_cliente = models.ForeignKey(
        Cliente, models.DO_NOTHING, db_column="id_cliente", blank=True, null=True
    )
    fh_creacion = models.DateTimeField(blank=True, null=True)
    estado = models.CharField(max_length=45)

    class Meta:
        managed = False
        db_table = "chat"


class Mensaje(models.Model):
    id_mensaje = models.AutoField(primary_key=True)
    id_receptor = models.ForeignKey(
        Usuario, models.DO_NOTHING, db_column="id_receptor", blank=True, null=True
    )
    id_emisor = models.ForeignKey(
        Usuario,
        models.DO_NOTHING,
        db_column="id_emisor",
        related_name="mensaje_id_emisor_set",
        blank=True,
        null=True,
    )
    id_chat = models.ForeignKey(
        Chat, models.DO_NOTHING, db_column="id_chat", blank=True, null=True
    )
    mensaje = models.TextField()
    fh_creacion = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "mensaje"
