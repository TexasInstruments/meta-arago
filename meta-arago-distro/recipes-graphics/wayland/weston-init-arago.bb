SUMMARY = "Configuration files for the Weston Wayland compositor"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI:append = " \
    file://weston.ini \
    file://weston-launch-calibrator.sh \
    file://emptty.conf \
"

S = "${WORKDIR}"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xwayland', '', d)}"
PACKAGECONFIG:append:qemuriscv64 = " use-pixman"
PACKAGECONFIG:append:qemuppc64 = " use-pixman"

PACKAGECONFIG[xwayland] = ",,"
PACKAGECONFIG[no-idle-timeout] = ",,"
PACKAGECONFIG[use-pixman] = ",,"

DEFAULTBACKEND ??= ""
DEFAULTBACKEND:qemuall ?= "drm"

do_install() {
	install -D -p -m0644 ${WORKDIR}/weston.ini ${D}${sysconfdir}/xdg/weston/weston.ini
	install -D -p -m0644 ${WORKDIR}/emptty.conf ${D}${sysconfdir}/emptty/conf

	if [ -n "${DEFAULTBACKEND}" ]; then
		sed -i -e "/^\[core\]/a backend=${DEFAULTBACKEND}-backend.so" ${D}${sysconfdir}/xdg/weston/weston.ini
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', 'yes', 'no', d)}" = "yes" ]; then
		sed -i -e "/^\[core\]/a xwayland=true" ${D}${sysconfdir}/xdg/weston/weston.ini
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'no-idle-timeout', 'yes', 'no', d)}" = "yes" ]; then
		sed -i -e "/^\[core\]/a idle-time=0" ${D}${sysconfdir}/xdg/weston/weston.ini
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'use-pixman', 'yes', 'no', d)}" = "yes" ]; then
		sed -i -e "/^\[core\]/a use-pixman=true" ${D}${sysconfdir}/xdg/weston/weston.ini
	fi

	install -dm 755 -o weston -g weston ${D}/home/weston
	install -Dm755 weston-launch-calibrator.sh ${D}/${bindir}/weston-launch-calibrator
}

inherit useradd

USERADD_PACKAGES = "${PN}"

# rdepends on weston which depends on virtual/egl
#
require ${COREBASE}/meta/recipes-graphics/wayland/required-distro-features.inc

RDEPENDS:${PN} = "weston kbd ${@bb.utils.contains('PACKAGECONFIG', 'xwayland', 'weston-xwayland', '', d)} emptty"

FILES:${PN} += "\
    ${sysconfdir}/xdg/weston/weston.ini \
    ${sysconfdir}/emptty/conf \
    /home/weston \
    ${bindir}/weston-launch-calibrator \
    "

CONFFILES:${PN} += "${sysconfdir}/xdg/weston/weston.ini ${sysconfdir}/emptty/conf"
RPROVIDES:${PN}:append = " virtual-emptty-conf"
RCONFLICTS:${PN} += "weston-init"

USERADD_PARAM:${PN} = "--home /home/weston --shell /bin/sh --user-group -G video,input,render,nopasswdlogin weston"
GROUPADD_PARAM:${PN} = "-r wayland; -r render; -r nopasswdlogin"
