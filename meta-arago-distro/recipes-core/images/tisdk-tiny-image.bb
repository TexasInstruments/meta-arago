SUMMARY = "Arago TI SDK super minimal base image for initramfs"

DESCRIPTION = "Image meant for basic boot of linux kernel. Intended as\
 bare system, this image does not package the kernel in the\
 standard /boot folder in rootfs. Instead, it provides a base\
 rootfs allowing kernel to be deployed elsewhere\
 (tftp/separate boot partition/jtag log etc..) and boot\
 the image.\
"

ARAGO_TINY_IMAGE_EXTRA_INSTALL ?= ""

require arago-tiny-image.inc

IMAGE_FEATURES:remove = "package-management"

IMAGE_INSTALL += " \
	${ARAGO_TINY_IMAGE_EXTRA_INSTALL} \
"

DEPENDS += " tisdk-tiny-initramfs"

export IMAGE_BASENAME = "tisdk-tiny-image"
