from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from gestion_usuarios.models import Usuario, Proveedor, Cliente
from gestion_usuarios.serializers import (
    UsuarioSerializer,
    ProveedorSerializer,
    ClienteSerializer,
)
from rest_framework_simplejwt.views import TokenObtainPairView


class ListaCreacionUsuarios(APIView):
    def get(self, request):
        usuarios = Usuario.objects.all()
        usuario_serilizer = UsuarioSerializer(usuarios, many=True)
        return Response(usuario_serilizer.data)

    def post(self, request):
        if int(request.data["id_tipo"]) == 3:
            usuario_serializer = UsuarioSerializer(data=request.data)
            if usuario_serializer.is_valid():
                usuario_serializer.save()
                return Response(usuario_serializer.data, status=status.HTTP_201_CREATED)
        return Response(usuario_serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class DetalleUsuario(APIView):
    def get(self, request, id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        usuario_serializer = UsuarioSerializer(usuario)
        return Response(usuario_serializer.data)

    def put(self, request, id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        usuario_serializer = UsuarioSerializer(usuario, data=request.data)
        if usuario_serializer.is_valid():
            usuario_serializer.save()
            return Response(usuario_serializer.data)
        return Response(usuario_serializer.errors)

    def patch(self, request, id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        usuario_serializer = UsuarioSerializer(usuario, data=request.data, partial=True)
        if usuario_serializer.is_valid():
            usuario_serializer.save()
            return Response(usuario_serializer.data)
        return Response(usuario_serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class ListaProveedor(APIView):
    def get(self, request):
        proveedores = Proveedor.objects.all()
        proveedor_serializer = ProveedorSerializer(proveedores, many=True)
        return Response(proveedor_serializer.data)


class ListaCliente(APIView):
    def get(self, request):
        clientes = Cliente.objects.all()
        cliente_serializer = ClienteSerializer(clientes, many=True)
        return Response(cliente_serializer.data)
