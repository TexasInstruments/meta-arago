SUMMARY = "ARM Neural Network SDK"
DESCRIPTION = "Linux software and tools to enable machine learning workloads on power-efficient devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3e14a924c16f7d828b8335a59da64074"

BRANCH = "branches/armnn_24_11"
SRC_URI = "git://github.com/ARM-software/armnn.git;branch=${BRANCH};protocol=https"
SRC_URI += "file://0001-Fix-type-casting-for-32bit-builds.patch"

# v24.11
SRCREV = "3ed70c005559d409feff2c578a1a39cf8fec8804"

S = "${WORKDIR}/git"

# Only compatible with armv7a, armv7ve, and aarch64
COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"
COMPATIBLE_MACHINE:armv7ve = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"

inherit cmake
inherit pkgconfig

DEPENDS = " \
    boost \
    protobuf \
    xxd-native \
    arm-compute-library \
"

PACKAGECONFIG += "unit-tests tests ref"
PACKAGECONFIG += "${@bb.utils.contains('TARGET_ARCH', 'aarch64', 'neon', '', d)}"
PACKAGECONFIG += "${@bb.utils.contains('TARGET_ARCH', 'arm', 'neon', '', d)}"

PACKAGECONFIG[neon] = "-DARMCOMPUTENEON=1, -DARMCOMPUTENEON=0"
PACKAGECONFIG[unit-tests] = "-DBUILD_UNIT_TESTS=1, -DBUILD_UNIT_TESTS=0"
PACKAGECONFIG[tests] = "-DBUILD_TESTS=1, -DBUILD_TESTS=0"
PACKAGECONFIG[ref] = "-DARMNNREF=1, -DARMNNREF=0"

EXTRA_OECMAKE += " \
    -DHALF_INCLUDE=${STAGING_DIR_TARGET} \
"

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    if ${@bb.utils.contains('PACKAGECONFIG', 'tests', 'true', 'false', d)}; then
        install -d ${D}${bindir}/${P}
        find ${B}/tests -maxdepth 1 -type f -executable -exec cp $CP_ARGS {} ${D}${bindir}/${P} \;
    fi

    if ${@bb.utils.contains('PACKAGECONFIG', 'unit-tests', 'true', 'false', d)}; then
        install -d ${D}${bindir}/${P}
        cp $CP_ARGS ${B}/UnitTests ${D}${bindir}/${P}
    fi

    if ${@bb.utils.contains('PACKAGECONFIG', 'tensorflow-lite', 'false', 'true', d)}; then
        rm -rf ${D}${includedir}/armnnTfLiteParser
    fi
}

CXXFLAGS += "-Wno-error=array-bounds -Wno-error=deprecated-declarations -Wno-error=nonnull"

FILES:${PN} += "${libdir}/*"
FILES:${PN}-dev += "${includedir}/* ${libdir}/cmake/armnn/* ${libdir}/pkgconfig/*.pc ${bindir}/*"

INSANE_SKIP:${PN} = "dev-so"
INSANE_SKIP:${PN}-dev += "buildpaths"
