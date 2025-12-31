FILESEXTRAPATHS:prepend := "${THISDIR}/libdrm:"

SRC_URI += " \
file://0001-Add-option-to-run-a-test-indefinitely.patch \
file://0002-omap-add-OMAP_BO-flags-to-affect-buffer-allocation.patch \
"

PR:append = ".arago4"
