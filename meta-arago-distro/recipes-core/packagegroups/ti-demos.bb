SUMMARY = "TI World packagegroup"
LICENSE = "MIT"

inherit packagegroup

PDM_ANOMALY = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'pdm-anomaly-detection', '', d)}"

RDEPENDS:${PN} = "\
    ${PDM_ANOMALY} \
"
