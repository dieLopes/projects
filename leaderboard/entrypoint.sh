#!/bin/bash

# Aguarda o banco estar pronto
echo "Aguardando o banco de dados iniciar..."
while ! nc -z db 5432; do
  sleep 1
done

# Aplica as migrations automaticamente
echo "Aplicando migrations..."
flask db upgrade

# Inicia o app
echo "Iniciando o servidor Flask..."
python run.py
