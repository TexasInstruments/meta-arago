SUMMARY = "The ARM Computer Vision and Machine Learning library"
DESCRIPTION = "The ARM Computer Vision and Machine Learning library is a set of functions optimised for both ARM CPUs and GPUs."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSES/MIT.txt;md5=3912d958d00bac4a6b550f75d7c806bb"

SRC_URI = " \
    git://github.com/ARM-software/ComputeLibrary.git;branch=releases/arm_compute_52_7_0;protocol=https \
    file://0001-Use-ARM_COMPUTE_ENABLE_NEON-in-code-for-checking-NEO.patch \
    file://0002-Use-ARM_COMPUTE_ENABLE_SVE-in-code-for-checking-SVE.patch \
    file://0003-Add-source-files-at-library-definition-time.patch \
    file://0004-Allow-ARMv7-builds-using-CMake.patch \
    file://0005-Fix-undefined-symbol-error-when-building-TensorInfo.patch \
    file://0006-Remove-TARGET-dependency.patch \
    file://0007-cmake-Generate-generic-library-name-instead-of.patch \
    file://0008-Add-FP16-source-path.patch \
"
SRCREV = "c9a1fff898abd5109b759e8e16616519dc758fdd"

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
	-DACL_MULTI_ISA=OFF \
	-DACL_ARCH_ISA=armv8-a \
	-DACL_BUILD_SVE=OFF \
	-DACL_BUILD_SVE2=OFF \
	-DCMAKE_BUILD_TYPE=Release \
	-DARM_COMPUTE_ENABLE_BF16=OFF \
	-DARM_COMPUTE_ENABLE_FP16=OFF \
	-DARM_COMPUTE_ENABLE_I8MM=OFF \
	-DARM_COMPUTE_ASSERTS_ENABLED=ON \
	-DARM_COMPUTE_ENABLE_FIXED_FORMAT_KERNELS=OFF \
"
EXTRA_OECMAKE:append:arm = " \
	-DACL_MULTI_ISA=OFF \
	-DACL_ARCH_ISA=armv7 \
	-DACL_BUILD_SVE=OFF \
	-DACL_BUILD_SVE2=OFF \
	-DCMAKE_BUILD_TYPE=Release \
	-DARM_COMPUTE_ENABLE_BF16=OFF \
	-DARM_COMPUTE_ENABLE_FP16=OFF \
	-DARM_COMPUTE_ASSERTS_ENABLED=ON \
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

	if ${@bb.utils.contains('PACKAGECONFIG', 'tests', 'true', 'false', d)}; then
		install -d ${D}${bindir}/${PN}-${PV}/tests
		# Copy the validation and benchmark binaries from the build directory
		install -m 0555 ${B}/arm_compute_validation ${D}${bindir}/${PN}-${PV}/tests
		install -m 0555 ${B}/arm_compute_benchmark ${D}${bindir}/${PN}-${PV}/tests
	fi
}

PACKAGES =+ "${PN}-tests ${PN}-examples"
FILES:${PN}-tests += "${bindir}/*/tests"
FILES:${PN}-examples += "${bindir}/*/examples"
