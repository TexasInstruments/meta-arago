FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Add-EST-Yang-Models.patch"

FILES:${PN} += "/usr/share/yang* /usr/share/netopeer2/* /usr/lib/sysrepo-plugind/*"
