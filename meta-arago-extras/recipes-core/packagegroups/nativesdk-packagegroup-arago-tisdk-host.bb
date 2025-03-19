SUMMARY = "Host packages for a standalone Arago SDK with TI tools"
PR = "r2"
LICENSE = "MIT"

inherit packagegroup
inherit_defer nativesdk

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

EXTRA_TI_TOOLS = " \
    nativesdk-ti-cgt6x \
    nativesdk-ti-cgt-pru \
    nativesdk-gcc-arm-baremetal \
"

UTILS:append:arago = " \
    nativesdk-open62541-examples \
    nativesdk-open62541-tests \
"

RDEPENDS:${PN} = "\
    nativesdk-packagegroup-sdk-host \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'nativesdk-wayland-dev', '', d)} \
    nativesdk-python3-setuptools \
    nativesdk-python3-numpy \
    nativesdk-git \
    nativesdk-mtd-utils-ubifs \
    ${EXTRA_TI_TOOLS} \
"

RDEPENDS:${PN}:remove = "\
    nativesdk-meson \
"
