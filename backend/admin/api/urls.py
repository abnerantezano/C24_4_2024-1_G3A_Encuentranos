from django.urls import path, include

urlpatterns = [
    path('', include('gestion_usuarios.urls'), name='gestion_usuarios'),
    path('', include('gestion_servicios.urls'), name='gestion_servicios'),
    path('', include('gestion_contratos.urls'), name='gestion_contratos'),
    path('', include('gestion_chats.urls'), name='gestion_chats'),
    path('', include('gestion_calificaciones.urls'), name='gestion_calificaciones'),
]
