PR:append = ".arago0"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI:append = " \
                  file://0001-chromium-gpu-sandbox-allow-access-to-PowerVR-GPU-fro.patch \
                  "
