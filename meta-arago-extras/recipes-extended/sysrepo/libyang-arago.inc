PV = "3.7.8"

LIC_FILES_CHKSUM = "file://LICENSE;md5=9bb3d334294e8719f41c531e28a9a697"

SRCREV = "03e294d83b610f89e8ba7b2156a80dc0ad534443"

do_install:append () {
    oe_multilib_header  libyang/ly_config.h
}
