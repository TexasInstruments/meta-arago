PR:append = ".arago1"

FILESEXTRAPATHS:prepend := "${THISDIR}/pipewire:"
SRC_URI:append = " file://pipewire.service"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${UNPACKDIR}/pipewire.service ${D}${systemd_system_unitdir}/
    fi
}

PACKAGECONFIG:append = " pipewire-jack"
PACKAGECONFIG:remove = "jack"
