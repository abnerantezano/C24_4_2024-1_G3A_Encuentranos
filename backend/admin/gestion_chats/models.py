from django.db import models
from gestion_usuarios.models import Proveedor, Cliente, Usuario

class Chat(models.Model):
    id_chat = models.AutoField(primary_key=True)
    id_proveedor = models.ForeignKey(Proveedor, on_delete=models.CASCADE)
    id_cliente = models.ForeignKey(Cliente, on_delete=models.CASCADE)
    fh_creacion = models.DateTimeField(auto_now_add=True)
    estado = models.CharField(max_length=45)

    class Meta:
        db_table = 'chat'

class Mensaje(models.Model):
    id_mensaje = models.AutoField(primary_key=True)
    id_receptor = models.ForeignKey(Usuario, on_delete=models.CASCADE, related_name='mensajes_recibidos')
    id_emisor = models.ForeignKey(Usuario, on_delete=models.CASCADE, related_name='mensajes_enviados')
    id_chat = models.ForeignKey(Chat, on_delete=models.CASCADE)
    mensaje = models.TextField()
    fh_creacion = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = 'mensaje'