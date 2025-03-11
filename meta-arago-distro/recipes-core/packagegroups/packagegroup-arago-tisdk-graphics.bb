SUMMARY = "Task to install graphics packages"
LICENSE = "MIT"
PR = "r26"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

GFX_WAYLAND = "\
    weston-init \
    weston-examples \
"

RDEPENDS:${PN} = "\
    libegl \
    glmark2 \
    kmscube \
    ${@bb.utils.contains('MACHINE_FEATURES', 'gc320', 'ti-gc320-tests', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', "${GFX_WAYLAND}", '', d)} \
"
