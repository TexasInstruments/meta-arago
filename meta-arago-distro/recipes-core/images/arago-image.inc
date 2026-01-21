# Common pieces for Arago images

LICENSE = "MIT"

COMPATIBLE_MACHINE = "ti-soc"

IMAGE_INSTALL = " \
	packagegroup-core-boot \
	${CORE_IMAGE_EXTRA_INSTALL} \
"

export IMAGE_BASENAME = "arago-image${ARAGO_IMAGE_SUFFIX}"

IMAGE_LINGUAS = ""

TOOLCHAIN_HOST_TASK:append = " nativesdk-buildtools-perl-dummy"
SDK_PACKAGE_ARCHS += "buildtools-dummy-${SDKPKGSUFFIX}"

BAD_RECOMMENDATIONS += " \
    libulm-dev \
    gdbserver-c6x-dev \
    coreutils \
    coreutils-dev \
"
BAD_RECOMMENDATIONS += "${@oe.utils.conditional("INIT_MANAGER", "sysvinit", "", "busybox-syslog", d)}"

inherit core-image remove-net-rules
