SUMMARY = "Extra modules and scripts for CMake"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING-CMAKE-SCRIPTS;md5=54c7042be62e169199200bc6477f04d1"

PV = "1.5.0+git"
SRCREV = "8dc36cfa882f07a60b3ebdf16d9aac57871bd382"

SRC_URI = " \
    git://anongit.kde.org/extra-cmake-modules;protocol=git;branch=master \
"

EXTRA_OECMAKE += "-DBUILD_TESTING=off"

inherit cmake

FILES:${PN}-dev += "${datadir}/ECM"
