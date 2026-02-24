SUMMARY = "Task to install additional utilities for initial board bringup"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

UTILS = " \
	 arm-benchmarks \
	 evtest \
	 memtester \
	 nbench-byte \
	 pcitest \
	 serialcheck \
	 yavta \
	 hdparm \
	 i2c-tools \
	 libdrm-tests \
	 rt-tests \
	 iozone3 \
	 net-tools \
	 ethtool  \
	 pciutils \
	 stress-ng \
	 iperf3 \
	 lmbench \
	 usbutils \
	 devmem2 \
	 phytool \
"

UTILS:append:ti-soc = " \
    mtd-utils-ubifs-tests \
"

UTILS:append:ti33x = " \
    omapconf \
"

UTILS:append:ti43x = " \
    omapconf \
"

UTILS:append:am57xx = " \
    omapconf \
"

UTILS:append:k3 = " \
    k3conf \
"

RDEPENDS:${PN} = "\
    ${UTILS} \
"
