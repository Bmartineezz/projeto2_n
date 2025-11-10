# Projeto Integrador – Cloud Developing 2025/1  

> Aplicação CRUD simples com **API REST**, **EC2**, **RDS**, **VPC** e **AWS Lambda**.  
> Projeto desenvolvido na disciplina **Serviços em Nuvem**.

---

## Integrantes do grupo

| Nome | Responsabilidade |
|------|------------------|
| **Bruna França Martinez** | Criação do front-end, API e banco de dados |
| **Gabriela Nellessen de Sousa** | Criação da VPC e desenvolvimento do backend |
| **Isadora Caetano Brandão de Sousa** | Documentação e auxílio na criação da API |
| **Letícia Santiago da Silva** | Desenvolvimento do backend e configuração da instância EC2 |

---

## 1. Visão Geral

Este projeto implementa uma aplicação web completa de avaliação de filmes utilizando arquitetura moderna em nuvem AWS. O sistema permite que usuários cadastrem filmes, 
criem avaliações com notas (1-5 estrelas) e comentários, além de gerenciar perfis de usuários.

## 1.1 Domínio de Negócio
O domínio escolhido foi catálogo e avaliação de filmes por apresentar:

Relacionamentos claros entre entidades (Filme ↔ Avaliação ↔ Usuário)
Operações CRUD intuitivas para demonstração
Potencial para estatísticas agregadas (endpoint /report)
Facilidade de validação de funcionalidades

---

## 2. Arquitetura da Aplicação

![Diagrama da Arquitetura](https://image2url.com/images/1762736035716-b23d6079-4ece-4a69-99ba-2378bc1e7540.png)

| Camada | Serviço AWS | Descrição |
|--------|--------------|-----------|
| **Frontend** | React (hospedado localmente) | Interface web para acesso ao CRUD |
| **Backend** | EC2 (Instância pública) | API REST desenvolvida em **Spring Boot** |
| **Banco de Dados** | Amazon RDS (PostgreSQL) | Armazena dados do sistema, hospedado em sub-rede privada |
| **VPC** | projeto_nuvem-vpc | Contém sub-redes públicas e privadas |
| **Sub-redes** | Private (RDS) e Public (EC2) | Organizam o tráfego interno e externo |
| **Segurança** | Security Groups | Controlam o acesso entre EC2 e RDS |
| **Lambda (opcional)** | AWS Lambda `/report` | Gera relatórios em formato JSON |
| **API Gateway** | Amazon API Gateway | Gerencia rotas da API e integrações com Lambda |

---

### Resumo da Configuração

- **Banco de dados:** `jdbc:postgresql://projeto-nuvem-bd.c5ctnoflyhee.us-east-1.rds.amazonaws.com:5432/postgres`  
- **Porta:** `5432`  
- **Região:** `us-east-1`  
- **VPC:** `projeto_nuvem-vpc`  
- **Sub-redes privadas:** `projeto_nuvem-subnet-private1-us-east-1a` e `projeto_nuvem-subnet-private2-us-east-1b` 
- **Acesso:** Somente EC2 tem permissão para acessar o RDS  
- **Publicamente acessível:** Não  
- **Segurança:** Controlada via Security Groups

---

## 3. Como Rodar Localmente

Clone o repositório e configure o ambiente local:


