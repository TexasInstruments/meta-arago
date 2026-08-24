# This include is for the common setting used to build Arago SDK images.  These images
# include both host and target side content. 
LICENSE = "MIT"

IMAGE_FSTYPES = "tar.xz"
IMAGE_NAME_SUFFIX = ""

inherit tisdk-bundle

COMPATIBLE_MACHINE = "omapl138|ti33x|ti43x|am57xx|k3"

# List of packages to remove from the host package.  This is usually due to
# licensing issues and unneeded dependencies.
HOST_CLEANUP_PACKAGES = ""
TOOLCHAIN_CLEANUP_PACKAGES = "libgnutls-dev libgnutls-extra26 libgnutls-openssl27 libtasn1-dev"

# Set DTB filters for each machine.  Use "unknown" by default to avoid
# picking up DTB files for devices with no DTB support.
DTB_FILTER = "unknown"
DTB_FILTER:ti33x = "am335x"
DTB_FILTER:am57xx = "am57xx"
DTB_FILTER:ti43x = "am43"
DTB_FILTER:omapl138 = "da850"
DTB_FILTER:am65xx = "k3-am654"
DTB_FILTER:j721e = "j721e\|fpdlink"
DTB_FILTER:j7200 = "j7200"
DTB_FILTER:j721s2 = "j721s2\|am68\|fpdlink\|k3-v3link"
DTB_FILTER:j784s4 = "j784s4\|am69\|fpdlink\|k3-v3link"
DTB_FILTER:j722s = "j722s\|fpdlink\|k3-v3link"
DTB_FILTER:am62xx-evm = "k3-am625"
DTB_FILTER:am62lxx = "k3-am62l"
DTB_FILTER:am62xx-lp-evm = "k3-am62-lp\|k3-am625-sk"
DTB_FILTER:am62xxsip-evm = "k3-am625"
DTB_FILTER:am64xx = "k3-am642"
DTB_FILTER:am62pxx-evm = "k3-am62p5"
DTB_FILTER:am62axx = "k3-am62a7\|k3-am62x-sk\|k3-fpdlink\|k3-v3link"
DTB_FILTER:am62dxx = "k3-am62d2\|k3-am62x-sk\|k3-am62a7-sk"

# K3 SoC system firmware configuration
# Maps machine types to their firmware naming variants for prebuilt-images
SYSFW_SOC ?= "*"
SYSFW_SOC:j721e = "j721e"
SYSFW_SOC:j7200 = "j7200"
SYSFW_SOC:j721s2 = "j721s2"
SYSFW_SOC:j784s4 = "j784s4"
SYSFW_SOC:j722s = "j722s"
SYSFW_SOC:am62axx = "am62ax"
SYSFW_SOC:am62xx = "am62x"
SYSFW_SOC:am62lxx = "am62lx"
SYSFW_SOC:am64xx = "am64x_sr2 am64x-gp"
SYSFW_SOC:am62pxx = "am62px"
SYSFW_SOC:am65xx = "am65x_sr2"
SYSFW_SOC:am62dxx = "am62ax"

SYSFW_PREFIX ?= "fs"
SYSFW_PREFIX:am64xx = "sci"
SYSFW_PREFIX:am65xx = "sci"
SYSFW_PREFIX:am62xx = "fs*"
SYSFW_PREFIX:am62pxx = "fs*"
SYSFW_PREFIX:am62axx = "fs*"
SYSFW_PREFIX:am62dxx = "fs*"

SYSFW_BINARY = "${@' '.join(['ti-' + d.getVar('SYSFW_PREFIX') + '-firmware-' + x + '*.bin' for x in d.getVar('SYSFW_SOC').split()])}"

# List of target side images to build for the SDK
TARGET_IMAGES = "arago-base-image arago-default-image arago-thinlinux-image arago-bootstrap-image"

TISDK_TOOLCHAIN = "meta-toolchain-arago-tisdk"
TOOLCHAIN_SUFFIX = "-tisdk"
