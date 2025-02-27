FILESEXTRAPATHS:prepend := "${THISDIR}/gstreamer1.0-plugins-good:"

SRC_URI:append = " \
    file://0002-Adding-support-for-bayer-formats-with-IR-component.patch \
    file://0003-v4l2-Changes-for-DMA-Buf-import-j721s2.patch \
    file://0004-v4l2-Give-preference-to-contiguous-format-if-support.patch \
    file://0005-HACK-gstv4l2object-Increase-min-buffers-for-CSI-capt.patch \
"

PR:append = ".arago0"
