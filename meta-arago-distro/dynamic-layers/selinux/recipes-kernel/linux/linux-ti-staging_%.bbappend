# The meta-selinux layer includes an selinux.cfg file containing
# configs necessary for the Linux kernel to enable SELinux

# In order to reduce maintainability issues, the file will
# be retained in meta-selinux layer

require ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', 'recipes-kernel/linux/linux-yocto_selinux.inc', '', d)}

do_configure:append() {
    if echo "${DISTRO_FEATURES}" | grep -q "selinux"; then
        cat ${UNPACKDIR}/selinux.cfg >> ${B}/.config
    fi
}
