from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from gestion_chats.models import Chat, Mensaje
from gestion_chats.serializers import ChatSerializer, MensajeSerializer
from django.shortcuts import get_object_or_404


class ListaChat(APIView):
    def get(self, request):
        chats = Chat.objects.all()
        chat_serializer = ChatSerializer(chats, many=True)
        return Response(chat_serializer.data)


class ListaMensaje(APIView):
    def get(self, request, id_chat):
        mensajes = Mensaje.objects.filter(id_chat=id_chat)
        mensaje_serializer = MensajeSerializer(mensajes, many=True)
        return Response(mensaje_serializer.data)


class DetalleMensaje(APIView):
    def get(self, request, id_chat, id_mensaje):
        mensaje = get_object_or_404(Mensaje, id_chat=id_chat, pk=id_mensaje)
        mensaje_serializer = MensajeSerializer(mensaje)
        return Response(mensaje_serializer.data)
