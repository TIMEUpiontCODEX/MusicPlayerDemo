from rest_framework import serializers
from .models import Song

class SongFileSerializer(serializers.ModelSerializer):
    file_url = serializers.SerializerMethodField()
    file_content = serializers.SerializerMethodField()

    class Meta:
        model = Song
        fields = ['id', 'title', 'file_url', 'file_content']

    def get_file_url(self, obj):
        if obj.file:
            return self.context['request'].build_absolute_uri(obj.file.url)
        return None

    def get_file_content(self, obj):
        if obj.file:
            return {
                'filename': obj.file.name.split('/')[-1],
                'size': obj.file.size,
                'content_type': 'audio/mpeg'  # 根据实际文件类型调整
            }
        return None
