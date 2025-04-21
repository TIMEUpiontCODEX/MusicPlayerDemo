from django.db import models
from django.urls import reverse
import uuid

# Create your models here.
class SongGenre(models.Model):
    name=models.CharField(max_length=100,help_text="Enter the song genre")
    def __str__(self):
        return self.name

class Song(models.Model):
    title=models.CharField(max_length=100)
    author=models.ForeignKey("Author",on_delete=models.SET_NULL,null=True)
    summary=models.TextField(max_length=1000,help_text="Enter the song summary")
    genre=models.ManyToManyField(SongGenre,help_text="Select a song genre")
    file = models.FileField(upload_to='songs/', blank=True, null=True)

    def __str__(self):
        return self.title
    def get_absolute_url(self):
        return reverse("song-detail",args=[str(self.id)])
    def display_genre(self):
        return ','.join([genre.name for genre in self.genre.all()[:3]])
    display_genre.short_description='Genre'

class SongInstance(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, help_text="Unique ID")
    song = models.ForeignKey("Song", on_delete=models.SET_NULL, null=True)
    version = models.CharField(max_length=100, blank=True, null=True)  # 设为可选
    date = models.DateField(null=True, blank=True)
    uploader = models.ForeignKey('auth.User', on_delete=models.SET_NULL, null=True, blank=True)

    STATUS = (
        ("c", "copyright"),
        ("n", "NOcopyright"),
    )

    status = models.CharField(
        max_length=1,
        choices=STATUS,
        blank=True,
        default="c",
        help_text="song copyright"
    )

    class Meta:
        ordering = ["id"]

    def __str__(self):
        return f"{self.id}({self.song.title})"

class Author(models.Model):
    name = models.CharField(max_length=100)
    date_of_birth = models.DateField(null=True, blank=True)
    date_of_death = models.DateField('Died', null=True, blank=True)

    class Meta:
        ordering = ['name']

    def get_absolute_url(self):
        return reverse('author-detail', args=[str(self.id)])

    def __str__(self):
        return f' {self.name}'

