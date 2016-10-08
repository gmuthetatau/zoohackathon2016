from rest_framework import viewsets

from report.serializers import ReportSerializer
from report.models import Report


class ReportViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Report.objects.all()
    serializer_class = ReportSerializer
