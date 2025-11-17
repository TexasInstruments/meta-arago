SUMMARY = "Udev rules for assorted TI SoCs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit bin_package

SRC_URI = " \
    file://usb1-rules.sh \
    file://usb2-rules.sh \
    file://50-arago.rules \
    file://37-can-j7.rules \
    file://37-can-am62.rules \
    file://37-can-dra7.rules \
    file://37-can-ti33x.rules \
"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/udev/scripts
    install -m 0755 ${UNPACKDIR}/usb1-rules.sh ${D}${sysconfdir}/udev/scripts/usb1-rules.sh
    install -m 0755 ${UNPACKDIR}/usb2-rules.sh ${D}${sysconfdir}/udev/scripts/usb2-rules.sh

    install -d ${D}${libdir}/udev/rules.d
    install -m 0644 ${UNPACKDIR}/50-arago.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-j7.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-am62.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-dra7.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-ti33x.rules ${D}${libdir}/udev/rules.d/
}

RDEPENDS:${PN} = "udev udev-extraconf"
