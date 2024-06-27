from rest_framework import serializers
from gestion_servicios.serializers import ServicioSerializer
from gestion_usuarios.serializers import ProveedorSerializer
from .models import Contrato, DetalleContrato


class ContratoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contrato
        fields = "__all__"


class DetalleContratoSerializer(serializers.ModelSerializer):
    servicio = ServicioSerializer(source="id_servicio", read_only=True)
    proveedor = ProveedorSerializer(source="id_proveedor", read_only=True)

    class Meta:
        model = DetalleContrato
        fields = "__all__"
