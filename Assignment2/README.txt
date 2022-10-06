Assignment 2:

Instructions
[8 marks]

 Utilizing Java’s TCP socket class develop your own custom Java web server that replicates the file upload functionality of Assignment I.  In other words, upon user specifying the URL of your web server on the browser, a file upload page, similar to the one in your Assignment I, should appear.  The user then shall be able to select an image file from a local folder and upload it to the server along with additional form data such as the date and caption of the Image.  [Hint:  use wireshark to confirm the composition and format of the request].   The server should be able to support uploads from multiple users simultaneously thus implying that it should be a multithreaded. 

 

The design of the server should be based on Object Oriented paradigm.  In other words, define your own interface or abstract class with two methods doGet and doPost.  Feel free to name this interface or abstract class as “Servlet”.  The doGet and doPost methods should have a request and a response object as parameters. Thus, define your own HttpRequest as well as HttpResponse classes to contain and parse the incoming and outgoing HTTP messages over TCP.  Create a FileUploadServlet that inherits from your Servlet class and implements its doGet and/or doPost methods appropriately to render the desired content to the browser.  Additionally, your server side would likely have the main Server class and the ServerThread class. 

 

Use Java Reflection to demonstrate that the FileUploadServlet could be compiled independently by the programmer and thereafter loaded and invoked dynamically by your server at run-time. 

 

Server should print appropriate debug (log) messages on the console at runtime to clearly present the order in which classes and methods are being called on the server side.  Any caught exception should be logged into a file.  Use AspectJ for this part.

 

After the request to upload has been handled, the server shall return an alphabetically sorted listing of the images folder as a response.  Refer to DirServlet for code for this functionality.  Use Stream API for sorting.

 

The server shall be able to distinguish between a request coming from a browser vs a desktop/native app.  If the request comes from the desktop/native app the server shall return the listing as a JSON string as opposed to an HTML page.  You are welcomed to use 3rd party libraries to handle creation and parsing of JSON strings.

 

Define and use at least one custom exception in your design.

 

Native Client App:

 

Develop a Java/TCP console application that uploads an image as multipart data along with other form data e.g. date and keyword via the upload Servlet you created in Assignment I.  Connect this console app to the above custom server as well, parse the JSON string in the incoming response from the server and print out the received listing on the screen. 

Submission Instructions: 

Please submit the code (preferably a link to the team’s github repo) and a readme.txt file containing instructions for compiling and running the code.  Also identify how the work was split among team members including testing done.  It is preferred that there is one owner per class.  Record and upload the demo of your solution.  A live online demo is also expected from each team sometimes around the due date.  


Reference Code:

 

Please find attached the code that should get you started on Assignment II.  Download and unzip the folder.  You will find two sub folders – UploadServer and ConsolApp.  Go to the UploadServer folder and run the UploadServer class using “java -classpath . UploadServer”.  Open another command window and go to CosnoleApp folder and run the Activity.class using “java -classpath . Activity”.  Each time you run the Activity class, it uploads a harcoded image file to the upload server which saves it in a local folder with a new timestamp as its filename.  Please have this running before your class on Monday.

This reference code does not support web protocols.  Your assignment is basically to enhance this reference code such that the ConsoleApp is able to talk to the UploadServlet used for Assignment I(b) (also attached below) in addition to the UploadServer of this assignment.   Enhance the above UploadServer so when contacted via a browser, it returns the HTML page that allows the user to specify the caption, date  and time, and an image using file uploader.  The server should parse the incoming multipart content in the HTTP Request and save the image file in a local directory.  Additionally, you will be incorporating Component based architecture, singleton design pattern, aspect oriented programming paradigm and functional programming paradigm in your solution.  