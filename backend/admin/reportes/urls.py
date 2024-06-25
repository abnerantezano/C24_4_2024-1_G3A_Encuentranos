from django.urls import path
from . import views

urlpatterns = [
    path('reportes/usuarios-recientes/', views.ReporteUsuariosAPIView.as_view(), name='reporte_usuarios_recientes'),
    path('reportes/contratos-recientes/', views.ReporteContratosAPIView.as_view(), name='reporte_contratos_recientes'),
    path('reportes/top-proveedores/', views.ReporteTopProveedoresAPIView.as_view(), name='reporte_top_proveedores'),
    path('reportes/servicios-proveedores/', views.ReporteServiciosAPIView.as_view(), name='reporte_servicios_con_proveedores'),
    path('reportes/clientes-contratos/', views.ReporteClientesAPIView.as_view(), name='reporte_clientes_con_mas_contratos'),
]