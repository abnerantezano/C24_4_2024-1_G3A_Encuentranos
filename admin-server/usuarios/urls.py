from django.urls import path
from . import views

urlpatterns = [
    # Usuarios
    path('listar', views.ListaUsuarios.as_view(), name='listar_usuarios'),
    path('detalle/<int:id_usuario>',
         views.DetalleUsuario.as_view(), name='detalle_usuario'),
    # Tipos
    path('tipos/listar', views.ListaTiposUsuario.as_view(), name='listar_tipos'),
    path('detalle/<int:id_tipo>',
         views.DetalleTipoUsuario.as_view(), name='detalle_tipo'),
    # Proveedores
    path('proveedores/listar', views.ListaProveedores.as_view(),
         name='listar_proveedores'),
    path('detalle/<int:id_proveedor>',
         views.DetalleProveedor.as_view(), name='detalle_proveedor'),
    # Clientes
    path('clientes/listar', views.ListaClientes.as_view(), name='listar_clientes'),
    path('detalle/<int:id_cliente>',
         views.DetalleCliente.as_view(), name='detalle_cliente'),
]
