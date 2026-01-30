SUMMARY = "Task to build and install header and libs into sdk"
LICENSE = "MIT"
PR = "r12"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

AUDIO = " \
    fftw-dev \
    libsndfile1-dev \
"

RDEPENDS:${PN} = "\
    ${AUDIO} \
    packagegroup-arago-gst-sdk-target \
"
