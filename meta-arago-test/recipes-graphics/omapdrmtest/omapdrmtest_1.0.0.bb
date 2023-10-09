SUMMARY = "Test applications for OMAP DRM interface"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://viddec3test.c;beginline=1;endline=16;md5=c391f44e40a29096285e3121923041df"

DEPENDS = "ffmpeg libdce libdrm virtual/libgbm virtual/libgles2 virtual/egl wayland"

COMPATIBLE_MACHINE = "omap-a15|ti43x"

inherit autotools pkgconfig

PR = "r29"
SRCREV = "1fceb3ac4b0e7ed014000a10dfe6ca8729f9cd8d"

EXTRA_OEMAKE = "CC="${CC}""
TARGET_CC_ARCH += "${LDFLAGS}"

SRC_URI = "git://git.ti.com/git/glsdk/omapdrmtest.git;protocol=https \
        file://0001-Makefile.am-lblah-flags-belong-to-LDADD-not-LDFLAGS.patch \
"

S = "${WORKDIR}/git"
