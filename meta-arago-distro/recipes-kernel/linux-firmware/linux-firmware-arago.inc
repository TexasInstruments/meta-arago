PR:append = ".arago2"

do_install:append() {
	rm -rf  ${D}/lib/firmware/ti-connectivity/
	rm -rf  ${D}/lib/firmware/cadence/
}
