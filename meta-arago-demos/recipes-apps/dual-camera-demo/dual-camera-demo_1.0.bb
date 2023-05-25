DESCRIPTION = "Qt Dual Camera Demo"
HOMEPAGE = "https://gitorious.org/dual-camera-demo/"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7ca707704d3354a64feeb4f19f52eb5"

DEPENDS += "libdrm cmem"
require recipes-core/matrix/matrix-gui-paths.inc

PR = "r20"

BRANCH = "master"
SRCREV = "3188b1eacb14dda0dcf3a58877962d86687b6d20"

SRC_URI = "git://git.ti.com/git/sitara-linux/dual-camera-demo.git;protocol=https;branch=${BRANCH} \
           file://desc_dual-camera.html \
           file://dual_camera_qt5.sh \
           file://dual_camera_qt4.sh \
           file://dual-camera.desktop \
"

S = "${WORKDIR}/git"

inherit qt5

export SDK_PATH_TARGET='${STAGING_DIR_HOST}'

# use the make targets already created in the Makefile.build files
do_install() {
    install -d ${D}/usr/bin
    install -d ${D}${MATRIX_APP_DIR}/dual-camera
    install dual_camera ${D}/usr/bin/dual_camera
    install ${WORKDIR}/dual_camera_qt5.sh ${D}/usr/bin/dual_camera.sh
    install ${WORKDIR}/desc_dual-camera.html ${D}/${MATRIX_APP_DIR}/dual-camera
    install ${WORKDIR}/dual-camera.desktop ${D}/${MATRIX_APP_DIR}/dual-camera
}

PACKAGES += "matrix-gui-apps-dual-camera"

RDEPENDS:${PN} += "libdrm libdrm-omap"

RDEPENDS:matrix-gui-apps-dual-camera  = "matrix-gui-apps-images matrix-gui-submenus-camera ${PN}"

# Add the matrix directory to the FILES
FILES:${PN} = "/usr/bin/*"

FILES:matrix-gui-apps-dual-camera = "${MATRIX_APP_DIR}/*"
