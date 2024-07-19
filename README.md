# **Description**

The aim of this project is to create a Web-App that parses a JSON file and enables the user to fethc the path of the JSON element by clicking on it.

The app also returns the Object Type of each of the parent Object that constitutes the selected element.

# **Working** **:** 
1. The front-end of the application is created using React. When a .json file is uploaded it gets parsed by the json renderer and upon submitting the file, it is send to the backend Sprin-boot application.
2. Once an element has been clicked, the object path is displayed to the user. This path is sent to the Spring-boot App that finds each object in the JSON file while returning its object type and name and also simultaneoulsy adding it to the path.
3. This path is stored in the response, which is read by axiom in the front-end and the Application shows it to the user.

# **A Screenshot of the Application:**

![image](https://github.com/user-attachments/assets/f1a63513-bb86-4cca-92c1-628b141906e8)
