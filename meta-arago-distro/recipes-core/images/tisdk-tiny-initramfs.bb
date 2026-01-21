SUMMARY = "Arago TI SDK super minimal base image for initramfs"

DESCRIPTION = "Image meant for basic boot of linux kernel. Intended as\
 bare system, this image does not package the kernel in the\
 standard /boot folder in rootfs. Instead, it provides a base\
 rootfs allowing kernel to be deployed elsewhere\
 (tftp/separate boot partition/jtag log etc..) and boot\
 the image.\
"

LICENSE = "MIT"

INITRAMFS_FSTYPES = "cpio cpio.xz"

INITRAMFS_MAXSIZE = "65536"

INITRAMFS_SCRIPTS ?= "initramfs-framework-base initramfs-module-udev"

PACKAGE_INSTALL = "${INITRAMFS_SCRIPTS} ${VIRTUAL-RUNTIME_base-utils} base-passwd"

# Ensure the initramfs only contains the bare minimum
IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""

# Don't allow the initramfs to contain a kernel, as kernel modules will depend
# on the kernel image.
PACKAGE_EXCLUDE = "kernel-image-*"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
IMAGE_NAME_SUFFIX ?= ""
IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

inherit image
