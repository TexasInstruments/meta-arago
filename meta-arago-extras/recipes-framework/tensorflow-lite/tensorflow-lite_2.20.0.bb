DESCRIPTION = "TensorFlow Lite is an open source deep learning framework for \
on-device inference."
AUTHOR = "Google Inc. and Yuan Tang"
SUMMARY = "TensorFlow Lite C++ Library, Python interpreter & Benchmark Model"
HOMEPAGE = "https://www.tensorflow.org/lite"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

# Get major of the PV variable
TF_MAJOR = "${@d.getVar('PV').split('.')[0]}"
TF_MINOR = "${@d.getVar('PV').split('.')[1]}"
TF_PATCH = "${@(d.getVar('PV').split('.') + ['0', '0'])[2]}"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;protocol=https;branch=r2.20 \
    file://0001-Update-CMakeLists-for-building.patch \
    file://0002-Update-CMakeLists-for-building-shared-object.patch \
    file://tensorflow2-lite.pc.in \
"

SRC_URI += " \
    git://github.com/abseil/abseil-cpp.git;protocol=https;branch=lts_2025_01_27;name=abseil-cpp;destsuffix=${S}/external/abseil-cpp \
    git://gitlab.com/libeigen/eigen.git;protocol=https;branch=master;name=eigen;destsuffix=${S}/external/eigen \
    git://github.com/google/farmhash.git;protocol=https;branch=master;name=farmhash;destsuffix=${S}/external/farmhash \
    git://github.com/petewarden/OouraFFT.git;protocol=https;branch=master;name=fft2d;destsuffix=${S}/external/fft2d \
    git://github.com/google/gemmlowp.git;branch=master;protocol=https;name=gemmlowp;destsuffix=${S}/external/gemmlowp \
    git://github.com/pytorch/cpuinfo.git;branch=main;protocol=https;name=cpuinfo;destsuffix=${S}/external/cpuinfo \
    git://github.com/jax-ml/ml_dtypes.git;branch=main;protocol=https;name=ml_dtypes;destsuffix=${S}/external/ml_dtypes \
    git://github.com/google/ruy.git;branch=master;protocol=https;name=ruy;destsuffix=${S}/external/ruy \
    git://github.com/google/flatbuffers;branch=master;protocol=https;name=flatbuffers;destsuffix=${S}/external/flatbuffers \
    git://github.com/google/pthreadpool.git;branch=main;protocol=https;name=pthreadpool-source;destsuffix=${S}/external/pthreadpool-source \
    git://github.com/google/XNNPACK.git;branch=master;protocol=https;name=xnnpack;destsuffix=${S}/external/xnnpack \
    git://github.com/Maratyszcza/FXdiv.git;branch=master;protocol=https;name=FXdiv-source;destsuffix=${S}/external/FXdiv-source \
    git://github.com/Maratyszcza/FP16.git;branch=master;protocol=https;name=FP16-source;destsuffix=${S}/external/FP16-source \
    git://git.gitlab.arm.com/kleidi/kleidiai.git;branch=main;protocol=https;name=kleidiai;destsuffix=${S}/external/kleidiai \
    git://github.com/Maratyszcza/psimd.git;branch=master;protocol=https;name=psimd;destsuffix=${S}/external/psimd \
    git://github.com/protocolbuffers/protobuf.git;branch=main;protocol=https;name=protobuf;destsuffix=${S}/external/protobuf \
"

SRCREV_FORMAT = "default"

# Matches v${PV} tag
SRCREV = "72fbba3d20f4616d7312b5e2b7f79daf6e82f2fa"
SRCREV_abseil-cpp = "d9e4955c65cd4367dd6bf46f4ccb8cd3d100540b"
SRCREV_eigen = "33d0937c6bdf5ec999939fb17f2a553183d14a74"
SRCREV_farmhash = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_fft2d = "c6fd2dd6d21397baa6653139d31d84540d5449a2"
SRCREV_gemmlowp = "16e8662c34917be0065110bfcd9cc27d30f52fdf"
SRCREV_cpuinfo = "de0ce7c7251372892e53ce9bc891750d2c9a4fd8"
SRCREV_ml_dtypes = "24084d9ed2c3d45bf83b7a9bff833aa185bf9172"
SRCREV_ruy = "3286a34cc8de6149ac6844107dfdffac91531e72"
SRCREV_flatbuffers = "e6463926479bd6b330cbcf673f7e917803fd5831"
SRCREV_pthreadpool-source = "c2ba5c50bb58d1397b693740cf75fad836a0d1bf"
SRCREV_xnnpack = "585e73e63cb35c8a416c83a48ca9ab79f7f7d45e"
SRCREV_FXdiv-source = "63058eff77e11aa15bf531df5dd34395ec3017c8"
SRCREV_FP16-source = "4dfe081cf6bcd15db339cf2680b9281b8451eeb3"
SRCREV_kleidiai = "dc69e899945c412a8ce39ccafd25139f743c60b1"
SRCREV_psimd = "072586a71b55b7f8c584153d223e95687148a900"
SRCREV_protobuf = "90b73ac3f0b10320315c2ca0d03a5a9b095d2f66"

