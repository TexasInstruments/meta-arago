PV = "3.7.8"

LIC_FILES_CHKSUM = "file://LICENSE;md5=9bb3d334294e8719f41c531e28a9a697"

SRCREV = "03e294d83b610f89e8ba7b2156a80dc0ad534443"

# 2.x still uses config.h and meta-oe/scarthgap tries
# to process it for multilib, which cannot be disabled
# and results in an error. Just duplicate the actual
# ly_config.h to suppress the error.
do_install:prepend () {
    install -d ${D}${includedir}/libyang
    install -m 644 libyang/ly_config.h ${D}${includedir}/libyang/config.h
}

# 3.x now comes with ly_config.h, so process it here
# for multilib
do_install:append () {
    oe_multilib_header  libyang/ly_config.h
}
