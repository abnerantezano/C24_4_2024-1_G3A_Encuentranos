from django.urls import path, include

urlpatterns = [
    path("", include("gestion_usuarios.urls"), name="gestion_usuarios"),
    path("", include("gestion_servicios.urls"), name="gestion_servicios"),
    path("", include("gestion_contratos.urls"), name="gestion_contratos"),
    path("", include("reportes.urls"), name="reportes"),
]
