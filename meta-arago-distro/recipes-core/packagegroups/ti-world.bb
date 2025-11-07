SUMMARY = "TI World packagegroup"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

INSANE_SKIP:${PN} += "dev-deps"

CHROMIUM = ""
CHROMIUM:append:am57xx = "\
    chromium-ozone-wayland \
"
CHROMIUM:append:k3 = "\
    chromium-ozone-wayland \
"

PYTHON2APPS = " \
    ${@bb.utils.contains("BBFILE_COLLECTIONS","browser-layer",bb.utils.contains('DISTRO_FEATURES','wayland',"${CHROMIUM}",'',d),'',d)} \
"

DEVTOOLS = " \
    linux-libc-headers-dev \
    build-essential \
    packagegroup-core-tools-debug \
    git \
"

RDEPENDS:${PN} = "\
    packagegroup-arago-base \
    packagegroup-arago-console \
    ti-test \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-tisdk-graphics','',d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','packagegroup-arago-tisdk-gtk','',d)} \
    packagegroup-arago-tisdk-connectivity \
    packagegroup-arago-tisdk-crypto \
    packagegroup-arago-tisdk-multimedia \
    packagegroup-arago-tisdk-addons \
    packagegroup-arago-tisdk-addons-extra \
    ${@bb.utils.contains('DISTRO_FEATURES','opengl','','packagegroup-arago-base-tisdk-server-extra',d)} \
    ${@bb.utils.contains("BBFILE_COLLECTIONS", "meta-python2", "${PYTHON2APPS}", "", d)} \
    ${DEVTOOLS} \
    ${@bb.utils.contains('TUNE_FEATURES', 'armv7a', 'valgrind', '', d)} \
    packagegroup-arago-misc \
    ${PREFERRED_PROVIDER_virtual/docker} \
"
