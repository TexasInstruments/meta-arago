PR:append = ".arago0"

EXTENSION_DIR = "/usr/lib/chromium/extensions/force-h264-youtube"

FILESEXTRAPATHS:prepend := "${THISDIR}/chromium-ozone-wayland:"

PACKAGECONFIG:append = " proprietary-codecs"

SRC_URI:append = " \
                  file://0001-media-gpu-v4l2-Use-ChromeOS-style-dev-paths-for-all-.patch \
                  file://0002-chromium-gpu-sandbox-Allow-GPU-sandbox-access-to-V4L.patch \
                  file://0003-chromium-gpu-v4l2-Fix-OUTPUT-queue-streaming-in-V4L2.patch \
                  file://0004-media-gpu-v4l2-Avoid-placing-incomplete-H264-access-.patch \
                  file://force-h264-youtube/force_h264.js \
                  file://force-h264-youtube/manifest.json \
                  "

do_install:append() {
    install -d ${D}${EXTENSION_DIR}
    install -m 0644 ${WORKDIR}/sources/force-h264-youtube/force_h264.js \
                    ${WORKDIR}/sources/force-h264-youtube/manifest.json \
                    ${D}${EXTENSION_DIR}/
}

FILES:${PN}:append = " ${EXTENSION_DIR}"
