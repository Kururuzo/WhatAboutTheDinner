# WhatAboutTheDinner?
(REST service to vote for restaurants)

<h2>The task is:</h2>

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
    + If it is before 11:00 we asume that he changed his mind.
    + If it is after 11:00 then it is too late, vote can't be changed
    
Each restaurant provides new menu each day.

## Run
```
$ mvn package
$ mvn cargo:run
```

## Users
After start 2 users available

| Username             | Password       | Role           |
|----------------------|----------------|----------------|
| user@yandex.ru       | password       | USER           |                   
| admin@gmail.com      | admin          | ADMIN          |


After this commands you will start hosting website on page {Base URL} `localhost:8080/rest`

# API documentation

### Admin Profile 

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | get all profiles     | {Base URL}/admin/users                              | Admin          |
| GET    | get profile          | {Base URL}/admin/users/{userId}                     | Admin          |
| GET    | get profile by email | {Base URL}/admin/users/by?email={userEmail}         | Admin          |
| POST   | Create Profile       | {Base URL}/admin/users                              | Admin          |
| PUT    | Update Profile       | {Base URL}/admin/users/{userId}                     | Admin          |
| DELETE | Delete Profile       | {Base URL}/admin/users/{userId}                     | Admin          |


### User Profile 

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get Profile          | {Base URL}/profile                                  | Authorized     |
| PUT    | Update Profile       | {Base URL}/profile                                  | Authorized     |
| DELETE | Delete Profile       | {Base URL}/profile                                  | Authorized     |
| POST   | Register Profile     | {Base URL}/profile/register                         | Not Authorized |


### Dishes

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get                  | {Base URL}/dishes/{dishId}                          | Authorized     |
| GET    | Get All              | {Base URL}/dishes                                   | Authorized     |
| POST   | Create               | {Base URL}/dishes                                   | Admin Only     |
| PUT    | Update               | {Base URL}/dishes/{dishId}                          | Admin Only     |
| DELETE | Delete               | {Base URL}/dishes/{dishId}                          | Admin Only     |


### Restaurants

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get                  | {Base URL}/restaurants/{restaurantId}               | Authorized     |
| GET    | Get All              | {Base URL}/restaurants                              | Authorized     |
| POST   | Create               | {Base URL}/restaurants                              | Admin Only     |
| PUT    | Update               | {Base URL}/restaurants/{restaurantId}               | Admin Only     |
| DELETE | Delete               | {Base URL}/restaurants/{restaurantId}               | Admin Only     |


### Menus

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Offer by date        | {Base URL}/menus/offer?date={date}                  | Anyone         |
| GET    | Get                  | {Base URL}/menus/{menuId}                           | Authorized     |
| GET    | Get All              | {Base URL}/menus                                    | Authorized     |
| GET    | Get by date          | {Base URL}/menus?date={date}                        | Authorized     |
| POST   | Create               | {Base URL}/menus                                    | Admin Only     |
| PUT    | Update               | {Base URL}/menus/{menuId}                           | Admin Only     |
| DELETE | Delete               | {Base URL}/menus/{menuId}                           | Admin Only     |


### Admin Votes

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get                  | {Base URL}/votes/admin/{voteId}                     | Admin Only     |
| GET    | Get All by User      | {Base URL}/votes/admin/byUser/{userId}              | Admin Only     |
| POST   | Create               | {Base URL}/votes/admin                              | Admin Only     |
| PUT    | Update               | {Base URL}/votes/admin/{voteId}                     | Admin Only     |
| DELETE | Delete               | {Base URL}/votes/admin/{voteId}                     | Admin Only     |

### User Votes

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get                  | {Base URL}/votes/{voteId}                           | Authorized     |
| GET    | Get by date          | {Base URL}/votes/?date={date}                       | Authorized     |
| GET    | Get All              | {Base URL}/votes                                    | Authorized     |
| POST   | Create or Update     | {Base URL}/votes                                    | Authorized     |
| DELETE | Delete               | {Base URL}/votes/{voteId}                           | Authorized     |

---
All samples in curls.md
---