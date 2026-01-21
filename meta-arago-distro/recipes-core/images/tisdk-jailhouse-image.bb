SUMMARY = "Arago TI SDK image for Jailhouse Hypervisor"

DESCRIPTION = "Arago TI SDK image meant for running Jailhouse, a partitioning \
Hypervisor based on Linux. This image is derived from tisdk-default-image and \
contains additional firmware and management tools for Jailhouse.\
"

require recipes-core/images/tisdk-default-image.bb

COMPATIBLE_MACHINE = "am62xx|am62pxx|am62lxx"

IMAGE_INSTALL += " jailhouse"
