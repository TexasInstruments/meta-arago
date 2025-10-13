SUMMARY = "Task to build and install header and libs in sdk"
LICENSE = "MIT"
PR = "r15"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

GSTREAMER_DEPS = " \
    gstreamer1.0-dev \
    gstreamer1.0-plugins-base-dev \
    gstreamer1.0-plugins-good-dev \
    gstreamer1.0-plugins-bad-dev \
"

GSTREAMER_DEPS:append:arago = " gstreamer1.0-libav-dev"

RDEPENDS:${PN} = " \
    ${GSTREAMER_DEPS} \
"
