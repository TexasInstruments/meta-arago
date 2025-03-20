FILESEXTRAPATHS:prepend := "${THISDIR}/psplash:"

PR:append = ".arago5"

SRC_URI += "file://psplash-18bpp.patch                      \
            file://0001-psplash-Add-Arago-custom-color.patch\
"

SPLASH_IMAGES = "file://txn-logo.png;outsuffix=default"
