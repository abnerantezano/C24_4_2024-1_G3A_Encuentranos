from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from gestion_usuarios.models import Usuario, TipoUsuario, Proveedor, Cliente
from gestion_usuarios.serializers import UsuarioSerializer, ProveedorSerializer, ClienteSerializer

class UsuarioListCreateAPIView(APIView):
    def get(self, request):
        usuarios = Usuario.objects.all()
        usuarioSerilizer = UsuarioSerializer(usuarios, many=True)
        return Response(usuarioSerilizer.data)

    def post(self, request):
        try:
            administrador_tipo = TipoUsuario.objects.get(nombre='Administrador')
        except TipoUsuario.DoesNotExist:
            return Response({"error": "No se encontró el tipo de usuario 'Administrador'"}, status=status.HTTP_404_NOT_FOUND)

        usuario_serializer = UsuarioSerializer(data=request.data)
        if usuario_serializer.is_valid():
            usuario_serializer.save(id_tipo=administrador_tipo)
            return Response(usuario_serializer.data, status=status.HTTP_201_CREATED)
        return Response(usuario_serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class UsuarioDetailAPIView(APIView):
    def get(self, request, id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        usuarioSerializer = UsuarioSerializer(usuario)
        return Response(usuarioSerializer.data)

    def patch(self, request, id_usuario):
        serializer = UsuarioSerializer(data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class ProveedorListAPIView(APIView):
    def get(self, request):
        proveedores = Proveedor.objects.all()
        serializer = ProveedorSerializer(proveedores, many=True)
        return Response(serializer.data)

class ClienteListAPIView(APIView):
    def get(self, request):
        clientes = Cliente.objects.all()
        serializer = ClienteSerializer(clientes, many=True)
        return Response(serializer.data)

