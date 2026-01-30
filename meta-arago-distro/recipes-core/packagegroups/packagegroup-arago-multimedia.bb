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

MULTIMEDIA:append:am57xx = " \
    vis \
"

ACCEL_MM = ""

ACCEL_MM:append:am57xx = " \
    ipumm-fw \
"

RDEPENDS:${PN} = "\
    ${AUDIO} \
    packagegroup-arago-gst \
    ${MULTIMEDIA} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'mmip', '${ACCEL_MM}', '', d)} \
"
