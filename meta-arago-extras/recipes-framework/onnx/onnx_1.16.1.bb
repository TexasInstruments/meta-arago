DESCRIPTION = "Open standard for machine learning interoperability"
SUMMARY = "ONNX Python package & C++ library"
HOMEPAGE = "https://www.onnx.ai/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://github.com/onnx/onnx.git;protocol=https;branch=rel-1.16.1"
SRC_URI += "\
	file://0001-Try-to-find-package-Protobuf-before-checking-if-it-w.patch \
	file://0002-Do-not-re-export-internal-targets-directories.patch \
"
SRCREV = "595228d99e3977ac27cb79d5963adda262af99ad"

S = "${WORKDIR}/git"

DEPENDS += "\
	abseil-cpp \
	protobuf \
	protobuf-native \
	python3-protobuf \
	python3-protobuf-native \
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
	python3-numpy \
	python3-protobuf \
	python3-coloredlogs \
	python3-flatbuffers \
	python3-sympy \
"

PACKAGECONFIG ??= "python sharedlib unittests"

PACKAGECONFIG[python] = "\
	-DBUILD_ONNX_PYTHON=ON \
	-DPYTHON_LIBRARY=${PYTHON_LIBRARY} \
	-DPYTHON_INCLUDE_DIRS=${PYTHON_INCLUDE_DIR}, \
	-DBUILD_ONNX_PYTHON=OFF, \
	${PYTHON_DEPENDS} \
"

PACKAGECONFIG[sharedlib] = "-DBUILD_SHARED_LIBS=ON, -D-DBUILD_SHARED_LIBS=OFF"
PACKAGECONFIG[unittests] = "-DONNX_BUILD_TESTS=ON, -DONNX_BUILD_TESTS=OFF, googletest"

EXTRA_OECMAKE:append = " \
	-DONNX_CUSTOM_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
	-DONNX_USE_PROTOBUF_SHARED_LIBS=ON \
	-DONNX_DISABLE_STATIC_REGISTRATION=ON \
	-DONNX_ML=1 \
	-DCMAKE_VERBOSE_MAKEFILE=ON \
	--log-level=VERBOSE \
"

inherit python3native cmake

python do_build_version_file() {
    import os
    import textwrap

    version_file_path = os.path.join(d.getVar('B'), "onnx", "version.py")
    os.makedirs(os.path.dirname(version_file_path), exist_ok=True)
    with open(version_file_path, "w", encoding="utf-8") as f:
        f.write(
            textwrap.dedent(
                f"""\
                # This file is generated. DO NOT EDIT!


                version = "{d.getVar('PV')}"
                git_version = "{d.getVar('SRCREV')}"
                """
            )
        )
}
addtask build_version_file before do_install after do_compile

do_install:append() {
	CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

	# Install the Python package.
	if ${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}; then
		install -d ${D}${PYTHON_SITEPACKAGES_DIR}/onnx
		(cd ${S}/onnx; cp --parents $(find . -name "*.py*") ${D}${PYTHON_SITEPACKAGES_DIR}/onnx)
		cp $CP_ARGS ${B}/onnx ${D}${PYTHON_SITEPACKAGES_DIR}
		cp $CP_ARGS ${B}/onnx_cpp2py_export.so ${D}${PYTHON_SITEPACKAGES_DIR}/onnx
	fi
}

# Add Python package
PACKAGE_BEFORE_PN += "python3-${PN}"
FILES:python3-${PN} += "${PYTHON_SITEPACKAGES_DIR}"
RDEPENDS:python3-${PN} += "${PYTHON_RDEPENDS} onnx"

# Output library is unversioned
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
