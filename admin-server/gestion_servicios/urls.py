from django.urls import path
from . import views


urlpatterns = [
    path('', views.ServiciosView.as_view(), name='servicios'),
    path('<int:id_servicio>',
         views.ServicioDetailView.as_view(), name='detalle_servicio')
]
