from rest_framework import serializers
from .models import TipoUsuario, Usuario, Distrito, Cliente, Proveedor


class TipoUsuarioSerializer(serializers.ModelSerializer):
    class Meta:
        model = TipoUsuario
        fields = "__all__"


class UsuarioSerializer(serializers.ModelSerializer):
    tipo = TipoUsuarioSerializer(source="id_tipo", read_only=True)

    class Meta:
        model = Usuario
        fields = "__all__"


class DistritoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Distrito
        fields = "__all__"


class ClienteSerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer(source="id_usuario", read_only=True)
    distrito = DistritoSerializer(source="id_distrito", read_only=True)

    class Meta:
        model = Cliente
        fields = "__all__"


class ProveedorSerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer(source="id_usuario", read_only=True)
    distrito = DistritoSerializer(source="id_distrito", read_only=True)

    class Meta:
        model = Proveedor
        fields = "__all__"
