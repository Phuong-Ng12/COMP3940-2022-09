#include <sys/socket.h>
#include <sys/types.h>
#include <resolv.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


main() {
  int sock;
  struct sockaddr_in server;
  int msgsock;
  char buf1[1024];
  struct hostent *hp;
  char *host = "127.0.0.1";
  int rval;

  sock = socket (AF_INET, SOCK_STREAM, 0);
  if (sock < 0) {
    perror("opening stream socket");
  }

  bzero(&server, sizeof(server));
  server.sin_family = AF_INET;
  server.sin_port = htons(8888);
  inet_aton("192.168.1.69", &server.sin_addr);
  
  if (connect(sock, (struct sockaddr*)&server, sizeof(server))<0){
    perror("connecting");
  }
  printf("\nConnected to server.\n");

//  strcpy(buf1,"~/Downloads");

//  if ((rval = write(sock, buf1, 1024)) < 0){
//    perror("writing socket");
//  }
//
//  char buf[1];
//  int i = 0;
//  while ((rval = write(sock, buf, 1)) == 1){
//      buf1[i] = buf;
//      i++;
//      putchar(buf);
//  }
//  buf[i]='\0';

  printf("\nClosing the connection\n");
  close (sock);
}
