//
// Created by miniature_pug on 11/6/22.
//

#ifndef C_WRAPPERS_HTTPSERVLETRESPONSE_H
#define C_WRAPPERS_HTTPSERVLETRESPONSE_H
class HttpServletResponse {
private:
    char **outputStream;
public:
    HttpServletResponse(char input[1024]);
    char** getOutputStream();
};
#endif //C_WRAPPERS_HTTPSERVLETRESPONSE_H
