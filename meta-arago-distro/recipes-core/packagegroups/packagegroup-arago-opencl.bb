SUMMARY = "Useful OpenCL packages for Arago"
DESCRIPTION = "Packages we recommend for working with OpenCL"

inherit packagegroup features_check

REQUIRED_DISTRO_FEATURES = "opencl"

RDEPENDS:${PN} = "\
    clinfo \
"
