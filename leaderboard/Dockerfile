FROM python:3.11-slim

WORKDIR /app

ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# Dá permissão para o entrypoint
RUN chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
