from django.urls import path
from . import views
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView

urlpatterns = [
    # JWT认证相关的URL
    path('api/token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/register/', views.api_register, name='api_register'),
    path('api/login/', views.api_login, name='api_login'),
    path('api/logout/', views.api_logout, name='api_logout'),
    
    # 歌曲相关的URL
    path('api/songs/<int:song_id>/download/', views.download_song_file, name='download_song_file'),
    path('api/songs/upload/', views.upload_song_file, name='upload_song_file'),
]
