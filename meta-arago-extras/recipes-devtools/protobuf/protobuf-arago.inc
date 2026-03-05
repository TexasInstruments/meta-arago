# Pin protobuf-native to use the same version as TensorFlow Lite 2.20.0
# to avoid version mismatch errors

# Override version to match
PV = "3.21.9"

# Use specific commit hash that corresponds to v24.3.25 tag
SRCREV = "90b73ac3f0b10320315c2ca0d03a5a9b095d2f66"

# Override SRC_URI to use 3.21.x branch and remove incompatible patches
SRC_URI = "git://github.com/protocolbuffers/protobuf.git;branch=main;protocol=https \
           file://run-ptest \
          "

LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b \
    file://third_party/utf8_range/LICENSE;md5=ef460f31070fd7184bd9059de2e4b132 \
"
