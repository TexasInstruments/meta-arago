SUMMARY = "Task to install Gtk dev packages in SDK"
LICENSE = "MIT"
PR = "r2"

inherit packagegroup

RDEPENDS:${PN} = "\
    gtk+3-dev \
"
