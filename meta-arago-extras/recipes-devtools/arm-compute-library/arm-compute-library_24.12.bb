SUMMARY = "The ARM Computer Vision and Machine Learning library"
DESCRIPTION = "The ARM Computer Vision and Machine Learning library is a set of functions optimised for both ARM CPUs and GPUs."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSES/MIT.txt;md5=35f8944fae972976691f3483b0ac9dba"

SRC_URI = " \
    git://github.com/ARM-software/ComputeLibrary.git;branch=main;protocol=https \
    file://0001-fix-Fix-indention-in-cmake-generator-script.patch \
    file://0002-Use-ARM_COMPUTE_ENABLE_NEON-in-code-for-checking-NEO.patch \
    file://0003-Use-ARM_COMPUTE_ENABLE_SVE-in-code-for-checking-SVE-.patch \
    file://0004-Add-source-files-at-library-definition-time.patch \
    file://0005-Add-CMake-options-for-SVE-SVE2-and-BF16-support.patch \
    file://0006-Allow-SVE-and-SVE2-support-to-be-disabled-in-CMake.patch \
    file://0007-Allow-ARMv7-builds-using-CMake.patch \
    file://0008-Fix-undefined-symbol-error-when-building-TensorInfo.patch \
"
SRCREV = "32bcced2af7feea6969dd1d22e58d0718dc488e3"

S = "${WORKDIR}/git"

# Only compatible with armv7a, armv7ve, and aarch64
COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"
COMPATIBLE_MACHINE:armv7ve = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"

inherit cmake

PACKAGECONFIG ??= "examples tests cppthreads openmp"

PACKAGECONFIG[Werror] = "-DARM_COMPUTE_WERROR=ON,-DARM_COMPUTE_WERROR=OFF"
PACKAGECONFIG[examples] = "-DARM_COMPUTE_BUILD_EXAMPLES=ON,-DARM_COMPUTE_BUILD_EXAMPLES=OFF"
PACKAGECONFIG[tests] = "-DARM_COMPUTE_BUILD_TESTING=ON,-DARM_COMPUTE_BUILD_TESTING=OFF"
PACKAGECONFIG[cppthreads] = "-DARM_COMPUTE_CPPTHREADS=ON,-DARM_COMPUTE_CPPTHREADS=OFF"
PACKAGECONFIG[openmp] = "-DARM_COMPUTE_OPENMP=ON,-DARM_COMPUTE_OPENMP=OFF"

EXTRA_OECMAKE:append:aarch64 = " \
	-DARM_COMPUTE_ARCH=armv8-a \
	-DENABLE_NEON=ON \
	-DENABLE_SVE=OFF \
	-DENABLE_SVE2=OFF \
"
EXTRA_OECMAKE:append:arm = " \
	-DARM_COMPUTE_ARCH=armv7-a \
	-DENABLE_NEON=ON \
	-DENABLE_SVE=OFF \
	-DENABLE_SVE2=OFF \
	-DARM_COMPUTE_ENABLE_BF16=OFF \
	-DARM_COMPUTE_ENABLE_FIXED_FORMAT_KERNELS=OFF \
"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# package unversioned .so files in PN (they are not dev symlinks)
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so"

# Install headers and examples
do_install:append() {
	CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

	# Install headers
	install -d ${D}${includedir}
	cp $CP_ARGS ${S}/arm_compute ${D}${includedir}
	cp $CP_ARGS ${S}/support ${D}${includedir}
	cp $CP_ARGS ${S}/include/half ${D}${includedir}

	# Latest ONNX Runtime uses some headers from the ACL source
	install -d ${D}${includedir}/src
	(cd ${S}/src; find -type f -name \*.h\* -exec install -D {} ${D}${includedir}/src/{} \;)

	# Install examples
	if ${@bb.utils.contains('PACKAGECONFIG', 'examples', 'true', 'false', d)}; then
		install -d ${D}${bindir}/${PN}-${PV}/examples
		for example in ${B}/examples/*; do
			if [ -d "$example" ]; then
				continue
			fi
			case "$example" in
				(*.o|*.a|*.cmake) continue;;
			esac
			install -m 0555 $example ${D}${bindir}/${PN}-${PV}/examples
		done
	fi
}

PACKAGES =+ "${PN}-tests ${PN}-examples"
FILES:${PN}-tests += "${libdir}/tests"
FILES:${PN}-examples += "${bindir}/*/examples"
