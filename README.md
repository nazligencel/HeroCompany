 # Hero Company Spring RestApi With Security

<i><b>Project</b></i></br>
In this project, it is a project where a company takes orders for customers' products. In the project, the admin and customer registration sections were created separately. It can log in to the system with a common login service. In this project, authorization service is provided by using jwt action. Security is top notch. The passwords entered by the users are transferred by being encrypted with the encryption algorithm. Admin and user privileges are different. For example, the user cannot add categories or products. When it adds, it gets an authorization error. The user can add products to his cart, list the products by category, and order the products. Password change for the user and admin has also been carried out. Admin has almost all privileges. However, it cannot change the user's password or user settings. The project was carried out under the umbrella of roles. By using Auditing Config, it is possible to view when the order, product or category was added or edited by whom. At the same time, all validation procedures were carried out. Errors are caught against potential errors using the Global Exception Handler. It is written on the principle of clean code. Postman and Swagger tools are used in this project documentation. It has been carried out in accordance with OpenApi standards.


## Languages, Technologies and Environments Used in this Project
|      Java      |      MySQL     |    IntelliJ    |  Spring Boot   | Spring RestApi | Spring Mail Framework  | Spring Security  |   
| :------------: | :------------: | :------------: | :------------: | :------------: | :--------------------: | :--------------: |
|  <img src ="https://cdn.iconscout.com/icon/free/png-256/java-60-1174953.png" width ="100px" height = "100px" style="float:left" > | <img src ="https://github.com/nazligencel/HeroCompany/blob/main/images/mysql.jpg" width ="65px" height = "65px" style="float:left " >  |<img src ="https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/IntelliJ_IDEA_Icon.svg/70px-IntelliJ_IDEA_Icon.svg.png" width ="90px" height = "90px" style="float:left " >| <img src ="https://github.com/nazligencel/HeroCompany/blob/main/images/spring.jpg" width ="90px" height = "90px" style="float:left" >|<img src ="https://github.com/nazligencel/HeroCompany/blob/main/images/restapi.jpg" width ="90px" height = "90px" style="float:left" >|<img src ="https://github.com/nazligencel/HeroCompany/blob/main/images/mail.jpg" width ="90px" height = "90px" style="float:left" >|<img src ="https://github.com/nazligencel/HeroCompany/blob/main/images/security.jpg" width ="90px" height = "90px" >|


## Project Overview 

|<img src="https://github.com/nazligencel/HeroCompany/blob/main/images/1.PNG" width="300" height = "300">|<img src="https://github.com/nazligencel/HeroCompany/blob/main/images/2.PNG" width="300" height = "300">|

|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/3.PNG" width="300">|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/4.PNG" width="300">|

|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/5.PNG" width="300">|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/6.PNG" width="300">|

|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/7.PNG" width="300">|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/8.PNG" width="300">|

|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/9.PNG" width="300">|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/10.PNG" width="300">|

|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/11.PNG" width="300">|<img src="https://github.com/nazligencel/SalesManagement/blob/main/images/12.PNG" width="300">|
