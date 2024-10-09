FROM postgres:14

ENV POSTGRES_PASSWORD=admin

ENV POSTGRES_DB=authorizer

COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
