from django.urls import path
from . import views
from rest_framework_simplejwt.views import TokenRefreshView, TokenVerifyView
from rest_framework_simplejwt.views import (
    TokenRefreshView,
    TokenObtainPairView,
    TokenVerifyView,
)


urlpatterns = [
    path(
        "usuarios/token/",
        TokenObtainPairView.as_view(),
        name="token_obtain_pair",
    ),
    path("usuarios/token/refresh/", TokenRefreshView.as_view(), name="token_refresh"),
    path("usuarios/token/verify/", TokenVerifyView.as_view(), name="token_verify"),
    path("usuarios/", views.ListaCreacionUsuarios.as_view(), name="usuarios"),
    path("usuarios/<int:id_usuario>/", views.DetalleUsuario.as_view(), name="usuario"),
    path("proveedores/", views.ListaProveedor.as_view(), name="proveedores"),
    path("clientes/", views.ListaCliente.as_view(), name="clientes"),
]
