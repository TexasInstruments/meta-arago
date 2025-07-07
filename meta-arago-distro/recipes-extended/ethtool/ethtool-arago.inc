PR:append = ".arago0"

FILESEXTRAPATHS:prepend := "${THISDIR}/ethtool:"

SRC_URI:append = " \
                  file://0001-pretty-add-support-for-ti-k3-cpsw-registers-and-ale-table-dump.patch \
                  "
