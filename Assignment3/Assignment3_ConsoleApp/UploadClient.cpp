//
// Created by Dalibor Cavlovic on 2022-11-06.
//

#pragma comment(lib, "rpcrt4.lib")

#include "UploadClient.h"
#include "Socket.h"

#include <sys/socket.h>
#include <sys/types.h>
#include <resolv.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <stdlib.h>
#include <stdio.h>
#include <uuid/uuid.h>
#include <vector>
#include <filesystem>
#include <ostream>
#include <iostream>
#include <fstream>

namespace fs = std::__fs::filesystem;

UploadClient::UploadClient() {

    uuid_t binuuid;
    uuid_generate_random(binuuid);
    char *uuid = new char[37];
    uuid_unparse(binuuid, uuid);
    std::string uuidString;
    uuidString.assign(uuid);

    std::string headerBoundary = "-----WebKitFormBondary" + uuidString + "\r\n";

    std::string bodyBoundary = "-----WebKitFormBondary" + uuidString + "\r\n";

}
//
//void UploadClient::writeInFile(std::vector<std::string> vectorList) {
//    std::string describe = vectorList.at(0) + "&" + vectorList.at(1) + "@" + vectorList.at(2) + "*";
//    fs::path p = fs::current_path();
//}

std::string UploadClient::uploadFile() {
    int sock;
    struct sockaddr_in server;
    int msgsock;
    char buf[1024];
    struct hostent *hp;
    char *host = "127.0.0.1";
    int rval;
    bool end = false;
    std::string PATH, CAPTION, DATE;
//    while (!end) {
        sock = socket (AF_INET, SOCK_STREAM, 0);
        if (sock < 0) {
            perror("opening stream socket");
        }
        bzero(&server, sizeof(server));
        hp = gethostbyname("localhost");
        bcopy((char*)hp->h_addr, (char*)&server.sin_addr, hp->h_length);
        server.sin_family = AF_INET;
        server.sin_port = 8888;

        if (connect(sock, (struct sockaddr*)&server, sizeof(server))<0){
            perror("connecting");
        }

        std::cout << "Upload Image Directory: " << std::endl;
        std::cin >> PATH;
        std::cin.clear();
        std::cout << "Caption: " << std::endl;
        std::cin >> CAPTION;
        std::cin.clear();
        std::cout << "Date of the Image: " << std::endl;
        std::cin >> DATE;
        std::cin.clear();

        std::fstream source(PATH);
        std::string imageDirectory = "./Images";
        std::ofstream dest(imageDirectory);
        std::ifstream::pos_type size = source.tellg();
        source.seekg(0);
        char *buffer = new char[size];

        source.read(buffer, size);
        dest.write(buffer, size);

        delete[] buffer;
        source.close();
        dest.close();

//    }

}