from .serializer import TipoUsuarioSerializer, UsuarioSerializer
from .models import TipoUsuario, Usuario
from rest_framework.response import Response
from rest_framework.views import APIView


class TipoUsuariosView(APIView):
    def get(self, request):
        dataTipoUsuarios = TipoUsuario.objects.all()
        serTiposUsuarios = TipoUsuarioSerializer(dataTipoUsuarios, many=True)
        return Response(serTiposUsuarios.data)

    def post(self, request):
        serTipoUsuario = TipoUsuarioSerializer(data=request.data)
        serTipoUsuario.is_valid(raise_exception=True)
        serTipoUsuario.save()
        return Response(serTipoUsuario.data)


class TipoUsuarioDetailView(APIView):
    def get(self, request, id_tipo):
        dataTipoUsuario = TipoUsuario.objects.get(pk=id_tipo)
        serTipoUsuario = TipoUsuarioSerializer(dataTipoUsuario)
        return Response(serTipoUsuario.data)

    def put(self, request, id_tipo):
        dataTipoUsuario = TipoUsuario.objects.get(pk=id_tipo)
        serTipoUsuario = TipoUsuarioSerializer(
            dataTipoUsuario, data=request.data)
        serTipoUsuario.is_valid(raise_exception=True)
        serTipoUsuario.save()
        return Response(serTipoUsuario.data)

    def delete(self, request, id_tipo):
        dataTipoUsuario = TipoUsuario.objects.get(pk=id_tipo)
        serTipoUsuario = TipoUsuarioSerializer(dataTipoUsuario)
        dataTipoUsuario.delete()
        return Response(serTipoUsuario.data)


class UsuarioView(APIView):
    def get(self, request):
        dataUsuarios = Usuario.objects.all()
        serUsuarios = UsuarioSerializer(dataUsuarios, many=True)
        return Response(serUsuarios.data)

    def post(self, request):
        serUsuario = UsuarioSerializer(data=request.data)
        serUsuario.is_valid(raise_exception=True)
        serUsuario.save()
        return Response(serUsuario.data)


class UsuarioDetailView(APIView):
    def get(self, request, id_usuario):
        dataUsuario = Usuario.objects.get(pk=id_usuario)
        serUsuario = UsuarioSerializer(dataUsuario)
        return Response(serUsuario.data)

    def put(self, request, id_usuario):
        dataUsuario = Usuario.objects.get(pk=id_usuario)
        serUsuario = UsuarioSerializer(
            dataUsuario, data=request.data
        )
        serUsuario.is_valid(raise_exception=True)
        serUsuario.save()
        return Response(serUsuario.data)

    def delete(self, request, id_usuario):
        dataUsuario = Usuario.objects.get(pk=id_usuario)
        serUsuario = UsuarioSerializer(dataUsuario)
        dataUsuario.delete()
        return Response(serUsuario.data)
