# Demo using Docker Secrets


## Create the secrets

Create the secrets bellow:

```
SPRING_DATASOURCE_PASSWORD
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
```

## Create the service

To create the service you should use the 3 secrets created before and use the following environment variables:

```
SPRING_DATASOURCE_USERNAME_FILE=/run/secrets/SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD_FILE=/run/secrets/SPRING_DATASOURCE_PASSWORD
SPRING_DATASOURCE_URL_FILE=/run/secrets/SPRING_DATASOURCE_URL
```
