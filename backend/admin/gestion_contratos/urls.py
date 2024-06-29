from django.urls import path
from . import views

urlpatterns = [
    path("contratos/", views.ListaCreacionContrato.as_view(), name="contrato-list"),
    path(
        "contratos/<int:id_contrato>/",
        views.DetalleContratoVista.as_view(),
        name="contrato-detail",
    ),
]
