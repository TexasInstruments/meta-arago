SUMMARY = "Arago super minimal base image for jailhouse linux demo"

DESCRIPTION = "Image meant for basic boot of linux inmate for jailhouse\
This image is derived from arago-tiny-initramfs and contains additional\
packages for OOB demo.\
"

require recipes-core/images/arago-tiny-initramfs.bb

COMPATIBLE_MACHINE = "am62xx|am62pxx|am62lxx"

IMAGE_FSTYPES += "cpio"

PACKAGE_INSTALL += "jailhouse-inmate"
