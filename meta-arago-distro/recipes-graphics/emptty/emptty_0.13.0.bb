SUMMARY = "Dead simple CLI Display Manager on TTY"
HOMEPAGE = "https://github.com/tvrzna/emptty"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=d1e4d12c7d1d17367ba5668706a405ba"

GO_IMPORT = "github.com/tvrzna/emptty"
GO_IMPORT_pam = "github.com/msteinert/pam"
SRC_URI = "\
    git://${GO_IMPORT}.git;protocol=https;branch=master \
    git://${GO_IMPORT_pam}.git;protocol=https;branch=master;name=pam;destsuffix=${S}/src/${GO_IMPORT_pam} \
    file://pamconf \
    file://init \
    "
SRCREV = "4046552b6f5cc1cf76ce8bf333e04e16c59febca"
SRCREV_pam = "50ded1b0e7864b9bf75005eb945a8ec826bcf69d"

SRCREV_FORMAT .= "_pam"

PACKAGES += "${PN}-conf"

PACKAGECONFIG ?= "${@bb.utils.filter('DISTRO_FEATURES', 'pam x11', d)}"
PACKAGECONFIG[pam] = ",,libpam,pam-plugin-succeed-if"
PACKAGECONFIG[x11] = ",,virtual/libx11"

DEPENDS += "${@bb.utils.contains('PACKAGECONFIG', 'pam', '', 'libxcrypt', d)}"

GO_TAGS = ""
GO_TAGS:append = "${@bb.utils.contains('PACKAGECONFIG', 'pam', '', ',nopam', d)}"
GO_TAGS:append = "${@bb.utils.contains('PACKAGECONFIG', 'x11', '', ',noxlib', d)}"

GOBUILDFLAGS:append = " -tags=${GO_TAGS}"

export GO111MODULE="off"

inherit go update-rc.d systemd

DEPENDS += "gzip-native"

do_install () {
    # general collateral
    install -Dm755 ${B}/${GO_BUILD_BINDIR}/emptty ${D}${bindir}/emptty
    oe_runmake -C ${S}/src/${GO_IMPORT} DESTDIR=${D} install-config
    install -d ${D}${mandir}/man1
    gzip -cn ${S}/src/${GO_IMPORT}/res/emptty.1 > ${D}${mandir}/man1/emptty.1.gz

    # pam config
    if "${@bb.utils.contains('PACKAGECONFIG','pam','true','false',d)}"
    then
        install -Dm644 ${WORKDIR}/pamconf ${D}${sysconfdir}/pam.d/emptty
    fi

    # init services
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}
    then
        oe_runmake -C ${S}/src/${GO_IMPORT} DESTDIR=${D} install-systemd
    else
        install -Dm755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/emptty
    fi
}

FILES:${PN} = "\
    ${systemd_system_unitdir}/emptty.service \
    ${sysconfdir}/init.d/emptty \
    ${bindir}/emptty \
    ${mandir}/man1/emptty.1.gz \
    ${sysconfdir}/pam.d/emptty \
"

FILES:${PN}-conf += "${sysconfdir}/emptty/conf"
CONFFILES:${PN}-conf += "${sysconfdir}/emptty/conf"
RPROVIDES:${PN}-conf += "virtual-emptty-conf"

RDEPENDS:${PN} += "virtual-emptty-conf"

SYSTEMD_SERVICE:${PN} = "emptty.service"

INITSCRIPT_NAME = "emptty"
INITSCRIPT_PARAMS = "start 10 5 2 . stop 20 0 1 6 ."
INHIBIT_UPDATERCD_BBCLASS = "${@oe.utils.conditional('VIRTUAL-RUNTIME_init_manager', 'systemd', '1', '', d)}"
