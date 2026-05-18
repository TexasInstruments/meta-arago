PR:append = ".arago0"

FILESEXTRAPATHS:prepend := "${THISDIR}/chromium-ozone-wayland:"

PACKAGECONFIG:append = " proprietary-codecs"

SRC_URI:append = " \
                  file://0001-media-gpu-v4l2-Use-ChromeOS-style-dev-paths-for-all-.patch \
                  file://0002-chromium-gpu-sandbox-Allow-GPU-sandbox-access-to-V4L.patch \
                  file://0003-chromium-gpu-v4l2-Fix-OUTPUT-queue-streaming-in-V4L2.patch \
                  file://0004-media-gpu-v4l2-Avoid-placing-incomplete-H264-access-.patch \
                  "
