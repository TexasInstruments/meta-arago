DESCRIPTION = "ONNX Runtime is a cross-platform, high performance machine learning inferencing framework"
SUMMARY = "ONNX Runtime Python package & C++ library"
HOMEPAGE = "https://www.onnxruntime.ai/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f7e3b1308cb5c00b372a6e78835732d"

SRC_URI = "\
	git://github.com/microsoft/onnxruntime.git;protocol=https;branch=rel-1.23.2 \
	git://github.com/HowardHinnant/date.git;protocol=https;branch=master;name=date;destsuffix=${S}/_deps/date-src \
	git://github.com/pytorch/cpuinfo.git;protocol=https;branch=main;name=cpuinfo;destsuffix=${S}/_deps/pytorch_cpuinfo-src \
	git://github.com/google/flatbuffers.git;protocol=https;branch=master;name=flatbuffers;destsuffix=${S}/_deps/flatbuffers-src \
	git://github.com/dcleblanc/SafeInt.git;protocol=https;branch=master;name=safeint;destsuffix=${S}/_deps/safeint-src \
	git://github.com/eigen-mirror/eigen.git;protocol=https;branch=master;name=eigen;destsuffix=${S}/_deps/eigen3-src \
	git://github.com/boostorg/mp11.git;protocol=https;branch=master;name=mp11;destsuffix=${S}/_deps/mp11-src \
	git://github.com/dmlc/dlpack.git;protocol=https;branch=main;name=dlpack;destsuffix=${S}/_deps/dlpack-src \
	git://github.com/abseil/abseil-cpp.git;protocol=https;branch=lts_2025_05_12;name=abseil-cpp;destsuffix=${S}/_deps/abseil_cpp-src \
	git://github.com/google/re2.git;protocol=https;branch=main;name=re2;destsuffix=${S}/_deps/re2-src \
"
SRC_URI += "\
	file://0001-Added-the-missing-header-to-fix-uint32_t-compilation.patch \
"

SRCREV_FORMAT = "default"
SRCREV = "a83fc4d58cb48eb68890dd689f94f28288cf2278"
SRCREV_date = "6e921e1b1d21e84a5c82416ba7ecd98e33a436d0"
SRCREV_cpuinfo = "8a1772a0c5c447df2d18edf33ec4603a8c9c04a6"
SRCREV_flatbuffers = "0100f6a5779831fa7a651e4b67ef389a8752bd9b"
SRCREV_safeint = "4cafc9196c4da9c817992b20f5253ef967685bf8"
SRCREV_eigen = "1d8b82b0740839c0de7f1242a3585e3390ff5f33"
SRCREV_mp11 = "0a0b5fb001ce0233ae3a6f99d849c0649e5a7361"
SRCREV_dlpack = "5c210da409e7f1e51ddf445134a4376fdbd70d7d"
SRCREV_abseil-cpp = "bc257a88f7c1939f24e0379f14a3589e926c950c"
SRCREV_re2 = "6dcd83d60f7944926bfd308cc13979fc53dd69ca"

# Only compatible with armv7a, armv7ve, and aarch64
COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"
COMPATIBLE_MACHINE:armv7ve = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"

DEPENDS += "\
	onnx \
	protobuf \
	protobuf-native \
	boost \
	nlohmann-json \
	microsoft-gsl \
"

PYTHON_DEPENDS = "\
	python3 \
	python3-numpy \
	python3-numpy-native \
	python3-pybind11 \
	python3-pybind11-native \
"

PYTHON_RDEPENDS = "\
	python3 \
	python3-onnx \
	python3-numpy \
	python3-protobuf \
	python3-coloredlogs \
	python3-flatbuffers \
	python3-sympy \
"

OECMAKE_SOURCEPATH = "${S}/cmake"

PACKAGECONFIG ??= "python sharedlib unittests acl"

PACKAGECONFIG[python] = "-Donnxruntime_ENABLE_PYTHON=ON, -Donnxruntime_ENABLE_PYTHON=OFF, ${PYTHON_DEPENDS}"
PACKAGECONFIG[sharedlib] = "-Donnxruntime_BUILD_SHARED_LIB=ON, -Donnxruntime_BUILD_SHARED_LIB=OFF"
PACKAGECONFIG[unittests] = "-Donnxruntime_BUILD_UNIT_TESTS=ON, -Donnxruntime_BUILD_UNIT_TESTS=OFF, googletest"

