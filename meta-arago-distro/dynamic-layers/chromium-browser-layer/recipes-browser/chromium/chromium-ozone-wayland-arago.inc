PR:append = ".arago0"

FILESEXTRAPATHS:prepend := "${THISDIR}/chromium-ozone-wayland-132.0.6834.83:"

PACKAGECONFIG[use-v4l2] = "use_v4l2_codec=true,use_v4l2_codec=false"
PACKAGECONFIG:append = " proprietary-codecs use-v4l2"

CHROMIUM_EXTRA_ARGS:append = " ${@bb.utils.contains('PACKAGECONFIG', 'use-v4l2', '--ozone-platform-hint=wayland --enable-features=AcceleratedVideoDecoder,AcceleratedVideoDecodeLinuxGL,AcceleratedVideoDecodeLinuxZeroCopyGL', '', d)}"

SRC_URI:append = " \
                  file://0001-chromium-gpu-sandbox-allow-access-to-PowerVR-GPU-fro.patch \
                  file://0002-chromium-gpu-sandbox-Allow-GPU-sandbox-access-to-V4L.patch \
                  file://0003-chromium-gpu-v4l2-Fix-OUTPUT-queue-streaming-in-V4L2.patch \
                  "
