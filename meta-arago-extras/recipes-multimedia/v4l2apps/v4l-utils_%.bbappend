FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-media-ctl-add-support-for-RGBIr-bayer-formats.patch \
"

PR:append = ".arago0"
