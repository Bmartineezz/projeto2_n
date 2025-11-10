#!/bin/bash

set -e

echo "=== Deploy com RDS PostgreSQL ==="

# Variáveis
APP_NAME="projeto-nuvem"
IMAGE_NAME="projeto-nuvem-api"
CONTAINER_NAME="projeto-nuvem-container"
PORT=8080

# Verificar se senha foi fornecida
if [ -z "$DB_PASSWORD" ]; then
    echo "❌ ERRO: Variável DB_PASSWORD não definida!"
    echo "Execute: export DB_PASSWORD='Mackenzie123'"
    exit 1
fi

# Cores
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${YELLOW}1. Parando container anterior...${NC}"
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

echo -e "${YELLOW}2. Removendo imagem anterior...${NC}"
docker rmi $IMAGE_NAME 2>/dev/null || true

echo -e "${YELLOW}3. Construindo nova imagem...${NC}"
docker build -t $IMAGE_NAME .

echo -e "${YELLOW}4. Iniciando container com RDS...${NC}"
docker run -d \
  --name $CONTAINER_NAME \
  -p $PORT:8080 \
  --restart unless-stopped \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_PASSWORD="$DB_PASSWORD" \
  $IMAGE_NAME

echo ""
echo -e "${GREEN}=== Deploy com RDS concluído! ===${NC}"
echo ""
echo "🗄️  Banco: PostgreSQL RDS"
echo "📍 Endpoint: projeto-nuvem-bd.c5ctnoflyhee.us-east-1.rds.amazonaws.com"
echo ""

# Aguardar inicialização
echo -e "${YELLOW}Aguardando conexão com RDS...${NC}"
sleep 15

# Verificar logs
echo -e "${YELLOW}Verificando logs...${NC}"
docker logs --tail 30 $CONTAINER_NAME

PUBLIC_IP=$(curl -s --connect-timeout 2 http://169.254.169.254/latest/meta-data/public-ipv4 2>/dev/null || echo "3.83.164.205")
echo ""
echo -e "${GREEN}✅ Aplicação disponível em: http://$PUBLIC_IP:$PORT${NC}"