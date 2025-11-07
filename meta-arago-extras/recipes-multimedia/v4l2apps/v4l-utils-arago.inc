FILESEXTRAPATHS:prepend := "${THISDIR}/v4l-utils:"

PV = "1.28.1+git"

# v4l2 explicitly sets _FILE_OFFSET_BITS=32 to get access to
# both 32 and 64 bit file APIs.  But it does not handle the time side?
# Needs further investigation
GLIBC_64BIT_TIME_FLAGS = ""

SRC_URI = "\
    git://git.linuxtv.org/v4l-utils.git;protocol=https;branch=stable-1.28 \
    file://0001-media-ctl-Install-media-ctl-header-and-library-files.patch \
    file://0002-media-ctl-add-support-for-RGBIr-bayer-formats.patch \
"

SRCREV = "757acdaec187d51803c2957d36df3d717a3529da"
