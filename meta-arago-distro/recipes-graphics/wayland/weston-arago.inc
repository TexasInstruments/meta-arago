FILESEXTRAPATHS:prepend := "${THISDIR}/weston:"

PR:append = ".arago1"

SRC_URI += " \
        file://0001-Revert-require-GL_EXT_unpack_subimage-commit.patch \
"
