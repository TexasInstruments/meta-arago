PR:append = ".arago3"

# meta-ti-bsp packages newer versions on these
do_install:append() {
	rm -rf  ${D}${nonarch_base_libdir}/firmware/ti-connectivity/
	rm -rf  ${D}${nonarch_base_libdir}/firmware/cadence/
}
