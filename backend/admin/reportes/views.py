from rest_framework.views import APIView
from rest_framework.response import Response
from django.core.paginator import Paginator, EmptyPage, PageNotAnInteger
from gestion_usuarios.models import Usuario, Proveedor, Cliente
from .models import DetalleCalificacion
from gestion_usuarios.serializers import (
    UsuarioSerializer,
    ProveedorSerializer,
    ClienteSerializer,
)
from gestion_servicios.models import Servicio
from gestion_servicios.serializers import ServicioSerializer
from gestion_contratos.models import Contrato
from gestion_contratos.serializers import ContratoSerializer
from django.db.models import Count


class ListaUsuarios(APIView):

    def get(self, request):
        usuarios_recientes = Usuario.objects.order_by("-fh_creacion")[:3]
        usuario_serializer = UsuarioSerializer(usuarios_recientes, many=True)
        return Response(usuario_serializer.data)


class ListaContratos(APIView):

    def get(self, request):
        contratos_recientes = Contrato.objects.order_by("-fh_creacion")[:3]
        contrato_serializer = ContratoSerializer(contratos_recientes, many=True)
        return Response(contrato_serializer.data)


class ListaProveedores(APIView):

    def get(self, request):
        top_proveedores = Proveedor.objects.annotate(
            count_calificaciones=Count("detallecalificacion", distinct=True)
        ).order_by("-count_calificaciones", "-calificacion_promedio")
        proveedor_serializer = ProveedorSerializer(top_proveedores, many=True)
        return Response(proveedor_serializer.data)


class ListaServicios(APIView):

    def get(self, request):
        servicios = Servicio.objects.all()
        data = []

        for servicio in servicios:
            cantidad_proveedores = servicio.servicioproveedor_set.count()
            servicio_serializer = ServicioSerializer(servicio)
            servicio_data = servicio_serializer.data
            servicio_data["cantidad_proveedores"] = cantidad_proveedores
            data.append(servicio_data)

        return Response(data)


class ListaClientes(APIView):

    def get(self, request):
        clientes = Cliente.objects.all()
        data = []

        for cliente in clientes:
            cliente_serializer = ClienteSerializer(cliente)
            cliente_data = cliente_serializer.data
            cantidad_contratos = cliente.contrato_set.count()
            cliente_data["cantidad_contratos"] = cantidad_contratos
            data.append(cliente_data)

        paginator = Paginator(data, 20)
        page = request.GET.get("page")
        try:
            paginated_data = paginator.page(page)
        except PageNotAnInteger:
            paginated_data = paginator.page(1)
        except EmptyPage:
            paginated_data = paginator.page(paginator.num_pages)

        return Response(paginated_data.object_list)
