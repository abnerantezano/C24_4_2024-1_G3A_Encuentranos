from rest_framework import generics
from rest_framework.response import Response
from gestion_contratos.models import Contrato, DetalleContrato
from gestion_contratos.serializers import ContratoSerializer, DetalleContratoSerializer

class ContratoListAPIView(generics.ListAPIView):
    queryset = Contrato.objects.all()
    serializer_class = ContratoSerializer

class ContratoDetailAPIView(generics.RetrieveAPIView):
    queryset = Contrato.objects.all()
    serializer_class = ContratoSerializer
    lookup_field = 'id_contrato'

    def retrieve(self, request):
        instance = self.get_object()
        detalles_contrato = DetalleContrato.objects.filter(id_contrato=instance.id_contrato)
        detalles_serializer = DetalleContratoSerializer(detalles_contrato, many=True)

        contrato_serializer = self.get_serializer(instance)
        response_data = contrato_serializer.data
        response_data['detalles'] = detalles_serializer.data

        return Response(response_data)