PR:append = ".arago7"

FILESEXTRAPATHS:prepend := "${THISDIR}/systemd:"

SRC_URI:append = " \
    file://10-eth.network \
    file://15-eth.network \
    file://30-wlan.network \
    file://60-usb.network \
    file://timesyncd.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${UNPACKDIR}/10-eth.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${UNPACKDIR}/15-eth.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${UNPACKDIR}/30-wlan.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${UNPACKDIR}/60-usb.network ${D}${sysconfdir}/systemd/network/

    # Allow automount from udev
    install -m 0644 ${D}${systemd_system_unitdir}/systemd-udevd.service ${D}${sysconfdir}/systemd/system/
    sed -i 's/MountFlags=slave/MountFlags=shared/g' ${D}${sysconfdir}/systemd/system/systemd-udevd.service

    # Need NAMESPACES enabled in the kernel, workaround for now
    install -m 0644 ${D}${systemd_system_unitdir}/systemd-hostnamed.service ${D}${sysconfdir}/systemd/system/
    sed -i 's/PrivateNetwork=yes/PrivateNetwork=no/g' ${D}${sysconfdir}/systemd/system/systemd-hostnamed.service

    install -d ${D}${sysconfdir}/systemd/
    install -m 0644 ${UNPACKDIR}/timesyncd.conf ${D}${sysconfdir}/systemd/

    # Remove new prompt code that is polluting the testing output
    rm ${D}/${nonarch_libdir}/tmpfiles.d/20-systemd-osc-context.conf
    rm ${D}/${nonarch_libdir}/systemd/profile.d/80-systemd-osc-context.sh
    rm ${D}/${sysconfdir}/profile.d/80-systemd-osc-context.sh
}
