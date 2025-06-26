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
    devmem2 \
    dma-heap-tests \
    dosfstools \
    ethtool \
    evtest \
    fio \
    git \
    gst-devtools \
    hwspinlocktest \
    hdparm \
    i2c-tools \
    iozone3 \
    iproute2-tc \
    iproute2-devlink \
    iperf3 \
    kernel-modules \
    kernel-selftest \
    kmsxx \
    kmsxx-python \
    libdrm-tests \
    linuxptp \
    lmbench \
    lsof \
    media-ctl \
    memtester \
    mstpd \
    mtd-utils \
    mtd-utils-ubifs \
    nbench-byte \
    netperf \
    openntpd \
    pcitest \
    perf \
    powertop \
    procps \
    pulseaudio-misc \
    rng-tools \
    rt-tests \
    rwmem \
    smcroute \
    stream \
    stress \
    stress-ng \
    tcpdump \
    v4l-utils \
    xdp-tools-arago \
    yavta \
"

TI_TEST_BASE:append:ti-soc = " \
    mtd-utils-ubifs-tests \
"

TI_TEST_EXTRAS = " \
    python3-pillow \
    pytesseract \
    piglit \
    python3-numpy \
    wayland-utils \
    opencl-cts \
"

TI_TEST_BASE:append:armv7a = " \
    cpuburn-neon \
"

TI_TEST_BASE:append:armv7ve = " \
    cpuburn-neon \
"

TI_TEST_TI_TOOLS = " \
    arm-benchmarks \
    arm-ddr-bandwidth \
    cpuloadgen \
    input-utils \
    ltp-ddt \
    openssl-perf \
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

TI_TEST_TI_TOOLS:append:omap-a15 = " \
    omapconf \
    pru-icss \
    switch-config \
    ${@oe.utils.conditional('ARAGO_BRAND', 'mainline', '', " \
        ti-ipc-test \
    ", d)} \
"

TI_TEST_TI_TOOLS:append:k3 = " \
    k3conf \
    switch-config \
    ti-rtos-echo-test-fw \
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

TI_TEST_TI_TOOLS:append:j742s2 = " \
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
