# Web Backend

## Setup

In order to setup the web backend, install the dependencies using `pip install -r requirements.txt` and then run `python manage.py migrate`.

## Usage

You can submit a report like so:

```sh
curl -X POST "http://localhost:5001/api/reports/" -H "Content-Type: application/json" -d '{"report_type": "A", "latitude": 1, "longitude": 2, "device_id": "curl", "event_time": "2016-10-07 18:00"}' -H "Accept: application/json"
```

You can view a JSON list of the reports by using an HTTP GET request:

```sh
curl "http://localhost:5001/api/reports/?format=json"
```

You can replace `json` format with `xml`, `yaml`, or `csv` for other formats. Browseable API documentation is available at [http://localhost:5001/api/](http://localhost:5001/api/).
