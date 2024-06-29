from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Servicio
from .serializers import ServicioSerializer
from django.conf import settings
import boto3
from django.shortcuts import get_object_or_404


class ListaCreacionServicio(APIView):

    def get(self, request):
        servicios = Servicio.objects.all()
        servicio_serializer = ServicioSerializer(servicios, many=True)
        return Response(servicio_serializer.data)

    def post(self, request):
        archivo = request.FILES.get("archivo")
        data = request.data.copy()

        if archivo:
            nombre_archivo = archivo.name

            if nombre_archivo.lower().endswith((".jpg", ".jpeg")):
                content_type = "image/jpeg"
            elif nombre_archivo.lower().endswith(".png"):
                content_type = "image/png"
            else:
                return Response(
                    {"error": "Unsupported file type"},
                    status=status.HTTP_400_BAD_REQUEST,
                )

            # Guardar archivo en AWS S3
            s3_client = boto3.client(
                "s3",
                aws_access_key_id=settings.AWS_ACCESS_KEY_ID,
                aws_secret_access_key=settings.AWS_SECRET_ACCESS_KEY,
                region_name=settings.AWS_S3_REGION_NAME,
            )

            try:
                s3_client.upload_fileobj(
                    archivo,
                    settings.AWS_STORAGE_BUCKET_NAME,
                    f"servicios/{nombre_archivo}",
                    ExtraArgs={"ACL": "public-read", "ContentType": content_type},
                )

                # Construir la URL del archivo en S3
                archivo_url = f"https://{settings.AWS_S3_CUSTOM_DOMAIN}/servicios/{nombre_archivo}"

                # Añadir la URL del archivo a los datos
                data["imagen_url"] = archivo_url
            except Exception as e:
                return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

        # Crear el servicio en la base de datos
        servicio_serializer = ServicioSerializer(data=data)
        servicio_serializer.is_valid(raise_exception=True)
        servicio_serializer.save()
        return Response(servicio_serializer.data, status=status.HTTP_201_CREATED)


class DetalleServicio(APIView):

    def get(self, request, id_servicio):
        servicio = get_object_or_404(Servicio, pk=id_servicio)
        servicio_serializer = ServicioSerializer(servicio)
        return Response(servicio_serializer.data)

    def put(self, request, id_servicio):
        servicio = get_object_or_404(Servicio, pk=id_servicio)
        archivo = request.FILES.get("archivo")

        data = request.data.copy()

        if archivo:
            try:
                nombre_archivo = archivo.name
                # Determinar el tipo de contenido correcto
                if nombre_archivo.lower().endswith((".jpg", ".jpeg")):
                    content_type = "image/jpeg"
                elif nombre_archivo.lower().endswith(".png"):
                    content_type = "image/png"
                else:
                    return Response(
                        {"error": "Unsupported file type"},
                        status=status.HTTP_400_BAD_REQUEST,
                    )

                s3_client = boto3.client(
                    "s3",
                    aws_access_key_id=settings.AWS_ACCESS_KEY_ID,
                    aws_secret_access_key=settings.AWS_SECRET_ACCESS_KEY,
                    region_name=settings.AWS_S3_REGION_NAME,
                )
                s3_client.upload_fileobj(
                    archivo,
                    settings.AWS_STORAGE_BUCKET_NAME,
                    f"servicios/{nombre_archivo}",
                    ExtraArgs={"ACL": "public-read", "ContentType": content_type},
                )
                archivo_url = f"https://{settings.AWS_S3_CUSTOM_DOMAIN}/servicios/{nombre_archivo}"
                data["imagen_url"] = archivo_url
            except Exception as e:
                return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

        servicio_serializer = ServicioSerializer(servicio, data=data)
        servicio_serializer.is_valid(raise_exception=True)
        servicio_serializer.save()
        return Response(servicio_serializer.data)

    def patch(self, request, id_servicio):
        servicio = get_object_or_404(Servicio, pk=id_servicio)
        archivo = request.FILES.get("archivo")

        data = request.data.copy()

        if archivo:
            try:
                nombre_archivo = archivo.name
                # Determinar el tipo de contenido correcto
                if nombre_archivo.lower().endswith((".jpg", ".jpeg")):
                    content_type = "image/jpeg"
                elif nombre_archivo.lower().endswith(".png"):
                    content_type = "image/png"
                else:
                    return Response(
                        {"error": "Unsupported file type"},
                        status=status.HTTP_400_BAD_REQUEST,
                    )

                s3_client = boto3.client(
                    "s3",
                    aws_access_key_id=settings.AWS_ACCESS_KEY_ID,
                    aws_secret_access_key=settings.AWS_SECRET_ACCESS_KEY,
                    region_name=settings.AWS_S3_REGION_NAME,
                )
                s3_client.upload_fileobj(
                    archivo,
                    settings.AWS_STORAGE_BUCKET_NAME,
                    f"servicios/{nombre_archivo}",
                    ExtraArgs={"ACL": "public-read", "ContentType": content_type},
                )
                archivo_url = f"https://{settings.AWS_S3_CUSTOM_DOMAIN}/servicios/{nombre_archivo}"
                data["imagen_url"] = archivo_url
            except Exception as e:
                return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

        servicio_serializer = ServicioSerializer(servicio, data=data, partial=True)
        servicio_serializer.is_valid(raise_exception=True)
        servicio_serializer.save()
        return Response(servicio_serializer.data)

    def delete(self, request, id_servicio):
        servicio = get_object_or_404(Servicio, pk=id_servicio)
        servicio.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
