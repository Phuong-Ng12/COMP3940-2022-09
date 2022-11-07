//
// Created by Dalibor Cavlovic on 2022-11-06.
//

#ifndef C__WRAPPERS_UPLOADCLIENT_H
#define C__WRAPPERS_UPLOADCLIENT_H

#include <string>

class UploadClient {
public:
    UploadClient();
//    void writeInFile(std::vector<std::string> vectorList);
//    bool isPathValid(std::string path);
    std::string uploadFile();
//    std::string getRequestHeader(std::string header);
//    std::string getRequestBody(std::string caption, std::string date);
};

#endif //C__WRAPPERS_UPLOADCLIENT_H
