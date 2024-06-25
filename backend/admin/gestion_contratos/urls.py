from django.urls import path
from gestion_contratos.views import ContratoListAPIView, ContratoDetailAPIView

urlpatterns = [
    path('contratos/', ContratoListAPIView.as_view(), name='contrato-list'),
    path('contratos/<int:id_contrato>/', ContratoDetailAPIView.as_view(), name='contrato-detail'),
]