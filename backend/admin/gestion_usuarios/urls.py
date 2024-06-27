from django.urls import path
from . import views

urlpatterns = [
    path("usuarios/", views.ListaCreacionUsuarios.as_view(), name="usuarios"),
    path("usuarios/<int:id_usuario>/", views.DetalleUsuario.as_view(), name="usuario"),
    path("proveedores/", views.ListaProveedor.as_view(), name="proveedores"),
    path("clientes/", views.ListaCliente.as_view(), name="clientes"),
]
