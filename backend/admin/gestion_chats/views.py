from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from gestion_chats.models import Chat, Mensaje
from gestion_chats.serializers import ChatSerializer, MensajeSerializer
from django.http import Http404

class ChatListAPIView(APIView):
    def get(self, request):
        chats = Chat.objects.all()
        serializer = ChatSerializer(chats, many=True)
        return Response(serializer.data)

class MensajeAPIView(APIView):
    def get_object(self, id_chat):
        try:
            return Mensaje.objects.filter(id_chat=id_chat)
        except Mensaje.DoesNotExist:
            raise Http404

    def get(self, request, id_chat):
        mensajes = self.get_object(id_chat)
        serializer = MensajeSerializer(mensajes, many=True)
        return Response(serializer.data)

class MensajeDetailAPIView(APIView):
    def get_object(self, id_chat, id_mensaje):
        try:
            return Mensaje.objects.get(id_chat=id_chat, id=id_mensaje)
        except Mensaje.DoesNotExist:
            raise Http404

    def get(self, request, id_chat, id_mensaje):
        mensaje = self.get_object(id_chat, id_mensaje)
        serializer = MensajeSerializer(mensaje)
        return Response(serializer.data)
