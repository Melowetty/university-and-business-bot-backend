import io
import qrcode
from flask import Response, Flask, request, jsonify

app = Flask(__name__)

@app.post("/generate")
def generate_qr():
    payload = request.get_json(silent=True) or {}
    data = payload.get("data")
    size = int(payload.get("size", 300))

    if not data or not isinstance(data, str):
        return jsonify({"error": "Field 'data' must be a non-empty string"}), 400

    qr = qrcode.QRCode(
        version=None,
        error_correction=qrcode.constants.ERROR_CORRECT_H,
        box_size=10,
        border=2,
    )
    qr.add_data(data)
    qr.make(fit=True)

    img = qr.make_image(fill_color="black", back_color="white").convert("RGB")
    target_size = max(100, min(1200, size))
    img = img.resize((target_size, target_size))

    buf = io.BytesIO()
    img.save(buf, format="PNG")
    return Response(buf.getvalue(), mimetype="image/png")


@app.get("/health")
def health():
    return jsonify({"status": "UP"})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8090)
