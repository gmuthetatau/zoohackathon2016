#!/usr/bin/env python
# encoding: utf-8


from flask import Flask, request


app = Flask(__name__)


@app.route("/report", methods=["POST"])
def report():
    data = request.json
    print(data['lat'])
    print(data['lng'])
    print(data['user'])
    print(data['type'])
    print(data['reported_time'])
    return "Thank you for your report\n"


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5001, debug=True)
