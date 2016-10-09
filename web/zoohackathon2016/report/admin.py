from django.contrib import admin

from .models import Report


class ReportAdmin(admin.ModelAdmin):
    list_display = ('latitude', 'longitude', 'event_time',
                    'arrival_time', 'modified_time', 'report_type', 'device_id')
    list_filter = ('latitude', 'longitude', 'event_time',
                   'arrival_time', 'modified_time', 'report_type', 'device_id')
    search_fields = ['latitude', 'longitude', 'event_time',
                     'arrival_time', 'modified_time', 'report_type', 'device_id', ]

admin.site.register(Report, ReportAdmin)
