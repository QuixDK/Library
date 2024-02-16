Деплоймент:  
1. Склонировать репозиторий  
2. В pgAdmin создать базу данных для проекта (Databases -> create -> database -> enter db name -> save)  
3. Перейти в пакет src/main/resources, в файле application.properties заполнить такие данные как логин(Owner), пароль от базы + url где нужно поменять только название и порт, если у вас уже занят порт 5432  
4. Вернуться в главный пакет репозитория ( library ), используя команду mvn package, собрать проект  
5. Дождавшись билда проекта, написать cd target в терминале  
6. Далее, для запуска проекта использовать команду java -jar library-1.jar  


RequestBodies and paths for tests in postman 
Create user: POST localhost:8080/api/v1/clients/  
```
{
    "fullName":"Your Full Name",
    "birthday":"yyyy-MM-dd"
}
```
AddBook: POST localhost:8080/api/v1/books/  
```
{
    "name":"Harry Potter and the Philosopher's Stone 2",
    "author":"J. K. Rowling",
    "isbn":"13: 9780545069680"
}
```
ClientRentABook: POST localhost:8080/api/v1/clients/books/rent  
```
{
    "userId":"1",
    "isbn":"13: 9780545069680"
}
```
GetAllClients: GET localhost:8080/api/v1/clients/  
GetClientsWithRentedBooks: GET localhost:8080/api/v1/clients/rented-books  
GetAllBooks:GET localhost:8080/api/v1/books/  

UpdateBook: PUT localhost:8080/api/v1/books/  
```
{
  "id" : 1,
  "name" : "Harry Potter and the Philosopher's Stone",
  "author" : "J. K. Rowling",
  "isbn" : "13: 9780545069680"
}
```
UpdateClient: PUT localhost:8080/api/v1/clients/  
```
{
  "id" : 1,
  "fullName" : "Harry Potter Philosopher",
  "birthday" : "2003-10-13"
}
```
