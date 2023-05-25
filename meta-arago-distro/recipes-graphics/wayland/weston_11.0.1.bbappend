FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR:append = ".arago1"

# Drop 0001-backend-drm-Select-plane-based-on-current-attached-C.patch once we
# get past version 11.0.1 or commit 94afcbcdc3f1b1cfc050da242c9c70009b007fc6
# upstream

SRC_URI += " \
        file://0003-weston-Fix-virtual-keyboard-display-issue-for-QT5-ap.patch \
        file://0001-backend-drm-Select-plane-based-on-current-attached-C.patch \
        file://0001-Revert-require-GL_EXT_unpack_subimage-commit.patch \
"
