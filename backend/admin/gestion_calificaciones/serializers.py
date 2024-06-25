from rest_framework import serializers
from .models import Calificacion, DetalleCalificacion

class CalificacionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Calificacion
        fields = '__all__'

class DetalleCalificacionSerializer(serializers.ModelSerializer):
    class Meta:
        model = DetalleCalificacion
        fields = '__all__'
