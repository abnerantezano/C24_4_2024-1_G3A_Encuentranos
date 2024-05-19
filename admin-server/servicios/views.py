from rest_framework.views import APIView
from rest_framework.response import Response
from .models import Servicio
from .serializers import ServicioSerializer
from django.db import connection

class ListaServicios(APIView):
    def get(self, request):
        servicios = Servicio.objects.all()
        serServicio = ServicioSerializer(servicios, many=True)
        return Response(serServicio.data)

    def post(self, request):
        nombre = request.data.get('nombre')
        descripcion = request.data.get('descripcion', None)

        with connection.cursor() as cursor:
            if descripcion:
                cursor.execute("CALL sp_actualiza_servicio('crear', NULL, %s, %s)", [nombre, descripcion])
            else:
                cursor.execute("CALL sp_actualiza_servicio('crear', NULL, %s, NULL)", [nombre])

        servicios = Servicio.objects.all()
        serServicio = ServicioSerializer(servicios, many=True)
        return Response(serServicio.data)


class DetalleServicio(APIView):
    def get(self, request,  id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(servicio)
        return Response(serServicio.data)

    def put(self, request, id_servicio):
        nombre = request.data.get('nombre')
        descripcion = request.data.get('descripcion', None)

        with connection.cursor() as cursor:
            cursor.execute("CALL sp_actualiza_servicio('actualizar', %s, %s, %s)", [id_servicio, nombre, descripcion])

        servicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(servicio)
        return Response(serServicio.data)

    def delete(self, request, id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio)
        nombre = servicio.nombre

        with connection.cursor() as cursor:
            cursor.execute("CALL sp_actualiza_servicio('eliminar', %s, NULL, NULL)", [id_servicio])
        
        return Response({'mensaje': 'Servicio eliminado'})