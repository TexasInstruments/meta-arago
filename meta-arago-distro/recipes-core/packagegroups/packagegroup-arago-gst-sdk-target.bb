DESCRIPTION = "Task to build and install header and libs in sdk"
LICENSE = "MIT"
PR = "r15"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

GSTREAMER_DEPS = " \
    gstreamer1.0-dev \
    gstreamer1.0-plugins-base-dev \
    gstreamer1.0-plugins-good-dev \
    gstreamer1.0-plugins-bad-dev \
    gstreamer1.0-libav-dev \
"

#    gstreamer1.0-plugins-hevc-dev 
GSTREAMER_DEPS:append:dra7xx = " \
"

RDEPENDS:${PN} = " \
    ${GSTREAMER_DEPS} \
"
