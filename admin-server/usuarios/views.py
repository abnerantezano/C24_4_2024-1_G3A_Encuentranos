from rest_framework.views import APIView
from rest_framework.response import Response
from .models import Cliente, Proveedor, TipoUsuario, Usuario
from .serializers import ClienteSerializer, ProveedorSerializer, TipoUsuarioSerializer, UsuarioSerializer


class ListaTiposUsuario(APIView):
    def get(self, request):
        tipos_usuario = TipoUsuario.objects.all()
        serTipo = TipoUsuarioSerializer(tipos_usuario, many=True)
        return Response(serTipo.data)

    def post(self, request):
        serTipo = TipoUsuarioSerializer(data=request.data)
        serTipo.is_valid(raise_exception=True)
        serTipo.save()
        return Response(serTipo)


class DetalleTipoUsuario(APIView):
    def get(self, request,  id_tipo):
        tipo_usuario = TipoUsuario.objects.get(pk=id_tipo)
        serTipo = TipoUsuarioSerializer(tipo_usuario)
        return Response(serTipo.data)

    def pull(self, request, id_tipo):
        tipo_usuario = TipoUsuario.objects.get(pk=id_tipo)
        serTipo = TipoUsuarioSerializer(tipo_usuario, data=request.data)
        serTipo.is_valid(raise_exception=True)
        serTipo.save()
        return Response(serTipo.data)

    def delete(self, request, id_tipo):
        tipo_usuario = TipoUsuario.objects.get(pk=id_tipo)
        serTipo = TipoUsuarioSerializer(tipo_usuario)
        tipo_usuario.delete()
        return Response(serTipo.data)


class ListaUsuarios(APIView):
    def get(self, request):
        usuarios = Usuario.objects.all()
        serUsuario = UsuarioSerializer(usuarios, many=True)
        return Response(serUsuario.data)

    def post(self, request):
        serUsuario = UsuarioSerializer(data=request.data)
        serUsuario.is_valid(raise_exception=True)
        serUsuario.save()
        return Response(serUsuario)


class DetalleUsuario(APIView):
    def get(self, request,  id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        serUsuario = UsuarioSerializer(usuario)
        return Response(serUsuario.data)

    def pull(self, request, id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        serUsuario = UsuarioSerializer(usuario, data=request.data)
        serUsuario.is_valid(raise_exception=True)
        serUsuario.save()
        return Response(serUsuario.data)

    def delete(self, request, id_usuario):
        usuario = Usuario.objects.get(pk=id_usuario)
        serUsuario = UsuarioSerializer(usuario)
        usuario.delete()
        return Response(serUsuario.data)


class ListaProveedores(APIView):
    def get(self, request):
        proveedores = Proveedor.objects.all()
        serProveedor = ProveedorSerializer(proveedores, many=True)
        return Response(serProveedor.data)

    def post(self, request):
        serProveedor = ProveedorSerializer(data=request.data)
        serProveedor.is_valid(raise_exception=True)
        serProveedor.save()
        return Response(serProveedor)


class DetalleProveedor(APIView):
    def get(self, request,  id_proveedor):
        proveedor = Proveedor.objects.get(pk=id_proveedor)
        serProveedor = ProveedorSerializer(proveedor)
        return Response(serProveedor.data)

    def pull(self, request, id_proveedor):
        proveedor = Proveedor.objects.get(pk=id_proveedor)
        serProveedor = ProveedorSerializer(proveedor, data=request.data)
        serProveedor.is_valid(raise_exception=True)
        serProveedor.save()
        return Response(serProveedor.data)

    def delete(self, request, id_proveedor):
        proveedor = Proveedor.objects.get(pk=id_proveedor)
        serProveedor = ProveedorSerializer(proveedor)
        proveedor.delete()
        return Response(serProveedor.data)


class ListaClientes(APIView):
    def get(self, request):
        clientes = Cliente.objects.all()
        serCliente = ClienteSerializer(clientes, many=True)
        return Response(serCliente.data)

    def post(self, request):
        serCliente = ClienteSerializer(data=request.data)
        serCliente.is_valid(raise_exception=True)
        serCliente.save()
        return Response(serCliente)


class DetalleCliente(APIView):
    def get(self, request,  id_cliente):
        cliente = Cliente.objects.get(pk=id_cliente)
        serCliente = ClienteSerializer(cliente)
        return Response(serCliente.data)

    def pull(self, request, id_cliente):
        cliente = Cliente.objects.get(pk=id_cliente)
        serCliente = ClienteSerializer(cliente, data=request.data)
        serCliente.is_valid(raise_exception=True)
        serCliente.save()
        return Response(serCliente.data)

    def delete(self, request, id_cliente):
        cliente = Cliente.objects.get(pk=id_cliente)
        serCliente = ClienteSerializer(cliente)
        cliente.delete()
        return Response(serCliente.data)
