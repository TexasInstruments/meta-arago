SUMMARY = "Bluetooth enable script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://bt-enable.sh;beginline=2;endline=18;md5=d134d0d385c53f9201a270fef8448f29"

SRC_URI = " \
	file://bt-enable.sh \
	file://bt-enable.service \
"

PR = "r1"

S = "${UNPACKDIR}"

INITSCRIPT_NAME = "bt-enable.sh"
INITSCRIPT_PARAMS = "defaults 99"

inherit allarch update-rc.d systemd

SYSTEMD_SERVICE:${PN} = "bt-enable.service"

RDEPENDS:${PN} += "bash"

do_install () {
	if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
		install -d ${D}${sysconfdir}/init.d
		install -m 0755 bt-enable.sh ${D}${sysconfdir}/init.d/
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${bindir}
		install -d ${D}${systemd_system_unitdir}
		install -m 0755 bt-enable.sh ${D}${bindir}
		install -m 0644 bt-enable.service ${D}${systemd_system_unitdir}
	fi
}
