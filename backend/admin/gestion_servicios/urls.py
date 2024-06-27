from django.urls import path
from . import views

urlpatterns = [
    path("servicios/", views.ListaCreacionServicio.as_view(), name="servicios"),
    path(
        "servicios/<int:id_servicio>/", views.DetalleServicio.as_view(), name="servicio"
    ),
]