inherit setuptools3 cmake

DEPENDS = " \
    flatbuffers-native \
    python3-numpy-native \
    python3-pybind11-native \
    python3-numpy \
    python3 \
    gzip-native \
    protobuf-native \
    python3-pybind11 \
"

# Set building environment variables
TENSORFLOW_TARGET = "linux"
TENSORFLOW_TARGET_ARCH:aarch64 = "aarch64"
TENSORFLOW_TARGET_ARCH:arm = "armv7"

PACKAGECONFIG ??= "xnnpack ruy"
PACKAGECONFIG[xnnpack] = "-DTFLITE_ENABLE_XNNPACK=ON, -DTFLITE_ENABLE_XNNPACK=OFF"
PACKAGECONFIG[ruy] = "-DTFLITE_ENABLE_RUY=ON, -DTFLITE_ENABLE_RUY=OFF"

# XNNPACK is not supported in Linux armv7-a [https://github.com/tensorflow/tensorflow/issues/64358]
# Hence, turning it off
PACKAGECONFIG:remove:arm = "xnnpack"

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite"

# The -O3 flag enables high-level optimizations for performance
# and the -DNDEBUG flag disables debugging code, such as assertions
# to further optimize the build for production use.
# Hence, activate -O3 optimization and disable debug symbols.
OECMAKE_C_FLAGS_RELEASE = "-O3 -DNDEBUG"
OECMAKE_CXX_FLAGS_RELEASE = "-O3 -DNDEBUG -flax-vector-conversions -DTF_MAJOR_VERSION=${TF_MAJOR} -DTF_MINOR_VERSION=${TF_MINOR} -DTF_PATCH_VERSION=${TF_PATCH} -DTF_VERSION_SUFFIX=\"\""

OECMAKE_C_FLAGS_RELEASE:append:arm = " -march=armv7-a -mfpu=neon"
OECMAKE_CXX_FLAGS_RELEASE:append:arm = " -march=armv7-a -mfpu=neon"

do_generate_toolchain_file:append:arm() {
    # Even if we set TENSORFLOW_TARGET_ARCH:arm as armv7, the TFLite CMake
    # build system still uses CMAKE_SYSTEM_PROCESSOR as arm in toolchain.cmake file
    # which leads to build failures. Hence, change it to armv7.
    sed -i 's:CMAKE_SYSTEM_PROCESSOR arm:CMAKE_SYSTEM_PROCESSOR armv7:g' ${WORKDIR}/toolchain.cmake
}

# Build tensorflow-lite.so library, _pywrap_tensorflow_interpreter_wrapper.so library and the benchmark_model application
OECMAKE_TARGET_COMPILE =  "tensorflow-lite benchmark_model _pywrap_tensorflow_interpreter_wrapper"

EXTRA_OECMAKE:append = " \
    -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
    -DCMAKE_SYSTEM_NAME=${TENSORFLOW_TARGET} \
    -DCMAKE_SYSTEM_PROCESSOR=${TENSORFLOW_TARGET_ARCH} \
    -DCMAKE_VERBOSE_MAKEFILE:BOOL=ON \
    -DPYTHON_TARGET_INCLUDE=${RECIPE_SYSROOT}${includedir}/${PYTHON_DIR} \
    -DNUMPY_TARGET_INCLUDE=${RECIPE_SYSROOT}${PYTHON_SITEPACKAGES_DIR}/numpy/_core/include \
    -DPYBIND11_TARGET_INCLUDE=${RECIPE_SYSROOT}${PYTHON_SITEPACKAGES_DIR}/pybind11/include \
    -DTFLITE_HOST_TOOLS_DIR=${STAGING_BINDIR_NATIVE} \
    -DPTHREADPOOL_SOURCE_DIR=${S}/external/pthreadpool-source \
    -DFXDIV_SOURCE_DIR=${S}/external/FXdiv-source \
    -DFP16_SOURCE_DIR=${S}/external/FP16-source \
    -DCPUINFO_SOURCE_DIR=${S}/external/cpuinfo \
    -DKLEIDIAI_SOURCE_DIR=${S}/external/kleidiai \
    -DPSIMD_SOURCE_DIR=${S}/external/psimd \
    -DCMAKE_CXX_STANDARD=17 \
    -DCMAKE_CXX_STANDARD_REQUIRED=ON \
    -DTF_MAJOR_VERSION=${TF_MAJOR} \
    -DTF_MINOR_VERSION=${TF_MINOR} \
    -DTF_PATCH_VERSION=${TF_PATCH} \
    --compile-no-warning-as-error \
    --log-level=VERBOSE \
"

do_configure:prepend() {
    for i in abseil-cpp cpuinfo eigen farmhash fft2d flatbuffers FP16-source FXdiv-source gemmlowp kleidiai ml_dtypes psimd protobuf pthreadpool-source ruy xnnpack; do
        cp -ra ${S}/external/$i ${B}/$i
    done

    # There is no Fortran compiler in the toolchain, but bitbake sets this variable anyway with unavailable
    # binary & it leads to "CMake Error: Could not find compiler set in environment variable FC:"
    unset FC
}