PACKAGECONFIG[acl] = "-Donnxruntime_USE_ACL=ON, -Donnxruntime_USE_ACL=OFF, arm-compute-library"
PACKAGECONFIG[armnn] = "-Donnxruntime_USE_ARMNN=ON, -Donnxruntime_USE_ARMNN=OFF, armnn"
PACKAGECONFIG[armnn-relu] = "-Donnxruntime_ARMNN_RELU_USE_CPU=ON, -Donnxruntime_ARMNN_RELU_USE_CPU=OFF"
PACKAGECONFIG[armnn-bn] = "-Donnxruntime_ARMNN_BN_USE_CPU=ON, -Donnxruntime_ARMNN_BN_USE_CPU=OFF"

EXTRA_OECMAKE:append = " \
	-DFETCHCONTENT_FULLY_DISCONNECTED=ON \
	-DFETCHCONTENT_SOURCE_DIR_DATE=${S}/_deps/date-src \
	-DFETCHCONTENT_SOURCE_DIR_PYTORCH_CPUINFO=${S}/_deps/pytorch_cpuinfo-src \
	-DFETCHCONTENT_SOURCE_DIR_PYTORCH_CLOG=${S}/_deps/pytorch_cpuinfo-src \
	-DFETCHCONTENT_SOURCE_DIR_FLATBUFFERS=${S}/_deps/flatbuffers-src \
	-DFETCHCONTENT_SOURCE_DIR_SAFEINT=${S}/_deps/safeint-src \
	-DFETCHCONTENT_SOURCE_DIR_EIGEN3=${S}/_deps/eigen3-src \
	-DFETCHCONTENT_SOURCE_DIR_ABSEIL_CPP=${S}/_deps/abseil_cpp-src \
	-DFETCHCONTENT_SOURCE_DIR_MP11=${S}/_deps/mp11-src \
	-DFETCHCONTENT_SOURCE_DIR_DLPACK=${S}/_deps/dlpack-src \
	-DFETCHCONTENT_SOURCE_DIR_RE2=${S}/_deps/re2-src \
"

EXTRA_OECMAKE:append = " \
	-DONNX_CUSTOM_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
	-Donnx_SOURCE_DIR=${RECIPE_SYSROOT}${PYTHON_SITEPACKAGES_DIR} \
	-DPython_INCLUDE_DIR=${STAGING_INCDIR}/${PYTHON_DIR}${PYTHON_ABI} \
	--compile-no-warning-as-error \
	--log-level=VERBOSE \
"

inherit ${@bb.utils.contains('PACKAGECONFIG', 'python', 'python3native', '', d)}
inherit python3-dir cmake

do_install:append() {
	CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

	# Install test binaries and data in test package
	install -d ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/libcustom_op_library.so ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/libcustom_op_get_const_input_test_library.so ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/libcustom_op_local_function.so ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/libcustom_op_invalid_library.so ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/libtest_execution_provider.so ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_customopregistration_test ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_global_thread_pools_test ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_logging_apis_test ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_mlas_test ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_perf_test ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_shared_lib_test ${D}${bindir}/${BPN}-tests
	install -m 0755 ${B}/onnxruntime_test_all ${D}${bindir}/${BPN}-tests
	cp $CP_ARGS ${B}/testdata ${D}${bindir}/${BPN}-tests

	# Install python tests and data
	cp $CP_ARGS ${S}/onnxruntime/test/python/* ${D}${bindir}/${BPN}-tests

	# Install the Python package.
	if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
		install -d ${D}${PYTHON_SITEPACKAGES_DIR}
		cp $CP_ARGS ${B}/onnxruntime ${D}${PYTHON_SITEPACKAGES_DIR}
		find ${D}${PYTHON_SITEPACKAGES_DIR} -name "libonnx*.so*" -exec rm {} \;
	fi
}

# Add Python package
PACKAGE_BEFORE_PN += "python3-${PN}"
FILES:python3-${PN} += "${PYTHON_SITEPACKAGES_DIR}"
RDEPENDS:python3-${PN} += "${PYTHON_RDEPENDS} onnxruntime"

# Add Python tests package
PACKAGE_BEFORE_PN += "python3-${PN}-tests"
FILES:python3-${PN}-tests += "\
	${bindir}/${BPN}-tests/*.py \
	${bindir}/${BPN}-tests/contrib_ops \
	${bindir}/${BPN}-tests/quantization \
	${bindir}/${BPN}-tests/transformers \
"
RDEPENDS:python3-${PN}-tests += "python3-${PN}"

# Add tests package
PACKAGE_BEFORE_PN += "${PN}-tests"
FILES:${PN}-tests = "${bindir}/${BPN}-tests"
INSANE_SKIP:${PN}-tests += "libdir"

# package unversioned .so files in PN (they are not dev symlinks)
FILES_SOLIBSDEV = "${libdir}/libonnxruntime.so"
FILES:${PN} += "${libdir}/libonnxruntime_providers_shared.so"

INSANE_SKIP:${PN}-dbg += "libdir"
