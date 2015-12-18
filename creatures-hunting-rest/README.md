# PA165 Creatures Hunting - REST module guide

## User entity##

###Get users###

**API URL** (GET method)

```
/pa165/rest/users
```

**Example**

```
curl -i -X GET http://localhost:8080/pa165/rest/users
```

###Get user by id###

**API URL** (GET method)

```
/pa165/rest/users/{id}
```

**Example**

```
curl -i -X GET http://localhost:8080/pa165/rest/users/1
```

## Creature entity##

###Get creatures###

**API URL** (GET method)

```
/pa165/rest/creatures
```

**Example**

```
curl -i -X GET http://localhost:8080/pa165/rest/creatures
```

###Create creature###

**API URL** (POST method)

```
/pa165/rest/creatures/create
```

**Example**

```
curl -i -X POST -H "Content-Type: application/json" --data '{"name":"Familiar"}' http://localhost:8080/pa165/rest/creatures/create
```

### Get creature by id###

**API URL** (GET method)

```
/pa165/rest/creatures/{id}
```

**Example**

```
curl -i -X GET http://localhost:8080/pa165/rest/creatures/1
```

###Update creature###

**API URL** (PUT method)

```
/pa165/rest/creatures/{id}
```

**Example**

```
curl -i -X PUT -H "Content-Type: application/json" --data '{"name":"new_name"}' http://localhost:8080/pa165/rest/creatures/1
```

###Get creatures in area###

**API URL** (GET method)

```
/pa165/rest/areas/{name}/creatures
```

**Example**

```
curl -i -X GET http://localhost:8080/pa165/rest/area/Riverdown+Valley/creatures
```