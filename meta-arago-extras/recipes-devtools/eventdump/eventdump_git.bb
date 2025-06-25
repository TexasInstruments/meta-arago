SUMMARY = "Utility for parsing system events"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://eventdump.c;beginline=1;endline=34;md5=23b59be24a88ddc407b553035f3fd3e9"

PR = "r4"
PV = "1.0+git"

SRCREV = "5db45a36a05a78ea44a4cb25312ed5ee3c2bd76d"

SRC_URI = "git://github.com/TI-ECS/eventdump.git;protocol=https;branch=master \
	file://0001-PATCH_Makefile_Play_nicer_with_bitbake-OpenEmbedded.patch"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} DEST_DIR=${D} BIN_DIR=${bindir} CC="${CC}""

do_install() {
	oe_runmake install
}
