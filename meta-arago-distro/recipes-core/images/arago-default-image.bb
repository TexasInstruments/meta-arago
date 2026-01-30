SUMMARY = "Arago full filesystem image"

DESCRIPTION = "Complete Arago filesystem image containing complete\
 applications and packages to entitle the SoC."

require arago-image.inc

IMAGE_FEATURES += "package-management splash"

ARAGO_DEFAULT_IMAGE_EXTRA_INSTALL ?= ""

# we're assuming some display manager is being installed with opengl
SYSTEMD_DEFAULT_TARGET = "${@bb.utils.contains('DISTRO_FEATURES','opengl','graphical.target','multi-user.target',d)}"

IMAGE_INSTALL += "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    ti-test \
    ti-test-extras \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-graphics','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-gtk','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opencl','packagegroup-arago-opencl','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','vulkan','packagegroup-arago-vulkan','',d)} \
    packagegroup-arago-connectivity \
    packagegroup-arago-crypto \
    packagegroup-arago-multimedia \
    packagegroup-arago-addons \
    packagegroup-arago-addons-extra \
    ${ARAGO_DEFAULT_IMAGE_EXTRA_INSTALL} \
    packagegroup-arago-sysrepo \
"

DEVTOOLS = " \
    linux-libc-headers-dev \
    build-essential \
    packagegroup-core-tools-debug \
    packagegroup-core-tools-profile \
    git \
    dtc \
"

IMAGE_INSTALL += "\
    ${DEVTOOLS} \
    docker \
"
