from rest_framework import generics, status
from rest_framework.response import Response
from .models import Usuario, TipoUsuario, Distrito, Proveedor, Cliente
from .serializers import TipoUsuarioSerializer, UsuarioSerializer, ProveedorSerializer, ClienteSerializer, DistritoSerializer
from django.shortcuts import get_object_or_404

class TipoUsuarioDetailAPIView(generics.RetrieveAPIView):
    queryset = TipoUsuario.objects.all()
    serializer_class = TipoUsuarioSerializer

class UsuarioListCreateAPIView(generics.ListCreateAPIView):
    queryset = Usuario.objects.all()
    serializer_class = UsuarioSerializer

    def perform_create(self, serializer):
        administrador_tipo = get_object_or_404(TipoUsuario, nombre='Administrador')
        serializer.save(id_tipo=administrador_tipo)


class UsuarioDetailAPIView(generics.RetrieveUpdateAPIView):
    queryset = Usuario.objects.all()
    serializer_class = UsuarioSerializer

    def patch(self, request):
        instance = self.get_object()
        instance.estado = request.data.get("estado", instance.estado)
        instance.save()
        serializer = self.get_serializer(instance)
        return Response(serializer.data, status=status.HTTP_200_OK)


class DistritoDetailAPIView(generics.RetrieveAPIView):
    queryset = Distrito.objects.all()
    serializer_class = DistritoSerializer

class ProveedorListAPIView(generics.ListAPIView):
    queryset = Proveedor.objects.all()
    serializer_class = ProveedorSerializer


class ClienteListAPIView(generics.ListAPIView):
    queryset = Cliente.objects.all()
    serializer_class = ClienteSerializer

