from django.contrib import admin
from .models import Author, SongGenre, Song, SongInstance
# Register your models here.
class AuthorAdmin(admin.ModelAdmin):
    list_display = ('name', 'date_of_birth', 'date_of_death')
class SongInstanceInline(admin.TabularInline):
    model = SongInstance
admin.site.register(SongGenre)
#admin.site.register(Song)
#admin.site.register(SongInstance)
#admin.site.register(Author)
admin.site.register(Author, AuthorAdmin)

@admin.register(Song)
class SongAdmin(admin.ModelAdmin):
    list_display = ('title', 'author', 'display_genre')
    inlines = [SongInstanceInline]

@admin.register(SongInstance)
class SongInstanceAdmin(admin.ModelAdmin):
    list_filter = ('status', 'song__genre')
