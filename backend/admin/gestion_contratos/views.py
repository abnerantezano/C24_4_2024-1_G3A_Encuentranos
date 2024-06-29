from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from gestion_contratos.models import Contrato, DetalleContrato
from gestion_contratos.serializers import ContratoSerializer, DetalleContratoSerializer
from django.shortcuts import get_object_or_404


class ListaCreacionContrato(APIView):
    def get(self, request):
        contratos = Contrato.objects.all()
        contrato_serializer = ContratoSerializer(contratos, many=True)
        return Response(contrato_serializer.data)

    def post(self, request):
        contrato_serializer = ContratoSerializer(data=request.data)
        if contrato_serializer.is_valid():
            contrato_serializer.save()
            return Response(contrato_serializer.data, status=status.HTTP_201_CREATED)
        return Response(contrato_serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class DetalleContratoVista(APIView):
    def get(self, request, id_contrato):
        contrato = get_object_or_404(Contrato, pk=id_contrato)
        detalles_contrato = DetalleContrato.objects.filter(id_contrato=id_contrato)
        detalles_serializer = DetalleContratoSerializer(detalles_contrato, many=True)
        contrato_serializer = ContratoSerializer(contrato)
        # ...........
        response_data = contrato_serializer.data
        response_data["detalles"] = detalles_serializer.data
        # ..........
        return Response(response_data)

    def put(self, request, id_contrato):
        contrato = get_object_or_404(Contrato, pk=id_contrato)
        contrato_serializer = ContratoSerializer(contrato, data=request.data)
        if contrato_serializer.is_valid():
            contrato_serializer.save()
            return Response(contrato_serializer.data)
        return Response(contrato_serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def patch(self, request, id_contrato):
        contrato = get_object_or_404(Contrato, pk=id_contrato)
        contrato_serializer = ContratoSerializer(
            contrato, data=request.data, partial=True
        )
        if contrato_serializer.is_valid():
            contrato_serializer.save()
            return Response(contrato_serializer.data)
        return Response(contrato_serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id_contrato):
        contrato = get_object_or_404(Contrato, pk=id_contrato)
        contrato.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
