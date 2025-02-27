SUMMARY = "Useful Vulkan packages for Arago"
DESCRIPTION = "Packages we recommend for working with Vulkan"

inherit packagegroup features_check

REQUIRED_DISTRO_FEATURES = "vulkan"

RDEPENDS:${PN} = "\
    vulkan-tools \
"
