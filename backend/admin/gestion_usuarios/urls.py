from django.urls import path
from .views import UsuarioListCreateAPIView, UsuarioDetailAPIView, ProveedorListAPIView, ClienteListAPIView

urlpatterns = [
    path('usuarios/', UsuarioListCreateAPIView.as_view(), name='usuarios'),
    path('usuarios/<int:id_usuario>/', UsuarioDetailAPIView.as_view(), name='usuario'),
    path('proveedores/', ProveedorListAPIView.as_view(), name='proveedores'),
    path('clientes/', ClienteListAPIView.as_view(), name='clientes'),
]