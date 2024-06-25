from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Servicio
from .serializers import ServicioSerializer
from django.conf import settings
import boto3

class ServicioListCreateAPIView(APIView):
    def get(self, request):
        servicios = Servicio.objects.all()
        serializer = ServicioSerializer(servicios, many=True)
        return Response(serializer.data)

    def post(self, request):
        archivo = request.data.get('archivo')
        nombre_archivo = archivo.name

        # Guardar archivo en AWS S3
        s3_client = boto3.client(
            's3',
            aws_access_key_id=settings.AWS_ACCESS_KEY_ID,
            aws_secret_access_key=settings.AWS_SECRET_ACCESS_KEY,
            region_name=settings.AWS_S3_REGION_NAME
        )

        try:
            s3_client.upload_fileobj(archivo, settings.AWS_STORAGE_BUCKET_NAME, nombre_archivo)
            # Construir la URL del archivo en S3
            archivo_url = f'https://{settings.AWS_S3_CUSTOM_DOMAIN}/{nombre_archivo}'
            
            # Crear el servicio en la base de datos
            data = {**request.data, 'imagen_url': archivo_url}
            serializer = ServicioSerializer(data=data)
            serializer.is_valid(raise_exception=True)
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_400_BAD_REQUEST)


class ServicioDetailAPIView(APIView):
    def get(self, request, id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio) 
        serializer = ServicioSerializer(servicio)
        return Response(serializer.data)

    def put(self, request, id_servicio):
        servicio = Servicio.objects.get(pk=id_servicio)
        archivo = request.data.get('archivo')

        if archivo:
            try:
                nombre_archivo = archivo.name
                s3_client = boto3.client(
                    's3',
                    aws_access_key_id=settings.AWS_ACCESS_KEY_ID,
                    aws_secret_access_key=settings.AWS_SECRET_ACCESS_KEY,
                    region_name=settings.AWS_S3_REGION_NAME
                )
                s3_client.upload_fileobj(archivo, settings.AWS_STORAGE_BUCKET_NAME, nombre_archivo)
                archivo_url = f'https://{settings.AWS_S3_CUSTOM_DOMAIN}/{nombre_archivo}'
                request.data['imagen_url'] = archivo_url
            except Exception as e:
                return Response({'error': str(e)}, status=status.HTTP_400_BAD_REQUEST)
        
        serializer = ServicioSerializer(servicio, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

    def patch(self, request, id_servicio):
        return self.put(request, id_servicio)

    def delete(self, request, pk):
        servicio = self.get_object(pk)
        servicio.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)