//
// Created by miniature_pug on 11/6/22.
//

#ifndef C_WRAPPERS_HTTPSERVLETREQUEST_H
#define C_WRAPPERS_HTTPSERVLETREQUEST_H
class HttpServletRequest {
private:
    char *inputStream = new char[1024];
public:
    HttpServletRequest(char* input) {
        this->inputStream = input;
    }
    char* getInputStream();
};
#endif //C_WRAPPERS_HTTPSERVLETREQUEST_H
