# Start the project
1. Clone the repository to your local machine
2. Open whole project and create a new data source (Db tables will be created automatically after start)
   - Url: jdbc:postgresql://localhost:5432/postgres
   - Username: disysuser
   - Password: disyspw
4. Go to "Services" on the bottom left
5. Right-click on "Spring Boot" > "Run"
6. Afterwards open project `community-gui`seperatly and start Java class `GuiApplication`
8. Enjoy :)

# How to use the application
- ```Refresh```-button: Returns usage data of current time
- ```Show Data```-button: Returns usage data of selected time span
- ```Start Date``` should be greater or equal the current date; ```End Date``` should be greater or equal to Start Date

# Screenshots
![Screenshot1](https://github.com/user-attachments/assets/eb6d5963-e5e0-4ffb-99f9-e506ef198400)
![Screenshot2](https://github.com/user-attachments/assets/b33c942e-92ec-4989-965a-e4858884d212)
