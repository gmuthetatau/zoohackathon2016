#!/usr/bin/env python
# encoding: utf-8


from flask import Flask


app = Flask(__name__)


@app.route("/report", methods=["POST"])
def report():
    data = request.json
    print(data)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5001, debug=True)
