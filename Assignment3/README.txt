For UploadFile WebApp:
1. Navigate to the UploadFile WebApp folder 
2. Open terminal on the folder directory
3. Type the command below to run the server:
g++ -o server main.cpp ServerSocket.cpp Socket.cpp
./server
4. Go to browser and type in the address bar to access the webapp
localhost:8888/
5. Fill in the form and choose a text file or an image to upload.
6. After hit Submit button, the browser will return "Unable to connect" 
(this is the bug we haven't been able to fix yet). You will have to return
to the terminal and run this command again to open the server:
./server
7.  When the server is running again, the file will be written to the server of
webapp under "recv.txt" file.
	- If you upload a text file, you'll see the content of the orginal text file in the recv.txt file
	- If you upload an image, you'll see a recv.txt file with the bytes of the image. (Sorry we couldn't fix the 
losing bytes over transmission therefore the image couldn't be fully written in .png or .jpg file)

For Console App:
1. Navigate to the UploadFile WebApp folder 
2. Open terminal on the folder directory
3. Type the command below to run the server:
g++ -o server main.cpp ServerSocket.cpp Socket.cpp
./server
4. Type the command below to run the client:
g++ -o client socketClient.cpp
./client
5. To upload a file Type the directory of the file
6. Type the caption for the file
7. Type the date of the file
8. Photo will be added to the "Images" Folder.