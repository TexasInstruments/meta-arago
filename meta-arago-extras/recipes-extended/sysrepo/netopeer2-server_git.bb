SUMMARY = "Implementation of network configuration tools based on NETCONF Protocol"
DESCRIPTION = "Netopeer2 is based on the new generation of the NETCONF and YANG libraries - \
libyang and libnetconf2. The Netopeer server uses sysrepo as a NETCONF datastore implementation."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=41daedff0b24958b2eba4f9086d782e1"

SRC_URI = " \
    git://github.com/CESNET/Netopeer2.git;protocol=https;branch=master \
    file://0001-Add-EST-Yang-Models.patch \
    ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'file://netopeer2-server', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'file://netopeer2-serverd.service', '', d)} \
"

PV = "2.2.35+git"
SRCREV = "6d1cb61ef3ce2274a91dc9cbc51318bcd0b54697"

DEPENDS = "libyang libnetconf2 sysrepo sysrepo-native"
RDEPENDS:${PN} += "bash curl"

FILES:${PN} += "${datadir}/yang* ${datadir}/netopeer2/* ${libdir}/sysrepo-plugind/*"

inherit cmake pkgconfig
inherit ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}

EXTRA_OECMAKE = " \
    -DCMAKE_INSTALL_PREFIX=${prefix} \
    -DCMAKE_BUILD_TYPE:String=Release \
    -DSYSREPO_SETUP=OFF \
    -DINSTALL_MODULES=OFF \
    -DGENERATE_HOSTKEY=OFF \
    -DMERGE_LISTEN_CONFIG=OFF \
"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "netopeer2-serverd.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

do_install:append () {
    install -d ${D}${sysconfdir}/netopeer2/scripts
    install -m 0755 ${S}/scripts/setup.sh ${D}${sysconfdir}/netopeer2/scripts/setup.sh
    install -m 0755 ${S}/scripts/merge_hostkey.sh ${D}${sysconfdir}/netopeer2/scripts/merge_hostkey.sh
    install -m 0755 ${S}/scripts/merge_config.sh ${D}${sysconfdir}/netopeer2/scripts/merge_config.sh
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${UNPACKDIR}/netopeer2-server ${D}${sysconfdir}/init.d/
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/netopeer2-serverd.service ${D}${systemd_system_unitdir}
    fi
}
