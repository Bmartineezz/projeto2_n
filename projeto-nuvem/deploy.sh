#!/bin/bash

# Script de deploy para EC2
# Uso: ./deploy.sh

set -e

echo "=== Deploy do Projeto Nuvem na EC2 ==="

# Variáveis
APP_NAME="projeto-nuvem"
IMAGE_NAME="projeto-nuvem-api"
CONTAINER_NAME="projeto-nuvem-container"
PORT=8080

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}1. Parando container anterior (se existir)...${NC}"
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

echo -e "${YELLOW}2. Removendo imagem anterior (se existir)...${NC}"
docker rmi $IMAGE_NAME 2>/dev/null || true

echo -e "${YELLOW}3. Construindo nova imagem Docker...${NC}"
docker build -t $IMAGE_NAME .

echo -e "${YELLOW}4. Iniciando container...${NC}"
docker run -d \
  --name $CONTAINER_NAME \
  -p $PORT:8080 \
  --restart unless-stopped \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=${DB_PASSWORD:-Mackenzie123} \
  $IMAGE_NAME

echo -e "${GREEN}=== Deploy concluído com sucesso! ===${NC}"
echo -e "Aplicação rodando em: http://$(curl -s http://169.254.169.254/latest/meta-data/public-ipv4):$PORT"
echo ""
echo "Para verificar os logs: docker logs -f $CONTAINER_NAME"
echo "Para parar: docker stop $CONTAINER_NAME"