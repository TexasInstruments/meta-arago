SUMMARY = "Task to add HMI related packages"
LICENSE = "MIT"
PR = "r0"

inherit packagegroup

RDEPENDS:${PN} = "\
    evse-hmi \
    protection-relays-hmi \
    mmwavegesture-hmi \
"
