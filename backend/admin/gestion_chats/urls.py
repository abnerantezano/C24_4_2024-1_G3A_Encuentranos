from django.urls import path
from .views import ChatListAPIView, MensajeAPIView, MensajeDetailAPIView

urlpatterns = [
    path('chats/', ChatListAPIView.as_view(), name='chats'),
    path('chats/<int:id_chat>/', MensajeAPIView.as_view(), name='mensajes'),
    path('chats/<int:id_chat>/<int:id_mensaje>/', MensajeDetailAPIView.as_view(), name='mensaje'),
]
