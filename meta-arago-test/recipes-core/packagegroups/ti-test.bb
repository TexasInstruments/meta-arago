SUMMARY = "TI Testing packagegroup"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

TI_TEST_BASE = "\
    alsa-utils \
    bc \
    bonnie++ \
    bridge-utils \
    cryptodev-tests \
    dma-heap-tests \
    dosfstools \
    ethtool \
    evtest \
    fio \
    git \
    hwspinlocktest \
    hdparm \
    i2c-tools \
    iozone3 \
    iproute2-tc \
    iproute2-devlink \
    iperf3 \
    kernel-modules \
    kernel-selftest \
    libdrm-tests \
    linuxptp \
    lmbench \
    lsof \
    memtester \
    mstpd \
    mtd-utils \
    mtd-utils-ubifs \
    mtd-utils-ubifs-tests \
    nbench-byte \
    netperf \
    openntpd \
    pcitest \
    perf \
    powertop \
    procps \
    pulseaudio-misc \
    rt-tests \
    rwmem \
    smcroute \
    stream \
    stress \
    stress-ng \
    tcpdump \
    v4l-utils \
    yavta \
"

TI_TEST_EXTRAS = " \
    piglit \
    python3-numpy \
"

TI_TEST_BASE:append:armv7a = " \
    cpuburn-neon \
"

TI_TEST_BASE:append:armv7ve = " \
    cpuburn-neon \
"

#    timestamping
TI_TEST_TI_TOOLS = " \
    arm-benchmarks \
    cpuloadgen \
    input-utils \
    ltp-ddt \
    uvc-gadget \
"

TI_TEST_TI_TOOLS:append:ti33x = " \
    omapconf \
    pru-icss \
    switch-config \
"

TI_TEST_TI_TOOLS:append:ti43x = " \
    omapconf \
    pru-icss \
    switch-config \
"

NOT_MAINLINE_MMIP_DEPS = "${@bb.utils.contains('MACHINE_FEATURES', 'mmip', 'omapdrmtest', '', d)}"

TI_TEST_TI_TOOLS:append:omap-a15 = " \
    omapconf \
    pru-icss \
    switch-config \
    ${@oe.utils.conditional('ARAGO_BRAND', 'mainline', '', " \
        ti-ipc-test \
	    ${NOT_MAINLINE_MMIP_DEPS} \
    ", d)} \
"

TI_TEST_TI_TOOLS:append:k3 = " \
    k3conf \
    switch-config \
    ti-rtos-firmware \
    ti-rpmsg-char \
    ti-rpmsg-char-examples \
"

TI_TEST_TI_TOOLS:append:am62xx   = " \
    pru-icss \
"

TI_TEST_TI_TOOLS:append:am64xx   = " \
    pru-icss \
"

TI_TEST_TI_TOOLS:append:am65xx   = " \
    pru-icss \
"

# Disable due to breakage
#    viddec-test-app 
TI_TEST_TI_TOOLS:append:j721e = " \
    pru-icss \
    ufs-utils \
    videnc-test-app \
"

TI_TEST_TI_TOOLS:append:j784s4 = " \
    ufs-utils \
"

TI_TEST_TI_TOOLS:append:omapl138 = " \
    ${@oe.utils.conditional('ARAGO_BRAND', 'mainline', '', 'ti-ipc-test', d)} \
"

RDEPENDS:${PN} = "\
    ${TI_TEST_BASE} \
    ${TI_TEST_TI_TOOLS} \
"

# Package group for things that should only be present in tisdk-default-image
PACKAGES += " ${PN}-extras"
RDEPENDS:${PN}-extras = "\
    ${TI_TEST_EXTRAS} \
"
