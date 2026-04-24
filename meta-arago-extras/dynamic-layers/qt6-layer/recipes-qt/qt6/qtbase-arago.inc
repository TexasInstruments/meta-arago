FILESEXTRAPATHS:prepend := "${THISDIR}/qtbase:"

SRC_URI:append = " \
    file://0001-qwaylandinputdevice.cpp-remove-serial-0-handling.patch \
"
PR:append = ".arago0"
