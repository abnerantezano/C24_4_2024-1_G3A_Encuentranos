from django.urls import path
from .views import UsuarioListCreateAPIView, UsuarioDetailAPIView, ProveedorListAPIView, ClienteListAPIView, DistritoDetailAPIView, TipoUsuarioDetailAPIView

urlpatterns = [
    path('usuarios/', UsuarioListCreateAPIView.as_view(), name='usuarios'),
    path('usuarios/<int:pk>/', UsuarioDetailAPIView.as_view(), name='usuario'),
    path('proveedores/', ProveedorListAPIView.as_view(), name='proveedores'),
    path('clientes/', ClienteListAPIView.as_view(), name='clientes'),
    path('distritos/<int:pk>/', DistritoDetailAPIView.as_view(), name='distrito'),
    path('tipos/<int:pk>/', TipoUsuarioDetailAPIView.as_view(), name='tipo'),
]