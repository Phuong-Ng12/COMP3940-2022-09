cmake_minimum_required(VERSION 3.23)
project(c__wrappers)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(c__wrappers
        HttpServlet.h
        HttpServletRequest.h
        HttpServletResponse.h
        main.cpp
        ServerSocket.cpp
        ServerSocket.h
        SimpleThread.cpp
        SimpleThread.h
        Socket.cpp
        Socket.h
        socketclient.c
        Thread.cpp
        Thread.h
        TwoThreadsTest.cpp
        UploadServlet.h)
