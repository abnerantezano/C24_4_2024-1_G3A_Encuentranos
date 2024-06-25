from rest_framework import serializers
from .models import Servicio, Distrito, ServicioProveedor

class ServicioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Servicio
        fields = '__all__'

class DistritoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Distrito
        fields = '__all__'

class ServicioProveedorSerializer(serializers.ModelSerializer):
    class Meta:
        model = ServicioProveedor
        fields = '__all__'
