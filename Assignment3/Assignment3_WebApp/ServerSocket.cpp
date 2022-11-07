#include "ServerSocket.h"
#include <sys/socket.h>
#include <sys/types.h>
#include <resolv.h>
#include <unistd.h>
#include <stdio.h>
#include <cstdlib>


ServerSocket::ServerSocket(int port)
{
  sock = socket (AF_INET, SOCK_STREAM, 0);
  if (sock < 0) {
    perror("opening stream socket");
  }
  struct sockaddr_in server;
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
  server.sin_port = htons(8888);
  
  if (bind (sock, (struct sockaddr *)&server, sizeof server) < 0) {
    perror ("Error in binding stream socket");
    exit(1);
  }
  printf("\nBinding successfully.\n");
  int e = listen (sock, 5);

  if (e == 0){
      printf("Listening...\n");
  } else {
      perror("Error in listening");
      exit(1);
  }

}

Socket* ServerSocket::Accept()
{
	sockaddr_in localAddr, remoteAddr;
	socklen_t addrLen = sizeof (remoteAddr);
    int cSock;
    cSock = accept(sock, (struct sockaddr *)&remoteAddr, &addrLen);
	Socket *cs = new Socket(cSock);
	return cs;
}
ServerSocket::~ServerSocket()
{
}
