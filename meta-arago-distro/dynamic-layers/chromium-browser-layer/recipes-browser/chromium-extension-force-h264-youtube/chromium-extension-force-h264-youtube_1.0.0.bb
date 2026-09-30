SUMMARY = "Chromium extension that forces YouTube to use H.264 codec"
LICENSE = "CLOSED"

python () {
    if not bb.utils.contains("MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS", "cnm-wave-fw", True, False, d):
        raise bb.parse.SkipRecipe("cnm-wave-fw not in MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS")
}

RDEPENDS:${PN} = "chromium-ozone-wayland"

SRC_URI = "file://force_h264.js \
           file://manifest.json \
           "

EXTENSION_DIR = "${libdir}/chromium/extensions/arago/force-h264-youtube"

do_install() {
    install -d ${D}${EXTENSION_DIR}
    install -m 0644 ${WORKDIR}/force_h264.js \
                    ${WORKDIR}/manifest.json \
                    ${D}${EXTENSION_DIR}/
}

FILES:${PN} = "${EXTENSION_DIR}"
