from django.urls import path
from . import views

urlpatterns = [
    path(
        "reportes/usuarios-recientes/",
        views.ListaUsuarios.as_view(),
        name="reporte_usuarios_recientes",
    ),
    path(
        "reportes/contratos-recientes/",
        views.ListaContratos.as_view(),
        name="reporte_contratos_recientes",
    ),
    path(
        "reportes/top-proveedores/",
        views.ListaProveedores.as_view(),
        name="reporte_top_proveedores",
    ),
    path(
        "reportes/servicios-proveedores/",
        views.ListaServicios.as_view(),
        name="reporte_servicios_con_proveedores",
    ),
    path(
        "reportes/clientes-contratos/",
        views.ListaClientes.as_view(),
        name="reporte_clientes_con_mas_contratos",
    ),
]
