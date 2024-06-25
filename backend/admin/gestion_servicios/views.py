from rest_framework import generics, status
from rest_framework.response import Response
from .models import Servicio
from .serializers import ServicioSerializer
from django.conf import settings
import boto3

class ServicioListCreateAPIView(generics.ListCreateAPIView):
    queryset = Servicio.objects.all()
    serializer_class = ServicioSerializer

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
            serializer = self.get_serializer(data={**request.data, 'imagen_url': archivo_url})
            serializer.is_valid(raise_exception=True)
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_400_BAD_REQUEST)

class ServicioDetailAPIView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Servicio.objects.all()
    serializer_class = ServicioSerializer

    def put(self, request):
        return self.update(request)

    def patch(self, request):
        return self.partial_update(request)

    def update(self, request):
        archivo = request.data.get('imagen')
        
        # Si hay un archivo en la solicitud, cargarlo en AWS S3
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
        
        return super().update(request)

    def partial_update(self, request):
        return self.update(request)