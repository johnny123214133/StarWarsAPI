# API Request Usage

## Characters

### Post

#### create a new character

Endpoint: localhost:8080/characters

Request body:
```
{
    "name" : "a name",
    "homePlanetId" : id,
    "starships" : [
        id1,
        id2,
        ...
    ]
}
```

### Get

#### get character by id

Endpoint: localhost:8080/characters?id={id}

### get character by name

Endpoint: localhost:8080/characters/find?name={name}

### Put

#### update character by id

Endpoint: localhost:8080/characters?id={id}

Request body:
```
{
    "name" : "a name",
    "homePlanetId" : id,
    "starships" : [
        id1,
        id2,
        ...
    ]
}
```

#### update character by name

Endpoint: localhost:8080/characters

Request body:
```
{
    "name" : "a name",
    "homePlanetId" : id,
    "starships" : [
        id1,
        id2,
        ...
    ]
}
```

### Delete

#### delete character by id

Endpoint: localhost:8080/characters/{id}

#### delete character by name

Endpoint: localhost:8080/characters/delete?name={name}

## Planets

### Post

#### create a new planet

Endpoint: localhost:8080/planets

Example Request body:

```
{
    "name" : "a name",
    "climate" : "climate",
    "population" : 10000
}
```

### Get

#### get planet by id

Endpoint: localhost:8080/planets?id={id}

### get planet by name

Endpoint: localhost:8080/planets/find?name={name}

### Put

#### update planet by id

Endpoint: localhost:8080/planets?id={id}

Example Request body:

```
{
    "name" : "a name",
    "climate" : "new climate",
    "population" : 300
}
```

#### update planet by name

Endpoint: localhost:8080/planets

Example Request body:

```
{
    "name" : "a name",
    "climate" : "new climate",
    "population" : 300
}
```

### Delete

#### delete planet by id

Endpoint: localhost:8080/planets/{id}

#### delete planet by name

Endpoint: localhost:8080/planets/delete?name={name}

## Starships

### Post

#### create a new starship

Endpoint: localhost:8080/starships

Example Request body:

```
{
    "name" : "a name",
    "model" : "a model",
    "costInCredits" : 10000.00
}
```

### Get

#### get starship by id

Endpoint: localhost:8080/starships?id={id}

### get starship by name

Endpoint: localhost:8080/starships/find?name={name}

### Put

#### update starship by id

Endpoint: localhost:8080/starships?id={id}

Example Request body:

```
{
    "name" : "a name",
    "model" : "a model",
    "costInCredits" : 500.00
}
```

#### update starship by name

Endpoint: localhost:8080/starships

Example Request body:

```
{
    "name" : "a name",
    "model" : "a model",
    "costInCredits" : 500.00
}
```

### Delete

#### delete starship by id

Endpoint: localhost:8080/starships/{id}

#### delete starship by name

Endpoint: localhost:8080/starships/delete?name={name}