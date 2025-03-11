DESCRIPTION = "Task to add Gtk embedded related packages"
LICENSE = "MIT"
PR = "r2"

# Required for packages that are renamed using DEBIAN_NAMES
# [YOCTO #7298]
PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    gtk+3 \
    gtk+3-demo \
"
