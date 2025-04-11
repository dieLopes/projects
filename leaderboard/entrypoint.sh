#!/bin/bash

echo "Esperando o banco iniciar..."
sleep 5

echo "Rodando migrations..."
flask db upgrade

echo "Iniciando a aplicação..."
exec flask run --host=0.0.0.0
