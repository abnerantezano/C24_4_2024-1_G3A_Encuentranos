from django.urls import path
from . import views
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

urlpatterns = [
    path("token/", TokenObtainPairView.as_view(), name="token_obtain_pair"),
    path("token/refresh/", TokenRefreshView.as_view(), name="logout"),
    path("usuarios/", views.ListaCreacionUsuarios.as_view(), name="usuarios"),
    path("usuarios/<int:id_usuario>/", views.DetalleUsuario.as_view(), name="usuario"),
    path("proveedores/", views.ListaProveedor.as_view(), name="proveedores"),
    path("clientes/", views.ListaCliente.as_view(), name="clientes"),
]
