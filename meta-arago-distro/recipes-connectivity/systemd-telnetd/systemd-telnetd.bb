SUMMARY = "telnetd"
DESCRIPTION = "systemd config for starting telnetd."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://telnetd.service;beginline=1;endline=17;md5=d134d0d385c53f9201a270fef8448f29"

SRC_URI = "file://telnetd.service"

S = "${UNPACKDIR}"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "telnetd.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install:append () {
    # install systemd unit files
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/telnetd.service ${D}${systemd_system_unitdir}
}
