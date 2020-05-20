### curl samples (application deployed in application context `What About The Dinner?`).
> For windows use `Git Bash`

######## ======= Admin Profile =======

#### get User 100001 (for Admin only)
`curl -s http://localhost:8080/rest/admin/users/1000 --user admin@gmail.com:admin`

#### get All Users (for Admin only)
`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

#### get User by email (for Admin only)
`curl -s http://localhost:8080/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`

#### Create User (for Admin only)
`curl -s -i -X POST -d '{"name":"New","email":"new@gmail.com","enabled":false,"registered":"2020-05-14T09:42:05.815+00:00","roles":["USER"],"votes":[{"id":100025,"date":"2020-04-01","restaurant":{"id":100013,"name":"Lucky Pizza"},"user":{"id":100000,"name":"User","email":"user@yandex.ru","enabled":true,"registered":"2020-05-14T09:42:05.815+00:00","roles":["USER"]}}],"password":"newPass"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

#### Update User (for Admin only)
`curl -X PUT -H 'Content-Type:application/json;charset=UTF-8' -d '{"id":100000,"name":"UpdatedName","email":"user@yandex.ru","enabled":true,"registered":"2020-05-14T09:44:52.179+00:00","roles":["ADMIN"],"password":"newPass"}' http://localhost:8080/rest/admin/users/100000 --user admin@gmail.com:admin`

#### delete User (for Admin only)
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users/100027 --user admin@gmail.com:admin`

######## ======= User Profile =======

#### register User (for Unregistered)
`curl -s -i -X POST -d '{"name":"newName","email":"newemal@yandex.ru","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/profile/register`

#### get Profile for User
`curl -s http://localhost:8080/rest/profile --user newemal@yandex.ru:newPassword`

#### update Profile for User
`curl -X PUT -H 'Content-Type:application/json;charset=UTF-8' -d '{"name":"superNewName","email":"newemal@yandex.ru","password":"newPassword"}' http://localhost:8080/rest/profile --user newemal@yandex.ru:newPassword`

#### delete Profile for User
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/profile --user newemal@yandex.ru:newPassword`

######## ======= Dishes =======

#### get dish (for Authenticated)
`curl 'http://localhost:8080/rest/dishes/100002' --user 'user@yandex.ru:password'`

#### get all dishes (for Authenticated)
`curl 'http://localhost:8080/rest/dishes' --user 'user@yandex.ru:password'`

#### create dish (for Admin only)
`curl -s -i -X POST -d '{"name" : "Boiled egg", "price" : 2.0}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes --user admin@gmail.com:admin`

#### update dish (for Admin only)
`curl -X PUT -H 'Content-Type:application/json;charset=UTF-8' -d '{"name" : "Boiled big egg", "price" : 3.0}' http://localhost:8080/rest/dishes/100028 --user admin@gmail.com:admin`

#### delete dish (for Admin only)
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes/100028 --user admin@gmail.com:admin`

######## ======= Restaurants =======

#### get restaurant (for Authenticated)
`curl 'http://localhost:8080/rest/restaurants/100013' --user 'user@yandex.ru:password'`

#### get all restaurants (for Authenticated)
`curl 'http://localhost:8080/rest/restaurants' --user 'user@yandex.ru:password'`

#### create restaurant (for Admin only)
`curl -s -i -X POST -d '{"name":"NewRest"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/restaurants --user admin@gmail.com:admin`

#### update restaurant (for Admin only)
`curl -X PUT -H 'Content-Type:application/json;charset=UTF-8' -d '{"name" : "Updated"}' http://localhost:8080/rest/restaurants/100029 --user admin@gmail.com:admin`

#### delete restaurant (for Admin only)
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/restaurants/100029  --user admin@gmail.com:admin`


######## ======= Menus =======

#### get get offer by day(for Anyone)
`curl 'http://localhost:8080/rest/menus/offer?date=2020-04-01'`

#### get menu (for Authenticated)
`curl 'http://localhost:8080/rest/menus/100016' --user 'user@yandex.ru:password'`

#### get all menus (for Authenticated)
`curl 'http://localhost:8080/rest/menus' --user 'user@yandex.ru:password'`

#### get all menus by day (for Authenticated)
`curl 'http://localhost:8080/rest/menus?date=2020-04-01' --user 'user@yandex.ru:password'`

#### create menu (for Admin only)
`curl -s -i -X POST -d '{"date":"2020-05-14","restaurant":{"id":100013,"name":"Lucky Pizza"},"dish":{"id":100002,"name":"Meat Soup","price":50}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/menus --user admin@gmail.com:admin`

#### update menu (for Admin only)
`curl -X PUT -H 'Content-Type:application/json;charset=UTF-8' -d '{"date":"2020-05-24","restaurant":{"id":100014,"name":"Lucky Pizza"},"dish":{"id":100002,"name":"Meat Soup","price":50}}' http://localhost:8080/rest/menus/100030 --user admin@gmail.com:admin`

#### delete menu (for Admin only)
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/menus/100030  --user admin@gmail.com:admin`

######## ======= Votes =======

#### get vote (for Authenticated)
`curl 'http://localhost:8080/rest/votes/100025' --user user@yandex.ru:password`

#### get vote by date(for Authenticated)
`curl 'http://localhost:8080/rest/votes?date=2020-04-01' --user user@yandex.ru:password`

#### get all votes (for Authenticated)
`curl 'http://localhost:8080/rest/votes' --user user@yandex.ru:password`

#### do vote (for Authenticated, menu for this day must exitsts)
`curl -s -i -X POST -d '100013' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/votes --user user@yandex.ru:password`

#### update vote (for Authenticated, vote for this day must exitsts)
`curl -X PUT http://localhost:8080/rest/votes/100025 --user user@yandex.ru:password`


#### delete menu (for Authenticated)
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/votes/100013  --user user@yandex.ru:password`

#### get vote results by date (for Authenticated)
`curl 'http://localhost:8080/rest/votes/results?date=2020-04-01' --user user@yandex.ru:password`

#### get vote by id (for Admin only)
`curl 'http://localhost:8080/rest/admin/votes/100025' --user admin@gmail.com:admin`

#### get all votes by user id (for Admin only)
`curl 'http://localhost:8080/rest/admin/votes?userId=100000' --user admin@gmail.com:admin`

#### create vote (for Admin only)
`curl -s -i -X POST -d '{"date":"2020-04-02","restaurant":{"id":100014,"name":"Steak House"},"user":{"id":100001,"name":"Admin","email":"admin@gmail.com","enabled":true,"registered":"2020-05-14T07:01:05.392+00:00","roles":["USER","ADMIN"]}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes --user admin@gmail.com:admin`

#### update vote (for Admin only)
`curl -X PUT -H 'Content-Type:application/json;charset=UTF-8' -d '{"date":"2020-05-04","restaurant":{"id":100014,"name":"Steak House"},"user":{"id":100001,"name":"Admin","email":"admin@gmail.com","enabled":true,"registered":"2020-05-14T07:01:05.392+00:00","roles":["USER","ADMIN"]}}' http://localhost:8080/rest/admin/votes/100033 --user admin@gmail.com:admin`

#### delete menu (for Admin only)
`curl -X DELETE -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes/100030  --user admin@gmail.com:admin`
