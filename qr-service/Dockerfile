FROM postgres:17.6-alpine

WORKDIR /app

RUN apk add --no-cache \
    python3 \
    py3-pip

COPY requirements.txt .
RUN python3 -m venv /opt/venv \
    && /opt/venv/bin/pip install --no-cache-dir -r requirements.txt

COPY app.py .

EXPOSE 8090

CMD ["/opt/venv/bin/python", "app.py"]
