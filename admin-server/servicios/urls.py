from django.urls import path
from . import views

urlpatterns = [
    path('listar', views.ListaServicios.as_view(), name='listar_servicios'),
    path('detalle/<int:id_servicio>',
         views.DetalleServicio.as_view(), name='detalle_servicio'),
]
