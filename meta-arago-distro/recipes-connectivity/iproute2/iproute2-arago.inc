PR:append = ".arago6"

FILESEXTRAPATHS:prepend := "${THISDIR}/iproute2:"

SRC_URI:append = " \
    file://0014-iplink-hsr-prp-add-support-for-vlan-tagged-supervisi.patch \
"
