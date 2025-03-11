SUMMARY = "A GStreamer NNstreamer pipelines real-time profiling plugin"
HOMEPAGE = "https://github.com/nnstreamer/nnshark"

LICENSE = "GPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=e1caa368743492879002ad032445fa97 \
                    file://COPYING.LESSER;md5=66c40c88533cd228b5b85936709801c8"

SRC_URI = "git://github.com/nnstreamer/nnshark.git;protocol=https;branch=master;name=nnshark \
           git://github.com/GStreamer/common.git;protocol=https;branch=master;name=common;subdir=${S}/common \
"

SRCREV_FORMAT = "default"
SRCREV_nnshark = "bcee91b23a98c981b87051fe1b2ad545991c4aa2"
SRCREV_common = "b64f03f6090245624608beb5d2fff335e23a01c0"

S = "${WORKDIR}/git"

DEPENDS = "\
    gtk-doc-native \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
"

inherit pkgconfig autotools-brokensep

EXTRA_OECONF = " \
        --disable-graphviz \
        --disable-gtk-doc \
"

do_configure:prepend() {
    sh autogen.sh --noconfigure
}

FILES:${PN} += "\
       ${libdir}/gstreamer-1.0/libgstsharktracers.so \
       ${libdir}/gstreamer-1.0/libgstsharktracers.la \
"
