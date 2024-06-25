from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from gestion_contratos.models import Contrato, DetalleContrato
from gestion_contratos.serializers import ContratoSerializer, DetalleContratoSerializer
from django.http import Http404

class ContratoListAPIView(APIView):
    def get(self, request):
        contratos = Contrato.objects.all()
        serializer = ContratoSerializer(contratos, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = ContratoSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class ContratoDetailAPIView(APIView):
    def get_object(self, id_contrato):
        try:
            return Contrato.objects.get(pk=id_contrato)
        except Contrato.DoesNotExist:
            raise Http404

    def get(self, request, id_contrato):
        contrato = self.get_object(id_contrato)
        detalles_contrato = DetalleContrato.objects.filter(id_contrato=id_contrato)
        detalles_serializer = DetalleContratoSerializer(detalles_contrato, many=True)

        contrato_serializer = ContratoSerializer(contrato)
        response_data = contrato_serializer.data
        response_data['detalles'] = detalles_serializer.data

        return Response(response_data)

    def put(self, request, id_contrato):
        contrato = self.get_object(id_contrato)
        serializer = ContratoSerializer(contrato, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def patch(self, request, id_contrato):
        contrato = self.get_object(id_contrato)
        serializer = ContratoSerializer(contrato, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id_contrato):
        contrato = self.get_object(id_contrato)
        contrato.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
