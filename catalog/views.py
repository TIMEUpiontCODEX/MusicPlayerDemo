from django.shortcuts import render, redirect
from django.http import FileResponse, JsonResponse
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_framework import status
from .models import Song
from .serializers import SongFileSerializer
from django.shortcuts import get_object_or_404
import os
from .models import SongInstance,Song,SongGenre,Author
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import MultiPartParser, FormParser
from django.core.files.storage import default_storage
from django.core.files.base import ContentFile
from rest_framework_simplejwt.tokens import RefreshToken
from django.conf import settings
import mimetypes


def index(request):
    num_songs=Song.objects.all().count()
    num_genre=SongGenre.objects.all().count()
    num_instances_available = SongInstance.objects.filter(status__exact='c').count()
    num_authors = Author.objects.count()

    return render(
        request,
        'index.html',
        context={'num_songs': num_songs, 'num_genre': num_genre,
                 'num_instances_available': num_instances_available, 'num_authors': num_authors},
    )


@api_view(['GET'])
def song_file_detail(request, song_id):
    """
    获取歌曲文件信息和下载链接
    """
    song = get_object_or_404(Song, pk=song_id)
    serializer = SongFileSerializer(song, context={'request': request})
    return Response(serializer.data)


@api_view(['POST'])
@permission_classes([AllowAny])
def api_register(request):
    try:
        username = request.data.get('username')
        password = request.data.get('password')
        email = request.data.get('email')

        if not username or not password:
            return JsonResponse({'error': '用户名和密码是必需的'}, status=400)

        if User.objects.filter(username=username).exists():
            return JsonResponse({'error': '用户名已存在'}, status=400)

        user = User.objects.create_user(username=username, password=password, email=email)
        return JsonResponse({'message': '注册成功'}, status=201)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)

@api_view(['POST'])
@permission_classes([AllowAny])
def api_login(request):
    username = request.data.get('username')
    password = request.data.get('password')

    user = authenticate(username=username, password=password)
    if user is not None:
        login(request, user)
        return JsonResponse({'message': '登录成功'})
    else:
        return JsonResponse({'error': '用户名或密码错误'}, status=401)

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def api_logout(request):
    logout(request)
    return JsonResponse({'message': '登出成功'})

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def upload_song_file(request):
    if 'file' not in request.FILES:
        return JsonResponse({'error': '没有上传文件'}, status=400)

    file = request.FILES['file']
    
    # 检查文件类型
    if file.content_type not in settings.ALLOWED_FILE_TYPES:
        return JsonResponse({'error': '不支持的文件类型'}, status=400)
    
    # 检查文件大小
    if file.size > settings.MAX_UPLOAD_SIZE:
        return JsonResponse({'error': '文件太大'}, status=400)

    try:
        # 创建新的Song对象
        song = Song.objects.create(
            title=os.path.splitext(file.name)[0],  # 使用文件名作为标题
            file=file  # 直接将文件赋值给FileField
        )
        
        return JsonResponse({
            'message': '文件上传成功',
            'id': song.id,
            'title': song.title,
            'file_name': os.path.basename(song.file.name)
        }, status=201)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)

@api_view(['GET'])
@permission_classes([IsAuthenticated])
def download_song_file(request, song_id):
    try:
        song = Song.objects.get(id=song_id)
        
        if not song.file:
            return JsonResponse({'error': '歌曲文件不存在'}, status=404)
            
        file_path = song.file.path  # 使用 FileField 的 path 属性获取完整路径
        
        if not os.path.exists(file_path):
            return JsonResponse({'error': '文件不存在'}, status=404)
        
        content_type, _ = mimetypes.guess_type(file_path)
        response = FileResponse(open(file_path, 'rb'), content_type=content_type)
        response['Content-Disposition'] = f'attachment; filename="{os.path.basename(song.file.name)}"'
        return response
    except Song.DoesNotExist:
        return JsonResponse({'error': '歌曲不存在'}, status=404)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)