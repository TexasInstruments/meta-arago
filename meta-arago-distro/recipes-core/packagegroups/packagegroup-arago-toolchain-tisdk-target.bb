SUMMARY = "Task to build and install header and libs into the sdk"
LICENSE = "MIT"
PR = "r14"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

TISDK_TOOLCHAIN_BASE_TARGET = "\
    packagegroup-arago-standalone-sdk-target \
    packagegroup-arago-multimedia-sdk-target \
"

TISDK_TOOLCHAIN_EXTRA_TARGET = "\
    packagegroup-arago-connectivity-sdk-target \
    packagegroup-arago-crypto-sdk-target \
"
TISDK_TOOLCHAIN_EXTRA_TARGET:omapl138 = ""

RDEPENDS:${PN} = "\
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-gtk-sdk-target','',d)} \
    ${TISDK_TOOLCHAIN_BASE_TARGET} \
    ${TISDK_TOOLCHAIN_EXTRA_TARGET} \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-graphics-sdk-target','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opencl','packagegroup-arago-opencl-sdk-target','',d)} \
    packagegroup-arago-addons-sdk-target \
"
