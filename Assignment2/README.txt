[Assignment is 90% complete]

Missing components:
a)Reflection (AOP) part; creating a httpServlet is still "HttpServlet = new UploadSerlvet" instead of using Class<?> theClass; method.

b)When the POST request happens from the browser, the data sent cannot be stored. We have spend 1-2 days trying to solve this so we hope its not a big issue since
we are sending the data, just not reading it.

Download:
UploadServer - the server that will be used to manage file upload and users.
ConsoleApp - Java Console Application that will work with the UploadServer to post a file in the console.

Instructions:

1. Compile downloaded java files in both UploadServer and ConsoleApp with:
javac -classpath *.java while in the directory

	
2.	ConsoleApp:
	
	1A) Start up the compiled UploadServer.java file in Upload Server by using command 
		java -cp .;json-simple-1.1.jar UploadServer

	2A) Once the server is running, start the compiled Activity.java file by using command
		java -cp .;json-simple-1.1.jar Activity
		and type in the approriate values for the required fields.


3.	UploadServer

	1B) Start up the compiled UploadServer.java file in Upload Server.

	2B) Now that the server is running, go to the link: http://localhost:8999/upload

	3B) You can upload your file by choosing a file of your liking and filling in the caption and date.
		(this will only call the post method and send the file in the form of multipart data that is not parsed)


*Notes: Please note that UploadClient0.java is for GET and UploadClient.java is for POST
	
	When using GET change Activity Line 14 to:
	System.out.println(new UploadClient0().uploadFile());
	
	When using POST change Activity Line 14 to:
	System.out.println(new UploadClient().uploadFile());

