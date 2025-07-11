SUMMARY = "Task to add multimedia related packages"
LICENSE = "MIT"
PR = "r24"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

MULTIMEDIA = " \
    libcamera \
    libcamera-gst \
"

AUDIO = " \
    fftw \
    libsndfile1 \
"

MULTIMEDIA:append:arago = " ffmpeg mpv"

MULTIMEDIA:omapl138 = ""

MULTIMEDIA:append:dra7xx = " \
    vis \
    hevc-arm-decoder \
"

ACCEL_MM = ""

ACCEL_MM:append:omap-a15 = " \
    ipumm-fw \
"

RDEPENDS:${PN} = "\
    ${AUDIO} \
    packagegroup-arago-gst \
    ${MULTIMEDIA} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'mmip', '${ACCEL_MM}', '', d)} \
"
