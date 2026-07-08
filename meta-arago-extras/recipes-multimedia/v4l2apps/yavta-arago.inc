FILESEXTRAPATHS:prepend := "${THISDIR}/yavta:"

SRC_URI:append = " \
    file://0001-Update-kernel-headers.patch \
    file://0002-Set-and-get-metadata-width-and-height.patch \
    file://0003-Add-support-for-generic-metadata-formats.patch \
    file://0004-Add-metadata-layout-ctrl.patch \
"

PR:append = ".arago0"
