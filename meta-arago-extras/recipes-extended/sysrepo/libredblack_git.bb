SUMMARY = "Library for handling red-black tree searching algorithm"
DESCRIPTION = "A library to provide the RedBlack balanced tree searching and sorting algorithm."
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=de174fb5a92cdbe038c88dc4c4316f99"

SRC_URI = "git://github.com/sysrepo/libredblack.git;protocol=https;branch=master"

PV = "1.0+git"
SRCREV = "a399310d99b61eec4d3c0677573ab5dddcf9395d"

inherit python3native autotools

EXTRA_OECONF = "--without-rbgen"

BBCLASSEXTEND = "native nativesdk"
