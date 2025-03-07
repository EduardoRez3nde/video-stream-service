
# Stream Video App

Este é um projeto de aplicação de streaming de vídeo desenvolvido com Spring Boot e Docker. Ele a reprodução de vídeos no navegador, com suporte para armazenamento local.

---

## 📋 **Tabela de Conteúdos**
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

---

## 🚀 **Funcionalidades**
- Upload de vídeos na pasta **/uploads/videos**.
- Reprodução de vídeos armazenados localmente.
- Configuração de ambiente com Docker para facilitar a execução.

---

## 🛠️ **Tecnologias Utilizadas**
- **Spring Boot**: Framework para desenvolvimento da aplicação.
- **Docker**: Para conteinerização e execução do projeto.
- **Java**: Linguagem de programação principal.
- **Maven**: Gerenciamento de dependências.
- **Docker Compose**: Para orquestração de contêineres.

---

## 📋 **Pré-requisitos**
Antes de começar, você precisará ter instalado em sua máquina as seguintes ferramentas:
- [Java JDK 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## 🚀 **Como Executar o Projeto**

### **1. Clone o Repositório**
```bash
git clone https://git@github.com:EduardoRez3nde/video-stream-service.git
```

### **2. Construa o Projeto com Maven**
```bash
mvn clean install
```

### **3. Execute com Docker Compose**
O projeto está configurado para rodar com Docker Compose. Execute o seguinte comando na raiz do projeto:
```bash
docker-compose up --build
```

### **4. Acesse a Aplicação**
Após o Docker Compose terminar de subir os contêineres, acesse a aplicação no navegador:
```
http://localhost:8080
```
### **5. Endpoint:**
```
/videos/stream?videoUrl=url_do_video
```
---

## 🤝 **Contribuição**
Contribuições são bem-vindas! Siga os passos abaixo para contribuir:

1. Faça um **fork** do projeto.
2. Crie uma nova branch com sua feature ou correção:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça commit das suas alterações:
   ```bash
   git commit -m 'Adicionando nova feature'
   ```
4. Envie para o repositório remoto:
   ```bash
   git push origin minha-feature
   ```
5. Abra um **Pull Request** e descreva suas alterações.

---

## 📄 **Licença**
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 **Autor**
- **Eduardo** - [GitHub](https://github.com/EduardoRez3nde) | [LinkedIn](https://www.linkedin.com/in/eduardo-rezende-911980265/)

---

