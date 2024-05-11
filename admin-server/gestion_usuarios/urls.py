from django.urls import path
from . import views

urlpatterns = [
    path('tipos', views.TipoUsuariosView.as_view(), name='tipos_usuario'),
    path('tipos/<int:id_tipo>',
         views.TipoUsuarioDetailView.as_view(), name='detalle_tipo'),
    path('', views.UsuarioView.as_view(), name='usuarios'),
    path('<int:id_usuario>', views.UsuarioDetailView.as_view(),
         name='detalle_usuario')
]
