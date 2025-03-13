SUMMARY = "libnetconf2 is a NETCONF library in C intended for building NETCONF clients and servers"
DESCRIPTION = "The library provides functions to connect NETCONF client and server to each other via SSH and to send, receive and process NETCONF messages."
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=08a5578c9bab06fb2ae84284630b973f"

SRC_URI = "git://github.com/CESNET/libnetconf2.git;protocol=https;branch=devel"

PV = "3.5.5+git"
SRCREV = "4c7858c9c41f1ac04d2b15c032291e335fc0f5f5"

S = "${WORKDIR}/git"

DEPENDS = "libssh openssl libyang libxcrypt mbedtls curl \
    ${@bb.utils.contains('DISTRO_FEATURES','pam','libpam','',d)} \
"

FILES:${PN} += "${datadir}/yang/modules/libnetconf2/*"

inherit cmake pkgconfig

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
#EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_BUILD_TYPE:String=Release -DLIBYANG_INCLUDE_DIR=/usr/include -DLIBYANG_LIBRARY=/usr/lib "
EXTRA_OECMAKE = " -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_BUILD_TYPE:String=Release "

BBCLASSEXTEND = "native nativesdk"
