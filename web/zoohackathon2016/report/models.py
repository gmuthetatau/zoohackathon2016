from __future__ import unicode_literals

from django.db import models
from model_utils import Choices

class Report(models.Model):
    """Store report information"""
    REPORT_TYPES = Choices(
        ('A', 'Shop/Market'),
        ('B', 'Restaurant/Eatery'),
        ('C', 'Live Animal Display'),
        ('D', 'Poaching'),
        ('E', 'Other'),
    )
    latitude = models.DecimalField(max_digits=9, decimal_places=6)
    longitude = models.DecimalField(max_digits=9, decimal_places=6)
    event_time = models.DateTimeField()
    arrival_time = models.DateTimeField(auto_now_add=True)
    modified_time = models.DateTimeField(auto_now=True)
    report_type = models.CharField(max_length=2, choices=REPORT_TYPES)
    device_id = models.CharField(max_length=50)

    def __unicode__(self):
        return u"({0}, {1}) {2}".format(self.latitude, self.longitude,
                                        self.get_report_type_display())
