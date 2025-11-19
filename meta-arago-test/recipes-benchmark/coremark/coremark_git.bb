SUMMARY = "Industry-standard benchmark that measures CPU performance"

LICENSE = "Coremark"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0a18b17ae63deaa8a595035f668aebe1"

SRC_URI = "git://github.com/eembc/coremark.git;branch=main;protocol=https"
PV = "1.01+git"
SRCREV = "1f483d5b8316753a742cbf5590caf5bd0a4e4777"

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake PORT_DIR=linux CC="${CC}" XCFLAGS="${CFLAGS}" LFLAGS_END="${LDFLAGS}" compile
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/coremark.exe ${D}${bindir}/coremark
}
