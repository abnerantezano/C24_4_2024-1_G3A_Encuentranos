from django.urls import path
from . import views

urlpatterns = [
    path("chats/", views.ListaChat.as_view(), name="chats"),
    path("chats/<int:id_chat>/", views.ListaMensaje.as_view(), name="mensajes"),
    path(
        "chats/<int:id_chat>/<int:id_mensaje>/",
        views.DetalleMensaje.as_view(),
        name="mensaje",
    ),
]
