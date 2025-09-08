inherit cmake

SRCREV = "8275634cf9ec31b6484c2e6be756237cb583999d"
PV = "2024.05.08"

EXTRA_OECMAKE = "-DBUILD_TESTING=OFF"

python() {
    # Delete the noexec flags for the following tasks.
    # Ensuring the tasks run.
    d.delVarFlag("do_configure", "noexec")
    d.delVarFlag("do_compile", "noexec")
}

do_install() {
	cmake_do_install
}
