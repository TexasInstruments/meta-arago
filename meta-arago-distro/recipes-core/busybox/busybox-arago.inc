FILESEXTRAPATHS:prepend := "${THISDIR}/busybox:"

SRC_URI += " \
	file://network.cfg \
	file://utils.cfg \
"

PR:append = ".arago22"
