from ServerFunctions import *
from flask import Flask, request, jsonify

GetFirebase()

app = Flask(__name__)

@app.route('/hello_world')
def hello_world():
    return 'Hello, World!'

@app.route('/echo_call/<param>')
def get_echo_call(param):
    return jsonify({"param": param})

@app.route('/echo_call', methods=['POST'])
def post_echo_call():
    param = request.get_json()
    print(param)

    return jsonify(param)

@app.route("/test", methods=['POST'])
def test():
    params = request.get_json()
    print(f"received Json 데이터 : {params}\n")

    response = {
        "result": "ok"
    }

    return jsonify(response)

@app.route("/predict", methods=['POST'])
def predict() :
    params = request.get_json()
    print(f"received Json 데이터 : {params}\n")

    preds = getModel(params)
    
    print(preds)

    return jsonify(preds)

if __name__ == '__main__' :
#   from waitress import serve
#   serve(app, host="0.0.0.0", port=8080)
    app.run(host='0.0.0.0')
