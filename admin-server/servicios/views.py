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
        serServicio = ServicioSerializer(data=request.data)
        serServicio.is_valid(raise_exception=True)
        serServicio.save()
        return Response(serServicio.data)


class DetalleServicio(APIView):
    def get(self, request,  id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(servicio)
        return Response(serServicio.data)

    def pull(self, request, id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(servicio, data=request.data)
        serServicio.is_valid(raise_exception=True)
        serServicio.save()
        return Response(serServicio.data)

    def delete(self, request, id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(servicio)
        servicio.delete()
        return Response(serServicio.data)
