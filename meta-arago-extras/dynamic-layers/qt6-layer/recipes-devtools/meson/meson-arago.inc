FILESEXTRAPATHS:prepend := "${THISDIR}/meson:"

SRC_URI:append = " \
    file://0001-qt-dependencies-respect-PKG_CONFIG_SYSROOT_DIR-for-c.patch \
"
