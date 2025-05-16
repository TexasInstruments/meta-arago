PR:append = ".arago0"

FILESEXTRAPATHS:prepend := "${THISDIR}/chromium-ozone-wayland-130.0.6723.91:"

SRC_URI:append = " \
                  file://0001-chromium-gpu-sandbox-allow-access-to-PowerVR-GPU-fro.patch \
                  "
