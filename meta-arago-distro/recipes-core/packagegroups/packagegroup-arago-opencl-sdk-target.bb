SUMMARY = "Task to install opencl packages on sdk target"

inherit packagegroup features_check

REQUIRED_DISTRO_FEATURES = "opencl"

RDEPENDS:${PN} = "\
    opencl-clhpp-dev \
    opencl-headers-dev \
"