SETUPTOOLS_SETUP_PATH = "${B}"

do_compile:append() {
    # Build the python wheel (procedure extract form the build_pip_package_with_cmake.sh)
    BUILD_DIR=${B}
    TENSORFLOW_DIR=${S}
    TENSORFLOW_LITE_DIR="${TENSORFLOW_DIR}/tensorflow/lite"
    TENSORFLOW_VERSION=$(grep "_VERSION = " "${TENSORFLOW_DIR}/tensorflow/tools/pip_package/setup.py.tpl" | cut -d= -f2 | sed "s/[ '-]//g")
    mkdir -p "${B}/tflite_runtime"
    cp -r "${TENSORFLOW_LITE_DIR}/tools/pip_package/debian" \
          "${TENSORFLOW_LITE_DIR}/tools/pip_package/MANIFEST.in" \
          "${B}"
    cp -r "${TENSORFLOW_LITE_DIR}/python/interpreter_wrapper" "${B}"
    cp "${TENSORFLOW_LITE_DIR}/tools/pip_package/setup_with_binary.py" "${B}/setup.py"
    cp "${TENSORFLOW_LITE_DIR}/python/interpreter.py" \
       "${TENSORFLOW_LITE_DIR}/python/metrics/metrics_interface.py" \
       "${TENSORFLOW_LITE_DIR}/python/metrics/metrics_portable.py" \
       "${B}/tflite_runtime"
    echo "__version__ = '${TENSORFLOW_VERSION}'" >> "${BUILD_DIR}/tflite_runtime/__init__.py"
    echo "__git_version__ = '$(git -C "${TENSORFLOW_DIR}" describe)'" >> "${BUILD_DIR}/tflite_runtime/__init__.py"

    export PACKAGE_VERSION="${TENSORFLOW_VERSION}"
    export PROJECT_NAME="tflite_runtime"
    cp "${B}/_pywrap_tensorflow_interpreter_wrapper.so" "tflite_runtime"

    setuptools3_do_compile
}

do_install() {
    # Install tensorflow-lite dynamic library
    install -d ${D}${libdir}
    install -m 0644 ${B}/libtensorflow-lite.so.${TF_MAJOR} ${D}${libdir}/libtensorflow-lite.so.${PV}

    ln -sf libtensorflow-lite.so.${PV} ${D}${libdir}/libtensorflow-lite.so.${TF_MAJOR}
    ln -sf libtensorflow-lite.so.${PV} ${D}${libdir}/libtensorflow-lite.so

    # armnn expects the the dynamic library of tensorflow lite to be named as "libtensorflowlite.so"
    # Eg - https://github.com/ARM-software/armnn/blob/branches/armnn_24_11/delegate/cmake/Modules/FindTfLite.cmake#L19
    # Hence, create a symbolic link to handle the same
    ln -snf libtensorflow-lite.so ${D}${libdir}/libtensorflowlite.so

    # Install benchmark_model
    install -d ${D}/opt/${PN}/tools/
    install -m 0755 ${B}/tools/benchmark/benchmark_model ${D}/opt/${PN}/tools/

    # Install header files
    install -d ${D}${includedir}/tensorflow/
    cd ${S}/tensorflow/
    cp --parents $(find ./lite -name "*.h*" -not -path "*cmake_build/*") ${D}${includedir}/tensorflow
    cp --parents $(find ./compiler/mlir/lite -name "*.h*" -not -path "*cmake_build/*") ${D}${includedir}/tensorflow
    cd ${B}
    cp --parents $(find . -name "*.h*") ${D}${includedir}/tensorflow/lite

    # Install TFlite python3 interpreter
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/tflite_runtime
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/tflite_runtime.egg-info
    install -m 0755  ${B}/tflite_runtime/* ${D}${PYTHON_SITEPACKAGES_DIR}/tflite_runtime
    install -m 0755  ${B}/tflite_runtime.egg-info/* ${D}${PYTHON_SITEPACKAGES_DIR}/tflite_runtime.egg-info

    # Install pkgconfig file required for NNstreamer build
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/sources/tensorflow2-lite.pc.in ${D}${libdir}/pkgconfig/tensorflow2-lite.pc
    sed -i 's:@version@:${PV}:g
            s:@libdir@:${libdir}:g
            s:@includedir@:${includedir}:g' \
            ${D}${libdir}/pkgconfig/tensorflow2-lite.pc
}

PACKAGES += "${PN}-tools python3-${PN}"

RDEPENDS:${PN} += " \
    ${PN}-tools \
    python3-${PN} \
"

FILES:${PN} = " \
    ${libdir}/lib*.so* \
    ${libdir}/pkgconfig/* \
"

FILES:${PN}-tools = " \
    /opt/* \
"

FILES:python3-${PN} = " \
    ${PYTHON_SITEPACKAGES_DIR}/tflite_runtime \
    ${PYTHON_SITEPACKAGES_DIR}/tflite_runtime.egg-info \
"

RDEPENDS:python3-${PN} += " python3-ctypes python3-numpy "

PROVIDES += "python3-${PN}"
