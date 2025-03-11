SUMMARY = "Common utilities/functionalities for sysrepo plugins"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f91d5dfaae99cc1943a8eca222cafa5c"

SRC_URI = "gitsm://github.com/telekom/sysrepo-plugins-common.git;protocol=https;branch=devel"
SRC_URI += "file://0001-so-version.patch"

PV = "0.1+git"
SRCREV = "20885de0d3bb95a05610fdb3a0f83d8f7c370fad"

S = "${WORKDIR}/git"

DEPENDS = "libyang sysrepo"

inherit cmake pkgconfig

EXTRA_OECMAKE = "-DCMAKE_INSTALL_PREFIX:PATH=${prefix}"
