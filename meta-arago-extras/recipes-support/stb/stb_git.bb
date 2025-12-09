SUMMARY = "single-file public domain (or MIT licensed) libraries for C/C++"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.md;md5=b499fad62f2f8e336bbea84cc94d1e60"

PV = "2.30"
PE = "1"

BRANCH = "master"
SRCREV = "f1c79c02822848a9bed4315b12c8c8f3761e1296"

SRC_URI = " \
    git://github.com/nothings/stb.git;protocol=https;branch=${BRANCH} \
"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${includedir}
    for hdr in ${S}/*.h
    do
        install -m 0644 $hdr ${D}${includedir}
    done
}
