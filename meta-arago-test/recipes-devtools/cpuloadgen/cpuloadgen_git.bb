SUMMARY = "Utility to generate specified CPU Load"
HOMEPAGE = "https://github.com/ptitiano/cpuloadgen"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6c2eb46c569d0cd7884089fea6b4f31"

PV = "0.94"
PR = "r1"

BRANCH ?= "master"
SRCREV ?= "dd2052b581ae7c2e0e06344887885fd6cd66eebb"

SRC_URI = "git://github.com/ptitiano/cpuloadgen.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "DESTDIR=${D}${bindir} CROSS_COMPILE=${TARGET_PREFIX} CC="${CC}" CFLAGS="${CFLAGS}""
CFLAGS += "-fpermissive -std=gnu11"

do_install () {
    install -d ${D}${bindir}
    oe_runmake install
}
