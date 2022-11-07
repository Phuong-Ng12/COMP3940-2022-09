//
// Created by miniature_pug on 11/6/22.
//

#include "HttpServlet.h"

#ifndef C_WRAPPERS_UPLOADSERVLET_H
#define C_WRAPPERS_UPLOADSERVLET_H
class UploadServlet : public HttpServlet {
public:
    void doPost(HttpServletRequest request, HttpServletResponse response) override;
    ~UploadServlet() = default;
};
#endif //C_WRAPPERS_UPLOADSERVLET_H
