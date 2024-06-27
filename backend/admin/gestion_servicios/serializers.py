from rest_framework import serializers
from gestion_usuarios.serializers import ProveedorSerializer
from .models import Servicio, ServicioProveedor


class ServicioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Servicio
        fields = "__all__"


class ServicioProveedorSerializer(serializers.ModelSerializer):
    servicio = ServicioSerializer(source="id_servicio", read_only=True)
    proveedor = ProveedorSerializer(source="id_proveedor", read_only=True)

    class Meta:
        model = ServicioProveedor
        fields = "__all__"
