PACKAGECONFIG:append = " qt6"

# Qt tools like rcc, moc and uic are located in /usr/libexec, instead of
# /usr/bin, which is not in PATH by default.
do_configure:prepend:class-target() {
	export PATH=${STAGING_DIR_NATIVE}${libexecdir}:$PATH
}
