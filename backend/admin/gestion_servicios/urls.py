from django.urls import path
from .views import ServicioListCreateAPIView, ServicioDetailAPIView

urlpatterns = [
    path('servicios/', ServicioListCreateAPIView.as_view(), name='servicios'),
    path('servicios/<int:id_servicio>/', ServicioDetailAPIView.as_view(), name='servicio'),
]