from rest_framework import serializers
from .models import Report


class ChoicesField(serializers.Field):

    def __init__(self, choices, **kwargs):
        self._choices = choices
        super(ChoicesField, self).__init__(**kwargs)

    def to_representation(self, obj):
        return self._choices[obj]

    def to_internal_value(self, data):
        return getattr(self._choices, data)


class ReportSerializer(serializers.ModelSerializer):
    report_type = ChoicesField(choices=Report.REPORT_TYPES)

    class Meta:
        model = Report
