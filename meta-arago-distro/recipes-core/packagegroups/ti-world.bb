SUMMARY = "TI World packagegroup"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

INSANE_SKIP:${PN} += "dev-deps"

CHROMIUM_TOOLS = "chromium-ozone-wayland"

DEVTOOLS = " \
    linux-libc-headers-dev \
    build-essential \
    packagegroup-core-tools-debug \
    packagegroup-core-tools-profile \
    git \
"

RDEPENDS:${PN} = "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    ti-test \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-graphics','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-gtk','',d)} \
    packagegroup-arago-connectivity \
    packagegroup-arago-crypto \
    packagegroup-arago-multimedia \
    packagegroup-arago-addons \
    packagegroup-arago-addons-extra \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','','packagegroup-arago-base-server-extra',d)} \
    ${@bb.utils.contains("BBFILE_COLLECTIONS","browser-layer",bb.utils.contains('DISTRO_FEATURES','wayland',"${CHROMIUM_TOOLS}",'',d),'',d)} \
    ${DEVTOOLS} \
    packagegroup-arago-misc \
    ${PREFERRED_PROVIDER_virtual/docker} \
"
