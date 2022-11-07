//
// Created by miniature_pug on 11/6/22.
//

#include "HttpServletResponse.h"

HttpServletResponse::HttpServletResponse(char input[1024]) {
    this->outputStream = &input;
}
char** HttpServletResponse::getOutputStream() {
    return this->outputStream;
}
