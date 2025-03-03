DESCRIPTION = "Task to add Gtk embedded related packages"
LICENSE = "MIT"
PR = "r2"

inherit packagegroup

RDEPENDS:${PN} = "\
    gtk+3 \
    gtk+3-demo \
"
