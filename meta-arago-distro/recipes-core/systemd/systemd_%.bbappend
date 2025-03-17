PR:append = ".arago7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://local.rules \
    file://usb1-rules.sh \
    file://usb2-rules.sh \
    file://10-eth.network \
    file://15-eth.network \
    file://30-wlan.network \
    file://60-usb.network \
    file://timesyncd.conf \
    file://37-can-j7.rules \
    file://37-can-am62.rules \
    file://37-can-dra7.rules \
    file://37-can-ti33x.rules \
"

do_install:append() {
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/local.rules ${D}${sysconfdir}/udev/rules.d/

    install -d ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${UNPACKDIR}/usb1-rules.sh ${D}${sysconfdir}/udev/scripts/
    install -m 0755 ${UNPACKDIR}/usb2-rules.sh ${D}${sysconfdir}/udev/scripts/

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

    install -d ${D}${libdir}/udev/rules.d
    install -m 0644 ${UNPACKDIR}/37-can-j7.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-am62.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-dra7.rules ${D}${libdir}/udev/rules.d/
    install -m 0644 ${UNPACKDIR}/37-can-ti33x.rules ${D}${libdir}/udev/rules.d/
}

FILES:udev += " \
    ${libdir}/udev/rules.d/37-can-j7.rules \
    ${libdir}/udev/rules.d/37-can-am62.rules \
    ${libdir}/udev/rules.d/37-can-dra7.rules \
    ${libdir}/udev/rules.d/37-can-ti33x.rules \
"
