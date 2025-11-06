SUMMARY = "Task to install graphics packages on sdk target"
LICENSE = "MIT"
PR = "r10"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

OPENGL_DEV = "\
    libegl-dev \
    libgl-dev \
    libgles1-dev \
    libgles2-dev \
    libgles3-dev \
"

WAYLAND_DEV = "\
    wayland-dev \
    weston-dev \
"

RDEPENDS:${PN} = "\
    libdrm-dev \
    libsdl2-dev \
    ktx-software-dev \
    nlohmann-json-dev \
    stb-dev \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', '${OPENGL_DEV}', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${WAYLAND_DEV}', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'gc320', 'ti-gc320-libs-dev', '', d)} \
"
