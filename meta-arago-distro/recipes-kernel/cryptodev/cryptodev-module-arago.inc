PR:append = ".arago0"

KERNEL_MODULE_AUTOLOAD += "cryptodev"

FILESEXTRAPATHS:prepend := "${THISDIR}/cryptodev-module:"

SRC_URI += "file://0001-Fix-build-for-Linux-6-18-rc1.patch"
