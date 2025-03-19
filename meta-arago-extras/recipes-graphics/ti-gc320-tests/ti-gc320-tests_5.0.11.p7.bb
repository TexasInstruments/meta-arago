DESCRIPTION = "Test application for GC320 2D API"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://dra7x-sdk-ti-gc320-tests_manifest.html;md5=22a001855467f373c47f45de5330e3c9"

COMPATIBLE_MACHINE = "omap-a15"

DEPENDS = "ti-gc320-libs"

BRANCH = "ti-${PV}"

SRC_URI = "git://git.ti.com/git/graphics/ti-gc320-test.git;protocol=https;branch=${BRANCH} \
file://0001-makefile.linux.def-relax-assignment-of-CC-and-other-.patch \
"
SRCREV = "3c334f2004c718c9bb4321213c362e65c491efe6"
PR = "r2"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "AQROOT=${S}/tests/src -f makefile.linux TOOLCHAIN_PATH=${TOOLCHAIN_PATH} CROSS_COMPILE=${TARGET_PREFIX} ARCH_TYPE=${TARGET_ARCH} CPU_TYPE=cortex-a15 VIVANTE_SDK_INC=${S}/sdk/include VIVANTE_SDK_LIB=${STAGING_DIR_TARGET}/usr/lib DESTDIR=${D} TARGET_PRODUCT=${TARGET_PRODUCT} LIBDIR=${libdir} SDK_DIR=${S}/build/tests/ EGL_API_FB=1"

do_install() {
	cd ${S}/tests/src/test/hal/common/UnitTest/galUtil
	oe_runmake install
	cd ${S}/tests/src/test/hal/common/UnitTest/units/gal2D/clear
	oe_runmake install
	cd ${S}/tests/src/test/hal/common/UnitTest/galRunTest2
	oe_runmake install

	install -d ${D}${bindir}/GC320/tests/unit_test/

	for file in ${S}/build/tests/samples/hal/unit_test/*; do
		install -m 755 $file ${D}${bindir}/GC320/tests/unit_test/
	done
}

INSANE_SKIP:${PN} += "ldflags already-stripped libdir"
INSANE_SKIP:${PN}-dbg += "libdir"
