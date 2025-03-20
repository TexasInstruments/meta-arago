PR:append = ".arago1"

PACKAGES =+ "${PN}-ibt-18"

LICENSE:${PN}-ibt-18 = "Firmware-ibt_firmware"

FILES:${PN}-ibt-18 = "${nonarch_base_libdir}/firmware/intel/ibt-18-*.sfi ${nonarch_base_libdir}/firmware/intel/ibt-18-*.ddc"

RDEPENDS:${PN}-ibt-18 += "${PN}-ibt-license"

do_install:append() {
	rm -rf  ${D}/lib/firmware/ti-connectivity/
	rm -rf  ${D}/lib/firmware/cadence/
}
