DESCRIPTION = "Target packages for the standalone SDK"
PR = "r13"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    libgcc \
    libgcc-dev \
    libstdc++-dev \
    libgomp-dev \
    ${LIBC_DEPENDENCIES} \
    libc-staticdev \
    linux-libc-headers-dev \
    gdbserver \
    alsa-dev \
    alsa-lib-dev \
    alsa-utils-dev \
    curl-dev \
    i2c-tools-dev \
    freetype-dev \
    libjpeg-turbo-dev  \
    lzo-dev \
    opkg-dev \
    libpng-dev \
    readline-dev \
    libusb-compat-dev \
    libusb1-dev \
    zlib-dev \
    ncurses-dev \
    opkg-dev \
    util-linux-dev \
"
