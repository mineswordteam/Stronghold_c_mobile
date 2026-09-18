cmake_minimum_required(VERSION 3.22.1)

project("stronghold_runtime")

add_library(
    stronghold_runtime
    SHARED
    native-lib.cpp
)

find_library(
    log-lib
    log
)

find_library(
    android-lib
    android
)

find_library(
    gles2-lib
    GLESv2
)

target_link_libraries(
    stronghold_runtime
    ${log-lib}
    ${android-lib}
    ${gles2-lib}
)
