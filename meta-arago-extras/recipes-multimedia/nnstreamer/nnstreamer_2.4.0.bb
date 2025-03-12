SUMMARY = "NNStreamer, Stream Pipeline Paradigm for Nerual Network Applications"
DESCRIPTION = "NNStreamer is a set of Gstreamer plugins that allow Gstreamer developers to adopt neural network models easily and efficiently."
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c25e5c1949624d71896127788f1ba590"

SRC_URI = "git://github.com/nnstreamer/nnstreamer.git;branch=main;protocol=https"
PV = "2.4.0+git"
SRCREV = "7c57c288250578cad598f065b6fda6c0ff720cf9"

S = "${WORKDIR}/git"

# Only compatible with armv7a, armv7ve, and aarch64
COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"
COMPATIBLE_MACHINE:armv7ve = "${@bb.utils.contains("TUNE_FEATURES","neon","(.*)","(^$)",d)}"

inherit meson pkgconfig

DEPENDS = " \
	orc-native \
	glib-2.0 \
	gstreamer1.0 \
	gstreamer1.0-plugins-base \
	python3 \
	python3-numpy \
	json-glib \
"

PACKAGECONFIG ??= " \
	protobuf \
	flatbuffers \
	tests \
	onnxruntime \
	tensorflow-lite \
"

PACKAGECONFIG[tests] = "-Denable-test=true -Dinstall-test=true, -Denable-test=false, gtest"
PACKAGECONFIG[protobuf] = "-Dprotobuf-support=enabled, -Dprotobuf-support=disabled, protobuf protobuf-native"
PACKAGECONFIG[flatbuffers] = "-Dflatbuf-support=enabled, -Dflatbuf-support=disabled, flatbuffers flatbuffers-native"
PACKAGECONFIG[armnn] = "-Darmnn-support=enabled, -Darmnn-support=disabled, armnn"
PACKAGECONFIG[onnxruntime] = "-Donnxruntime-support=enabled, -Donnxruntime-support=disabled, onnxruntime"
PACKAGECONFIG[tensorflow-lite] = "-Dtflite2-support=enabled, -Dtflite2-support=disabled, tensorflow-lite"

do_install:append() {
	CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

	# Install test data
	install -d ${D}/${bindir}/unittest-nnstreamer/tests
	cp $CP_ARGS ${S}/tests/test_models ${D}/${bindir}/unittest-nnstreamer/tests
	rm -f ${D}/${bindir}/unittest-nnstreamer/tests/test_models/models/tvm*
}

FILES:${PN} += "\
	${libdir}/*.so \
	${libdir}/gstreamer-1.0/*.so \
	${libdir}/nnstreamer/* \
	${sysconfdir}/nnstreamer.ini \
"
INSANE_SKIP:${PN} += "dev-so"

PACKAGES =+ "${PN}-tests"

FILES:${PN}-tests += "\
	${libdir}/nnstreamer/customfilters/* \
	${bindir}/unittest-nnstreamer/* \
"
INSANE_SKIP:${PN}-tests += "buildpaths"

FILES:${PN}-dev = "\
	${includedir}/nnstreamer/* \
	${libdir}/pkgconfig/*.pc \
"
