from .serializer import ServicioSerializer
from .models import Servicio
from rest_framework.response import Response
from rest_framework.views import APIView


class IndexView(APIView):
    def get(self, request):
        context = {'mensaje': 'servidor activo'}
        return Response(context)


class ServiciosView(APIView):
    def get(self, request):
        dataServicios = Servicio.objects.all()
        serServicios = ServicioSerializer(dataServicios, many=True)
        return Response(serServicios.data)

    def post(self, request):
        serServicio = ServicioSerializer(data=request.data)
        serServicio.is_valid(raise_exception=True)
        serServicio.save()
        return Response(serServicio.data)


class ServicioDetailView(APIView):
    def get(self, request, id_servicio):
        dataServicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(dataServicio)
        return Response(serServicio.data)

    def put(self, request, id_servicio):
        dataServicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(dataServicio, data=request.data)
        serServicio.is_valid(raise_exception=True)
        serServicio.save()
        return Response(serServicio.data)

    def delete(self, request, id_servicio):
        dataServicio = Servicio.objects.get(pk=id_servicio)
        serServicio = ServicioSerializer(dataServicio)
        dataServicio.delete()
        return Response(serServicio.data)
