# gate-api

API de validação e triagem de embarque do projeto **GATE ClickBus** (FIAP).

## Como rodar

```bash
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Console do banco H2: `http://localhost:8080/h2-console`
  (JDBC URL: `jdbc:h2:file:./data/gateclickbus`, usuário `sa`, senha em branco)

## Estrutura de pacotes

```
com.gateclickbus.api
├── GateApiApplication.java   ← Main
├── model/                    ← entidades JPA
│   └── enums/
├── repository/                ← interfaces JPA
├── service/                   ← regra de negócio
├── controller/                 ← endpoints REST
├── dto/
│   ├── request/
│   └── response/
└── config/                    ← CORS, segurança, beans
```

## Status

- [x] Sprint 1 (parte 1) — esqueleto do projeto (`Main`, `pom.xml`, banco H2 configurado)
- [ ] Sprint 1 (parte 2) — `model/` + `repository/` + massa de dados de teste
- [ ] Sprint 2 — `service/` (triagem e remarcação) + `controller/` + `dto/` + Swagger
- [ ] Sprint 3 — testes unitários + integração com o app Android
