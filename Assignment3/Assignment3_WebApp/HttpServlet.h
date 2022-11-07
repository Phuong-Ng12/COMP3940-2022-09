//
// Created by miniature_pug on 11/6/22.
//
#ifndef C_WRAPPERS_HTTPSERVLET_H
#define C_WRAPPERS_HTTPSERVLET_H

#include "HttpServletRequest.h"
#include "HttpServletResponse.h"

class HttpServlet {
public:
    virtual void doGet(HttpServletRequest request, HttpServletResponse response) = 0;
    virtual void doPost(HttpServletRequest request, HttpServletResponse response) = 0;
    virtual ~HttpServlet() = default;
};
#endif //C_WRAPPERS_HTTPSERVLET_H
