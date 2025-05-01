SUMMARY = "Base tools that are recommended for most images"
LICENSE = "MIT"
PR = "r10"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ARAGO_ALSA_BASE = "\
    libasound \
    alsa-utils-aplay \
"

ARAGO_BASE = "\
    module-init-tools \
    mtd-utils \
    mtd-utils-ubifs \
    curl \
    initscript-telnetd \
    systemd-telnetd \
    ethtool \
    bash \
    opkg-bash-completion \
    udev-extraconf \
    libgpiod \
    libgpiod-tools \
"

ARAGO_EXTRA = "\
    devmem2 \
    tcpdump \
    parted \
    dropbear \
    openssh-sftp-server \
    kmsxx \
    kmsxx-python \
    can-utils \
    docker \
    expat \
    glib-2.0 \
    libxml2 \
    libpcre \
    iptables \
    iperf3 \
    netperf \
    arago-gpl-notice \
    arago-feed-config \
    nfs-utils-client \
    cifs-utils \
    phytool \
    lldpd \
"

ARAGO_EXTRA:append:arago = "\
    dbus-broker \
"

OPTEE_PKGS = " \
    optee-os \
    optee-client \
    optee-test \
    optee-examples \
"

# minimal set of packages - needed to boot
RDEPENDS:${PN} = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'zeroconf', 'packagegroup-base-zeroconf', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'alsa', '${ARAGO_ALSA_BASE}', '',d)} \
    ${ARAGO_BASE} \
    ${ARAGO_EXTRA} \
    ${@bb.utils.contains_any('OPTEEOUTPUTMACHINE', 'ti', "${OPTEE_PKGS}", "", d)} \
"

RDEPENDS:${PN}:append:k3 = " ${OPTEE_PKGS}"
