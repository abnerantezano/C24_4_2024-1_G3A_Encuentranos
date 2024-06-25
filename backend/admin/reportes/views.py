from rest_framework.views import APIView
from rest_framework.response import Response
from django.core.paginator import Paginator, EmptyPage, PageNotAnInteger
from gestion_usuarios.models import Usuario, Proveedor, Cliente
from gestion_usuarios.serializers import UsuarioSerializer, ProveedorSerializer, ClienteSerializer
from gestion_servicios.models import Servicio
from gestion_contratos.models import Contrato
from gestion_contratos.serializers import ContratoSerializer
from django.db.models import Count

class ReporteUsuariosAPIView(APIView):
    def get(self, request):
        usuarios_recientes = Usuario.objects.order_by('-fh_creacion')[:3]
        serializer = UsuarioSerializer(usuarios_recientes, many=True)
        return Response(serializer.data)

class ReporteContratosAPIView(APIView):
    def get(self, request):
        contratos_recientes = Contrato.objects.order_by('-fh_creacion')[:3]
        serializer = ContratoSerializer(contratos_recientes, many=True)
        return Response(serializer.data)

class ReporteTopProveedoresAPIView(APIView):
    def get(self, request):
        top_proveedores = Proveedor.objects.order_by('-calificacion_promedio')[:4]
        serializer = ProveedorSerializer(top_proveedores, many=True)
        return Response(serializer.data)

class ReporteServiciosAPIView(APIView):
    def get(self, request):
        servicios_con_proveedores = Servicio.objects.all()
        data = []
        for servicio in servicios_con_proveedores:
            proveedores_count = servicio.servicioproveedor_set.count()
            servicio_data = {
                'id_servicio': servicio.id_servicio,
                'nombre': servicio.nombre,
                'descripcion': servicio.descripcion,
                'imagen_url': servicio.imagen_url,
                'fh_creacion': servicio.fh_creacion,
                'cantidad_proveedores': proveedores_count
            }
            data.append(servicio_data)
        return Response(data)

class ReporteClientesAPIView(APIView):
    def get(self, request):
        clientes_con_mas_contratos = Cliente.objects.annotate(num_contratos=Count('contrato')).order_by('-num_contratos')[:20]
        paginator = Paginator(clientes_con_mas_contratos, 20)
        page = request.GET.get('page')
        try:
            clientes = paginator.page(page)
        except PageNotAnInteger:
            clientes = paginator.page(1)
        except EmptyPage:
            clientes = paginator.page(paginator.num_pages)

        serializer = ClienteSerializer(clientes, many=True)
        return Response(serializer.data)
