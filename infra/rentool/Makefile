DC = docker compose
DB = docker build
PRUNE = docker system prune

KONG_API_FILE = docker/kong/docker-compose.yaml
KEYCLOAK_FILE = docker/keycloak/docker-compose.yaml
PGADMIN4_FILE = docker/pgadmin4/docker-compose.yaml
RENTOOL_FILE = docker/services/docker-compose.yaml

KONG_DOCKERFILE = ./docker/kong/

# RenTool
# Команда для запуска сервиса tool
.PHONY: start-tool-service
start-tool-service:
	${DC} -f ${RENTOOL_FILE} up -d tool

# Команда для остановки и удаления сервиса tool
.PHONY: drop-tool-service
drop-tool-service:
	${DC} -f ${RENTOOL_FILE} down tool

# Команда для запуска контейнера базы данных tool
.PHONY: start-tool-db
start-tool-db:
	${DC} -f ${RENTOOL_FILE} up -d postgresql-tool

# Команда для остановки и удаления контейнера базы данных tool
.PHONY: drop-tool-db
drop-tool-db:
	${DC} -f ${RENTOOL_FILE} down postgresql-tool

# Команда для миграций базы данных сервиса tool
.PHONY: start-tool-migrations
start-tool-migrations:
	${DC} -f ${RENTOOL_FILE} up -d liquibase-migrations-tool

# Команда для запуска сервиса rent
.PHONY: start-rent-service
start-rent-service:
	${DC} -f ${RENTOOL_FILE} up -d rent

# Команда для остановки и удаления сервиса rent
.PHONY: drop-rent-service
drop-rent-service:
	${DC} -f ${RENTOOL_FILE} down rent

# Команда для запуска контейнера базы данных rent
.PHONY: start-rent-db
start-rent-db:
	${DC} -f ${RENTOOL_FILE} up -d postgresql-rent

# Команда для остановки и удаления контейнера базы данных rent
.PHONY: drop-rent-db
drop-rent-db:
	${DC} -f ${RENTOOL_FILE} down postgresql-rent

# Команда для миграций базы данных сервиса rent
.PHONY: start-rent-migrations
start-rent-migrations:
	${DC} -f ${RENTOOL_FILE} up -d liquibase-migrations-rent

# Команда для запуска сервиса user
.PHONY: start-user-service
start-user-service:
	${DC} -f ${RENTOOL_FILE} up -d user

# Команда для остановки и удаления сервиса user
.PHONY: drop-user-service
drop-user-service:
	${DC} -f ${RENTOOL_FILE} down user

# Команда для запуска контейнера базы данных user
.PHONY: start-user-db
start-user-db:
	${DC} -f ${RENTOOL_FILE} up -d postgresql-user

# Команда для остановки и удаления контейнера базы данных user
.PHONY: drop-user-db
drop-user-db:
	${DC} -f ${RENTOOL_FILE} down postgresql-user

# Команда для миграций базы данных сервиса user
.PHONY: start-user-migrations
start-user-migrations:
	${DC} -f ${RENTOOL_FILE} up -d liquibase-migrations-user

# Minio
# Команда для запуска контейнера s3 хранилища minio
.PHONY: start-minio
start-minio:
	${DC} -f ${RENTOOL_FILE} up -d minio

# Команда для остановки и удаления контейнера s3 хранилища minio
.PHONY: drop-minio
drop-minio:
	${DC} -f ${RENTOOL_FILE} down minio
# KONG
# Комада для сборки кастомного image, включающего нужные плагины 
.PHONY: build-kong
build-kong:
	${DB} -t kong:3.4-ubuntu-oidc ${KONG_DOCKERFILE}

# Команда для запуска контейнера kong
.PHONY: start-kong
start-kong:
	${DC} -f ${KONG_API_FILE} up -d kong-dbless

# Команда для остановки и удаления контейнера kong
.PHONY: drop-kong
drop-kong:
	${DC} -f ${KONG_API_FILE} down kong-dbless
# PGADMIN4
# Команда для запуска контейнера pgadmin4
.PHONY: start-pgadmin4
start-pgadmin4:
	${DC} -f ${PGADMIN4_FILE} up --build -d pgadmin4

# Команда для остановки и удаления контейнера pgadmin4
.PHONY: drop-pgadmin4
drop-pgadmin4:
	${DC} -f ${PGADMIN4_FILE} down pgadmin4

# KEYCLOAK
# Команда для запуска контейнера keycloak
.PHONY: start-keycloak
start-keycloak:
	${DC} -f ${KEYCLOAK_FILE} up -d keycloak

# Команда для остановки и удаления контейнера keycloak
.PHONY: drop-keycloak
drop-keycloak:
	${DC} -f ${KEYCLOAK_FILE} down keycloak

# Команда для запуска контейнера keycloak-db
.PHONY: start-keycloak-db
start-keycloak-db:
	${DC} -f ${KEYCLOAK_FILE} up -d keycloak-db

# Команда для остановки и удаления контейнера keycloak-db
.PHONY: drop-keycloak-db
drop-keycloak-db:
	${DC} -f ${KEYCLOAK_FILE} down keycloak-db
# All
# Команда для полной очистки docker
.PHONY: prune
prune:
	${PRUNE} -a

