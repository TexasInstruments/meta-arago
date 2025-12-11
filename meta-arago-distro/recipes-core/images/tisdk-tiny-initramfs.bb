SUMMARY = "Arago TI SDK super minimal base image for initramfs"

DESCRIPTION = "Image meant for basic boot of linux kernel. Intended as\
 bare system, this image does not package the kernel in the\
 standard /boot folder in rootfs. Instead, it provides a base\
 rootfs allowing kernel to be deployed elsewhere\
 (tftp/separate boot partition/jtag log etc..) and boot\
 the image.\
"

LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES:remove = "package-management"

INITRAMFS_FSTYPES = "cpio cpio.xz"

#INITRAMFS_MAXSIZE = "65536"
IMAGE_OVERHEAD_FACTOR = "1.1"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

PACKAGE_INSTALL = "packagegroup-arago-initramfs"

export IMAGE_BASENAME = "tisdk-tiny-initramfs${ARAGO_IMAGE_SUFFIX}"

# To further reduce the size of the rootfs, remove the /boot directory from
# the final image this is usually done by adding RDEPENDS_kernel-base = ""
# in the configuration file. In our case we can't use this method. Instead we
# just wipe out the content of "/boot" before creating the image.
ROOTFS_POSTPROCESS_COMMAND += "empty_boot_dir; "
empty_boot_dir () {
	rm -rf ${IMAGE_ROOTFS}/boot/*
}
