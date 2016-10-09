import pytest
import json
pytestmark = pytest.mark.django_db


def test_api_endpoints(client):
    url = '/api/reports/'
    content = client.get(url)
    assert content.status_code == 200
    data = {
        "report_type": "A",
        "latitude": "0.000006",
        "longitude": "0.000005",
        "event_time": "2016-10-09T06:30:35Z",
        "arrival_time": "2016-10-09T06:30:40.276553Z",
        "modified_time": "2016-10-09T06:30:40.276586Z",
        "device_id": "23123123123123123"
    }
    content = client.post(url, data=json.dumps(data),
                          content_type='application/json')
    assert content.status_code == 201
