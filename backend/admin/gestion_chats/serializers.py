from rest_framework import serializers
from gestion_servicios.serializers import ServicioSerializer
from gestion_usuarios.serializers import ProveedorSerializer
from .models import Chat, Mensaje

class ChatSerializer(serializers.ModelSerializer):
    class Meta:
        model = Chat
        fields = '__all__'

class MensajeSerializer(serializers.ModelSerializer):
    servicio = ServicioSerializer(source='id_servicio', read_only=True)
    proveedor = ProveedorSerializer(source='id_proveedor', read_only=True)

    class Meta:
        model = Mensaje
        fields = '__all__'
