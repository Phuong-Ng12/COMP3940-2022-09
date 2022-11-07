#include "Socket.h"
#include <sys/socket.h>
#include <sys/types.h>
#include <resolv.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <cstdlib>
#include "HttpServletResponse.h"
#include "HttpServletRequest.h"

#define SIZE 1

void send_file(FILE *fp, int sock) {
    int n;
    char data[SIZE] = {0};
    while (fgets(data, SIZE, fp) != NULL) {
        if (send(sock, data, sizeof(data), 0) == -1) {
            perror("Error in sending file");
            exit(1);
        }
        bzero(data, SIZE);
    }
}

void write_file(int sock) {
    int n;
    FILE *fp;
    char *fileName = "recv.txt";
    char buffer[SIZE];

    fp = fopen(fileName, "w");
    while (1) {
        n = recv(sock, buffer, SIZE, 0);
        if (n <= 0) {
            break;
        }
        fprintf(fp, "%s", buffer);
        bzero(buffer, SIZE);
    }
}

Socket::Socket(int sock) {
    this->sock = sock;
}

char *Socket::getRequest() {
    int rval;
    char *buf = new char[1024];

    if ((rval = read(sock, buf, 1024)) < 0) {
        perror("reading socket");
    } else {
        char arr[1024] = "HTTP/1.1 200 OK\nContent-Type:text/html\nContent-Length: 1024\n\n"
                         "<body>\n<h1>Upload your photo</h1>\n"
                         "<form action=\"/\" method=\"POST\" enctype=\"multipart/form-data\">\n"
                         "    <label for=\"caption\">Caption</label><br>\n"
                         "    <input type=\"text\" id=\"caption\" name=\"caption\"><br><br>\n"
                         "    <label for=\"date\">Date</label><br>\n"
                         "    <input type=\"date\" id=\"date\" name=\"date\"><br><br>\n"
                         "    <label for=\"filename\">Filename</label><br>\n"
                         "    <input type=\"file\" id=\"filename\" name=\"fileName\"><br><br>\n"
                         "    <input type=\"submit\" value=\"Submit\">\n"
                         "</form>\n</body>";
        int send_req = send(sock, arr, sizeof(arr), 0);
        printf("\nhereingetRequest: %s\n", buf);
    }

    return buf;
}

void Socket::sendResponse(char *res) {
    int rval;

    if ((rval = write(sock, res, strlen(res))) < 0) {
        perror("writing socket");
    } else {
        printf("\nres: %s\n", res);
        write_file(sock);
    }
}

Socket::~Socket() {
}
